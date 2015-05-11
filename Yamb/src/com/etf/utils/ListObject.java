package com.etf.utils;

public class ListObject
{
private String position;
public ListObject(String position, String name, String score, String date)
{
	super();
	this.position = position;
	this.name = name;
	this.score = score;
	this.date = date;
}

public ListObject()
{
}

private String name;
private String score;
private String date;

public String getPosition()
{
	return position;
}
public void setPosition(String position)
{
	this.position = position;
}
public String getName()
{
	return name;
}
public void setName(String name)
{
	this.name = name;
}
public String getScore()
{
	return score;
}
public void setScore(String score)
{
	this.score = score;
}
public String getDate()
{
	return date;
}
public void setDate(String date)
{
	this.date = date;
}
	
	
}
