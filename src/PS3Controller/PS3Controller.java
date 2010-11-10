package PS3Controller;

import org.apache.log4j.Logger;

import HostDevice.CommInterface;
import RobotCommands.CompositeCommands;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;


// TODO: Auto-generated Javadoc
/**
 * The Class PS3Controller.
 */
public class PS3Controller {
	
	/** The log4j looger object */
	private Logger log;

	/** The PS3 controller. */
	private Controller PS3Controller;

	/** The PS3 components. */
	private Component[] PS3Components;

	/** The left y axis component. */
	private int LEFT_Y_AXIS = 1;

	/** The right x axis component. */
	private int RIGHT_X_AXIS = 2;

	/** The Triangle button. */
	private int TRIANGLE_BUTTON = 12;

	/** The X button. */
	private int X_BUTTON = 14;

	/** The DPAD Up button. */
	private int DPAD_UP_BUTTON = 4;

	/** The DPAD Down button. */
	private int DPAD_DOWN_BUTTON = 6;

	/** The DPAD Left button. */
	private int DPAD_LEFT_BUTTON = 7;

	/** The DPAD Right button. */
	private int DPAD_RIGHT_BUTTON = 5;

	/** The previous drive power. */
	private int previousDrivePower = 0;

	/** The previous steer power. */
	private int previousSteerPower = 0;

	/**
	 * Instantiates a new PS3 controller.
	 */
	public PS3Controller() {
		
		log = Logger.getLogger(PS3Controller.class);
		log.info("PS3 Controller Class Instanciated");

		//Get the list of the connected controllers
		Controller [] controllerList = ControllerEnvironment.getDefaultEnvironment().getControllers();

		boolean controllerFound = false;
		
		//For all the connected controllers
		for(int i =0;i<controllerList.length;i++){

			//If the controller in the list is the PS3 controller, save it
			if(controllerList[i].getName().equals("PLAYSTATION(R)3 Controller")) {
				log.info("Found PS3 controller on channel " + i);
				PS3Controller = controllerList[i];
				controllerFound = true;
			}

		}
		
		//If the playstation controller wasn't found in the list of connected devices.
		if ( controllerFound == false ) {
			log.error("No PS3 Controller found.");
			System.out.println("No PS3 Controller Found.");
		}

		//Save the list of components for the PS3 Controller
		try {
			PS3Components = PS3Controller.getComponents();
		}
		catch(Exception e) {
			System.out.print(e);
		}
	}

	/**
	 * Poll controller.
	 */
	private void pollController() {

		//Poll the controller for data
		PS3Controller.poll();
	}

	/**
	 * Gets the left y axis.
	 *
	 * @return the left y axis from -255 to 255
	 */
	public int getLeftYAxis() {

		//Get the polled value and convert it to an integer from -255 to 255
		double polledValue = PS3Components[LEFT_Y_AXIS].getPollData();
		polledValue = Math.round(polledValue*-1*255);

		//If the polled value is greater than 20 (to eliminate idle noise)
		if (Math.abs(polledValue) > 20) {

			//Return the value
			return (int)polledValue;
		}
		else {
			return 0;
		}

	}

	/**
	 * Gets the right x axis value.
	 *
	 * @return the right x axis from -255 to 255
	 */
	public int getRightXAxis() {

		//Get the polled value and convert it to an integer from -255 to 255
		double polledValue = PS3Components[RIGHT_X_AXIS].getPollData();
		polledValue = Math.round(polledValue*255);

		//If the polled value is greater than 20 (to eliminate idle noise)
		if (Math.abs(polledValue) > 20) {

			//Return the value
			return (int)polledValue;
		}
		else {
			return 0;
		}

	}

