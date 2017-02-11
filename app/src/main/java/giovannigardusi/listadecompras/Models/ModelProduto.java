package giovannigardusi.listadecompras.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Giovanni on 04/02/2017.
 */

public class ModelProduto implements Parcelable {

    private String nome;
    private String quantidade;
    private String unidade;
    private boolean marcado;

    public ModelProduto() { }

    public ModelProduto(String nome, String quantidade, String unidade) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade = unidade;
    }

    // Recebe usando Parcel
    private ModelProduto(Parcel p) {
        this.nome = p.readString();
        this.quantidade = p.readString();
        this.unidade = p.readString();
        this.marcado = p.readInt() > 0;
    }

    public String getNome() {
        return nome;
    }
    public String getQuantidade() {
        return quantidade;
    }
    public String getUnidade() {
        return unidade;
    }
    public boolean getMarcado() {
        return marcado;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }
    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }
    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    public String writeToFile(boolean keepChecked) {
        if (keepChecked) {
            return getNome() + ":" + getQuantidade() + ":" + getUnidade() + ":" + getMarcado() + ";";
        } else {
            return getNome() + ":" + getQuantidade() + ":" + getUnidade() + ":false;";
        }

    }

    public static ModelProduto readFromFile(String input) {
        String[] str = input.split(":");

        ModelProduto item = new ModelProduto();
        item.setNome(str[0]);
        item.setQuantidade(str[1]);
        item.setUnidade(str[2]);
        item.setMarcado(Boolean.parseBoolean(str[3]));

        return item;
    }

    public static final Parcelable.Creator<ModelProduto>
        CREATOR = new Parcelable.Creator<ModelProduto>() {

        public ModelProduto createFromParcel(Parcel in) {
            return new ModelProduto(in);
        }

        public ModelProduto[] newArray(int size) {
            return new ModelProduto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    // Envia usando Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(quantidade);
        dest.writeString(unidade);
        dest.writeInt(marcado ? 1 : 0);
    }
}
