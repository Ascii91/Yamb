package com.etf.utils;

import java.util.List;

import com.etf.controller.Controler;
import com.etf.model.FieldData;
import com.etf.view.Field;

public class ColumnUtils
{
    private static int highlightColor1 = 0x5f0000ff;

    public static void columnBell(List<Field> fields)
    {

        if (Controler.getControler().getBrojBacanja() == 1)
        {
            for (Field f : fields)
            {
                FieldData data = f.getFieldData();
                if (data.getFieldX() / data.getFieldWidth() == 6)// &&
                                                                 // data.isNajava())

                {
                    if (f.getType() != 2)
                    {
                        continue;
                    }
                    else
                    {
                        switch (data.getFieldY() / data.getFieldHeight())
                        {
                            case 1 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(1));
                                break;
                            case 2 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(2));
                                break;
                            case 3 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(3));
                                break;
                            case 4 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(4));
                                break;
                            case 5 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(5));
                                break;
                            case 6 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(6));
                                break;
                            case 8 :
                                f.getFieldData().setSugestion(Calculator.calculateMax());
                                break;
                            case 9 :
                                f.getFieldData().setSugestion(Calculator.calculateMin());
                                break;
                            case 11 :
                                f.getFieldData().setSugestion(Calculator.calculateTriling());
                                break;

