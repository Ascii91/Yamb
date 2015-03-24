package com.etf.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
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
	
	
	public Dices(Context context)
	{
		super(context);

		initBitmaps(context);

	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawBitmap(one, startX+margin, startY, null);
		canvas.drawBitmap(one, startX + one.getWidth() + margin, startY, null);
		canvas.drawBitmap(one, startX + 2 * one.getWidth() + margin, startY, null);
		canvas.drawBitmap(one, startX + 3 * one.getWidth() + margin, startY, null);
		canvas.drawBitmap(one, startX + 4 * one.getWidth() + margin, startY, null);
		canvas.drawBitmap(one, startX + 5 * one.getWidth() + margin, startY, null);
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
		three =  resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.three), diceWidth, diceWidth);
		four =  resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.four), diceWidth, diceWidth);
		five =  resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.five), diceWidth, diceWidth);
		six =  resizeBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.six), diceWidth, diceWidth);
	}

	private Bitmap resizeBitmap(Bitmap b, int reqWidth, int reqHeight)
	{
		Matrix m = new Matrix();
		m.setRectToRect(new RectF(0, 0, b.getWidth(), b.getHeight()), new RectF(0, 0, reqWidth, reqHeight), Matrix.ScaleToFit.CENTER);
		return Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);

	}

}
