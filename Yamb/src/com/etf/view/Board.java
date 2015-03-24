package com.etf.view;

import java.util.ArrayList;
import java.util.List;

import com.etf.model.FieldData;
import com.etf.utils.Constants;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

public class Board extends ImageView
{

	private List<Field> fields;

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
		Log.e("INIT", "BOARD");
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

	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		for (Field f : fields)
		{
			f.draw(canvas);

		}
		new Dices(this.getContext()).draw(canvas);

	}

}
