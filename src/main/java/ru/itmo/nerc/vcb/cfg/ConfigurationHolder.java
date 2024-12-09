package ru.itmo.nerc.vcb.cfg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeId;
import org.yaml.snakeyaml.nodes.ScalarNode;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConfigurationHolder {
    
    private static volatile ConfigurationHolder instance;
    
    public static ConfigurationHolder getInstance () {
        if (instance == null) {
            synchronized (ConfigurationHolder.class) {
                if (instance == null) {
                    instance = new ConfigurationHolder ();
                }
            }
        }
        
        return instance;
    }
    
    public static BotConfiguration getConfigurationFromSingleton () {
        return getInstance ().getConfiguration ();
    }
    
    //private static final File INDEX_CFG_FILE = new File ("cfg", "index.yml");
    
    //private final BotConfiguration configurationProxy = makeConfigurationProxy (BotConfiguration.class, INDEX_CFG_FILE);
    private final BotConfiguration configurationProxy;
    private final File indexConfigurationFile;
    
    private ConfigurationHolder () {
        final var indexFilePath = System.getProperty ("bot.configuration.file");
        if (indexFilePath == null) {
            throw new IllegalStateException ("Configuration file property is not defined");
        }
        
        indexConfigurationFile = new File (indexFilePath);
        if (!indexConfigurationFile.canRead ()) {
            throw new IllegalStateException ("Configuration file can't be read");
        }
        
        configurationProxy = makeConfigurationProxy (BotConfiguration.class, indexConfigurationFile);
    }
    
    public BotConfiguration getConfiguration () {
        return configurationProxy;
    }
    
    private <C> C makeConfigurationProxy (Class <C> type, File file) {
        final var factory = new ProxyFactory ();
        factory.setSuperclass (type);
        
        try {
            //log.info ("Make proxy class for {} from file {}", type.getSimpleName (), file);
            final var handler = new ConfigurationProxyWrapper <> (type, file);
            
            @SuppressWarnings ("unchecked")
            final var proxy = (C) factory.create (new Class <?> [0], new Object [0], handler);
            //log.info ("Proxy: {}", proxy);
            
            return proxy;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException re) {
            return null;
        }
    }
    
    @RequiredArgsConstructor
    private class ConfigurationProxyWrapper <C> implements MethodHandler {
                
        private static final long IGNORE_CONFIG_UPDATE_TIME = 1000L;
        
        private final Class <C> type;
        private final File file;
        
        private volatile C configuration;
        private volatile long modificationTime = 0L;
        
        public void checkForUpdates () {
            if (System.currentTimeMillis () - modificationTime > IGNORE_CONFIG_UPDATE_TIME) {
                final var lastModified = indexConfigurationFile.lastModified ();
                if (lastModified > modificationTime || (lastModified == 0L && modificationTime > 0L)) {
                    modificationTime = lastModified;
                    configuration = null;
                }
            }
            
            if (configuration == null) {
                synchronized (this) {
                    if (configuration == null) {
                        final var directory = file.getParentFile ();
                        final var yaml = new Yaml (new ConfigurationConstructor (directory));
                        log.info ("Reading {} from file {}", type.getSimpleName (), file.toString ());
                        
                        try (final var fis = new FileInputStream (file)) {
                            configuration = yaml.loadAs (fis, type);
                        } catch (IOException ioe) {
                            log.error ("Failed to read and parse configuration file", ioe);
                            configuration = null;
                        }
                    }
                }
            }
        }
        
        @Override
        public Object invoke (Object self, Method thisMethod, Method proceed, Object [] args) throws Throwable {
            checkForUpdates ();
            return configuration == null ? null : thisMethod.invoke (configuration, args);
        }
        
    }
    
    private class ConfigurationConstructor extends Constructor {
        
        private File dir;
        
        public ConfigurationConstructor (File dir) {
            super (new LoaderOptions ());
            this.dir = dir;
            
            yamlClassConstructors.put (NodeId.scalar, new LazyConstructor ());
        }
        
        private class LazyConstructor extends Constructor.ConstructScalar {
            
            @Override
            public Object construct (Node node) {
                final var nodeType = node.getType ();
                final var value = ((ScalarNode) node).getValue ();
                
                if (value.endsWith (".yml") && new File (dir, value).isFile ()) {
                    return makeConfigurationProxy (nodeType, new File (dir, value));
                } else {
                    return super.construct (node);
                }
            }
            
        }
        
    }
    
}
