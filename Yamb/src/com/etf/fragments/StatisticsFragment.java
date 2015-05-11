package com.etf.fragments;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.etf.db.YambDb;
import com.etf.simulation.Igra;
import com.etf.utils.ListObject;
import com.etf.yamb.R;

public class StatisticsFragment extends Fragment implements ListAdapter
{
	private View view;
	private ListView lv;
	List<Igra> igre;
	List<ListObject> objectList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.statistics_fragment_layout, container, false);
		lv = (ListView) view.findViewById(R.id.list_view_statistics);

		YambDb yb = new YambDb(getActivity());

		igre = yb.getIgre();
		objectList = new ArrayList<ListObject>();

		Calendar calendar = Calendar.getInstance();

		for (Igra i : igre)
		{
			calendar.setTimeInMillis(i.getVreme());
			int mYear = calendar.get(Calendar.YEAR);
			int mMonth = calendar.get(Calendar.MONTH);
			int mDay = calendar.get(Calendar.DAY_OF_MONTH);

			ListObject lo = new ListObject();
			lo.setDate("" + mDay + "-" + mMonth + "-" + mYear);
			lo.setName("" + i.getPl1());
			lo.setPosition("2");
			lo.setScore("SCORE");

			objectList.add(lo);
		}

		lv.setAdapter(this);

		return view;
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount()
	{
		return igre.size();
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean hasStableIds()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			LayoutInflater vi = (LayoutInflater) this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.list_row, null);
			TextView tv1 = (TextView) convertView.findViewById(R.id.pozicija);
			TextView tv2 = (TextView) convertView.findViewById(R.id.name);
			TextView tv3 = (TextView) convertView.findViewById(R.id.score);
			TextView tv4 = (TextView) convertView.findViewById(R.id.datum);

			tv1.setText(objectList.get(position).getPosition());

			tv2.setText(objectList.get(position).getName());

			tv3.setText(objectList.get(position).getScore());

			tv4.setText(objectList.get(position).getDate());
		}
		return convertView;
	}

	@Override
	public int getItemViewType(int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount()
	{
		return 1;
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
