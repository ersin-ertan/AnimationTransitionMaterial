package com.nullcognition.animationtransitionmaterial.slidingfragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.nullcognition.animationtransitionmaterial.R;

public class SlidingFragments extends AppCompatActivity implements OnTextFragmentAnimationEndListener, FragmentManager.OnBackStackChangedListener{

	ImageFragment imageFragment;
	TextFragment  textFragment;
	View          darkHoverView;

	boolean didSlideOut, isAnimating;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sliding_fragment);

		darkHoverView = findViewById(R.id.dark_hover_view);
		darkHoverView.setAlpha(0);

		FragmentManager fm = getSupportFragmentManager();

		imageFragment = (ImageFragment) fm.findFragmentById(R.id.move_fragment);
		textFragment = new TextFragment();
		fm.addOnBackStackChangedListener(this);

		textFragment.setOnTextFragmentAnimationEnd(this);
		textFragment.setClickListener(clickListener);
		imageFragment.setClickListener(clickListener);
		darkHoverView.setOnClickListener(clickListener);

	}

	View.OnClickListener clickListener = new View.OnClickListener(){

		@Override
		public void onClick(final View v){
			switchFragments();
		}
	};

	// toggle between two frament states, an call animations, entry/exit of animation of text are in R.animator
	// animations of image fragment are in slideback forward methods, translucent dark hover view must fade
	// in at the same time as the image fragment animates into the background which is difficult to time properly
	// because setCustomAnimations method can only modify the two frag in the transaction
	private void switchFragments(){
		if(isAnimating){return;}
		isAnimating = true;
		if(didSlideOut){
			didSlideOut = false;
			getSupportFragmentManager().popBackStack();
		}
		else{
			didSlideOut = true;

			Animator.AnimatorListener listener = new Animator.AnimatorListener(){
				@Override
				public void onAnimationStart(final Animator animation){ }
				@Override
				public void onAnimationEnd(final Animator animation){
					FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
					transaction.setCustomAnimations(R.anim.slide_fragment_in, 0, 0, R.anim.slide_fragment_out);
					transaction.add(R.id.move_to_back_container, textFragment);
					transaction.addToBackStack(null);
					transaction.commit();
				}
				@Override
				public void onAnimationCancel(final Animator animation){ }
				@Override
				public void onAnimationRepeat(final Animator animation){ }
			};
			slideBack(listener);
		}
	}

	@Override
	public void onBackStackChanged(){
		if(!didSlideOut){
			slideForward(null);
		}
	}

	public void slideBack(Animator.AnimatorListener listener){
		View movingFragmentView = imageFragment.getView();

		PropertyValuesHolder rotateX                = PropertyValuesHolder.ofFloat("rotationX", 40f);
		PropertyValuesHolder scaleX                 = PropertyValuesHolder.ofFloat("scaleX", 0.8f);
		PropertyValuesHolder scaleY                 = PropertyValuesHolder.ofFloat("scaleY", 0.8f);
		ObjectAnimator       movingFragmentAnimator = ObjectAnimator.ofPropertyValuesHolder(movingFragmentView, rotateX, scaleX, scaleY);

		ObjectAnimator darkHoverViewAnimator = ObjectAnimator.ofFloat(darkHoverView, "alpha", 0.0f, 0.5f);

		ObjectAnimator movingFragmentRotator = ObjectAnimator.ofFloat(movingFragmentView, "rotationX", 0);
		movingFragmentRotator.setStartDelay(getResources().getInteger(R.integer.half_slide_up_down_duration));

		AnimatorSet s = new AnimatorSet();
		s.playTogether(movingFragmentAnimator, darkHoverViewAnimator, movingFragmentRotator);
		s.addListener(listener);
		s.start();
	}

	/**
	 This method animates the image fragment into the foreground by both
	 scaling and rotating the fragment's view, while also removing the
	 previously added translucent dark hover view. Upon the completion of
	 this animation, the image fragment regains focus since this method is
	 called from the onBackStackChanged method.
	 */
	public void slideForward(Animator.AnimatorListener listener){
		View movingFragmentView = imageFragment.getView();

		PropertyValuesHolder rotateX                = PropertyValuesHolder.ofFloat("rotationX", 40f);
		PropertyValuesHolder scaleX                 = PropertyValuesHolder.ofFloat("scaleX", 1.0f);
		PropertyValuesHolder scaleY                 = PropertyValuesHolder.ofFloat("scaleY", 1.0f);
		ObjectAnimator       movingFragmentAnimator = ObjectAnimator.ofPropertyValuesHolder(movingFragmentView, rotateX, scaleX, scaleY);

		ObjectAnimator darkHoverViewAnimator = ObjectAnimator.ofFloat(darkHoverView, "alpha", 0.5f, 0.0f);

		ObjectAnimator movingFragmentRotator = ObjectAnimator.ofFloat(movingFragmentView, "rotationX", 0);
		movingFragmentRotator.setStartDelay(getResources().getInteger(R.integer.half_slide_up_down_duration));

		AnimatorSet s = new AnimatorSet();
		s.playTogether(movingFragmentAnimator, movingFragmentRotator, darkHoverViewAnimator);
		s.setStartDelay(getResources().getInteger(R.integer.slide_up_down_duration));
		s.addListener(new AnimatorListenerAdapter(){
			@Override
			public void onAnimationEnd(Animator animation){isAnimating = false;}
		});
		s.start();
	}

	public void onAnimationEnd(){ isAnimating = false;}

}
