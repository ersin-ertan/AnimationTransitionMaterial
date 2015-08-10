package com.nullcognition.animationtransitionmaterial.transition;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.nullcognition.animationtransitionmaterial.R;

public class TransitionActivity extends AppCompatActivity
		implements RadioGroup.OnCheckedChangeListener{

	private Scene scene01;
	private Scene scene02;
	private Scene scene03;

	private TransitionManager transitionManagerForScene03;
	private ViewGroup              sceneRoot;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transition);

		initViews();
	}
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void initViews(){

		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.select_scene);
		radioGroup.setOnCheckedChangeListener(this);
		sceneRoot = (ViewGroup) findViewById(R.id.scene_root);

		scene01 = new Scene(sceneRoot, (View)sceneRoot.findViewById(R.id.container));

		scene02 = Scene.getSceneForLayout(sceneRoot, R.layout.scene02, this);

		scene03 = Scene.getSceneForLayout(sceneRoot, R.layout.scene03, this);

		transitionManagerForScene03 =
				TransitionInflater.from(this).inflateTransitionManager(
						R.transition.scene03_transition_manager, sceneRoot);
	}

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public void onCheckedChanged(final RadioGroup group, final int checkedId){
		switch(checkedId){
			case R.id.select_scene_1:{
				// BEGIN_INCLUDE(transition_simple)
				// You can start an automatic transition with TransitionManager.go().
				TransitionManager.go(scene01);
				// END_INCLUDE(transition_simple)
				break;
			}
			case R.id.select_scene_2:{
				TransitionManager.go(scene02);
				break;
			}
			case R.id.select_scene_3:{
				// BEGIN_INCLUDE(transition_custom)
				// You can also start a transition with a custom TransitionManager.
				transitionManagerForScene03.transitionTo(scene03);
				// END_INCLUDE(transition_custom)
				break;
			}
			case R.id.select_scene_4:{
				// BEGIN_INCLUDE(transition_dynamic)
				// Alternatively, transition can be invoked dynamically without a Scene.
				// For this, we first call TransitionManager.beginDelayedTransition().

				TransitionManager.beginDelayedTransition(sceneRoot);

				// Then, we can just change view properties as usual.
				View square = sceneRoot.findViewById(R.id.transition_square);
				ViewGroup.LayoutParams params = square.getLayoutParams();
				int newSize = getResources().getDimensionPixelSize(R.dimen.square_size_expanded);
				params.width = newSize;
				params.height = newSize;
				square.setLayoutParams(params);
				// END_INCLUDE(transition_dynamic)
				break;
			}
		}
	}
}
