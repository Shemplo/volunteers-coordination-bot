# Volunteers coordination bot

Telegram bot for managin processes taking place at the event venue performed by volunteers.
Bot allows to submit tasks and follow the completion status. 
Also bot can provide some helpful information about the event.

### Running bot

1. Create configuration file and fulfill it with required parameters
2. Replace `[path to config file]` with path to the configuration file and execute result in console. 
```
java -Dbot.configuration.file=[path to config file] -jar VCB.jar
```