package com.nullcognition.animationtransitionmaterial;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;

public class BounceImageDrawables extends AppCompatActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		setLayout();
	}
	private void setLayout(){
//		setContentView(R.layout.activity_bounce); // uncomment to see this example
		setContentView(R.layout.gridlayout);
		newGrid();
	}
	private void newGrid(){
		final GridLayout gridLayout = (GridLayout) findViewById(R.id.gridlayout);

		gridLayout.removeAllViews();

		int total  = 35;
		int column = 5;
		int row    = total / column;
		gridLayout.setColumnCount(column);
		gridLayout.setRowCount(row + 1);
		for(int i = 0, c = 0, r = 0; i < total; i++, c++){
			if(c == column){
				c = 0;
				r++;
			}
			final int finalC = c;
			final int finalR = r;
			GridLayout.LayoutParams param = new GridLayout.LayoutParams();
			param.height = GridLayout.LayoutParams.WRAP_CONTENT;
			param.width = GridLayout.LayoutParams.WRAP_CONTENT;
			param.rightMargin = 5;
			param.topMargin = 5;
			param.setGravity(Gravity.CENTER);

			ImageView imageView = new DrawableMaster().getImageView();

			param.columnSpec = GridLayout.spec(finalC);
			param.rowSpec = GridLayout.spec(finalR);
			imageView.setLayoutParams(param);
			gridLayout.addView(imageView);
		}
	}


	class DrawableMaster{

		DrawableMaster(){}

		private ImageView getImageView(){
			AnimationDrawable ad = new AnimationDrawable();
			for(int ii = 0; ii < 80; ii++){
				ad.addFrame(getDrawableForFrameNumber(ii), 50);
			}

			ad.setOneShot(false);
			ImageView iv = new ImageView(getApplicationContext());
			iv.setImageDrawable(ad);
			ad.start();
			return iv;
		}

		private BitmapDrawable getDrawableForFrameNumber(int frameNumber){

			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			int height = displaymetrics.heightPixels;
			int width  = displaymetrics.widthPixels;

			Bitmap bitmap = Bitmap.createBitmap(width / 6, height / 10, Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			canvas.drawColor(Color.GRAY);

			Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			paint.setTextSize(bitmap.getWidth() / 8);
			paint.setColor(Color.BLACK);

			canvas.drawText("Frame:" + frameNumber, bitmap.getWidth() / 10, bitmap.getHeight() / 2 + paint.getTextSize() / 2, paint);
			// remember it starts at the top left of the text, thus move it down by half of the size of the text
			return new BitmapDrawable(getResources(), bitmap);
		}

	}

}
