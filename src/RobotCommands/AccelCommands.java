package RobotCommands;

import HostDevice.ComInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class AccelCommands.
 */
public class AccelCommands extends Commands{

	/**
	 * Instantiates a new accel commands.
	 *
	 * @param serial the serial
	 */
	public AccelCommands(ComInterface serial) {
		super(serial);
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

}
