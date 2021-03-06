package com.etf.activities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.etf.db.YambDb;
import com.etf.simulation.Igra;
import com.etf.utils.ListObject;
import com.etf.yamb.R;

// Fragment zadu�en za statistiku prethodnih igara
public class StatisticsActivity extends Activity implements ListAdapter, OnItemClickListener
{

    private ListView lv;
    private Button   deleteAllButton;
    private YambDb   yb;
    List<Igra>       igre;
    List<ListObject> objectList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_fragment_layout);
        lv = (ListView) findViewById(R.id.list_view_statistics);
        deleteAllButton = (Button) findViewById(R.id.button_delete_all);
        yb = new YambDb(this);
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
            lo.setDate("" + mDay + "." + mMonth + "." + mYear);
            lo.setName("" + i.getWinnerName());
            lo.setScore(i.getWinnerScore());

            objectList.add(lo);
        }
        Collections.sort(objectList);

        for (int i = 0; i < objectList.size(); i++)
        {
            objectList.get(i).setPosition("" + (i + 1));
        }

        lv.setAdapter(this);
        lv.setOnItemClickListener(this);
        deleteAllButton.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                yb.deleteAll();
                objectList = new ArrayList<ListObject>();

            }
        });

    }

    // Automatski generisane metode
    @Override
    public void registerDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer)
    {

    }

    @Override
    public int getCount()
    {
        return igre.size();
    }

    @Override
    public Object getItem(int position)
    {

        return null;
    }

    @Override
    public long getItemId(int position)
    {

        return 0;
    }

    @Override
    public boolean hasStableIds()
    {

        return false;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            LayoutInflater vi = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_row, null);
            TextView tv1 = (TextView) convertView.findViewById(R.id.pozicija);
            TextView tv2 = (TextView) convertView.findViewById(R.id.name);
            TextView tv3 = (TextView) convertView.findViewById(R.id.score);
            TextView tv4 = (TextView) convertView.findViewById(R.id.datum);

            tv1.setText(objectList.get(position).getPosition());

            tv2.setText(objectList.get(position).getName());

            tv3.setText("" + objectList.get(position).getScore());

            tv4.setText(objectList.get(position).getDate());
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position)
    {
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

        return false;
    }

    @Override
    public boolean areAllItemsEnabled()
    {

        return true;
    }

    @Override
    public boolean isEnabled(int position)
    {

        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

        Intent i = new Intent(this, SimulationActivity.class);

        i.putExtra("position", position);
        startActivity(i);

    }
    // new AlertDialog.Builder(this).setTitle(R.string.quit_title).setMessage(R.string.simulation_message).setPositiveButton(android.R.string.yes,
    // new DialogInterface.OnClickListener()
    // {
    // public void onClick(DialogInterface dialog, int which)
    // {
    //
    //
    // //TODO: pokrenuti aktivnost simulacije sa rednim brojem igre
    //
    // }
    // }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener()
    // {
    // public void onClick(DialogInterface dialog, int which)
    // {
    //
    // }
    // }).setIcon(android.R.drawable.ic_dialog_info).show();
    //
    // }

}
