package com.nullcognition.animationtransitionmaterial;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

// shape deformation, stretch on length of the fall, and squash at the bottom of the floor

public class SquashStrech extends AppCompatActivity{

	private static final AccelerateInterpolator accelerator = new AccelerateInterpolator();
	private static final DecelerateInterpolator decelerator = new DecelerateInterpolator();

	ViewGroup container = null;
	private static final long BASE_DURATION = 300;
	private              long animatorScale = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_squash_strech);

		container = (ViewGroup) findViewById(R.id.container);
	}

	public void onButtonClick(final View view){
		long animationDuration = (long) (BASE_DURATION * animatorScale);

		view.setPivotX(view.getWidth() / 2);
		view.setPivotY(view.getHeight());

		PropertyValuesHolder ty       = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, container.getHeight() - view.getHeight());
		PropertyValuesHolder sx       = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.7f);
		PropertyValuesHolder sy       = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f);
		ObjectAnimator       downAnim = ObjectAnimator.ofPropertyValuesHolder(view, ty, sx, sy);
		downAnim.setInterpolator(accelerator);
		downAnim.setDuration((long) animationDuration * 4);
		// ^^^ first animation for the downward motion

		sx = PropertyValuesHolder.ofFloat(View.SCALE_X, 2);
		sy = PropertyValuesHolder.ofFloat(View.SCALE_Y, .5f);
		ObjectAnimator stretchAnim = ObjectAnimator.ofPropertyValuesHolder(view, sx, sy);
		stretchAnim.setRepeatCount(1);
		stretchAnim.setRepeatMode(ValueAnimator.REVERSE);
		stretchAnim.setInterpolator(decelerator);
		stretchAnim.setDuration(animationDuration * 2);
		// ^^^ the squish part

		// Animate back to the start
		ty = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 0);
		sx = PropertyValuesHolder.ofFloat(View.SCALE_X, 1);
		sy = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1);
		ObjectAnimator upAnim = ObjectAnimator.ofPropertyValuesHolder(view, ty, sx, sy);
		upAnim.setDuration((long) (animationDuration * 4));
		upAnim.setInterpolator(decelerator);


		AnimatorSet set = new AnimatorSet();
		set.playSequentially(downAnim, stretchAnim, upAnim);
		set.start();
	}

}
