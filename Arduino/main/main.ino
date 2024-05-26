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

      if(!res) {
        Serial.println("Failed to connect");
        fingerPrint.getLcd().clear(); 
        fingerPrint.getLcd().backlight();
        fingerPrint.getLcd().print("CONNECTION FAILED");
        fingerPrint.getLcd().backlight();
        delay(DELAY);
    } else {   
        Serial.println("connected to WIFI");
        fingerPrint.getLcd().clear(); 
        fingerPrint.getLcd().backlight();
        fingerPrint.getLcd().print("CONNECTED TO");
        fingerPrint.getLcd().setCursor(0,1);
        fingerPrint.getLcd().print(""+ WiFi.SSID());
        fingerPrint.getLcd().backlight();
        delay(DELAY);

        fingerPrint.getLcd().clear(); 
        fingerPrint.getLcd().backlight();
        fingerPrint.getLcd().print("CONNECTED TO");
        fingerPrint.getLcd().setCursor(0,1);
        fingerPrint.getLcd().print("DATABASE");
        fingerPrint.getLcd().backlight();
        delay(DELAY);


        config.api_key = API_KEY;
        config.database_url = DATABASE_URL;

        if(Firebase.signUp(&config, &auth,"","")) {
          Serial.println("Signup OK");
          signOk = true;
        fingerPrint.getLcd().clear(); 
        fingerPrint.getLcd().backlight();
        fingerPrint.getLcd().print("   CONNECTED");
        fingerPrint.getLcd().backlight();
        delay(DELAY);
        } else {
          Serial.printf("%s\n", config.signer.signupError.message.c_str());
        fingerPrint.getLcd().clear(); 
        fingerPrint.getLcd().backlight();
        fingerPrint.getLcd().print("   FAILED");
        fingerPrint.getLcd().backlight();
        delay(DELAY);
        }


        Firebase.begin(&config, &auth);
        Firebase.reconnectWiFi(true);

        fingerPrint.initializeFingerprint();

        if(!Firebase.RTDB.beginStream(&fbStream1, "/STATUS/lockStatus"))
        Serial.printf("Stream 1 error %s\n\n", fbStream1.errorReason().c_str());

        if(!Firebase.RTDB.beginStream(&fbStream2, "/STATUS/sensorStatus"))
        Serial.printf("Stream 2 error %s\n\n", fbStream2.errorReason().c_str());     
    } 
}

void readDataFromFirebase() {

  if(Firebase.ready() && signOk) {
      if(!Firebase.RTDB.readStream(&fbStream1))
        Serial.printf("Stream 1 error %s\n\n", fbStream1.errorReason().c_str());
      if(fbStream1.streamAvailable()){
        if(fbStream1.dataType() == "string") {
          lockStatus = fbStream1.stringData();
          Serial.println("SUCCESSFULLY READ FROM " + fbStream1.dataPath() + ": " + lockStatus + " (" + fbStream1.dataType() + ")");

          if(lockStatus == "Locked" ) {

            digitalWrite(pin,LOW);
            Serial.println("LOCKED");
            fingerPrint.getLcd().clear();
            fingerPrint.getLcd().backlight();
            fingerPrint.getLcd().print("     LOCKED");
            fingerPrint.getLcd().backlight();

          } else if(lockStatus == "Unlocked" ) {

            digitalWrite(pin,HIGH);
            Serial.println("UNLOCKED");
            fingerPrint.getLcd().clear();
            fingerPrint.getLcd().backlight();
            fingerPrint.getLcd().print("     UNLOCKED");
            fingerPrint.getLcd().backlight();

          }
        
        
        }
      } 

      if(!Firebase.RTDB.readStream(&fbStream2))
        Serial.printf("Stream 1 error %s\n\n", fbStream2.errorReason().c_str());
      if(fbStream2.streamAvailable()){
        if(fbStream1.dataType() == "string") {
          sensorState = fbStream2.stringData();
          Serial.println("SUCCESSFULLY READ FROM " + fbStream2.dataPath() + ": " + sensorState + " (" + fbStream2.dataType() + ")");

          if(sensorState == "Enrollment") {

            fingerPrint.setEnrollment(true);
            fingerPrint.setAuthentication(false);

          } else if(sensorState == "Authentication") {

            fingerPrint.setEnrollment(false);
            fingerPrint.setAuthentication(true);

          } 


        }
      } 
  }
}





