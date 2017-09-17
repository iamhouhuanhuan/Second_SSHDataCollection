package com.example.luxiang.sshdatacollection;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends Activity {

    EditText editText;
    ImageButton finishButton;
    TextView text;
    ImageButton startButton;
    Log logData = new Log();
    StopLog stop = new StopLog();
    public static int clickNum = 0;
    public static String suffix = ".dat";
    public static String hostname = "192.168.1.112";
    public static String username = "luxiang";
    public static String password = "root";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.filename);
        startButton = (ImageButton) findViewById(R.id.button_start);
        finishButton = (ImageButton) findViewById(R.id.button_finish);
        text = (TextView) findViewById(R.id.text);
    }

    public void startCollection(View v) {

        String txtResult;

        if (editText.getText().toString().equals("")) {
            txtResult = "Please input file name!";
            text.setText(txtResult);
            text.setTextColor(Color.RED);
            return;
        } else {
            txtResult = "Logging...";
            text.setText(txtResult);
            text.setTextColor(Color.BLACK);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                logData.sshConnect(editText.getText().toString());
            }
        }).start();
    }

    public void stopCollection(View v) {
        String txtResult;
        if (editText.getText().toString().equals("")) {
            txtResult = "Please click the start button to log!";
            text.setText(txtResult);
            text.setTextColor(Color.RED);
            text.setTextSize(25);
            return;
        }

        text.setText("Finish, please next one!");
        text.setTextColor(Color.GREEN);

        new Thread(new Runnable() {
            @Override
            public void run() {
                stop.stopLog();
            }
        }).start();
    }

    public void quit(View v) {
        if (clickNum == 0) {
            text.setTextColor(Color.RED);
            text.setText("Don't touch me!");
            clickNum = clickNum + 1;
        } else if (clickNum == 1) {
            clickNum = clickNum - 1;
            new Thread() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            text.setTextColor(Color.RED);
                            text.setText("I'm angry. Bye!");
                        }
                    });
                }
            }.start();
            this.finish();
        }
    }
}
