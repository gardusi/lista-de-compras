package giovannigardusi.listadecompras.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import giovannigardusi.listadecompras.Adapters.AdapterQuantidade;
import giovannigardusi.listadecompras.Adapters.AdapterUnidade;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Constants;
import giovannigardusi.listadecompras.Utils.Utils;

public class ActivityNovoItem extends AppCompatActivity {

    public final static String PARAM_ITEM = "novoItem";
    public final static String PARAM_POS = "position";

    Activity activity = this;

    private boolean isEdit = false;

    private EditText itemEditText;
    private Spinner quantidadeSpinner;
    private Spinner unidadeSpinner;
    private TextView adicionarButton;

    private AdapterUnidade adapterUnidade;
    private AdapterQuantidade adapterQuantidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item);

        // Recebe parametros da Activity anterior
        ModelProduto item = getIntent().getParcelableExtra(PARAM_ITEM);

        // Ativa botao de Voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Cria tela com teclado aberto
        Utils.showKeyboard(activity);

        // Pega os IDs
        itemEditText = (EditText) findViewById(R.id.activity_novo_item_item);
        quantidadeSpinner = (Spinner) findViewById(R.id.activity_novo_item_quantidade);
        unidadeSpinner = (Spinner) findViewById(R.id.activity_novo_item_unidade);
        adicionarButton = (TextView) findViewById(R.id.activity_novo_item_adicionar);

        // Cria os Adapters
        adapterUnidade = new AdapterUnidade(activity);
        adapterQuantidade = new AdapterQuantidade(activity);

        // Atribui os Adapters
        unidadeSpinner.setAdapter(adapterUnidade);
        quantidadeSpinner.setAdapter(adapterQuantidade);

        // Preenche dados caso seja uma edicao
        if (item != null) {
            isEdit = true;
            adicionarButton.setTag(item.getMarcado());
            writeFields(item);
        }

        // Botao de Adicionar
        adicionarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModelProduto item = new ModelProduto();
                item.setNome(itemEditText.getText().toString());
                item.setUnidade(unidadeSpinner.getSelectedItem().toString());
                item.setQuantidade(quantidadeSpinner.getSelectedItem().toString());

                Intent intent = new Intent();
                intent.putExtra(PARAM_ITEM, item);
                if (isEdit) {
                    item.setMarcado((boolean) adicionarButton.getTag());
                    intent.putExtra(PARAM_POS, getIntent().getIntExtra(PARAM_POS, -1));
                    activity.setResult(ActivityListaDeCompras.RESULT_EDIT_ITEM, intent);
                } else {
                    activity.setResult(ActivityListaDeCompras.RESULT_NEW_ITEM, intent);
                }
                Utils.hideKeyboard(activity);
                activity.finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Utils.hideKeyboard(activity);
                activity.setResult(ActivityListaDeCompras.RESULT_BACK);
                activity.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void writeFields(ModelProduto item) {
        itemEditText.setText(item.getNome());

        int position;
        String quantidade = item.getQuantidade();
        switch (quantidade) {
            case Constants.ONE_FOURTH:
                position = 0;
                break;
            case Constants.ONE_HALF:
                position = 1;
                break;
            default:
                position = Integer.parseInt(quantidade) + 1;
                break;
        }
        quantidadeSpinner.setSelection(position);

        String unidade = item.getUnidade();
        ArrayList<String> unidades = Constants.getInstance().getUnidades();
        for (int i = 0; i < unidades.size(); i++) {
            if (unidades.get(i).equals(unidade)) {
                unidadeSpinner.setSelection(i);
                break;
            }
        }
    }
}
