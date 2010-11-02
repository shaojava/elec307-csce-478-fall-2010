package RobotCommands;

import org.apache.log4j.Logger;
import HostDevice.CommInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class MobileRobotCommands.
 */
public class BaseCommands {

	/** The serial interface. */
	protected CommInterface serialInterface;
	
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
		if (serialInterface.sendCommand((byte) 2)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
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
		if (serialInterface.sendCommand((byte) 3)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
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
		if (serialInterface.sendCommand((byte) 4)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
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
			return false;
		}
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 5)) {
			
			//If the command byte was received, send the sensitivity value
			//Return true if the command was executed successfully
			return serialInterface.sendByte((byte) sensitivity);
		}
		else {
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
		if (serialInterface.sendCommand((byte) 6)) {
			
			//If the command byte was received, return true
			return true;
		}
		else {
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
		if (serialInterface.sendCommand((byte) 7)) {
			
			//If the command byte was received, return true
			return true;
		}
		else {
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
		if (serialInterface.sendCommand((byte) 8)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
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
		if (serialInterface.sendCommand((byte) 9)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
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
		if (serialInterface.sendCommand((byte) 10)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
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
		if (serialInterface.sendCommand((byte) 11)) {
			
			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
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
		if (serialInterface.sendCommand((byte) 12)) {
			
			//If the command byte was received, send the power level
			//If the power level was received, return true
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
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
			return false;
		}
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		if (serialInterface.sendCommand((byte) 13)) {
			return serialInterface.sendByte(new Integer(power).byteValue());
		}
		else {
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
			return false;
		}
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		if (serialInterface.sendCommand((byte) 14)) {
			return serialInterface.sendByte((byte) power);
		}
		else {
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
			return false;
		}
		
		//If the command byte was received, send the power level
		//If the power level was received, return true
		if (serialInterface.sendCommand((byte) 15)) {
			return serialInterface.sendByte((byte) power);
		}
		else {
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
			return false;
		}
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 16)) {
			
			//If the command byte was received, send the power level
			//If the power level was received, return true
			return serialInterface.sendByte((byte) power);
		}
		else {
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
		if (serialInterface.sendCommand((byte) 17)) {
			return true;
		}
		else {
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
		if (serialInterface.sendCommand((byte) 18)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Sleep robot.
	 *
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	public boolean sleepRobot() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 255)) {
			
			return true;
			
		}
		else {
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
		if (serialInterface.sendCommand((byte) 19)) {

			//If the command byte was received, return the value
			return Integer.parseInt(serialInterface.receiveResult().trim());
		}
		else {
			return -1;
		}
	}
	
}
