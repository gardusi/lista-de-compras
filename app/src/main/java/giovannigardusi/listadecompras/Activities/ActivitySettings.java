package giovannigardusi.listadecompras.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Settings;

public class ActivitySettings extends AppCompatActivity {

    private Activity activity = this;

    private Switch simpleSaveSwitch;
    private Switch showCheckSwitch;
    private TextView salvarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Ativa botao de Voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Pega os IDs
        simpleSaveSwitch = (Switch) findViewById(R.id.activity_settings_simplesave);
        showCheckSwitch = (Switch) findViewById(R.id.activity_settings_check);
        salvarButton = (TextView) findViewById(R.id.activity_settings_salvar);

        // Inicializa com dados pre-configurados
        simpleSaveSwitch.setChecked(Settings.getInstance().isSimpleSave());
        showCheckSwitch.setChecked(Settings.getInstance().isShowCheck());

        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Settings.getInstance().setSimpleSave(simpleSaveSwitch.isChecked());
                Settings.getInstance().setShowCheck(showCheckSwitch.isChecked());
                activity.setResult(ActivityListaDeCompras.RESULT_SETTINGS);
                activity.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        activity.setResult(ActivityListaDeCompras.RESULT_BACK);
        activity.finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.setResult(ActivityListaDeCompras.RESULT_BACK);
                activity.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
