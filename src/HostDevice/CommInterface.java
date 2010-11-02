package HostDevice;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import RobotCommands.Commands;

// TODO: Auto-generated Javadoc
/**
 * The Class ComInterface.
 */
public class CommInterface {
	
	private int BAUD_RATE = 115200;

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
	
	private Logger log;

	/**
	 * Instantiates a new serial communication interface.
	 *
	 * @param portName the port name
	 * @throws Exception the exception
	 */
	public CommInterface(String portName) throws Exception {
		
		//
		log = Logger.getLogger(CommInterface.class);
		
		//Get the communication port identifier
		log.info("Getting port identifier for: " + portName);
		CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier(portName);
		
		//Set up the serial port
		log.info("Opening port");
		serialPort = (SerialPort)portId.open("serial talk", 4000);
		
		//Set up the input stream
		inputStream = serialPort.getInputStream();
		
		//Set up the output stream
		outputStream = serialPort.getOutputStream();
		
		//Set the serial port parameters (these are defaults)
		log.info("Setting port parameters");
		log.info("Baud Rate: " + BAUD_RATE);
		serialPort.setSerialPortParams(BAUD_RATE,
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
			log.info("Writing SYN command to comm port: " + synCommand);
			outputStream.write(synCommand);

			//If there are bytes available to be read (some response from the device)
			if (inputStream.available() > 0) {
				
				log.info("Data recieved on input stream.");

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
				log.info("recieved: " + recievedData);
				
				//Check if the received data was an ACK command from the device
				if (recievedData.toString().equals("ACK")) {

					log.debug("ACK command recieved.  Connection Established.");
					
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

		log.debug("Sending command byte: " + commandByte);
		
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
				
				log.debug("Command " + commandByte + " acknowledged.");
				
				//Receive the result from the device
				return true;
				
			}
			else {
				log.error("Byte not recieved for " + commandByte + " command was not a confirmation byte");
				log.error("Command byte recieved: " + recievedByte[0]);
				return false;
			}
		}
		
		else {
			log.error("There was not data recieved for " + commandByte + " command.");
			return false;
		}
		
	}
	
	/**
	 * Send byte.
	 *
	 * @param dataByte the data byte
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean sendByte(byte dataByte) throws Exception{

		log.debug("Sending data byte: " + dataByte);
		
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
				log.error("Byte not recieved for " + dataByte + " data was not a confirmation byte");
				log.error("Command byte recieved: " + recievedByte[0]);
				return false;
			}
		}
		log.error("There was no confirmation data recieved for " + dataByte + " data.");
		return false;
	}

	/**
	 * Receive result.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String receiveResult() throws Exception{
		
		log.info("Expecting data from the remote device");
		
		//Wait for the data to be sent
		Thread.sleep(RECIEVE_WAIT_DELAY);

		//If there are bytes available to be read (some response from the device)
		if (inputStream.available() > 0) {
			
			log.info("Recived data from the remote device");

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
				
				log.info("Recived data string: " + receivedData.toString());
				
				//Send a confirmation byte
				log.info("Sending command confirmation byte");
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
