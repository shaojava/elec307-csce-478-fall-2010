package RobotCommands;

import HostDevice.ComInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class MotorCommands.
 */
public class MotorCommands extends Commands {

	/**
	 * Instantiates a new motor commands.
	 *
	 * @param serial the serial
	 */
	public MotorCommands(ComInterface serial) {
		super(serial);
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
	
	
}
