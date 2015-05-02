package com.etf.view;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.etf.controller.Controler;
import com.etf.model.FieldData;
import com.etf.utils.Constants;

public class Board extends ImageView implements OnTouchListener
{

	private List<Field> fields;
	private Dices dices;
	private static int highlightColor1 = 0x7f0000ff;
	private static int highlightColor2 = 0x7f0000ff;

	Paint txtPaint = new Paint();

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

			}
		}
		dices = new Dices(this);

		this.setOnTouchListener(this);
	}

	public void enableShaking()
	{
		dices.enableShaking();
	}

	public void disableShaking()
	{
		dices.disableShaking();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		for (Field f : fields)
		{
			f.draw(canvas);

		}

		dices.draw(canvas);
		txtPaint.setColor(Color.BLUE);
		txtPaint.setTextSize(42);

		canvas.drawText("TOTAL SCORE: 999", dices.getStartX() + fields.get(0).getFieldData().getFieldWidth() * 2, dices.getStartY() - 25, txtPaint);
	}

	public void showDialog()
	{
		Controler.getControler().getGameFragment().getActivity().runOnUiThread(new Runnable()
		{

			@Override
			public void run()
			{

				createDialog("TAPNI A ZATIM PROMUCKAJ TELEFON").show();

			}
		});
	}

	private Dialog createDialog(String message)
	{
		// Use the Builder class for convenient dialog construction
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
					notifyAll();
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
		case MotionEvent.ACTION_DOWN:
			dices.onTouch(event);
			return true;
		case MotionEvent.ACTION_UP:
			return true;
		case MotionEvent.ACTION_MOVE:
			return true;
		}
		return false;
	}

	public void colorFields(int i)
	{

		if (i == 1)
		{
			// proveravamo kolonu na dole dok ne nadjemo sledece slobodno
			for (Field f : fields)
			{
				FieldData data = f.getFieldData();
				if (data.getFieldX() / data.getFieldWidth() == 1) //strelica nadole
				{
					if (data.getFieldValue() != -1)
					{
						continue;
					} else
					{
						switch (data.getFieldY() / data.getFieldHeight())
						{
						case 1:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 2:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 3:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 4:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 5:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 6:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 7:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 8:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 9:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;

						case 10:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 11:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;

						case 12:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 13:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 14:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 15:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;
						case 16:
							f.getFieldData().setSugestion(calculateSugetstion1());
							break;

						}

					}

					f.setHighlightColor(highlightColor1);
				}

			}

		} else if (i == 2)
		{

		} else if (i == 3)
		{

		}
		// postInvalidate();
	}

	private int calculateSugetstion1()
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
