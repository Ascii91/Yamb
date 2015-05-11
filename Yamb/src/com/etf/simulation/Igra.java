package com.etf.simulation;

import java.util.ArrayList;
import java.util.List;

public class Igra
{

    private int           rb;
    private String        pl1;
    private String        pl2;
    private String        pl3;
    private String        pl4;
    private long          vreme;
    private int           trajanje;
    private int           brojIgraca;
    private List<Bacanje> lista;

    public Igra(String pl1, String pl2, String pl3, String pl4, long vreme, int trajanje, int brojIgraca)
    {
        super();

        this.pl1 = pl1;
        this.pl2 = pl2;
        this.pl3 = pl3;
        this.pl4 = pl4;
        this.vreme = vreme;
        this.trajanje = trajanje;
        this.brojIgraca = brojIgraca;
        lista = new ArrayList<Bacanje>();
    }

    public int getRb()
    {
        return rb;
    }

    public void setRb(int rb)
    {
        this.rb = rb;
    }

    public String getPl1()
    {
        return pl1;
    }

    public void setPl1(String pl1)
    {
        this.pl1 = pl1;
    }

    public String getPl2()
    {
        return pl2;
    }

    public void setPl2(String pl2)
    {
        this.pl2 = pl2;
    }

    public String getPl3()
    {
        return pl3;
    }

    public void setPl3(String pl3)
    {
        this.pl3 = pl3;
    }

    public String getPl4()
    {
        return pl4;
    }

    public void setPl4(String pl4)
    {
        this.pl4 = pl4;
    }

    public long getVreme()
    {
        return vreme;
    }

    public void setVreme(long vreme)
    {
        this.vreme = vreme;
    }

    public int getTrajanje()
    {
        return trajanje;
    }

    public void setTrajanje(int trajanje)
    {
        this.trajanje = trajanje;
    }

    public int getBrojIgraca()
    {
        return brojIgraca;
    }

    public void setBrojIgraca(int brojIgraca)
    {
        this.brojIgraca = brojIgraca;
    }

    public List<Bacanje> getLista()
    {
        return lista;
    }

    public void setLista(List<Bacanje> lista)
    {
        this.lista = lista;
    }

    @Override
    public String toString()
    {

        String str = pl1 + " " + pl2 + " " + pl3 + " " + pl4 + " " + trajanje + " " + vreme + " " + brojIgraca;

        for (Bacanje b : lista)
        {
            str += b.getBacanje1() + " " + b.getBacanje2() + " " + b.getBacanje3() + " " + b.getIgrac() + " " + b.getX() + " " + b.getY() + " " + b.getValue()
                    + " " + b.getSelected1() + " " + b.getSelected2();
        }
        str += '\n';
        return str;
    }

}
