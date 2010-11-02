package RobotCommands;

import HostDevice.ComInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class MobileRobotCommands.
 */
public class Commands {

	/** The serial interface. */
	protected ComInterface serialInterface;
	
	/**
	 * Instantiates a new mobile robot commands.
	 *
	 * @param serial the serial
	 */
	public Commands(ComInterface serial) {
		serialInterface = serial;
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
	
	/**
	 * Gets the hall distance.
	 *
	 * @return the hall distance
	 * @throws Exception the exception
	 */
	public double getHallDistance() throws Exception {
		
		//Send the command byte
		if (serialInterface.sendCommand((byte) 19)) {
			
			//If the command byte was received, return the value
			double hallCount = Integer.parseInt(serialInterface.receiveResult().trim());
			double hallDistance = (Math.PI * hallCount) / 6;
			
			return hallDistance;
			
		}
		else {
			return -1;
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
	
}
