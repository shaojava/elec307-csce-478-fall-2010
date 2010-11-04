#define INLENGTH 16
#define INTERMINATOR 13
#define COMMAND_RECV_WAIT 30
#define ASCII_CONFIRM_BYTE 0x06

//Initialize the Accelerometer
void initFMTransmitter() {

  //Print status message
  LCDPrintLine("Initializing", 1, 0);
  LCDPrintLine("FM Transmitter", 2, 0);
}

//Establish a connection with the host machine
void establishConnection() {

  //Print the status information to the user
  LCDPrintLine("Waiting for", 1, 0);
  LCDPrintLine("Host", 2, 0);
  delay(1000);

  //Wait for some response
  while (true) {

    //Check to see if it was a connection request from the host computer (SYN)
    if (fmReceiveString().equals("SYN")) {
      //Respond to the connection request
      Serial1.println("ACK");

      //Inform the user that the connection was established
      LCDPrintLine("Connection", 1, 0);
      LCDPrintLine("Established", 2, 0);

      //Stop waiting for a connection request.  Break from the loop.
      break;
    }
  }
}

//Read any availible bytes from the FM transmitter
String fmReceiveString() {

  while(!Serial1.available()) {
    //Wait
  }

  String receivedString;
  byte receivedByte = -1;

  do {
    if(Serial1.available()) {
      receivedByte = Serial1.read();
  
      //Read one byte from the buffer and add it to the input
      receivedString += (char) receivedByte;
    }

  } 
  while (receivedByte != 0x0a);

  //Return the read serial data
  return receivedString.trim();
} 

int fmWaitForCommand() {
  while(!Serial1.available()) {
    //Wait
  }

  String receivedString;
  byte receivedByte = 0;

  do {
    
    if(Serial1.available()) {
      receivedByte = Serial1.read();
  
      //Read one byte from the buffer and add it to the input
      receivedString += (char) receivedByte;
    }

  } while (receivedByte != 0x0a);

  Serial1.write(ASCII_CONFIRM_BYTE);

  //Return the read serial data
  receivedString.trim();

  char temp[20];

  receivedString.toCharArray(temp, 19);
  int convertedInt = atoi(temp);
  if (convertedInt == 0 && receivedString != "0")
  {
    convertedInt = -1;
  }

  return convertedInt;
}

boolean fmSendInteger(int sendData) {
  
  //Send the data over the serial connection
  Serial1.println(sendData, DEC);

  //Read one byte from the serial line
  byte byteResponse = 0;
  
  do {
    byteResponse = Serial1.read();
  } 
  while (byteResponse != ASCII_CONFIRM_BYTE);

  //Check to see if it was a connection request from the host computer (SYN)
  if (byteResponse == ASCII_CONFIRM_BYTE) {
    return true; 
  }
  else  {
    return false;
  }
}

byte fmReceiveByte() {

  byte byteResponse;

  while(!Serial1.available()) {
    //Do nothing
  }

  byteResponse = Serial.read();
  Serial1.write(ASCII_CONFIRM_BYTE);

  //Check to see if it was a connection request from the host computer (SYN)
  return byteResponse;
}

int fmReceiveInt() {

  while(!Serial1.available()) {
    //Wait
  }

  String receivedString;
  byte receivedByte = 0;

  do {
    if(Serial1.available()) {
      receivedByte = Serial1.read();
  
      //Read one byte from the buffer and add it to the input
      receivedString += (char) receivedByte;
    }

  } 
  while (receivedByte != 0x0a);

  Serial1.write(ASCII_CONFIRM_BYTE);

  //Return the read serial data
  receivedString.trim();

  char temp[20];

  receivedString.toCharArray(temp, 19);
  int convertedInt = atoi(temp);
  if (convertedInt == 0 && receivedString != "0")
  {
    convertedInt = -1;
  }

  return convertedInt;

}





