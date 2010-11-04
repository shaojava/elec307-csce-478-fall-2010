package PS3Controller;

import RobotCommands.CompositeCommands;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;


// TODO: Auto-generated Javadoc
/**
 * The Class PS3Controller.
 */
public class PS3Controller {

	/** The PS3 controller. */
	private Controller PS3Controller;

	/** The PS3 components. */
	private Component[] PS3Components;

	/** The left y axis component. */
	private int LEFT_Y_AXIS = 1;

	/** The right x axis component. */
	private int RIGHT_X_AXIS = 2;

	/** The previous drive power. */
	private int previousDrivePower = 0;

	/** The previous steer power. */
	private int previousSteerPower = 0;

	/**
	 * Instantiates a new PS3 controller.
	 */
	public PS3Controller() {

		//Get the list of the connected controllers
		Controller [] controllerList = ControllerEnvironment.getDefaultEnvironment().getControllers();

		//For all the connected controllers
		for(int i =0;i<controllerList.length;i++){

			//If the controller in the list is the PS3 controller, save it
			if(controllerList[i].getName().equals("PLAYSTATION(R)3 Controller")) {
				PS3Controller = controllerList[i];
			}

		}

		//Save the list of components for the PS3 Controller
		PS3Components = PS3Controller.getComponents();

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
				commands.motorDriveForward(Math.abs(drivePower));
			}

			//If the drive power is negative
			if(drivePower < 0) {
				commands.motorDriveReverse(Math.abs(drivePower));
			}
			
			//If the drive power is 0, stop the motors
			if(drivePower == 0) {
				commands.motorStop();
			}
		}
		
		//If the steering level has changed a significant amount since the last value sent,
		//send a new command to the robot.  This cuts down on unneeded communications.
		if (Math.abs(steerPower - previousSteerPower) > 5) {

			//If the steer power is positive
			if (steerPower > 0) {
				commands.motorTurnRight(Math.abs(steerPower));
			}
			
			//If the steer power is negative
			if(steerPower < 0) {
				commands.motorTurnLeft(Math.abs(steerPower));
			}
			
			//If the drive power is 0, center the motors
			if(steerPower == 0) {
				commands.motorCenter();
			}
		}

		//Console output (for temp debug)
		System.out.println(drivePower + " " + steerPower);
	}



}
