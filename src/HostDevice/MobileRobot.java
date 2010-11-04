package HostDevice;

import RobotCommands.BaseCommands;
import RobotCommands.CompositeCommands;

// TODO: Auto-generated Javadoc
/**
 * The Class MobileRobot.
 */
public class MobileRobot {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception{
		
		//Establish IO port with machine communications port using the machine name for the port
		CommInterface serialInterface = new CommInterface("/dev/tty.usbserial-A700eEl4");
		
		//If the connection was successful
		if (serialInterface.establishConnection()) {
			
			//Instantiate a MobileRobot commands object
			CompositeCommands commands = new CompositeCommands(serialInterface);
			
			commands.accelerate(50, 255, 10000);
			commands.deaccelerate(255, 50, 10000);
			commands.motorStop();
			
			//System.exit(0);
		}
	}
}