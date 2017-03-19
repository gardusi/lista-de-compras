package giovannigardusi.listadecompras.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import giovannigardusi.listadecompras.Adapters.AdapterArquivo;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Constants;
import giovannigardusi.listadecompras.Utils.ReaderLista;
import giovannigardusi.listadecompras.Utils.Settings;

import static android.view.View.GONE;

public class ActivityMenu extends AppCompatActivity {
    
    private final static int ANIMATION_TIMER = 400;

    Activity activity = this;

    private FrameLayout arquivosContainer;
    private RelativeLayout menuContainer;
    private TextView novaLista;
    private TextView abrirLista;

    TranslateAnimation showMenu;
    TranslateAnimation hideMenu;

    private interface AnimationStateListener { void start(); void end(); }
    private AnimationStateListener animationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        novaLista = (TextView) findViewById(R.id.activity_menu_nova_lista);
        abrirLista = (TextView) findViewById(R.id.activity_menu_abrir_lista);
        arquivosContainer = (FrameLayout) findViewById(R.id.activity_menu_frame);
        menuContainer = (RelativeLayout) findViewById(R.id.activity_menu_bar_container);

        menuContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                showMenu = new TranslateAnimation(-menuContainer.getWidth(), 0, 0, 0);
                showMenu.setDuration(ANIMATION_TIMER);
                showMenu.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        menuContainer.setVisibility(View.VISIBLE);
                        arquivosContainer.animate().alpha(0.0f).setDuration(ANIMATION_TIMER).start();
                    }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        arquivosContainer.setVisibility(View.GONE);
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
                hideMenu = new TranslateAnimation(0, -menuContainer.getWidth(), 0, 0);
                hideMenu.setDuration(ANIMATION_TIMER);
                hideMenu.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) { animationListener.start(); }
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        menuContainer.setVisibility(GONE);
                        animationListener.end();
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) { }
                });
            }
        });

        novaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuContainer.startAnimation(hideMenu);
                animationListener = new AnimationStateListener() {
                    @Override
                    public void start() { }
                    @Override
                    public void end() {
                        Intent intent = new Intent(activity, ActivityListaDeCompras.class);
                        intent.putParcelableArrayListExtra(ActivityListaDeCompras.PARAM_LISTA, null);
                        startActivity(intent);
                    }
                };
            }
        });
        abrirLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuContainer.startAnimation(hideMenu);
                animationListener = new AnimationStateListener() {
                    @Override
                    public void start() {
                        if (!Settings.getInstance().isSimpleSave()) fileChooser();
                    }
                    @Override
                    public void end() {
                        if (Settings.getInstance().isSimpleSave()) {
                            ArrayList<ModelProduto> listaDeCompras = ReaderLista.read(activity, Constants.SIMPLE_SAVE_NAME);
                            Intent intent = new Intent(activity, ActivityListaDeCompras.class);
                            intent.putParcelableArrayListExtra(ActivityListaDeCompras.PARAM_LISTA, listaDeCompras);
                            startActivity(intent);
                        }
//                        else {
//                            fileChooser();
//                            Intent intent = new Intent(activity, ActivityAbrirLista.class);
//                            startActivity(intent);
//                        }
                    }
                };
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (arquivosContainer.getVisibility() == View.GONE && menuContainer.getVisibility() == View.GONE)
            menuContainer.startAnimation(showMenu);
    }

    @Override
    public void onBackPressed() {
        if (menuContainer.getVisibility() == View.GONE)
            menuContainer.startAnimation(showMenu);
        else super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                menuContainer.startAnimation(showMenu);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fileChooser() {
        ListView arquivosView = (ListView) findViewById(R.id.activity_abrir_lista_arquivos);

        // Remocao de arquivos indesejados
        ArrayList<File> excluded = new ArrayList<>();
        ArrayList<File> files = new ArrayList<>(Arrays.asList(activity.getFilesDir().listFiles()));
        for (File file : files) {
            if (file.getName().startsWith("_")) excluded.add(file);
            else if (file.getName().equals("instant-run")) excluded.add(file);
        }
        for (File file : excluded) files.remove(file);

        final AdapterArquivo arquivosAdapter = new AdapterArquivo(activity, files);
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
        arquivosContainer.setAlpha(0.0f);
        arquivosContainer.setVisibility(View.VISIBLE);
        arquivosContainer.animate().alpha(1.0f).setDuration(ANIMATION_TIMER).start();
    }
}
