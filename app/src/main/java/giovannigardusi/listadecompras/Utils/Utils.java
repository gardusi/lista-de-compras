package giovannigardusi.listadecompras.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;

/**
 * Created by Giovanni on 05/02/2017.
 */

public class Utils {

    public static void showKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    public static void hideKeyboard(Activity activity) {
        ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(activity.findViewById(R.id.activity_novo_item).getWindowToken(), 0);
    }

    public static void writeFile(Activity activity, String filename, ArrayList<ModelProduto> lista) {
        try {
            // Write a file to the disk
            FileOutputStream fOut = activity.openFileOutput(filename, Activity.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            // Write the string to the file
            osw.write("{");
            for (ModelProduto item : lista) {
                osw.write(item.writeToFile());
            }
            osw.write("}");

            // ensure that everything is really written out and close
            osw.flush();
            osw.close();

            Log.d("ESCRITA", activity.getFilesDir().getAbsolutePath());
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public static ArrayList<ModelProduto> readFile(Activity activity, String filename) {

        ArrayList<ModelProduto> lista = new ArrayList<>();
        try {
            FileInputStream fIn = activity.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fIn);

            StringBuilder fileReader = new StringBuilder();
            boolean endOfFile = false;
            if ((char) isr.read() == '{') {
                while (!endOfFile) {
                    char character = (char) isr.read();
                    switch (character) {
                        case '}':
                            endOfFile = true;
                            break;
                        case ';':
                            String str = fileReader.toString();
                            lista.add(ModelProduto.readFromFile(str));
                            fileReader = new StringBuilder();
                            break;
                        default:
                            fileReader.append(character);
                            break;
                    }
                }
            }

        } catch (IOException err) {
            err.printStackTrace();
        }
        return lista;
    }
}
