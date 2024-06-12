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