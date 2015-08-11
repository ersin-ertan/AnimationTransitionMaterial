package com.nullcognition.animationtransitionmaterial.activitytransloli;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nullcognition.animationtransitionmaterial.R;
import com.squareup.picasso.Picasso;

public class ActivitySceneTransition extends AppCompatActivity
		implements AdapterView.OnItemClickListener{

	private GridView    gridView;
	private GridAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid);

		gridView = (GridView) findViewById(R.id.grid);
		gridView.setOnItemClickListener(this);

		adapter = new GridAdapter();
		gridView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id){

		Item item = (Item) parent.getItemAtPosition(position);

		Intent intent = new Intent(this, DetailActivity.class);
		intent.putExtra(DetailActivity.EXTRA_PARAM_ID, item.getId());

		ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
				new Pair<View, String>(view.findViewById(R.id.imageview_item), DetailActivity.VIEW_NAME_HEADER_IMAGE),
				new Pair<View, String>(view.findViewById(R.id.textview_name), DetailActivity.VIEW_NAME_HEADER_TITLE));

		ActivityCompat.startActivity(this, intent, activityOptions.toBundle());
	}

	private class GridAdapter extends BaseAdapter{

		@Override
		public int getCount(){
			return Item.ITEMS.length;
		}

		@Override
		public Item getItem(int position){
			return Item.ITEMS[position];
		}

		@Override
		public long getItemId(int position){
			return getItem(position).getId();
		}

		@Override
		public View getView(int position, View view, ViewGroup viewGroup){
			if(view == null){
				view = getLayoutInflater().inflate(R.layout.grid_item, viewGroup, false);
			}

			final Item item = getItem(position);

			// Load the thumbnail image

			ImageView image = (ImageView) view.findViewById(R.id.imageview_item);
			Picasso.with(image.getContext()).load(item.getThumbnailUrl()).into(image);

			// Set the TextView's contents
			TextView name = (TextView) view.findViewById(R.id.textview_name);
			name.setText(item.getName());

			return view;
		}
	}

}