                            case 12 :
                                f.getFieldData().setSugestion(Calculator.calculateStraight());
                                break;
                            case 13 :
                                f.getFieldData().setSugestion(Calculator.calculateFull());
                                break;
                            case 14 :
                                f.getFieldData().setSugestion(Calculator.calculatePoker());
                                break;
                            case 15 :
                                f.getFieldData().setSugestion(Calculator.calculateYamb());
                                break;

                        }
                        f.setHighlightColor(highlightColor1);

                    }

                }

            }
        }
        else
        {
            for (Field f : fields)
            {

                FieldData data = f.getFieldData();
                if (data.getFieldX() / data.getFieldWidth() == 6 && data.isNajava())

                {
                    if (f.getType() != 2)
                    {
                        continue;
                    }
                    else
                    {
                        switch (data.getFieldY() / data.getFieldHeight())
                        {
                            case 1 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(1));
                                break;
                            case 2 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(2));
                                break;
                            case 3 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(3));
                                break;
                            case 4 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(4));
                                break;
                            case 5 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(5));
                                break;
                            case 6 :
                                f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(6));
                                break;
                            case 8 :
                                f.getFieldData().setSugestion(Calculator.calculateMax());
                                break;
                            case 9 :
                                f.getFieldData().setSugestion(Calculator.calculateMin());
                                break;
                            case 11 :
                                f.getFieldData().setSugestion(Calculator.calculateTriling());
                                break;

                            case 12 :
                                f.getFieldData().setSugestion(Calculator.calculateStraight());
                                break;
                            case 13 :
                                f.getFieldData().setSugestion(Calculator.calculateFull());
                                break;
                            case 14 :
                                f.getFieldData().setSugestion(Calculator.calculatePoker());
                                break;
                            case 15 :
                                f.getFieldData().setSugestion(Calculator.calculateYamb());
                                break;

                        }
                        f.setHighlightColor(highlightColor1);

                    }

                }
            }
        }
    }

    public static void columnHand(List<Field> fields)
    {
        for (Field f : fields)
        {
            FieldData data = f.getFieldData();
            if (data.getFieldX() / data.getFieldWidth() == 5)

            {
                if (f.getType() != 2)
                {
                    continue;
                }
                else
                {
                    switch (data.getFieldY() / data.getFieldHeight())
                    {
                        case 1 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(1));
                            break;
                        case 2 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(2));
                            break;
                        case 3 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(3));
                            break;
                        case 4 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(4));
                            break;
                        case 5 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(5));
                            break;
                        case 6 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(6));
                            break;
                        case 8 :
                            f.getFieldData().setSugestion(Calculator.calculateMax());
                            break;
                        case 9 :
                            f.getFieldData().setSugestion(Calculator.calculateMin());
                            break;
                        case 11 :
                            f.getFieldData().setSugestion(Calculator.calculateTriling());
                            break;

                        case 12 :
                            f.getFieldData().setSugestion(Calculator.calculateStraight());
                            break;
                        case 13 :
                            f.getFieldData().setSugestion(Calculator.calculateFull());
                            break;
                        case 14 :
                            f.getFieldData().setSugestion(Calculator.calculatePoker());
                            break;
                        case 15 :
                            f.getFieldData().setSugestion(Calculator.calculateYamb());
                            break;

                    }
                    f.setHighlightColor(highlightColor1);

                }

            }

        }

    }

    public static void columnMiddleUpDown(List<Field> fields)
    {

        for (Field f : fields)
        {
            // f.getFieldData().setSugestion(0);
            FieldData data = f.getFieldData();
            if (data.getFieldX() / data.getFieldWidth() == 4) // strelica
                                                              // nadole
            {
                if (data.getFieldValue() != -1 || f.getType() != 2 || data.getFieldY() / data.getFieldHeight() < 9)
                {
                    continue;
                }
                else
                {

                    switch (data.getFieldY() / data.getFieldHeight())
                    {

                        case 9 :
                            f.getFieldData().setSugestion(Calculator.calculateMin());
                            break;
                        case 11 :
                            f.getFieldData().setSugestion(Calculator.calculateTriling());
                            break;
                        //
                        case 12 :
                            f.getFieldData().setSugestion(Calculator.calculateStraight());
                            break;
                        case 13 :
                            f.getFieldData().setSugestion(Calculator.calculateFull());
                            break;
                        case 14 :
                            f.getFieldData().setSugestion(Calculator.calculatePoker());
                            break;
                        case 15 :
                            f.getFieldData().setSugestion(Calculator.calculateYamb());
                            break;

                    }
                    f.setHighlightColor(highlightColor1);
                    break;
                }

            }
        }

        for (int i = fields.size() - 1; i >= 0; i--)
        {
            Field f = fields.get(i);
            // f.getFieldData().setSugestion(0);
            FieldData data = f.getFieldData();
            if (data.getFieldX() / data.getFieldWidth() == 4) // strelica
                                                              // nadole
            {
                if (data.getFieldValue() != -1 || f.getType() != 2 || data.getFieldY() / data.getFieldHeight() > 8)
                {
                    continue;
                }
                else
                {
                    switch (data.getFieldY() / data.getFieldHeight())
                    {
                        case 8 :
                            f.getFieldData().setSugestion(Calculator.calculateMax());
                            break;
                        case 6 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(6));
                            break;
                        case 5 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(5));
                            break;
                        case 4 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(4));
                            break;
                        case 3 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(3));
                            break;
                        case 2 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(2));
                            break;
                        case 1 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(1));
                            break;

                    }
                    f.setHighlightColor(highlightColor1);
                    break;

                }
            }
        }

    }

    public static void columnUp(List<Field> fields)
    {
        for (int i = fields.size() - 1; i >= 0; i--)
        {
            Field f = fields.get(i);
            // f.getFieldData().setSugestion(0);
            FieldData data = f.getFieldData();
            if (data.getFieldX() / data.getFieldWidth() == 3) // strelica
                                                              // nadole
            {
                if (data.getFieldValue() != -1 || f.getType() != 2)
                {
                    continue;
                }
                else
                {

                    switch (data.getFieldY() / data.getFieldHeight())
                    {
                        case 15 :
                            f.getFieldData().setSugestion(Calculator.calculateYamb());
                            break;
                        case 14 :
                            f.getFieldData().setSugestion(Calculator.calculatePoker());
                            break;
                        case 13 :
                            f.getFieldData().setSugestion(Calculator.calculateFull());
                            break;
                        case 12 :
                            f.getFieldData().setSugestion(Calculator.calculateStraight());
                            break;
                        case 11 :
                            f.getFieldData().setSugestion(Calculator.calculateTriling());
                            break;
                        case 9 :
                            f.getFieldData().setSugestion(Calculator.calculateMin());
                            break;
                        case 8 :
                            f.getFieldData().setSugestion(Calculator.calculateMax());
                            break;
                        case 6 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(6));
                            break;
                        case 5 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(5));
                            break;
                        case 4 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(4));
                            break;
                        case 3 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(3));
                            break;
                        case 2 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(2));
                            break;
                        case 1 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(1));
                            break;

                    //

                    }
                    f.setHighlightColor(highlightColor1);
                    break;
                }

            }

        }
    }

    public static void columnUpDown(List<Field> fields)
    {
        for (Field f : fields)
        {

            FieldData data = f.getFieldData();
            if (data.getFieldX() / data.getFieldWidth() == 2)

            {
                if (f.getType() != 2)
                {
                    continue;
                }
                else
                {
                    switch (data.getFieldY() / data.getFieldHeight())
                    {
                        case 1 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(1));
                            break;
                        case 2 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(2));
                            break;
                        case 3 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(3));
                            break;
                        case 4 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(4));
                            break;
                        case 5 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(5));
                            break;
                        case 6 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(6));
                            break;
                        case 8 :
                            f.getFieldData().setSugestion(Calculator.calculateMax());
                            break;
                        case 9 :
                            f.getFieldData().setSugestion(Calculator.calculateMin());
                            break;
                        case 11 :
                            f.getFieldData().setSugestion(Calculator.calculateTriling());
                            break;

                        case 12 :
                            f.getFieldData().setSugestion(Calculator.calculateStraight());
                            break;
                        case 13 :
                            f.getFieldData().setSugestion(Calculator.calculateFull());
                            break;
                        case 14 :
                            f.getFieldData().setSugestion(Calculator.calculatePoker());
                            break;
                        case 15 :
                            f.getFieldData().setSugestion(Calculator.calculateYamb());
                            break;

                    }
                    f.setHighlightColor(highlightColor1);

                }

            }

        }

    }

    public static void columnDown(List<Field> fields)
    {
        for (Field f : fields)
        {
            FieldData data = f.getFieldData();
            if (data.getFieldX() / data.getFieldWidth() == 1) // strelica
                                                              // nadole
            {
                if (data.getFieldValue() != -1 || f.getType() != 2)
                {
                    continue;
                }
                else
                {

                    switch (data.getFieldY() / data.getFieldHeight())
                    {
                        case 1 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(1));
                            break;
                        case 2 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(2));
                            break;
                        case 3 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(3));
                            break;
                        case 4 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(4));
                            break;
                        case 5 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(5));
                            break;
                        case 6 :
                            f.getFieldData().setSugestion(Calculator.calculateSugetstionNumber(6));
                            break;
                        case 8 :
                            f.getFieldData().setSugestion(Calculator.calculateMax());
                            break;
                        case 9 :
                            f.getFieldData().setSugestion(Calculator.calculateMin());
                            break;
                        case 11 :
                            f.getFieldData().setSugestion(Calculator.calculateTriling());
                            break;
                        //
                        case 12 :
                            f.getFieldData().setSugestion(Calculator.calculateStraight());
                            break;
                        case 13 :
                            f.getFieldData().setSugestion(Calculator.calculateFull());
                            break;
                        case 14 :
                            f.getFieldData().setSugestion(Calculator.calculatePoker());
                            break;
                        case 15 :
                            f.getFieldData().setSugestion(Calculator.calculateYamb());
                            break;

                    }
                    f.setHighlightColor(highlightColor1);
                    break;
                }

            }

        }

    }
}
