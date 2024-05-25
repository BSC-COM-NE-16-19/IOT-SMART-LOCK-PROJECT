#include <WiFiManager.h>
#include <FingerPrint.h> 
#include <WiFi.h>
#include <String.h>

#include <Firebase_ESP_Client.h>
#include <addons/TokenHelper.h>
#include <addons/RTDBHelper.h>

#define API_KEY ""
#define DATABASE_URL ""

#define pin 2

FingerPrint fingerPrint = FingerPrint();

FirebaseData fbData, fbStream1, fbStream2;
FirebaseAuth auth;
FirebaseConfig config;

bool signOk = false;

String lockStatus, sensorState;

void setup() {

}

void loop() {

}