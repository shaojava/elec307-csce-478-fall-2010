int HMC6352Address = 0x42 >> 1;

//Initialize the Compass
void initCompass() {
  
  if (compassGetHeading() != -1) {
    //Compass responded correctly
  }
  else {
    LCDPrintLine("Compass", 1, 0);
    LCDPrintLine("Failure", 2, 0);
    
    while(true) {
      
    }
  }
  
  //Print status message
  LCDPrintLine("Initializing", 1, 0);
  LCDPrintLine("Compass", 2, 0);
}

int compassGetHeading() {
  
  //The returned compass heading
  int heading;
  
  //Start transmission
  Wire.beginTransmission(HMC6352Address);
  
  //Send a request to get the compass heading
  Wire.send('A');
  
  //End the transmission
  Wire.endTransmission();
 
  //Give the compass adiquate time to respond
  delay(10);
  
  //Request a response from the device, 2-bytes in length
  Wire.requestFrom(HMC6352Address, 2);
  
  //If there are 2 byte's or less availiable to be read
  if(2 <= Wire.available()){
    
    //Get the first byte
    heading = Wire.receive();
    
    //Shift heading by 8 since the first bit is the most significant
    heading = heading << 8;
    
    //Get the second byte value and add it to the current heading
    heading += Wire.receive();
    
    //Return the compass heading
    return heading;
  }
  
  //We didn't get the response we expected...
  else {
    
    //Return an invalid value
    return -1;
  }
}

//Put the compass to sleep
void compassSleep() {
  
  //Start transmission
  Wire.beginTransmission(HMC6352Address);
  
  //Send the Sleep command to the device
  Wire.send('S');
  
  //End the transmission
  Wire.endTransmission();
  
}

//Wake up the compass
void compassWake() {
  
  //Start transmission
  Wire.beginTransmission(HMC6352Address);
  
  //Send the Sleep command to the device
  Wire.send('W');
  
  //End the transmission
  Wire.endTransmission();
  
}

