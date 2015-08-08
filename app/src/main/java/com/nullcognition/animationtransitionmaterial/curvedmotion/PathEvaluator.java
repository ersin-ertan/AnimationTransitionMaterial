package com.nullcognition.animationtransitionmaterial.curvedmotion;
// ersin 08/08/15 Copyright (c) 2015+ All rights reserved.

import android.animation.TypeEvaluator;

/**
 This evaluator interpolates between two PathPoint values given the value t, the
 proportion traveled between those points. The value of the interpolation depends
 on the operation specified by the endValue (the operation for the interval between
 PathPoints is always specified by the end point of that interval).
 */
public class PathEvaluator implements TypeEvaluator<PathPoint>{
	@Override
	public PathPoint evaluate(float t, PathPoint startValue, PathPoint endValue){
		float x, y;
		if(endValue.operation == PathPoint.CURVE){
			float oneMinusT = 1 - t;
			x = oneMinusT * oneMinusT * oneMinusT * startValue.x +
					3 * oneMinusT * oneMinusT * t * endValue.control0x +
					3 * oneMinusT * t * t * endValue.control1x +
					t * t * t * endValue.x;
			y = oneMinusT * oneMinusT * oneMinusT * startValue.y +
					3 * oneMinusT * oneMinusT * t * endValue.control0y +
					3 * oneMinusT * t * t * endValue.control1y +
					t * t * t * endValue.y;
		}
		else if(endValue.operation == PathPoint.LINE){
			x = startValue.y + t * (endValue.x - startValue.x);
			y = startValue.y + t * (endValue.y - startValue.y);
		}
		else{
			x = endValue.x;
			y = endValue.y;
		}
		return PathPoint.moveTo(x, y);
	}
}
