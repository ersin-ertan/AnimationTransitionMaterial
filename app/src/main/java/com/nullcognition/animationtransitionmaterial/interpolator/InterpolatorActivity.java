package com.nullcognition.animationtransitionmaterial.interpolator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.nullcognition.animationtransitionmaterial.R;

public class InterpolatorActivity extends AppCompatActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_interpolator);

		if(savedInstanceState == null){
			getSupportFragmentManager().beginTransaction()
			                           .replace(R.id.sample_content_fragment, new InterpolatorFragment())
			                           .commit();
		}
	}
}
