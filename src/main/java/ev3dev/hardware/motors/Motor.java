//-----------------------------------------------------------------------------
//~autogen autogen-header

//~autogen
//-----------------------------------------------------------------------------

package ev3dev.hardware.motors;

//-----------------------------------------------------------------------------

import java.io.IOException;

import ev3dev.exception.InvalidPortException;
import ev3dev.hardware.Device;
import ev3dev.hardware.ports.LegoPort;
import ev3dev.io.Def;
import ev3dev.io.Sysclass;

//-----------------------------------------------------------------------------

//~autogen generic-classes classes.motor>currentClass


//~autogen

public class Motor extends Device{
	
//-----------------------------------------------------------------------------

	private String address;
	
//-----------------------------------------------------------------------------
	
	/***
	 * Creates a new motor object.
	 * @param device A device object connecting a motor
	 * @throws InvalidPortException If the LegoPort isn't a OUTPUT, invalid or a tacho-motor.
	 * @throws IOException If the LegoPort specified goes wrong
	 */
	public Motor(LegoPort port) throws InvalidPortException, IOException{
		super(port, Def.MOTOR_CLASS_NAME, Def.SUB_MOTOR_CLASS_NAME);
		address = port.getAddress();
		
		//Verify is the LegoPort connecting a motor / is a output
		if (!address.contains("out")){
			throw new InvalidPortException("The specified port (" + port.getAddress() + ") isn't a output.");
		} else if (!port.getStatus().equals(Def.MOTOR_CLASS_NAME)){
			throw new InvalidPortException("The specified port (" + port.getAddress() + ") isn't a motor (" + port.getStatus() + ")");
		}
	}

//-----------------------------------------------------------------------------
	
//~autogen generic-get-set classes.motor>currentClass
	/***
	 * Get the address of this motor.
	 * @return LegoPort address described in String
	 * @throws IOException If the motor doesn't exist or IO ERROR
	 */
	public String getAddress() throws IOException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(Def.PROPERTY_ADDRESS);
	}
	
	/***
	 * Generic method to send commands to the motor controller.
	 * @param command Command that suits for the motor driver
	 */
	public void sendCommand(String command) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_COMMAND, command);
	}
	
	/***
	 * Cause the motor to run until another command is sent
	 * @throws IOException
	 */
	public void runForever() throws IOException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(Def.COMMAND_RUN_FOREVER);
	}
	
	/***
	 * Run to an absolute position specified by <b>position_sp</b>
	 *  and then stop using the command specified in <b>stop_command</b>
	 * @throws IOException If I/O goes wrong
	 */
	public void runToAbsPos() throws IOException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(Def.COMMAND_RUN_TO_ABS_POS);
	}
	
	/***
	 * Run to a position relative to the current position value.
	 * The new position will be <b>current position</b> + <b>position_sp</b>.
	 * When the new position is reached, the motor will stop
	 *  using the command specified by <b>stop_command</b>.
	 * @throws IOException If I/O goes wrong.
	 */
	public void runToRelPos() throws IOException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(Def.COMMAND_RUN_TO_REL_POS);
	}
	
	/***
	 * Run the motor for the amount of time specified in <b>time_sp</b>
	 *  and then stop the motor using the command specified by
	 *  <b>stop_command</b>
	 * @throws IOException If I/O goes wrong
	 */
	public void runTimed() throws IOException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(Def.COMMAND_RUN_TIMED);
	}
	
	/***
	 * Run the motor at the duty cycle specified by <b>duty_cycle_sp</b>.
	 *  Unlike other run commands, changing <b>duty_cycle_sp</b> while
	 *   running will take effect immediately
	 * @throws IOException If I/O goes wrong
	 */
	public void runDirect() throws IOException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(Def.COMMAND_RUN_DIRECT);
	}
	
	/**
	 * Stop any of the run commands before they are complete using the command specified by <b>stop_command</b>.
	 * @throws IOException If I/O goes wrong
	 */
	public void stop() throws IOException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(Def.COMMAND_STOP);
	}
	
	/**
	 * Reset all of the motor parameter attributes to their default value. This will also have the effect of stopping the motor.
	 * @throws IOException If I/O goes wrong
	 */
	public void reset() throws IOException{
		if (!this.isConnected()){
			return;
		}
		sendCommand(Def.COMMAND_RESET);
	}
	
	/**
	 * Returns a list of commands that are supported by the motor controller.
	 *  Possible values are run-forever, run-to-abs-pos, run-to-rel-pos,
	 *   run-timed, run-direct, stop and reset. Not all commands may be supported.
	 * @return A String Arrays with all the supported commands
	 * @throws IOException If I/O goes wrong
	 */
	public String[] getCommands() throws IOException{
		if (!this.isConnected()){
			return null;
		}
		String str = this.getAttribute(Def.PROPERTY_COMMANDS);
		return Sysclass.separateSpace(str);
	}
	
	/**
	 * Returns the number of tacho counts in one rotation of the motor.
	 *  Tacho counts are used by the position and speed attributes, so
	 *   you can use this value to convert rotations or degrees to tacho
	 *    counts. In the case of linear actuators, the units here will
	 *     be counts per centimeter.
	 * @return Counts per cm
	 * @throws IOException If I/O goes wrong
	 */
	public int getCountPerRot() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String countperrot = this.getAttribute(Def.PROPERTY_COUNT_PER_ROT);
		return Integer.parseInt(countperrot);
	}
	
	/**
	 * Returns the name of the driver that provides this tacho motor device.
	 * @return The name of the driver
	 * @throws IOException If I/O goes wrong
	 */
	public String getDriverName() throws IOException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(Def.PROPERTY_DRIVER_NAME);
	}
	
	
	/**
	 * Returns the current duty cycle of the motor. Units are percent. Values are -100 to 100.
	 * @return Percentage
	 * @throws IOException If I/O goes wrong
	 */
	public int getDutyCycle() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String dutycycle = this.getAttribute(Def.PROPERTY_DUTY_CYCLE);
		return Integer.parseInt(dutycycle);
	}
	
	/**
	 * Writing sets the duty cycle setpoint. Reading returns the current value. Units are in percent.
	 *  Valid values are -100 to 100. A negative value causes the motor to rotate in reverse.
	 *   This value is only used when speed_regulation is off.
	 * @return Percentage
	 * @throws IOException If I/O goes wrong
	 */
	public int getDutyCycleSP() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String dutycyclesp = this.getAttribute(Def.PROPERTY_DUTY_CYCLE_SP);
		return Integer.parseInt(dutycyclesp);
	}
	
	/**
	 * Writing sets the duty cycle setpoint. Reading returns the current value. Units are in percent.
	 *  Valid values are -100 to 100. A negative value causes the motor to rotate in reverse.
	 *   This value is only used when speed_regulation is off.
	 * @param sp Percentage
	 * @throws IOException If I/O goes wrong
	 */
	public void setDutyCycleSP(int sp) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_DUTY_CYCLE_SP, Integer.toString(sp));
	}
	
	/**
	 * Sets the polarity of the rotary encoder. This is an advanced feature to all use of motors
	 *  that send inversed encoder signals to the EV3. This should be set correctly by the driver
	 *   of a device. It You only need to change this value if you are using a unsupported device.
	 *    Valid values are normal and inversed.
	 * @return The polarity of the rotary encoder
	 * @throws IOException If I/O goes wrong
	 */
	public String getEncoderPolarity() throws IOException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(Def.PROPERTY_ENCODER_POLARITY);
	}
	
	/**
	 * Sets the polarity of the rotary encoder. This is an advanced feature to all use of motors
	 *  that send inversed encoder signals to the EV3. This should be set correctly by the driver
	 *   of a device. It You only need to change this value if you are using a unsupported device.
	 *    Valid values are normal and inversed.
	 * @param encoder_polarity The polarity of the rotary encoder
	 * @throws IOException If I/O goes wrong
	 */
	public void setEncoderPolarity(String encoder_polarity) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_ENCODER_POLARITY, encoder_polarity);
	}
	
	public String getPolarity() throws IOException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(Def.PROPERTY_POLARITY);
	}
	
	public void setPolarity(String polarity) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_POLARITY, polarity);
	}
	
	public int getPosition() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute("position_p");
		return Integer.parseInt(str);
	}
	
	public void setPosition(int position) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_POSITION, Integer.toString(position));
	}
	
	public int getPosition_P() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_POSITION_P);
		return Integer.parseInt(str);
	}
	
	public int getPosition_I() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_POSITION_I);
		return Integer.parseInt(str);
	}
	
	public int getPosition_D() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_POSITION_D);
		return Integer.parseInt(str);
	}
	
	public void setPosition_P(int position_p) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_POSITION_P, Integer.toString(position_p));
	}
	
	public void setPosition_I(int position_i) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_POSITION_I, Integer.toString(position_i));
	}
	
	public void setPosition_D(int position_d) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_POSITION_D, Integer.toString(position_d));
	}
	
	public int getPosition_SP() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_POSITION_SP);
		return Integer.parseInt(str);
	}

	public void setPosition_SP(int position_sp) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_POSITION_SP, Integer.toString(position_sp));
	}
	
	public int getSpeed() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_SPEED);
		return Integer.parseInt(str);
	}
	
	public int getSpeed_SP() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_SPEED);
		return Integer.parseInt(str);
	}
	
	public void setSpeed_SP(int speed_sp) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_SPEED_SP, Integer.toString(speed_sp));
	}
	
	public int getRamp_Up_SP() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_RAMP_UP_SP);
		return Integer.parseInt(str);
	}
	
	public void setRamp_Up_SP(int ramp_up_sp) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_RAMP_UP_SP, Integer.toString(ramp_up_sp));
	}
	
	public int getRamp_Down_SP() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_RAMP_DOWN_SP);
		return Integer.parseInt(str);
	}
	
	public void setRamp_Down_SP(int ramp_down_sp) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_RAMP_DOWN_SP, Integer.toString(ramp_down_sp));
	}
	
	public String getSpeedRegulationEnabled() throws IOException{
		if (!this.isConnected()){
			return null;
		}
		String str = this.getAttribute(Def.PROPERTY_SPEED_REGULATION_ENABLED);
		return str;
	}
	
	public boolean isSpeedRegulationEnabled() throws IOException{
		if (!this.isConnected()){
			return false;
		}
		String str = getSpeedRegulationEnabled();
		if (str.equals("on")){
			return true;
		} else if (str.equals("off")){
			return false;
		} else {
			return false;
		}
	}
	
	public void setSpeedRegulationEnabled(boolean enabled) throws IOException{
		if (!this.isConnected()){
			return;
		}
		String str = enabled ? "on" : "off";
		this.setAttribute(Def.PROPERTY_SPEED_REGULATION_ENABLED, str);
	}
	
	public void setSpeedRegulationEnabled(String onoff) throws IOException{
		if (!this.isConnected()){
			return;
		}
		boolean enabled = false;
		if (onoff.equals("on")){
			enabled = true;
		} else if (onoff.equals("off")){
			enabled = false;
		} else {
			enabled = false;
		}
		setSpeedRegulationEnabled(enabled);
	}
	
	public int getSpeedRegulation_P() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_SPEED_REGULATION_P);
		return Integer.parseInt(str);
	}
	
	public void setSpeedRegulation_P(int p) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_SPEED_REGULATION_P, Integer.toString(p));
	}
	
	public int getSpeedRegulation_I() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_SPEED_REGULATION_I);
		return Integer.parseInt(str);
	}
	
	public void setSpeedRegulation_I(int i) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_SPEED_REGULATION_I, Integer.toString(i));
	}
	
	public int getSpeedRegulation_D() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_SPEED_REGULATION_D);
		return Integer.parseInt(str);
	}
	
	public void setSpeedRegulation_D(int d) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_SPEED_REGULATION_D, Integer.toString(d));
	}
	
	public String getStateViaString() throws IOException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(Def.PROPERTY_STATE);
	}
	
	public String[] getState() throws IOException{
		if (!this.isConnected()){
			return null;
		}
		String str = getStateViaString();
		return Sysclass.separateSpace(str);
	}
	
	public String getStopCommand() throws IOException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(Def.PROPERTY_STOP_COMMAND);
	}
	
	public void setStopCommand(String stop_command) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_STOP_COMMAND, stop_command);
	}
	
	public String getStopCommandsViaString() throws IOException{
		if (!this.isConnected()){
			return null;
		}
		return this.getAttribute(Def.PROPERTY_STOP_COMMANDS);
	}
	
	public String[] getStopCommands() throws IOException{
		if (!this.isConnected()){
			return null;
		}
		String str = getStopCommandsViaString();
		return Sysclass.separateSpace(str);
	}
	
	public int getTime_SP() throws IOException{
		if (!this.isConnected()){
			return -1;
		}
		String str = this.getAttribute(Def.PROPERTY_TIME_SP);
		return Integer.parseInt(str);
	}
	
	public void setTime_SP(int time_sp) throws IOException{
		if (!this.isConnected()){
			return;
		}
		this.setAttribute(Def.PROPERTY_TIME_SP, Integer.toString(time_sp));
	}
//~autogen
}
//-----------------------------------------------------------------------------