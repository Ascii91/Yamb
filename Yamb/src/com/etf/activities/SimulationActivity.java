package com.etf.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.etf.controller.Controler;
import com.etf.db.YambDb;
import com.etf.simulation.Igra;
import com.etf.yamb.R;

public class SimulationActivity extends Activity implements OnClickListener
{

    private int       redniBrojBacanja = 0;
    private Igra      igra;
    private ImageView leftArrow;
    private ImageView rightArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulation_activity_layout);

        leftArrow = (ImageView) findViewById(R.id.imageView1);
        rightArrow = (ImageView) findViewById(R.id.imageView2);

        leftArrow.setOnClickListener(this);
        rightArrow.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        int pos = extras.getInt("position");

        // Step 1 get games
        YambDb yb = new YambDb(this);
        List<Igra> igre = yb.getIgre();
        this.igra = igre.get(pos); 
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Controler.getControler().setSimulation(true);

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Controler.getControler().setSimulation(false);
    }

    public int getRedniBrojBacanja()
    {
        return redniBrojBacanja;
    }

    public void setRedniBrojBacanja(int redniBrojBacanja)
    {
        this.redniBrojBacanja = redniBrojBacanja;
    }

    public Igra getIgra()
    {
        return igra;
    }

    public void setIgra(Igra igra)
    {
        this.igra = igra;
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.imageView1 :// left
                break;
            case R.id.imageView2 : // right
                break;
            default :
                break;
        }

    }
}
