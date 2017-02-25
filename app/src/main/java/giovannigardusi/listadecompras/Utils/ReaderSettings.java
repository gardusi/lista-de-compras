package giovannigardusi.listadecompras.Utils;

import android.app.Activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by Giovanni on 12/02/2017.
 */

public class ReaderSettings {

    private static boolean[] props = new boolean[2];

    public static void write(Activity activity) {
        try {
            // Write a file to the disk
            FileOutputStream fOut = activity.openFileOutput(Constants.SETTINGS_NAME, Activity.MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fOut);

            osw.write("{");
            // Save settings
            osw.write(String.valueOf(Settings.getInstance().isSimpleSave()) + ";");
            osw.write(String.valueOf(Settings.getInstance().isShowCheck()) + ";");
            osw.write("}");

            // ensure that everything is really written out and close
            osw.flush();
            osw.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public static void read(Activity activity) throws IOException {
        FileInputStream fIn = activity.openFileInput(Constants.SETTINGS_NAME);
        InputStreamReader isr = new InputStreamReader(fIn);

        StringBuilder fileReader = new StringBuilder();
        boolean endOfFile = false;
        int i = 0;
        if ((char) isr.read() == '{') {
            while (!endOfFile) {
                char character = (char) isr.read();
                switch (character) {
                    case '}':
                        endOfFile = true;
                        break;
                    case ';':
                        String str = fileReader.toString();
                        props[i++] = Boolean.parseBoolean(str);
                        fileReader = new StringBuilder();
                        break;
                    default:
                        fileReader.append(character);
                        break;
                }
            }
        }
        // Load settings
        Settings.getInstance().setSimpleSave(props[0]);
        Settings.getInstance().setShowCheck(props[1]);
    }
}
