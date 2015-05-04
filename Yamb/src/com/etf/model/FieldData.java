package com.etf.model;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

import com.etf.utils.Constants;

public class FieldData
{

	private int fieldWidth;
	private int fieldHeight;
	private int fieldX;
	private int fieldY;
	private int fieldValue = -1; // inicijalna vrednost
	private int sugestion = -1; // inicijalni predlog
	private boolean najava = false;

	public FieldData(Context context, int fieldX, int fieldY)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		fieldWidth = width / Constants.FIELDS_HORISONTAL;
		fieldHeight = height / Constants.FIELDS_VERTICAL;

		this.fieldX = fieldX * fieldWidth;
		this.fieldY = fieldY * fieldHeight;

	}

	public int getFieldWidth()
	{
		return fieldWidth;
	}

	public void setFieldWidth(int fieldWidth)
	{
		this.fieldWidth = fieldWidth;
	}

	public int getFieldHeight()
	{
		return fieldHeight;
	}

	public void setFieldHeight(int fieldHeight)
	{
		this.fieldHeight = fieldHeight;
	}

	public int getFieldX()
	{
		return fieldX;
	}

	public void setFieldX(int fieldX)
	{
		this.fieldX = fieldX;
	}

	public int getFieldY()
	{
		return fieldY;
	}

	public void setFieldY(int fieldY)
	{
		this.fieldY = fieldY;
	}

	public int getFieldValue()
	{
		return fieldValue;
	}

	public void setFieldValue(int fieldValue)
	{
		this.fieldValue = fieldValue;
	}

	public int getSugestion()
	{
		return sugestion;
	}

	public void setSugestion(int sugestion)
	{
		this.sugestion = sugestion;
	}

	public boolean isNajava()
	{
		return najava;
	}

	public void setNajava(boolean najava)
	{
		this.najava = najava;
	}

}
