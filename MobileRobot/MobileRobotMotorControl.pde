#define mcDriveFwd 4
#define mcDriveBkwd 5
#define mcTurnLeft 3
#define mcTurnRight 2

boolean driveForward;
boolean driveReverse;
boolean turnLeft;
boolean turnRight;

void initMotors() {
  
  analogWrite(mcDriveFwd, 0);
  analogWrite(mcDriveBkwd, 0);
  analogWrite(mcTurnLeft, 0);
  analogWrite(mcTurnRight, 0);
  
  driveForward = false;
  driveReverse = false;
  turnLeft = false;
  turnRight = false;
  
  //Print status message
  LCDPrintLine("Initializing", 1, 0);
  LCDPrintLine("Motor Controls", 2, 0);
  
}

//Drive Forward
void mcForward (int value) {
  if (driveReverse == false) {
    LCDClearScreen();
    LCDPrintLine("Drive Forward", 1, 0);
    driveForward = true;
    analogWrite(mcDriveFwd, value);
  }
  else {
    analogWrite(mcDriveBkwd, 0);
    driveReverse = false;
    analogWrite(mcDriveFwd, value);
  }
}

//Drive Backward
void mcReverse (int value) {
  if (driveForward == false) {
    LCDClearScreen();
    LCDPrintLine("Drive Reverse", 1, 0);
    driveReverse = true;
    analogWrite(mcDriveBkwd, value);
  }
  else {
    analogWrite(mcDriveFwd, 0);
    driveForward = false;
    analogWrite(mcDriveBkwd, value);
  }
}

//Stop Driving
void mcStop() {
  LCDClearScreen();
  LCDPrintLine("Stop Driving", 1, 0);
  analogWrite(mcDriveFwd, 0);
  analogWrite(mcDriveBkwd, 0);
}

//Turn Left
void mcLeft (int value) {
  if (turnRight == false) {
    LCDClearScreen();
    LCDPrintLine("Turn Left", 1, 0);
    turnLeft = true;
    analogWrite(mcTurnLeft, value);
  }
  else {
    analogWrite(mcTurnRight, 0);
    turnRight = false;
    analogWrite(mcTurnLeft, value);
  }
}

//Turn Right
boolean mcRight (int value) {
  if (turnLeft == false) {
    LCDClearScreen();
    LCDPrintLine("Turning Right", 1, 0);
    turnRight = true;
    analogWrite(mcTurnRight, value);
    return true;
  }
  else {
    analogWrite(mcTurnLeft, 0);
    turnRight = false;
    analogWrite(mcTurnRight, value);
  }
}

//Center Steering
void mcCenter() {
  LCDClearScreen();
  LCDPrintLine("Center Steering", 1, 0);
  analogWrite(mcTurnLeft, 0);
  analogWrite(mcTurnRight, 0);
}

