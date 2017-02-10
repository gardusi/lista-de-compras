package giovannigardusi.listadecompras.Activities.ListaOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import giovannigardusi.listadecompras.Activities.ActivityListaDeCompras;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Utils;

public class ActivityAbrirLista extends AppCompatActivity {

    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_lista);

        ArrayList<ModelProduto> listaDeCompras = Utils.readFile(activity, "lista_teste.txt");

        Intent intent = new Intent(activity, ActivityListaDeCompras.class);
        intent.putParcelableArrayListExtra(ActivityListaDeCompras.PARAM_OBJ, listaDeCompras);
        startActivity(intent);
        activity.finish();
    }
}
