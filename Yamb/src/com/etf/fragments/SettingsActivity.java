package com.etf.fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.etf.controller.Controler;
import com.etf.utils.Constants;
import com.etf.yamb.R;

//Fragment za podešavanje igrice
public class SettingsActivity extends Activity implements OnClickListener, OnSeekBarChangeListener
{

	private SeekBar seekBar;
	private Button sacuvaj;
	private Button nazad;
	private TextView progressText;
	private CheckBox cb;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_fragment_layout);
//		try
//		{
//			//Controler.getControler().getBoard().disableShaking();
//		} catch (Exception e)
//		{
//		}
		seekBar = (SeekBar) findViewById(R.id.seekBar1);
		sacuvaj = (Button) findViewById(R.id.btn_sacuvaj);
		nazad = (Button) findViewById(R.id.btn_nazad);
		progressText = (TextView) findViewById(R.id.progress_text);
		cb = (CheckBox) findViewById(R.id.check_box1);

		sacuvaj.setOnClickListener(this);
		nazad.setOnClickListener(this);
		seekBar.setOnSeekBarChangeListener(this);

		SharedPreferences prefs = getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);

		int progress = prefs.getInt(Constants.OSETLJIVOST, -1);
		boolean sound = prefs.getBoolean(Constants.ZVUK, true);

		// prvi put, nema inicijalnih podešavanja
		if (progress == -1)
		{
			progress = 50;
			sound = true;

			cb.setChecked(sound);
			seekBar.setProgress(progress);
			save();

		}

		cb.setChecked(sound);
		seekBar.setProgress(progress);

	}

	@Override
	public void onClick(View v)
	{

		switch (v.getId())
		{
		case R.id.btn_sacuvaj:
			save();

			this.finish();
			break;
		case R.id.btn_nazad:

			this.finish();
			break;
		}

	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	{
		progressText.setText("" + progress + "%");

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{

	}

	/**
	 * Èuvanje svih podataka
	 */
	private void save()
	{
		saveProgressToPrefs(Constants.SETTINGS, Constants.OSETLJIVOST, seekBar.getProgress());
		saveSoundToPrefs(Constants.SETTINGS, Constants.ZVUK, cb.isChecked());
	//	Controler.getControler().resetControler();
		Toast.makeText(this, R.string.changes_saved, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Èuvanje podešavanja zvuka u preferencama
	 * 
	 * @param prefsName
	 * @param prefsValue
	 * @param value
	 */
	private void saveSoundToPrefs(String prefsName, String prefsValue, boolean value)
	{
		SharedPreferences settings = getSharedPreferences(prefsName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(prefsValue, value);
		editor.commit();

	}

	/**
	 * Èuvanje osetljivosti kockica u preference
	 * 
	 * @param prefsName
	 * @param prefsValue
	 * @param value
	 */
	private void saveProgressToPrefs(String prefsName, String prefsValue, int value)
	{
		SharedPreferences settings = getSharedPreferences(prefsName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(prefsValue, value);
		editor.commit();

	}
}
