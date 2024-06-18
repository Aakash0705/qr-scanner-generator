package com.example.qr_code_generatorscanner;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import eu.livotov.labs.android.camview.ScannerLiveView;
import eu.livotov.labs.android.camview.scanner.decoder.zxing.ZXDecoder;

import com.itextpdf.text.Image;

public class ScanQRCodeActivity extends AppCompatActivity {
    private final static int REQUEST_MANAGE_EXTERNAL_STORAGE = 1;
    private static final int SELECT_PHOTO = 100;
    private ScannerLiveView scannerLiveView;
    private TextView scannedTextView;
    private String scannedData;
    private String intensity;
    private static Paint paint;

    private Button selectFromGalleryBtn;
    private Bitmap bitmap_mapped;
    Bitmap stdBmp, totalDeviationBmp1, totalDeviationBmp2, patternDeviationBmp1, patternDeviationBmp2;

    private String doctorName;
    private String hospital;
    private String doctorAddress;
    private String doctorPhone;
    private String patientName;
    private String mrn;
    private String testEye="R";
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
    //int[] INTENSITY_RESULT;
    public static int[] INTENSITY_RESULT = new int[65];
    public static String[] intensityParts = new String[65];


    _24_2_Coordinates coordinate24_2;
    // Coordinates class
    double[][] small_rect_r = {           //Corner rectangles : right eye
            {823, 225, 4}, // 0
            {933, 335, 4}, // 1
            {930, 820, 4}, // 2
            {820, 930, 4}, // 3
            {335, 930, 4}, // 4
            {225, 820, 4}, // 5
            {113, 710, 4}, // 6
            {113, 445, 4}, // 7
            {225, 335, 4}, // 8
            {335, 225, 4}, // 9
    };

    double[][] small_rect_l = {        //Corner rectangles : left eye
            {823, 225, 4}, // 0
            {933, 335, 4}, // 1
            {1040, 445, 4},// 2
            {1040, 710, 4},// 3
            {930, 820, 4}, // 4
            {820, 930, 4}, // 5
            {335, 930, 4}, // 6
            {225, 820, 4}, // 7
            {225, 335, 4}, // 8
            {335, 225, 4}, // 9
    };

    int dot_width = 4;
    int[] top_right = new int[64];
    int[] second_top_right = new int[64];
    int[] middle_right = new int[64];
    int[] second_bottom_right = new int[64];
    int[] bottom_right = new int[64];

    int[] third_bottom_right = new int[64];
    int[] middle_bottom = new int[64];
    int[] third_bottom_left = new int[64];
    int[] bottom_left = new int[64];

    int[] second_bottom_left = new int[64];
    int[] middle_left = new int[64];
    int[] second_top_left = new int[64];
    int[] top_left = new int[64];

    int[] third_top_left = new int[64];
    int[] middle_top = new int[64];
    int[] third_top_right = new int[64];

    public int A,B,C,D,E;
    public double[] small_rec_vals ={0,31,27,25,22};

