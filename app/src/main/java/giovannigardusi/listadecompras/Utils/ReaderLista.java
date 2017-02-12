package giovannigardusi.listadecompras.Utils;

import android.app.Activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import giovannigardusi.listadecompras.Models.ModelProduto;

/**
 * Created by Giovanni on 11/02/2017.
 */

public class ReaderLista {

    public static void write(Activity activity, String name, ArrayList<ModelProduto> lista, boolean keepChecked) {
        try {
            // Write a file to the disk
            FileOutputStream fOut = activity.openFileOutput(name + ".txt", Activity.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            // Write the string to the file
            osw.write("{");
            for (ModelProduto item : lista) {
                osw.write(item.writeToFile(keepChecked));
            }
            osw.write("}");

            // ensure that everything is really written out and close
            osw.flush();
            osw.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public static ArrayList<ModelProduto> read(Activity activity, String name) {
        String filename;
        if (name.endsWith(".txt")) filename = name;
        else filename = name + ".txt";

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
