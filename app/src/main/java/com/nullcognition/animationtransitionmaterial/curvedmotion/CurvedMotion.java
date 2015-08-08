package com.nullcognition.animationtransitionmaterial.curvedmotion;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.nullcognition.animationtransitionmaterial.R;

// curved path between two endpoints, via PathEvaluator which does the interpolation along the path
// with Bezier control and anchor points in the path

public class CurvedMotion extends AppCompatActivity{

	private static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
	boolean topleft = true;
	Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_curved_motion);

		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View v){
				// Capture current location of button
				final int oldLeft = button.getLeft();
				final int oldTop  = button.getTop();

				// Change layout parameters of button to move it
				moveButton();

				// onpredrawlistener for button after layout but before drawing
				button.getViewTreeObserver().addOnPreDrawListener(
						new ViewTreeObserver.OnPreDrawListener(){
							public boolean onPreDraw(){
								button.getViewTreeObserver().removeOnPreDrawListener(this);

								// Capture new location
								int left   = button.getLeft();
								int top    = button.getTop();
								int deltaX = left - oldLeft;
								int deltaY = top - oldTop;

								// Set up path to new location using a Bezier spline curve
								AnimatorPath path = new AnimatorPath();
								path.moveTo(-deltaX, -deltaY);
								path.curveTo(-(deltaX / 2), -deltaY, 0, -deltaY / 2, 0, 0);

								// Set up the animation
								final ObjectAnimator anim = ObjectAnimator.ofObject(
										CurvedMotion.this, "buttonLoc", new PathEvaluator(), path.getPoints().toArray());
								anim.setInterpolator(DECELERATE_INTERPOLATOR);
								anim.start();
								return true;
							}
						});


			}
		});
	}
	private void moveButton(){
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) button.getLayoutParams();
		if(topleft){
			params.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		}
		else{
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			params.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		}
		button.setLayoutParams(params);
		topleft = !topleft;
	}

	/**
	 We need this setter to translate between the information the animator
	 produces (a new "PathPoint" describing the current animated location)
	 and the information that the button requires (an xy location). The
	 setter will be called by the ObjectAnimator given the 'buttonLoc'
	 property string.
	 */
	public void setButtonLoc(PathPoint newLoc){
		button.setTranslationX(newLoc.x);
		button.setTranslationY(newLoc.y);
	}


}
