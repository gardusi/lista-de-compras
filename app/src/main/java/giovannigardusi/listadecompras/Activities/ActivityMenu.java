package giovannigardusi.listadecompras.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import giovannigardusi.listadecompras.Activities.ListaOptions.ActivityAbrirLista;
import giovannigardusi.listadecompras.R;

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
                intent.putParcelableArrayListExtra(ActivityListaDeCompras.PARAM_OBJ, null);
                startActivity(intent);
            }
        });

        abrirLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ActivityAbrirLista.class);
                startActivity(intent);
            }
        });
    }
}
