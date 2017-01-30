package org.team3128.mechanisms;

import org.team3128.common.hardware.misc.Piston;
import org.team3128.common.hardware.motor.MotorGroup;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.InterruptHandlerFunction;

public class GearRollerBackDoor  {

	public enum GearState
	{
		INERT,
		SUCKIN,
		REVERSE;		
	}
	
	Piston doorPiston, gearPiston;
	MotorGroup gearRoller;
	DigitalInput digitalInput;
	GearState state; //may be accessed from an interrupt, do not write directly

	public GearRollerBackDoor(Piston doorPiston, Piston gearPiston, MotorGroup gearRoller, DigitalInput digitalInput) 
	{
		this.doorPiston = doorPiston;
		this.gearPiston = gearPiston;
		this.gearRoller = gearRoller;
		this.digitalInput = digitalInput;
		
		deactivateLoadingMode();
		
		digitalInput.enableInterrupts();
		digitalInput.requestInterrupts(new InterruptHandlerFunction<Object>()
		{
			public void interruptFired(int interruptAssertedMask, Object param) 
			{
				onGearLimitSwitchTriggered();
			};
		});
	}
	
	private synchronized void setState(GearState newState)
	{
		this.state = newState;
		
		if (newState == GearState.INERT) {
			gearRoller.setTarget(0);
		}
		else if (newState == GearState.SUCKIN) {
			gearRoller.setTarget(-1);
		}
		else if (newState == GearState.REVERSE) {
			gearRoller.setTarget(1);
		}
	}
	
	public void activateLoadingMode()
	{
		doorPiston.setPistonOn();
		gearPiston.setPistonOn();
		
		setState(GearState.SUCKIN);
	}
	
	private void onGearLimitSwitchTriggered()
	{
		setState(GearState.REVERSE);
		
		gearPiston.setPistonOff();
	}
	public void deactivateLoadingMode()
	{
		setState(GearState.INERT);
		
		doorPiston.setPistonOff();
	}
	
	
}