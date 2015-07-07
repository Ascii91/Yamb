package com.etf.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.etf.controller.Controler;
import com.etf.db.YambDb;
import com.etf.simulation.Bacanje;
import com.etf.simulation.Igra;
import com.etf.utils.Calculator;
import com.etf.view.Field;
import com.etf.yamb.R;

public class SimulationActivity extends Activity implements OnClickListener
{

	private int redniBrojBacanja = 0;
	private int ukupanBrojBacanja;
	private Igra igra;
	private ImageView leftArrow;
	private ImageView rightArrow;
	private Bacanje trenutnoBacanje;
	private int ukupnoIgraca;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simulation_activity_layout);

		leftArrow = (ImageView) findViewById(R.id.imageView1);
		rightArrow = (ImageView) findViewById(R.id.imageView2);

		leftArrow.setOnClickListener(this);
		rightArrow.setOnClickListener(this);

		Bundle extras = getIntent().getExtras();
		int pos = extras.getInt("position");

		// Step 1 get games
		YambDb yb = new YambDb(this);
		List<Igra> igre = yb.getIgre();
		this.igra = igre.get(pos);
		ukupanBrojBacanja = igra.getLista().size();

		ukupnoIgraca = 1;
		if (!igra.getPl2().equals("noPlayer"))
		{
			ukupnoIgraca = 2;
		}
		if (!igra.getPl3().equals("noPlayer"))
		{
			ukupnoIgraca = 3;
		}
		if (!igra.getPl4().equals("noPlayer"))
		{
			ukupnoIgraca = 4;
		}

	}

	@Override
	protected void onResume()
	{
		super.onResume();
		Controler.getControler().setSimulation(true);
		Log.e("IGRA", "" + igra.toString());
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		Controler.getControler().setSimulation(false);
	}

	public int getRedniBrojBacanja()
	{
		return redniBrojBacanja;
	}

	public void setRedniBrojBacanja(int redniBrojBacanja)
	{
		this.redniBrojBacanja = redniBrojBacanja;
	}

	public Igra getIgra()
	{
		return igra;
	}

	public void setIgra(Igra igra)
	{
		this.igra = igra;
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.imageView1:
			Log.e("Left", "true");
			redniBrojBacanja--;
			if (redniBrojBacanja <= 0)
			{
				redniBrojBacanja = 0;
			}
			trenutnoBacanje = igra.getLista().get(redniBrojBacanja);

			break;
		case R.id.imageView2:

			Log.e("Right", "true");
			redniBrojBacanja++;
			if (redniBrojBacanja >= ukupanBrojBacanja)
			{
				redniBrojBacanja = ukupanBrojBacanja;
			}
			trenutnoBacanje = igra.getLista().get(redniBrojBacanja);
			break;
		default:
			break;
		}

		fillSimulationTable(redniBrojBacanja);

	}

	private void fillSimulationTable(int redniBrojBacanja)
	{

		String lastThrow = trenutnoBacanje.getBacanje1();
		Controler.getControler().setBrojBacanja(1);
		if (!(trenutnoBacanje.getBacanje2().equals("") || trenutnoBacanje == null))
		{
			lastThrow = trenutnoBacanje.getBacanje2();
			Controler.getControler().setBrojBacanja(2);
		}
		if (!(trenutnoBacanje.getBacanje3().equals("") || trenutnoBacanje == null))
		{
			lastThrow = trenutnoBacanje.getBacanje3();
			Controler.getControler().setBrojBacanja(3);
		}

		char[] charValues = lastThrow.toCharArray();
		int[] values = new int[6];
		for (int i = 0; i < 6; i++)
		{
			values[i] = Integer.parseInt("" + charValues[i]);
		}

		switch (redniBrojBacanja % ukupnoIgraca)
		{
		case 0:
			Controler.getControler().setPlayerName(igra.getPl1());
			Controler.getControler().getBoard().setFields(Controler.getControler().getBoard().getFieldsPlayer1());
			for (Field f : Controler.getControler().getBoard().getFieldsPlayer1())
			{
				f.getFieldData().setFieldValue(-1);
			}
			for (int i = 0; i < redniBrojBacanja; i++)
			{
				for (Field f : Controler.getControler().getBoard().getFieldsPlayer1())
				{
					if (f.getFieldData().getFieldX() == igra.getLista().get(i).getX() && f.getFieldData().getFieldY() == igra.getLista().get(i).getY())
					{
						f.getFieldData().setFieldValue(igra.getLista().get(i).getValue());
					}
				}
			}

			break;
		case 1:
			Controler.getControler().setPlayerName(igra.getPl2());
			Controler.getControler().getBoard().setFields(Controler.getControler().getBoard().getFieldsPlayer2());
			for (Field f : Controler.getControler().getBoard().getFieldsPlayer2())
			{
				f.getFieldData().setFieldValue(-1);
			}

			for (int i = 0; i < redniBrojBacanja; i++)
			{
				for (Field f : Controler.getControler().getBoard().getFieldsPlayer2())
				{
					if (f.getFieldData().getFieldX() == igra.getLista().get(i).getX() && f.getFieldData().getFieldY() == igra.getLista().get(i).getY())
					{
						f.getFieldData().setFieldValue(igra.getLista().get(i).getValue());
					}
				}
			}

			break;
		case 2:
			Controler.getControler().setPlayerName(igra.getPl3());
			Controler.getControler().getBoard().setFields(Controler.getControler().getBoard().getFieldsPlayer3());
			for (Field f : Controler.getControler().getBoard().getFieldsPlayer3())
			{
				f.getFieldData().setFieldValue(-1);
			}

			for (int i = 0; i < redniBrojBacanja; i++)
			{
				for (Field f : Controler.getControler().getBoard().getFieldsPlayer3())
				{
					if (f.getFieldData().getFieldX() == igra.getLista().get(i).getX() && f.getFieldData().getFieldY() == igra.getLista().get(i).getY())
					{
						f.getFieldData().setFieldValue(igra.getLista().get(i).getValue());
					}
				}
			}

			break;
		case 3:
			Controler.getControler().setPlayerName(igra.getPl4());
			Controler.getControler().getBoard().setFields(Controler.getControler().getBoard().getFieldsPlayer4());
			for (Field f : Controler.getControler().getBoard().getFieldsPlayer4())
			{
				f.getFieldData().setFieldValue(-1);
			}

			for (int i = 0; i < redniBrojBacanja; i++)
			{
				for (Field f : Controler.getControler().getBoard().getFieldsPlayer4())
				{
					if (f.getFieldData().getFieldX() == igra.getLista().get(i).getX() && f.getFieldData().getFieldY() == igra.getLista().get(i).getY())
					{
						f.getFieldData().setFieldValue(igra.getLista().get(i).getValue());
					}
				}
			}

			break;
		}

		Calculator.calculateSum(Controler.getControler().getBoard().getFields());
		Controler.getControler().setValues(values);
		Controler.getControler().getDices().setValues(values);
		Controler.getControler().getBoard().invalidate();
	}

	public int getUkupnoIgraca()
	{
		return ukupnoIgraca;
	}

	public void setUkupnoIgraca(int ukupnoIgraca)
	{
		this.ukupnoIgraca = ukupnoIgraca;
	}
}
