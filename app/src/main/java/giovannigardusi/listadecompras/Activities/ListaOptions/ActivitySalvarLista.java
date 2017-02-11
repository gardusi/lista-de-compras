package giovannigardusi.listadecompras.Activities.ListaOptions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import giovannigardusi.listadecompras.Activities.ActivityListaDeCompras;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Utils;

public class ActivitySalvarLista extends AppCompatActivity {

    private final String ENCRYPT_KEY = "";

    private Activity activity = this;

    private EditText arquivoEditText;
    private Switch checkSwitch;
    private TextView salvarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salvar_lista);

        // Recebe parametros da Activity anterior
        final ArrayList<ModelProduto> listaDeCompras = getIntent().getParcelableArrayListExtra(ActivityListaDeCompras.PARAM_LISTA);

        // Ativa botao de Voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Cria tela com teclado aberto
        Utils.showKeyboard(activity);

        // Pega os IDs
        arquivoEditText = (EditText) findViewById(R.id.activity_salvar_lista_arquivo);
        checkSwitch = (Switch) findViewById(R.id.activity_salvar_lista_check);
        salvarButton = (TextView) findViewById(R.id.activity_salvar_lista_salvar);

        salvarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = arquivoEditText.getText().toString();
                boolean keepChecked = checkSwitch.isChecked();
                Utils.writeFile(activity, filename, listaDeCompras, keepChecked);
                Utils.hideKeyboard(activity);
                activity.finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Utils.hideKeyboard(activity);
                activity.setResult(ActivityListaDeCompras.RESULT_NO_ITEM);
                activity.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