    int u_zone_1, u_zone_2,u_zone_3,u_zone_4,u_zone_5;
    int l_zone_1, l_zone_2,l_zone_3,l_zone_4,l_zone_5;
    int[] NORMAL_INTENSITY = new int[55];
    int[] SORTED_ARRAY = new int[55];
    int[] PATTERN_DEVIATION = new int[55];
    int[] TOTAL_DEVIATION = new int[55];



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrcode);

        scannerLiveView = findViewById(R.id.camView);
        scannedTextView = findViewById(R.id.scannedData);
        selectFromGalleryBtn = findViewById(R.id.selectFromGalleryBtn);
        paint = new Paint();
        coordinate24_2 = new _24_2_Coordinates(this);




        if (checkPermission()) {
            Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
        } else {
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
                doctorName = parsedData.get("Doctor name");
                hospital = parsedData.get("hospital");
                doctorAddress = parsedData.get("doctor address");
                doctorPhone = parsedData.get("doctor phone");
                patientName = parsedData.get("Patient Name");
                mrn = parsedData.get("MRN");
                testEye = parsedData.get("Eye");
                Log.d("hello5","eye"+ testEye);
                phoneNo = parsedData.get("Phone no");
                threshold = parsedData.get("threshold");
                fixationLoss = parsedData.get("Fixation Loss");
                falsePOSError = parsedData.get("False POS Error");
                falseNEGError = parsedData.get("False NEG Error");
                testDuration = parsedData.get("Test Duration");
                Log.d("testDuration","duration"+ testDuration);
                fovea = parsedData.get("Fovea");
                fixationTarget = parsedData.get("Fixation Target");
                fixationMonitor = parsedData.get("Fixation Monitor");
                stimulusSize = parsedData.get("Stimulus Size");
                visualAcuity = parsedData.get("Visual Acuity");
                power = parsedData.get("Power");
                date = parsedData.get("Date");
                Log.d("testDuration3","date"+ date);
                time = parsedData.get("Time");
                Log.d("testDuration2","time"+ time);
                age = parsedData.get("Age");
                gender = parsedData.get("Gender");
                intensity = parsedData.get("INTENSITY");
                Log.d("hello","intensity" + intensity);
                totalDeviation = parsedData.get("Total deviation");
                patternDeviation = parsedData.get("Pattern Deviation");
                visualFieldIndex = parsedData.get("Visual Field Index (VFI)");
                meanDeviation = parsedData.get("Mean Deviation (MD)");
                patternStandardDeviation = parsedData.get("Pattern Standard Deviation (PSD)");
                ght = parsedData.get("GHT");
                eyeTracking = parsedData.get("Eye Tracking");
                lookedAway = parsedData.get("Looked Away");

               // calculate();
                parseAndAssignIntensity(intensity);
                // Background Task to create the report
                new MyTask().execute();
//                try {
//                    createPdfWrapper();
//                } catch (FileNotFoundException e) {
//                    throw new RuntimeException(e);
//                } catch (DocumentException e) {
//                    throw new RuntimeException(e);
//                }
            }


            private Map<String, String> parseScannedData(String data) {
                Map<String, String> dataMap = new HashMap<>();
                String[] lines = data.split("\n");

                for (String line : lines) {
                    String[] keyValue = line.split(":",2);
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

    private void setIntensityPattern() {

        Paint stdpaint = new Paint();
        stdpaint.setColor(Color.BLACK);
        stdpaint.setStyle(Paint.Style.FILL);
        stdpaint.setTextSize(24);
        stdpaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

        stdBmp = Bitmap.createBitmap(1200, 1200, Bitmap.Config.ARGB_8888);
        Canvas stdCanvas = new Canvas(stdBmp);

        int newwidth = 4;
        float newleft = 600 - newwidth / 2;
        float newtop = 0;
        float newright = newleft + newwidth;
        float newbottom = 1200;
        stdCanvas.drawRect(newleft, newtop, newright, newbottom, stdpaint);

        newleft = 0;
        newtop = 600 - newwidth / 2;
        newright = 1200;
        newbottom = newtop + newwidth;
        stdCanvas.drawRect(newleft, newtop, newright, newbottom, stdpaint);

//        for (int i = 1; i < 55; i++) {
//            int x = coordinate24_2.getCoordinates(i, (testEye))[0] + 600 - 27;
//            int y = coordinate24_2.getCoordinates(i, (testEye))[1] + 600;
//            stdpaint.setTextSize(30);
//            stdpaint.setStyle(Paint.Style.FILL);
//            stdCanvas.drawText(String.valueOf(INTENSITY_RESULT[i]), x, y, stdpaint);
//        }
        if (coordinate24_2 != null) {
            for (int i = 1; i < 55; i++) {
                int[] coordinates = coordinate24_2.getCoordinates(i, testEye);
                int x = coordinates[0] + 600 - 27;
                int y = coordinates[1] + 600;
                stdpaint.setColor(Color.BLACK);
                stdpaint.setTextSize(30);
                stdpaint.setStyle(Paint.Style.FILL);
                stdCanvas.drawText(String.valueOf(INTENSITY_RESULT[i]), x, y, stdpaint);
            }
        } else {
            // Handle the case where coordinate24_2 is null
            Log.e("ScanQRCodeActivity", "coordinate24_2 is null");
        }
    }

    private void parseAndAssignIntensity(String intensity) {
        // Remove the square brackets from the string
        String intensityValues = intensity.replace("[", "").replace("]", "");

        // Split the string by commas
        String[] intensityParts = intensityValues.split(",\\s*");

        // Log the split values
        Log.d("hello2", "intensityParts: " + Arrays.toString(intensityParts));
        Log.d("mello2", "Number of items inside intensityParts: " + intensityParts.length);

        // Ensure the length does not exceed the INTENSITY_RESULT array length
        int limit = Math.min(intensityParts.length, INTENSITY_RESULT.length);

        // Parse and assign the values to the INTENSITY_RESULT array
        for (int i = 0; i < limit; i++) {
            try {
                INTENSITY_RESULT[i] = Integer.parseInt(intensityParts[i].trim());
                Log.d("hello3", "intensity: " + Arrays.toString(INTENSITY_RESULT));
            } catch (NumberFormatException e) {
                // Handle if the string part cannot be parsed as an integer
                Log.e("ScanQRCodeActivity", "Error parsing intensityParts[" + i + "]: " + intensityParts[i], e);
            }
        }

        // Log the final number of items in INTENSITY_RESULT
        Log.d("mello3", "Number of intensityResult items: " + INTENSITY_RESULT.length);
    }

//    private void calculate(){
//         //intensityParts = new String[65]; // Initialize intensityParts array
//
//        if (intensity != null) {
//            intensityParts = intensity.split(",");
//            Log.d("hello2","intensity"+ Arrays.toString(intensityParts));
//            Log.d("mello2", "Number of items inside intensityParts: " + intensityParts.length);
//
//            for (String part : intensityParts) {
//                // Process each part as needed
//            }
//        } else {
//            // Handle the case where intensity is null
//            Log.e("ScanQRCodeActivity", "Intensity is null");
//
//        }
//        int limit = Math.min(intensityParts.length, INTENSITY_RESULT.length);
//
//        for (int i = 0; i < 55; i++) {
//            try {
//                INTENSITY_RESULT[i] = Integer.parseInt(intensityParts[i].trim());
//                Log.d("hello3", "intensity: " + Arrays.toString(INTENSITY_RESULT));
//                Log.d("mello3", "Number of intensityResult: " + INTENSITY_RESULT.length);
//            } catch (NumberFormatException e) {
//                // Handle if the string part cannot be parsed as an integer
//                e.printStackTrace();
//                // You might want to handle this case based on your application's logic
//            }
//        }
//    }

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
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @SuppressLint("StaticFieldLeak")
    public class MyTask extends AsyncTask<Void, Void, Void> {
        public void onPreExecute() {

        }

        public Void doInBackground(Void... unused) {

            //getMappedBitmap();                  // Colored mapping bitmap
            setIntensityPattern();              // Raw data Numbered bitmap
            //drawtotalDeviationPattern();        // Total deviation calculation and bitmap
            //drawPatternDeviationPattern();      // Pattern deviation calculation and bitmap


            Log.d("FAST", "REPORT2");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //final_report.setImageBitmap(bitmap_mapped);
                }
            });

            // Creating the PDF File
            try {
                createPdfWrapper();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        String pdffilepath=savePdf();
        if (pdffilepath != null) {
            viewPdf(pdffilepath);
        } else {
            Toast.makeText(this, "Failed to generate PDF", Toast.LENGTH_SHORT).show();
        }

    }
    private BaseFont bfNormal;
    private BaseFont bfBold;

    private void initializeFonts() {
        try {
            // Load the normal font
            Font fontNormal = FontFactory.getFont("res/font/product_sans_regular.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK);

            if (fontNormal == null) {
                throw new IOException("Failed to load normal font");
            }
            bfNormal = fontNormal.getBaseFont();

            // Load the bold font
            Font fontBold = FontFactory.getFont("res/font/product_sans_bold.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 0.8f, Font.NORMAL, BaseColor.BLACK);

            if (fontBold == null) {
                throw new IOException("Failed to load bold font");
            }
            bfBold = fontBold.getBaseFont();

            if (bfNormal == null || bfBold == null) {
                throw new IOException("BaseFont is null after loading");
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FontError", "Error initializing fonts: " + e.getMessage());
        }
    }

    private void createHeadings(PdfContentByte cb, float x, float y, String text, int size, String fontStyle) {
        if (text == null) {
            Log.e("FontError", "Text is null for the provided heading");
            return; // Early exit if text is null
        }

        cb.beginText();
        BaseFont currentFont = bfNormal;
        if (fontStyle.equals("normal")) {
            currentFont = bfNormal;
        } else {
            currentFont = bfBold;
        }

        if (currentFont != null) {
            cb.setFontAndSize(currentFont, size);
            cb.setTextMatrix(x, y);
            cb.showText(text);
        } else {
            Log.e("FontError", "Font is null for style: " + fontStyle);
        }
        cb.endText();
    }




    private void pdf_logo_and_details(Document document, PdfContentByte cb) {
        createHeadings(cb, 120, 805, doctorName, 9, "bold");
        createHeadings(cb, 120, 793, hospital, 9, "normal");
        createHeadings(cb, 120, 781, doctorAddress, 9, "normal");
        createHeadings(cb, 120, 769, doctorPhone, 9, "normal");


        //File imgFile = new File(doctor_logo_path);
//        if(imgFile.exists()) {
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            createImages(document,myBitmap,25,770,50,50);
//        }
//        else {
//            Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
//            createImages(document, icon,25,770,50,50);
//        }
//
        Drawable d3 = getResources().getDrawable(R.drawable.ivalogo);
        BitmapDrawable bitDw3 = ((BitmapDrawable) d3);
        Bitmap bmp_logo3 = bitDw3.getBitmap();
        createImages(document,bmp_logo3,460,770,100,100);

    }

    private void pdf_name_mrn_box(Document document, PdfContentByte cb) {
        Rectangle rectangle3 = new Rectangle(0, 820, 600, 850);
        BaseColor myColor = new BaseColor(1, 194, 193);
        rectangle3.setBackgroundColor(myColor);
        cb.rectangle(rectangle3);


        createHeadings(cb, 30, 745, "Name", 10, "bold");
        createHeadings(cb, 100, 745, patientName, 10, "normal");

        createHeadings(cb, 30, 730, "Patient MRN", 10, "bold");
        createHeadings(cb, 100, 730, mrn, 10, "normal");
        createHeadings(cb, 420, 730, "Phone no", 10, "bold");
        createHeadings(cb, 490, 730, phoneNo, 10, "normal");

        createHeadings(cb, 420, 745, "Eye", 10, "bold");

        createHeadings(cb, 490, 745,testEye, 10, "normal");

    }

    private void pdf_details_col_1(Document document, PdfContentByte cb) {
        createHeadings(cb, 30, 713, "CENTRAL TOP STANDARD THRESHOLD", 8, "bold");


        createHeadings(cb, 30, 690, "Fixation Loss", 9, "bold");
        createHeadings(cb, 100, 690, "-", 9, "normal");


        createHeadings(cb, 30, 678, "False POS Error", 9, "bold");
        createHeadings(cb, 100, 678, falsePOSError, 9, "normal");


        createHeadings(cb, 30, 666, "False NEG Error", 9, "bold");
        createHeadings(cb, 100, 666, falseNEGError, 9, "normal");


        createHeadings(cb, 30, 654, "Test Duration", 9, "bold");
        createHeadings(cb, 100, 654, testDuration+"min", 9, "normal");


        createHeadings(cb, 30, 642, "Fovea", 9, "bold");
        createHeadings(cb, 100, 642, fovea, 9, "normal");

    }

    private void pdf_details_col_2(Document document, PdfContentByte cb) {
        createHeadings(cb, 140, 690, "Fixation Target", 9, "bold");
        createHeadings(cb, 220, 690, "Central", 9, "normal");

        createHeadings(cb, 140, 678, "Fixation monitor", 9, "bold");
        createHeadings(cb, 220, 678, "Blind Spot Test", 9, "normal");

        createHeadings(cb, 140, 666, "Stimulus Size", 9, "bold");
        createHeadings(cb, 220, 666, stimulusSize, 9, "normal");
    }

    private void pdf_details_col_3(Document document, PdfContentByte cb){
        createHeadings(cb, 300, 690, "Visual Acuity", 9, "bold");
        createHeadings(cb, 390, 690, visualAcuity, 9, "normal");

        createHeadings(cb, 300, 678, "Power", 9, "bold");

        createHeadings(cb, 340, 678, power, 9, "normal");


    }
    private void pdf_details_col_4(Document document, PdfContentByte cb) {
        createHeadings(cb, 450, 690, "Date", 9, "bold");
        createHeadings(cb, 490, 690, date, 9, "normal");
        createHeadings(cb, 450, 678, "Time", 9, "bold");
        createHeadings(cb, 490, 678, time, 9, "normal");

        createHeadings(cb, 450, 666, "Age", 9, "bold");
        createHeadings(cb, 490, 666, age + " years", 9, "normal");

        createHeadings(cb, 450, 654, "Gender", 9, "bold");
        createHeadings(cb, 490, 654, gender, 9, "normal");

        createImages(document, stdBmp, 30, 420, 200, 210);
        //createImages(document, bitmap_mapped, 270, 420, 200, 210);

        createHeadings(cb, 490, 610, "Sensitivity", 9, "normal");
        createHeadings(cb, 490, 600, "threshold [dB]", 9, "normal");
    }

        private void threshold_range_plot(Document document, PdfContentByte cb){
        Paint stdpaint = new Paint();
        stdpaint.setColor(Color.BLACK);
        stdpaint.setStyle(Paint.Style.FILL);

        Bitmap bm1_1 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Bitmap bm2_1 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Bitmap bm3_1 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Bitmap bm4_1 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Bitmap bm5_1 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Bitmap bm6_1 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Bitmap bm7_1 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Bitmap bm8_1 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Bitmap bm9_1 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);

        Canvas stdcanvas1_1=new Canvas(bm1_1);
        Canvas stdcanvas2_1=new Canvas(bm2_1);
        Canvas stdcanvas3_1=new Canvas(bm3_1);
        Canvas stdcanvas4_1=new Canvas(bm4_1);
        Canvas stdcanvas5_1=new Canvas(bm5_1);
        Canvas stdcanvas6_1=new Canvas(bm6_1);
        Canvas stdcanvas7_1=new Canvas(bm7_1);
        Canvas stdcanvas8_1=new Canvas(bm8_1);
        Canvas stdcanvas9_1=new Canvas(bm9_1);

        stdpaint.setColor(TemporaryVariables.getColorCode(0));
        stdcanvas1_1.drawRect(0,0,60,60,stdpaint);

        stdpaint.setColor(TemporaryVariables.getColorCode(3));
        stdcanvas2_1.drawRect(0,0,60,60,stdpaint);

        stdpaint.setColor(TemporaryVariables.getColorCode(8));
        stdcanvas3_1.drawRect(0,0,60,60,stdpaint);

        stdpaint.setColor(TemporaryVariables.getColorCode(13));
        stdcanvas4_1.drawRect(0,0,60,60,stdpaint);

        stdpaint.setColor(TemporaryVariables.getColorCode(18));
        stdcanvas5_1.drawRect(0,0,60,60,stdpaint);

        stdpaint.setColor(TemporaryVariables.getColorCode(23));
        stdcanvas6_1.drawRect(0,0,60,60,stdpaint);

        stdpaint.setColor(TemporaryVariables.getColorCode(28));
        stdcanvas7_1.drawRect(0,0,60,60,stdpaint);

        stdpaint.setColor(TemporaryVariables.getColorCode(33));
        stdcanvas8_1.drawRect(0,0,60,60,stdpaint);

        stdpaint.setColor(TemporaryVariables.getColorCode(35));
        stdcanvas9_1.drawRect(0,0,60,60,stdpaint);

        createImages(document, bm9_1, 540, 570, 20, 20);
        createImages(document, bm8_1, 540, 555, 20, 20);
        createImages(document, bm7_1, 540, 540, 20, 20);
        createImages(document, bm6_1, 540, 525, 20, 20);
        createImages(document, bm5_1, 540, 510, 20, 20);
        createImages(document, bm4_1, 540, 495, 20, 20);
        createImages(document, bm3_1, 540, 480, 20, 20);
        createImages(document, bm2_1, 540, 465, 20, 20);
        createImages(document, bm1_1, 540, 450, 20, 20);

        createHeadings(cb,500,580,"34 - 36",9,"normal");
        createHeadings(cb,500,565,"31 - 35",9,"normal");
        createHeadings(cb,500,550,"26 - 30",9,"normal");
        createHeadings(cb,500,535,"21 - 25",9,"normal");
        createHeadings(cb,500,520,"16 - 20",9,"normal");
        createHeadings(cb,500,505,"11 - 15",9,"normal");
        createHeadings(cb,500,490,"6 - 10",9,"normal");
        createHeadings(cb,500,475,"1 - 5",9,"normal");
        createHeadings(cb,500,460,"0",9,"normal");
    }
    private void p_value_range_plotting(Document document, PdfContentByte cb){
        Paint stdpaint = new Paint();
        stdpaint.setColor(Color.BLACK);
        stdpaint.setStyle(Paint.Style.FILL);

        Bitmap bm1 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Bitmap bm2 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Bitmap bm3 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);
        Bitmap bm4 = Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888);

        Canvas stdcanvas1=new Canvas(bm1);
        Canvas stdcanvas2=new Canvas(bm2);
        Canvas stdcanvas3=new Canvas(bm3);
        Canvas stdcanvas4=new Canvas(bm4);

        // <5

        stdcanvas1.drawCircle(20,20,4,stdpaint);
        stdcanvas1.drawCircle(20,40,4,stdpaint);
        stdcanvas1.drawCircle(40,20,4,stdpaint);
        stdcanvas1.drawCircle(40,40,4,stdpaint);

        //<2

        stdcanvas2.drawCircle(10,10,4,stdpaint);
        stdcanvas2.drawCircle(30,10,4,stdpaint);
        stdcanvas2.drawCircle(50,10,4,stdpaint);

        stdcanvas2.drawCircle(10,30,4,stdpaint);
        stdcanvas2.drawCircle(50,30,4,stdpaint);

        stdcanvas2.drawCircle(10,50,4,stdpaint);
        stdcanvas2.drawCircle(30,50,4,stdpaint);
        stdcanvas2.drawCircle(50,50,4,stdpaint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(2);

        stdcanvas2.drawLine(30,10,10,30,stdpaint);
        stdcanvas2.drawLine(10,30,30,50,stdpaint);
        stdcanvas2.drawLine(30,50,50,30,stdpaint);
        stdcanvas2.drawLine(50,30,30,10,stdpaint);

        // <1
        paint.setStyle(Paint.Style.FILL);
        stdcanvas3.drawCircle(10,10,4,stdpaint);
        stdcanvas3.drawCircle(30,10,4,stdpaint);
        stdcanvas3.drawCircle(50,10,4,stdpaint);

        stdcanvas3.drawCircle(10,30,4,stdpaint);
        stdcanvas3.drawCircle(30,30,4,stdpaint);
        stdcanvas3.drawCircle(50,30,4,stdpaint);

        stdcanvas3.drawCircle(10,50,4,stdpaint);
        stdcanvas3.drawCircle(30,50,4,stdpaint);
        stdcanvas3.drawCircle(50,50,4,stdpaint);

        //<0.5
        stdcanvas4.drawRect(0,0,60,60,stdpaint);


        createImages(document, bm1, 180, 105, 20, 20);
        createImages(document, bm2, 180, 90, 20, 20);
        createImages(document, bm3, 180, 75, 20, 20);
        createImages(document, bm4, 180, 60, 20, 20);

        createHeadings(cb,200,115,"< 5%",10,"normal");
        createHeadings(cb,200,100,"< 2%",10,"normal");
        createHeadings(cb,200,85," < 1%",10,"normal");
        createHeadings(cb,200,70,"< 0.5%",10,"normal");
    }
    private void VFI_MD_PSD_GHT_Box(Document document, PdfContentByte cb) {
        Rectangle rectangle2 = new Rectangle(385, 300, 580, 250);
        rectangle2.setBorder(Rectangle.BOX);
        rectangle2.setBorderColor(BaseColor.BLACK);
        rectangle2.setBorderWidth(1);
        cb.rectangle(rectangle2);

        createHeadings(cb, 390, 290, "Visual Field Index (VFI)", 9, "bold");
        createHeadings(cb, 540, 290, "" + visualFieldIndex + "%", 9, "normal");

        createHeadings(cb, 390, 280, "Mean Deviation (MD)", 9, "bold");
        createHeadings(cb, 540, 280, meanDeviation + " dB", 9, "normal");

        createHeadings(cb, 390, 270, "Pattern Standard Deviation (PSD)", 9, "bold");
        createHeadings(cb, 540, 270, patternStandardDeviation + " dB", 9, "normal");


        createHeadings(cb, 390, 255, "GHT", 9, "bold");
        createHeadings(cb, 440, 255, ght, 9, "normal");


    }

    private void eye_tracking_box(Document document, PdfContentByte cb) {
        Rectangle rectangle5 = new Rectangle(385, 180, 580, 230);
        rectangle5.setBorder(Rectangle.BOX);
        rectangle5.setBorderColor(BaseColor.BLACK);
        rectangle5.setBorderWidth(1);
        cb.rectangle(rectangle5);

        createHeadings(cb, 390, 220, "Eye tracking Parameters", 9, "bold");
        createHeadings(cb, 390, 205, "Eye Tracking:  " + eyeTracking, 9, "normal");

        createHeadings(cb, 390, 195, "Looked Away:  " + lookedAway, 9, "normal");
        //createHeadings(cb, 390, 185,"No. of Blinks:  "+ TemporaryVariables.getLookedAway(),9,"normal");

    }

    //    private void additional_notes_data(Document document, PdfContentByte cb){
//        if(!patientAdditionalNotes.equals("NA") && !patientAdditionalNotes.equals("")) {
//            createHeadings(cb,390,155,"Additional Comments",9,"bold");
//            Font font = FontFactory.getFont("res/font/product_sans_medium.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 9, Font.NORMAL, BaseColor.BLACK);
//            Rectangle rect = new Rectangle(390, 100, 580, 150);
//            ColumnText ct = new ColumnText(docWriter.getDirectContent());
//            ct.setSimpleColumn(rect);
//            ct.addElement(new Paragraph(patientAdditionalNotes, font));
//            try {
//                ct.go();
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    private void footer(Document document, PdfContentByte cb) {

        createHeadings(cb, 50, 45, "Disclaimer: This report is only indicative and not conclusive, please refer to an expert before further action/ medication.", 9, "normal");
        //createHeadings(cb, 30,  30, "iVA -Intelligent Vision Analyzer " + " | Software: " +software_version +" | Hardware: "+hardware_version, 9, "normal");
        createHeadings(cb, 390, 30, "Developed by Alfaleus", 9, "normal");

        Rectangle rectangle4 = new Rectangle(0, 0, 800, 20);
        BaseColor myColor = new BaseColor(1, 194, 193);
        rectangle4.setBackgroundColor(myColor);
        cb.rectangle(rectangle4);

    }

    private void createImages(Document document, Bitmap bmp, float xPosition, float yPosition, float xSize, float ySize) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image image = null;
        try {
            image = Image.getInstance(stream.toByteArray());
        } catch (BadElementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (image != null) {
            image.setAbsolutePosition(xPosition, yPosition);
            image.scaleToFit(xSize, ySize);
        }
        try {
            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private String savePdf() {
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        String format = s.format(new Date());
        String pdfFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + format + "scannedData.pdf";
        File file = new File(pdfFile);

        OutputStream output = null;
        try {
            output = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Document document = new Document(PageSize.A4, 36, 36, 36, 36); // A4 size with 36 unit margins
        PdfWriter docWriter = null;
        try {
            docWriter = PdfWriter.getInstance(document, output);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        document.open();
        initializeFonts();

        PdfContentByte cb = docWriter.getDirectContent();

        // BORDER TOP
        Rectangle rectangle = new Rectangle(25, 725, 575, 760);
        rectangle.setBorder(Rectangle.BOX);
        rectangle.setBorderColor(BaseColor.BLACK);
        rectangle.setBorderWidth(1);
        cb.rectangle(rectangle);

        // Add content here
        pdf_logo_and_details(document, cb);
        pdf_name_mrn_box(document, cb);
        pdf_details_col_1(document, cb);
        pdf_details_col_2(document, cb);
        pdf_details_col_3(document,cb);
        pdf_details_col_4(document, cb);
        // Uncomment and adjust as needed
         threshold_range_plot(document, cb);
        // createImages(document, totalDeviationBmp1, 30, 250, 150, 155);
        // createHeadings(cb, 75, 235, "Total Deviation", 9, "normal");
        // createImages(document, totalDeviationBmp2, 30, 80, 150, 155);
        // createImages(document, patternDeviationBmp1, 210, 250, 150, 155);
        // createHeadings(cb, 260, 235, "Pattern Deviation", 9, "normal");
        // createImages(document, patternDeviationBmp2, 210, 80, 150, 155);
         p_value_range_plotting(document, cb);
        VFI_MD_PSD_GHT_Box(document, cb);
        eye_tracking_box(document, cb);
        // additional_notes_data(document, cb);
        footer(document, cb);

        document.close();

        try {
            output.flush();
            output.close();
            docWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pdfFile;
    }


    private void viewPdf(String pdfFilePath) {
        try {
            File pdfFile = new File(pdfFilePath);

            // Get URI for the file using FileProvider
            Uri uri = FileProvider.getUriForFile(this,
                    getPackageName() + ".provider", pdfFile);

            // Check if URI is not null
            if (uri != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(uri, "application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                // Verify that there are apps available to open this intent
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "No PDF viewer app installed", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Failed to get PDF URI", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error opening PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



}