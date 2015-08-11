package com.nullcognition.androiddesignpatterns.fragment;
// ersin 11/08/15 Copyright (c) 2015+ All rights reserved.

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.nullcognition.androiddesignpatterns.fragment.fragments.FragmentObjectTransition;
import com.nullcognition.androiddesignpatterns.fragment.fragments.FragmentTest2;

public class AdapterFragments extends FragmentPagerAdapter{

	public AdapterFragments(final FragmentManager fm){
		super(fm);
	}

	@Override
	public Fragment getItem(final int position){
		return (Fragment) StoreFragments.fragments.get(position);
	}

	@Override
	public int getCount(){
		return StoreFragments.fragments.size();
	}


	public static class StoreFragments{

		public static SparseArray<FragmentStorable> fragments = new SparseArray<>();

		static{
			fragments.put(0, new FragmentObjectTransition());
			fragments.put(1, new FragmentTest2());
		}

	}
}
