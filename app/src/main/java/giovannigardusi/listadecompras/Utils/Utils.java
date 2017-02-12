package giovannigardusi.listadecompras.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

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

    public static boolean isAlphanumerical(String str) {
        char[] charSeq = str.toCharArray();
        for(char c : charSeq)
            if (!Character.isLetterOrDigit(c))
                // Spaces are allowed
                if (c != ' ') return false;
        return true;
    }

    public static Locale getCurrentLocale(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return activity.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return activity.getResources().getConfiguration().locale;
        }
    }
}
