package RobotCommands;

import org.apache.log4j.Logger;

import HostDevice.ComInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class HallCommands.
 */
public class HallCommands extends Commands {

	private Logger log;
	
	/**
	 * Instantiates a new hall commands.
	 *
	 * @param serial the serial
	 */
	public HallCommands(ComInterface serial) {
		super(serial);
		log = Logger.getLogger(HallCommands.class);
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

}
