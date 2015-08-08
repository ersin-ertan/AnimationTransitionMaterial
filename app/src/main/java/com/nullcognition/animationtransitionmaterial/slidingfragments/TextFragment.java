package com.nullcognition.animationtransitionmaterial.slidingfragments;
// ersin 08/08/15 Copyright (c) 2015+ All rights reserved.

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nullcognition.animationtransitionmaterial.R;

public class TextFragment extends Fragment{

	View.OnClickListener               clickListener;
	OnTextFragmentAnimationEndListener listener;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.text_fragment, container, false);
		view.setOnClickListener(clickListener);
		return view;
	}

	public void setClickListener(View.OnClickListener clickListener){
		this.clickListener = clickListener;
	}

	@Override
	public Animator onCreateAnimator(int transit, boolean enter, int nextAnim){
		int            id   = enter ? R.animator.slide_fragment_in : R.animator.slide_fragment_out;
		final Animator anim = AnimatorInflater.loadAnimator(getActivity(), id);
		if(enter){
			anim.addListener(new AnimatorListenerAdapter(){
				@Override
				public void onAnimationEnd(Animator animation){
					listener.onAnimationEnd();
				}
			});
		}
		return anim;
	}


	public void setOnTextFragmentAnimationEnd(OnTextFragmentAnimationEndListener listener){
		this.listener = listener;
	}


}
