package HostDevice;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;

// TODO: Auto-generated Javadoc
/**
 * The Class ComInterface.
 */
public class ComInterface {

	/** The time to wait for a return. */
	private int RECIEVE_WAIT_DELAY = 50;
	
	/** The time to wait between sending connection requests. */
	private int CONNECT_REQUEST_WAIT_DELAY = 2000;
	
	/** The value of a confirmation byte. */
	private byte COMFIRM_COMMAND_BYTE = 1;

	/** The serial port. */
	private SerialPort serialPort;
	
	/** The input stream. */
	private InputStream inputStream;
	
	/** The output stream. */
	private OutputStream outputStream;

	/**
	 * Instantiates a new serial communication interface.
	 *
	 * @param portName the port name
	 * @throws Exception the exception
	 */
	public ComInterface(String portName) throws Exception {
		
		//Get the communication port identifier
		CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
		
		//Set up the serial port
		serialPort = (SerialPort)portId.open("serial talk", 4000);
		
		//Set up the input stream
		inputStream = serialPort.getInputStream();
		
		//Set up the output stream
		outputStream = serialPort.getOutputStream();
		
		//Set the serial port parameters (these are defaults)
		serialPort.setSerialPortParams(9600,
				SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,
				SerialPort.PARITY_NONE);
	}

	/**
	 * Establish connection.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
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
				
				//Trim the input data
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

	/**
	 * Send command.
	 *
	 * @param commandByte the command byte
	 * @return true, if successful
	 * @throws Exception the exception
	 */
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
				return false;
			}
		}
		return false;
	}
	
	/**
	 * Send byte.
	 *
	 * @param dataByte the data byte
	 * @return true, if successful
	 * @throws Exception the exception
	 */
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
				return false;
			}
		}
		return false;
	}

	/**
	 * Receive result.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String receiveResult() throws Exception{
		//Wait for the data to be sent
		Thread.sleep(RECIEVE_WAIT_DELAY);

		//If there are bytes available to be read (some response from the device)
		if (inputStream.available() > 0) {

			//Initialize the string buffer and received char variable
			StringBuffer receivedData = new StringBuffer();
			byte receivedByte = 0;

			//While there are bytes available in the buffer, read them until
			//a return character in encountered (end of command)
			while (inputStream.available() > 0) {
				receivedByte = (byte) inputStream.read();
				if (receivedByte != 1) {
					receivedData.append((char)receivedByte);
				}
			}

			//If there was data received, send a confirmation to the device
			if (receivedData.length() > 0) {
				//Send a confirmation byte
				outputStream.write(COMFIRM_COMMAND_BYTE);

				//Return the result as a string
				return receivedData.toString();
			}
			else {
				return null;
			}
		}
		else {
			return null;
		}
	}

}
