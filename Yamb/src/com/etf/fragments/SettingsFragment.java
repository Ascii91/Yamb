package com.etf.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.etf.controller.Controler;
import com.etf.utils.Constants;
import com.etf.yamb.R;

public class SettingsFragment extends Fragment implements OnClickListener, OnSeekBarChangeListener
{

    private View     view;
    private SeekBar  seekBar;
    private Button   sacuvaj;
    private Button   nazad;
    private TextView progressText;
    private CheckBox cb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.settings_fragment_layout, container, false);
        try
        {
            Controler.getControler().getBoard().disableShaking();
        }
        catch (Exception e)
        {}
        seekBar = (SeekBar) view.findViewById(R.id.seekBar1);
        sacuvaj = (Button) view.findViewById(R.id.btn_sacuvaj);
        nazad = (Button) view.findViewById(R.id.btn_nazad);
        progressText = (TextView) view.findViewById(R.id.progress_text);
        cb = (CheckBox) view.findViewById(R.id.check_box_zvuk);

        sacuvaj.setOnClickListener(this);
        nazad.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);

        SharedPreferences prefs = this.getActivity().getSharedPreferences(Constants.SETTINGS, Context.MODE_PRIVATE);

        int progress = prefs.getInt(Constants.OSETLJIVOST, -1);
        boolean sound = prefs.getBoolean(Constants.ZVUK, true);

        if (progress == -1)
        {
            progress = 50;
            sound = true;

            cb.setChecked(sound);
            seekBar.setProgress(progress);
            save();

        }

        cb.setChecked(sound);
        seekBar.setProgress(progress);

        return view;
    }

    @Override
    public void onClick(View v)
    {

        FragmentManager fm = this.getActivity().getFragmentManager();

        switch (v.getId())
        {
            case R.id.btn_sacuvaj :
                save();
                fm.popBackStack();
                break;
            case R.id.btn_nazad :
                fm.popBackStack();
                break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    {
        progressText.setText("" + progress + "%");

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {

    }

    private void save()
    {

        saveProgressToPrefs(Constants.SETTINGS, Constants.OSETLJIVOST, seekBar.getProgress());
        saveSoundToPrefs(Constants.SETTINGS, Constants.ZVUK, cb.isChecked());
        Controler.getControler().resetControler();
        Toast.makeText(this.getActivity(), R.string.changes_saved, Toast.LENGTH_SHORT).show();
    }

    private void saveSoundToPrefs(String prefsName, String prefsValue, boolean value)
    {
        SharedPreferences settings = getActivity().getSharedPreferences(prefsName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(prefsValue, value);
        editor.commit();

    }

    private void saveProgressToPrefs(String prefsName, String prefsValue, int value)
    {
        SharedPreferences settings = getActivity().getSharedPreferences(prefsName, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(prefsValue, value);
        editor.commit();

    }
}
