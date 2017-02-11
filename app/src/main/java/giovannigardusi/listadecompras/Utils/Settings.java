package giovannigardusi.listadecompras.Utils;

/**
 * Created by Giovanni on 11/02/2017.
 */

public class Settings {

    private static final Settings settings = new Settings();

    private boolean simpleSave;

    // Class constructor is forbidden
    private Settings() {}

    public static Settings getInstance() {
        return settings;
    }

    public void setSimpleSave(boolean simpleSave) {
        this.simpleSave = simpleSave;
    }

    public boolean isSimpleSave() {
        return simpleSave;
    }
}
