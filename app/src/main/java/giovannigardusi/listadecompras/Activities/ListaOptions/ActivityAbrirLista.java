package giovannigardusi.listadecompras.Activities.ListaOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import giovannigardusi.listadecompras.Activities.ActivityListaDeCompras;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Utils;

public class ActivityAbrirLista extends AppCompatActivity {

    private Activity activity = this;

    private EditText arquivoEditText;
    private TextView abrirButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_lista);

        // Ativa botao de Voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Cria tela com teclado aberto
        Utils.showKeyboard(activity);

        // Pega os IDs
        arquivoEditText = (EditText) findViewById(R.id.activity_abrir_lista_arquivo);
        abrirButton = (TextView) findViewById(R.id.activity_abrir_lista_abrir);

        abrirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = arquivoEditText.getText().toString();
                ArrayList<ModelProduto> listaDeCompras = Utils.readFile(activity, filename);
                Intent intent = new Intent(activity, ActivityListaDeCompras.class);
                intent.putParcelableArrayListExtra(ActivityListaDeCompras.PARAM_LISTA, listaDeCompras);
                Utils.hideKeyboard(activity);
                startActivity(intent);
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
