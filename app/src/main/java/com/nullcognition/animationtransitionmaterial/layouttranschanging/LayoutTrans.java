package com.nullcognition.animationtransitionmaterial.layouttranschanging;

import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.nullcognition.animationtransitionmaterial.R;

public class LayoutTrans extends AppCompatActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_layout_trans);

		final Button       addButton    = (Button) findViewById(R.id.addButton);
		final Button       removeButton = (Button) findViewById(R.id.removeButton);
		final LinearLayout container    = (LinearLayout) findViewById(R.id.container);
		final Context      context      = this;

		for(int i = 0; i < 2; ++i){ container.addView(new ColoredView(this));}

		addButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(final View v){
				container.addView(new ColoredView(context), 1);
			}
		});

		removeButton.setOnClickListener(
				new View.OnClickListener(){
					@Override
					public void onClick(final View v){
						if(container.getChildCount() > 0){
							container.removeViewAt(Math.min(1, container.getChildCount() - 1));
							// this is used to remove the value of 1 each time except when the child count
							// is 1 thus the -1 to remove the 0th element
						}
					}
				}
		);
		// assumes a layouttransition is set on teh container, because container has attribute animateLayoutChanges
		// is set as true, can also call setLayoutTransition(new LayoutTransition()) to set it on any container
		LayoutTransition transition = container.getLayoutTransition();

		// in jelly bean you can monitor the whole container for all layout changes and animate all of them
		transition.enableTransitionType(LayoutTransition.CHANGING);
	}

	private static class ColoredView extends View{

		private boolean                   expanded         = false;
		private LinearLayout.LayoutParams compressedParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
		private LinearLayout.LayoutParams expandedParams   = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);

		public ColoredView(final Context context){
			super(context);

			int red   = (int) (Math.random() * 128 + 127);
			int green = (int) (Math.random() * 128 + 127);
			int blue  = (int) (Math.random() * 128 + 127);
			int color = 0xff << 24 | (red << 16) | (green << 8) | blue;

			setBackgroundColor(color);
			setLayoutParams(compressedParams);

			setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v){
					// Size changes will cause a LayoutTransition animation if the CHANGING
					// transition is enabled
					setLayoutParams(expanded ? compressedParams : expandedParams);
					expanded = !expanded;
					requestLayout();
				}
			});
		}
	}

}
