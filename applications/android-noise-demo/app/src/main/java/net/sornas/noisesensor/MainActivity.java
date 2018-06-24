package net.sornas.noisesensor;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private Handler handler;
    private SoundMeter soundMeter;

    private String url = "194.12.153.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textview_helloworld);

        soundMeter = new SoundMeter();
        soundMeter.start();



        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                tcpClient = new TcpClient();
                tcpClient.run();
                Log.w("tcp", "tcpClient.run()");
            }
        });
        thread.start();

        handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                double amp = soundMeter.getAmplitude();
                updateLabel(amp);
                publishData(amp);

                handler.postDelayed(this, 1000);
            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundMeter.stop();
    }

    private TextView textView;
    private void updateLabel(double amp) {
        textView.setText(Double.toString(amp));
    }

    private TcpClient tcpClient;
    private void publishData(double amp) {
        String msgPayload = "audio value="
                + amp
                + " "
                + System.currentTimeMillis() * 1000 * 1000;

        tcpClient.sendMessage(msgPayload);
    }
}