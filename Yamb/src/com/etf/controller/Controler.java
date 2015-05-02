package com.etf.controller;

import com.etf.fragments.GameFragment;
import com.etf.view.Board;

public class Controler
{
	private static Controler controler;
	private boolean shakingInProgress;
	private Board board;
	private State stanje = State.POCETNO_STANJE;
	private GameFragment gameFragment;
	private int brojBacanja;
	private int[] values = new int[6];
	
	
	
	public Board getBoard()
	{
		return board;
	}

	public static void setControler(Controler controler)
	{
		Controler.controler = controler;
	}

	public void stopShaking()
	{
		setShakingInProgress(false);
	}

	public static Controler getControler()
	{
		if (controler == null)
		{
			controler = new Controler();
		}
		return controler;
	}

	public boolean isShakingInProgress()
	{
		return shakingInProgress;
	}

	public void setShakingInProgress(boolean shakingInProgress)
	{
		this.shakingInProgress = shakingInProgress;
	}

	public void initAndStartGame()
	{

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

	public GameFragment getGameFragment()
	{
		return gameFragment;
	}

	public void setGameFragment(GameFragment gameFragment)
	{
		this.gameFragment = gameFragment;
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

}
