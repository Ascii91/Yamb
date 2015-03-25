package com.etf.view;

public class Controler
{
	private static Controler controler;
	private boolean shakingInProgress;
	
	
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

}
