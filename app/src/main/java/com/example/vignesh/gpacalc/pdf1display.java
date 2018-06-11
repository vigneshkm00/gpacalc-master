package com.example.vignesh.gpacalc;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class pdf1display extends AppCompatActivity {

    TextView gr,dsp,lst1,lst2,lst3;
    public String regNo= " ";
    EditText rn;
    Button sve;
    private File pdfFile;
    String[] arr1;
    String[] selections1;
    String cgpa;

    public FontSelector font=new FontSelector();
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf1display);
        arr1 = getIntent().getStringArrayExtra("subj");
        cgpa = getIntent().getStringExtra("cgpa");

        // final String regno = {rn.getText().toString()};
                ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar4);
                pb.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(),"Please wait while we preparing PDF for you",Toast.LENGTH_SHORT).show();
                regNo=getIntent().getStringExtra("regno").toString();

                try {
                    createPdfWrapper();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }


    }
    private void createPdfWrapper() throws FileNotFoundException,DocumentException,IOException{

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }

                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        }else {
            createPdf();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    try {
                        createPdfWrapper();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                } else {
                    // Permission Denied
                    Toast.makeText(this, "WRITE_EXTERNAL Permission Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws IOException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");



        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            //Log.i(TAG, "Created a new directory for PDF");
        }
        String filename= regNo + ".pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(),filename);
        OutputStream output = new FileOutputStream(pdfFile);
        Rectangle pages=new Rectangle(PageSize.A4);
        pages.setBackgroundColor(BaseColor.WHITE);
        com.itextpdf.text.Document document = new com.itextpdf.text.Document(pages);

        PdfWriter writer=PdfWriter.getInstance(document, output);
        document.open();
        Rectangle rect=new Rectangle(580,810);
        rect.enableBorderSide(3);
        rect.setLeft(20f);
        rect.setBottom(20f);

        rect.setBorderWidth(2);
        rect.setBorder(Rectangle.BOX);
        rect.setBorderColor(BaseColor.BLACK);

        document.add(new Paragraph("\n\n\n\n\n\n"));


        Drawable d=getResources().getDrawable(R.drawable.study);

        BitmapDrawable bitDw=((BitmapDrawable)d);
        Bitmap bmp=bitDw.getBitmap();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG,100,stream);
        byte[] imgeByte=stream.toByteArray();
        Image image=Image.getInstance(imgeByte);
        //  float w=image.getScaledWidth();
        //float h=image.getScaledHeight();
//Font font=FontFactory.getFont(FONT, BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
        Font font= new Font(Font.FontFamily.HELVETICA,20);
        image.setAbsolutePosition(80f,250f);
        image.scaleAbsolute(410f,450f);

        PdfPTable table1=new PdfPTable(1);

        table1.setSpacingAfter(1f);
        table1.setWidthPercentage(75);
        PdfPCell c=new PdfPCell(new Paragraph(Font.BOLD, "\n    Chesmo GPA/CGPA Calculated Report \n \t\t      Register No:"+getIntent().getStringExtra("regno").toString()+"\n\n", font));

        PdfPTable table2=new PdfPTable(1);
        table2.setSpacingBefore(1f);
        table2.setSpacingAfter(1f);
        table2.setWidthPercentage(75);
        PdfPCell de=new PdfPCell(new Paragraph("\n                         CGPA:  "+cgpa+"\n  ",font));


        table1.addCell(c);
        table2.addCell(de);



        PdfPTable table=new PdfPTable(2);

        table.setWidthPercentage(75);

        table.setSpacingAfter(1f);
        table.setSpacingBefore(1f);
        float[] cwidth={2f,4f};
        table.setWidths(cwidth);
       // PdfPCell c1=new PdfPCell(new Paragraph("\n S.No\n "));
        PdfPCell c2=new PdfPCell(new Paragraph(Font.BOLD,"\n     Semester"));
        PdfPCell c3=new PdfPCell(new Paragraph(Font.BOLD,"\n  GPA"));
       // table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);


        int se=1;
        int sem = Integer.parseInt(getIntent().getStringExtra("sem"));
        for(int i=0;i<sem;i++) {

            String j=String.valueOf(se);
            String l=arr1[i];
            if(l!= "") {
         //   PdfPCell c4 = new PdfPCell(new Paragraph("\n "+j+"\n  "));
            PdfPCell c5=new PdfPCell(new Paragraph("\n   "+j+"\n  "));
            se++;
            PdfPCell c6=new PdfPCell(new Paragraph("\n  "+l+"\n  "));
         //   table.addCell(c4);
            table.addCell(c5);
            table.addCell(c6);
        }
        }

        document.add(image);
        document.add(table1);
        document.add(table);
        document.add(table2);
        document.add(rect);
        // document.add(img);
        document.close();
        previewPdf();
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar4);
        pb.setVisibility(View.INVISIBLE);
        setResult(101);
        finish();

    }

    private void previewPdf() {

        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
         //   Uri uri = Uri.fromFile(pdfFile);
            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".my.package.name.provider", pdfFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(photoURI, "application/pdf");

            startActivity(intent);
        }else{
            Toast.makeText(this,"Download a PDF Viewer to see the generated PDF",Toast.LENGTH_SHORT).show();
        }
    }

}
