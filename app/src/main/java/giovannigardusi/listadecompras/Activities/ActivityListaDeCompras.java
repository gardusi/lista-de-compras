package giovannigardusi.listadecompras.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import giovannigardusi.listadecompras.Activities.ListaOptions.ActivityImprimirLista;
import giovannigardusi.listadecompras.Activities.ListaOptions.ActivitySalvarLista;
import giovannigardusi.listadecompras.Adapters.AdapterListaDeCompras;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Settings;
import giovannigardusi.listadecompras.Utils.Utils;

public class ActivityListaDeCompras extends AppCompatActivity {

    public final static String PARAM_LISTA = "listaDeCompras";
    public final static String PARAM_SETTINGS = "settings";

    public final static int RESULT_NO_ITEM = 0;
    public final static int RESULT_NEW_ITEM = 1;
    public final static int RESULT_EDIT_ITEM = 2;
    public final static int RESULT_SETTINGS = 3;

    private Activity activity = this;

    private ListView listaDeComprasView;
    private ImageView adicionarButton;

    private AdapterListaDeCompras adapterListaDeCompras;

    private ArrayList<ModelProduto> listaDeCompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_compras);

        // Recebe parametros salvos antes de onStop()
        if (savedInstanceState != null) {
            listaDeCompras = savedInstanceState.getParcelableArrayList(PARAM_LISTA);
        } else {
            // Recebe parametros da Activity anterior
            listaDeCompras = getIntent().getParcelableArrayListExtra(PARAM_LISTA);
            if (listaDeCompras == null) {
                listaDeCompras = new ArrayList<>();
            }
        }

        // Configura Support Action Bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        // Pega os IDs
        listaDeComprasView = (ListView) findViewById(R.id.activity_lista_de_compras_lista);
        adicionarButton = (ImageView) findViewById(R.id.activity_lista_de_compras_adicionar);

        // Pinta sinal (+) na cor branca
        adicionarButton.setColorFilter(Color.WHITE);

        adicionarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityNovoItem.class);
                startActivityForResult(intent, RESULT_NEW_ITEM);
            }
        });

        adapterListaDeCompras = new AdapterListaDeCompras(activity, listaDeCompras);
        listaDeComprasView.setAdapter(adapterListaDeCompras);
        listaDeComprasView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, ActivityNovoItem.class);
                intent.putExtra(ActivityNovoItem.PARAM_ITEM, listaDeCompras.get(position));
                intent.putExtra(ActivityNovoItem.PARAM_POS, position);
                startActivityForResult(intent, RESULT_EDIT_ITEM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == resultCode) {
            ModelProduto item = data.getParcelableExtra(ActivityNovoItem.PARAM_ITEM);
            switch (resultCode) {
                case RESULT_NEW_ITEM:
                    listaDeCompras.add(item);
                    break;
                case RESULT_EDIT_ITEM:
                    int position = data.getIntExtra(ActivityNovoItem.PARAM_POS, -1);
                    listaDeCompras.set(position, item);
                    break;
                case RESULT_SETTINGS:

            }
            adapterListaDeCompras.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_de_compras, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                saveAlert();
                return true;
            // Tela de Salvar Lista
            case R.id.menu_lista_de_compras_salvar:
                if (Settings.getInstance().isSimpleSave()) {
                    Utils.writeFile(activity, "simple_save_list.txt", listaDeCompras, true);
                    Toast.makeText(activity, "Lista salva!", Toast.LENGTH_LONG).show();
                } else {
                    Intent intentSave = new Intent(activity, ActivitySalvarLista.class);
                    intentSave.putParcelableArrayListExtra(PARAM_LISTA, listaDeCompras);
                    startActivity(intentSave);
                }
                return true;
            // Tela de Imprimir Lista
            case R.id.menu_lista_de_compras_imprimir:
                Intent intentPrint = new Intent(activity, ActivityImprimirLista.class);
                intentPrint.putParcelableArrayListExtra(PARAM_LISTA, listaDeCompras);
                startActivity(intentPrint);
                return true;
            // Tela de Configuracoes
            case R.id.menu_lista_de_compras_settings:
                Intent intentSettings = new Intent(activity, ActivitySettings.class);
                startActivityForResult(intentSettings, RESULT_SETTINGS);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        instanceState.putParcelableArrayList(PARAM_LISTA, listaDeCompras);
        super.onSaveInstanceState(instanceState);
    }

    @Override
    public void onBackPressed() {
        saveAlert();
    }

    private void saveAlert() {
        if (listaDeCompras.size() > 0) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            activity.finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage(getString(R.string.activity_lista_de_compras_sair))
                    .setPositiveButton("Sair sem Salvar", dialogClickListener)
                    .setNegativeButton("Voltar", dialogClickListener).show();
        } else {
            activity.finish();
        }
    }
}
