package com.etf.utils;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.etf.controller.Controler;
import com.etf.model.FieldData;
import com.etf.view.Field;

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

        int[] tempNiz = Controler.getControler().getValues().clone();
        for (int i = 6; i >= 1; i--)
        {
            int numCount = 0;
            for (int j = 0; j < 6; j++)
            {
                if (tempNiz[j] == i)
                {

                    numCount++;
                }
            }

            if (numCount >= 3)
            {

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
                if (tempNiz[j] == i) num++;
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
                if (tempNiz[j] == i) num++;
            }

            if (num >= 5)
            {
                return num * i + 50;
            }

        }

        return 0;
    }

    /**
     * @param fields Izracunavamo sumu svih kolona
     */
    public static void calculateSum(List<Field> fields)
    {
        int suma1Down = 0;
        int suma2Down = 0;
        int suma3Down = 0;
        int keceviDown = 0;

        int suma1Up = 0;
        int suma2Up = 0;
        int suma3Up = 0;
        int keceviUp = 0;

        int suma1UpDown = 0;
        int suma2UpDown = 0;
        int suma3UpDown = 0;
        int keceviUpDown = 0;

        int suma1MiddleUpDown = 0;
        int suma2MiddleUpDown = 0;
        int suma3MiddleUpDown = 0;
        int keceviMiddleUpDown = 0;

        int suma1Hand = 0;
        int suma2Hand = 0;
        int suma3Hand = 0;
        int keceviHand = 0;

        int suma1Bell = 0;
        int suma2Bell = 0;
        int suma3Bell = 0;
        int keceviBell = 0;

        int suma1Total = 0;
        int suma2Total = 0;
        int suma3Total = 0;

        for (Field f : fields)
        {

            FieldData data = f.getFieldData();
            if (data.getFieldX() / data.getFieldWidth() == 1) // strelica
                                                              // nadole
            {

                {

                    switch (data.getFieldY() / data.getFieldHeight())
                    {
                        case 1 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma1Down += 1 * data.getFieldValue();
                                keceviDown = data.getFieldValue();
                            }
                            break;
                        case 2 :
                        case 3 :
                        case 4 :
                        case 5 :
                        case 6 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma1Down += data.getFieldValue();

                            }
                            break;

                        case 7 :
                            if (suma1Down >= 60)
                            {
                                suma1Down += 30;
                            }
                            f.getFieldData().setFieldValue(suma1Down);

                            break;

                        case 8 : // max
                            if (data.getFieldValue() >= 0)
                            {
                                suma2Down += data.getFieldValue();
                            }
                            break;
                        case 9 : // min
                            if (data.getFieldValue() >= 0)
                            {
                                suma2Down -= data.getFieldValue();
                            }
                            break;
                        case 10 : // suma

                            if (suma2Down > 0)
                            {
                                suma2Down *= keceviDown;
                            }
                            else
                            {
                                suma2Down = 0;
                            }

                            f.getFieldData().setFieldValue(suma2Down);

                            break;
                        case 11 :
                        case 12 :
                        case 13 :
                        case 14 :
                        case 15 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma3Down += data.getFieldValue();
                            }
                            break;

                        case 16 :
                            f.getFieldData().setFieldValue(suma3Down);
                            break;

                    }

                }

            }

            // kolona UPDOWN

            if (data.getFieldX() / data.getFieldWidth() == 2) // strelica
            // nagore
            {

                {

                    switch (data.getFieldY() / data.getFieldHeight())
                    {
                        case 1 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma1UpDown += 1 * data.getFieldValue();
                                keceviUpDown = data.getFieldValue();
                            }
                            break;
                        case 2 :
                        case 3 :
                        case 4 :
                        case 5 :
                        case 6 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma1UpDown += data.getFieldValue();

                            }
                            break;

                        case 7 :
                            if (suma1UpDown >= 60)
                            {
                                suma1UpDown += 30;
                            }
                            f.getFieldData().setFieldValue(suma1UpDown);

                            break;

                        case 8 : // max
                            if (data.getFieldValue() >= 0)
                            {
                                suma2UpDown += data.getFieldValue();
                            }
                            break;
                        case 9 : // min
                            if (data.getFieldValue() >= 0)
                            {
                                suma2UpDown -= data.getFieldValue();
                            }
                            break;
                        case 10 : // suma

                            if (suma2UpDown > 0)
                            {
                                suma2UpDown *= keceviUpDown;
                            }
                            else
                            {
                                suma2UpDown = 0;
                            }

                            f.getFieldData().setFieldValue(suma2UpDown);

                            break;
                        case 11 :
                        case 12 :
                        case 13 :
                        case 14 :
                        case 15 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma3UpDown += data.getFieldValue();
                            }
                            break;

                        case 16 :
                            f.getFieldData().setFieldValue(suma3UpDown);
                            break;

                    }

                }

            }

            // kolona gore

            if (data.getFieldX() / data.getFieldWidth() == 3) // strelica
            // nagore
            {

                {

                    switch (data.getFieldY() / data.getFieldHeight())
                    {
                        case 1 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma1Up += 1 * data.getFieldValue();
                                keceviUp = data.getFieldValue();
                            }
                            break;
                        case 2 :
                        case 3 :
                        case 4 :
                        case 5 :
                        case 6 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma1Up += data.getFieldValue();

                            }
                            break;

                        case 7 :
                            if (suma1Up >= 60)
                            {
                                suma1Up += 30;
                            }
                            f.getFieldData().setFieldValue(suma1Up);

                            break;

                        case 8 : // max
                            if (data.getFieldValue() >= 0)
                            {
                                suma2Up += data.getFieldValue();
                            }
                            break;
                        case 9 : // min
                            if (data.getFieldValue() >= 0)
                            {
                                suma2Up -= data.getFieldValue();
                            }
                            break;
                        case 10 : // suma

                            if (suma2Up > 0)
                            {
                                suma2Up *= keceviUp;
                            }
                            else
                            {
                                suma2Up = 0;
                            }

                            f.getFieldData().setFieldValue(suma2Up);

                            break;
                        case 11 :
                        case 12 :
                        case 13 :
                        case 14 :
                        case 15 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma3Up += data.getFieldValue();
                            }
                            break;

                        case 16 :
                            f.getFieldData().setFieldValue(suma3Up);
                            break;

                    }

                }

            }

            // Middle up down

            if (data.getFieldX() / data.getFieldWidth() == 4) // strelica
            // nagore
            {

                {

                    switch (data.getFieldY() / data.getFieldHeight())
                    {
                        case 1 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma1MiddleUpDown += 1 * data.getFieldValue();
                                keceviMiddleUpDown = data.getFieldValue();
                            }
                            break;
                        case 2 :
                        case 3 :
                        case 4 :
                        case 5 :
                        case 6 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma1MiddleUpDown += data.getFieldValue();

                            }
                            break;

                        case 7 :
                            if (suma1MiddleUpDown >= 60)
                            {
                                suma1MiddleUpDown += 30;
                            }
                            f.getFieldData().setFieldValue(suma1MiddleUpDown);

                            break;

                        case 8 : // max
                            if (data.getFieldValue() >= 0)
                            {
                                suma2MiddleUpDown += data.getFieldValue();
                            }
                            break;
                        case 9 : // min
                            if (data.getFieldValue() >= 0)
                            {
                                suma2MiddleUpDown -= data.getFieldValue();
                            }
                            break;
                        case 10 : // suma

                            if (suma2MiddleUpDown > 0)
                            {
                                suma2MiddleUpDown *= keceviMiddleUpDown;
                            }
                            else
                            {
                                suma2MiddleUpDown = 0;
                            }

                            f.getFieldData().setFieldValue(suma2MiddleUpDown);

                            break;
                        case 11 :
                        case 12 :
                        case 13 :
                        case 14 :
                        case 15 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma3MiddleUpDown += data.getFieldValue();
                            }
                            break;

                        case 16 :
                            f.getFieldData().setFieldValue(suma3MiddleUpDown);
                            break;

                    }

                }

            }

            // Hand

            if (data.getFieldX() / data.getFieldWidth() == 5) // strelica
            // nagore
            {

                {

                    switch (data.getFieldY() / data.getFieldHeight())
                    {
                        case 1 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma1Hand += data.getFieldValue();
                                keceviHand = data.getFieldValue();
                            }
                            break;
                        case 2 :
                        case 3 :
                        case 4 :
                        case 5 :
                        case 6 :
                            if (data.getFieldValue() >= 0)
                            {
                                Log.e("BEFORE", "" + suma1Hand);
                                Log.e("POS", "" + (data.getFieldY() / data.getFieldHeight()));
                                Log.e("VAL", "" + data.getFieldValue());
                                suma1Hand += data.getFieldValue();
                                Log.e("After", "" + suma1Hand);

                            }
                            break;

                        case 7 :
                            if (suma1Hand >= 60)
                            {
                                suma1Hand += 30;
                            }
                            f.getFieldData().setFieldValue(suma1Hand);

                            break;

                        case 8 : // max
                            if (data.getFieldValue() >= 0)
                            {
                                suma2Hand += data.getFieldValue();
                            }
                            break;
                        case 9 : // min
                            if (data.getFieldValue() >= 0)
                            {
                                suma2Hand -= data.getFieldValue();
                            }
                            break;
                        case 10 : // suma

                            if (suma2Hand > 0)
                            {
                                suma2Hand *= keceviHand;
                            }
                            else
                            {
                                suma2Hand = 0;
                            }

                            f.getFieldData().setFieldValue(suma2Hand);

                            break;
                        case 11 :
                        case 12 :
                        case 13 :
                        case 14 :
                        case 15 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma3Hand += data.getFieldValue();
                            }
                            break;

                        case 16 :
                            f.getFieldData().setFieldValue(suma3Hand);
                            break;

                    }

                }

            }

            // Bell

            if (data.getFieldX() / data.getFieldWidth() == 6) // strelica
            // nagore
            {

                {

                    switch (data.getFieldY() / data.getFieldHeight())
                    {
                        case 1 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma1Bell += 1 * data.getFieldValue();
                                keceviBell = data.getFieldValue();
                            }
                            break;
                        case 2 :
                        case 3 :
                        case 4 :
                        case 5 :
                        case 6 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma1Bell += data.getFieldValue();

                            }
                            break;

                        case 7 :
                            if (suma1Bell >= 60)
                            {
                                suma1Bell += 30;
                            }
                            f.getFieldData().setFieldValue(suma1Bell);

                            break;

                        case 8 : // max
                            if (data.getFieldValue() >= 0)
                            {
                                suma2Bell += data.getFieldValue();
                            }
                            break;
                        case 9 : // min
                            if (data.getFieldValue() >= 0)
                            {
                                suma2Bell -= data.getFieldValue();
                            }
                            break;
                        case 10 : // suma

                            if (suma2Bell > 0)
                            {
                                suma2Bell *= keceviBell;
                            }
                            else
                            {
                                suma2Hand = 0;
                            }

                            f.getFieldData().setFieldValue(suma2Bell);

                            break;
                        case 11 :
                        case 12 :
                        case 13 :
                        case 14 :
                        case 15 :
                            if (data.getFieldValue() >= 0)
                            {
                                suma3Bell += data.getFieldValue();
                            }
                            break;

                        case 16 :
                            f.getFieldData().setFieldValue(suma3Bell);
                            break;

                    }

                }

            }

        }
        suma1Total = suma1Down + suma1UpDown + suma1Up + suma1MiddleUpDown + suma1Hand + suma1Bell;
        suma2Total = suma2Down + suma2UpDown + suma2Up + suma2MiddleUpDown + suma2Hand + suma2Bell;
        suma3Total = suma3Down + suma2UpDown + suma2Up + suma2MiddleUpDown + suma2Hand + suma2Bell;

        Controler.getControler().setScore(suma1Total + suma2Total + suma3Total);

        for (Field f : fields)
        {
            FieldData data = f.getFieldData();
            if (data.getFieldX() / data.getFieldWidth() == 7)
            {
                switch (data.getFieldY() / data.getFieldHeight())
                {
                    case 7 :
                        f.getFieldData().setFieldValue(suma1Total);
                        break;
                    case 10 :
                        f.getFieldData().setFieldValue(suma2Total);
                        break;
                    case 16 :
                        f.getFieldData().setFieldValue(suma3Total);
                        break;
                }
            }
        }

    }

}
