package RobotCommands;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import HostDevice.CommInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class MobileRobotCommands.
 */
public class BaseCommands {

	/** The serial interface. */
	protected CommInterface serialInterface;
	
	/** The log. */
	private Logger log;
	
	/**
	 * Instantiates a new mobile robot commands.
	 *
	 * @param serial the serial
	 */
	public BaseCommands(CommInterface serial) {
		serialInterface = serial;
		log = Logger.getLogger(BaseCommands.class);
	}
	
	/**
	 * Gets the accelerometer x.
	 *
	 * @return the accelerometer x
	 * @throws Exception the exception
	 */
	public int getAccelerometerX() throws Exception {
		
		//Send the command byte
		log.info("Sending get accelerometer X command");
		if (serialInterface.sendCommand(2)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveInteger().trim());
		}
		else {
			log.error("Error getting accelerometer X value");
			return -1;
		}
	}
	
	/**
	 * Gets the accelerometer y.
	 *
	 * @return the accelerometer y
	 * @throws Exception the exception
	 */
	public int getAccelerometerY() throws Exception {
		
		//Send the command byte
		log.info("Sending get accelerometer Y command");
		if (serialInterface.sendCommand(3)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveInteger().trim());
		}
		else {
			log.error("Error getting accelerometer y value");
			return -1;
		}
	}
	
	/**
	 * Gets the accelerometer z.
	 *
	 * @return the accelerometer z
	 * @throws Exception the exception
	 */
	public int getAccelerometerZ() throws Exception {
		
		//Send the command byte
		log.info("Sending get accelerometer Z command");
		if (serialInterface.sendCommand(4)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveInteger().trim());
		}
		else {
			log.error("Error getting accelerometer Z value");
			return -1;
		}
	}
	
	/**
	 * Sets the accelerometer sensitivity.
	 *
	 * @param sensitivity the sensitivity
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean setAccelerometerSensitivity(int sensitivity) throws Exception {
		
		//If the sensitivity value is not in the correct range, return false
		if (sensitivity < 0 || sensitivity > 3) {
			log.error("Sensitivity is not a valid range from 0 to 3");
			return false;
		}
		
		//Send the command byte
		log.info("Sending set accelerometer sensitivity command");
		if (serialInterface.sendCommand(5)) {
			
			//If the command byte was received, send the sensitivity value
			//Return true if the command was executed successfully
			return serialInterface.sendInteger(sensitivity);
		}
		else {
			log.error("Error setting accelerometer sensitivity");
			return false;
		}
	}
	
	/**
	 * Sets the accelerometer sleep.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean setAccelerometerSleep() throws Exception {
		
		//Send the command byte
		log.info("Sending accelerometer sleep command");
		if (serialInterface.sendCommand(6)) {
			
			//If the command byte was received, return true
			return true;
		}
		else {
			log.error("Error setting accelerometer to sleep");
			return false;
		}
	}
	
	/**
	 * Sets the accelerometer wake.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean setAccelerometerWake() throws Exception {
		
		//Send the command byte
		log.info("Sending accelerometer wake command");
		if (serialInterface.sendCommand(7)) {
			
			//If the command byte was received, return true
			return true;
		}
		else {
			log.error("Error setting accelerometer to wake up");
			return false;
		}
	}
	
	/**
	 * Gets the iR front.
	 *
	 * @return the iR front
	 * @throws Exception the exception
	 */
	public int getIRFront() throws Exception {
		
		//Send the command byte
		log.info("Sending get front IR analog value command");
		if (serialInterface.sendCommand(8)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveInteger().trim());
		}
		else {
			log.error("Error getting the front IR analog value");
			return -1;
		}
	}
	
	/**
	 * Gets the iR left.
	 *
	 * @return the iR left
	 * @throws Exception the exception
	 */
	public int getIRLeft() throws Exception {
		
		//Send the command byte
		log.info("Sending get left IR analog value command");
		if (serialInterface.sendCommand(9)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveInteger().trim());
		}
		else {
			log.error("Error getting the left IR analog value");
			return -1;
		}
	}
	
	/**
	 * Gets the iR right.
	 *
	 * @return the iR right
	 * @throws Exception the exception
	 */
	public int getIRRight() throws Exception {
		
		//Send the command byte
		log.info("Sending get right IR analog value command");
		if (serialInterface.sendCommand(10)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveInteger().trim());
		}
		else {
			log.error("Error getting the right IR analog value");
			return -1;
		}
	}
	
	/**
	 * Gets the iR back.
	 *
	 * @return the iR back
	 * @throws Exception the exception
	 */
	public int getIRBack() throws Exception {
		
		//Send the command byte
		log.info("Sending get back IR analog value command");
		if (serialInterface.sendCommand((byte) 11)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveInteger().trim());
		}
		else {
			log.error("Error getting the back IR analog value");
			return -1;
		}
	}
	
	/**
	 * Gets the iR bottom.
	 *
	 * @return the iR bottom
	 * @throws Exception the exception
	 */
	public int getIRBottom() throws Exception {
		
		//Send the command byte
		log.info("Sending get bottom IR analog value command");
		if (serialInterface.sendCommand(12)) {
			
			//If the command byte was received, send the power level
			//If the power level was received, return true
			return Integer.parseInt(serialInterface.receiveInteger().trim());
		}
		else {
			log.error("Error getting the bottom IR analog value");
			return -1;
		}
	}
	
	/**
	 * Motor drive forward.
	 *
	 * @param power the power
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean motorDriveForward(int power) throws Exception {
		
		//If the supplied power is not within the accepted range, return false
		if  (power < 0 || power > 255) {
			log.error("The power value for the drive forward command is not in valid range");
			return false;
		}
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		log.info("Sending drive forward command");
		if (serialInterface.sendCommand(13)) {
			return serialInterface.sendInteger(power);
		}
		else {
			log.error("Error sending the drive forward command");
			return false;
		}
	}
	
	/**
	 * Motor drive reverse.
	 *
	 * @param power the power
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean motorDriveReverse(int power) throws Exception {
		
		//If the supplied power is not within the accepted range, return false
		if  (power < 0 || power > 255) {
			log.error("The power value for the drive reverse command is not in valid range");
			return false;
		}
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		log.info("Sending drive reverse command");
		if (serialInterface.sendCommand(14)) {
			return serialInterface.sendInteger(power);
		}
		else {
			log.error("Error sending the drive reverse command");
			return false;
		}
	}
	
	/**
	 * Motor turn left.
	 *
	 * @param power the power
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean motorTurnLeft(int power) throws Exception {
		
		//If the supplied power is not within the accepted range, return false
		if  (power < 0 || power > 255) {
			log.error("The power value for the turn left command is not in valid range");
			return false;
		}
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		log.info("Sending turn left command");
		if (serialInterface.sendCommand(15)) {
			return serialInterface.sendInteger(power);
		}
		else {
			log.error("Error sending the turn left command");
			return false;
		}
	}
	
	/**
	 * Motor turn right.
	 *
	 * @param power the power
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean motorTurnRight(int power) throws Exception {
		
		//If the supplied power is not within the accepted range, return false
		if  (power < 0 || power > 255) {
			log.error("The power value for the turn right command is not in valid range");
			return false;
		}
		
		//Send the command byte
		log.info("Sending turn right command");
		if (serialInterface.sendCommand(16)) {
			
			//If the command byte was received, send the power level
			//If the power level was received, return true
			return serialInterface.sendInteger(power);
		}
		else {
			log.error("Error sending the turn right command");
			return false;
		}
	}
	
	/**
	 * Motor stop.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean motorStop() throws Exception {
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		log.info("Sending drive stop command");
		if (serialInterface.sendCommand(17)) {
			return true;
		}
		else {
			log.error("Error sending the motor stop command");
			return false;
		}
	}
	
	/**
	 * Motor center.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean motorCenter() throws Exception {
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		log.info("Sending steering center command");
		if (serialInterface.sendCommand(18)) {
			return true;
		}
		else {
			log.error("Error sending the steering center command");
			return false;
		}
	}
	
	/**
	 * Menu up.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean menuUp() throws Exception {
		
		log.info("Sending menu up command");
		if (serialInterface.sendCommand(21)) {
			return true;
		}
		else {
			log.error("Error sending menu up command");
			return false;
		}
	}
	
	/**
	 * Menu down.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean menuDown() throws Exception {
		
		log.info("Sending menu down command");
		if (serialInterface.sendCommand(22)) {
			return true;
		}
		else {
			log.error("Error sending menu down command");
			return false;
		}
	}
	
	/**
	 * Menu left.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean menuLeft() throws Exception {
		
		log.info("Sending menu left command");
		if (serialInterface.sendCommand(23)) {
			return true;
		}
		else {
			log.error("Error sending menu left command");
			return false;
		}
	}
	
	/**
	 * Menu right.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean menuRight() throws Exception {
		
		log.info("Sending menu right command");
		if (serialInterface.sendCommand(24)) {
			return true;
		}
		else {
			log.error("Error sending menu right command");
			return false;
		}
	}
	
	/**
	 * Menu select.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean menuSelect() throws Exception {
		
		log.info("Sending menu select command");
		if (serialInterface.sendCommand(25)) {
			return true;
		}
		else {
			log.error("Error sending menu select command");
			return false;
		}
	}
	
	/**
	 * Menu back.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean menuBack() throws Exception {
		
		log.info("Sending menu back command");
		if (serialInterface.sendCommand(26)) {
			return true;
		}
		else {
			log.error("Error sending menu back command");
			return false;
		}
	}
	
	/**
	 * Gets the hall count.
	 *
	 * @return the hall count
	 * @throws Exception the exception
	 */
	public int getHallCount() throws Exception {

		//Send the command byte
		log.info("Sending get hall effect sensor count command");
		if (serialInterface.sendCommand(19)) {

			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveInteger().trim());
		}
		else {
			return -1;
		}
	}
	
}
