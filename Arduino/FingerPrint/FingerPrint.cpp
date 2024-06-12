#include "FingerPrint.h"

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

FingerPrint::FingerPrint() {}

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
    Serial.println("Stored!");
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