package com.nullcognition.androiddesignpatterns;
// ersin 11/08/15 Copyright (c) 2015+ All rights reserved.


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import static com.nullcognition.androiddesignpatterns.Utils.RADIOHEAD_ALBUM_URLS;
import static com.nullcognition.androiddesignpatterns.Utils.RADIOHEAD_ALBUM_NAMES;



public class
		MainActivity extends AppCompatActivity{

	private static final String  TAG   = "MainActivity";
	private static final boolean DEBUG = true;

	static final String EXTRA_CURRENT_ITEM_POSITION = "extra_current_item_position";
	static final String EXTRA_OLD_ITEM_POSITION     = "extra_old_item_position";

	private RecyclerView mRecyclerView;
	private Bundle       mTmpState;
	private boolean      mIsReentering;

	private final SharedElementCallback mCallback = new SharedElementCallback(){
		@Override
		public void onMapSharedElements(List<String> names, Map<String, View> sharedElements){
			LOG("onMapSharedElements(List<String>, Map<String, View>)", mIsReentering);
			if(mIsReentering){
				int oldPosition = mTmpState.getInt(EXTRA_OLD_ITEM_POSITION);
				int currentPosition = mTmpState.getInt(EXTRA_CURRENT_ITEM_POSITION);
				if(currentPosition != oldPosition){
					// If currentPosition != oldPosition the user must have swiped to a different
					// page in the DetailsActivity. We must update the shared element so that the
					// correct one falls into place.
					String newTransitionName = RADIOHEAD_ALBUM_NAMES[currentPosition];
					View newSharedView = mRecyclerView.findViewWithTag(newTransitionName);
					if(newSharedView != null){
						names.clear();
						names.add(newTransitionName);
						sharedElements.clear();
						sharedElements.put(newTransitionName, newSharedView);
					}
				}
				mTmpState = null;
			}

			if(!mIsReentering){
				View navigationBar = findViewById(android.R.id.navigationBarBackground);
				View statusBar = findViewById(android.R.id.statusBarBackground);
				int actionBarId = getResources().getIdentifier("action_bar_container", "id", "android");
				View actionBar = findViewById(actionBarId);

				if(navigationBar != null){
					names.add(navigationBar.getTransitionName());
					sharedElements.put(navigationBar.getTransitionName(), navigationBar);
				}
				if(statusBar != null){
					names.add(statusBar.getTransitionName());
					sharedElements.put(statusBar.getTransitionName(), statusBar);
				}
				if(actionBar != null){
					actionBar.setTransitionName("actionBar");
					names.add(actionBar.getTransitionName());
					sharedElements.put(actionBar.getTransitionName(), actionBar);
				}
			}
			else{
				names.remove(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
				sharedElements.remove(Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME);
				names.remove(Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
				sharedElements.remove(Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME);
				names.remove("actionBar");
				sharedElements.remove("actionBar");
			}

			LOG("=== names: " + names.toString(), mIsReentering);
			LOG("=== sharedElements: " + Utils.setToString(sharedElements.keySet()), mIsReentering);
		}

		@Override
		public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements,
		                                 List<View> sharedElementSnapshots){
			LOG("onSharedElementStart(List<String>, List<View>, List<View>)", mIsReentering);
			logSharedElementsInfo(sharedElementNames, sharedElements);
		}

		@Override
		public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements,
		                               List<View> sharedElementSnapshots){
			LOG("onSharedElementEnd(List<String>, List<View>, List<View>)", mIsReentering);
			logSharedElementsInfo(sharedElementNames, sharedElements);
		}

		private void logSharedElementsInfo(List<String> names, List<View> sharedElements){
			LOG("=== names: " + names.toString(), mIsReentering);
			for(View view : sharedElements){
				int[] loc = new int[2];
				view.getLocationInWindow(loc);
				Log.i(TAG, "=== " + view.getTransitionName() + ": " + "(" + loc[0] + ", " + loc[1] + ")");
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setExitSharedElementCallback(mCallback);

		Resources res = getResources();
		mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new GridLayoutManager(this, res.getInteger(R.integer.num_columns)));
		mRecyclerView.setAdapter(new CardAdapter());
	}

	private class CardAdapter extends RecyclerView.Adapter<CardHolder>{

		@Override
		public CardHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
			LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
			return new CardHolder(inflater.inflate(R.layout.image_card, viewGroup, false));
		}

		@Override
		public void onBindViewHolder(CardHolder holder, int position){
			holder.bind(position);
		}

		@Override
		public int getItemCount(){
			return RADIOHEAD_ALBUM_URLS.length;
		}
	}


	private class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

		private final ImageView mImage;
		private       int       mPosition;

		public CardHolder(View itemView){
			super(itemView);
			itemView.setOnClickListener(this);
			mImage = (ImageView) itemView.findViewById(R.id.image);
		}

		public void bind(int position){
			Picasso.with(mImage.getContext()).load(RADIOHEAD_ALBUM_URLS[position]).into(mImage);
			mImage.setTransitionName(RADIOHEAD_ALBUM_NAMES[position]);
			mImage.setTag(RADIOHEAD_ALBUM_NAMES[position]);
			mPosition = position;
		}

		@Override
		public void onClick(View v){
			mIsReentering = false;
			LOG("startActivity(Intent, Bundle)", false);
			Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
			intent.putExtra(EXTRA_CURRENT_ITEM_POSITION, mPosition);
			startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(
					MainActivity.this, mImage, mImage.getTransitionName()).toBundle());
		}
	}

	@Override
	public void onActivityReenter(int requestCode, Intent data){
		LOG("onActivityReenter(int, Intent)", true);
		super.onActivityReenter(requestCode, data);
		mIsReentering = true;
		mTmpState = new Bundle(data.getExtras());
		int oldPosition     = mTmpState.getInt(EXTRA_OLD_ITEM_POSITION);
		int currentPosition = mTmpState.getInt(EXTRA_CURRENT_ITEM_POSITION);
		if(oldPosition != currentPosition){
			mRecyclerView.scrollToPosition(currentPosition);
		}
		postponeEnterTransition();
		mRecyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){
			@Override
			public boolean onPreDraw(){
				mRecyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
				// TODO: hack! not sure why, but requesting a layout pass is necessary in order to fix re-mapping + scrolling glitches!
				mRecyclerView.requestLayout();
				startPostponedEnterTransition();
				return true;
			}
		});
	}

	private static void LOG(String message, boolean isReentering){
		if(DEBUG){
			Log.i(TAG, String.format("%s: %s", isReentering ? "REENTERING" : "EXITING", message));
		}
	}
}
