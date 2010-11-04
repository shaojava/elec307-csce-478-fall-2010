//IR Sensor Pin Definitions
#define irFront 11
#define irLeft 12
#define irRight 13
#define irBack 14
#define irBottom 15

//Define the accelerometer normalization level
#define irNorm 50

//Initialize the IR sensors (make sure that there is some value on
//all of the IR ports (they are plugged in and functioning
void initIRSensors() {
  
  //Print status message
  LCDPrintLine("Initializing", 1, 0);
  LCDPrintLine("IR Sensors", 2, 0);
}

//Getter method for the front IR sensor
int irGetFront() {
  
  //Average the value over 50 iterations (5 milliseconds)
  double average = 0;
  for(int i = 0; i < irNorm; i++) {
    average += analogRead(irFront);
  }
  average /= irNorm;
  return round(average);
}

//Getter method for the left IR sensor
int irGetLeft() {
  
  //Average the value over 50 iterations (5 milliseconds)
  double average = 0;
  for(int i = 0; i < irNorm; i++) {
    average += analogRead(irLeft);
  }
  average /= irNorm;
  return round(average);
}

//Getter method for the right IR sensor
int irGetRight() {
  
  //Average the value over 50 iterations (5 milliseconds)
  double average = 0;
  for(int i = 0; i < irNorm; i++) {
    average += analogRead(irRight);
  }
  average /= irNorm;
  return round(average);
}

//Getter method for the back IR sensor
int irGetBack() {
  
  //Average the value over 50 iterations (5 milliseconds)
  double average = 0;
  for(int i = 0; i < irNorm; i++) {
    average += analogRead(irBack);
  }
  average /= irNorm;
  return round(average);
}

//Getter method for the right IR sensor
int irGetBottom() {
  
  //Average the value over 50 iterations (5 milliseconds)
  double average = 0;
  for(int i = 0; i < irNorm; i++) {
    average += analogRead(irBottom);
  }
  average /= irNorm;
  return round(average);
}

//Print the raw IR value to the LCD
void irPrint(int sensor) {
  
  //Switch on the sensor value
  switch(sensor) {
    
  //The front IR sensor
  case 0:
    LCDPrintLine("Front IR",1, 0);
    LCDPrintLine(irGetFront(), 2, 0);
    break;
    
  //The left IR sensor
  case 1:
    LCDPrintLine("Left IR",1, 0);
    LCDPrintLine(irGetLeft(), 2, 0);
    break;
    
  //The right IR sensor
  case 2:
    LCDPrintLine("Right IR",1, 0);
    LCDPrintLine(irGetRight(), 2, 0);
    break;
    
  //The back IR sensor
  case 3:
    LCDPrintLine("Back IR",1, 0);
    LCDPrintLine(irGetBack(), 2, 0);
    break;
    
  //The bottom IR sensor
  case 4:
    LCDPrintLine("Bottom IR",1, 0);
    LCDPrintLine(irGetBottom(), 2, 0);
    break;
  }
}
  
