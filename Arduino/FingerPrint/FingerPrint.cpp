// libraries
//include fingerprint
#include "FingerPrint.h"
#include "String.h"
#include "Firebase_ESP_Client.h"

#include <SoftwareSerial.h>


SoftwareSerial a9gSerial(5, 15); // RX, TX
#define MODEM_RX 16
#define MODEM_TX 17
#define mySerial Serial2
#define DELAY 2000
#define pin 2
#define buzzlePin 23

int attempts = 0;
int buzzleDelay = 5000;

Adafruit_Fingerprint finger = Adafruit_Fingerprint(&mySerial);

LCD_I2C lcd = LCD_I2C(0x27, 16, 2);

FirebaseData fData;

FingerPrint::FingerPrint() {}

void FingerPrint::initializeFingerprint() {

  pinMode(pin, OUTPUT);

  pinMode(buzzlePin, OUTPUT);

  checkFingerPrintSensor();
  
  displayFingerPrintProperties();


  finger.getTemplateCount();

  if (finger.templateCount == 0) {
    Serial.print("Sensor doesn't contain any fingerprint data. Please enroll fingerprint.");
    _enrollment = true;
    _authenticationAndVerification = false;

  }
  else {
    Serial.println("Waiting for valid finger...");
      Serial.print("Sensor contains "); Serial.print(finger.templateCount); Serial.println(" templates");
      _enrollment = false;
      _authenticationAndVerification = true;
  }
   
}

