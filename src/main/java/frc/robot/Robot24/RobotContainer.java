// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Robot24;

import frc.lib.DriverStationTriggers;
import frc.lib.RobotContainerInterface;
import frc.lib.Arduino.Arduino;
import frc.lib.can.CanStream;
import frc.lib.controllers.CommandXBoxWrapper;
import frc.lib.controllers.LazyCommandXboxWrapper;
import frc.lib.devices.DigitalInputWrapper;
import frc.lib.drivetrain.DriveCommand;
import frc.lib.drivetrain.DriveTrain;
import frc.lib.drivetrain.ObjectTracker;
import frc.lib.faults.Fault;
import frc.lib.faults.PDHLogPowerFaults;
import frc.lib.leds.CANdleWrapper;
import frc.lib.leds.LEDs;
import frc.lib.leds.LedSignal;
import frc.lib.limeLight.LimelightPortForwarding;
import frc.lib.music.MusicToneCommand;
import frc.lib.music.Note;
import frc.lib.music.TalonOrchestra;
import frc.lib.selfCheck.RobotSelfCheckCommand;
import frc.robot.Robot24.Constants.OperatorConstants;

import com.ctre.phoenix.led.CANdle;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import static frc.lib.Arduino.ArduinoCommand.*;

public class RobotContainer{

  
    // driverController.a().onTrue(arduino.runCommand(BLUE));
    // driverController.b().onTrue(arduino.runCommand(GREEN));

  @SuppressWarnings("unused")
  private final DriveTrain driveTrain = new DriveTrain(
    Constants.DriveTrain.frontLeftOffset,
    Constants.DriveTrain.frontrightoffset, 
    Constants.DriveTrain.backleftoffset, 
    Constants.DriveTrain.backrightoffset
  );
  private final PowerDistribution PowerDistribution = new PowerDistribution();
  @SuppressWarnings("unused")
  private final CommandXBoxWrapper driverController = new CommandXBoxWrapper("Driver Controller",
      OperatorConstants.kDriverControllerPort);



  public RobotContainer() {
    PDHLogPowerFaults.setPdh(PowerDistribution, 8, 12, 13, 14, 15, 16, 17, 22, 23);
    configureBindings();
  }


  private void configureBindings() {

    // driver controls

    var driveCommand = new DriveCommand(
        driverController,
        driveTrain);
    driveTrain.setDefaultCommand(driveCommand);
    // driverController.joysticksTrigger().onTrue(driveCommand);

    driverController.start().onTrue(driveTrain.zeroCommand());
    // duplacates on purpos
    driverController.back().onTrue(driveTrain.zeroCommand());

    //driverController.x().whileTrue((driveTrain.xcommand()));
    driverController.x().onTrue(driveTrain.zeroTurningMotors());

    // driverController.povRight().whileTrue(new Angles(arm));




  
    // driverController.a().whileTrue(new ObjectTracker(driveTrain, driverController::getLeftX, driverController::getLeftY));


  }


  public Command getTestCommand() {
    return new RobotSelfCheckCommand(
        Commands.sequence(
            new MusicToneCommand(Note.MiddleC, driveTrain).withTimeout(0.25),
            new MusicToneCommand(Note.HighC, driveTrain).withTimeout(0.25)),
        Commands.sequence(
            new MusicToneCommand(Note.MiddleC, driveTrain).withTimeout(0.25),
            new MusicToneCommand(Note.LowC, driveTrain).withTimeout(0.25)),
        driveTrain
        // shooter,
        // intake,
        //  arm
         );
  }
  // CanStream canStream = new CanStream();
}
