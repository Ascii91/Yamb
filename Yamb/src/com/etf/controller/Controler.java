package com.etf.controller;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.SharedPreferences;

import com.etf.activities.GameActivity;
import com.etf.model.FieldData;
import com.etf.simulation.Bacanje;
import com.etf.simulation.Igra;
import com.etf.utils.Constants;
import com.etf.utils.State;
import com.etf.view.Board;
import com.etf.view.Dices;

public class Controler
{
	private static Controler controler;

	private Igra igra;
	private Board board;
	private State stanje = State.POCETNO_STANJE;
	private GameActivity gameActivity;
	private int brojBacanja;
	private int[] values = new int[6];
	private int totalMoves;
	private String playerName;
	private int playerNumber;
	private int numOfPlayers;
	private boolean isNajava;
	private int score = 0;
	private Dices dices;
	private boolean simulation;

	private boolean shouldSix = false;
	
	
	private FieldData lastFieldData1;
	private FieldData lastFieldData2;
	private FieldData lastFieldData3;
	private FieldData lastFieldData4;

	private float volume = 0.2f;
	
	public void resetControler()
	{
		controler = new Controler();
		if (igra != null)
		{
			igra.setLista(new ArrayList<Bacanje>());
		}
	}

	public int getNumOfPlayers()
	{
		return numOfPlayers;
	}

	public Board getBoard()
	{
		return board;
	}

	public static void setControler(Controler controler)
	{
		Controler.controler = controler;
	}
    //Add comment
	public static Controler getControler()
	{
		if (controler == null)
		{
			controler = new Controler();
		}
		return controler;
	}

	/**
	 * Inicijalizuje i startuje igru
	 */
	public void initAndStartGame()
	{
		SharedPreferences prefs = getControler().getBoard().getContext().getSharedPreferences(Constants.IGRA, 0);
		String pl1 = prefs.getString(Constants.IGRAC1, "-");
		String pl2 = prefs.getString(Constants.IGRAC2, "-");
		String pl3 = prefs.getString(Constants.IGRAC3, "-");
		String pl4 = prefs.getString(Constants.IGRAC4, "-");
		long vreme = System.currentTimeMillis();
		int trajanje = 0;
		int brojIgraca = Integer.parseInt(prefs.getString(Constants.BROJ_IGRACA, "1"));

		igra = new Igra(pl1, pl2, pl3, pl4, vreme, trajanje, brojIgraca);

		
		
		new GameProgress().execute();

	}

	public State getStanje()
	{
		return stanje;
	}

	public void setStanje(State stanje)
	{
		this.stanje = stanje;
	}

	public void setBoard(Board board)
	{
		this.board = board;

	}

	public GameActivity getGameActivity()
	{
		return gameActivity;
	}

	public void setGameActivity(GameActivity gameActivity)
	{
		this.gameActivity = gameActivity;
	}

	public int getBrojBacanja()
	{
		return brojBacanja;
	}

	public void setBrojBacanja(int brojBacanja)
	{
		this.brojBacanja = brojBacanja;
	}

	public int[] getValues()
	{
		return values;
	}

	public void setValues(int[] values)
	{
		this.values = values;
	}

	public int getPlayerNumber()
	{
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber)
	{
		// cuvamo stari score
		switch (this.getPlayerNumber())
		{

		case 1:
			board.setPlayer1Score(score);
			break;
		case 2:
			board.setPlayer2Score(score);
			break;
		case 3:
			board.setPlayer3Score(score);
			break;
		case 4:
			board.setPlayer4Score(score);
			break;

		}

		// i ucitavamo novi
		switch (playerNumber)
		{
		case 1:
			score = board.getPlayer1Score();
			break;
		case 2:
			score = board.getPlayer2Score();
			break;
		case 3:
			score = board.getPlayer3Score();
			break;
		case 4:
			score = board.getPlayer4Score();
			break;

		}

		this.playerNumber = playerNumber;
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	public void setNumOfPlayers(int num)
	{
		this.numOfPlayers = num;
	}

	public boolean isNajava()
	{
		return isNajava;
	}

	public void setNajava(boolean isNajava)
	{
		this.isNajava = isNajava;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public Dices getDices()
	{
		return dices;
	}

	public void setDices(Dices dices)
	{
		this.dices = dices;
	}

	public Igra getIgra()
	{
		return igra;
	}

	public void setIgra(Igra igra)
	{
		this.igra = igra;
	}

	public void setSensitiviy(int sens)
	{

		getBoard().getDices().setSensitivity(sens);
	}

	public int getTotalMoves()
	{
		return totalMoves;
	}

	public void setTotalMoves(int totalMoves)
	{
		this.totalMoves = totalMoves;
	}

	public boolean isSimulation()
	{
		return simulation;
	}

	public void setSimulation(boolean simulation)
	{
		this.simulation = simulation;
	}

	public FieldData getLastFieldData1()
	{
		return lastFieldData1;
	}

	public void setLastFieldData1(FieldData lastFieldData1)
	{
		this.lastFieldData1 = lastFieldData1;
	}

	public FieldData getLastFieldData2()
	{
		return lastFieldData2;
	}

	public void setLastFieldData2(FieldData lastFieldData2)
	{
		this.lastFieldData2 = lastFieldData2;
	}

	public FieldData getLastFieldData3()
	{
		return lastFieldData3;
	}

	public void setLastFieldData3(FieldData lastFieldData3)
	{
		this.lastFieldData3 = lastFieldData3;
	}

	public FieldData getLastFieldData4()
	{
		return lastFieldData4;
	}

	public void setLastFieldData4(FieldData lastFieldData4)
	{
		this.lastFieldData4 = lastFieldData4;
	}

	public float getVolume()
	{
		return volume;
	}

	public void setVolume(float volume)
	{
		this.volume = volume;
	}

	public boolean isShouldSix()
	{
		return shouldSix;
	}

	public void setShouldSix(boolean shouldSix)
	{
		this.shouldSix = shouldSix;
	}
}
