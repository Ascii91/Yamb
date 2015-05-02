package com.etf.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

import com.etf.model.FieldData;

public class Field extends ImageView
{

	private Context context;
	private FieldData fieldData;
	private Bitmap fieldBitmap;

	private Paint paint;
	private int a, b, c, d;
	private int type = 0;

	public Field(Context context, FieldData fieldData, int type)
	{
		super(context);
		this.context = context;
		this.setFieldData(fieldData);
		this.type = type;

		paint = new Paint();

		a = fieldData.getFieldX();
		b = fieldData.getFieldY();
		c = a + fieldData.getFieldWidth();
		d = b + fieldData.getFieldHeight();

		if (type == 0) // okvir polja
		{
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

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (type != -1)
		{
			canvas.drawRect(a, b, c, d, paint);
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
}