uint8_t FingerPrint::getFingerprintEnroll() { 

  int p = -1;
  Serial.print("Waiting for valid finger to enroll as #"); Serial.println(_id);
   
   lcd.clear();
   lcd.backlight();
   lcd.print("Place finger");
   lcd.setCursor(0, 1); 
   lcd.print("on sensor");
   delay(DELAY);
  while (p != FINGERPRINT_OK) {
    p = finger.getImage();
    switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image taken");
      lcd.clear();
      lcd.backlight();
      lcd.print("Image taken");
      delay(DELAY);
      break;
    case FINGERPRINT_NOFINGER:
      Serial.print(".");
      break;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      lcd.clear();
      lcd.backlight();
      lcd.print("ERROR");
      delay(DELAY);
      break;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("Imaging error");
      lcd.clear();
      lcd.backlight();
      lcd.print("ERROR");
      delay(DELAY);
      break;
    default:
      Serial.println("Unknown error");
      lcd.clear();
      lcd.backlight();
      lcd.print("ERROR");
      delay(DELAY);
      break;
    }
  }

  // OK success!

  p = finger.image2Tz(1);
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image converted");
      lcd.clear();
      lcd.backlight();
      lcd.print("Image converted");
      delay(DELAY);
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      lcd.clear();
      lcd.backlight();
      lcd.print("ERROR");
      delay(DELAY);
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      lcd.clear();
      lcd.backlight();
      lcd.print("ERROR");
      delay(DELAY);
      return p;
    case FINGERPRINT_FEATUREFAIL:
      Serial.println("Could not find fingerprint features");
      lcd.clear();
      lcd.backlight();
      lcd.print("ERROR");
      delay(DELAY);
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      Serial.println("Could not find fingerprint features");
      lcd.clear();
      lcd.backlight();
      lcd.print("ERROR");
      delay(DELAY);
      return p;
    default:
      Serial.println("Unknown error");
      lcd.clear();
      lcd.backlight();
      lcd.print("ERROR");
      delay(DELAY);
      return p;
  }

  Serial.println("Remove finger");
  lcd.clear();
  lcd.backlight();
  lcd.print("Remove finger");
  delay(DELAY);
  p = 0;
  while (p != FINGERPRINT_NOFINGER) {
    p = finger.getImage();
  }
  Serial.print("ID "); Serial.println(_id);
  p = -1;
  Serial.println("Place same finger again");
  lcd.clear();
  lcd.backlight();
  lcd.print("Place same");
  lcd.setCursor(0, 1); 
  lcd.print("finger again");
  delay(DELAY);
  while (p != FINGERPRINT_OK) {
    p = finger.getImage();
    switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image taken");
      lcd.clear();
      lcd.backlight();
      lcd.print("Image taken");
      delay(DELAY);
      break;
    case FINGERPRINT_NOFINGER:
      Serial.print(".");
      break;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      lcd.clear();
      lcd.backlight();
      lcd.print("COMMUNICATION");
      lcd.setCursor(0,1);
      lcd.print("ERROR");
      delay(DELAY);
      break;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("Imaging error");
      lcd.clear();
      lcd.backlight();
      lcd.print("IMAGING ERROR");
      delay(DELAY);
      break;
    default:
      Serial.println("Unknown error");
      lcd.clear();
      lcd.backlight();
      lcd.print("UNKNOWN ERROR");
      delay(DELAY);
      break;
    }
  }

  // OK success!

  p = finger.image2Tz(2);
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image converted");
      lcd.clear();
      lcd.backlight();
      lcd.print("Image converted");
      delay(DELAY);
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      lcd.clear();
      lcd.backlight();
      lcd.print("IMAGE TOO MESSY");
      delay(DELAY);
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      lcd.clear();
      lcd.backlight();
      lcd.print("COMMUNICATION");
      lcd.setCursor(0,1);
      lcd.print("ERROR");
      delay(DELAY);
      return p;
    case FINGERPRINT_FEATUREFAIL:
      Serial.println("Could not find fingerprint features");
      lcd.clear();
      lcd.backlight();
      lcd.print("ERROR");
      delay(DELAY);
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      Serial.println("Could not find fingerprint features");
      lcd.clear();
      lcd.backlight();
      lcd.print("ERROR");
      delay(DELAY);
      return p;
    default:
      Serial.println("Unknown error");
      lcd.clear();
      lcd.backlight();
      lcd.print("ERROR");
      delay(DELAY);
      return p;
  }

  // OK converted!
  Serial.print("Creating model for #");  Serial.println(_id);

  p = finger.createModel();
  if (p == FINGERPRINT_OK) {
    Serial.println("Prints matched!");
    lcd.clear();
    lcd.backlight();
    lcd.print("Prints matched");
    delay(DELAY);
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
    lcd.clear();
    lcd.backlight();
    lcd.print("COMMUNICATION");
    lcd.setCursor(0,1);
    lcd.print("ERROR");
    delay(DELAY);
    return p;
  } else if (p == FINGERPRINT_ENROLLMISMATCH) {
    Serial.println("Fingerprints did not match");
    lcd.clear();
    lcd.backlight();
    lcd.print("FINGERPRINTS DID");
    lcd.setCursor(0,1);
    lcd.print("NOT MATCH");
    delay(DELAY);
    lcd.clear();
    lcd.print("TRY AGAIN....");
    delay(DELAY);
    return p;
  } else {
    Serial.println("Unknown error");
    lcd.clear();
    lcd.backlight();
    lcd.print("UNKNOWN ERROR");
    delay(DELAY);
    return p;
  }

  Serial.print("ID "); Serial.println(_id);
  p = finger.storeModel(_id);
  if (p == FINGERPRINT_OK) {

   String fName = "Default";
    String surname = "Default";
    String permission = "deny";
  if(Firebase.ready() && true) {
           if(Firebase.RTDB.setString(&fData, "/USER/" + String(_id) + "/fName", fName) && 
             Firebase.RTDB.setString(&fData, "/USER/" + String(_id) + "/surname", surname) && 
             Firebase.RTDB.setString(&fData, "/USER/" + String(_id) + "/permission", permission) ){
             Serial.println("Successfully added fingerprint to Firebase");
           } 
    }
    Serial.println("Stored!");
    lcd.clear();
    lcd.backlight();
    lcd.print("Stored!");
    _enrollment = false;
    _authenticationAndVerification = true;
    delay(DELAY);
    lcd.clear();
    lcd.backlight();
    lcd.print("CHECKING LOCK");
    lcd.setCursor(0,1);
    lcd.print("  STATUS");
    delay(DELAY);

    int pinStatus = digitalRead(pin);
    if(pinStatus == HIGH) {
    lcd.clear();
    lcd.backlight();
    lcd.print("   UNLOCKED");
    delay(DELAY);
    } else {
    lcd.clear();
    lcd.backlight();
    lcd.print("  LOCKED");
    delay(DELAY);
    }
    
    lcd.clear();
    lcd.backlight();
    lcd.print("Stored!");
    _enrollment = false;
    _authenticationAndVerification = true;
    delay(DELAY);
    
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
    lcd.clear();
    lcd.print("COMMUNICATION");
    lcd.setCursor(0,1);
    lcd.print("ERROR");
    delay(DELAY);
    return p;
  } else if (p == FINGERPRINT_BADLOCATION) {
    Serial.println("Could not store in that location");
    lcd.clear();
    lcd.backlight();
    lcd.print("ERROR");
    delay(DELAY);
    return p;
  } else if (p == FINGERPRINT_FLASHERR) {
    Serial.println("Error writing to flash");
    lcd.clear();
    lcd.backlight();
    lcd.print("ERROR WRITING");
    lcd.setCursor(0,1);
    lcd.print("TO FLASH");
    delay(DELAY);
    return p;
  } else {
    Serial.println("Unknown error");
    lcd.clear();
    lcd.backlight();
    lcd.print("ERROR");
    delay(DELAY);
    return p;
  }

  return true;

}
void FingerPrint::checkFingerPrintSensor() { 
  
  Serial.begin(9600);
  while (!Serial);  // For Yun/Leo/Micro/Zero/...
  delay(100);
  Serial.println("\n\nAdafruit finger detect test");

  a9gSerial.begin(9600);
  Serial.println("Initializing A9G module...");
  // Give the module some time to initialize
  delay(3000);

// set the data rate for the sensor serial port
  finger.begin(57600);
  delay(5);
  if (finger.verifyPassword()) {
    Serial.println("Found fingerprint sensor!");
  } else {
    Serial.println("Did not find fingerprint sensor :(");
    while (1) { delay(1); }
  }

}
void FingerPrint::displayFingerPrintProperties() {
Serial.println(F("Reading sensor parameters"));
  finger.getParameters();
  Serial.print(F("Status: 0x")); Serial.println(finger.status_reg, HEX);
  Serial.print(F("Sys ID: 0x")); Serial.println(finger.system_id, HEX);
  Serial.print(F("Capacity: ")); Serial.println(finger.capacity);
  Serial.print(F("Security level: ")); Serial.println(finger.security_level);
  Serial.print(F("Device address: ")); Serial.println(finger.device_addr, HEX);
  Serial.print(F("Packet len: ")); Serial.println(finger.packet_len);
  Serial.print(F("Baud rate: ")); Serial.println(finger.baud_rate);
}

