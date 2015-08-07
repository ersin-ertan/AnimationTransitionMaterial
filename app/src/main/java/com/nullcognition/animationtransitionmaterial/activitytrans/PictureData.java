package com.nullcognition.animationtransitionmaterial.activitytrans;
// ersin 07/08/15 Copyright (c) 2015+ All rights reserved.

import android.graphics.Bitmap;
public class PictureData {
	int resourceId;
	String description;
	Bitmap thumbnail;

	public PictureData(int resourceId, String description, Bitmap thumbnail) {
		this.resourceId = resourceId;
		this.description = description;
		this.thumbnail = thumbnail;
	}
}
