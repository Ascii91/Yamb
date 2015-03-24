package com.etf.view;

import com.etf.model.FieldData;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.widget.ImageView;

public class Field extends ImageView
{

	private Context context;
	private FieldData fieldData;
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

		if (type == 0)
		{
			paint.setColor(Color.RED);

		} else if (type == 1)
		{
			paint.setColor(Color.BLUE);
		} else if (type == 2)
		{
			paint.setColor(Color.YELLOW);
		}
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);

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
}
