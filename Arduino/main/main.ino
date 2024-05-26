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

int DELAY = 2000;

void setup() {

   // initializing lcd display
    fingerPrint.getLcd().begin();
    fingerPrint.getLcd().display();
    fingerPrint.getLcd().clear();
    fingerPrint.getLcd().backlight();

    // displaying system startup 
    fingerPrint.getLcd().print("SYSTEM STARTING");
    fingerPrint.getLcd().backlight();
    delay(DELAY);
    
    // connecting to the network 
    fingerPrint.getLcd().clear(); 
    fingerPrint.getLcd().backlight();
    fingerPrint.getLcd().print("CONNECT TO A");
    fingerPrint.getLcd().setCursor(0,1);
    fingerPrint.getLcd().print("NETWORK");
    fingerPrint.getLcd().backlight();
    delay(DELAY);

}

void loop() {

}

void connectToNetwork() {

   
  pinMode(pin, OUTPUT);
 // making the esp32 act as a wifi client 
  WiFi.mode(WIFI_STA);

    Serial.begin(9500);
   
   // wifi manager for providing ssid and password to the system
    WiFiManager wm;
  
  // changing the mode of esp32 module to turn to an access point  
    bool res;
     res = wm.autoConnect("SmartLock","12345678");
     fingerPrint.getLcd().backlight();

}





