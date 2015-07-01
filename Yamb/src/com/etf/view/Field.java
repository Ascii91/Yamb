package com.etf.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.etf.activities.SettingsActivity;
import com.etf.controller.Controler;
import com.etf.controller.GameProgress;
import com.etf.model.FieldData;
import com.etf.simulation.Bacanje;
import com.etf.utils.Constants;
import com.etf.utils.State;
import com.etf.yamb.R;

public class Field extends ImageView
{

	private FieldData fieldData;
	private Bitmap fieldBitmap;
	private int highlightColor = 0;
	private Paint paint;
	private int a, b, c, d;
	private int type = 0;

	public Field(Context context, FieldData fieldData, int type)
	{
		super(context);
		this.setFieldData(fieldData);
		this.setType(type);
		paint = new Paint();

		// Koordinate taèaka polja (temena pravougaonika)
		a = fieldData.getFieldX();
		b = fieldData.getFieldY();
		c = a + fieldData.getFieldWidth();
		d = b + fieldData.getFieldHeight();

		// podesavamo parametre za iscrtavanje polja na tabli (Postoje 3
		// raylicita tipa polja na tabli)

		if (type == 0 || ((fieldData.getFieldY() == 0) && ((fieldData.getFieldX() / fieldData.getFieldWidth()) == 7))) // okvir
																														// polja
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

	/**
	 * @param fieldX
	 * @param fieldY
	 *            Setuje slicicu kolone u zavisnosti od parametara fieldX i
	 *            fieldY
	 */
	private void setBitmapByNumber(int fieldX, int fieldY)
	{
		if (fieldY == 0)
		{

			switch (fieldX / fieldData.getFieldWidth())
			{
			case 1:
				setFieldBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.arrow_down));
				break;
			case 2:
				setFieldBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.arrow_up_down));
				break;
			case 3:
				setFieldBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.arrow_up));
				break;
			case 4:
				setFieldBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.arrow_middle));
				break;
			case 5:
				setFieldBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.arrow_hand));
				break;
			case 6:
				setFieldBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.arrow_bell));
				break;
			case 7:
				setType(0);
				setFieldBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.drawable.settings_iccon));
				break;
			}
		}

	}

	/*
	 * Iscrtavanje komponente i njenog sadrzaja
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		paint.setTextSize(42);
		int cc = paint.getColor(); // Cuvamo boju da bismo je posle
									// restore-ovali

		if (getType() != -1) // polja sa tipom -1 su prazna polja, to su
								// praznine na tabli
		{

			if (getType() == 2) // Ovo su polja u koja mi upisujemo vrednost
			{

				// Upisujemo sugestivnu vrednost // ako postoji predlog i ako
				// nije upisana stvarna vrednost polja i ako nije najava
				// ispisujemo
				// predlog
				if (fieldData.getSugestion() >= 0 && fieldData.getFieldValue() == -1 && fieldData.isNajava() == false)
				{
					paint.setStyle(Style.FILL);
					paint.setColor(getHighlightColor());
					canvas.drawRect(a + 3, b + 3, c - 3, d - 3, paint);
					paint.setColor(0x3f00ff00);
					canvas.drawText("" + fieldData.getSugestion(), fieldData.getFieldX() + fieldData.getFieldWidth() / 3, fieldData.getFieldY() + 2 * fieldData.getFieldHeight()
							/ 3, paint);

				}
				// upisujemo vrednost polja // ovo su polja koja imaju stalnu
				// vrednost (vec popunjena )
				else if (fieldData.getFieldValue() != -1)
				{

					paint.setTextSize(42);
					paint.setStrokeWidth(3);
					paint.setColor(Color.BLACK);
					canvas.drawText("" + fieldData.getFieldValue(), fieldData.getFieldX() + fieldData.getFieldWidth() / 3, fieldData.getFieldY() + 2 * fieldData.getFieldHeight()
							/ 3, paint);
				}
				// Upisujemo sugestivnu vrednost u najavu (CRVENOM BOJOM)
				else if (fieldData.isNajava())// u pitanju je najava
				{
					paint.setStyle(Style.FILL);
					paint.setColor(getHighlightColor());
					canvas.drawRect(a + 3, b + 3, c - 3, d - 3, paint);
					paint.setColor(0x3fff0000);
					if (fieldData.getSugestion() == -1)
					{
						fieldData.setSugestion(0);
					}
					canvas.drawText("" + fieldData.getSugestion(), fieldData.getFieldX() + fieldData.getFieldWidth() / 3, fieldData.getFieldY() + 2 * fieldData.getFieldHeight()
							/ 3, paint);
				}

			} else if (getType() == 1 && fieldData.getFieldValue() >= 0) // Upisujemo
																			// polja
																			// sume
																			// (PLAVO)
			{
				paint.setTextSize(42);
				paint.setStrokeWidth(3);
				paint.setColor(Color.BLUE);
				canvas.drawText("" + fieldData.getFieldValue(), fieldData.getFieldX() + fieldData.getFieldWidth() / 5, fieldData.getFieldY() + 2 * fieldData.getFieldHeight() / 3,
						paint);
			}

			// ovo je border oko polja
			paint.setStyle(Style.STROKE);
			paint.setColor(cc);
			canvas.drawRect(a, b, c, d, paint);

			// ovo je slicica
			if (fieldBitmap != null)
			{
				canvas.drawBitmap(fieldBitmap, fieldData.getFieldX() + fieldData.getFieldWidth() / 2 - fieldBitmap.getWidth() / 2, fieldData.getFieldY(), null);
			}

			// Iscrtavamo vertikalnu granicnu liniju
			if (fieldData.getFieldX() == 0)
			{
				paint.setColor(Color.BLACK);
				drawVerticalBorder(canvas);

			}
		}
	}

	/**
	 * @param canvas
	 *            Iscrtava vertikalnu granicu ( skroz leva polja na tabli)
	 */
	private void drawVerticalBorder(Canvas canvas)

	{
		switch (fieldData.getFieldY() / fieldData.getFieldHeight())
		{
		case 1:
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

	/**
	 * Klikom na odreðeno polje na tabli poziva se onTouch
	 * 
	 * @param event
	 */
	public void onTouch(MotionEvent event)
	{

		Rect rect = new Rect(a, b, c, d);
		if (rect.contains((int) event.getX(), (int) event.getY())) // Ukoliko
																	// smo
																	// kliknuli
																	// na ovo
																	// polje
		{
			int move = Controler.getControler().getBrojBacanja();

			// u pitanju je najava
			if (move == 1 && fieldData.getFieldX() / fieldData.getFieldWidth() == 6 && getType() == 2 && fieldData.getFieldValue() == -1)// najava
			{

				Controler.getControler().setNajava(true);
				Controler.getControler().getBoard().resetNajava();
				fieldData.setNajava(true);

				new GameProgress().execute();
				Controler.getControler().getBoard().invalidate();

			}
			// U pitanju je upis u hand kolonu
			else if (move == 1 && fieldData.getFieldX() / fieldData.getFieldWidth() == 5 && getType() == 2 && fieldData.getFieldValue() == -1)
			{
				Controler.getControler().setNajava(false);
				fieldData.setFieldValue(fieldData.getSugestion());

				Controler.getControler().setStanje(State.POCETNO_STANJE);
				Controler.getControler().setBrojBacanja(0);
				Controler.getControler().getBoard().invalidate();

				int numP = Controler.getControler().getNumOfPlayers();
				int playerNum = Controler.getControler().getPlayerNumber();

				// prebacujemo se na seledeæeg igraèa
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

				Toast.makeText(this.getContext(), R.string.uneto_polje, Toast.LENGTH_SHORT).show();

				// Ažuriranje polja potrebnih za statistiku
				List<Bacanje> lista = Controler.getControler().getIgra().getLista();
				lista.get(lista.size() - 1).setX(fieldData.getFieldX());
				lista.get(lista.size() - 1).setY(fieldData.getFieldY());
				lista.get(lista.size() - 1).setValue(fieldData.getSugestion());

				new GameProgress().execute();

			}
			// Upis u neka od ostalih polja
			else if ((move == 3 || move == 1 || move == 2) && getType() == 2 && fieldData.getSugestion() != -1 && fieldData.getFieldValue() == -1)
			{

				Controler.getControler().setNajava(false);
				fieldData.setFieldValue(fieldData.getSugestion());
				Controler.getControler().setStanje(State.POCETNO_STANJE);
				Controler.getControler().setBrojBacanja(0);
				Controler.getControler().getBoard().invalidate();

				// prebacivanje na sledeæeg igraèa
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
				Toast.makeText(this.getContext(), R.string.uneto_polje, Toast.LENGTH_SHORT).show();

				// Ažuriranje polja potrebnih za voðenje statistike
				List<Bacanje> lista = Controler.getControler().getIgra().getLista();
				lista.get(lista.size() - 1).setX(fieldData.getFieldX());
				lista.get(lista.size() - 1).setY(fieldData.getFieldY());
				lista.get(lista.size() - 1).setValue(fieldData.getSugestion());

				new GameProgress().execute();

			}

			// Settings dugme - klikom na setings dugme otvara se meni za
			// podešavanje parametara za igru
			if (fieldData.getFieldY() == 0 && fieldData.getFieldX() / fieldData.getFieldWidth() == 7)
			{

				Vibrator v = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
				v.vibrate(50);
				 Intent i = new Intent(this.getContext(),
				 SettingsActivity.class);
				
				 this.getContext().startActivity(i);
				final Activity act = (Activity) this.getContext();
				// Poruka - restart partije
				new AlertDialog.Builder((Activity) (this.getContext())).setTitle(R.string.quit_title).setMessage(R.string.warning_message)
						.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int which)
							{
								Intent i = new Intent(act, SettingsActivity.class);

							//	act.startActivity(i);
						//		act.finish();
							}
						}).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialog, int which)
							{
								// do nothing
							}
						}).setIcon(android.R.drawable.ic_dialog_alert);//.show();
				//

			}

		}
	}

	/**
	 * Metoda potrebna za testiranje, izbrisati je naknadno
	 * 
	 * @throws IOException
	 */
	private void writeToSD() throws IOException
	{
		File sd = Environment.getExternalStorageDirectory();
		String DB_PATH;
		DB_PATH = getContext().getFilesDir().getAbsolutePath().replace("files", "databases") + File.separator;
		String DB_NAME = "yamb";

		if (sd.canWrite())
		{
			String currentDBPath = DB_NAME;
			String backupDBPath = "backupname.db";
			File currentDB = new File(DB_PATH, currentDBPath);
			File backupDB = new File(sd, backupDBPath);

			if (currentDB.exists())
			{
				FileChannel src = new FileInputStream(currentDB).getChannel();
				FileChannel dst = new FileOutputStream(backupDB).getChannel();
				dst.transferFrom(src, 0, src.size());
				src.close();
				dst.close();
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
}
