package com.nullcognition.animationtransitionmaterial.slidingfragments;
// ersin 08/08/15 Copyright (c) 2015+ All rights reserved.

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nullcognition.animationtransitionmaterial.R;

public class ImageFragment extends Fragment{

	View.OnClickListener clickListener;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.image_fragment, container, false);
		view.setOnClickListener(clickListener);
		return view;
	}

	public void setClickListener(View.OnClickListener clickListener){
		this.clickListener = clickListener;
	}
}
