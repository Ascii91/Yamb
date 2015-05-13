package com.etf.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class ShakeEventManager implements SensorEventListener
{

    private SensorManager      sManager;
    private Sensor             s;

    private static final int   MOV_THRESHOLD              = 2;           // Donja granica
    private static float       ALPHA                      = 1.0F;
    private static final int   SHAKE_WINDOW_TIME_INTERVAL = 600;         // milliseconds
    private static final float MOV_LIMIT                  = 99;          // Gornja granica

    // Gravity force on x,y,z axis
    private float              gravity[]                  = new float[3];

    private long               firstMovTime;
    private ShakeListener      listener;
    private boolean            shakeInProgres             = false;
    private int                smirenost;
    private int                aktivnost;
    private Context            context;

    public ShakeEventManager()
    {}

    public void setListener(ShakeListener listener)
    {
        this.listener = listener;
    }

    public void init(Context ctx)
    {
        this.setContext(ctx);
        sManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        s = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    public void register()
    {
        sManager.registerListener(this, s, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        float maxAcc = calcMaxAcceleration(sensorEvent);// Izracunavamo trenutno ubrzanje

        // Zaletanje kockica :D
        if (maxAcc >= MOV_THRESHOLD && maxAcc < MOV_LIMIT)
        {
            smirenost = 0;
          
            if (aktivnost <= 0)// ako muckanje nije u toku zapocni novo merenje  muckanja (Ovo se desava kad se prvi put udje ovde )
            {
                aktivnost++;

                firstMovTime = System.currentTimeMillis();
            }
            else
            {
                long now = System.currentTimeMillis();
              
                if ((now - firstMovTime) < SHAKE_WINDOW_TIME_INTERVAL) // ukoliko nismosmo muckali vise od SHAKE WINDOW TIME INTERVALa
                {

                    aktivnost++;

                }
                else  // ako smo muckali onoliko koliko treba
                {
                    if (!shakeInProgres) // ukoliko nije pocelo vec postavi ga na pocelo i obavesti listener da je pocelo muckanje
                    {
                        shakeInProgres = true;
                        listener.onShake(); // obavestavamo da je pocelo muækanje
                    }
                    return;  // u toku muckanja pozivace se ovaj  return 
                }

            }
        }
        else
        // Smirivanje kockica
        {
            //Ako je u toku muckanje
            if (shakeInProgres)
            {
                smirenost++;

                if (smirenost > 3) // ako su se kockice dovoljno smirile
                {
                    resetAllData();
                }
            }
            //Ako nije u toku muckanje 
            else
            {
                aktivnost--;
                if (aktivnost < 0)// obezbedjuje da ne ode u minus
                {
                    aktivnost = 0;
                }
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {}

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

    /**
     * Racunanje gravitacione sile
     * @param currentVal
     * @param index
     * @return
     */
    private float calcGravityForce(float currentVal, int index)
    {
        return ALPHA * gravity[index] + (1 - ALPHA) * currentVal;
    }

    private void resetAllData()
    {

        aktivnost = 0;
        smirenost = 0;
        shakeInProgres = false;
        listener.onStopShaking();

    }

    public Context getContext()
    {
        return context;
    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public static interface ShakeListener
    {
        public void onStopShaking();

        public void onShake();
    }

    public void setSensitivity(int sens)
    {
        ALPHA = 1.0f * (((float) sens) / 100);
    }
}