package com.etf.utils;

import android.content.Context;

import com.etf.view.Dices;

public class DiceRoller implements ShakeEventManager.ShakeListener
{

    private ShakeEventManager sd;
    private Dices             dices;

    public DiceRoller(Dices dices)
    {
        this.dices = dices;
    }

    public void register(Context context)
    {
        sd = new ShakeEventManager();
        sd.setListener(this);
        sd.init(context);
        sd.register();
    }

    public void deregister()
    {
        if (sd != null)
        {
            sd.deregister();
        }
    }

    @Override
    public void onShake()
    {
        dices.startShaking();

    }

    @Override
    public void onStopShaking()
    {
        dices.stopShaking();

    }

    public void setSensitivity(int sens)
    {
        sd.setSensitivity(sens);

    }

}
