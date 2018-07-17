package paluch.ivanna.fishifish;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private FlyingFIshView gameView;
    private Handler handler = new Handler();
    private final static long Interval =30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameView = new FlyingFIshView(this);
        setContentView(gameView);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       gameView.invalidate();
                    }
                });
            }
        },0, Interval);
    }
}
