package com.nullcognition.animationtransitionmaterial.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;

// ersin 06/08/15 Copyright (c) 2015+ All rights reserved.
public class V3 extends V{
	public V3(final Context context){
		super(context);
	}
	public V3(final Context context, final AttributeSet attrs){
		super(context, attrs);
	}
	public V3(final Context context, final AttributeSet attrs, final int defStyleAttr){
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void startAnimation(){
		ObjectAnimator animator = getValueAnimator(); // should be call get ObjectAnimator, but the abstract class has been defined as Value
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.REVERSE);
		animator.setInterpolator(new AccelerateInterpolator());
		animator.start();
	}
	@Override
	public ObjectAnimator getValueAnimator(){
		return ObjectAnimator.ofInt(this, "shapeY", 0, (getHeight() - mShapeH));
	}
}
// This variation uses an ObjectAnimator. The functionality is exactly the same as
// in Bouncer2, but this time the boilerplate code is greatly reduced because we
// tell ObjectAnimator to automatically animate the target object for us, so we no
// longer need to listen for frame updates and do that work ourselves.
