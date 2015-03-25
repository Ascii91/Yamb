package com.etf.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.etf.utils.Constants;
import com.etf.utils.DiceRoller;
import com.etf.yamb.R;

public class Dices extends ImageView
{

	private Bitmap one;
	private Bitmap two;
	private Bitmap three;
	private Bitmap four;
	private Bitmap five;
	private Bitmap six;

	private int startX; // start possition
	private int startY;// start position of dices y
	private int margin = 5;

	private DiceRoller diceRoller;
	private int[] values = new int[6];
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
		diceRoller.register(board.getContext());
	}

	public void rollDices(int[] dicePositions)
	{
		for (int i = 0; i < 6; i++)
		{
			if (dicePositions[i] == 1)
			{
				// rollDice
				// generisi slucajan broj za selektovane kockice i prikazi
				// animaciju muckanja
				values[i] = (int) (Math.random() * 6 + 1);

			}
		}

	}

	public boolean stopShaking = true;

	public void startShaking()
	{
//		if (stopShaking != false)
//		{
			stopShaking = false;
			animateDice(1);
			animateDice(2);
			animateDice(3);
			animateDice(4);
			animateDice(5);
			animateDice(0);
		//}
	}

	public void stopShaking()
	{
		stopShaking = true;
		 Vibrator v = (Vibrator) board.getContext().getSystemService(Context.VIBRATOR_SERVICE);
		 // Vibrate for 500 milliseconds
		 v.vibrate(100);
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
					values[dice] = (int) ((Math.random() * 6) + 1);
					 Vibrator v = (Vibrator) board.getContext().getSystemService(Context.VIBRATOR_SERVICE);
					 // Vibrate for 500 milliseconds
					 v.vibrate(1);
					animateDice(dice);
					board.postInvalidate();

				}
			}, 100);

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

		int diceWidth = (width - margin) / 6; // sirina kockice, ista ce biti i
												// visina
		this.startY = (height / (Constants.FIELDS_VERTICAL)) * (Constants.FIELDS_VERTICAL) - diceWidth - margin;

		one = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.one), diceWidth, diceWidth);
		two = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.two), diceWidth, diceWidth);
		three = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.three), diceWidth, diceWidth);
		four = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.four), diceWidth, diceWidth);
		five = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.five), diceWidth, diceWidth);
		six = resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.six), diceWidth, diceWidth);

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

			canvas.drawBitmap(getBitmapByNumber(values[0]), startX + margin, startY, null);
			canvas.drawBitmap(getBitmapByNumber(values[1]), startX + one.getWidth() + margin, startY, null);
			canvas.drawBitmap(getBitmapByNumber(values[2]), startX + 2 * one.getWidth() + margin, startY, null);
			canvas.drawBitmap(getBitmapByNumber(values[3]), startX + 3 * one.getWidth() + margin, startY, null);
			canvas.drawBitmap(getBitmapByNumber(values[4]), startX + 4 * one.getWidth() + margin, startY, null);
			canvas.drawBitmap(getBitmapByNumber(values[5]), startX + 5 * one.getWidth() + margin, startY, null);
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

}
