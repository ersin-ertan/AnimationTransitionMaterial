package com.nullcognition.animationtransitionmaterial;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

public class MainActivity02 extends AppCompatActivity{

	boolean isRed = true;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

//		createCrossFade();
		createPropertyAnimations();

	}

	CheckBox mCheckBox;
	private void createPropertyAnimations(){

		setContentView(R.layout.activity_property_animator);

		mCheckBox = (CheckBox) findViewById(R.id.checkbox);
		final Button alphaButton     = (Button) findViewById(R.id.alphaButton);
		final Button translateButton = (Button) findViewById(R.id.translateButton);
		final Button rotateButton    = (Button) findViewById(R.id.rotateButton);
		final Button scaleButton     = (Button) findViewById(R.id.scaleButton);
		final Button setButton       = (Button) findViewById(R.id.setButton);

		ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(alphaButton, View.ALPHA, 0);
		setRepeat(alphaAnimation);

		ObjectAnimator translateAnimation = ObjectAnimator.ofFloat(translateButton, View.TRANSLATION_X, 800);
		setRepeat(translateAnimation);

		ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(rotateButton, View.ROTATION, 360);
		setRepeat(rotateAnimation);

		// Scale the button in X and Y. Note the use of PropertyValuesHolder to animate
		// multiple properties on the same object in parallel.
		PropertyValuesHolder pvhX           = PropertyValuesHolder.ofFloat(View.SCALE_X, 2);
		PropertyValuesHolder pvhY           = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2);
		ObjectAnimator       scaleAnimation = ObjectAnimator.ofPropertyValuesHolder(scaleButton, pvhX, pvhY);
		setRepeat(scaleAnimation);

		// all animations on one object
		AnimatorSet setAnimation = new AnimatorSet();
		setAnimation.play(translateAnimation).after(alphaAnimation).before(rotateAnimation);
		setAnimation.play(rotateAnimation).before(scaleAnimation);


		// setup the animation to play on button click, with animation.start(), or with via loading the resource
		setupAnimation(alphaButton, alphaAnimation, R.animator.fade);
		setupAnimation(translateButton, translateAnimation, R.animator.move);
		setupAnimation(rotateButton, rotateAnimation, R.animator.spin);
		setupAnimation(scaleButton, scaleAnimation, R.animator.scale);
		setupAnimation(setButton, setAnimation, R.animator.combo);


	}

	private void setupAnimation(View view, final Animator animation, final int animationID){
		view.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				// If the button is checked, load the animation from the given resource
				// id instead of using the passed-in animation parameter. See the xml files
				// for the details on those animations.
				if(mCheckBox.isChecked()){
					// when using resources and clicking the animation button after the animation has started will permanently change the default start value to the value of when you clicked it,

					Animator anim = AnimatorInflater.loadAnimator(MainActivity02.this, animationID);
					anim.setTarget(v);
					anim.start();
					return;
				}
				animation.start();
			}
		});
	}

	private void setRepeat(ObjectAnimator objectAnimator){
		objectAnimator.setRepeatCount(1);
		objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
	}

	private void createCrossFade(){

		setContentView(R.layout.activity_cross_fade);
		ImageView imageView = (ImageView) findViewById(R.id.imageView);

		Bitmap bitmap00 = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
		Bitmap bitmap01 = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmap00);
		canvas.drawColor(Color.RED);

		canvas = new Canvas(bitmap01);
		canvas.drawColor(Color.GREEN);

		BitmapDrawable[] bitmapDrawables = new BitmapDrawable[2];
		bitmapDrawables[0] = new BitmapDrawable(getResources(), bitmap00);
		bitmapDrawables[1] = new BitmapDrawable(getResources(), bitmap01);

		// key class, layers Red 0 then green 1, with red on top, then does fade
		final TransitionDrawable transitionDrawable = new TransitionDrawable(bitmapDrawables);
		imageView.setImageDrawable(transitionDrawable);

		// on Clicklistener with the isRed boolean works well, if using the touch listener
		// a bug is when you touch wait till green then untouch and touch, which will restart
		// the transition from red to green, thus the green to red restart is a discrete jump

		imageView.setOnTouchListener(
				new View.OnTouchListener(){
					@Override
					public boolean onTouch(final View v, final MotionEvent event){
						if(MotionEvent.ACTION_DOWN == event.getAction()){
//							transitionDrawable.getAlpha(); may be of help to see how far along the way you have gone, ex. past 50 so set the transition faster(hack)
							transitionDrawable.startTransition(500);
						}
						else if(MotionEvent.ACTION_UP == event.getAction()){
							transitionDrawable.reverseTransition(500);
						}
						return true;
					}
				}
		);
	}
}
