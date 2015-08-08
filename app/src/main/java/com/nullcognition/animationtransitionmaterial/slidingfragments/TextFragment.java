package com.nullcognition.animationtransitionmaterial.slidingfragments;
// ersin 08/08/15 Copyright (c) 2015+ All rights reserved.

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

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
	public Animation onCreateAnimation(final int transit, final boolean enter, final int nextAnim){
		super.onCreateAnimation(transit, enter, nextAnim);
		int             id   = enter ? R.anim.slide_fragment_in : R.anim.slide_fragment_out;
		final Animation anim = AnimationUtils.loadAnimation(getActivity(), id);
		if(enter){
			anim.setAnimationListener(new Animation.AnimationListener(){
				public void onAnimationEnd(Animation animation){ listener.onAnimationEnd();}
				public void onAnimationRepeat(Animation animation){ }
				public void onAnimationStart(Animation animation){ }
			});
		}
		return anim;
	}

	public void setOnTextFragmentAnimationEnd(OnTextFragmentAnimationEndListener listener){
		this.listener = listener;
	}


}
