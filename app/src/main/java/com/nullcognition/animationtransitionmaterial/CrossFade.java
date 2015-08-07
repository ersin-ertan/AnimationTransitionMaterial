package com.nullcognition.animationtransitionmaterial;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class CrossFade extends AppCompatActivity{

	boolean isRed = true;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		createCrossFade();


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
