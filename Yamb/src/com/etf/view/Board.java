package com.etf.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.etf.controller.Controler;
import com.etf.controller.GameProgress;
import com.etf.db.YambDb;
import com.etf.model.FieldData;
import com.etf.utils.Calculator;
import com.etf.utils.ColumnUtils;
import com.etf.utils.Constants;

public class Board extends ImageView implements OnTouchListener
{

	private List<Field> fields;
	private List<Field> fieldsPlayer1;
	private List<Field> fieldsPlayer2;
	private List<Field> fieldsPlayer3;
	private List<Field> fieldsPlayer4;
	private int player1Score = 0;
	private int player2Score = 0;
	private int player3Score = 0;
	private int player4Score = 0;
	private Dices dices;
	private Paint txtPaint = new Paint();

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

	/**
	 * Inicijalizovanje table za igru
	 */
	public void initBoard()
	{

		Controler.getControler().setBoard(this);
		setFields(new ArrayList<Field>());
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
				getFields().add(new Field(this.getContext(), new FieldData(this.getContext(), i, j), type));
				fieldsPlayer1.add(new Field(this.getContext(), new FieldData(this.getContext(), i, j), type));
				fieldsPlayer2.add(new Field(this.getContext(), new FieldData(this.getContext(), i, j), type));
				fieldsPlayer3.add(new Field(this.getContext(), new FieldData(this.getContext(), i, j), type));
				fieldsPlayer4.add(new Field(this.getContext(), new FieldData(this.getContext(), i, j), type));

			}
		}
		Dices dic = new Dices(this);
		Controler.getControler().setDices(dic);
		setDices(dic);

		SharedPreferences prefs = this.getContext().getSharedPreferences(Constants.IGRA, 0);
		Controler.getControler().setPlayerName(prefs.getString(Constants.IGRAC1, ""));
		Controler.getControler().setPlayerNumber(1);
		Controler.getControler().setNumOfPlayers(Integer.parseInt(prefs.getString(Constants.BROJ_IGRACA, "")));

		this.setOnTouchListener(this);
	}

	/**
	 * Ukljucivanje funkcionalnosti muckanja
	 */
	public void enableShaking()
	{

		getDices().enableShaking();
	}

	/**
	 * Iskljucivanje funkcionalnosti mu�kanja
	 */
	public void disableShaking()
	{
		Log.e("Disable shaking", "true");
		getDices().disableShaking();
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		Log.e("onDraw", "truie");
		super.onDraw(canvas);

		for (Field f : getFields())
		{
			f.draw(canvas);

		}
		// iscrtavanje kockica
		getDices().draw(canvas);

		// Iscrtavanje teksta za score
		txtPaint.setColor(Color.BLUE);
		txtPaint.setTextSize(32);
		String footerText = Controler.getControler().getPlayerName() + "      Total score:" + Controler.getControler().getScore() + "      Bacanje: "
				+ Controler.getControler().getBrojBacanja();

		canvas.drawText(footerText, getDices().getStartX() + getDices().getDiceWidth(), getDices().getStartY() - 5, txtPaint);

	}

	/**
	 * Prikazuje pobedniku dijalog i sa;uvava partiju u lokalnu bazu podataka
	 */
	public void showWinnerDialog()
	{

		int score1 = Controler.getControler().getBoard().getPlayer1Score();
		int score2 = Controler.getControler().getBoard().getPlayer2Score();
		int score3 = Controler.getControler().getBoard().getPlayer3Score();
		int score4 = Controler.getControler().getBoard().getPlayer4Score();

		SharedPreferences prefs = this.getContext().getSharedPreferences(Constants.IGRA, 0);

		int maxScore = Math.max(Math.max(score1, score2), Math.max(score3, score4));
		String maxName = "";
		if (maxScore == score1)
		{
			maxName = prefs.getString(Constants.IGRAC1, "");
		} else if (maxScore == score2)
		{
			maxName = prefs.getString(Constants.IGRAC2, "");
		} else if (maxScore == score3)
		{
			maxName = prefs.getString(Constants.IGRAC3, "");
		} else if (maxScore == score4)
		{
			maxName = prefs.getString(Constants.IGRAC4, "");
		}
		Controler.getControler().getIgra().setWinnerName(maxName);
		Controler.getControler().getIgra().setWinnerScore(maxScore);

		YambDb ydm = new YambDb(getContext());
		ydm.insertIgra();
		try
		{
			writeToSD();
		} catch (IOException e)
		{

			e.printStackTrace();
		}

		final String pobednik = maxName;

		Controler.getControler().getGameActivity().runOnUiThread(new Runnable()
		{

			@Override
			public void run()
			{

				String message = "Cestitamo pobednik je " + pobednik;

				Controler.getControler().getBoard().disableShaking();
				AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

				builder.setMessage(message);
				builder.setOnDismissListener(new OnDismissListener()
				{

					@Override
					public void onDismiss(DialogInterface dialog)
					{

						FragmentManager fm = Controler.getControler().getGameActivity().getFragmentManager();
						fm.popBackStack();

					}
				});

			}
		});
	}

	/**
	 * Prikazuje dijalog i priprema tablu za narednog igra;a U slu;aju da je
	 * kraj igre prikazuje cestitku korisniku
	 */
	public void showDialog()
	{
		int num = Controler.getControler().getTotalMoves();
		num++;
		Controler.getControler().setTotalMoves(num);

		if (num / Controler.getControler().getNumOfPlayers() == 79)
		{

			showWinnerDialog();
			return;
		}
		Log.e("MOVE", "" + num);
		Calculator.calculateSum(getFields());

		Controler.getControler().getGameActivity().runOnUiThread(new Runnable()
		{

			@Override
			public void run()
			{

				switch (Controler.getControler().getPlayerNumber())
				{
				case 1:

					setFields(fieldsPlayer1);

					break;
				case 2:
					setFields(fieldsPlayer2);
					break;
				case 3:
					setFields(fieldsPlayer3);
					break;
				case 4:
					setFields(fieldsPlayer4);
					break;

				}
				Calculator.calculateSum(getFields());
	//			createDialog("Na potezu je " + Controler.getControler().getPlayerName() + "\nTapni a zatim promuckaj telefon").show();

			}
		});
	}

	/**
	 * Kreira dijalog sa tekstom poruke
	 * 
	 * @param message
	 * @return
	 */
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

				invalidate();
				new GameProgress().execute();

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
			getDices().onTouch(event);
			for (Field f : getFields())
			{
				f.onTouch(event);
			}

			return true;
		case MotionEvent.ACTION_UP:
			return true;
		case MotionEvent.ACTION_MOVE:
			return true;
		}
		return false;
	}

	/**
	 * U zavisnosti od parametra radi sen�enje svih polja potrebnih
	 * 
	 * @param i
	 *            redni broj poteza
	 */
	public void colorFields(int i)
	{
		resetSugestions();

		if (i == 1)
		{

			ColumnUtils.columnDown(getFields());
			ColumnUtils.columnUpDown(getFields());
			ColumnUtils.columnUp(getFields());
			ColumnUtils.columnMiddleUpDown(getFields());
			ColumnUtils.columnHand(getFields());
			ColumnUtils.columnBell(getFields());
		} else if (i == 2)
		{
			if (Controler.getControler().isNajava() == false)
			{
				ColumnUtils.columnDown(getFields());
				ColumnUtils.columnUpDown(getFields());
				ColumnUtils.columnUp(getFields());
				ColumnUtils.columnMiddleUpDown(getFields());
			} else
			{
				ColumnUtils.columnBell(getFields());
			}
		} else if (i == 3)
		{
			if (Controler.getControler().isNajava() == false)
			{
				ColumnUtils.columnDown(getFields());
				ColumnUtils.columnUpDown(getFields());
				ColumnUtils.columnUp(getFields());
				ColumnUtils.columnMiddleUpDown(getFields());
			} else
			{
				ColumnUtils.columnBell(getFields());
			}
		}
	}

	/**
	 * Resetuje predloge za popunjavanje polja
	 */
	public void resetSugestions()
	{
		for (Field f : getFields())
		{
			f.setHighlightColor(0);
			f.getFieldData().setSugestion(-1);
		}
	}

	/**
	 * Resetuje trenutnu najavu
	 */
	public void resetNajava()
	{
		for (Field f : getFields())
		{
			f.setHighlightColor(0);
			f.getFieldData().setNajava(false);
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

	public int getPlayer1Score()
	{
		return player1Score;
	}

	public void setPlayer1Score(int player1Score)
	{
		this.player1Score = player1Score;
	}

	public int getPlayer2Score()
	{
		return player2Score;
	}

	public void setPlayer2Score(int player2Score)
	{
		this.player2Score = player2Score;
	}

	public int getPlayer3Score()
	{
		return player3Score;
	}

	public void setPlayer3Score(int player3Score)
	{
		this.player3Score = player3Score;
	}

	public int getPlayer4Score()
	{
		return player4Score;
	}

	public void setPlayer4Score(int player4Score)
	{
		this.player4Score = player4Score;
	}

	/**
	 * Moja metoda za testiranje, potrebno obrisati kasnije
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

	public List<Field> getFields()
	{
		return fields;
	}

	public void setFields(List<Field> fields)
	{
		this.fields = fields;
	}

}
