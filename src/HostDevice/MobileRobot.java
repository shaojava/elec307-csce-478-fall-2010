package HostDevice;

import MobileRobotCommands.Commands;

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
		ComInterface serialInterface = new ComInterface("/dev/tty.usbserial-A700eEl4");
		
		//If the connection was successful
		if (serialInterface.establishConnection()) {
			
			//Instantiate a MobileRobot commands object
			Commands commandInterface = new Commands(serialInterface);
			
			//Wait
			Thread.sleep(2000);
			
			//Drive Forward
			commandInterface.motorDriveForward(255);
			
			//Wait
			Thread.sleep(1000);
			
			//Stop the motor
			commandInterface.motorStop();
			
			//Get the distance traveled
			System.out.println(commandInterface.getHallCount());
			System.out.println(commandInterface.getHallDistance());
		
			System.exit(0);
		}
	}
}