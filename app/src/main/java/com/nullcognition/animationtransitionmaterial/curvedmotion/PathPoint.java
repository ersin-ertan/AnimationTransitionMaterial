package com.nullcognition.animationtransitionmaterial.curvedmotion;
// ersin 08/08/15 Copyright (c) 2015+ All rights reserved.


// hold info about location/how the path should get to that location from previoeus path location,
// and info on traversal for intervals from previous location
public class PathPoint{

	public static final int MOVE  = 0;
	public static final int LINE  = 1;
	public static final int CURVE = 2;

	float x, y;
	float control0x, control0y; // control points for curve
	float control1x, control1y;
	int operation; // motion from PathPoint in AnimatorPath to location of this PathPoint, either move, line, curve

	private PathPoint(int operation, float x, float y){
		this.operation = operation;
		this.x = x;
		this.y = y;
	}

	private PathPoint(float c0X, float c0Y, float c1X, float c1Y, float x, float y){
		control0x = c0X;
		control0y = c0Y;
		control1x = c1X;
		control1y = c1Y;
		this.x = x;
		this.y = y;
		operation = CURVE;
	}

	// Constructs and returns a PathPoint object that describes a line to the given xy location.
	public static PathPoint lineTo(float x, float y){
		return new PathPoint(LINE, x, y);
	}

	/**
	 PathPoint object that describes a curve to the given xy location with the control points at c0 and c1.
	 */
	public static PathPoint curveTo(float c0X, float c0Y, float c1X, float c1Y, float x, float y){
		return new PathPoint(c0X, c0Y, c1X, c1Y, x, y);
	}

	/**
	 Constructs and returns a PathPoint object that describes a discontinuous move to the given, xy location.
	 */
	public static PathPoint moveTo(float x, float y){
		return new PathPoint(MOVE, x, y);
	}


}
