#ifndef FingerPrint_h
#define FingerPrint_h

#include "LCD-I2C.h"
#include "Adafruit_Fingerprint.h"
#include "Arduino.h"


class FingerPrint {

public:
  FingerPrint();
  uint8_t getFingerprintEnroll();
  uint8_t getFingerprintID();
  void initializeFingerprint();
  void displayFingerPrintProperties();
  void checkFingerPrintSensor();
  void readyToEnroll();
  bool getEnrollment();
  bool getAuthentication();
  void setEnrollment(bool enrollment);
  void setAuthentication(bool authentication);

private:
  uint8_t _id;
  bool _enrollment;
  bool _authenticationAndVerification;    
};

#endif