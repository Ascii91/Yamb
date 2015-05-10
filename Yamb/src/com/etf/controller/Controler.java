package com.etf.controller;

import com.etf.fragments.GameFragment;
import com.etf.utils.State;
import com.etf.view.Board;
import com.etf.view.Dices;

public class Controler
{
    private static Controler controler;
    private boolean          shakingInProgress;
    private Board            board;
    private State            stanje = State.POCETNO_STANJE;
    private GameFragment     gameFragment;
    private int              brojBacanja;
    private int[]            values = new int[6];

    private String           playerName;
    private int              playerNumber;
    private int              numOfPlayers;
    private boolean          isNajava;
    private int              score  = 0;
    private Dices dices;
    
    
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

    public int getPlayerNumber()
    {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber)
    {
        // cuvamo stari score
        switch (this.getPlayerNumber())
        {

            case 1 :
                board.setPlayer1Score(score);
                break;
            case 2 :
                board.setPlayer2Score(score);
                break;
            case 3 :
                board.setPlayer3Score(score);
                break;
            case 4 :
                board.setPlayer4Score(score);
                break;

        }

        switch (playerNumber)
        {
            case 1 :
                score = board.getPlayer1Score();
                break;
            case 2 :
                score = board.getPlayer2Score();
                break;
            case 3 :
                score = board.getPlayer3Score();
                break;
            case 4 :
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

}
