void setup() {
  delay(500);
  
  //Initialize the LCD Display
  initLCDScreen();
  delay(1000);
  
  //Display the splash screen
  LCDPrintLine("University of", 1, 0);
  LCDPrintLine("Nebraska-Linc", 2, 0);
  delay(5000);
  
  //Initialize the Accelerometer
  initAccelerometer();
  delay(1000);
  
  //Initalize the hall effect sensor
  //initHallSensor();
  //delay(1000);
  
  //Initalize IR sensors
  //initIRSensors();
  //delay(1000);
  
  //Initialize the FM serial port
  LCDPrintLine("Initializing", 1, 0);
  LCDPrintLine("FM Transmitter", 2, 0);
  Serial.begin(4800);
  
  //Clear the Screen
  LCDClearScreen();
  delay(1000);
  
  //Establish connection with the laptop
  establishConnection();

}

void loop() {
  //Serial.print(accelGetX());
  // put your main code here, to run repeatedly: 
  //Serial.print("Ping");
  //delay(1000);
  //LCDClearScreen();
  
  switch(fmWaitForCommand()) {
    
    //Get accelerometer X axis
    case 0x02:
      fmSendValue(accelGetX());
      break;
      
    //Get accelerometer Y axis
    case 0x03:
      fmSendValue(accelGetY());
      break;
    
    //Get accelerometer Z axis
    case 0x04:
      fmSendValue(accelGetZ());
      break;
      
    //Set the accelerometer sensitivity
    case 0x05:
      accelSetSensitivity((int)fmReceiveByte());
      break;
      
    //Sleep the Accelerometer
    case 0x06:
      accelSleep();
      break;
    
    //Wake the Accelerometer
    case 0x07:
      accelWake();
      break;
      
    //Get front IR sensor
    case 0x08:
      fmSendValue(irGetFront());
      break;
      
    //Get left IR sensor
    case 0x09:
      fmSendValue(irGetLeft());
      break;
      
    //Get right IR sensor
    case 0x0a:
      fmSendValue(irGetRight());
      break;
      
    //Get back IR sensor
    case 0x0b:
      fmSendValue(irGetBack());
      break;
      
    //Get bottom IR sensor
    case 0x0c:
      fmSendValue(irGetBottom());
      break;
      
    //LCD Print Line
    case 0x0d:
      char* line = fmReceiveChars();
      LCDPrintLine(line, 1, 0);
      break;
  }
}
  
