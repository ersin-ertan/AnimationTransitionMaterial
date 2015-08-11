package com.nullcognition.androiddesignpatterns.fragment;
// ersin 11/08/15 Copyright (c) 2015+ All rights reserved.

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.fragmentargs.FragmentArgs;

public class Fragment00 extends Fragment implements FragmentStorable{

	public int layoutResId;

	public Fragment00(){}

	@Override
	public void onCreate(final Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		FragmentArgs.inject(this);
	}

	@Nullable
	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState){
		return inflater.inflate(layoutResId, container, false);
	}

}
