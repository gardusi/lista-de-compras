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

import java.util.ArrayList;

import giovannigardusi.listadecompras.Activities.ListaOptions.ActivityImprimirLista;
import giovannigardusi.listadecompras.Activities.ListaOptions.ActivitySalvarLista;
import giovannigardusi.listadecompras.Adapters.AdapterListaDeCompras;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Constants;

public class ActivityListaDeCompras extends AppCompatActivity {

    public final static String PARAM_OBJ = "listaDeCompras";

    public final static int NO_ITEM = 0;
    public final static int NEW_ITEM = 1;
    public final static int EDIT_ITEM = 2;

    Activity activity = this;

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
            listaDeCompras = savedInstanceState.getParcelableArrayList(PARAM_OBJ);
        } else {
            // Recebe parametros da Activity anterior
            listaDeCompras = getIntent().getParcelableArrayListExtra(PARAM_OBJ);
            if (listaDeCompras == null) {
                listaDeCompras = new ArrayList<>();
            }
        }

        // Ativa botao de Voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Pega os IDs
        listaDeComprasView = (ListView) findViewById(R.id.activity_lista_de_compras_lista);
        adicionarButton = (ImageView) findViewById(R.id.activity_lista_de_compras_adicionar);

        // Pinta sinal (+) na cor branca
        adicionarButton.setColorFilter(Color.WHITE);

        adicionarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityNovoItem.class);
                startActivityForResult(intent, NEW_ITEM);
            }
        });

        adapterListaDeCompras = new AdapterListaDeCompras(activity, listaDeCompras);
        listaDeComprasView.setAdapter(adapterListaDeCompras);
        listaDeComprasView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, ActivityNovoItem.class);
                intent.putExtra(ActivityNovoItem.PARAM_OBJ, listaDeCompras.get(position));
                intent.putExtra(ActivityNovoItem.PARAM_POS, position);
                startActivityForResult(intent, EDIT_ITEM);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == resultCode) {
            ModelProduto item = data.getParcelableExtra(ActivityNovoItem.PARAM_OBJ);
            switch (resultCode) {
                case NEW_ITEM:
                    listaDeCompras.add(item);
                    break;
                case EDIT_ITEM:
                    int position = data.getIntExtra(ActivityNovoItem.PARAM_POS, -1);
                    listaDeCompras.set(position, item);
                    break;
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
                Intent intentSave = new Intent(activity, ActivitySalvarLista.class);
                intentSave.putParcelableArrayListExtra(PARAM_OBJ, listaDeCompras);
                startActivity(intentSave);
                return true;
            // Tela de Imprimir Lista
            case R.id.menu_lista_de_compras_imprimir:
                Intent intentPrint = new Intent(activity, ActivityImprimirLista.class);
                intentPrint.putParcelableArrayListExtra(PARAM_OBJ, listaDeCompras);
                startActivity(intentPrint);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        instanceState.putParcelableArrayList(PARAM_OBJ, listaDeCompras);
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
            builder.setMessage(Constants.SAVE_MSG).setPositiveButton("Sair sem Salvar", dialogClickListener)
                    .setNegativeButton("Voltar", dialogClickListener).show();
        } else {
            activity.finish();
        }
    }
}
