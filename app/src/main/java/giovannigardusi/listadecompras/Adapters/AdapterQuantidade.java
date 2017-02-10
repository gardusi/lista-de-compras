package giovannigardusi.listadecompras.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import giovannigardusi.listadecompras.R;
import giovannigardusi.listadecompras.Utils.Constants;

/**
 * Created by Giovanni on 04/02/2017.
 */

public class AdapterQuantidade extends ArrayAdapter {

    private Activity activity;

    private static ArrayList<String> quantidades = Constants.getInstance().getQuantidades();

    public AdapterQuantidade(Activity activity) {
        super(activity, R.layout.item_spinner_standard, quantidades);
        this.activity = activity;
    }

    @Override
    public @NonNull View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public @NonNull View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private @NonNull View getCustomView(int position, View convertView, @NonNull ViewGroup parent) {
        // Captura View da linha
        LayoutInflater inflater = activity.getLayoutInflater();
        View row = inflater.inflate(R.layout.item_spinner_standard, parent, false);

        TextView text = (TextView) row.findViewById(R.id.item_spinner_standard_text);
        text.setText(quantidades.get(position));

        return row;
    }
}
