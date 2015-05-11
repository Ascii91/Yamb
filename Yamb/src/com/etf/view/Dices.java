package com.etf.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.etf.controller.Controler;
import com.etf.controller.GameProgress;
import com.etf.utils.Constants;
import com.etf.utils.DiceRoller;
import com.etf.yamb.R;

//Objekat kockice sve 
public class Dices extends ImageView
{

	private Bitmap one;
	private Bitmap two;
	private Bitmap three;
	private Bitmap four;
	private Bitmap five;
	private Bitmap six;

	private Bitmap one_selected;
	private Bitmap two_selected;
	private Bitmap three_selected;
	private Bitmap four_selected;
	private Bitmap five_selected;
	private Bitmap six_selected;

	private int startX; // start possition
	private int startY; // start position of dices y
	private int margin = 5;

	private DiceRoller diceRoller;
	private int[] values = new int[6];
	private boolean[] selected = new boolean[6];
	private int diceWidth;
	private MediaPlayer mp;
	private boolean shouldPlay;

	private Board board;

	public Dices(Context context)
	{
		super(context);

		initBitmaps(context);

	}

	public Dices(Board board)
	{
		super(board.getContext());

		this.board = board;
		initBitmaps(board.getContext());
		diceRoller = new DiceRoller(this);

	}

	public boolean stopShaking = true;

	public void startShaking()
	{  
		setMp(MediaPlayer.create(getContext(), R.raw.roll_dice));

		SharedPreferences prefs = ((Activity) (this.getContext())).getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
		shouldPlay = prefs.getBoolean(Constants.ZVUK, true);

		stopShaking = false;
		if (!getSelected()[0])
			animateDice(0);

		if (!getSelected()[1])
			animateDice(1);

		if (!getSelected()[2])
			animateDice(2);

		if (!getSelected()[3])
			animateDice(3);

		if (!getSelected()[4])
			animateDice(4);

		if (!getSelected()[5])
			animateDice(5);

	}

	public void stopShaking()
	{
		SharedPreferences prefs = ((Activity) (this.getContext())).getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);
		shouldPlay = prefs.getBoolean(Constants.ZVUK, true);

		stopShaking = true;
		Vibrator v = (Vibrator) board.getContext().getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(100);
		int brojBacanja = Controler.getControler().getBrojBacanja();
		brojBacanja++;
		Controler.getControler().setBrojBacanja(brojBacanja);
		Controler.getControler().setValues(values);
		mp.stop();
		setMp(MediaPlayer.create(getContext(), R.raw.throw_dice));

