#ifndef FingerPrint_h
#define FingerPrint_h

#include "LCD-I2C.h"
#include "Adafruit_Fingerprint.h"
#include "Arduino.h"


class FingerPrint {

public:
  FingerPrint();

private:
  uint8_t _id;
  bool _enrollment;
  bool _authenticationAndVerification;    
};

#endif