	/**
	 * Send robot commands based on the controller input.
	 *
	 * @param commands the robot command object
	 * @throws Exception the exception
	 */
	public void sendRobotCommands(CompositeCommands commands) throws Exception {

		//Poll the controller for current values
		pollController();

		//Get the values of the components of interest
		int drivePower = getLeftYAxis();
		int steerPower = getRightXAxis();

		//If the drive level has changed a significant value since the last value
		//Send a command to the robot.  This cuts down on unneeded communications.
		if (Math.abs(drivePower - previousDrivePower) > 5) {

			//Set the new drive power
			previousDrivePower = drivePower;

			//If the drive power is positive
			if (drivePower > 0) {
				log.info("Changing forward drive power to " + drivePower);
				commands.motorDriveForward(Math.abs(drivePower));
			}

			//If the drive power is negative
			if(drivePower < 0) {
				log.info("Changing reverse drive power to " + drivePower);
				commands.motorDriveReverse(Math.abs(drivePower));
			}

			//If the drive power is 0, stop the motors
			if(drivePower == 0) {
				log.info("Changing drive power to 0");
				commands.motorStop();
			}
		}

		//If the steering level has changed a significant amount since the last value sent,
		//send a new command to the robot.  This cuts down on unneeded communications.
		if (Math.abs(steerPower - previousSteerPower) > 5) {

			previousSteerPower = steerPower;

			//If the steer power is positive
			if (steerPower > 0) {
				log.info("Changing right steering power to 255");
				commands.motorTurnRight(Math.abs(255));
			}

			//If the steer power is negative
			if(steerPower < 0) {
				log.info("Changing left steering power to 255");
				commands.motorTurnLeft(Math.abs(255));
			}

			//If the drive power is 0, center the motors
			if(steerPower == 0) {
				log.info("Changing steering power to 0 (centering)");
				commands.motorCenter();
			}
		}

		//Establish an event queue for the PS3 controller
		EventQueue queue = PS3Controller.getEventQueue();
		Event event = new Event();

		while(queue.getNextEvent(event)) {

			//Get the next component in the event queue
			Component comp = event.getComponent();

			//If the component was the DPAD Up button
			if(comp.getName().equals(Integer.toString(DPAD_UP_BUTTON)) && comp.getPollData() == 1.0f) {
				log.info("DPAD up button press found in the event queue");
				commands.menuUp();
			}

			//If the component was the DPAD Down button
			if(comp.getName().equals(Integer.toString(DPAD_DOWN_BUTTON)) && comp.getPollData() == 1.0f) {
				log.info("DPAD down button press found in the event queue");
				commands.menuDown();
			}

			//If the component was the DPAD Left button
			if(comp.getName().equals(Integer.toString(DPAD_LEFT_BUTTON)) && comp.getPollData() == 1.0f) {
				log.info("DPAD left button press found in the event queue");
				commands.menuLeft();
			}

			//If the component was the DPAD Right button
			if(comp.getName().equals(Integer.toString(DPAD_RIGHT_BUTTON)) && comp.getPollData() == 1.0f) {
				log.info("DPAD right button press found in the event queue");
				commands.menuRight();
			}

			//If the component was the X button
			if(comp.getName().equals(Integer.toString(X_BUTTON)) && comp.getPollData() == 1.0f) {
				log.info("X button press found in the event queue");
				commands.menuSelect();
			}

			//If the component was the Triangle button
			if(comp.getName().equals(Integer.toString(TRIANGLE_BUTTON)) && comp.getPollData() == 1.0f) {
				log.info("Triangle button press found in the event queue");
				commands.menuBack();
			}
		}
	}

	/**
	 * Manual control.  This method allows the application to transfer control of the
	 * mobile device to the user.  This is a good function to use when the algorithm 
	 * fails so that we can manually drive the robot home.
	 *
	 * @param commands the commands
	 * @param txSleepTime the tx sleep time
	 * @throws Exception the exception
	 */
	public void manualControl(CompositeCommands commands, int txSleepTime) throws Exception {

		//Do this until we decide to break from the loop
		while(true) {
			
			//Send the commands to the robot based on controller input
			sendRobotCommands(commands);
			
			//Wait
			Thread.sleep(txSleepTime);	
		}
	}
	
	
}
