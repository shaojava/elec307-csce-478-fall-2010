package HostDevice;

import org.apache.log4j.Logger;
import org.jfree.ui.RefineryUtilities;

import PS3Controller.PS3Controller;
import RobotCommands.BaseCommands;
import RobotCommands.CompositeCommands;

// TODO: Auto-generated Javadoc
/**
 * The Class MobileRobot.
 */
public class MobileRobot {

	private static Logger log;

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception{

		log = Logger.getLogger(PS3Controller.class);
		log.info("Host Device Program Started");
		
        final RobotPlotter posPlot = new RobotPlotter("Dead Rekoning Plot");
        posPlot.pack();
        RefineryUtilities.centerFrameOnScreen(posPlot);

		//Establish IO port with machine communications port using the machine name for the port
		log.info("Starting the serial interface input and output stream.");
		CommInterface serialInterface = new CommInterface("/dev/tty.usbserial-A700eEl4");

		//If the connection was successful
		log.info("Attempting to establish connection with the mobile device");
		if (serialInterface.establishConnection()) {

			//Instantiate new robot commands object
			log.info("Instantiating a composite commands interface object");
			CompositeCommands commands = new CompositeCommands(serialInterface);

			//Try to Instantiate a PS3 controller object
			//Will fail if the controller is not connected
			try {
				
				//Instantiate new PS3 controller object
				log.info("Instantiating a PS3 Controller object");
				PS3Controller controller = new PS3Controller();
				
				controller.attachPlot(posPlot);

				//Wait
				Thread.sleep(1000);

				//Start manual control with 10ms delay times
				//This will loop until the start button is pressed on the controller
				controller.manualControl(commands, 10);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error(e.getStackTrace().toString());
			}
		}
	}
}