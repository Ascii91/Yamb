package com.etf.utils;

import java.util.ArrayList;

import android.util.Log;

import com.etf.controller.Controler;

public class Calculator
{

	public static int calculateMax()
	{
		int max = 0;

		int[] tempNiz = Controler.getControler().getValues().clone();
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				if (tempNiz[i] > tempNiz[j])
				{
					int temp = tempNiz[i];
					tempNiz[i] = tempNiz[j];
					tempNiz[j] = temp;

				}
			}
		}

		for (int i = 0; i < 5; i++)
		{
			max += tempNiz[i];
		}

		return max;

	}

	public static int calculateMin()
	{
		int min = 0;
		int[] tempNiz = Controler.getControler().getValues().clone();
		for (int i = 0; i < 6; i++)
		{
			for (int j = 0; j < 6; j++)
			{
				if (tempNiz[i] < tempNiz[j])
				{
					int temp = tempNiz[i];
					tempNiz[i] = tempNiz[j];
					tempNiz[j] = temp;

				}
			}
		}
		for (int i = 0; i < 5; i++)
		{
			min += tempNiz[i];
		}

		return min;
	}

	public static int calculateSugetstionNumber(int num)
	{

		int sugestion = 0;
		for (int i = 0; i < 6; i++)
		{
			if (Controler.getControler().getValues()[i] == num)
			{
				sugestion += num;

			}
		}
		if (sugestion > 5 * num)
		{
			sugestion = 5 * num;
		}
		return sugestion;
	}

	public static int calculateTriling()
	{
		int triling = 0;
		int[] tempNiz = Controler.getControler().getValues();
		int num = 0;

		for (int i = 6; i >= 1; i--)
		{
			num = 0;
			for (int j = 0; j < 6; j++)
			{
				if (tempNiz[j] == i)
				{
					num++;
				}

			}
			if (num >= 3)
			{
				triling += 20 + 3 * i;
				return triling;
			}

		}
		return triling;
	}

	public static int calculateStraight()
	{

		int[] tempNiz = Controler.getControler().getValues().clone();
		ArrayList<Integer> lista = new ArrayList<Integer>();

		for (int i = 0; i < 6; i++)
		{
			lista.add(tempNiz[i]);
		}

		if (lista.contains(2) && lista.contains(3) && lista.contains(4) && lista.contains(5) && lista.contains(6))
		{
			int bacanje = Controler.getControler().getBrojBacanja();
			if (bacanje == 1)
			{
				return 66;
			}
			if (bacanje == 2)
			{
				return 56;
			}
			if (bacanje == 3)
			{
				return 46;
			}

		}
		if (lista.contains(1) && lista.contains(2) && lista.contains(3) && lista.contains(4) && lista.contains(5))
		{
			int bacanje = Controler.getControler().getBrojBacanja();
			if (bacanje == 1)
			{
				return 66;
			}
			if (bacanje == 2)
			{
				return 56;
			}
			if (bacanje == 3)
			{
				return 46;
			}
		}
		return 0;
	}

	public static int calculateFull()
	{
		Log.e("calculate full", "true");
		int[] tempNiz = Controler.getControler().getValues().clone();
		for (int i = 6; i >= 1; i--)
		{  
			int numCount = 0;
			for (int j = 0; j < 6; j++)
			{
				if (tempNiz[j] == i)
				{
					Log.e("calculate ", "true"+i);
					
					numCount++;
				}
			}

			if (numCount >= 3)
			{
				Log.e("Ulazim u trecu :D ", "true");
				for (int k = 6; k >= 1; k--)
				{
					int numCount2 = 0;
					if (k == i)
					{
						continue;
					}

					for (int l = 0; l < 6; l++)
					{
						if (tempNiz[l] == k)
						{
							numCount2++;
						}
					}

					if (numCount2 >= 2)
					{
						return 30 + 3 * i + 2 * k;
					}
				}

			}

		}

		return 0;
	}

	public static int calculatePoker()
	{
		int[] tempNiz = Controler.getControler().getValues().clone();

		for (int i = 1; i <= 6; i++)
		{
			int num = 0;
			for (int j = 0; j < 6; j++)
			{
				if (tempNiz[j] == i)
					num++;
			}

			if (num >= 4)
			{
				return num * i + 40;
			}
		}
		return 0;
	}

	public static int calculateYamb()
	{
		int[] tempNiz = Controler.getControler().getValues().clone();

		for (int i = 1; i <= 6; i++)
		{
			int num = 0;
			for (int j = 0; j < 6; j++)
			{
				if (tempNiz[j] == i)
					num++;
			}

			if (num >= 5)
			{
				return num * i + 50;
			}

		}

		return 0;
	}

}
