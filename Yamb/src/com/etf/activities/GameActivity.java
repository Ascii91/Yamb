package com.etf.activities;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		LocationListener locationListener = new MyLocationListener();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
		Calendar c = Calendar.getInstance();

		Log.e("time", "" + c);
		Log.e("Loc", "" + locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
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

	public class MyLocationListener implements LocationListener

	{

		@Override
		public void onLocationChanged(Location loc)

		{
			Log.e("Lon", "" + loc.getLongitude());
			Log.e("lat", "" + loc.getLatitude());
		}

		@Override
		public void onProviderDisabled(String provider)

		{

		}

		@Override
		public void onProviderEnabled(String provider)

		{
			Log.e("enabled", "true");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras)

		{

		}

	}/* End of Class MyLocationListener */

}/* End of UseGps Activity */
