# Volunteers coordination bot

Telegram bot for managin processes taking place at the event venue performed by volunteers.
Bot allows to submit tasks and follow the completion status. 
Also bot can provide some helpful information about the event.

### Installing bot

1. Choose existing telegram bot (or create new one with [@BotFather](https://t.me/BotFather)) which will be used for sending and handling messages.

   For better user experience leave only `/help` command in menu, all other commands can be accessed with `/help` command <small>(based on authentication)</small>.
2. Create configuration file at any place where it can be read and fullfill it with bot token and bot username, and other cofiguration fields.

   Authentication configuration should contains passwords as plain text, be sure that only desired users have access to this file.
   You can define passwords only for roles that are needed (usually it's only `PARTICIPANT` (volunteer) and `MODERATOR`) and don't
   menion other roles at all.
4. Clone this repository and run `mvn package`.

   Projects contains ANTLR grammar but it's already generated to java source files, so you don't have to generate it manually.
5. Copy `VCB.jar` from `target` directory to deployment directory (where bot will be run) and run it with instructions bellow.

### Running bot on host

1. Create configuration file and fulfill it with required parameters.
2. Replace `[path to config file]` with path to the configuration file and execute result in console. 
```
java -Dbot.configuration.file=[path to config file] -jar VCB.jar
```

### Inline query mode

Bot supports inline queries with grammar defined in [this file](https://github.com/Shemplo/volunteers-coordination-bot/blob/master/src/main/resources/antlr/InlineQueryGrammar.g4). 
So you can type something after `@your_bot` and quick completions for commands will be shown based on keywords that you used.

Create task (moderator role required): you can type `@your_bot task Task content` (do not use `;` character as task content) and this will show completion with 2 options for creating question or task for completion for all available groups.
If you need to specify assigment to groups you can add `@your_bot task Task content; groups 1, 2, ...`, also bots supports nerations, so you can write `...; groups -1, -2` and this will mean that task is assigned to all groups except `1` and `2`.

Answer task (at least participant role required): tasks have button that will automatically fill inline query template for the answer, it will look like `@your_bot id 8; answer`, and all you need is to click on suggested quick response or 
type answer (do not use `;`) after `answer` keyword and click on suggested option.
