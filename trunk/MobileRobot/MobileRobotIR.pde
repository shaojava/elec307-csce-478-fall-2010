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
  //assert(analogRead(irFront) > 0);
  //assert(analogRead(irLeft) > 0);
  //assert(analogRead(irRight) > 0);
  //assert(analogRead(irBack) > 0);
  //assert(analogRead(irBottom) > 0);
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
