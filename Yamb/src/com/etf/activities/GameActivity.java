package com.etf.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.etf.controller.Controler;
import com.etf.yamb.R;

//Fragment u kome se odvija igrica
public class GameActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_fragment_layout);
		Controler.getControler().setGameActivity(this);
		Controler.getControler().initAndStartGame();
		Log.e("CREATE:", "TR");
	}

	public void onBackPressed()
	{
		new AlertDialog.Builder(this).setTitle(R.string.quit_title).setMessage(R.string.quit_message).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				// fm.popBackStack();
				finish();
			}
		}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				// do nothing
			}
		}).setIcon(android.R.drawable.ic_dialog_alert).show();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		try
		{

			Controler.getControler().getBoard().disableShaking();
		} catch (Exception e)
		{
		}

	}

	@Override
	protected void onResume()
	{
		Log.e("Resume", "true");
		super.onResume();

		try
		{
			
			if (Controler.getControler().getBrojBacanja() != 3)
			{
				Controler.getControler().getBoard().enableShaking();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
