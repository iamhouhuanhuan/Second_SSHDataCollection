package com.example.luxiang.sshdatacollection;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    public static String rec;
    EditText editText;
    Button startButton;
    Button finishButton;
    TextView text;
    Log logData = new Log();
    StopLog stop = new StopLog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.filename);
        startButton = (Button) findViewById(R.id.button_start);
        finishButton = (Button) findViewById(R.id.button_finish);
        text = (TextView) findViewById(R.id.text);
    }

    public void startCollection(View v) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                rec = logData.sshConnect(editText.getText().toString());
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!rec.equals("Please input file name!")) {
            text.setTextColor(Color.BLACK);
            text.setText(rec);
        } else {
            text.setTextColor(Color.RED);
            text.setText(rec);
        }

    }

    public void stopCollection(View v) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                stop.stopLog();
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        text.setText("Finish, please next one.");
        text.setTextColor(Color.GREEN);
    }
}
