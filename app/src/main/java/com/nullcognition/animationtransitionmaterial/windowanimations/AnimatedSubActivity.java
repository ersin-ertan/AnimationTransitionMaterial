package com.nullcognition.animationtransitionmaterial.windowanimations;

import android.app.Activity;
import android.os.Bundle;

import com.nullcognition.animationtransitionmaterial.R;

// provides custom animation behavior. When this activity is exited, the user will see the
// behavior specified in the overridePendingTransition() call.

public class AnimatedSubActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_window_anim_sub);
	}

	@Override
	public void finish(){
		super.finish();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}
}
