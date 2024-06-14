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
  LCD_I2C getLcd();
  bool getEnrollment();
  bool getAuthentication();
  void setEnrollment(bool enrollment);
  void setAuthentication(bool authentication);
  void sendCommand(const char* cmd, int delayTime);
  void sendSMS(const char* phoneNumber, const char* message);

private:
  uint8_t _id;
  bool _enrollment;
  bool _authenticationAndVerification;    
};

#endif
