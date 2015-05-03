package com.etf.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.etf.view.Board;
import com.etf.view.Dices;

public class GameProgress extends AsyncTask<Void, Void, Void>
{

	@Override
	protected Void doInBackground(Void... params)
	{
		Log.e("MOVE NO", "" + Controler.getControler().getBrojBacanja());
		State stanje = Controler.getControler().getStanje();
		switch (stanje)
		{
		case POCETNO_STANJE: // u ovom stanju je tap to sledece stanje za
								// sada
			Controler.getControler().getBoard().resetSugestions();
			Controler.getControler().getBoard().resetDices();
			Controler.getControler().getBoard().showDialog();
			Controler.getControler().setStanje(State.WAIT_USER);
		case WAIT_USER: // ceka korisnika da mucka ili da unese potez
			// sada imamo 2 podstanja moze da odigra ako hoce i mora da
			// odigra

			int brojBacanja = Controler.getControler().getBrojBacanja();
			switch (brojBacanja)
			{
			case 1: // zavrsen drugi potez;
				Board board = Controler.getControler().getBoard();
				board.colorFields(1);

				break; // ofarbaj polja u zuto
			case 2:
				Board board2 = Controler.getControler().getBoard();
				board2.colorFields(2);

				break;
			case 3: // ofarbaj polja u crveno i disabluj shaking do kraja
					// unosa
				Board board3 = Controler.getControler().getBoard();
				board3.disableShaking();
				board3.colorFields(3);
				break;

			}
		default:
			break;

		}
		return null;
		// return;
		// try
		// {
		//
		// this.wait();
		// } catch (Exception e)
		// {
		// Log.e("probudjen", "true");
		// }

	}

	// }

}
