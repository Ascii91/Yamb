package com.etf.controller;

import java.util.List;

import com.etf.simulation.Bacanje;
import com.etf.utils.State;
import com.etf.view.Board;

//Klasa zadu�ena za stanja igrice
public class GameProgress
{

    protected Void doInBackground()
    {

        State stanje = Controler.getControler().getStanje();

        switch (stanje)
        {
            case POCETNO_STANJE : // ovo je stanje pre prvog mu�kanja
                // inicijalizuje novi potez
                Controler.getControler().getBoard().resetSugestions();
                Controler.getControler().getBoard().resetDices();
                Controler.getControler().getBoard().showDialog();
                Controler.getControler().setStanje(State.WAIT_USER);
                Bacanje b = new Bacanje();
                b.setIgrac(Controler.getControler().getPlayerName());
                Controler.getControler().getIgra().getLista().add(b);

            case WAIT_USER : // u ovom stanju �eka se da korisnik unese rezultat u polje ili da promu�ka kockice

                int brojBacanja = Controler.getControler().getBrojBacanja();
                switch (brojBacanja)
                {
                    case 1 : // nakon bacanja 1
                        // Sencenje potrebnih polja
                        Board board = Controler.getControler().getBoard();
                        board.colorFields(1);

                        // podaci za statistiku
                        int[] tempNiz = Controler.getControler().getValues().clone();
                        String values = "" + tempNiz[0] + tempNiz[1] + tempNiz[2] + tempNiz[3] + tempNiz[4] + tempNiz[5];
                        List<Bacanje> lista = Controler.getControler().getIgra().getLista();
                        lista.get(lista.size() - 1).setBacanje1(values);

                        break;
                    case 2 : // nakon poteza 2 ;

                        // Sencenje potrebnh polja
                        Board board2 = Controler.getControler().getBoard();
                        board2.colorFields(2);

                        // Podaci potrebni za statistiku
                        int[] tempNiz2 = Controler.getControler().getValues().clone();
                        String values2 = "" + tempNiz2[0] + tempNiz2[1] + tempNiz2[2] + tempNiz2[3] + tempNiz2[4] + tempNiz2[5];
                        List<Bacanje> lista2 = Controler.getControler().getIgra().getLista();
                        lista2.get(lista2.size() - 1).setBacanje2(values2);
                        boolean[] tempSelected2 = Controler.getControler().getBoard().getDices().getSelected().clone();
                        String selectedString = "";
                        for (boolean i : tempSelected2)
                        {
                            if (i) selectedString += "1";
                            else
                            {
                                selectedString += "0";
                            }
                        }

                        lista2.get(lista2.size() - 1).setSelected1(selectedString);

                        break;
                    case 3 : // nakon poteza 3

                        // Sencenje potrebnih polja
                        Board board3 = Controler.getControler().getBoard();
                        board3.disableShaking();
                        board3.colorFields(3);

                        // Podaci potrebni za statistiku
                        int[] tempNiz3 = Controler.getControler().getValues().clone();
                        String values3 = "" + tempNiz3[0] + tempNiz3[1] + tempNiz3[2] + tempNiz3[3] + tempNiz3[4] + tempNiz3[5];
                        List<Bacanje> lista3 = Controler.getControler().getIgra().getLista();
                        lista3.get(lista3.size() - 1).setBacanje3(values3); // uneto
                                                                            // bacanje 2
                        boolean[] tempSelected3 = Controler.getControler().getBoard().getDices().getSelected().clone();
                        String selectedString3 = "";
                        for (boolean i : tempSelected3)
                        {
                            if (i) selectedString3 += "1";
                            else
                            {
                                selectedString3 += "0";
                            }
                        }

                        lista3.get(lista3.size() - 1).setSelected2(selectedString3);

                        break;

                }
            default :
                break;

        }

        return null;

    }

    /**
     * Samo izvr�i doInBackground metodu
     */
    public void execute()
    {
        doInBackground();
    }

}
