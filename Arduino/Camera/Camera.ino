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
