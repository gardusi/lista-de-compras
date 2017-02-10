package giovannigardusi.listadecompras.Activities.ListaOptions;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import giovannigardusi.listadecompras.Activities.ActivityListaDeCompras;
import giovannigardusi.listadecompras.Models.ModelProduto;
import giovannigardusi.listadecompras.R;

public class ActivityImprimirLista extends AppCompatActivity {

    private static final Rectangle[] COLUMNS = {
            new Rectangle(36, 36, 290, 806),
            new Rectangle(305, 36, 559, 806)
    };

    private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imprimir_lista);

        ArrayList<ModelProduto> listaDeCompras = getIntent()
                .getParcelableArrayListExtra(ActivityListaDeCompras.PARAM_OBJ);

        createPdf("test.pdf", listaDeCompras);
    }

    private void createPdf(String filename, ArrayList<ModelProduto> lista) {

        Document document = new Document();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";

        try {
            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            File file = new File(dir, filename);
            FileOutputStream fos = new FileOutputStream(file);

            PdfWriter writer = PdfWriter.getInstance(document, fos);

            //open the document
            document.open();

            PdfContentByte canvas = writer.getDirectContent();
            ColumnText ct = new ColumnText(canvas);

            List leftSide = new List(List.UNORDERED);
            List rightSide = new List(List.UNORDERED);
            rightSide.setListSymbol("");

            for (ModelProduto item : lista) {
                ListItem leftText = new ListItem(item.getNome());
                ListItem rightText = new ListItem(item.getQuantidade() + " " + item.getUnidade());

                leftText.setFont(new Font(Font.FontFamily.COURIER, 20));
                rightText.setFont(new Font(Font.FontFamily.COURIER, 20));

                leftSide.add(leftText);
                rightSide.add(rightText);
            }

            // Imprime as colunas do PDF
            ct.setSimpleColumn(COLUMNS[0]);
            ct.addElement(leftSide);
            ct.go();
            ct.setSimpleColumn(COLUMNS[1]);
            ct.addElement(rightSide);
            ct.go();

        } catch (DocumentException | IOException e) {
            Log.e("PDFCreator", "Exception:" + e);
        } finally {
            document.close();
        }

        viewPdf(filename);
    }

    private void viewPdf(String filename) {

        File pdfFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir/" + filename);
        Uri uri = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(uri, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(ActivityImprimirLista.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }
}
