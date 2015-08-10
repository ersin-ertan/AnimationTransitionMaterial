package com.nullcognition.animationtransitionmaterial.interpolator;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.nullcognition.animationtransitionmaterial.R;

public class InterpolatorFragment extends Fragment{

	private View         view;
	private Spinner      interpolatorSpinner;
	private SeekBar      durationSeekbar;
	private TextView     durationLabel;
	private Interpolator interpolators[];
	private Path         pathIn; // shrinking ing from 100 to 20%
	private Path         pathOut;
	private              boolean isOut               = false;
	private static final int     INITIAL_DURATION_MS = 750;

	public InterpolatorFragment(){
	}


	// perhaps not the best thing to do, but is used for the other android.R.interpolator.fast...
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState){
		View   rootView = inflater.inflate(R.layout.fragment_interpolator, container, false);
		Button button   = (Button) rootView.findViewById(R.id.animateButton);
		button.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View view){
				// Interpolator selected in the spinner
				Interpolator interpolator = interpolators[interpolatorSpinner.getSelectedItemPosition()];
				long         duration     = durationSeekbar.getProgress();
				Path         path         = isOut ? pathIn : pathOut;

				startAnimation(interpolator, duration, path);
			}
		});

		durationLabel = (TextView) rootView.findViewById(R.id.durationLabel);

		interpolators = new Interpolator[]{
				AnimationUtils.loadInterpolator(getActivity(), android.R.interpolator.linear),
				AnimationUtils.loadInterpolator(getActivity(), android.R.interpolator.fast_out_linear_in),
				AnimationUtils.loadInterpolator(getActivity(), android.R.interpolator.fast_out_slow_in),
				AnimationUtils.loadInterpolator(getActivity(), android.R.interpolator.linear_out_slow_in)
		};
		String[] interpolatorNames = getResources().getStringArray(R.array.interpolator_names);

		interpolatorSpinner = (Spinner) rootView.findViewById(R.id.interpolatorSpinner);

		ArrayAdapter<String> spinnerAdapter =
				new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, interpolatorNames);

		interpolatorSpinner.setAdapter(spinnerAdapter);

		durationSeekbar = (SeekBar) rootView.findViewById(R.id.durationSeek);
		durationSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b){
				durationLabel.setText(getResources().getString(R.string.animation_duration, i));
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar){}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar){}
		});

		durationSeekbar.setProgress(INITIAL_DURATION_MS);

		view = rootView.findViewById(R.id.square);

		pathIn = new Path();
		pathIn.moveTo(0.2f, 0.2f);
		pathIn.lineTo(1f, 1f);

		pathOut = new Path();
		pathOut.moveTo(1f, 1f);
		pathOut.lineTo(0.2f, 0.2f);
		return rootView;
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public ObjectAnimator startAnimation(Interpolator interpolator, long duration, Path path){
		ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.SCALE_X, View.SCALE_Y, path);

		animator.setDuration(duration);
		animator.setInterpolator(interpolator);

		animator.start();

		return animator;
	}

	public Interpolator[] getInterpolators(){
		return interpolators;
	}

	public Path getPathIn(){
		return pathIn;
	}

	public Path getPathOut(){
		return pathOut;
	}
}
