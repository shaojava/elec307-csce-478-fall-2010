package HostDevice;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class ComInterface.
 */
public class CommInterface {

	private int BAUD_RATE = 115200;

	/** The time to wait for a return. */
	private int RECIEVE_WAIT_DELAY = 30;

	/** The time to wait between sending connection requests. */
	private int CONNECT_REQUEST_WAIT_DELAY = 2000;

	/** The value of a confirmation byte. */
	private byte ASCII_COMFIRM_BYTE = 0x06;

	private byte ASCII_NEW_LINE = 0x0a;

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
			
			//Print a dot and sleep until another connection request is sent
			System.out.print(".");
			Thread.sleep(CONNECT_REQUEST_WAIT_DELAY);

			//Send a SYN command
			byte[] synCommand = new String("SYN\r\n").getBytes("ASCII");
			log.info("Writing SYN command to comm port: " + synCommand);
			outputStream.write(synCommand);

			//If there are bytes available to be read (some response from the device)
			if (inputStream.available() > 0) {

				log.info("Data recieved on input stream.");

				//Initialize the string buffer and received char variable
				StringBuffer recievedData = new StringBuffer();
				byte recievedByte = 0;

				//While there are bytes available in the buffer, read them until
				//a return character in encountered (end of command)
				do {
					recievedByte = (byte) inputStream.read();
					recievedData.append((char)recievedByte);
				} while (recievedByte != ASCII_NEW_LINE);

				//Trim the input data (remove the carriage return and the new line return)
				String receivedString = recievedData.substring(0, recievedData.length()-2);
				log.info("recieved: " + receivedString);

				//Check if the received data was an ACK command from the device
				if (receivedString.equals("ACK")) {

					log.debug("ACK command recieved.  Connection Established.");

					//Print that the connection was established and return true
					System.out.println("\nConnection Established");
					return true;
				}
			}
		}
	}

	/**
	 * Send command.
	 *
	 * @param commandByte the command byte
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean sendCommand(int commandInt) throws Exception{

		byte[] byteArray = new String(Integer.toString(commandInt) + "\r\n").getBytes("ASCII");

		log.debug("Sending ASCII integer byte array: " + byteArray);
		
		//Send the byte command
		outputStream.write(byteArray);

		byte recievedByte = 0;

		do {
			recievedByte = (byte) inputStream.read();
		} while (recievedByte != ASCII_COMFIRM_BYTE);

		//If the byte received was a confirmation
		if ( recievedByte == ASCII_COMFIRM_BYTE ) {

			log.debug("Receive integer " + byteArray + " acknowledged.");

			//Receive the result from the device
			return true;

		}
		else {
			log.error("Byte not recieved for integer ASCII byte array " + byteArray + " was not a confirmation byte");
			log.error("Byte recieved: " + recievedByte);
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
	public boolean sendInteger(int inputInteger) throws Exception{
		
		byte[] byteArray = new String(Integer.toString(inputInteger) + "\r\n").getBytes("ASCII");

		log.debug("Sending ASCII integer byte array: " + byteArray);
		
		//Send the byte command
		outputStream.write(byteArray);

		byte recievedByte = 0;

		do {
			recievedByte = (byte) inputStream.read();
		} while (recievedByte != ASCII_COMFIRM_BYTE);

		//If the byte received was a confirmation
		if ( recievedByte == ASCII_COMFIRM_BYTE ) {

			log.debug("Receive integer " + inputInteger + " acknowledged.");

			//Receive the result from the device
			return true;

		}
		else {
			log.error("Byte not recieved for integer ASCII byte array " + byteArray + " was not a confirmation byte");
			log.error("Byte recieved: " + recievedByte);
			return false;
		}
	}
	
	public boolean sendByte(byte byteToSend) throws Exception{
		
		//Send the byte command
		outputStream.write(byteToSend);

		byte recievedByte = 0;

		do {
			recievedByte = (byte) inputStream.read();
		} while (recievedByte != ASCII_COMFIRM_BYTE);

		//If the byte received was a confirmation
		if ( recievedByte == ASCII_COMFIRM_BYTE ) {

			log.debug("Receive byte " + byteToSend + " acknowledged.");

			//Receive the result from the device
			return true;

		}
		else {
			log.error("Byte not recieved for byte:" + byteToSend + " was not a confirmation byte");
			log.error("Byte recieved: " + recievedByte);
			return false;
		}
	}

	/**
	 * Receive result.
	 *
	 * @return the string
	 * @throws Exception the exception
	 */
	public String receiveInteger() throws Exception{

		log.info("Expecting data from the remote device");

		//Initialize the string buffer and received char variable
		StringBuffer recievedData = new StringBuffer();
		byte recievedByte = 0;

		//While there are bytes available in the buffer, read them until
		//a return character in encountered (end of command)
		do {
			recievedByte = (byte) inputStream.read();
			recievedData.append((char)recievedByte);
		} while (recievedByte != ASCII_NEW_LINE);


		//Trim the input data (remove the carriage return and the new line return)
		String receivedString = recievedData.substring(0, recievedData.length()-2);
		log.info("recieved: " + receivedString);

		log.info("Recived data string: " + receivedString);

		//Send a confirmation byte
		log.info("Sending command confirmation byte");
		outputStream.write(ASCII_COMFIRM_BYTE);

		//Return the result as a string
		return receivedString;
	}

}
