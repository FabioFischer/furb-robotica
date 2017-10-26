package examples;

import java.util.*;
import algorithms.*;
import core.Direction;
import core.IRobotic;
import core.ISensorWall;
import core.RoboticMapper;
import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class Trab2 {
	
	static class SensorWall implements ISensorWall {
		private UltrasonicSensor ultrasonicSensor;
		
		private int speed = 400;		
		private int rotateRight = 92;
		private int rotateLeft = -92;
		
		private int distanceThreshold = 20;
		
		public SensorWall(SensorPort sensorPort) {
			this.ultrasonicSensor = new UltrasonicSensor(sensorPort);
		}

		public boolean isBlocked() {
			System.out.println(ultrasonicSensor.getDistance());
			return ultrasonicSensor.getDistance() <= distanceThreshold;
		}
		
		public void rotateLeft() {
			Motor.A.setSpeed(speed);
			Motor.A.rotate(rotateRight);			
			Motor.A.setSpeed(0);
		}
		
		public void rotateRight() {
			Motor.A.setSpeed(speed);
			Motor.A.rotate(rotateLeft);
			Motor.A.setSpeed(0);
		}

		@Override
		public boolean detect(Direction direction) {
			if (direction == Direction.FRONT) {
				return isBlocked();
			} else if (direction == Direction.RIGHT) {
				this.rotateRight();
				boolean isBlocked = this.isBlocked();
				this.rotateLeft();
				return isBlocked;				
			} else if (direction == Direction.LEFT) {
				this.rotateLeft();
				boolean isBlocked = this.isBlocked();
				this.rotateRight();
				return isBlocked;	
			}
			
			return false;
		}		
	}
		
	static class Robotic implements IRobotic {
		private int movementCycles = 1850;
		private int movementSpeed = 390;
		private int rotateSpeed = 390;
		private int rotateCicles = 150;

		private void setSpeed(int speed) {
			Motor.C.setSpeed(speed);
			Motor.B.setSpeed(speed);
		}
		
		private void broker() {
			while (!Button.ENTER.isDown()) { }
			while (!Button.ENTER.isUp()) {}
		}
		
		public void rotateRight() {
			this.setSpeed(rotateSpeed);
			
			for (int i = 0; i < rotateCicles; i++) {
				Motor.C.forward();
				Motor.B.backward();
			}
			
			this.setSpeed(0);
			this.broker();
		}
		
		
		public void rotateLeft() {
			this.setSpeed(rotateSpeed);
			
			for (int i = 0; i < rotateCicles; i++) {
				Motor.B.forward();
				Motor.C.backward();
			}
			
			this.setSpeed(0);
			this.broker();
		}

		@Override
		public void moviment() {
			this.setSpeed(movementSpeed);
			
			for (int i = 0; i < movementCycles; i++) {
				Motor.B.forward();
				Motor.C.forward();
			}

			this.setSpeed(0);
		}

		@Override
		public void rotate(Direction direction) {
			if (direction == Direction.RIGHT) this.rotateRight();
			else if (direction == Direction.LEFT) this.rotateLeft();
		}
	}
	
	static int[][] world = new int[][] {
		{  1,  2,  3,  4,  5,  6,  7 },
		{  8,  9, 10, 11, 12, 13, 14 },
		{ 15, 16, 17, 18, 19, 20, 21 },
		{ 22, 23, 24, 25, 26, 27, 28 },
		{ 29, 30, 31, 32, 33, 34, 35 },
		{ 36, 37, 38, 39, 40, 41, 42 },
		{ 43, 44, 45, 46, 47, 48, 49 }
	};
		
	public static void main(String args[]) throws InterruptedException {
		Robotic robo = new Robotic();
		SensorWall sensorWall = new SensorWall(SensorPort.S4);
		
		for (int i = 0; i < world.length; i++) {
			for (int j = 0; j < world.length; j++) {
				world[i][j] = 0;
			}
		}
		
		RoboticMapper robotic = new RoboticMapper(
			world,
			new Point(0, 0),
			Direction.RIGHT,
			sensorWall,
			robo
		);
		
		while (!Button.ENTER.isDown());
						
		while (true) {				
			robotic.discover();
		}
	}
}




