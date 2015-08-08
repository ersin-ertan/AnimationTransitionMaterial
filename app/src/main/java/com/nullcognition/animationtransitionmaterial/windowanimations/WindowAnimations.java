package com.nullcognition.animationtransitionmaterial.windowanimations;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nullcognition.animationtransitionmaterial.R;

// using the custom animation, the scale animation, and the thumbnail scale up animation with the cross fade

public class WindowAnimations extends AppCompatActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_window_animations2);

		final Button    defaultButton   = (Button) findViewById(R.id.defaultButton);
		final Button    translateButton = (Button) findViewById(R.id.translateButton);
		final Button    scaleButton     = (Button) findViewById(R.id.scaleButton);
		final ImageView thumbnail       = (ImageView) findViewById(R.id.thumbnail);


		// By default, launching a sub-activity uses the system default for window animations
		defaultButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent subActivity = new Intent(WindowAnimations.this, SubActivity.class);
				startActivity(subActivity);
			}
		});

		// Custom animations allow us to do things like slide the next activity in as we
		// slide this activity out
		translateButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent subActivity = new Intent(WindowAnimations.this, AnimatedSubActivity.class);
				// The enter/exit animations for the two activities are specified by xml resources
				Bundle translateBundle = ActivityOptions.makeCustomAnimation(
						WindowAnimations.this, R.anim.slide_in_left, R.anim.slide_out_left).toBundle();
				startActivity(subActivity, translateBundle);
			} // with the key class being the ActivityOptions.makeCustomAnimation
		});

		// Starting in Jellybean, you can provide an animation that scales up the new
		// activity from a given source rectangle
		scaleButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				Intent subActivity = new Intent(WindowAnimations.this, AnimatedSubActivity.class);
				Bundle scaleBundle = ActivityOptions.makeScaleUpAnimation(
						v, 0, 0, v.getWidth(), v.getHeight()).toBundle();
				startActivity(subActivity, scaleBundle);
			}
		});

		// Starting in Jellybean, you can also provide an animation that scales up the new
		// activity from a given bitmap, cross-fading between the starting and ending
		// representations. Here, we scale up from a thumbnail image of the final sub-activity
		thumbnail.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v){
				BitmapDrawable drawable    = (BitmapDrawable) thumbnail.getDrawable();
				Bitmap         bm          = drawable.getBitmap();
				Intent         subActivity = new Intent(WindowAnimations.this, AnimatedSubActivity.class);
				Bundle scaleBundle = ActivityOptions.makeThumbnailScaleUpAnimation(
						thumbnail, bm, 0, 0).toBundle();
				startActivity(subActivity, scaleBundle);
			}
		});
	}


}
