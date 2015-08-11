package com.nullcognition.androiddesignpatterns.fragment.fragments;
// ersin 11/08/15 Copyright (c) 2015+ All rights reserved.

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nullcognition.androiddesignpatterns.R;
import com.nullcognition.androiddesignpatterns.fragment.Fragment00;

import java.security.InvalidParameterException;

public class FragmentObjectTransition extends Fragment00 implements View.OnClickListener{

	private ViewGroup mRootView;
	private View      mRedBox, mGreenBox, mBlueBox, mBlackBox;

	@Override
	public void onCreate(final Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		layoutResId = R.layout.fragment_object_transition;

	}

	@Override
	public void onViewCreated(final View view, final Bundle savedInstanceState){
		mRootView = (ViewGroup) getActivity().findViewById(R.id.layout_root_view);

		Button run = (Button) getActivity().findViewById(R.id.btnRun);
		run.setOnClickListener(this);

		mRedBox = getActivity().findViewById(R.id.red_box);
		mGreenBox = getActivity().findViewById(R.id.green_box);
		mBlueBox = getActivity().findViewById(R.id.blue_box);
		mBlackBox = getActivity().findViewById(R.id.black_box);
	}

	int transitionNum = 0;

	@Override
	public void onClick(View v){
		switch(transitionNum){
			case 0:
				TransitionManager.beginDelayedTransition(mRootView, new Fade());
				toggleVisibility(mRedBox, mGreenBox, mBlueBox, mBlackBox);
				transitionNum++;
				break;
			case 1:
				TransitionManager.beginDelayedTransition(mRootView, new Slide());
				toggleVisibility(mRedBox, mGreenBox, mBlueBox, mBlackBox);
				transitionNum++;
				break;
			case 2:
				TransitionManager.beginDelayedTransition(mRootView, new Explode());
				toggleVisibility(mRedBox, mGreenBox, mBlueBox, mBlackBox);
				transitionNum++;
				break;
			case 3:
				TransitionManager.beginDelayedTransition(mRootView, new AutoTransition());
				toggleVisibility(mRedBox, mGreenBox, mBlueBox, mBlackBox);
				transitionNum++;
				break;
			case 4:
				TransitionManager.beginDelayedTransition(mRootView, new ChangeBounds());
				toggleVisibility(mRedBox, mGreenBox, mBlueBox, mBlackBox);
				transitionNum++;
				break;
			case 5:
				TransitionManager.beginDelayedTransition(mRootView, new ChangeImageTransform());
				toggleVisibility(mRedBox, mGreenBox, mBlueBox, mBlackBox);
				transitionNum++;
				break;
			case 6:
				TransitionManager.beginDelayedTransition(mRootView, new ChangeTransform());
				toggleVisibility(mRedBox, mGreenBox, mBlueBox, mBlackBox);
				transitionNum++;
				break;
			case 7:
				TransitionManager.beginDelayedTransition(mRootView, new ChangeClipBounds());
				toggleVisibility(mRedBox, mGreenBox, mBlueBox, mBlackBox);
				transitionNum++;
				break;
			case 8:
				TransitionManager.beginDelayedTransition(mRootView, new Transition(){
					@Override
					public void captureStartValues(final TransitionValues transitionValues){

					}

					@Override
					public void captureEndValues(final TransitionValues transitionValues){

					}
				});
				toggleVisibility(mRedBox, mGreenBox, mBlueBox, mBlackBox);
				transitionNum = 0;
				break;
			default:
				throw new InvalidParameterException();
		}
	}

	private static void toggleVisibility(View... views){
		for(View view : views){
			boolean isVisible = view.getVisibility() == View.VISIBLE;
			view.setVisibility(isVisible ? View.INVISIBLE : View.VISIBLE);
		}
	}
}
