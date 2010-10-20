package HostDevice;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;

public class ComInterface {
	private int RECIEVE_WAIT_DELAY = 25;
	private int CONNECT_REQUEST_WAIT_DELAY = 2000;
	private byte COMFIRM_COMMAND_BYTE = 1;

	private SerialPort serialPort;
	private InputStream inputStream;
	private OutputStream outputStream;

	public ComInterface(String portName) throws Exception {
		CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
		serialPort = (SerialPort)portId.open("serial talk", 4000);
		inputStream = serialPort.getInputStream();
		outputStream = serialPort.getOutputStream();
		serialPort.setSerialPortParams(4800,
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
	}

	public boolean establishConnection() throws Exception{

		//Print getting connection request
		System.out.print("Sending connection request");

		//Keep trying to contact the device indefinitely
		while(true){

			//Send a SYN command
			byte[] synCommand = new String("SYN").getBytes("ASCII");
			outputStream.write(synCommand);

			//If there are bytes available to be read (some response from the device)
			if (inputStream.available() > 0) {

				//Initialize the string buffer and received char variable
				StringBuffer recievedData = new StringBuffer();
				byte recievedByte = 0;

				//Wait for all the data to come in
				Thread.sleep(RECIEVE_WAIT_DELAY);

				//While there are bytes available in the buffer, read them until
				//a return character in encountered (end of command)
				while (inputStream.available() > 0) {
					recievedByte = (byte) inputStream.read();
					if (recievedByte != 1) {
						recievedData.append((char)recievedByte);
					}
				}
				recievedData.trimToSize();
				//Check if the received data was an ACK command from the device
				if (recievedData.toString().equals("ACK")) {

					//Print that the connection was established and return true
					System.out.println("\nConnection Established");
					return true;
				}
			}

			//Print a dot and sleep until another connection request is sent
			System.out.print(".");
			Thread.sleep(CONNECT_REQUEST_WAIT_DELAY);
		}
	}

	public boolean sendCommand(byte commandByte) throws Exception{

		//Send the byte command
		outputStream.write(commandByte);

		//Wait for a response
		Thread.sleep(RECIEVE_WAIT_DELAY);

		//If there are bytes available to be read (confirm byte from the device)
		if (inputStream.available() > 0) {

			//Read only one byte from the buffer
			byte[] recievedByte = new byte[inputStream.available()];
			inputStream.read(recievedByte, 0, 1);

			//If the byte received was a confirmation
			if ( recievedByte[0] == COMFIRM_COMMAND_BYTE ) {
				
				//Receive the result from the device
				return true;
				
			}
			else {
				System.out.println("A confirmation byte was never recieved.");
				return false;
			}
		}
		System.out.println("There was some general error");
		return false;
	}
	
	public boolean sendByte(byte dataByte) throws Exception{

		//Send the byte command
		outputStream.write(dataByte);

		//Wait for a response
		Thread.sleep(RECIEVE_WAIT_DELAY);

		//If there are bytes available to be read (confirm byte from the device)
		if (inputStream.available() > 0) {

			//Read only one byte from the buffer
			byte[] recievedByte = new byte[inputStream.available()];
			inputStream.read(recievedByte, 0, 1);

			//If the byte received was a confirmation
			if ( recievedByte[0] == COMFIRM_COMMAND_BYTE ) {
				
				//Receive the result from the device
				return true;
				
			}
			else {
				System.out.println("A confirmation byte was never recieved.");
				return false;
			}
		}
		System.out.println("There was some general error");
		return false;
	}

	public String recieveResult() throws Exception{
		//Wait for the data to be sent
		Thread.sleep(RECIEVE_WAIT_DELAY);

		//If there are bytes available to be read (some response from the device)
		if (inputStream.available() > 0) {

			StringBuffer receivedData = new StringBuffer();
			byte receivedByte = 0;

			while (inputStream.available() > 0) {
				receivedByte = (byte) inputStream.read();
				if (receivedByte != 1) {
					receivedData.append((char)receivedByte);
				}
			}

			if (receivedData.length() > 0) {
				//Send a confirmation byte
				outputStream.write(COMFIRM_COMMAND_BYTE);

				return receivedData.toString();
			}
			else {
				System.out.println("There was a 0 lenth response.");
				return null;
			}
		}
		else {
			System.out.println("There was no response data revieced.");
			return null;
		}
	}

}
