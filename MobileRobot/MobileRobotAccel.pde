//Accelerometer Pin Definitions
#define accelGS1 22
#define accelGS2 24
#define accelSlp 31
#define accelX 0
#define accelY 1
#define accelZ 2

//Define the accelerometer normalization level
#define accelNorm 50

//Define global vairables
int accelCurrentState = 0; 
int accelAccuracyLvl = 0;

//Initialize the Accelerometer
void initAccelerometer() {
  
  //Turn on the accelerometer
  accelWake();
  
  //Set the accuracy level
  accelSetSensitivity(0);
  
  //Print status message
  LCDPrintLine("Initializing", 1, 0);
  LCDPrintLine("Accelerometer", 2, 0);
}

//Sleep the Accelerometer
void accelSleep() {
  //If the accelerometer is awake
  if (accelCurrentState == 1) {
    
    //Sleep up the accelerometer
    digitalWrite(accelSlp, LOW);
    accelCurrentState = 0;
  }
}

//Wake the Accelerometer
void accelWake() {
  
  //If the accelerometer is sleeping
  if (accelCurrentState == 0) {
    
    //Wake up the accelerometer
    digitalWrite(accelSlp, HIGH);
    accelCurrentState = 1;
  }
}

//Set the sensitivity level of the accelerometer
void accelSetSensitivity(int level) {
  //Switch on the levels
  switch (level) {
    
    //G-Range 1.5g (800mV/G)
    case 0:
      digitalWrite(accelGS1, LOW);
      digitalWrite(accelGS2, LOW);
    
    //G-Range 2g (600mV/G)
    case 1:
      digitalWrite(accelGS1, HIGH);
      digitalWrite(accelGS2, LOW);
    
    //G-Range 4g (300mV/G)
    case 2:
      digitalWrite(accelGS1, LOW);
      digitalWrite(accelGS2, HIGH);
    
    //G-Range 6g (200mV/G)
    case 3:
      digitalWrite(accelGS1, HIGH);
      digitalWrite(accelGS2, HIGH);
    
    //Default to high sensitivity G-Range 1.5g (800mV/G)
    default:
      digitalWrite(accelGS1, LOW);
      digitalWrite(accelGS2, LOW);
  }
}

//Getter method for the X axis value
int accelGetX() {
  
  //Average the value over 50 iterations (5 milliseconds)
  double average = 0;
  for(int i = 0; i < accelNorm; i++) {
    average += analogRead(accelX);
  }
  average /= accelNorm;
  return round(average);
}

//Getter method for the Y axis value
int accelGetY() {
  
  //Average the value over 50 iterations (5 milliseconds)
  double average = 0;
  for(int i = 0; i < accelNorm; i++) {
    average += analogRead(accelY);
  }
  average /= accelNorm;
  return round(average);
}

//Getter method for the Z axis value
int accelGetZ() {
  
  //Average the value over 50 iterations (5 milliseconds)
  double average = 0;
  for(int i = 0; i < accelNorm; i++) {
    average += analogRead(accelZ);
  }
  average /= accelNorm;
  return round(average);
}

//Getter method for the current state
int accelGetState() {
   return accelCurrentState;
}

//Getter method for the current sensitivity
int accelGetSensitivity() {
   return accelAccuracyLvl;
}
