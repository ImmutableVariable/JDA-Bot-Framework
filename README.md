# JDA Bot Framework

A pretty basic framework that has a sqlite database. With this, you should be able to create a bot with commands that use a database.

## Setup

1. Clone the repository
2. Create a .env file in the resources folder (there is a .env.example file that you can read and base it off of)
3. Run the bot with gradle, 
  1. if you add the args "global" it will register the bot's slash commands globally.
  2. if you add the args "guild" it will register the bot's slash commands to the guild specified in the .env file.

## Commands
1. /ping -> Responds with "Pong!"
2. /Echo -> Example of text input, echos the input back to the user.
3. /database-test -> Example of database, just runs `select 1`