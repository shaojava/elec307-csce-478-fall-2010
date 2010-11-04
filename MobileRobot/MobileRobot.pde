#define WAIT 1000
#define INITWAIT 500
#include <Wire.h>
#include <avr/sleep.h>

void setup() {
  delay(500);

  //Initialize the LCD Display
  initLCDScreen();
  delay(1000);

  //Display project title
  LCDPrintLine("Presenting:", 1, 0);
  LCDPrintLine("Mobile Robot", 2, 0);
  delay(WAIT);

  //Display authors
  LCDPrintLine("by: Micheal K", 1, 0);
  LCDPrintLine(" and Bryan B", 2, 0);
  delay(WAIT);

  //Display the splash screen
  LCDPrintLine("University of", 1, 0);
  LCDPrintLine("Nebraska-Linc", 2, 0);
  delay(WAIT);

  //Initialize the Accelerometer
  initAccelerometer();
  delay(INITWAIT);

  //Initialize the Compass
  //Wire.begin();
  //initCompass();
  //delay(WAIT);

  //Initialize Motor Controls
  initMotors();
  delay(INITWAIT);

  //Initalize the hall effect sensor
  initHallSensor();
  delay(INITWAIT);

  //Initalize IR sensors
  initIRSensors();
  delay(INITWAIT);

  //Initialize the FM serial port
  initFMTransmitter();
  delay(INITWAIT);

  //Begin Serial connection
  Serial1.begin(115200);

  //Clear the Screen
  LCDClearScreen();
  delay(INITWAIT);

  //Establish connection with the laptop
  establishConnection();
  

}

void loop() {

  Serial1.flush();
  switch(fmWaitForCommand()) {

    //Get  X axis
  case 2:
    fmSendInteger(accelGetX());
    break;

    //Get accelerometer Y axis
  case 3:
    fmSendInteger(accelGetY());
    break;

    //Get accelerometer Z axis
  case 4:
    fmSendInteger(accelGetZ());
    break;

    //Set the accelerometer sensitivity
  case 5:
    accelSetSensitivity(fmReceiveInt());
    break;

    //Sleep the Accelerometer
  case 6:
    accelSleep();
    break;

    //Wake the Accelerometer
  case 7:
    accelWake();
    break;

    //Get front IR sensor
  case 8:
    fmSendInteger(irGetFront());
    break;

    //Get left IR sensor
  case 9:
    fmSendInteger(irGetLeft());
    break;

    //Get right IR sensor
  case 10:
    fmSendInteger(irGetRight());
    break;

    //Get back IR sensor
  case 11:
    fmSendInteger(irGetBack());
    break;

    //Get bottom IR sensor
  case 12:
    fmSendInteger(irGetBottom());
    break;

  //Motor Controller Drive Forward
  case 13:
    mcForward(fmReceiveInt());
    break;
  
  //Motor Controller Drive Reverse
  case 14:
    mcReverse(fmReceiveInt());
    break;
  
  //Motor Controller Turn Left
  case 15:
    mcLeft(fmReceiveInt());
    break;
  
  //Motor Controller Turn Right
  case 16:
    mcRight(fmReceiveInt());
    break;
  
  //Motor Controller Stop
  case 17:
    mcStop();
    break;
  
  //Motor Controller Center Steering
  case 18:
    mcCenter();
    break;
  
  //Get the Hall Effect Sensor Count
  case 19:
    fmSendInteger(hallGetCount());
    break;
    
  //Reset Hall Efect Sensor
  case 20:
    hallResetCount();
    break;
    
  default:
    break;
  } 

}



