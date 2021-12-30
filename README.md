# TelegramBot
To run bot telegrams on a local server, you need to download the ngrok application.
Using it, we can create a real server on the Internet that will redirect requests to a local server.

1. Run ngrok with http 5000 parameters. ( ngrok.exe http 5000 )
![](../Screenshot_2.png)

2. Copy the https address and write the request in the browser :
https://api.telegram.org/token/setWebhook?url=https_addres

token = bot5084327029:AAESpIG-jM6cppxWri0Z5HfccTEIJB8M7RY

3. Create a sql database with a script:
   create database telegram_bot;

4. Change the database configuration, write your username, password and url in application.properties

![](../Screenshot_1.png)

5. Run SpringBoot application.

