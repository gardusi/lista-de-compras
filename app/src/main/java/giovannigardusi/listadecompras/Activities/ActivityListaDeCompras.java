package giovannigardusi.listadecompras.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import giovannigardusi.listadecompras.Activities.ListaIO.ActivityImprimirLista;
import giovannigardusi.listadecompras.Activities.ListaIO.ActivitySalvarLista;
import giovannigardusi.listadecompras.Adapters.AdapterListaDeCompras;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Constants;
import giovannigardusi.listadecompras.Utils.ReaderLista;
import giovannigardusi.listadecompras.Utils.Settings;

public class ActivityListaDeCompras extends AppCompatActivity {

    public final static String PARAM_LISTA = "listaDeCompras";
    public final static String PARAM_SAVED = "isSaved";

    public final static int RESULT_BACK = 0;
    public final static int RESULT_NEW_ITEM = 1;
    public final static int RESULT_EDIT_ITEM = 2;
    public final static int RESULT_SAVED_LIST = 3;
    public final static int RESULT_SETTINGS = 4;

    private Activity activity = this;

    private ListView listaDeComprasView;
    private FloatingActionButton adicionarButton;

    private AdapterListaDeCompras adapterListaDeCompras;

    private ArrayList<ModelProduto> listaDeCompras;
    private boolean isSaved;

    public interface OnCheckListener { void onCheck(); }
    public OnCheckListener onCheckListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_compras);

        // Recebe parametros salvos antes de onStop()
        if (savedInstanceState != null) {
            listaDeCompras = savedInstanceState.getParcelableArrayList(PARAM_LISTA);
            isSaved = savedInstanceState.getBoolean(PARAM_SAVED);
        } else {
            // Recebe parametros da Activity anterior
            isSaved = true;
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
        adicionarButton = (FloatingActionButton) findViewById(R.id.activity_lista_de_compras_adicionar);

        onCheckListener = new OnCheckListener() {
            @Override
            public void onCheck() { isSaved = false; }
        };
        listaDeComprasView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_IDLE:
                        adicionarButton.show();
                        break;
                    case SCROLL_STATE_FLING:
                    case SCROLL_STATE_TOUCH_SCROLL:
                        adicionarButton.hide();
                        break;
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) { }
        });

        adapterListaDeCompras = new AdapterListaDeCompras(activity, listaDeCompras, onCheckListener);
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
            switch (resultCode) {
                case RESULT_NEW_ITEM:
                    ModelProduto newItem = data.getParcelableExtra(ActivityNovoItem.PARAM_ITEM);
                    listaDeCompras.add(newItem);
                    isSaved = false;
                    break;
                case RESULT_EDIT_ITEM:
                    ModelProduto editItem = data.getParcelableExtra(ActivityNovoItem.PARAM_ITEM);
                    int position = data.getIntExtra(ActivityNovoItem.PARAM_POS, -1);
                    listaDeCompras.set(position, editItem);
                    isSaved = false;
                    break;
                case RESULT_SAVED_LIST:
                    isSaved = true;
                    break;
                case RESULT_SETTINGS:
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
                if (Settings.getInstance().isSimpleSave()) {
                    ReaderLista.write(activity, Constants.SIMPLE_SAVE_NAME, listaDeCompras, true);
                    Toast.makeText(activity, getString(R.string.activity_lista_de_compras_saved), Toast.LENGTH_LONG).show();
                } else {
                    Intent intentSave = new Intent(activity, ActivitySalvarLista.class);
                    intentSave.putParcelableArrayListExtra(PARAM_LISTA, listaDeCompras);
                    startActivityForResult(intentSave, RESULT_SAVED_LIST);
                }
                return true;
            // Tela de Imprimir Lista
            case R.id.menu_lista_de_compras_imprimir:
                if (listaDeCompras.size() > 0) {
                    Intent intentPrint = new Intent(activity, ActivityImprimirLista.class);
                    intentPrint.putParcelableArrayListExtra(PARAM_LISTA, listaDeCompras);
                    startActivity(intentPrint);
                } else {
                    Toast.makeText(activity, getString(R.string.activity_lista_de_compras_vazia), Toast.LENGTH_LONG).show();
                }
                return true;
            // Enviar Lista
//            case R.id.menu_lista_de_compras_enviar:
//                Bitmap bmp = Screenshot.takeScreenshot(activity, R.id.activity_lista_de_compras_lista);
//                Screenshot.onClickApp(activity, "Lista de Compras", bmp);
//                return true;
            // Tela de Configuracoes
            case R.id.menu_lista_de_compras_settings:
                Intent intentSettings = new Intent(activity, ActivitySettings.class);
                startActivityForResult(intentSettings, RESULT_SETTINGS);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        instanceState.putParcelableArrayList(PARAM_LISTA, listaDeCompras);
        instanceState.putBoolean(PARAM_SAVED, isSaved);
        super.onSaveInstanceState(instanceState);
    }

    @Override
    public void onBackPressed() {
        saveAlert();
    }

    private void saveAlert() {
        if (!isSaved) {
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

    public void adicionarItem(View v) {
        Intent intent = new Intent(activity, ActivityNovoItem.class);
        startActivityForResult(intent, RESULT_NEW_ITEM);
    }
}
