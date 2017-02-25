package giovannigardusi.listadecompras;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.io.IOException;

import giovannigardusi.listadecompras.Activities.ActivityMenu;
import giovannigardusi.listadecompras.Utils.ReaderSettings;
import giovannigardusi.listadecompras.Utils.Settings;

public class ActivityMain extends AppCompatActivity {

    Activity activity = this;
    Handler mHandler;
    Runnable mNextActivityCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        // Load settings
        try {
            ReaderSettings.read(activity);
        } catch (IOException err) {
            Settings.getInstance().setShowCheck(true);
            Settings.getInstance().setSimpleSave(true);
        }

        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        mNextActivityCallback = new Runnable() {
            @Override
            public void run() {
                // Intent to jump to the next activity
                Intent intent = new Intent(activity, ActivityMenu.class);
                startActivity(intent);
                finish(); // so the splash activity goes away
            }
        };
        mHandler.postDelayed(mNextActivityCallback, 2000L);
    }
}
