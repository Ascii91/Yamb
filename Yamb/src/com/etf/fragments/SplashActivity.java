package com.etf.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.etf.controller.Controler;
import com.etf.utils.Constants;
import com.etf.yamb.R;

public class SplashActivity extends Activity implements OnClickListener, android.widget.CompoundButton.OnCheckedChangeListener
{

	private Button buttonIgraj;
	private Button buttonPodesavanja;
	private Button buttonStatistika;
	private CheckBox cb1;
	private CheckBox cb2;
	private CheckBox cb3;
	private CheckBox cb4;
	private int brojIgraca = 1;

	// Polja za unošenje imena
	private EditText player1name;
	private EditText player2name;
	private EditText player3name;
	private EditText player4name;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_fragment_layout);
		buttonIgraj = (Button) findViewById(R.id.button_igraj);
		buttonPodesavanja = (Button) findViewById(R.id.button_podesavanja);
		buttonStatistika = (Button) findViewById(R.id.button_statistika);
		brojIgraca = 1;

		buttonIgraj.setOnClickListener(this);
		buttonPodesavanja.setOnClickListener(this);
		buttonStatistika.setOnClickListener(this);
	}

	private void openPlayerSelectDialog()
	{
		final Dialog dialog = new Dialog(this);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.player_number_select_layout);

		cb1 = (CheckBox) dialog.findViewById(R.id.check_box1);
		cb2 = (CheckBox) dialog.findViewById(R.id.checkBox2);
		cb3 = (CheckBox) dialog.findViewById(R.id.checkBox3);
		cb4 = (CheckBox) dialog.findViewById(R.id.checkBox4);

		cb1.setOnCheckedChangeListener(this);
		cb2.setOnCheckedChangeListener(this);
		cb3.setOnCheckedChangeListener(this);
		cb4.setOnCheckedChangeListener(this);

		player1name = (EditText) dialog.findViewById(R.id.editText1);
		player2name = (EditText) dialog.findViewById(R.id.editText2);
		player3name = (EditText) dialog.findViewById(R.id.editText3);
		player4name = (EditText) dialog.findViewById(R.id.editText4);

		brojIgraca = 0;
		cb1.setChecked(true);
		cb2.setChecked(false);
		cb3.setChecked(false);
		cb4.setChecked(false);
		cb1.setTextColor(Color.BLACK);
		cb2.setTextColor(Color.GRAY);
		cb3.setTextColor(Color.GRAY);
		cb4.setTextColor(Color.GRAY);

		player1name.setVisibility(View.VISIBLE);
		player2name.setVisibility(View.INVISIBLE);
		player3name.setVisibility(View.INVISIBLE);
		player4name.setVisibility(View.INVISIBLE);

		Button okButton = (Button) dialog.findViewById(R.id.ok_button);
		Button cancelButton = (Button) dialog.findViewById(R.id.cancel_button);
		okButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				if (brojIgraca <= 0)
				{
					Toast.makeText(dialog.getContext(), (R.string.minimum_players), Toast.LENGTH_SHORT).show();
					return;
				}

				saveToPreferenceAndStartGame();
				dialog.dismiss();
			}
		});

		cancelButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				dialog.dismiss();
			}
		});

		dialog.show();
	}

	@Override
	public void onClick(View v)
	{
		// FragmentManager fm = getActivity().getFragmentManager();
		// Fragment fragment = null;

		switch (v.getId())
		{
		case R.id.button_igraj:
			Controler.getControler().resetControler();
			openPlayerSelectDialog();
			break;
		case R.id.button_podesavanja:

			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);

			break;
		case R.id.button_statistika:

			Intent i2 = new Intent(this, StatisticsActivity.class);
			startActivity(i2);
			break;
		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{

		switch (buttonView.getId())
		{

		case R.id.check_box1:

			if (cb1.isChecked())
			{
				brojIgraca++;
				cb1.setChecked(true);
				cb1.setTextColor(Color.BLACK);
				player1name.setVisibility(View.VISIBLE);
			} else
			{
				brojIgraca--;
				cb1.setChecked(false);
				cb1.setTextColor(Color.GRAY);
				player1name.setVisibility(View.INVISIBLE);
			}

			break;
		case R.id.checkBox2:

			if (cb2.isChecked())
			{
				brojIgraca++;
				cb2.setChecked(true);
				cb2.setTextColor(Color.BLACK);
				player2name.setVisibility(View.VISIBLE);
			} else
			{
				brojIgraca--;
				cb2.setChecked(false);
				cb2.setTextColor(Color.GRAY);
				player2name.setVisibility(View.INVISIBLE);
			}

			break;
		case R.id.checkBox3:

			if (cb3.isChecked())
			{
				brojIgraca++;
				cb3.setChecked(true);
				cb3.setTextColor(Color.BLACK);
				player3name.setVisibility(View.VISIBLE);
			} else
			{
				brojIgraca--;
				cb3.setChecked(false);
				cb3.setTextColor(Color.GRAY);
				player3name.setVisibility(View.INVISIBLE);
			}

			break;
		case R.id.checkBox4:

			if (cb4.isChecked())
			{
				brojIgraca++;
				cb4.setChecked(true);
				player4name.setVisibility(View.VISIBLE);
				cb4.setTextColor(Color.BLACK);
			} else
			{
				brojIgraca--;
				player4name.setVisibility(View.INVISIBLE);
				cb4.setTextColor(Color.GRAY);
				cb4.setChecked(false);

			}

			break;
		default:

			break;
		}

	}

	private void saveToPreferenceAndStartGame()
	{
		String NO_PLAYER = "noPlayer";
		String pl1Name = NO_PLAYER;
		String pl2Name = NO_PLAYER;
		String pl3Name = NO_PLAYER;
		String pl4Name = NO_PLAYER;

		if (cb1.isChecked())
		{
			pl1Name = player1name.getText().toString();
		}
		if (cb2.isChecked())
		{
			pl2Name = player2name.getText().toString();
		}
		if (cb3.isChecked())
		{
			pl3Name = player3name.getText().toString();
		}
		if (cb4.isChecked())
		{
			pl4Name = player4name.getText().toString();
		}

		saveToPrefs(Constants.IGRA, Constants.BROJ_IGRACA, "" + brojIgraca);
		saveToPrefs(Constants.IGRA, Constants.IGRAC1, pl1Name);
		saveToPrefs(Constants.IGRA, Constants.IGRAC2, pl2Name);
		saveToPrefs(Constants.IGRA, Constants.IGRAC3, pl3Name);
		saveToPrefs(Constants.IGRA, Constants.IGRAC4, pl4Name);

		Intent i = new Intent(this, GameActivity.class);
		startActivity(i);

	}

	private void saveToPrefs(String prefsName, String prefsValue, String value)
	{
		SharedPreferences settings = getSharedPreferences(prefsName, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(prefsValue, value);
		editor.commit();

	}

}
