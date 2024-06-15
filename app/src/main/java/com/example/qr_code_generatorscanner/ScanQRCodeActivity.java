package com.example.qr_code_generatorscanner;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;


public class ScanQRCodeActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 100;
    private ScannerLiveView scannerLiveView;
    private TextView scannedTextView;
    private String scannedData;
    private String intensity;
    private Button selectFromGalleryBtn;
    Bitmap stdBmp;

    private String doctorName;
    private String hospital;
    private String doctorAddress;
    private String doctorPhone;
    private String patientName;
    private String mrn;
    private String testEye;
    private String phoneNo;
    private String threshold;
    private String fixationLoss;
    private String falsePOSError;
    private String falseNEGError;
    private String testDuration;
    private String fovea;
    private String fixationTarget;
    private String fixationMonitor;
    private String stimulusSize;
    private String visualAcuity;
    private String power;
    private String date;
    private String time;
    private String age;
    private String gender;
    private String totalDeviation;
    private String patternDeviation;
    private String visualFieldIndex;
    private String meanDeviation;
    private String patternStandardDeviation;
    private String ght;
    private String eyeTracking;
    private String lookedAway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        scannerLiveView = findViewById(R.id.camView);
        scannedTextView = findViewById(R.id.scannedData);
        selectFromGalleryBtn = findViewById(R.id.selectFromGalleryBtn);

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

                // Parse the scanned data
                Map<String, String> parsedData = parseScannedData(data);

                // Use the parsed data as needed
                // For example, print the INTENSITY
                String intensity = parsedData.get("INTENSITY");
                String[] intensityParts = intensity.split(",");
                int[] INTENSITY_RESULT = new int[intensityParts.length];

                for (int i = 0; i < intensityParts.length; i++) {
                    try {
                        INTENSITY_RESULT[i] = Integer.parseInt(intensityParts[i].trim());
                    } catch (NumberFormatException e) {
                        // Handle if the string part cannot be parsed as an integer
                        e.printStackTrace();
                        // You might want to handle this case based on your application's logic
                    }
                }

                createPdf(intensity);
            }

            private Map<String, String> parseScannedData(String data) {
                Map<String, String> dataMap = new HashMap<>();
                String[] lines = data.split("\n");

                for (String line : lines) {
                    String[] keyValue = line.split(":");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim(); // Trim key to remove any leading or trailing whitespace
                        String value = keyValue[1].trim(); // Trim value to remove any leading or trailing whitespace
                        dataMap.put(key, value);
                    }
                }

                return dataMap;
            }

        });

        selectFromGalleryBtn.setOnClickListener(v -> selectImageFromGallery());
    }

    private void selectImageFromGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
                //decodeQRCode(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    private void decodeQRCode(Bitmap bitmap) {
