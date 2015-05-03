package com.etf.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeEventManager implements SensorEventListener
{

	private SensorManager sManager;
	private Sensor s;

	private static final int MOV_THRESHOLD = 2;
	private static final float ALPHA = 0.6F;
	private static final int SHAKE_WINDOW_TIME_INTERVAL = 600; // milliseconds
	private static final float MOV_LIMIT = 99;

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

	}

	public void register()
	{
		sManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onSensorChanged(SensorEvent sensorEvent)
	{
		float maxAcc = calcMaxAcceleration(sensorEvent);
		if (maxAcc >= MOV_THRESHOLD && maxAcc < MOV_LIMIT)
		{

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
					// listener.onShake();
				} else
				{
					if (!shakeInProgres)
					{
						// Log.e("Zapoceto Muckanje", "true");
						shakeInProgres = true;
						listener.onShake();
					}
					return;
				}

			}
		} else
		{
			if (shakeInProgres)
			{
				counter2++;

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
		if (gravity[0] == 0 || gravity[1] == 0 || gravity[2] == 0)
		{
			gravity[0] = event.values[0];
			gravity[1] = event.values[1];
			gravity[2] = event.values[2];
			return 0;
		}
		gravity[0] = calcGravityForce(event.values[0], 0);
		gravity[1] = calcGravityForce(event.values[1], 1);
		gravity[2] = calcGravityForce(event.values[2], 2);

		float accX = event.values[0] - gravity[0];
		float accY = event.values[1] - gravity[1];
		float accZ = event.values[2] - gravity[2];

		float max1 = Math.max(accX, accY);
		return Math.max(max1, accZ);
	}

	private float calcGravityForce(float currentVal, int index)
	{
		return ALPHA * gravity[index] + (1 - ALPHA) * currentVal;
	}

	private void resetAllData()
	{

		counter = 0;
		counter2 = 0;
		shakeInProgres = false;
		listener.onStopShaking();

	}

	public static interface ShakeListener
	{
		public void onStopShaking();

		public void onShake();
	}
}