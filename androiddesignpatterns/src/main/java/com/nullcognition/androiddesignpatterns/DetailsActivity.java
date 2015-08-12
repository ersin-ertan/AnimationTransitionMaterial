package com.nullcognition.androiddesignpatterns;
// ersin 11/08/15 Copyright (c) 2015+ All rights reserved.


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import java.util.List;
import java.util.Map;

import static com.nullcognition.androiddesignpatterns.MainActivity.EXTRA_CURRENT_ITEM_POSITION;
import static com.nullcognition.androiddesignpatterns.MainActivity.EXTRA_OLD_ITEM_POSITION;

public class DetailsActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

	private static final String  TAG   = "DetailsActivity";
	private static final boolean DEBUG = true;

	private static final String STATE_CURRENT_POSITION = "state_current_position";
	private static final String STATE_OLD_POSITION     = "state_old_position";

	private DetailsFragmentPagerAdapter mAdapter;
	private int                         mCurrentPosition;
	private int                         mOriginalPosition;
	private boolean                     mIsReturning;

	private final SharedElementCallback mCallback = new SharedElementCallback(){
		@Override
		public void onMapSharedElements(List<String> names, Map<String, View> sharedElements){
			LOG("onMapSharedElements(List<String>, Map<String, View>)", mIsReturning);
			if(mIsReturning){
				View sharedView = mAdapter.getCurrentDetailsFragment().getSharedElement();
				if(sharedView == null){
					// If shared view is null, then it has likely been scrolled off screen and
					// recycled. In this case we cancel the shared element transition by
					// removing the shared elements from the shared elements map.
					names.clear();
					sharedElements.clear();
				}
				else if(mCurrentPosition != mOriginalPosition){
					names.clear();
					sharedElements.clear();
					names.add(sharedView.getTransitionName());
					sharedElements.put(sharedView.getTransitionName(), sharedView);
				}
			}

			LOG("=== names: " + names.toString(), mIsReturning);
			LOG("=== sharedElements: " + Utils.setToString(sharedElements.keySet()), mIsReturning);
		}

		@Override
		public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements,
		                                 List<View> sharedElementSnapshots){
			LOG("onSharedElementStart(List<String>, List<View>, List<View>)", mIsReturning);
			if(!mIsReturning){
				getWindow().setEnterTransition(makeEnterTransition(getSharedElement(sharedElements)));
			}
		}

		@Override
		public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements,
		                               List<View> sharedElementSnapshots){
			LOG("onSharedElementEnd(List<String>, List<View>, List<View>)", mIsReturning);
			if(mIsReturning){
				getWindow().setReturnTransition(makeReturnTransition());
			}
		}

		private View getSharedElement(List<View> sharedElements){
			for(final View view : sharedElements){
				if(view instanceof ImageView){
					return view;
				}
			}
			return null;
		}
	};

	private Transition makeEnterTransition(View sharedElement){
		View rootView = mAdapter.getCurrentDetailsFragment().getView();
		assert rootView != null;

		TransitionSet enterTransition = new TransitionSet();

		// Play a circular reveal animation starting beneath the shared element.
		Transition circularReveal = new CircularReveal(sharedElement);
		circularReveal.addTarget(rootView.findViewById(R.id.reveal_container));
		enterTransition.addTransition(circularReveal);

		// Slide the cards in through the bottom of the screen.
		Transition cardSlide = new Slide(Gravity.BOTTOM);
		cardSlide.addTarget(rootView.findViewById(R.id.text_container));
		enterTransition.addTransition(cardSlide);

		// Don't fade the navigation/status bars.
		Transition fade = new Fade();
		fade.excludeTarget(android.R.id.navigationBarBackground, true);
		fade.excludeTarget(android.R.id.statusBarBackground, true);
		enterTransition.addTransition(fade);

		final Resources res             = getResources();
		final ImageView backgroundImage = (ImageView) rootView.findViewById(R.id.background_image);
		backgroundImage.setAlpha(0f);
		enterTransition.addListener(new TransitionListenerAdapter(){
			@Override
			public void onTransitionEnd(Transition transition){
				backgroundImage.animate().alpha(1f).setDuration(res.getInteger(R.integer.image_background_fade_millis));
			}
		});

		enterTransition.setDuration(getResources().getInteger(R.integer.transition_duration_millis));
		return enterTransition;
	}

	private Transition makeReturnTransition(){
		View rootView = mAdapter.getCurrentDetailsFragment().getView();
		assert rootView != null;

		TransitionSet returnTransition = new TransitionSet();

		// Slide and fade the circular reveal container off the top of the screen.
		TransitionSet slideFade = new TransitionSet();
		slideFade.addTarget(rootView.findViewById(R.id.reveal_container));
		slideFade.addTransition(new Slide(Gravity.TOP));
		slideFade.addTransition(new Fade());
		returnTransition.addTransition(slideFade);

		// Slide the cards off the bottom of the screen.
		Transition cardSlide = new Slide(Gravity.BOTTOM);
		cardSlide.addTarget(rootView.findViewById(R.id.text_container));
		returnTransition.addTransition(cardSlide);

		returnTransition.setDuration(getResources().getInteger(R.integer.transition_duration_millis));
		return returnTransition;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		postponeEnterTransition();
		setEnterSharedElementCallback(mCallback);

		if(savedInstanceState == null){
			mCurrentPosition = getIntent().getExtras().getInt(EXTRA_CURRENT_ITEM_POSITION);
			mOriginalPosition = mCurrentPosition;
		}
		else{
			mCurrentPosition = savedInstanceState.getInt(STATE_CURRENT_POSITION);
			mOriginalPosition = savedInstanceState.getInt(STATE_OLD_POSITION);
		}

		mAdapter = new DetailsFragmentPagerAdapter(getSupportFragmentManager());
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(mAdapter);
		pager.setOnPageChangeListener(this);
		pager.setCurrentItem(mCurrentPosition);

		getWindow().getSharedElementEnterTransition().setDuration(getResources().getInteger(R.integer.transition_duration_millis));
	}

	@Override
	public void onSaveInstanceState(@NonNull Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_CURRENT_POSITION, mCurrentPosition);
		outState.putInt(STATE_OLD_POSITION, mOriginalPosition);
	}

	@Override
	public void finishAfterTransition(){
		LOG("finishAfterTransition()", true);
		mIsReturning = true;
		getWindow().setReturnTransition(makeReturnTransition());
		Intent data = new Intent();
		data.putExtra(EXTRA_OLD_ITEM_POSITION, getIntent().getExtras().getInt(EXTRA_CURRENT_ITEM_POSITION));
		data.putExtra(EXTRA_CURRENT_ITEM_POSITION, mCurrentPosition);
		setResult(RESULT_OK, data);
		super.finishAfterTransition();
	}

	@Override
	public void onPageSelected(int position){
		mCurrentPosition = position;
	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
		// Do nothing.
	}

	@Override
	public void onPageScrollStateChanged(int state){
		// Do nothing.
	}

	private static void LOG(String message, boolean isReturning){
		if(DEBUG){
			Log.i(TAG, String.format("%s: %s", isReturning ? "RETURNING" : "ENTERING", message));
		}
	}
}
