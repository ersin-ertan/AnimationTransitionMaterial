package com.nullcognition.animationtransitionmaterial;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

public class MainActivity03 extends AppCompatActivity{

	private static final float TX_START = 0;
	private static final float TY_START = 0;
	private static final float TX_END   = 200;
	private static final float TY_END   = 400;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		multipropertyAnimation();
	}
	private void multipropertyAnimation(){
		setContentView(R.layout.activity_multipropertyanimation);

	}

	// valueanimator to animate fractional values which are then set to the object
	public void runValueAnimator(final View view){
		ValueAnimator anim = ValueAnimator.ofFloat(0, 400);
		anim.setDuration(1000);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){
			@Override
			public void onAnimationUpdate(final ValueAnimator animation){
				float fraction = animation.getAnimatedFraction();
				((Button) view).setText(String.valueOf(animation.getAnimatedFraction()));
				view.setTranslationX(TX_START + fraction * (TX_END - TX_START));
				view.setTranslationY(TY_START + fraction * (TY_END - TY_START));
			}
		});
		anim.start();
	}

	// clean way to animate view properties
	public void runViewPropertyAnimator(final View view){
		view.animate().translationX(TX_END).translationY(TY_END).setDuration(1000);
	}

	// multiple object animator objects to be run in parallel
	public void runObjectAnimators(View view){
		ObjectAnimator oaX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, TX_END);
		ObjectAnimator oaY = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, TY_END);
//		oaX.start(); // just put the start on the end of the above definitions and delete the object assignment
//		oaY.start(); // object assignment used with below example of animation set for parallel

		// or to run them in parallel
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(oaX).with(oaY);
		animatorSet.setDuration(1000);
//		animatorSet.setInterpolator(new OvershootInterpolator()); // working
		animatorSet.start();
	}

	public void runObjectAnimator(View view){
		PropertyValuesHolder pvhTX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, TX_END);
		PropertyValuesHolder pvhTY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, TY_END);
		ObjectAnimator.ofPropertyValuesHolder(view, pvhTX, pvhTY).setDuration(1000).start();
	}


}