		if (shouldPlay)
		{
			mp.start();
		}
		new GameProgress().execute();

	}

	// animate specific dice
	public void animateDice(final int dice)
	{
		final Handler handler = new Handler();
		if (!stopShaking)
		{
			handler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{

					if (!stopShaking)
					{
						// Vibrator v = (Vibrator)
						// board.getContext().getSystemService(Context.VIBRATOR_SERVICE);
						// v.vibrate(1);
						values[dice] = (int) ((Math.random() * 6) + 1);
						animateDice(dice);
					}
					board.invalidate();
					if (!getMp().isPlaying() && shouldPlay)
					{
						getMp().start();
					}
				}
			}, 70);

		}
		{
			board.invalidate();
		}

	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		drawDices(canvas);

	}

	private void initBitmaps(Context context)
	{
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;

		diceWidth = (width - margin) / 6; // sirina kockice, ista ce biti i
											// visina
		this.startY = (height / (Constants.FIELDS_VERTICAL)) * (Constants.FIELDS_VERTICAL) - diceWidth - margin;

		one = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.one), diceWidth, diceWidth);
		two = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.two), diceWidth, diceWidth);
		three = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.three), diceWidth, diceWidth);
		four = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.four), diceWidth, diceWidth);
		five = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.five), diceWidth, diceWidth);
		six = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.six), diceWidth, diceWidth);

		one_selected = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.one_selected), diceWidth, diceWidth);
		two_selected = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.two_selected), diceWidth, diceWidth);
		three_selected = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.three_selected), diceWidth, diceWidth);
		four_selected = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.four_selected), diceWidth, diceWidth);
		five_selected = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.five_selected), diceWidth, diceWidth);
		six_selected = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.six_selected), diceWidth, diceWidth);

	}

	private Bitmap getSelectedBitmapByNumber(int i)
	{
		switch (i)
		{
		case 1:

			return one_selected;
		case 2:
			return two_selected;
		case 3:

			return three_selected;
		case 4:

			return four_selected;
		case 5:

			return five_selected;
		case 6:

			return six_selected;
		default:
			return one_selected;
		}

	}

	private Bitmap getBitmapByNumber(int i)
	{
		switch (i)
		{
		case 1:

			return one;
		case 2:

			return two;
		case 3:

			return three;
		case 4:

			return four;
		case 5:

			return five;
		case 6:

			return six;
		default:
			return one;
		}

	}

	private void drawDices(Canvas canvas)
	{

		try
		{
			if (!getSelected()[0])
			{
				canvas.drawBitmap(getBitmapByNumber(values[0]), startX + margin, startY, null);
			} else
			{
				canvas.drawBitmap(getSelectedBitmapByNumber(values[0]), startX + margin, startY, null);
			}
			if (!getSelected()[1])
			{
				canvas.drawBitmap(getBitmapByNumber(values[1]), startX + one.getWidth() + margin, startY, null);
			} else
			{
				canvas.drawBitmap(getSelectedBitmapByNumber(values[1]), startX + one.getWidth() + margin, startY, null);
			}
			if (!getSelected()[2])
			{
				canvas.drawBitmap(getBitmapByNumber(values[2]), startX + 2 * one.getWidth() + margin, startY, null);

			} else
			{
				canvas.drawBitmap(getSelectedBitmapByNumber(values[2]), startX + 2 * one.getWidth() + margin, startY, null);
			}
			if (!getSelected()[3])
			{
				canvas.drawBitmap(getBitmapByNumber(values[3]), startX + 3 * one.getWidth() + margin, startY, null);
			} else
			{
				canvas.drawBitmap(getSelectedBitmapByNumber(values[3]), startX + 3 * one.getWidth() + margin, startY, null);
			}
			if (!getSelected()[4])
			{
				canvas.drawBitmap(getBitmapByNumber(values[4]), startX + 4 * one.getWidth() + margin, startY, null);
			} else
			{

				canvas.drawBitmap(getSelectedBitmapByNumber(values[4]), startX + 4 * one.getWidth() + margin, startY, null);
			}
			if (!getSelected()[5])
			{
				canvas.drawBitmap(getBitmapByNumber(values[5]), startX + 5 * one.getWidth() + margin, startY, null);

			} else
			{
				canvas.drawBitmap(getSelectedBitmapByNumber(values[5]), startX + 5 * one.getWidth() + margin, startY, null);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private Bitmap resizeBitmap(Bitmap b, int reqWidth, int reqHeight)
	{
		Matrix m = new Matrix();
		m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);
		return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);

	}

	public void enableShaking()
	{
		diceRoller.register(getContext());
	}

	public void disableShaking()
	{
		diceRoller.deregister();
	}

	public void setStartX(int startX)
	{
		this.startX = startX;
	}

	public int getStartY()
	{
		return startY;
	}

	public int getStartX()
	{
		return startX;
	}

	public void setStartY(int startY)
	{
		this.startY = startY;
	}

	public void onTouch(MotionEvent v)
	{

		if (Controler.getControler().getBrojBacanja() == 0)
		{
			return;
		}
		int bitmapWidth = one.getWidth();
		int bitmapHeight = one.getHeight();

		int clickedX = (int) v.getX();
		int clickedY = (int) v.getY();

		for (int i = 0; i <= 5; i++)
		{
			int measure = startX + margin + i * bitmapWidth;
			Rect diceRect = new Rect(measure, startY, measure + bitmapWidth, startY + bitmapHeight);
			if (diceRect.contains(clickedX, clickedY))
			{
				if (getSelected()[i])
				{
					getSelected()[i] = false;
					board.invalidate();
				} else
				{
					getSelected()[i] = true;

					board.invalidate();
				}
				break;
			}

		}

	}

	public void reset()
	{
		values = new int[6];
		setSelected(new boolean[6]);
	}

	public int getDiceWidth()
	{
		return diceWidth;
	}

	public void setDiceWidth(int diceWidth)
	{
		this.diceWidth = diceWidth;
	}

	public MediaPlayer getMp()
	{
		return mp;
	}

	public void setMp(MediaPlayer mp)
	{
		this.mp = mp;
	}

    public boolean[] getSelected()
    {
        return selected;
    }

    public void setSelected(boolean[] selected)
    {
        this.selected = selected;
    }
}
