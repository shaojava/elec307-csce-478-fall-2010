//Hall Sensor Pin Definitions
#define pinHallSensor 21

//Define the hall sensor normalization level
#define hallNorm 50

#define LOCeicra 0x69
#define LOCeimsk 0x3D
#define LOCeifr 0x3C
#define CLEAR 0xFF

int partialRotationCount = 0;

//Initialize the Hall effect sensor
void initHallSensor() {
  LCDPrintLine("Initializing", 1, 0);
  LCDPrintLine("Hall Sensors", 2, 0);
  initializePinChangeRegisters();
  
}

int hallGetLeft() {
  return digitalRead(pinHallSensor);
}

void hallResetCount() {
  partialRotationCount = 0;
}

void initializePinChangeRegisters() {
  unsigned char *eicra;
  unsigned char *eimsk;
  unsigned char *eifr;
  eicra = (unsigned char *) LOCeicra;
  eimsk = (unsigned char *) LOCeimsk;
  eifr = (unsigned char *) LOCeifr;
  
  //Clear to initial values
  *eicra &= ~CLEAR;
  *eimsk &= ~CLEAR;
  *eifr &= ~CLEAR;
   
  //Enable pin change interrupt 2 or a change
  *eicra |= 0x01; //set ISC00 to 1
  *eicra &= ~0x02; //set ISC01 to 0
  //Enable interrupt for when PD4 changes
  *eimsk |= 0x01; //set INT0 to 1
  //Set pinHallSensor to an input
  pinMode(pinHallSensor, INPUT);
}

int hallGetCount() {
  noInterrupts(); //disable interrupts so we get an accurate reading
  int copyPartialRotationCount = partialRotationCount; //copy value
  interrupts(); //enable again
  return copyPartialRotationCount; //return the copy, the distance traveled
}

ISR(INT0_vect) { //ISR for pin INT0
  //do with when INT0_vect is tripped, where an interrupt has occured, the pin value has changed
  partialRotationCount++;
}
