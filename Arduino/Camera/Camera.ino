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

//CAMERA_MODEL_AI_THINKER
#define PWDN_GPIO_NUM     32
#define RESET_GPIO_NUM    -1
#define XCLK_GPIO_NUM      0
#define SIOD_GPIO_NUM     26
#define SIOC_GPIO_NUM     27

#define Y9_GPIO_NUM       35
#define Y8_GPIO_NUM       34
#define Y7_GPIO_NUM       39
#define Y6_GPIO_NUM       36
#define Y5_GPIO_NUM       21
#define Y4_GPIO_NUM       19
#define Y3_GPIO_NUM       18
#define Y2_GPIO_NUM        5
#define VSYNC_GPIO_NUM    25
#define HREF_GPIO_NUM     23
#define PCLK_GPIO_NUM     22





