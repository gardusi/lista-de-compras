package giovannigardusi.listadecompras.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import giovannigardusi.listadecompras.Activities.ListaOptions.ActivityAbrirLista;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Settings;
import giovannigardusi.listadecompras.Utils.Utils;

public class ActivityMenu extends AppCompatActivity {

    Activity activity = this;

    private TextView novaLista;
    private TextView abrirLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        novaLista = (TextView) findViewById(R.id.activity_menu_nova_lista);
        abrirLista = (TextView) findViewById(R.id.activity_menu_abrir_lista);

        novaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityListaDeCompras.class);
                intent.putParcelableArrayListExtra(ActivityListaDeCompras.PARAM_LISTA, null);
                startActivity(intent);
            }
        });

        abrirLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Settings.getInstance().isSimpleSave()) {
                    ArrayList<ModelProduto> listaDeCompras = Utils.readFile(activity, "simple_save_list.txt");
                    Intent intent = new Intent(activity, ActivityListaDeCompras.class);
                    intent.putParcelableArrayListExtra(ActivityListaDeCompras.PARAM_LISTA, listaDeCompras);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(activity, ActivityAbrirLista.class);
                    startActivity(intent);
                }
            }
        });
    }
}
