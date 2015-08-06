package com.nullcognition.animationtransitionmaterial.views;
// ersin 06/08/15 Copyright (c) 2015+ All rights reserved.

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.nullcognition.animationtransitionmaterial.R;

public abstract class V extends View{

	Bitmap mBitmap;
	Paint paint = new Paint();
	int mShapeX, mShapeY;
	int mShapeW, mShapeH;


	public V(final Context context){
		super(context);
		setupShape();
	}
	public V(final Context context, final AttributeSet attrs){
		super(context, attrs);
		setupShape();

	}
	public V(final Context context, final AttributeSet attrs, final int defStyleAttr){
		super(context, attrs, defStyleAttr);
		setupShape();
	}
	private void setupShape(){
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.abc_btn_check_to_on_mtrl_015);
		mShapeW = mBitmap.getWidth();
		mShapeH = mBitmap.getHeight();

		setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(final View v){
				startAnimation();
			}
		});

	}

	public void setmShapeX(int shapeX){
		int minX = mShapeX;
		int maxX = mShapeX + mShapeW;
		mShapeX = shapeX;
		minX = Math.min(mShapeX, minX);
		maxX = Math.max(mShapeX + mShapeW, maxX);
		invalidate(minX, mShapeY, maxX, mShapeY + mShapeH);
	}

	public void setShapeY(int shapeY){
		int minY = mShapeY;
		int maxY = mShapeY + mShapeH;
		mShapeY = shapeY;
		minY = Math.min(mShapeY, minY);
		maxY = Math.max(mShapeY + mShapeH, maxY);
		invalidate(mShapeX, minY, mShapeX + mShapeW, maxY);
	}

	@Override
	protected void onSizeChanged(final int w, final int h, final int oldw, final int oldh){
		super.onSizeChanged(w, h, oldw, oldh);
		mShapeX = (w - mBitmap.getWidth()) / 2;
		mShapeY = 0;
	}

	@Override
	protected void onDraw(final Canvas canvas){
		super.onDraw(canvas);
		canvas.drawBitmap(mBitmap, mShapeX, mShapeY, paint);
	}


	public abstract void startAnimation();

	public abstract ValueAnimator getValueAnimator();


}
