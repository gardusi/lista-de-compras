package giovannigardusi.listadecompras.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;

/**
 * Created by Giovanni on 04/02/2017.
 */

public class AdapterListaDeCompras extends ArrayAdapter {

    private Activity activity;

    private ArrayList<ModelProduto> listaDeProdutos;

    public AdapterListaDeCompras(Activity activity, ArrayList<ModelProduto> lista) {
        super(activity, R.layout.item_spinner_standard, lista);
        this.activity = activity;
        this.listaDeProdutos = lista;
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

        String strQtd = listaDeProdutos.get(position).getQuantidade();
        strQtd = strQtd + " " + listaDeProdutos.get(position).getUnidade();

        nome.setText(listaDeProdutos.get(position).getNome());
        quantidade.setText(strQtd);

        return row;
    }
}
