package com.etf.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class ShakeEventManager implements SensorEventListener
{

	private SensorManager sManager;
	private Sensor s;
 
	private static final int MOV_COUNTS = 2; 
	private static final int MOV_THRESHOLD = 5;
	private static final float ALPHA = 0.9F;
	private static final int SHAKE_WINDOW_TIME_INTERVAL = 800; // milliseconds

	// Gravity force on x,y,z axis
	private float gravity[] = new float[3];

	private int counter;
	private long firstMovTime;
	private ShakeListener listener;
	private boolean shakeInProgres = false;
	private int counter2 = 0;

	public ShakeEventManager()
	{
	}

	public void setListener(ShakeListener listener)
	{
		this.listener = listener;
	}

	public void init(Context ctx)
	{
		sManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
		s = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		register();
	}

	public void register()
	{
		sManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent)
	{
		float maxAcc = calcMaxAcceleration(sensorEvent);

		if (maxAcc >= MOV_THRESHOLD)
		{
			Log.e("Counter", "" + counter);
			counter2 = 0;
			//
			if (counter <= 0)// ako muckanje nije u toku zapocni novo merenje
								// muckanja
			{
				counter++;

				firstMovTime = System.currentTimeMillis();

			} else
			{
				long now = System.currentTimeMillis();
				if ((now - firstMovTime) < SHAKE_WINDOW_TIME_INTERVAL)
				{

					counter++;

				} else
				{
					if (!shakeInProgres)
					{
						Log.e("Zapoceto Muckanje", "true");
						shakeInProgres = true;
					}
					return;
				}

			}
		} else
		{
			if (shakeInProgres)
			{
				counter2++;
				Log.e("Counter2", "" + counter2);

				// ako je vreme isteklo
				if (counter2 > 3)
				{
					resetAllData();
				}
			} else
			{
				counter--;
				if (counter < 0)
				{
					counter = 0;
				}
			}
		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int i)
	{
	}

	public void deregister()
	{
		sManager.unregisterListener(this);
	}

	private float calcMaxAcceleration(SensorEvent event)
	{
		gravity[0] = calcGravityForce(event.values[0], 0);
		gravity[1] = calcGravityForce(event.values[1], 1);
		gravity[2] = calcGravityForce(event.values[2], 2);

		float accX = event.values[0] - gravity[0];
		float accY = event.values[1] - gravity[1];
		float accZ = event.values[2] - gravity[2];

		float max1 = Math.max(accX, accY);
		return Math.max(max1, accZ);
	}

	// Low pass filter
	private float calcGravityForce(float currentVal, int index)
	{
		return ALPHA * gravity[index] + (1 - ALPHA) * currentVal;
	}

	private void resetAllData()
	{

		Log.e("End", "Shake");
		counter = 0;
		counter2 = 0;
		shakeInProgres = false;
	}

	public static interface ShakeListener
	{
		public void onShake();
	}
}