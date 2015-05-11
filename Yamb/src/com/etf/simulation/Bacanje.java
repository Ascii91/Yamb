package com.etf.simulation;

public class Bacanje
{
    private int    brojPartije;
    private String igrac;
    private String bacanje1;
    private String bacanje2;
    private String bacanje3;
    private int    x;
    private int    y;
    private int    value;
    private String selected1;
    private String selected2;

    public Bacanje(){}
    
    public Bacanje(int brojPartije, String igrac, String bacanje1, String bacanje2, String bacanje3, int x, int y, int value, String selected1, String selected2)
    {
        super();
        this.brojPartije = brojPartije;
        this.igrac = igrac;
        this.bacanje1 = bacanje1;
        this.bacanje2 = bacanje2;
        this.bacanje3 = bacanje3;
        this.x = x;
        this.y = y;
        this.value = value;
        this.selected1 = selected1;
        this.selected2 = selected2;
    }

    public int getBrojPartije()
    {
        return brojPartije;
    }

    public void setBrojPartije(int brojPartije)
    {
        this.brojPartije = brojPartije;
    }

    public String getIgrac()
    {
        return igrac;
    }

    public void setIgrac(String igrac)
    {
        this.igrac = igrac;
    }


    public String getBacanje1()
    {
        return bacanje1;
    }

    public void setBacanje1(String bacanje1)
    {
        this.bacanje1 = bacanje1;
    }

    public String getBacanje2()
    {
        return bacanje2;
    }

    public void setBacanje2(String bacanje2)
    {
        this.bacanje2 = bacanje2;
    }

    public String getBacanje3()
    {
        return bacanje3;
    }

    public void setBacanje3(String bacanje3)
    {
        this.bacanje3 = bacanje3;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public int getValue()
    {
        return value;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public String getSelected1()
    {
        return selected1;
    }

    public void setSelected1(String selected1)
    {
        this.selected1 = selected1;
    }

    public String getSelected2()
    {
        return selected2;
    }

    public void setSelected2(String selected2)
    {
        this.selected2 = selected2;
    }

}
