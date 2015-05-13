package com.etf.simulation;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;

import com.etf.db.YambDb;

public class SimulationProgress extends AsyncTask<Context, Void, Void>
{

    @Override
    protected Void doInBackground(Context... params)
    {
        // Step 1 get games
        YambDb yb = new YambDb(params[0]);
        List<Igra> igre = yb.getIgre();

        // Select specific game

        Igra igra = igre.get(0);

        
        for(Bacanje b: igra.getLista())
        {
            
         //Bacanje 1
          
           //Controler.getControler().initAndStartGame();
            
            
             //prikazi animaciju i postavi bacanje 1 kockice
            //ukoliko postoji bacanje 2 i selected 1 je aktivno selektuj 1 i predji na bacanje 2
            //ukoliko ne postoji upisi rezultat i continue
            
         
          //Bacanje2 
            //prikazi animaciju i postavi bacanje 2 kockice
            //ukoliko postoji bacanje 3 i selected 2 je aktivno selektuj 2 i predji na bacanje 2
            //ukoliko ne postoji upisi rezultat i continue
            
            //Bacanje 3
            //prikazi animaciju i postavi bacanje 3 kockice
            //upisi rezultat i continue
            
     
            
            
            
        }
        // ispisi cestitku i popuj stek    
         
            
            
            
            



        return null;
    }

    @Override
    protected void onPostExecute(Void result)
    {
        super.onPostExecute(result);

        // TODO: Ovde ce se ispisati poruka da je partija zavrsena

    }

}
