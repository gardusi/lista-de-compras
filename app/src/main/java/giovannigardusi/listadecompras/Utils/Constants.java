package giovannigardusi.listadecompras.Utils;

import java.util.ArrayList;

/**
 * Created by Giovanni on 04/02/2017.
 */

public class Constants {

    private static final Constants constants = new Constants();

    private ArrayList<String> unidades;
    private ArrayList<String> quantidades;

    public static final String ONE_HALF = "½";
    public static final String ONE_FOURTH = "¼";

    public static final String SAVE_MSG = "Você quer mesmo sair?\nSe sair agora, sua lista não será salva.";

    private Constants() {
        populateUnidades();
        populateQuantidades();
    }

    private void populateUnidades() {
        unidades = new ArrayList<String>() {{
            add(0, "unidade(s)");
            add(1, "cartela(s)");
            add(2, "caixa(s)");
            add(3, "pacote(s)");
            add(4, "pote(s)");
            add(5, "quilo(s)");
            //add(6, "grama(s)");
        }};
    }

    private void populateQuantidades() {
        quantidades = new ArrayList<String>() {{
            add(0, Constants.ONE_FOURTH);
            add(1, Constants.ONE_HALF);
            for(int i=1; i<=6; i++){
                add(i+1, ""+i);
            }
        }};
    }

    public static Constants getInstance() {
        return constants;
    }

    public ArrayList<String> getUnidades() {
        return this.unidades;
    }

    public ArrayList<String> getQuantidades() {
        return this.quantidades;
    }
}
