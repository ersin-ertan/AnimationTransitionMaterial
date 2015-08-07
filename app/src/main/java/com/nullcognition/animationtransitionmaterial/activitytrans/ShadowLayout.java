package com.nullcognition.animationtransitionmaterial.activitytrans;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

// ersin 07/08/15 Copyright (c) 2015+ All rights reserved.
public class ShadowLayout extends RelativeLayout{

	Paint mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	float  mShadowDepth;
	Bitmap mShadowBitmap;
	static final int   BLUR_RADIUS     = 6;
	static final RectF sShadowRectF    = new RectF(0, 0, 200, 200);
	static final Rect  sShadowRect     = new Rect(0, 0, 200 + 2 * BLUR_RADIUS, 200 + 2 * BLUR_RADIUS);
	static       RectF tempShadowRectF = new RectF(0, 0, 0, 0);

	public ShadowLayout(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
		init();
	}

	public ShadowLayout(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}

	public ShadowLayout(Context context){
		super(context);
		init();
	}

	/**
	 Called by the constructors - sets up the drawing parameters for the drop shadow.
	 */
	private void init(){
		mShadowPaint.setColor(Color.BLACK);
		mShadowPaint.setStyle(Paint.Style.FILL);
		setWillNotDraw(false);
		mShadowBitmap = Bitmap.createBitmap(sShadowRect.width(),
				sShadowRect.height(), Bitmap.Config.ARGB_8888);
		Canvas c = new Canvas(mShadowBitmap);
		mShadowPaint.setMaskFilter(new BlurMaskFilter(BLUR_RADIUS, BlurMaskFilter.Blur.NORMAL));
		c.translate(BLUR_RADIUS, BLUR_RADIUS);
		c.drawRoundRect(sShadowRectF, sShadowRectF.width() / 40,
				sShadowRectF.height() / 40, mShadowPaint);
	}

	/**
	 The "depth" factor determines the offset distance and opacity of the shadow (shadows that
	 are further away from the source are offset greater and are more translucent).

	 @param depth
	 */
	public void setShadowDepth(float depth){
		if(depth != mShadowDepth){
			mShadowDepth = depth;
			mShadowPaint.setAlpha((int) (100 + 150 * (1 - mShadowDepth)));
			invalidate(); // We need to redraw when the shadow attributes change
		}
	}

	/**
	 Overriding onDraw allows us to draw shadows behind every child of this container.
	 onDraw() is called to draw a layout's content before the children are drawn, so the
	 shadows will be drawn first, behind the children (which is what we want).
	 */
	@Override
	protected void onDraw(Canvas canvas){
		for(int i = 0; i < getChildCount(); ++i){
			View child = getChildAt(i);
			if(child.getVisibility() != View.VISIBLE || child.getAlpha() == 0){
				continue;
			}
			int depthFactor = (int) (80 * mShadowDepth);
			canvas.save();
			canvas.translate(child.getLeft() + depthFactor,
					child.getTop() + depthFactor);
			canvas.concat(child.getMatrix());
			tempShadowRectF.right = child.getWidth();
			tempShadowRectF.bottom = child.getHeight();
			canvas.drawBitmap(mShadowBitmap, sShadowRect, tempShadowRectF, mShadowPaint);
			canvas.restore();
		}
	}


}
