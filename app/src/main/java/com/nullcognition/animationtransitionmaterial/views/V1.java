package com.nullcognition.animationtransitionmaterial.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;

// ersin 06/08/15 Copyright (c) 2015+ All rights reserved.
public class V1 extends V{
	public V1(final Context context){
		super(context);
	}
	public V1(final Context context, final AttributeSet attrs){
		super(context, attrs);
	}
	public V1(final Context context, final AttributeSet attrs, final int defStyleAttr){
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void startAnimation(){
		ValueAnimator animator = getValueAnimator();
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.REVERSE);

		animator.start();
	}

	@Override
	public ValueAnimator getValueAnimator(){
		ValueAnimator anim = ValueAnimator.ofFloat(0, 1);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
			@Override
			public void onAnimationUpdate(ValueAnimator animation){
				setShapeY((int) (animation.getAnimatedFraction() * (getHeight() - mShapeH)));
			}
		});
		return anim;
	}
}
// In this variation, we put the shape into an infinite bounce, where it keeps moving
// up and down. Note that it's still not actually "bouncing" because it just uses
// default time interpolation.
