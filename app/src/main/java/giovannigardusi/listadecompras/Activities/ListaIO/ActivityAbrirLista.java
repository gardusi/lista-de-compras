package giovannigardusi.listadecompras.Activities.ListaIO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import giovannigardusi.listadecompras.Activities.ActivityListaDeCompras;
import giovannigardusi.listadecompras.Adapters.AdapterArquivo;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Constants;
import giovannigardusi.listadecompras.Utils.ReaderLista;

public class ActivityAbrirLista extends AppCompatActivity {

    private Activity activity = this;

//    private EditText arquivoEditText;
//    private TextView abrirButton;
    private ListView arquivosView;
    private AdapterArquivo arquivosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_lista);

        // Ativa botao de Voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arquivosView = (ListView) findViewById(R.id.activity_abrir_lista_arquivos);

        // Remocao de arquivos indesejados
        File instantRun = null, simpleSave = null;
        ArrayList<File> files = new ArrayList<>(Arrays.asList(activity.getFilesDir().listFiles()));
        for (File file : files) {
            if (file.getName().equals("instant-run")) instantRun = file;
            else if (file.getName().equals(Constants.SIMPLE_SAVE_NAME+".txt")) simpleSave = file;
        }
        if (instantRun != null) files.remove(instantRun);
        if (simpleSave != null) files.remove(simpleSave);

        arquivosAdapter = new AdapterArquivo(activity, files);
        arquivosView.setAdapter(arquivosAdapter);
        arquivosView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = arquivosAdapter.getFile(position);
                ArrayList<ModelProduto> lista = ReaderLista.read(activity, file.getName());
                Intent intent = new Intent(activity, ActivityListaDeCompras.class);
                intent.putParcelableArrayListExtra(ActivityListaDeCompras.PARAM_LISTA, lista);
                startActivity(intent);
                activity.finish();
            }
        });

        // Cria tela com teclado aberto
//        Utils.showKeyboard(activity);

        // Pega os IDs
//        arquivoEditText = (EditText) findViewById(R.id.activity_abrir_lista_arquivo);
//        abrirButton = (TextView) findViewById(R.id.activity_abrir_lista_abrir);
//
//        abrirButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String filename = arquivoEditText.getText().toString();
//                ArrayList<ModelProduto> listaDeCompras = ReaderLista.read(activity, filename);
//                Intent intent = new Intent(activity, ActivityListaDeCompras.class);
//                intent.putParcelableArrayListExtra(ActivityListaDeCompras.PARAM_LISTA, listaDeCompras);
//                Utils.hideKeyboard(activity);
//                startActivity(intent);
//                activity.finish();
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                Utils.hideKeyboard(activity);
                activity.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
