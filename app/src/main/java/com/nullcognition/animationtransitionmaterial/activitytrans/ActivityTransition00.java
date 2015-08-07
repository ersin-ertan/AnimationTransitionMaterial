package com.nullcognition.animationtransitionmaterial.activitytrans;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import com.nullcognition.animationtransitionmaterial.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityTransition00 extends AppCompatActivity{

	private static final String PACKAGE        = "com.example.android.activityanim";
	static               float  sAnimatorScale = 1;

	GridLayout mGridLayout;
	HashMap<ImageView, PictureData> mPicturesData = new HashMap<ImageView, PictureData>();
	BitmapUtils                     mBitmapUtils  = new BitmapUtils();

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity_transition00);

		// Grayscale filter used on all thumbnails
		ColorMatrix grayMatrix = new ColorMatrix();
		grayMatrix.setSaturation(0);
		ColorMatrixColorFilter grayscaleFilter = new ColorMatrixColorFilter(grayMatrix);

		mGridLayout = (GridLayout) findViewById(R.id.gridLayout);
		mGridLayout.setColumnCount(3);
		mGridLayout.setUseDefaultMargins(true);

		// add all photo thumbnails to layout
		Resources              resources = getResources();
		ArrayList<PictureData> pictures  = mBitmapUtils.loadPhotos(resources);
		for(int i = 0; i < pictures.size(); ++i){
			PictureData pictureData = pictures.get(i);
			BitmapDrawable thumbnailDrawable = new BitmapDrawable(resources, pictureData.thumbnail);
			thumbnailDrawable.setColorFilter(grayscaleFilter);
			ImageView imageView = new ImageView(this);
			imageView.setOnClickListener(thumbnailClickListener);
			imageView.setImageDrawable(thumbnailDrawable);
			mPicturesData.put(imageView, pictureData);
			mGridLayout.addView(imageView);
		}
	}


	/**
	 When the user clicks a thumbnail, bundle up information about it and launch the
	 details activity.
	 */
	private View.OnClickListener thumbnailClickListener = new View.OnClickListener(){

		@Override
		public void onClick(View v){
			// Interesting data to pass across are the thumbnail size/location, the
			// resourceId of the source bitmap, the picture description, and the
			// orientation (to avoid returning back to an obsolete configuration if
			// the device rotates again in the meantime)
			int[] screenLocation = new int[2];
			v.getLocationOnScreen(screenLocation);
			PictureData info = mPicturesData.get(v);
			Intent subActivity = new Intent(ActivityTransition00.this,
					ActivityTransition01.class);
			int orientation = getResources().getConfiguration().orientation;
			subActivity.
					           putExtra(PACKAGE + ".orientation", orientation).
					           putExtra(PACKAGE + ".resourceId", info.resourceId).
					           putExtra(PACKAGE + ".left", screenLocation[0]).
					           putExtra(PACKAGE + ".top", screenLocation[1]).
					           putExtra(PACKAGE + ".width", v.getWidth()).
					           putExtra(PACKAGE + ".height", v.getHeight()).
					           putExtra(PACKAGE + ".description", info.description);
			startActivity(subActivity);

			// Override transitions: we don't want the normal window animation in addition
			// to our custom one
			overridePendingTransition(0, 0);
		}
	};

}
