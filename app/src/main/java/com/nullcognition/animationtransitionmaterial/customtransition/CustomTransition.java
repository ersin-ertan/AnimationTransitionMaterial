package com.nullcognition.animationtransitionmaterial.customtransition;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.nullcognition.animationtransitionmaterial.R;

public class CustomTransition extends AppCompatActivity
		implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_transition);

		initViews(savedInstanceState);
	}


	private static final String STATE_CURRENT_SCENE = "current_scene";
	private static final String TAG                 = "CustomTransitionFragment";
	private Scene[]    mScenes;
	private int        mCurrentScene;
	private Transition mTransition;

	public CustomTransition(){}

	public void initViews(Bundle savedInstanceState){

		FrameLayout container = (FrameLayout) findViewById(R.id.container);

		findViewById(R.id.show_next_scene).setOnClickListener(this);

		if(null != savedInstanceState){
			mCurrentScene = savedInstanceState.getInt(STATE_CURRENT_SCENE);
		}
		mScenes = new Scene[]{
				Scene.getSceneForLayout(container, R.layout.ct_scene01, this),
				Scene.getSceneForLayout(container, R.layout.ct_scene02, this),
				Scene.getSceneForLayout(container, R.layout.ct_scene03, this),
		};

		mTransition = new ChangeColor();
		TransitionManager.go(mScenes[mCurrentScene % mScenes.length]);
	}

	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_CURRENT_SCENE, mCurrentScene);
	}

	@Override
	public void onClick(View v){
		switch(v.getId()){
			case R.id.show_next_scene:{
				mCurrentScene = (mCurrentScene + 1) % mScenes.length;
				Log.i(TAG, "Trans:scene:" + mCurrentScene);
				TransitionManager.go(mScenes[mCurrentScene], mTransition);
				break;
			}
		}
	}
}
