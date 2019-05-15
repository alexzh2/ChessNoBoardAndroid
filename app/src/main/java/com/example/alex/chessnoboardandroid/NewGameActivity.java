package com.example.alex.chessnoboardandroid;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

public class NewGameActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private static final String TAG = MainApp.MainTag + UCIWrapper.class.getSimpleName();

    TextView strengthLabel;
    String origLabel;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);



        final SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);
        strengthLabel = findViewById(R.id.strengthLabel);
        origLabel = strengthLabel.getText().toString();

        String jsonObj = getIntent().getStringExtra("prevParm");
        NewGameParams prevParm = (new Gson()).fromJson(jsonObj, NewGameParams.class);

        spinner = findViewById(R.id.spinner);
        spinner.setSelection(prevParm.boardMode.getIndex());

        Log.d(TAG, String.format("onCreate NewGameActivity. strengthLabel=%s", Boolean.toString(strengthLabel!=null)));

        seekBar.setProgress(prevParm.compStrength);
        updateLabel(seekBar.getProgress());
    }

    public void onStart(View view) {

        NewGameParams newGameParm = new NewGameParams();

        int pos = spinner.getSelectedItemPosition();
        newGameParm.boardMode = BoardMode.fromId(pos);

        newGameParm.compStrength = ((SeekBar) findViewById(R.id.seekBar)).getProgress();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("newGameParm", (new Gson()).toJson(newGameParm));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    void updateLabel(int progress) {
        strengthLabel.setText(origLabel + " " + Integer.toString(progress));
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        updateLabel(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
