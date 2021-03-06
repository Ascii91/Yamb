package com.etf.utils;

import android.content.Context;

import com.etf.controller.Controler;
import com.etf.view.Dices;

//Klasa za vr�enje kockica
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
        if (Controler.getControler().isSimulation() == false)
        {
            sd = new ShakeEventManager();
            sd.setListener(this);
            sd.init(context);
            sd.register();
        }
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
