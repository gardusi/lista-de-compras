package giovannigardusi.listadecompras.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

import giovannigardusi.listadecompras.Activities.ActivityListaDeCompras;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Settings;

/**
 * Created by Giovanni on 04/02/2017.
 */

public class AdapterListaDeCompras extends ArrayAdapter {

    private Activity activity;

    private ArrayList<ModelProduto> listaDeProdutos;
    private ActivityListaDeCompras.OnCheckListener onCheckListener;

    public AdapterListaDeCompras(Activity activity, ArrayList<ModelProduto> lista, ActivityListaDeCompras.OnCheckListener onCheckListener) {
        super(activity, R.layout.item_spinner_standard, lista);
        this.activity = activity;
        this.listaDeProdutos = lista;
        this.onCheckListener = onCheckListener;
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private @NonNull View getCustomView(int position, View convertView, @NonNull ViewGroup parent) {
        // Captura View da linha
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.item_produto, parent, false);

        TextView nome = (TextView) row.findViewById(R.id.item_produto_nome);
        TextView quantidade = (TextView) row.findViewById(R.id.item_produto_quantidade);
        CheckBox checkBox = (CheckBox) row.findViewById(R.id.item_produto_checkbox);

        if (Settings.getInstance().isShowCheck()) {
            // Armazena a posicao do CheckBox a ser marcado
            checkBox.setTag(position);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int position = (Integer) buttonView.getTag();
                    listaDeProdutos.get(position).setMarcado(buttonView.isChecked());
                }
            });
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { onCheckListener.onCheck(); }
            });
            checkBox.setChecked(listaDeProdutos.get(position).getMarcado());
        } else {
            checkBox.setVisibility(View.GONE);
        }

        String strQtd = listaDeProdutos.get(position).getQuantidade();
        strQtd = strQtd + " " + listaDeProdutos.get(position).getUnidade();

        nome.setText(listaDeProdutos.get(position).getNome());
        quantidade.setText(strQtd);

        return row;
    }
}