// Enroll

void FingerPrint::readyToEnroll() { 

    Serial.println("Ready to enroll a fingerprint!");
          Serial.println("Please type in the ID # (from 1 to 127) you want to save this finger as...");

       finger.getTemplateCount(); 

          if (finger.templateCount > 0) {
            _id = finger.templateCount;
            _id = _id + 1;
          } else {
            _id = 1;
          }

          if (_id == 0) {// ID #0 not allowed, try again!
           return;
         }

  Serial.print("Enrolling ID #");
  Serial.println(_id);

  while (!getFingerprintEnroll() );
}

void FingerPrint::checkFingerPrintSensor() {
  Serial.begin(9600);
  while (!Serial);  // For Yun/Leo/Micro/Zero/...
  delay(100);
  Serial.println("\n\nAdafruit finger detect test");

  // set the data rate for the sensor serial port
  finger.begin(57600);
  delay(5);
  if (finger.verifyPassword()) {
    Serial.println("Found fingerprint sensor!");
  } else {
    Serial.println("Did not find fingerprint sensor :(");
    while (1) { delay(1); }
  }

}

uint8_t FingerPrint::getFingerprintID() {
 uint8_t p = finger.getImage();
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image taken");
      break;
    case FINGERPRINT_NOFINGER:
      //Serial.println("No finger detected");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_IMAGEFAIL:
      Serial.println("Imaging error");
      return p;
    default:
      Serial.println("Unknown error");
      return p;
  }

  // OK success!

  p = finger.image2Tz();
  switch (p) {
    case FINGERPRINT_OK:
      Serial.println("Image converted");
      break;
    case FINGERPRINT_IMAGEMESS:
      Serial.println("Image too messy");
      return p;
    case FINGERPRINT_PACKETRECIEVEERR:
      Serial.println("Communication error");
      return p;
    case FINGERPRINT_FEATUREFAIL:
      Serial.println("Could not find fingerprint features");
      return p;
    case FINGERPRINT_INVALIDIMAGE:
      Serial.println("Could not find fingerprint features");
      return p;
    default:
      Serial.println("Unknown error");
      return p;
  }

  // OK converted!
  p = finger.fingerSearch();
  if (p == FINGERPRINT_OK) {
    Serial.println("Found a print match!");
    uint8_t id = finger.fingerID;
    String dataPath = "/USER/" + String(id);
    if(Firebase.ready() && true) {
         if(Firebase.RTDB.getString(&fData, dataPath + "/permission") ) {
          if(fData.dataType() == "string"){
            String permission = fData.stringData();
             String name = "";
             Serial.println("Permission: " + permission);

            
    attempts = 0;
    digitalWrite(pin,HIGH);
    Serial.println("UNLOCKED");
    lcd.clear();
    lcd.backlight();
    lcd.print("     UNLOCKED");
    delay(3000);
    digitalWrite(pin,LOW);
    Serial.println("LOCKED");
    lcd.clear();
    lcd.backlight();
    lcd.print("     LOCKED");
  } else if (p == FINGERPRINT_PACKETRECIEVEERR) {
    Serial.println("Communication error");
    lcd.clear();
    lcd.backlight();
    lcd.print("Place finger");
    lcd.setCursor(0,1);
    lcd.print("Properly");
    delay(DELAY);
    return p;
  } else if (p == FINGERPRINT_NOTFOUND) {
    Serial.println("Did not find a match");
    lcd.clear();
    lcd.backlight();
    lcd.print("     DENIED");
    delay(DELAY);
    lcd.clear();
    lcd.backlight();
    lcd.print("     LOCKED");

    attempts++;
    if(attempts == 3) {
      // Configure the A9G module for SMS
      sendCommand("AT+CMGF=1", 3000); // Set SMS to text mode
      sendSMS("+265990409624", "\"Alert: UNAUTHORISED INDIVIDUAL IS ATTEMPTETING TO ACCESS YOUR LOCK. PLEASE CHECK YOUR TELEGRAM FOR A PICTURE.\""); // Sending SMS to the specified number
      digitalWrite(buzzlePin,HIGH);
      delay(buzzleDelay);
      digitalWrite(buzzlePin,LOW);
      attempts = 0;
    }
    return p;
  } else {
    Serial.println("Unknown error");
    lcd.clear();
    lcd.backlight();
    lcd.print("Place finger");
    lcd.setCursor(0,1);
    lcd.print("Properly");
    delay(DELAY);
    lcd.clear();
    lcd.backlight();
    lcd.print("     LOCKED");
    return p;
  }

  // found a match!
  Serial.print("Found ID #"); Serial.print(finger.fingerID);
  Serial.print(" with confidence of "); Serial.println(finger.confidence);

  return finger.fingerID;
}

void FingerPrint::displayFingerPrintProperties() {
  Serial.println(F("Reading sensor parameters"));
  finger.getParameters();
  Serial.print(F("Status: 0x")); Serial.println(finger.status_reg, HEX);
  Serial.print(F("Sys ID: 0x")); Serial.println(finger.system_id, HEX);
  Serial.print(F("Capacity: ")); Serial.println(finger.capacity);
  Serial.print(F("Security level: ")); Serial.println(finger.security_level);
  Serial.print(F("Device address: ")); Serial.println(finger.device_addr, HEX);
  Serial.print(F("Packet len: ")); Serial.println(finger.packet_len);
  Serial.print(F("Baud rate: ")); Serial.println(finger.baud_rate);
}

void FingerPrint::setEnrollment(bool enrollment) {
  _enrollment = enrollment;
}

void FingerPrint::setAuthentication(bool authentication) {
  _authenticationAndVerification = authentication;
}

bool FingerPrint::getEnrollment() {
  return _enrollment;
}

LCD_I2C FingerPrint::getLcd(){
  return lcd;
}

bool FingerPrint::getAuthentication() {
  return _authenticationAndVerification;
}

void FingerPrint::sendCommand(const char* cmd, int delayTime) {
  a9gSerial.println(cmd);
  delay(delayTime);
   while (a9gSerial.available()) {
    Serial.write(a9gSerial.read()); // Print the response to the Serial Monitor
  }
}

void FingerPrint::sendSMS(const char* phoneNumber, const char* message) {
  sendCommand("AT+CMGF=1", 100); // Set SMS to text mode
a9gSerial.print("AT+CMGS=\"");
  a9gSerial.print(phoneNumber);
delay(100); // Wait for prompt
  a9gSerial.print(message);
  delay(100);
a9gSerial.write(26); // Send Ctrl+Z to indicate end of message
  delay(1000); // Wait for the SMS to be sent
  a9gSerial.println("\"");
 while (a9gSerial.available()) {
    Serial.write(a9gSerial.read()); // Print the response to the Serial Monitor
  }
}
