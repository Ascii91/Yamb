package com.etf.utils;

public class ListObject implements Comparable<ListObject>
{
    private String position;

    public ListObject(String position, String name, int score, String date)
    {
        super();
        this.position = position;
        this.name = name;
        this.score = score;
        this.date = date;
    }

    public ListObject()
    {}

    private String name;
    private int    score;

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

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

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    @Override
    public int compareTo(ListObject another)
    {
        if (this.getScore() > another.getScore())
        {
            return 1;
        }
        else if (this.getScore() < another.getScore())
        {
            return -1;
        }
        return 0;
    }

}
