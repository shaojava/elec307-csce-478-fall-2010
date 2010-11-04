package HostDevice;

import PS3Controller.PS3Controller;
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

			//Instantiate new robot commands object
			CompositeCommands commands = new CompositeCommands(serialInterface);
			
			//Instantiate new PS3 controller object
			PS3Controller controller = new PS3Controller();

			//Wait
			Thread.sleep(1000);
			
			//Do this forever
			while(true) {
				
				//Use the PS3 controller object to send commands to the robot 
				controller.sendRobotCommands(commands);
				
				//Wait
				Thread.sleep(10);
			}
		}


	}
}