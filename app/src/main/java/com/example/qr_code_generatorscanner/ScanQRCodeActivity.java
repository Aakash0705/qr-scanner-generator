package com.example.qr_code_generatorscanner;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.pm.PackageManager;
import android.content.ActivityNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

public class ScanQRCodeActivity extends AppCompatActivity {

    private ScannerLiveView scannerLiveView;
    private TextView scannedTextView;
    private String scannedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        scannerLiveView = findViewById(R.id.camView);
        scannedTextView = findViewById(R.id.scannedData);

        if(checkPermission()){
            Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
        }else{
            requestPermission();
        }

        scannerLiveView.setScannerViewEventListener(new ScannerLiveView.ScannerViewEventListener() {
            @Override
            public void onScannerStarted(ScannerLiveView scanner) {
                Toast.makeText(ScanQRCodeActivity.this, "Scanner Started...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerStopped(ScannerLiveView scanner) {
                Toast.makeText(ScanQRCodeActivity.this, "Scanner Stopped...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onScannerError(Throwable err) {
                Toast.makeText(ScanQRCodeActivity.this, "Scanner Error Occurred Please Start Again...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeScanned(String data) {
                scannedData = data;
                scannedTextView.setText(data);
                createPdf(data);
            }
        });
    }

    private boolean checkPermission(){
        int cameraPermission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int vibratePermission = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);
        return cameraPermission == PackageManager.PERMISSION_GRANTED && vibratePermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(){
        int PERMISSION_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, VIBRATE}, PERMISSION_CODE);
    }

    @Override
    protected void onPause() {
        scannerLiveView.stopScanner();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ZXDecoder decoder = new ZXDecoder();
        decoder.setScanAreaPercent(0.8);
        scannerLiveView.setDecoder(decoder);
        scannerLiveView.startScanner();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0){
            boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if(cameraAccepted && vibrationAccepted){
                Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Permission Denied \n You can't use the app without permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createPdf(String data) {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/scannedData.pdf";
        File file = new File(pdfPath);

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            document.add(new Paragraph("Scanned Data:").setFont(font));
            document.add(new Paragraph(data).setFont(font));

            document.close();

            Toast.makeText(this, "PDF Created Successfully", Toast.LENGTH_SHORT).show();

            // Optionally, you can provide an option to view the PDF
            viewPdf(file);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating PDF", Toast.LENGTH_SHORT).show();
        }
    }

    private void viewPdf(File file) {
        // Use FileProvider to get a content URI
        try {
            android.net.Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
            android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No PDF viewer app installed", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error opening PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
