package giovannigardusi.listadecompras.Activities.ListaOptions;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import giovannigardusi.listadecompras.Activities.ActivityListaDeCompras;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Utils;

public class ActivitySalvarLista extends AppCompatActivity {

    private final String ENCRYPT_KEY = "";

    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salvar_lista);

        ArrayList<ModelProduto> listaDeCompras = getIntent().getParcelableArrayListExtra(ActivityListaDeCompras.PARAM_OBJ);

        Utils.writeFile(activity, "lista_teste.txt", listaDeCompras);
        activity.finish();
    }
}
