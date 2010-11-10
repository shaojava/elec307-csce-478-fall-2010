/*
 * 
 */
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

	/** The BAU d_ rate. */
	private int BAUD_RATE = 115200;

	/** The time to wait for a return. */
	private int RECIEVE_WAIT_DELAY = 30;

	/** The time to wait between sending connection requests. */
	private int CONNECT_REQUEST_WAIT_DELAY = 2000;

	/** The value of a confirmation byte. */
	private byte ASCII_COMFIRM_BYTE = 0x06;

	/** The ASCI i_ ne w_ line. */
	private byte ASCII_NEW_LINE = 0x0a;

	/** The serial port. */
	private SerialPort serialPort;

	/** The input stream. */
	private InputStream inputStream;

	/** The output stream. */
	private OutputStream outputStream;

	/** The log. */
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

			log.info("Writing SYN command to comm port: " + byteArrayToHexString(synCommand));
			outputStream.write(synCommand);
			outputStream.flush();

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
				log.info("Data recieved: " + receivedString);

				//Check if the received data was an ACK command from the device
				if (receivedString.equals("ACK")) {

					log.info("ACK command recieved.  Connection Established.");

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
	 * @param commandInt the command int
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean sendCommand(int commandInt) throws Exception{

		byte[] byteArray = new String(Integer.toString(commandInt) + "\r\n").getBytes("ASCII");

		log.info("Send Command: " + commandInt);

		//Send the byte command
		log.info("Sending byte array for command " + commandInt + ": " + byteArrayToHexString(byteArray));
		outputStream.write(byteArray);
		outputStream.flush();

		byte recievedByte = 0;

		do {
			recievedByte = (byte) inputStream.read();
			log.info("Read byte 0x" + Integer.toHexString(recievedByte) + " from input stream");
		} while (recievedByte != ASCII_COMFIRM_BYTE);

		//If the byte received was a confirmation
		if ( recievedByte == ASCII_COMFIRM_BYTE ) {

			log.info("Send Command " + commandInt + ": Received acknoledge byte from device");

			//Receive the result from the device
			return true;

		}
		else {
			log.error("Send Command " + commandInt + ":Byte not recieved for integer ASCII byte array " + byteArray + " was not a confirmation byte");
			log.error("Send Command " + commandInt + ":Byte recieved: " + recievedByte);
			return false;
		}
	}

	/**
	 * Send byte.
	 *
	 * @param inputInteger the input integer
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean sendInteger(int inputInteger) throws Exception{

		byte[] byteArray = new String(Integer.toString(inputInteger) + "\r\n").getBytes("ASCII");

		log.info("Send Integer " + inputInteger);

		//Send the byte command
		log.info("Sending byte array for integer " + inputInteger + ": " + byteArrayToHexString(byteArray));
		outputStream.write(byteArray);
		outputStream.flush();

		byte recievedByte = 0;

		do {
			recievedByte = (byte) inputStream.read();
		} while (recievedByte != ASCII_COMFIRM_BYTE);

		//If the byte received was a confirmation
		if ( recievedByte == ASCII_COMFIRM_BYTE ) {

			log.debug("Send Integer " + inputInteger + ": Received acknowledge byte from device.");

			//Receive the result from the device
			return true;

		}
		else {
			log.error("Send Integer " + inputInteger + ":Byte not recieved for integer ASCII byte array " + byteArray + " was not a confirmation byte");
			log.error("Send Integer " + inputInteger + ":Byte recieved: " + recievedByte);
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

		log.info("Recieve Integer: Expecting to recieve integer from the remote device");

		//Initialize the string buffer and received char variable
		StringBuffer recievedData = new StringBuffer();
		StringBuffer logData = new StringBuffer();
		byte recievedByte = 0;

		//While there are bytes available in the buffer, read them until
		//a return character in encountered (end of command)
		logData.append("[ ");
		do {
			recievedByte = (byte) inputStream.read();
			logData.append("0x" + Integer.toHexString(recievedByte) + " ");
			recievedData.append((char)recievedByte);
		} while (recievedByte != ASCII_NEW_LINE);

		logData.append("]");
		
		log.info("Recieve Integer: Recieved " + logData.toString() + " from input stream");

		//Trim the input data (remove the carriage return and the new line return)
		String receivedString = recievedData.substring(0, recievedData.length()-2);
		log.info("Recieve Integer: Converted byte array to string: " + receivedString);

		//Send a confirmation byte
		log.info("Recieve Integer: Sending command confirmation byte.");
		outputStream.write(ASCII_COMFIRM_BYTE);
		outputStream.flush();

		//Return the result as a string
		return receivedString;
	}

	/**
	 * This method converts a byte array to hex string.
	 *
	 * @param b the b
	 * @return the string
	 */
	public static String byteArrayToHexString(byte[] b) {
		
		//Initialize the string buffer
		StringBuffer sb = new StringBuffer(b.length * 2);
		
		//Start constructing the byte array
		sb.append("[ ");
		
		//For all the bytes in the array
		for (int i = 0; i < b.length; i++) {
			
			//Convert the byte to an integer
			int v = b[i] & 0xff;
			
			//Left shift
			if (v < 16) {
				sb.append('0');
			}
			
			//Add the hex string representation of the byte 
			sb.append("0x" + Integer.toHexString(v).toUpperCase() + " ");
		}
		
		//Close the byte array string
		sb.append("]");
		
		//Convert the string buffer to a string a return it
		return sb.toString();
	}

}
