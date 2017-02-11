package giovannigardusi.listadecompras.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import giovannigardusi.listadecompras.R;

public class ActivitySettings extends AppCompatActivity {

    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
