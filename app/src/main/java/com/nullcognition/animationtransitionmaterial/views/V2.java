package com.nullcognition.animationtransitionmaterial.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;

// ersin 06/08/15 Copyright (c) 2015+ All rights reserved.
public class V2 extends V{
	public V2(final Context context){
		super(context);
	}
	public V2(final Context context, final AttributeSet attrs){
		super(context, attrs);
	}
	public V2(final Context context, final AttributeSet attrs, final int defStyleAttr){
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void startAnimation(){
		ValueAnimator animator = getValueAnimator();
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.REVERSE);
		animator.setInterpolator(new AccelerateInterpolator());
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
		// This variation finally has the shape bouncing, due to the use of an
		// AccelerateInterpolator, which speeds up as the animation proceed. Note that
		// when the animation reverses, the interpolator acts in reverse, decelerating
		// movement.
