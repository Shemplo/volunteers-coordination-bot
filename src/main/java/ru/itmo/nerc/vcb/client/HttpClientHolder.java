package ru.itmo.nerc.vcb.client;

import lombok.experimental.UtilityClass;
import okhttp3.OkHttpClient;
import ru.itmo.nerc.vcb.cfg.ConfigurationHolder;

import java.net.InetSocketAddress;
import java.net.Proxy;

@UtilityClass
public class HttpClientHolder {

    private volatile OkHttpClient instance;

    public OkHttpClient getInstance () {
        if (instance == null) {
            synchronized (HttpClientHolder.class) {
                if (instance == null) {
                    final var configuration = ConfigurationHolder.getConfigurationFromSingleton ();
                    final var proxy = configuration.getProxy ();

                    final var clientBuilder = new OkHttpClient.Builder ();
                    if (proxy != null) {
                        final var proxyAddress = new InetSocketAddress(proxy.getHost (), proxy.getPort ());
                        clientBuilder.proxy (new Proxy(proxy.getType (), proxyAddress));
                    }
                    instance = clientBuilder.build();
                }
            }
        }

        return instance;
    }
}
