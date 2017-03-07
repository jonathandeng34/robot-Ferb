package org.team3128.autonomous;

import org.team3128.common.util.enums.Direction;
import org.team3128.common.util.units.Length;
import org.team3128.main.MainFerb;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoPlaceGearFromRetrievalZone extends CommandGroup
{
	public AutoPlaceGearFromRetrievalZone(MainFerb robot){
		//real numbers still need to be added for the move and turn commands
		addSequential(robot.drive.new CmdMoveForward(-75 * Length.in, 4000, 0.5));
		addSequential(robot.drive.new CmdInPlaceTurn(40, 1750, Direction.LEFT));
		addSequential(robot.drive.new CmdMoveForward(-28 * Length.in, 3000, 0.50));
		addSequential(robot.gearRollerBackDoor.new CmdSetDepositingMode(true));
		addSequential(robot.drive.new CmdMoveForward(24 * Length.in, 6000, 0.5));
		addSequential(robot.gearRollerBackDoor.new CmdSetDepositingMode(false));
	}
}