//        int[] intArray = new int[bitmap.getWidth() * bitmap.getHeight()];
//        bitmap.getPixels(intArray, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
//
//        RGBLuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(), intArray);
//        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
//        MultiFormatReader reader = new MultiFormatReader();
//
//        try {
//            Result result = reader.decode(binaryBitmap);
//            String scannedData = result.getText();
//            Log.d("genData","data"+ scannedData);
//            Toast.makeText(this, "QR Code scanned successfully: " + scannedData, Toast.LENGTH_SHORT).show();
//
//            // List of keys to fetch
//            List<String> keys = Arrays.asList("Doctor name","hospital","doctor address","doctor phone",
//                    "Patient Name","MRN","Eye","Phone no","threshold","Fixation Loss","False POS Error",
//                    "False NEG Error","Test Duration","Fovea","Fixation Target","Fixation Monitor",
//                    "Stimulus Size","Visual Acuity","Power","Date","Time","Age","Gender","INTENSITY",
//                    "Total deviation","Pattern Deviation","Visual Field Index (VFI)","Mean Deviation (MD)",
//                    "Pattern Standard Deviation (PSD)","GHT","Eye Tracking","Looked Away");
//
//            // Regular expression to match key-value pairs separated by ;
//            Pattern pattern = Pattern.compile("([^;]+):([^;]+);?");
//            Matcher matcher = pattern.matcher(scannedData);
//
//            // Variables to store values
//            String doctorName = null;
//            String hospital = null;
//            String doctorAddress = null;
//            String doctorPhone = null;
//            String patientName = null;
//            String mrn = null;
//            String eye = null;
//            String phoneNo = null;
//            String threshold = null;
//            String fixationLoss = null;
//            String falsePOSError = null;
//            String falseNEGError = null;
//            String testDuration = null;
//            String fovea = null;
//            String fixationTarget = null;
//            String fixationMonitor = null;
//            String stimulusSize = null;
//            String visualAcuity = null;
//            String power = null;
//            String date = null;
//            String time = null;
//            String age = null;
//            String gender = null;
//            intensity = null;
//            String totalDeviation = null;
//            String patternDeviation = null;
//            String visualFieldIndex = null;
//            String meanDeviation = null;
//            String patternStandardDeviation = null;
//            String ght = null;
//            String eyeTracking = null;
//            String lookedAway = null;
//
//            // Iterate through matched pairs
//            while (matcher.find()) {
//                String key = matcher.group(1).trim();
//                String value = matcher.group(2).trim();
//                // Assign values to corresponding variables
//                switch (key) {
//                    case "Doctor name":
//                        doctorName = value;
//                        break;
//                    case "hospital":
//                        hospital = value;
//                        break;
//                    case "doctor address":
//                        doctorAddress = value;
//                        break;
//                    case "doctor phone":
//                        doctorPhone = value;
//                        break;
//                    case "Patient Name":
//                        patientName = value;
//                        break;
//                    case "MRN":
//                        mrn = value;
//                        break;
//                    case "Eye":
//                        eye = value;
//                        break;
//                    case "Phone no":
//                        phoneNo = value;
//                        break;
//                    case "threshold":
//                        threshold = value;
//                        break;
//                    case "Fixation Loss":
//                        fixationLoss = value;
//                        break;
//                    case "False POS Error":
//                        falsePOSError = value;
//                        break;
//                    case "False NEG Error":
//                        falseNEGError = value;
//                        break;
//                    case "Test Duration":
//                        testDuration = value;
//                        break;
//                    case "Fovea":
//                        fovea = value;
//                        break;
//                    case "Fixation Target":
//                        fixationTarget = value;
//                        break;
//                    case "Fixation Monitor":
//                        fixationMonitor = value;
//                        break;
//                    case "Stimulus Size":
//                        stimulusSize = value;
//                        break;
//                    case "Visual Acuity":
//                        visualAcuity = value;
//                        break;
//                    case "Power":
//                        power = value;
//                        break;
//                    case "Date":
//                        date = value;
//                        break;
//                    case "Time":
//                        time = value;
//                        break;
//                    case "Age":
//                        age = value;
//                        break;
//                    case "Gender":
//                        gender = value;
//                        break;
//                    case "INTENSITY":
//                        intensity = value;
//                        break;
//                    case "Total deviation":
//                        totalDeviation = value;
//                        break;
//                    case "Pattern Deviation":
//                        patternDeviation = value;
//                        break;
//                    case "Visual Field Index (VFI)":
//                        visualFieldIndex = value;
//                        break;
//                    case "Mean Deviation (MD)":
//                        meanDeviation = value;
//                        break;
//                    case "Pattern Standard Deviation (PSD)":
//                        patternStandardDeviation = value;
//                        break;
//                    case "GHT":
//                        ght = value;
//                        break;
//                    case "Eye Tracking":
//                        eyeTracking = value;
//                        break;
//                    case "Looked Away":
//                        lookedAway = value;
//                        break;
//                    default:
//                        // Handle unrecognized keys or do nothing
//                        break;
//                }
//            }
//
//            //createPdf(intensity);
//
//        }catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Error decoding QR Code", Toast.LENGTH_SHORT).show();
//        }
//    }
    private boolean checkPermission() {
        int cameraPermission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int vibratePermission = ContextCompat.checkSelfPermission(getApplicationContext(), VIBRATE);
        return cameraPermission == PackageManager.PERMISSION_GRANTED && vibratePermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
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
        if (grantResults.length > 0) {
            boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean vibrationAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (cameraAccepted && vibrationAccepted) {
                Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Denied \n You can't use the app without permissions", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void createPdf(String data) {

        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String format = s.format(new Date());
        if (data == null) {
            data = "N/A"; // or some default value
       }

        //String timeStamp = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + format + "" +"scannedData.pdf";
        File file = new File(pdfPath);

        try {
            PdfWriter writer = new PdfWriter(new FileOutputStream(file));
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);

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
            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No PDF viewer app installed", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error opening PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
