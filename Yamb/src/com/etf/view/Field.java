package com.etf.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.etf.controller.Controler;
import com.etf.controller.GameProgress;
import com.etf.controller.State;
import com.etf.model.FieldData;
import com.etf.utils.Constants;
import com.etf.yamb.R;

public class Field extends ImageView
{

	private Context context;
	private FieldData fieldData;
	private Bitmap fieldBitmap;
	private int highlightColor = 0;

	private Paint paint;
	private int a, b, c, d;
	private int type = 0;

	public Field(Context context, FieldData fieldData, int type)
	{
		super(context);
		this.context = context;
		this.setFieldData(fieldData);
		this.setType(type);

		paint = new Paint();

		a = fieldData.getFieldX();
		b = fieldData.getFieldY();
		c = a + fieldData.getFieldWidth();
		d = b + fieldData.getFieldHeight();

		if (type == 0) // okvir polja
		{

			setBitmapByNumber(fieldData.getFieldX(), fieldData.getFieldY());

			paint.setColor(Color.RED);

		} else if (type == 1) // suma polja
		{
			paint.setColor(Color.BLUE);
		} else if (type == 2) // upis vrednosnih polja
		{
			paint.setColor(Color.YELLOW);
		}
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(6);

	}

	private void setBitmapByNumber(int fieldX, int fieldY)
	{
		if (fieldY == 0) // prvi red
		{

			switch (fieldX / fieldData.getFieldWidth())
			{
			case 1:
				setFieldBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_down));
				break;
			case 2:
				setFieldBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_up_down));
				break;
			case 3:
				setFieldBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_up));
				break;
			case 4:
				setFieldBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_middle));
				break;
			case 5:
				setFieldBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_hand));
				break;
			case 6:
				setFieldBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow_bell));
				break;
			}
		}

	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (getType() != -1)
		{
			int cc = paint.getColor();

			if (getType() == 2)
			{

				if (fieldData.getSugestion() >= 0 && fieldData.getFieldValue() == -1)
				{
					paint.setStyle(Style.FILL);
					paint.setColor(getHighlightColor());
					canvas.drawRect(a + 3, b + 3, c - 3, d - 3, paint);
					paint.setTextSize(42);
					paint.setColor(0x3f00ff00);
					canvas.drawText("" + fieldData.getSugestion(), fieldData.getFieldX() + fieldData.getFieldWidth() / 3, fieldData.getFieldY() + 2 * fieldData.getFieldHeight()
							/ 3, paint);
					// fieldData.setSugestion(0);
					// highlightColor = 0;
				}

				else if (fieldData.getFieldValue() != -1)
				{
					paint.setTextSize(42);
					paint.setStrokeWidth(3);
					paint.setColor(Color.BLACK);
					canvas.drawText("" + fieldData.getFieldValue(), fieldData.getFieldX() + fieldData.getFieldWidth() / 3, fieldData.getFieldY() + 2 * fieldData.getFieldHeight()
							/ 3, paint);
				}

			}
			//
			paint.setStyle(Style.STROKE);
			paint.setColor(cc);

			canvas.drawRect(a, b, c, d, paint);

			if (fieldBitmap != null)
			{
				canvas.drawBitmap(fieldBitmap, fieldData.getFieldX() + fieldData.getFieldWidth() / 2 - fieldBitmap.getWidth() / 2, fieldData.getFieldY(), null);
			}

			if (fieldData.getFieldX() == 0)
			{

				paint.setColor(Color.BLACK);
				paint.setTextSize(42);

				switch (fieldData.getFieldY() / fieldData.getFieldHeight())
				{
				case 1:
					// Log.e("X.." + fieldData.getFieldX(), "Y.." +
					// fieldData.getFieldY());
					canvas.drawText("1", fieldData.getFieldWidth() / 4, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);
					break;
				case 2:
					canvas.drawText("2", fieldData.getFieldWidth() / 4, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);
					break;
				case 3:
					canvas.drawText("3", fieldData.getFieldWidth() / 4, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);
					break;
				case 4:
					canvas.drawText("4", fieldData.getFieldWidth() / 4, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);
					break;
				case 5:
					canvas.drawText("5", fieldData.getFieldWidth() / 4, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);
					break;
				case 6:
					canvas.drawText("6", fieldData.getFieldWidth() / 4, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);
					break;
				case 7:
					paint.setColor(Color.BLUE);
					paint.setTextSize(30);
					paint.setStrokeWidth(3);
					canvas.drawText("SUM", fieldData.getFieldWidth() / 8, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);
					break;
				case 8:
					paint.setTextSize(30);
					paint.setStrokeWidth(3);
					canvas.drawText("MAX", fieldData.getFieldWidth() / 8, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);
					break;
				case 9:
					paint.setTextSize(30);
					paint.setStrokeWidth(3);
					canvas.drawText("MIN", fieldData.getFieldWidth() / 8, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);
					break;

				case 10:
					paint.setColor(Color.BLUE);
					paint.setTextSize(30);
					paint.setStrokeWidth(3);
					canvas.drawText("SUM", fieldData.getFieldWidth() / 8, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);
					break;
				case 11:
					canvas.drawText("T", fieldData.getFieldWidth() / 4, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);

					break;

				case 12:
					canvas.drawText("S", fieldData.getFieldWidth() / 4, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);
					break;
				case 13:
					canvas.drawText("F", fieldData.getFieldWidth() / 4, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);

					break;
				case 14:
					canvas.drawText("P", fieldData.getFieldWidth() / 4, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);

					break;
				case 15:
					canvas.drawText("J", fieldData.getFieldWidth() / 4, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);

					break;
				case 16:
					paint.setColor(Color.BLUE);
					paint.setTextSize(30);
					paint.setStrokeWidth(3);
					canvas.drawText("SUM", fieldData.getFieldWidth() / 8, fieldData.getFieldHeight() + fieldData.getFieldY() - fieldData.getFieldHeight() / 4, paint);

					break;

				}

			}

		}
	}

	public FieldData getFieldData()
	{
		return fieldData;
	}

	public void setFieldData(FieldData fieldData)
	{
		this.fieldData = fieldData;
	}

	public Bitmap getFieldBitmap()
	{
		return fieldBitmap;
	}

	public void setFieldBitmap(Bitmap fieldBitmap)
	{
		this.fieldBitmap = fieldBitmap;
	}

	public int getHighlightColor()
	{
		return highlightColor;
	}

	public void setHighlightColor(int highlightColor)
	{
		this.highlightColor = highlightColor;
	}

	public void onTouch(MotionEvent event)
	{
		Rect rect = new Rect(a, b, c, d);
		if (rect.contains((int) event.getX(), (int) event.getY()))
		{
			// postoje dva nacina unosenja, posle prvog rucni i posle treceg
			// ostali

			int move = Controler.getControler().getBrojBacanja();
			if (move == 1 && fieldData.getFieldX() / fieldData.getFieldWidth() == 5 && getType() == 2)
			{
				fieldData.setFieldValue(fieldData.getSugestion());

				Controler.getControler().setStanje(State.POCETNO_STANJE);
				Controler.getControler().setBrojBacanja(0);
				Controler.getControler().getBoard().postInvalidate();

				int numP = Controler.getControler().getNumOfPlayers();
				int playerNum = Controler.getControler().getPlayerNumber();
				playerNum++;
				if (playerNum > numP)
				{
					playerNum = 1;
				}
				SharedPreferences prefs = this.getContext().getSharedPreferences(Constants.IGRA, 0);
				Controler.getControler().setPlayerNumber(playerNum);
				switch (playerNum)
				{
				case 1:
					Controler.getControler().setPlayerName(prefs.getString(Constants.IGRAC1, ""));
					break;
				case 2:
					Controler.getControler().setPlayerName(prefs.getString(Constants.IGRAC2, ""));
					break;
				case 3:
					Controler.getControler().setPlayerName(prefs.getString(Constants.IGRAC3, ""));
					break;
				case 4:
					Controler.getControler().setPlayerName(prefs.getString(Constants.IGRAC4, ""));
					break;

				}
				Toast.makeText(this.getContext(), "Uspesno ste uneli vrednost", Toast.LENGTH_LONG).show();

				new GameProgress().execute();

			} else if (move == 3 && getType() == 2 && fieldData.getSugestion() != -1 && fieldData.getFieldValue() == -1)
			{

				fieldData.setFieldValue(fieldData.getSugestion());
				Controler.getControler().setStanje(State.POCETNO_STANJE);
				Controler.getControler().setBrojBacanja(0);
				Controler.getControler().getBoard().postInvalidate();
				int numP = Controler.getControler().getNumOfPlayers();
				int playerNum = Controler.getControler().getPlayerNumber();
				playerNum++;
				if (playerNum > numP)
				{
					playerNum = 1;
				}
				SharedPreferences prefs = this.getContext().getSharedPreferences(Constants.IGRA, 0);
				Controler.getControler().setPlayerNumber(playerNum);
				switch (playerNum)
				{
				case 1:
					Controler.getControler().setPlayerName(prefs.getString(Constants.IGRAC1, ""));
					break;
				case 2:
					Controler.getControler().setPlayerName(prefs.getString(Constants.IGRAC2, ""));
					break;
				case 3:
					Controler.getControler().setPlayerName(prefs.getString(Constants.IGRAC3, ""));
					break;
				case 4:
					Controler.getControler().setPlayerName(prefs.getString(Constants.IGRAC4, ""));
					break;

				}
				Toast.makeText(this.getContext(), "Uspesno ste uneli vrednost", Toast.LENGTH_LONG).show();

				new GameProgress().execute();

			}

		}

	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}
}
