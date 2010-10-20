#define INLENGTH 16
#define INTERMINATOR 13
#define COMMAND_RECV_WAIT 25

//Establish a connection with the host machine
void establishConnection() {

  //Print the status information to the user
  LCDPrintLine("Waiting for", 1, 0);
  LCDPrintLine("Host", 2, 0);
  delay(1000);

  //Wait for some response
  while (true) {
    
    //Read any availible bytes from the serial line
    char* buffer = fmEstablishReceiveChars();
      //Check to see if it was a connection request from the host computer (SYN)
    if (strcmp(buffer, "SYN") == 0) {
      //Respond to the connection request
      Serial.print("ACK");
      
      //Flush any additional SYN requests from the buffer
      Serial.flush();
      
      //Inform the user that the connection was established
      LCDPrintLine("Connection", 1, 0);
      LCDPrintLine("Established", 2, 0);
      delay(1000);
      
      //Stop waiting for a connection request.  Break from the loop.
      break;
    }
  }
}

//Read any availible bytes from the FM transmitter
char* fmEstablishReceiveChars() {
  
  //Initialize the count to 0
  int byteCount = 0;
  int availableBytes = Serial.available();
  
  //Initialize a char array to store the serial data
  char serialData[availableBytes+1];

  //While there are bytes in the buffer and the amount of bytes in the 
  //buffer hasn't exceeded the maximum command length
  while (Serial.available()) {
    
    //Read one byte from the buffer and add it to the input
    serialData[byteCount] = Serial.read();
    
    //Increase the amount of bytes read
    byteCount++;
  }
  
  //Null terminate the string so that it can be user with std C++ libraries
  serialData[availableBytes] = 0;
  //Return the read serial data
  return serialData;
} 

byte fmWaitForCommand() {
  byte commandByte = 0x00;
  
  //Wait indefinitely for a command byte
  while(true) {
    
    //If something has been recieved
    if(Serial.available()) {
      
      //Read a single byte
      commandByte = Serial.read();
      
      //Send a confirmation byte
      Serial.write(0x01);
      
      //We're done waiting, break the loop
      break;
    } 
  }
  
  //Return the recieved command
  return commandByte;
}

boolean fmSendValue(int sendData) {
  
  //Send the data over the serial connection
  Serial.print(sendData, DEC);
  
  delay(COMMAND_RECV_WAIT);
    
  //Read any availible bytes from the serial line
  byte byteResponse = Serial.read();
    
  //Check to see if it was a connection request from the host computer (SYN)
  if (byteResponse == 0x01) {
    return true; 
  }
  else  {
    return false;
  }
}

byte fmReceiveByte() {
  
  //Wait
  delay(COMMAND_RECV_WAIT);
  
  byte byteResponse;
    
  //Read any availible bytes from the serial line
  if(Serial.available()) {
    byteResponse = Serial.read();
    Serial.write(0x01);
  }
    
  //Check to see if it was a connection request from the host computer (SYN)
  return byteResponse;
}

char* fmReceiveChars() {
  
  delay(COMMAND_RECV_WAIT);
  
  //Initialize the count to 0
  int byteCount = 0;
  int availableBytes = Serial.available();
  
  //Initialize a char array to store the serial data
  char serialData[availableBytes+1];

  //While there are bytes in the buffer and the amount of bytes in the 
  //buffer hasn't exceeded the maximum command length
  while (Serial.available()) {
    
    //Read one byte from the buffer and add it to the input
    serialData[byteCount] = Serial.read();
    
    //Increase the amount of bytes read
    byteCount++;
  }
  
  //If there was data recieved
  if (sizeof(serialData) > 1) {
    //Send a confirmation byte
      Serial.write(0x01);
  }
  
  //Null terminate the string so that it can be user with std C++ libraries
  serialData[availableBytes] = 0;
  //Return the read serial data
  return serialData;
} 
  




