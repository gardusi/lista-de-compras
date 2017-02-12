package giovannigardusi.listadecompras.Adapters;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Utils;

/**
 * Created by Giovanni on 11/02/2017.
 */

public class AdapterArquivo extends ArrayAdapter {

    private Activity activity;
    private ArrayList<File> arquivos;

    public AdapterArquivo(Activity activity, ArrayList<File> lista) {
        super(activity, R.layout.item_arquivo, lista);
        this.activity = activity;
        this.arquivos = lista;
    }

    @Override
    public
    @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private
    @NonNull
    View getCustomView(final int position, View convertView, @NonNull ViewGroup parent) {
        // Captura View da linha
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.item_arquivo, parent, false);

        TextView nome = (TextView) row.findViewById(R.id.item_arquivo_nome);
        TextView data = (TextView) row.findViewById(R.id.item_arquivo_data);
        ImageView excluir = (ImageView) row.findViewById(R.id.item_arquivo_excluir);

        nome.setText(arquivos.get(position).getName().replaceFirst(".txt", ""));
        Date lastModified = new Date(arquivos.get(position).lastModified());

        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Utils.getCurrentLocale(activity));
        data.setText(dateFormat.format(lastModified));

        excluir.setTag(position);
        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String name = arquivos.get(position).getName();
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                int position = (Integer) v.getTag();
                                activity.deleteFile(name);
                                arquivos.remove(position);
                                notifyDataSetChanged();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage(activity.getString(R.string.activity_abrir_lista_excluir)
                        + " \"" + name.replaceFirst(".txt", "") + "\"?")
                        .setPositiveButton("Sim, Excluir", dialogClickListener)
                        .setNegativeButton("Cancelar", dialogClickListener).show();
            }
        });
        return row;
    }

    public File getFile(int position) {
        return arquivos.get(position);
    }
}
