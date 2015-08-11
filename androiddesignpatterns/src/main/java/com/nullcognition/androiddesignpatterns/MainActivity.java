package com.nullcognition.androiddesignpatterns;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.nullcognition.androiddesignpatterns.fragment.AdapterFragments;

// Specific to Lolipop - 5.0 api 21


public class MainActivity extends AppCompatActivity{

	AdapterFragments adapterFragments;
	ViewPager        viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		adapterFragments = new AdapterFragments(getSupportFragmentManager());

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(adapterFragments);
		viewPager.setCurrentItem(0);

		// Watch for button clicks.
		final int numElements = adapterFragments.getCount();

		Button button = (Button) findViewById(R.id.btnPrev);
		button.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				int current = viewPager.getCurrentItem();
				if(current > 0){ viewPager.setCurrentItem(--current); }
			}
		});
		button = (Button) findViewById(R.id.btnNext);
		button.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				int current = viewPager.getCurrentItem();
				if(current < numElements - 1){ viewPager.setCurrentItem(++current); }
			}
		});
	}
}
