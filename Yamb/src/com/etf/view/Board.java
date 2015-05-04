package com.etf.view;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.etf.controller.Controler;
import com.etf.controller.GameProgress;
import com.etf.model.FieldData;
import com.etf.utils.ColumnUtils;
import com.etf.utils.Constants;

public class Board extends ImageView implements OnTouchListener
{

    private List<Field> fields;
    private List<Field> fieldsPlayer1;
    private List<Field> fieldsPlayer2;
    private List<Field> fieldsPlayer3;
    private List<Field> fieldsPlayer4;

    private Dices       dices;
    private Paint       txtPaint = new Paint();

    public Board(Context context)
    {
        super(context);
        initBoard();
    }

    public Board(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initBoard();
    }

    public Board(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initBoard();
    }

    public void initBoard()
    {
        Controler.getControler().setBoard(this);
        fields = new ArrayList<Field>();
        fieldsPlayer1 = new ArrayList<Field>();
        fieldsPlayer2 = new ArrayList<Field>();
        fieldsPlayer3 = new ArrayList<Field>();
        fieldsPlayer4 = new ArrayList<Field>();

        for (int i = 0; i < Constants.FIELDS_HORISONTAL; i++)
        {
            for (int j = 0; j < Constants.FIELDS_VERTICAL; j++)
            {

                int type = 2;
                if (i == 0 || j == 0)
                {
                    type = 0;
                }// border
                if (j > 16 || i == 7)
                {
                    type = -1;
                }// empty
                if (j == 7 || j == 10 || j == 16)
                {
                    type = 1;
                }// sum
                fields.add(new Field(this.getContext(), new FieldData(this.getContext(), i, j), type));
                fieldsPlayer1.add(new Field(this.getContext(), new FieldData(this.getContext(), i, j), type));
                fieldsPlayer2.add(new Field(this.getContext(), new FieldData(this.getContext(), i, j), type));
                fieldsPlayer3.add(new Field(this.getContext(), new FieldData(this.getContext(), i, j), type));
                fieldsPlayer4.add(new Field(this.getContext(), new FieldData(this.getContext(), i, j), type));

            }
        }
        setDices(new Dices(this));
        SharedPreferences prefs = this.getContext().getSharedPreferences(Constants.IGRA, 0);
        Controler.getControler().setPlayerName(prefs.getString(Constants.IGRAC1, ""));
        Controler.getControler().setPlayerNumber(1);
        Controler.getControler().setNumOfPlayers(Integer.parseInt(prefs.getString(Constants.BROJ_IGRACA, "")));

        this.setOnTouchListener(this);
    }

    public void enableShaking()
    {
        getDices().enableShaking();
    }

    public void disableShaking()
    {
        getDices().disableShaking();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        for (Field f : fields)
        {
            f.draw(canvas);

        }

        getDices().draw(canvas);
        txtPaint.setColor(Color.BLUE);
        txtPaint.setTextSize(38);
        canvas.drawText("TOTAL SCORE: 999", getDices().getStartX() + fields.get(0).getFieldData().getFieldWidth() * 2, getDices().getStartY() - 25, txtPaint);
        txtPaint.setTextSize(32);

        canvas.drawText("" + Controler.getControler().getPlayerName(), getDices().getStartX() + 10, getDices().getStartY() - 25, txtPaint);
        canvas.drawText("Bacanje: " + Controler.getControler().getBrojBacanja(), getDices().getStartX() + fields.get(0).getFieldData().getFieldWidth() * 2
                + 350, getDices().getStartY() - 25, txtPaint);

    }

    public void showDialog()
    {
        Controler.getControler().getGameFragment().getActivity().runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {

                switch (Controler.getControler().getPlayerNumber())
                {
                    case 1 :
                        fields = fieldsPlayer1;
                        break;

                    case 2 :
                        fields = fieldsPlayer2;
                        break;
                    case 3 :
                        fields = fieldsPlayer3;
                        break;
                    case 4 :
                        fields = fieldsPlayer4;
                        break;

                }

                createDialog("Na potezu je " + Controler.getControler().getPlayerName() + "\nTapni a zatim promuckaj telefon").show();

            }
        });
    }

    private Dialog createDialog(String message)
    {
        Controler.getControler().getBoard().disableShaking();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage(message);
        builder.setOnDismissListener(new OnDismissListener()
        {

            @Override
            public void onDismiss(DialogInterface dialog)
            {

                Controler.getControler().getBoard().enableShaking();
                synchronized (this)
                {
                    // notifyAll();
                    invalidate();
                    new GameProgress().execute();

                }

            }
        });

        return builder.create();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN :
                getDices().onTouch(event);
                for (Field f : fields)
                {
                    f.onTouch(event);
                }

                return true;
            case MotionEvent.ACTION_UP :
                return true;
            case MotionEvent.ACTION_MOVE :
                return true;
        }
        return false;
    }

    public void colorFields(int i)
    {
        resetSugestions();

        if (i == 1)
        {

            ColumnUtils.columnDown(fields);
            ColumnUtils.columnUpDown(fields);
            ColumnUtils.columnUp(fields);
            ColumnUtils.columnMiddleUpDown(fields);
            ColumnUtils.columnHand(fields);
        }
        else if (i == 2)
        {

            ColumnUtils.columnDown(fields);
            ColumnUtils.columnUpDown(fields);
            ColumnUtils.columnUp(fields);
            ColumnUtils.columnMiddleUpDown(fields);
        }
        else if (i == 3)
        {

            ColumnUtils.columnDown(fields);
            ColumnUtils.columnUpDown(fields);
            ColumnUtils.columnUp(fields);
            ColumnUtils.columnMiddleUpDown(fields);
        }

    }

    public void resetSugestions()
    {
        for (Field f : fields)
        {
            f.setHighlightColor(0);
            f.getFieldData().setSugestion(-1);
        }
    }

    public Dices getDices()
    {
        return dices;
    }

    public void setDices(Dices dices)
    {
        this.dices = dices;
    }

    public void resetDices()
    {
        dices.reset();

    }

    public List<Field> getFieldsPlayer1()
    {
        return fieldsPlayer1;
    }

    public void setFieldsPlayer1(List<Field> fieldsPlayer1)
    {
        this.fieldsPlayer1 = fieldsPlayer1;
    }

    public List<Field> getFieldsPlayer2()
    {
        return fieldsPlayer2;
    }

    public void setFieldsPlayer2(List<Field> fieldsPlayer2)
    {
        this.fieldsPlayer2 = fieldsPlayer2;
    }

    public List<Field> getFieldsPlayer3()
    {
        return fieldsPlayer3;
    }

    public void setFieldsPlayer3(List<Field> fieldsPlayer3)
    {
        this.fieldsPlayer3 = fieldsPlayer3;
    }

    public List<Field> getFieldsPlayer4()
    {
        return fieldsPlayer4;
    }

    public void setFieldsPlayer4(List<Field> fieldsPlayer4)
    {
        this.fieldsPlayer4 = fieldsPlayer4;
    }

}
