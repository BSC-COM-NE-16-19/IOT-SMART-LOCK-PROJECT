//code to send a picture of the intruder to the owner of the building after 3 wrong attempts of unlocking the door
//through telegram

//including necessary libraries
#include <Arduino.h>
#include <WiFi.h>
#include <WiFiClientSecure.h>
#include "soc/soc.h"
#include "soc/rtc_cntl_reg.h"
#include "esp_camera.h"
#include <UniversalTelegramBot.h>
#include <ArduinoJson.h>
#include <String.h>

#include <Firebase_ESP_Client.h>
#include <addons/TokenHelper.h>
#include <addons/RTDBHelper.h>

#define API_KEY "AIzaSyCehFFOt1zgg4VkE1Lol9zvWoNj-q5AWx0"
#define DATABASE_URL "https://test-c2c3c-default-rtdb.firebaseio.com/"

FirebaseData fbData, fbStream1;
FirebaseAuth auth;
FirebaseConfig config;

bool signOk = false;

String message = " ";

String welcome = " ";
const char* ssid = "HUAWEI Y7 2019";
const char* password = "1214@KB.one";

int numNewMessages;

// Initialize Telegram BOT
String BOTtoken = "6565922428:AAGTv0YP3NE6I_YGznPx4GwNqoXQVCvkMTM";  // your Bot Token (Get from Botfather)

// Use @myidbot to find out the chat ID of an individual or a group
// Also note that you need to click "start" on a bot before it can
// message you
String CHAT_ID = "7105758160";

bool sendPhoto = false;

WiFiClientSecure clientTCP;
UniversalTelegramBot bot(BOTtoken, clientTCP);
UniversalTelegramBot bot(BOTtoken, clientTCP);

#define FLASH_LED_PIN 4
bool flashState = LOW;

//Checks for new messages every 1 second.
int botRequestDelay = 1000;
unsigned long lastTimeBotRan;


