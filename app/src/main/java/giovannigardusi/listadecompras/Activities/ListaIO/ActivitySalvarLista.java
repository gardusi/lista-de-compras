package giovannigardusi.listadecompras.Activities.ListaIO;

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
import giovannigardusi.listadecompras.Utils.ReaderLista;
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
                String filename = arquivoEditText.getText().toString().trim();
                if (filename.equals(""))
                    arquivoEditText.setError(getString(R.string.activity_salvar_lista_rule2));
                else if (Utils.isAlphanumerical(filename)) {
                    boolean keepChecked = checkSwitch.isChecked();
                    ReaderLista.write(activity, filename, listaDeCompras, keepChecked);
                    Utils.hideKeyboard(activity);
                    activity.finish();
                } else arquivoEditText.setError(getString(R.string.activity_salvar_lista_rule));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Utils.hideKeyboard(activity);
                activity.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
