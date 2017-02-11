package giovannigardusi.listadecompras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import giovannigardusi.listadecompras.Activities.ActivityMenu;
import giovannigardusi.listadecompras.Utils.Settings;

public class ActivityMain extends AppCompatActivity {

    Activity activity = this;
    Handler mHandler;
    Runnable mNextActivityCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        //TODO [TEST] Configura Settings
        Settings.getInstance().setSimpleSave(false);
        Settings.getInstance().setShowCheck(true);

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
