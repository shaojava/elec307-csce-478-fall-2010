package RobotCommands;

import org.apache.log4j.Logger;

import HostDevice.ComInterface;

// TODO: Auto-generated Javadoc
/**
 * The Class MobileRobotCommands.
 */
public class Commands {

	/** The serial interface. */
	protected ComInterface serialInterface;
	
	private Logger log;
	
	/**
	 * Instantiates a new mobile robot commands.
	 *
	 * @param serial the serial
	 */
	public Commands(ComInterface serial) {
		serialInterface = serial;
		log = Logger.getLogger(Commands.class);
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
