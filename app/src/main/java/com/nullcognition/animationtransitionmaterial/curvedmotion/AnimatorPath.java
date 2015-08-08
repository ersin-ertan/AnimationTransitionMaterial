package com.nullcognition.animationtransitionmaterial.curvedmotion;
// ersin 08/08/15 Copyright (c) 2015+ All rights reserved.

import java.util.ArrayList;
import java.util.Collection;


// hold info about point along the path, api alows you to specify a move location to jump to the next point,
// a line location-segment from the previous point,o and a curve location for bezier curve from last location
public class AnimatorPath{

	// points on the path
	ArrayList<PathPoint> points = new ArrayList<PathPoint>();

	//
	public void moveTo(float x, float y){
		points.add(PathPoint.moveTo(x, y));
	}

	// create a straight line from pathpoint to new one by x and y
	public void lineTo(float x, float y){
		points.add(PathPoint.lineTo(x, y));
	}

	// create a quadratic bbezier curve rom the current path point to next by x and y, usinging current path
	// location as first anchor point and the intermediate control points as the end anchor
	public void curveTo(float c0X, float c0Y, float c1X, float c1Y, float x, float y){
		points.add(PathPoint.curveTo(c0X, c0Y, c1X, c1Y, x, y));
	}

	// return collection of PathPoint objects that describes all points in the path
	public Collection<PathPoint> getPoints(){ return points;}
}
