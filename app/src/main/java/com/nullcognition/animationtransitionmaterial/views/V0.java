package com.nullcognition.animationtransitionmaterial.views;
// ersin 06/08/15 Copyright (c) 2015+ All rights reserved.

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;

public class V0 extends V{
	public V0(final Context context){
		super(context);
	}
	public V0(final Context context, final AttributeSet attrs){
		super(context, attrs);
	}
	public V0(final Context context, final AttributeSet attrs, final int defStyleAttr){
		super(context, attrs, defStyleAttr);
	}

	@Override
	public void startAnimation(){
		ValueAnimator animator = getValueAnimator();



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
