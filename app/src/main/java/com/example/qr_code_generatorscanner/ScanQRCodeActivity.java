package com.example.qr_code_generatorscanner;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.VIBRATE;



import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import com.example.qr_code_generatorscanner.NormativeData._24_2_NormativeData;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
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
import android.provider.Settings;
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
import java.util.List;
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


    private final int PERMISSION_REQUEST_CODE=100;
    private static final int WRITE_SETTINGS_REQUEST_CODE = 1001;
    private static final int ALL_FILES_ACCESS_REQUEST_CODE = 1002;
    private boolean flagSetting;

    double psd = 0;
    String software_version="", hardware_version="";



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

    private String pdfFile;
    SharedPreferences sharedPreferences;
    private static final String MY_PREFS_NAME = "METADATA_PS_OPERATOR";



    private Canvas canvas;
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
        bitmap_mapped = Bitmap.createBitmap(1200, 1200, Bitmap.Config.ARGB_8888);
        paint = new Paint();
        canvas = new Canvas(bitmap_mapped);

        coordinate24_2 = new _24_2_Coordinates(this);

        sharedPreferences = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);

        software_version = TemporaryVariables.getSoftwareVersion();

        hardware_version = TemporaryVariables.getHardwareVersion();

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            flagSetting = Settings.System.canWrite(getApplicationContext());
        }


//        if (checkPermission()) {
//            Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
//        } else {
//            requestPermission();
//        }
        if (checkPermission() && checkSettingsPermission()) {
            Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
        } else {
            if (!checkPermission()) {
                requestPermission();
            } else if (!checkSettingsPermission()) {
                askPermission();
            }
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
                if(intensity!=null){
                parseAndAssignIntensity(intensity);}
                else{
                    Toast.makeText(ScanQRCodeActivity.this,"Data not scanned",Toast.LENGTH_SHORT).show();
                }
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

    private void getMappedBitmap(){
        int width = 4;
        float left = 600 - width / 2;
        float top = 0;
        float right = left + width;
        float bottom = 1200;

        canvas.drawRect(left, top, right, bottom, paint);
        left = 0;
        top = 600 - width / 2;
        right = 1200;
        bottom = top + width;
        canvas.drawRect(left, top, right, bottom, paint);

        int distancePoint_10degree = (int) 183.33;
        int distancePoint_20degree = (int) (183.33 * 2);
        int distancePoint_30degree = (int) (183.33 * 3);

        left = 600 + distancePoint_10degree - width;
        top = 600 - 3 * width;
        right = left + width;
        bottom = top + 6 * width;
        canvas.drawRect(left, top, right, bottom, paint);

        left = 600 + distancePoint_20degree - width;
        top = 600 - 3 * width;
        right = left + width;
        bottom = top + 6 * width;
        canvas.drawRect(left, top, right, bottom, paint);

        left = 600 + distancePoint_30degree - width;
        top = 600 - 3 * width;
        right = left + width;
        bottom = top + 6 * width;
        canvas.drawRect(left, top, right, bottom, paint);

        left = 600 - distancePoint_10degree - width;
        top = 600 - 3 * width;
        right = left + width;
        bottom = top + 6 * width;
        canvas.drawRect(left, top, right, bottom, paint);

        left = 600 - distancePoint_20degree - width;
        top = 600 - 3 * width;
        right = left + width;
        bottom = top + 6 * width;
        canvas.drawRect(left, top, right, bottom, paint);

        left = 600 - distancePoint_30degree - width;
        top = 600 - 3 * width;
        right = left + width;
        bottom = top + 6 * width;
        canvas.drawRect(left, top, right, bottom, paint);

        left = 600 - 3 * width;
        top = 600 - distancePoint_10degree - width;
        right = left + 6 * width;
        bottom = top + width;
        canvas.drawRect(left, top, right, bottom, paint);

        left = 600 - 3 * width;
        top = 600 - distancePoint_20degree - width;
        right = left + 6 * width;
        bottom = top + width;
        canvas.drawRect(left, top, right, bottom, paint);

        left = 600 - 3 * width;
        top = 600 - distancePoint_30degree - width;
        right = left + 6 * width;
        bottom = top + width;
        canvas.drawRect(left, top, right, bottom, paint);

        left = 600 - 3 * width;
        top = 600 + distancePoint_10degree - width;
        right = left + 6 * width;
        bottom = top + width;
        canvas.drawRect(left, top, right, bottom, paint);

        left = 600 - 3 * width;
        top = 600 + distancePoint_20degree - width;
        right = left + 6 * width;
        bottom = top + width;
        canvas.drawRect(left, top, right, bottom, paint);

        left = 600 - 3 * width;
        top = 600 + distancePoint_30degree - width;
        right = left + 6 * width;
        bottom = top + width;
        canvas.drawRect(left, top, right, bottom, paint);


        for (int i = 0; i < 55; i++) {
            int[] coor = coordinate24_2.getCoordinates(i, String.valueOf(testEye));
            int INTENSITY = INTENSITY_RESULT[i];
            if (INTENSITY < 0)
                INTENSITY = 0;

            ABCDE(i);
            if (testEye.equals("Right")) {
                draw_dots_rectangle(coor[0] + 600 - 55, coor[1] + 600 - 55, INTENSITY, i);
            }

            else if (testEye.equals("Left")) {
                draw_dots_rectangle(coor[0] + 600 - 55, coor[1] + 600 - 55, INTENSITY, i);
            }
        }

        if (testEye.equals("Right")) {
            small_rect_r[0][2] = getAverageContrast(INTENSITY_RESULT[45], INTENSITY_RESULT[51], INTENSITY_RESULT[17]);
            small_rect_r[1][2] = getAverageContrast(INTENSITY_RESULT[54], INTENSITY_RESULT[50], INTENSITY_RESULT[12]);
            small_rect_r[2][2] = getAverageContrast(INTENSITY_RESULT[37], INTENSITY_RESULT[45], INTENSITY_RESULT[18]);
            small_rect_r[3][2] = getAverageContrast(INTENSITY_RESULT[50], INTENSITY_RESULT[44], INTENSITY_RESULT[11]);
            small_rect_r[4][2] = getAverageContrast(INTENSITY_RESULT[11], INTENSITY_RESULT[5], INTENSITY_RESULT[23]);
            small_rect_r[5][2] = getAverageContrast(INTENSITY_RESULT[18], INTENSITY_RESULT[10], INTENSITY_RESULT[6]);
            small_rect_r[6][2] = getAverageContrast(INTENSITY_RESULT[5], INTENSITY_RESULT[1], INTENSITY_RESULT[24]);
            small_rect_r[7][2] = getAverageContrast(INTENSITY_RESULT[4], INTENSITY_RESULT[10], INTENSITY_RESULT[5]);
            small_rect_r[8][2] = getAverageContrast(INTENSITY_RESULT[28], INTENSITY_RESULT[37], INTENSITY_RESULT[19]);
            small_rect_r[9][2] = getAverageContrast(INTENSITY_RESULT[11], INTENSITY_RESULT[19], INTENSITY_RESULT[22]);
        } else {
            small_rect_l[0][2] = getAverageContrast(INTENSITY_RESULT[45], INTENSITY_RESULT[51], INTENSITY_RESULT[17]);
            small_rect_l[1][2] = getAverageContrast(INTENSITY_RESULT[54], INTENSITY_RESULT[50], INTENSITY_RESULT[12]);
            small_rect_l[2][2] = getAverageContrast(INTENSITY_RESULT[37], INTENSITY_RESULT[45], INTENSITY_RESULT[18]);
            small_rect_l[3][2] = getAverageContrast(INTENSITY_RESULT[50], INTENSITY_RESULT[44], INTENSITY_RESULT[11]);
            small_rect_l[4][2] = getAverageContrast(INTENSITY_RESULT[11], INTENSITY_RESULT[5], INTENSITY_RESULT[23]);
            small_rect_l[5][2] = getAverageContrast(INTENSITY_RESULT[18], INTENSITY_RESULT[10], INTENSITY_RESULT[6]);
            small_rect_l[6][2] = getAverageContrast(INTENSITY_RESULT[5], INTENSITY_RESULT[1], INTENSITY_RESULT[24]);
            small_rect_l[7][2] = getAverageContrast(INTENSITY_RESULT[4], INTENSITY_RESULT[10], INTENSITY_RESULT[5]);
            small_rect_l[8][2] = getAverageContrast(INTENSITY_RESULT[28], INTENSITY_RESULT[44], INTENSITY_RESULT[10]);
            small_rect_l[9][2] = getAverageContrast(INTENSITY_RESULT[18], INTENSITY_RESULT[19], INTENSITY_RESULT[7]);
        }

        for (int i = 0; i < 10; i++) {
            if (testEye.equals("Right")) {
                draw_dots_small_rectangle((int) small_rect_r[i][0], (int) small_rect_r[i][1], i);
            } else {
                draw_dots_small_rectangle((int) small_rect_l[i][0], (int) small_rect_l[i][1], i);
            }
        }
        /*
        paint.setColor(Color.BLACK);
        if (testEye.equals("Right")){
            double BS_degree = (BS_Device-630)/6.5;
            double BS_Screen = BS_degree*18.33;
            Log.d("BS_",BS_Device+"-"+BS_degree+"-"+BS_Screen);
            canvas.drawOval((float)(BS_Screen-2.5*18.33)+600, (float)(-2.25*18.33)+600,(float)(BS_Screen+18.33*2.5)+600, (float)(5.25*18.33)+600, paint);
        }
        else{
            double BS_degree = BS_Device/6.5;
            double BS_Screen = BS_degree*18.33;
            Log.d("BS_",BS_Device+"-"+BS_degree+"-"+BS_Screen);
            canvas.drawOval((float)(-BS_Screen-2.5*18.33)+600, (float)(-2.25*18.33)+600,(float)(-BS_Screen+18.33*2.5)+600, (float)(5.25*18.33)+600, paint);
        }
        */

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
    public void ABCDE(int a){
        if (testEye.equals("Left")) {
            if (a == 1) {
                A = INTENSITY_RESULT[2];
                B = INTENSITY_RESULT[6];
                C = INTENSITY_RESULT[1];
                D = INTENSITY_RESULT[1];
                E = INTENSITY_RESULT[1];
            } else if (a == 2) {
                A = INTENSITY_RESULT[3];
                B = INTENSITY_RESULT[7];
                C = INTENSITY_RESULT[2];
                D = INTENSITY_RESULT[2];
                E = INTENSITY_RESULT[1];
            } else if (a == 3) {
                A = INTENSITY_RESULT[4];
                B = INTENSITY_RESULT[8];
                C = INTENSITY_RESULT[3];
                D = INTENSITY_RESULT[3];
                E = INTENSITY_RESULT[2];
            } else if (a == 4) {
                A = INTENSITY_RESULT[5];
                B = INTENSITY_RESULT[9];
                C = INTENSITY_RESULT[4];
                D = INTENSITY_RESULT[4];
                E = INTENSITY_RESULT[3];
            } else if (a == 5) {
                A = INTENSITY_RESULT[6];
                B = INTENSITY_RESULT[12];
                C = INTENSITY_RESULT[5];
                D = INTENSITY_RESULT[5];
                E = INTENSITY_RESULT[5];
            } else if (a == 6) {
                A = INTENSITY_RESULT[7];
                B = INTENSITY_RESULT[13];
                C = INTENSITY_RESULT[6];
                D = INTENSITY_RESULT[1];
                E = INTENSITY_RESULT[5];
            } else if (a == 7) {
                A = INTENSITY_RESULT[8];
                B = INTENSITY_RESULT[14];
                C = INTENSITY_RESULT[7];
                D = INTENSITY_RESULT[2];
                E = INTENSITY_RESULT[6];
            } else if (a == 8) {
                A = INTENSITY_RESULT[9];
                B = INTENSITY_RESULT[15];
                C = INTENSITY_RESULT[8];
                D = INTENSITY_RESULT[3];
                E = INTENSITY_RESULT[7];
            } else if (a == 9) {
                A = INTENSITY_RESULT[10];
                B = INTENSITY_RESULT[16];
                C = INTENSITY_RESULT[9];
                D = INTENSITY_RESULT[4];
                E = INTENSITY_RESULT[8];
            } else if (a == 10) {
                A = INTENSITY_RESULT[5];
                B = INTENSITY_RESULT[17];
                C = INTENSITY_RESULT[10];
                D = INTENSITY_RESULT[5];
                E = INTENSITY_RESULT[9];
            } else if (a == 11) {
                A = INTENSITY_RESULT[12];
                B = INTENSITY_RESULT[20];
                C = INTENSITY_RESULT[11];
                D = INTENSITY_RESULT[11];
                E = INTENSITY_RESULT[11];
            } else if (a == 12) {
                A = INTENSITY_RESULT[13];
                B = INTENSITY_RESULT[21];
                C = INTENSITY_RESULT[12];
                D = INTENSITY_RESULT[5];
                E = INTENSITY_RESULT[11];
            } else if (a == 13) {
                A = INTENSITY_RESULT[14];
                B = INTENSITY_RESULT[22];
                C = INTENSITY_RESULT[13];
                D = INTENSITY_RESULT[6];
                E = INTENSITY_RESULT[12];
            } else if (a == 14) {
                A = INTENSITY_RESULT[15];
                B = INTENSITY_RESULT[23];
                C = INTENSITY_RESULT[14];
                D = INTENSITY_RESULT[7];
                E = INTENSITY_RESULT[13];
            } else if (a == 15) {
                A = INTENSITY_RESULT[16];
                B = INTENSITY_RESULT[24];
                C = INTENSITY_RESULT[15];
                D = INTENSITY_RESULT[8];
                E = INTENSITY_RESULT[14];
            } else if (a == 16) {
                A = INTENSITY_RESULT[17];
                B = INTENSITY_RESULT[25];
                C = INTENSITY_RESULT[16];
                D = INTENSITY_RESULT[9];
                E = INTENSITY_RESULT[15];
            } else if (a == 17) {
                A = INTENSITY_RESULT[18];
                B = INTENSITY_RESULT[26];
                C = INTENSITY_RESULT[17];
                D = INTENSITY_RESULT[10];
                E = INTENSITY_RESULT[16];
            } else if (a == 18) {
                A = INTENSITY_RESULT[18];
                B = INTENSITY_RESULT[27];
                C = INTENSITY_RESULT[18];
                D = INTENSITY_RESULT[18];
                E = INTENSITY_RESULT[17];
            } else if (a == 19) {
                A = INTENSITY_RESULT[19];
                B = INTENSITY_RESULT[28];
                C = INTENSITY_RESULT[19];
                D = INTENSITY_RESULT[19];
                E = INTENSITY_RESULT[27];
            } else if (a == 20) {
                A = INTENSITY_RESULT[21];
                B = INTENSITY_RESULT[29];
                C = INTENSITY_RESULT[20];
                D = INTENSITY_RESULT[11];
                E = INTENSITY_RESULT[20];
            } else if (a == 21) {
                A = INTENSITY_RESULT[22];
                B = INTENSITY_RESULT[30];
                C = INTENSITY_RESULT[21];
                D = INTENSITY_RESULT[12];
                E = INTENSITY_RESULT[20];
            } else if (a == 22) {
                A = INTENSITY_RESULT[23];
                B = INTENSITY_RESULT[31];
                C = INTENSITY_RESULT[22];
                D = INTENSITY_RESULT[13];
                E = INTENSITY_RESULT[21];
            } else if (a == 23) {
                A = INTENSITY_RESULT[24];
                B = INTENSITY_RESULT[32];
                C = INTENSITY_RESULT[23];
                D = INTENSITY_RESULT[14];
                E = INTENSITY_RESULT[22];
            } else if (a == 24) {
                A = INTENSITY_RESULT[25];
                B = INTENSITY_RESULT[33];
                C = INTENSITY_RESULT[24];
                D = INTENSITY_RESULT[15];
                E = INTENSITY_RESULT[23];
            } else if (a == 25) {
                A = INTENSITY_RESULT[26];
                B = INTENSITY_RESULT[34];
                C = INTENSITY_RESULT[25];
                D = INTENSITY_RESULT[16];
                E = INTENSITY_RESULT[24];
            } else if (a == 26) {
                A = INTENSITY_RESULT[27];
                B = INTENSITY_RESULT[35];
                C = INTENSITY_RESULT[26];
                D = INTENSITY_RESULT[17];
                E = INTENSITY_RESULT[25];
            } else if (a == 27) {
                A = INTENSITY_RESULT[19];
                B = INTENSITY_RESULT[36];
                C = INTENSITY_RESULT[27];
                D = INTENSITY_RESULT[18];
                E = INTENSITY_RESULT[26];
            } else if (a == 28) {
                A = INTENSITY_RESULT[28];
                B = INTENSITY_RESULT[10];
                C = INTENSITY_RESULT[28];
                D = INTENSITY_RESULT[19];
                E = INTENSITY_RESULT[36];
            } else if (a == 29) {
                A = INTENSITY_RESULT[30];
                B = INTENSITY_RESULT[37];
                C = INTENSITY_RESULT[29];
                D = INTENSITY_RESULT[20];
                E = INTENSITY_RESULT[29];
            } else if (a == 30) {
                A = INTENSITY_RESULT[31];
                B = INTENSITY_RESULT[38];
                C = INTENSITY_RESULT[30];
                D = INTENSITY_RESULT[21];
                E = INTENSITY_RESULT[29];
            } else if (a == 31) {
                A = INTENSITY_RESULT[32];
                B = INTENSITY_RESULT[39];
                C = INTENSITY_RESULT[31];
                D = INTENSITY_RESULT[22];
                E = INTENSITY_RESULT[30];
            } else if (a == 32) {
                A = INTENSITY_RESULT[33];
                B = INTENSITY_RESULT[40];
                C = INTENSITY_RESULT[32];
                D = INTENSITY_RESULT[23];
                E = INTENSITY_RESULT[31];
            } else if (a == 33) {
                A = INTENSITY_RESULT[34];
                B = INTENSITY_RESULT[41];
                C = INTENSITY_RESULT[33];
                D = INTENSITY_RESULT[24];
                E = INTENSITY_RESULT[32];
            } else if (a == 34) {
                A = INTENSITY_RESULT[35];
                B = INTENSITY_RESULT[42];
                C = INTENSITY_RESULT[34];
                D = INTENSITY_RESULT[25];
                E = INTENSITY_RESULT[33];
            } else if (a == 35) {
                A = INTENSITY_RESULT[36];
                B = INTENSITY_RESULT[43];
                C = INTENSITY_RESULT[35];
                D = INTENSITY_RESULT[26];
                E = INTENSITY_RESULT[34];
            } else if (a == 36) {
                A = INTENSITY_RESULT[28];
                B = INTENSITY_RESULT[44];
                C = INTENSITY_RESULT[36];
                D = INTENSITY_RESULT[27];
                E = INTENSITY_RESULT[35];
            } else if (a == 37) {
                A = INTENSITY_RESULT[38];
                B = INTENSITY_RESULT[37];
                C = INTENSITY_RESULT[37];
                D = INTENSITY_RESULT[29];
                E = INTENSITY_RESULT[37];
            } else if (a == 38) {
                A = INTENSITY_RESULT[39];
                B = INTENSITY_RESULT[45];
                C = INTENSITY_RESULT[38];
                D = INTENSITY_RESULT[30];
                E = INTENSITY_RESULT[37];
            } else if (a == 39) {
                A = INTENSITY_RESULT[40];
                B = INTENSITY_RESULT[46];
                C = INTENSITY_RESULT[39];
                D = INTENSITY_RESULT[31];
                E = INTENSITY_RESULT[38];
            } else if (a == 40) {
                A = INTENSITY_RESULT[41];
                B = INTENSITY_RESULT[47];
                C = INTENSITY_RESULT[40];
                D = INTENSITY_RESULT[32];
                E = INTENSITY_RESULT[39];
            } else if (a == 41) {
                A = INTENSITY_RESULT[42];
                B = INTENSITY_RESULT[48];
                C = INTENSITY_RESULT[41];
                D = INTENSITY_RESULT[33];
                E = INTENSITY_RESULT[40];
            } else if (a == 42) {
                A = INTENSITY_RESULT[43];
                B = INTENSITY_RESULT[49];
                C = INTENSITY_RESULT[42];
                D = INTENSITY_RESULT[34];
                E = INTENSITY_RESULT[41];
            } else if (a == 43) {
                A = INTENSITY_RESULT[44];
                B = INTENSITY_RESULT[50];
                C = INTENSITY_RESULT[43];
                D = INTENSITY_RESULT[35];
                E = INTENSITY_RESULT[42];
            } else if (a == 44) {
                A = INTENSITY_RESULT[44];
                B = INTENSITY_RESULT[44];
                C = INTENSITY_RESULT[44];
                D = INTENSITY_RESULT[36];
                E = INTENSITY_RESULT[43];
            } else if (a == 45) {
                A = INTENSITY_RESULT[46];
                B = INTENSITY_RESULT[45];
                C = INTENSITY_RESULT[45];
                D = INTENSITY_RESULT[38];
                E = INTENSITY_RESULT[45];
            } else if (a == 46) {
                A = INTENSITY_RESULT[47];
                B = INTENSITY_RESULT[51];
                C = INTENSITY_RESULT[46];
                D = INTENSITY_RESULT[39];
                E = INTENSITY_RESULT[45];
            } else if (a == 47) {
                A = INTENSITY_RESULT[48];
                B = INTENSITY_RESULT[52];
                C = INTENSITY_RESULT[47];
                D = INTENSITY_RESULT[40];
                E = INTENSITY_RESULT[46];
            } else if (a == 48) {
                A = INTENSITY_RESULT[49];
                B = INTENSITY_RESULT[53];
                C = INTENSITY_RESULT[48];
                D = INTENSITY_RESULT[41];
                E = INTENSITY_RESULT[47];
            } else if (a == 49) {
                A = INTENSITY_RESULT[50];
                B = INTENSITY_RESULT[54];
                C = INTENSITY_RESULT[49];
                D = INTENSITY_RESULT[42];
                E = INTENSITY_RESULT[48];
            } else if (a == 50) {
                A = INTENSITY_RESULT[50];
                B = INTENSITY_RESULT[50];
                C = INTENSITY_RESULT[50];
                D = INTENSITY_RESULT[43];
                E = INTENSITY_RESULT[49];
            } else if (a == 51) {
                A = INTENSITY_RESULT[52];
                B = INTENSITY_RESULT[51];
                C = INTENSITY_RESULT[51];
                D = INTENSITY_RESULT[46];
                E = INTENSITY_RESULT[51];
            } else if (a == 52) {
                A = INTENSITY_RESULT[53];
                B = INTENSITY_RESULT[52];
                C = INTENSITY_RESULT[52];
                D = INTENSITY_RESULT[47];
                E = INTENSITY_RESULT[51];
            } else if (a == 53) {
                A = INTENSITY_RESULT[54];
                B = INTENSITY_RESULT[53];
                C = INTENSITY_RESULT[53];
                D = INTENSITY_RESULT[48];
                E = INTENSITY_RESULT[52];
            } else if (a == 54) {
                A = INTENSITY_RESULT[54];
                B = INTENSITY_RESULT[54];
                C = INTENSITY_RESULT[54];
                D = INTENSITY_RESULT[49];
                E = INTENSITY_RESULT[53];
            }
        }
        else if (testEye.equals("Right")) {
            if (a == 1) {
                A = INTENSITY_RESULT[2];
                B = INTENSITY_RESULT[6];
                C = INTENSITY_RESULT[1];
                D = INTENSITY_RESULT[1];
                E = INTENSITY_RESULT[1];
            } else if (a == 2) {
                A = INTENSITY_RESULT[3];
                B = INTENSITY_RESULT[7];
                C = INTENSITY_RESULT[2];
                D = INTENSITY_RESULT[2];
                E = INTENSITY_RESULT[1];
            } else if (a == 3) {
                A = INTENSITY_RESULT[4];
                B = INTENSITY_RESULT[8];
                C = INTENSITY_RESULT[3];
                D = INTENSITY_RESULT[3];
                E = INTENSITY_RESULT[2];
            } else if (a == 4) {
                A = INTENSITY_RESULT[4];
                B = INTENSITY_RESULT[9];
                C = INTENSITY_RESULT[4];
                D = INTENSITY_RESULT[4];
                E = INTENSITY_RESULT[3];
            } else if (a == 5) {
                A = INTENSITY_RESULT[6];
                B = INTENSITY_RESULT[12];
                C = INTENSITY_RESULT[5];
                D = INTENSITY_RESULT[5];
                E = INTENSITY_RESULT[5];
            } else if (a == 6) {
                A = INTENSITY_RESULT[7];
                B = INTENSITY_RESULT[13];
                C = INTENSITY_RESULT[6];
                D = INTENSITY_RESULT[1];
                E = INTENSITY_RESULT[5];
            } else if (a == 7) {
                A = INTENSITY_RESULT[8];
                B = INTENSITY_RESULT[14];
                C = INTENSITY_RESULT[7];
                D = INTENSITY_RESULT[2];
                E = INTENSITY_RESULT[6];
            } else if (a == 8) {
                A = INTENSITY_RESULT[9];
                B = INTENSITY_RESULT[15];
                C = INTENSITY_RESULT[8];
                D = INTENSITY_RESULT[3];
                E = INTENSITY_RESULT[7];
            } else if (a == 9) {
                A = INTENSITY_RESULT[10];
                B = INTENSITY_RESULT[16];
                C = INTENSITY_RESULT[9];
                D = INTENSITY_RESULT[4];
                E = INTENSITY_RESULT[8];
            } else if (a == 10) {
                A = INTENSITY_RESULT[10];
                B = INTENSITY_RESULT[17];
                C = INTENSITY_RESULT[10];
                D = INTENSITY_RESULT[10];
                E = INTENSITY_RESULT[9];
            } else if (a == 11) {
                A = INTENSITY_RESULT[12];
                B = INTENSITY_RESULT[20];
                C = INTENSITY_RESULT[11];
                D = INTENSITY_RESULT[11];
                E = INTENSITY_RESULT[11];
            } else if (a == 12) {
                A = INTENSITY_RESULT[13];
                B = INTENSITY_RESULT[21];
                C = INTENSITY_RESULT[12];
                D = INTENSITY_RESULT[5];
                E = INTENSITY_RESULT[11];
            } else if (a == 13) {
                A = INTENSITY_RESULT[14];
                B = INTENSITY_RESULT[22];
                C = INTENSITY_RESULT[13];
                D = INTENSITY_RESULT[6];
                E = INTENSITY_RESULT[12];
            } else if (a == 14) {
                A = INTENSITY_RESULT[15];
                B = INTENSITY_RESULT[23];
                C = INTENSITY_RESULT[14];
                D = INTENSITY_RESULT[7];
                E = INTENSITY_RESULT[13];
            } else if (a == 15) {
                A = INTENSITY_RESULT[16];
                B = INTENSITY_RESULT[24];
                C = INTENSITY_RESULT[15];
                D = INTENSITY_RESULT[8];
                E = INTENSITY_RESULT[14];
            } else if (a == 16) {
                A = INTENSITY_RESULT[17];
                B = INTENSITY_RESULT[25];
                C = INTENSITY_RESULT[16];
                D = INTENSITY_RESULT[9];
                E = INTENSITY_RESULT[15];
            } else if (a == 17) {
                A = INTENSITY_RESULT[18];
                B = INTENSITY_RESULT[26];
                C = INTENSITY_RESULT[17];
                D = INTENSITY_RESULT[10];
                E = INTENSITY_RESULT[16];
            } else if (a == 18) {
                A = INTENSITY_RESULT[18];
                B = INTENSITY_RESULT[27];
                C = INTENSITY_RESULT[18];
                D = INTENSITY_RESULT[18];
                E = INTENSITY_RESULT[17];
            } else if (a == 19) {
                A = INTENSITY_RESULT[20];
                B = INTENSITY_RESULT[28];
                C = INTENSITY_RESULT[19];
                D = INTENSITY_RESULT[19];
                E = INTENSITY_RESULT[19];
            } else if (a == 20) {
                A = INTENSITY_RESULT[21];
                B = INTENSITY_RESULT[29];
                C = INTENSITY_RESULT[20];
                D = INTENSITY_RESULT[11];
                E = INTENSITY_RESULT[19];
            } else if (a == 21) {
                A = INTENSITY_RESULT[22];
                B = INTENSITY_RESULT[30];
                C = INTENSITY_RESULT[21];
                D = INTENSITY_RESULT[12];
                E = INTENSITY_RESULT[20];
            } else if (a == 22) {
                A = INTENSITY_RESULT[23];
                B = INTENSITY_RESULT[31];
                C = INTENSITY_RESULT[22];
                D = INTENSITY_RESULT[13];
                E = INTENSITY_RESULT[21];
            } else if (a == 23) {
                A = INTENSITY_RESULT[24];
                B = INTENSITY_RESULT[32];
                C = INTENSITY_RESULT[23];
                D = INTENSITY_RESULT[14];
                E = INTENSITY_RESULT[22];
            } else if (a == 24) {
                A = INTENSITY_RESULT[25];
                B = INTENSITY_RESULT[33];
                C = INTENSITY_RESULT[24];
                D = INTENSITY_RESULT[15];
                E = INTENSITY_RESULT[23];
            } else if (a == 25) {
                A = INTENSITY_RESULT[26];
                B = INTENSITY_RESULT[34];
                C = INTENSITY_RESULT[25];
                D = INTENSITY_RESULT[16];
                E = INTENSITY_RESULT[24];
            } else if (a == 26) {
                A = INTENSITY_RESULT[27];
                B = INTENSITY_RESULT[35];
                C = INTENSITY_RESULT[26];
                D = INTENSITY_RESULT[17];
                E = INTENSITY_RESULT[25];
            } else if (a == 27) {
                A = INTENSITY_RESULT[27];
                B = INTENSITY_RESULT[36];
                C = INTENSITY_RESULT[27];
                D = INTENSITY_RESULT[18];
                E = INTENSITY_RESULT[26];
            } else if (a == 28) {
                A = INTENSITY_RESULT[29];
                B = INTENSITY_RESULT[28];
                C = INTENSITY_RESULT[28];
                D = INTENSITY_RESULT[19];
                E = INTENSITY_RESULT[28];
            } else if (a == 29) {
                A = INTENSITY_RESULT[30];
                B = INTENSITY_RESULT[37];
                C = INTENSITY_RESULT[29];
                D = INTENSITY_RESULT[20];
                E = INTENSITY_RESULT[28];
            } else if (a == 30) {
                A = INTENSITY_RESULT[31];
                B = INTENSITY_RESULT[38];
                C = INTENSITY_RESULT[30];
                D = INTENSITY_RESULT[21];
                E = INTENSITY_RESULT[29];
            } else if (a == 31) {
                A = INTENSITY_RESULT[32];
                B = INTENSITY_RESULT[39];
                C = INTENSITY_RESULT[31];
                D = INTENSITY_RESULT[22];
                E = INTENSITY_RESULT[30];
            } else if (a == 32) {
                A = INTENSITY_RESULT[33];
                B = INTENSITY_RESULT[40];
                C = INTENSITY_RESULT[32];
                D = INTENSITY_RESULT[23];
                E = INTENSITY_RESULT[31];
            } else if (a == 33) {
                A = INTENSITY_RESULT[34];
                B = INTENSITY_RESULT[41];
                C = INTENSITY_RESULT[33];
                D = INTENSITY_RESULT[24];
                E = INTENSITY_RESULT[32];
            } else if (a == 34) {
                A = INTENSITY_RESULT[35];
                B = INTENSITY_RESULT[42];
                C = INTENSITY_RESULT[34];
                D = INTENSITY_RESULT[25];
                E = INTENSITY_RESULT[33];
            } else if (a == 35) {
                A = INTENSITY_RESULT[36];
                B = INTENSITY_RESULT[43];
                C = INTENSITY_RESULT[35];
                D = INTENSITY_RESULT[26];
                E = INTENSITY_RESULT[34];
            } else if (a == 36) {
                A = INTENSITY_RESULT[36];
                B = INTENSITY_RESULT[44];
                C = INTENSITY_RESULT[36];
                D = INTENSITY_RESULT[27];
                E = INTENSITY_RESULT[35];
            } else if (a == 37) {
                A = INTENSITY_RESULT[38];
                B = INTENSITY_RESULT[37];
                C = INTENSITY_RESULT[37];
                D = INTENSITY_RESULT[29];
                E = INTENSITY_RESULT[37];
            } else if (a == 38) {
                A = INTENSITY_RESULT[39];
                B = INTENSITY_RESULT[45];
                C = INTENSITY_RESULT[38];
                D = INTENSITY_RESULT[30];
                E = INTENSITY_RESULT[37];
            } else if (a == 39) {
                A = INTENSITY_RESULT[40];
                B = INTENSITY_RESULT[46];
                C = INTENSITY_RESULT[39];
                D = INTENSITY_RESULT[31];
                E = INTENSITY_RESULT[38];
            } else if (a == 40) {
                A = INTENSITY_RESULT[41];
                B = INTENSITY_RESULT[47];
                C = INTENSITY_RESULT[40];
                D = INTENSITY_RESULT[32];
                E = INTENSITY_RESULT[39];
            } else if (a == 41) {
                A = INTENSITY_RESULT[42];
                B = INTENSITY_RESULT[48];
                C = INTENSITY_RESULT[41];
                D = INTENSITY_RESULT[33];
                E = INTENSITY_RESULT[40];
            } else if (a == 42) {
                A = INTENSITY_RESULT[43];
                B = INTENSITY_RESULT[49];
                C = INTENSITY_RESULT[42];
                D = INTENSITY_RESULT[34];
                E = INTENSITY_RESULT[41];
            } else if (a == 43) {
                A = INTENSITY_RESULT[44];
                B = INTENSITY_RESULT[50];
                C = INTENSITY_RESULT[43];
                D = INTENSITY_RESULT[35];
                E = INTENSITY_RESULT[42];
            } else if (a == 44) {
                A = INTENSITY_RESULT[10];
                B = INTENSITY_RESULT[11];
                C = INTENSITY_RESULT[44];
                D = INTENSITY_RESULT[36];
                E = INTENSITY_RESULT[43];
            } else if (a == 45) {
                A = INTENSITY_RESULT[46];
                B = INTENSITY_RESULT[45];
                C = INTENSITY_RESULT[45];
                D = INTENSITY_RESULT[38];
                E = INTENSITY_RESULT[45];
            } else if (a == 46) {
                A = INTENSITY_RESULT[47];
                B = INTENSITY_RESULT[51];
                C = INTENSITY_RESULT[46];
                D = INTENSITY_RESULT[39];
                E = INTENSITY_RESULT[45];
            } else if (a == 47) {
                A = INTENSITY_RESULT[48];
                B = INTENSITY_RESULT[52];
                C = INTENSITY_RESULT[47];
                D = INTENSITY_RESULT[40];
                E = INTENSITY_RESULT[46];
            } else if (a == 48) {
                A = INTENSITY_RESULT[49];
                B = INTENSITY_RESULT[53];
                C = INTENSITY_RESULT[48];
                D = INTENSITY_RESULT[41];
                E = INTENSITY_RESULT[47];
            } else if (a == 49) {
                A = INTENSITY_RESULT[50];
                B = INTENSITY_RESULT[54];
                C = INTENSITY_RESULT[49];
                D = INTENSITY_RESULT[42];
                E = INTENSITY_RESULT[48];
            } else if (a == 50) {
                A = INTENSITY_RESULT[50];
                B = INTENSITY_RESULT[50];
                C = INTENSITY_RESULT[50];
                D = INTENSITY_RESULT[43];
                E = INTENSITY_RESULT[49];
            } else if (a == 51) {
                A = INTENSITY_RESULT[52];
                B = INTENSITY_RESULT[51];
                C = INTENSITY_RESULT[51];
                D = INTENSITY_RESULT[46];
                E = INTENSITY_RESULT[51];
            } else if (a == 52) {
                A = INTENSITY_RESULT[53];
                B = INTENSITY_RESULT[52];
                C = INTENSITY_RESULT[52];
                D = INTENSITY_RESULT[47];
                E = INTENSITY_RESULT[51];
            } else if (a == 53) {
                A = INTENSITY_RESULT[54];
                B = INTENSITY_RESULT[53];
                C = INTENSITY_RESULT[53];
                D = INTENSITY_RESULT[48];
                E = INTENSITY_RESULT[52];
            } else if (a == 54) {
                A = INTENSITY_RESULT[54];
                B = INTENSITY_RESULT[13];
                C = INTENSITY_RESULT[54];
                D = INTENSITY_RESULT[49];
                E = INTENSITY_RESULT[53];
            }
        }
    }
    public void ABC_Small(int i) {

        if (testEye.equals("Left")) {
            if (i == 0) {
                small_rec_vals[3] = (getAverageContrast(bottom_right[4], top_right[9], top_left[10]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[3], top_left[10], third_top_left[10]));
                small_rec_vals[1] = (getAverageContrast(second_bottom_right[4], bottom_right[4], small_rec_vals[3]));
                small_rec_vals[2] = (getAverageContrast(small_rec_vals[1], small_rec_vals[3], small_rec_vals[4]));
            } else if (i == 1) {
                small_rec_vals[3] = (getAverageContrast(bottom_right[10], top_right[17], top_left[18]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[3], top_left[18], third_top_left[18]));
                small_rec_vals[1] = (getAverageContrast(second_bottom_right[10], bottom_right[10], small_rec_vals[3]));
                small_rec_vals[2] = (getAverageContrast(small_rec_vals[1], small_rec_vals[3], small_rec_vals[4]));
            } else if (i == 2) {
                small_rec_vals[3] = (getAverageContrast(bottom_right[18], top_right[27], top_left[19]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[3], top_left[19], third_top_left[19]));
                small_rec_vals[1] = (getAverageContrast(second_bottom_right[18], bottom_right[18], small_rec_vals[3]));
                small_rec_vals[2] = (getAverageContrast(small_rec_vals[1], small_rec_vals[3], small_rec_vals[4]));
            } else if (i == 3) {
                small_rec_vals[1] = (getAverageContrast(bottom_left[28], bottom_right[36], top_right[44]));
                small_rec_vals[2] = (getAverageContrast(small_rec_vals[1], third_bottom_left[28], bottom_left[28]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[1], second_top_right[44], top_right[44]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[1], small_rec_vals[2], small_rec_vals[3]));
            } else if (i == 4) {
                small_rec_vals[1] = (getAverageContrast(bottom_left[44], bottom_right[43], top_right[50]));
                small_rec_vals[2] = (getAverageContrast(third_bottom_left[44], bottom_left[44], small_rec_vals[1]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[1], second_top_right[50], top_right[50]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[3], small_rec_vals[1], small_rec_vals[2]));
            } else if (i == 5) {
                small_rec_vals[1] = (getAverageContrast(bottom_left[50], bottom_right[49], top_right[50]));
                small_rec_vals[2] = (getAverageContrast(third_bottom_left[50], bottom_left[50], small_rec_vals[1]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[1], second_top_right[54], top_right[54]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[3], small_rec_vals[1], small_rec_vals[2]));
            } else if (i == 6) {
                small_rec_vals[2] = (getAverageContrast(bottom_right[45], bottom_left[46], top_left[51]));
                small_rec_vals[1] = (getAverageContrast(third_bottom_right[45], bottom_right[45], small_rec_vals[2]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[1], small_rec_vals[2], small_rec_vals[4]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[2], top_left[51], second_top_left[51]));

            } else if (i == 7) {
                small_rec_vals[2] = (getAverageContrast(bottom_right[37], bottom_left[38], top_left[45]));
                small_rec_vals[1] = (getAverageContrast(third_bottom_right[37], bottom_right[37], small_rec_vals[2]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[1], small_rec_vals[2], small_rec_vals[4]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[2], top_left[45], second_top_left[45]));
            } else if (i == 8) {
                small_rec_vals[4] = (getAverageContrast(bottom_left[5], top_left[12], top_right[11]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[4], third_top_right[11], top_right[11]));
                small_rec_vals[2] = (getAverageContrast(small_rec_vals[4], second_bottom_left[5], bottom_left[5]));
                small_rec_vals[1] = (getAverageContrast(small_rec_vals[2], small_rec_vals[3], small_rec_vals[4]));

            } else if (i == 9) {
                small_rec_vals[4] = (getAverageContrast(bottom_left[1], top_left[6], top_right[5]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[4], third_top_right[5], top_right[5]));
                small_rec_vals[2] = (getAverageContrast(small_rec_vals[4], second_bottom_left[1], bottom_left[1]));
                small_rec_vals[1] = (getAverageContrast(small_rec_vals[2], small_rec_vals[3], small_rec_vals[4]));

            }
        } else if (testEye.equals("Right")) {
            if (i == 0) {
                small_rec_vals[3] = (getAverageContrast(bottom_right[4], top_right[9], top_left[10]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[3], top_left[10], third_top_left[10]));
                small_rec_vals[1] = (getAverageContrast(second_bottom_right[4], bottom_right[4], small_rec_vals[3]));
                small_rec_vals[2] = (getAverageContrast(small_rec_vals[1], small_rec_vals[3], small_rec_vals[4]));
            } else if (i == 1) {
                small_rec_vals[3] = (getAverageContrast(bottom_right[10], top_right[17], top_left[18]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[3], top_left[18], third_top_left[18]));
                small_rec_vals[1] = (getAverageContrast(second_bottom_right[10], bottom_right[10], small_rec_vals[3]));
                small_rec_vals[2] = (getAverageContrast(small_rec_vals[1], small_rec_vals[3], small_rec_vals[4]));
            } else if (i == 6) {
                small_rec_vals[2] = (getAverageContrast(bottom_right[28], bottom_left[29], top_left[37]));
                small_rec_vals[1] = (getAverageContrast(third_bottom_right[28], bottom_right[28], small_rec_vals[2]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[1], small_rec_vals[2], small_rec_vals[4]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[2], top_left[37], second_top_left[37]));
            } else if (i == 7) {
                small_rec_vals[4] = (getAverageContrast(bottom_left[11], top_left[20], top_right[19]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[4], third_top_right[19], top_right[19]));
                small_rec_vals[2] = (getAverageContrast(small_rec_vals[4], second_bottom_left[11], bottom_left[11]));
                small_rec_vals[1] = (getAverageContrast(small_rec_vals[2], small_rec_vals[3], small_rec_vals[4]));

            } else if (i == 2) {
                small_rec_vals[1] = (getAverageContrast(bottom_left[44], bottom_right[43], top_right[50]));
                small_rec_vals[2] = (getAverageContrast(third_bottom_left[44], bottom_left[44], small_rec_vals[1]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[1], second_top_right[50], top_right[50]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[3], small_rec_vals[1], small_rec_vals[2]));
            } else if (i == 3) {
                small_rec_vals[1] = (getAverageContrast(bottom_left[50], bottom_right[49], top_right[50]));
                small_rec_vals[2] = (getAverageContrast(third_bottom_left[50], bottom_left[50], small_rec_vals[1]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[1], second_top_right[54], top_right[54]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[3], small_rec_vals[1], small_rec_vals[2]));
            } else if (i == 4) {
                small_rec_vals[2] = (getAverageContrast(bottom_right[45], bottom_left[46], top_left[51]));
                small_rec_vals[1] = (getAverageContrast(third_bottom_right[45], bottom_right[45], small_rec_vals[2]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[1], small_rec_vals[2], small_rec_vals[4]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[2], top_left[51], second_top_left[51]));

            } else if (i == 5) {
                small_rec_vals[2] = (getAverageContrast(bottom_right[37], bottom_left[38], top_left[45]));
                small_rec_vals[1] = (getAverageContrast(third_bottom_right[37], bottom_right[37], small_rec_vals[2]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[1], small_rec_vals[2], small_rec_vals[4]));
                small_rec_vals[4] = (getAverageContrast(small_rec_vals[2], top_left[45], second_top_left[45]));
            } else if (i == 8) {
                small_rec_vals[4] = (getAverageContrast(bottom_left[5], top_left[12], top_right[11]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[4], third_top_right[11], top_right[11]));
                small_rec_vals[2] = (getAverageContrast(small_rec_vals[4], second_bottom_left[5], bottom_left[5]));
                small_rec_vals[1] = (getAverageContrast(small_rec_vals[2], small_rec_vals[3], small_rec_vals[4]));

            } else if (i == 9) {
                small_rec_vals[4] = (getAverageContrast(bottom_left[1], top_left[6], top_right[5]));
                small_rec_vals[3] = (getAverageContrast(small_rec_vals[4], third_top_right[5], top_right[5]));
                small_rec_vals[2] = (getAverageContrast(small_rec_vals[4], second_bottom_left[1], bottom_left[1]));
                small_rec_vals[1] = (getAverageContrast(small_rec_vals[2], small_rec_vals[3], small_rec_vals[4]));
            }
        }
    }

    private void draw_dots_small_rectangle ( int x1, int y1, int num){
        ABC_Small(num);
        for (int i = 0; i < 22; i = i + 1) {
            for (int j = 0; j < 22; j = j + 1) {
                paint.setColor(TemporaryVariables.getColorCode((int)small_rec_vals[1]));
                canvas.drawCircle(x1 + i, y1 + j, dot_width - 2, paint);
            }

            for (int j = 22; j < 44; j = j + 1) {
                paint.setColor(TemporaryVariables.getColorCode((int)small_rec_vals[3]));
                canvas.drawCircle(x1 + i, y1 + j, dot_width - 2, paint);
            }
        }
        for (int i = 22; i < 44; i = i + 1) {
            for (int j = 0; j < 22; j = j + 1) {
                paint.setColor(TemporaryVariables.getColorCode((int)small_rec_vals[2]));
                canvas.drawCircle(x1 + i, y1 + j, dot_width - 2, paint);
            }
            for (int j = 22; j < 44; j = j + 1) {
                paint.setColor(TemporaryVariables.getColorCode((int)small_rec_vals[4]));
                canvas.drawCircle(x1 + i, y1 + j, dot_width - 2, paint);
            }
        }
    }

    private void draw_dots_rectangle(int x, int y, int intensity, int point) {
        int i = 0, coll;
        double cval = 0;
        Paint p = new Paint();
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setTextSize(18);
        p.setColor(Color.WHITE);

        for (i = 0; i < 22; i = i + 1) {
            for (int j = 0; j < 22; j = j + 1) {
                cval = (68 * C + 11 * D + 11 * E) / 90.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                top_left[point] = (int) cval;
            }
            for (int j = 22; j < 44; j = j + 1) {
                cval = (22 * C + 7 * E + D) / 30.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                second_top_left[point] = (int) cval;
            }
            for (int j = 44; j < 66; j = j + 1) {
                cval = (3 * C + 2 * E) / 5.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                middle_left[point] = (int) cval;
            }
            for (int j = 66; j < 88; j = j + 1) {
                cval = (22 * C + 7 * E + B) / 30.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                second_bottom_left[point] = (int) cval;
            }
            for (int j = 88; j < 110; j = j + 1) {
                cval = (68 * C + 11 * B + 11 * E) / 90.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                bottom_left[point] = (int) cval;
            }

        }

        for (i = 22; i < 44; i = i + 1) {
            for (int j = 0; j < 22; j = j + 1) {
                cval = (22 * C + 7 * D + E) / 30.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                third_top_left[point] = (int) cval;
            }
            for (int j = 22; j < 44; j = j + 1) {
                cval = (8 * C + E + D) / 10.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);

            }

            for (int j = 44; j < 66; j = j + 1) {
                cval = (4 * C + E) / 5.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
            }

            for (int j = 66; j < 88; j = j + 1) {
                cval = (8 * C + E + B) / 10.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
            }

            for (int j = 88; j < 110; j = j + 1) {
                cval = (22 * C + 7 * B + E) / 30.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                third_bottom_left[point] = (int) cval;
            }
        }

        for (i = 44; i < 66; i = i + 1) {
            for (int j = 0; j < 22; j = j + 1) {
                cval = (3 * C + 2 * D) / 5.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                middle_top[point] = (int) cval;
            }
            for (int j = 22; j < 44; j = j + 1) {
                cval = (4 * C + D) / 5.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
            }
            for (int j = 44; j < 66; j = j + 1) {
                cval = C * 1.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);

                if (cval>=21)
                    p.setColor(Color.BLACK);
            }
            canvas.drawText(Integer.toString(intensity), x + 35, y + 55, p);

            for (int j = 66; j < 88; j = j + 1) {
                cval = (4 * C + B) / 5.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
            }
            for (int j = 88; j < 110; j = j + 1) {
                cval = (3 * C + 2 * B) / 5.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                middle_bottom[point]= (int) cval;
            }
        }


        for (i = 66; i < 88; i = i + 1) {
            for (int j = 0; j < 22; j = j + 1) {
                cval = (22 * C + 7 * D + A) / 30.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                third_top_right[point]= (int) cval;
            }
            for (int j = 22; j < 44; j = j + 1) {
                cval = (8 * C + A + D) / 10.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
            }
            for (int j = 44; j < 66; j = j + 1) {
                cval = (A + 4 * C) / 5.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
            }
            for (int j = 66; j < 88; j = j + 1) {
                cval = (8 * C + A + B) / 10.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
            }
            for (int j = 88; j < 110; j = j + 1) {
                cval = (22 * C + 7 * B + A) / 30.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                third_bottom_right[point]= (int) cval;
            }
        }


        for (i = 88; i < 110; i = i + 1) {
            int j = 0;
            for (j = 0; j < 22; j = j + 1) {
                cval = (68 * C + 11 * A + 11 * D) / 90.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                top_right[point] = (int) cval;
            }
            for (j = 22; j < 44; j = j + 1) {
                cval = (22 * C + 7 * A + D) / 30.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                second_top_right[point]= (int) cval;
            }
            for (j = 44; j < 66; j = j + 1) {
                cval = (2 * A + 3 * C) / 5.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                middle_right[point]= (int) cval;
            }
            for (j = 66; j < 88; j = j + 1) {
                cval = (22 * C + 7 * A + B) / 30.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                second_bottom_right[point]= (int) cval;
            }
            for (j = 88; j < 110; j = j + 1) {
                cval = (68 * C + 11 * A + 11 * B) / 90.0;
                coll = TemporaryVariables.getColorCode(Math.round(cval));
                paint.setColor(coll);
                canvas.drawCircle(x + i, y + j, dot_width - 2, paint);
                bottom_right[point]= (int) cval;
            }
        }

    }
    public  void drawtotalDeviationPattern() {

        Paint stdpaint = new Paint();
        stdpaint.setColor(Color.BLACK);
        stdpaint.setStyle(Paint.Style.FILL);
        stdpaint.setTextSize(45);
        stdpaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        stdpaint.setStrokeWidth(5);

        totalDeviationBmp1 = Bitmap.createBitmap(1200,1200, Bitmap.Config.ARGB_8888);
        totalDeviationBmp2 = Bitmap.createBitmap(1200,1200, Bitmap.Config.ARGB_8888);
        Canvas stdcanvas=new Canvas(totalDeviationBmp1);
        Canvas stdcanvas2=new Canvas(totalDeviationBmp2);

        int newwidth = 4;
        float newleft = 600 - newwidth / 2;
        float newtop = 0;
        float newright = newleft + newwidth;
        float newbottom = 1200;

        stdcanvas.drawRect(newleft, newtop, newright, newbottom, stdpaint);
        stdcanvas2.drawRect(newleft, newtop, newright, newbottom, stdpaint);

        newleft = 0;
        newtop = 600 - newwidth / 2;
        newright = 1200;
        newbottom =newtop + newwidth;

        stdcanvas.drawRect(newleft, newtop, newright, newbottom, stdpaint);
        stdcanvas2.drawRect(newleft, newtop, newright, newbottom, stdpaint);

        // NORMATIVE DATA


        _24_2_NormativeData data = new _24_2_NormativeData();
        NORMAL_INTENSITY = data.getNormativeData(Integer.parseInt(age), testEye);

        for(int i=1;i<55;i++) {
            TOTAL_DEVIATION[i] = INTENSITY_RESULT[i] - NORMAL_INTENSITY[i];

            if (testEye.equals("Left")) {
                if (i == 21 || i == 30)
                    continue;
            } else {
                if (i == 26 || i == 35)
                    continue;
            }
            int x = coordinate24_2.getCoordinates(i, String.valueOf(testEye))[0] + 600 - 34;
            int y = coordinate24_2.getCoordinates(i, String.valueOf(testEye))[1] + 600;
            paint.setTextSize(45);
            paint.setColor(Color.BLACK);
            if (TOTAL_DEVIATION[i] >= 0) {
                stdcanvas.drawText("  " + String.valueOf(TOTAL_DEVIATION[i]), x, y, paint);
            } else
                stdcanvas.drawText(String.valueOf(TOTAL_DEVIATION[i]), x, y, paint);


            x = coordinate24_2.getCoordinates(i, String.valueOf(testEye))[0] + 600;
            y = coordinate24_2.getCoordinates(i, String.valueOf(testEye))[1] + 600;


            //FIRST CIRCLE

            if (i == 23 || i == 24 || i == 32 || i == 33) {

                if (TOTAL_DEVIATION[i] >= -3) {
                    stdcanvas2.drawCircle(x, y, 2, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -4) {
                    draw_1_perc(stdcanvas2, x, y, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -5) {
                    draw_2_perc(stdcanvas2, x, y, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -6) {
                    draw_5_perc(stdcanvas2, x, y, stdpaint);
                } else {
                    draw_0_5_perc(stdcanvas2, x, y, stdpaint);
                }
            }

            // Second Circle

            else if (i == 13 || i == 14 || i == 15 || i == 16 || i == 22 || i == 25 || i == 31 || i == 34 || i == 39 || i == 40 || i == 41 || i == 42) {

                if (TOTAL_DEVIATION[i] >= -4) {
                    stdcanvas2.drawCircle(x, y, 2, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -5) {
                    draw_1_perc(stdcanvas2, x, y, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -6) {
                    draw_2_perc(stdcanvas2, x, y, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -7) {
                    draw_5_perc(stdcanvas2, x, y, stdpaint);
                } else {
                    draw_0_5_perc(stdcanvas2, x, y, stdpaint);
                }
            }

            // Third Circle

            else if (i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 10 || i == 12 || i == 17 || i == 21 || i == 26 || i == 30 || i == 35 || i == 38 || i == 43 || i == 45 || i == 46 || i == 47 || i == 48 || i == 49 || i == 50) {

                if (TOTAL_DEVIATION[i] >= -4) {
                    stdcanvas2.drawCircle(x, y, 2, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -5) {
                    draw_1_perc(stdcanvas2, x, y, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -6) {
                    draw_2_perc(stdcanvas2, x, y, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -7) {
                    draw_5_perc(stdcanvas2, x, y, stdpaint);
                } else {
                    draw_0_5_perc(stdcanvas2, x, y, stdpaint);
                }

            }

            //Fourth Circle

            else if (i == 1 || i == 2 || i == 3 || i == 4 || i == 11 || i == 18 || i == 20 || i == 27 || i == 29 || i == 36 || i == 37 || i == 44 || i == 51 || i == 52 || i == 53 || i == 54) {

                if (TOTAL_DEVIATION[i] >= -5) {
                    stdcanvas2.drawCircle(x, y, 2, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -6) {
                    draw_1_perc(stdcanvas2, x, y, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -7) {
                    draw_2_perc(stdcanvas2, x, y, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -8) {
                    draw_5_perc(stdcanvas2, x, y, stdpaint);
                } else {
                    draw_0_5_perc(stdcanvas2, x, y, stdpaint);
                }
            }

            //FIFTH
            else {

                if (TOTAL_DEVIATION[i] >= -5) {
                    stdcanvas2.drawCircle(x, y, 2, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -6) {
                    draw_1_perc(stdcanvas2, x, y, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -7) {
                    draw_2_perc(stdcanvas2, x, y, stdpaint);
                } else if (TOTAL_DEVIATION[i] == -8) {
                    draw_5_perc(stdcanvas2, x, y, stdpaint);
                } else {
                    draw_0_5_perc(stdcanvas2, x, y, stdpaint);
                }
            }
        }
        String totaldeviationString = Arrays.toString(TOTAL_DEVIATION);

        for(int i=1;i<55;i++){
            int md=0;
            if (testEye.equals("Left")) {
                if (i != 21 && i != 30)
                    md = md + TOTAL_DEVIATION[i];
            } else {
                if (i != 26 && i != 35)
                    md = md + TOTAL_DEVIATION[i];
            }
        }
    }
    public  void drawPatternDeviationPattern() {

        Paint stdpaint = new Paint();
        stdpaint.setColor(Color.BLACK);
        stdpaint.setStyle(Paint.Style.FILL);
        stdpaint.setTextSize(45);
        stdpaint.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        stdpaint.setStrokeWidth(5);

        patternDeviationBmp1 = Bitmap.createBitmap(1200,1200, Bitmap.Config.ARGB_8888);
        patternDeviationBmp2 = Bitmap.createBitmap(1200,1200, Bitmap.Config.ARGB_8888);
        Canvas stdcanvas=new Canvas(patternDeviationBmp1);
        Canvas stdcanvas2=new Canvas(patternDeviationBmp2);

        int newwidth = 4;
        float newleft = 600 - newwidth / 2;
        float newtop = 0;
        float newright = newleft + newwidth;
        float newbottom = 1200;

        stdcanvas.drawRect(newleft, newtop, newright, newbottom, stdpaint);
        stdcanvas2.drawRect(newleft, newtop, newright, newbottom, stdpaint);
        newleft = 0;
        newtop = 600 - newwidth / 2;
        newright = 1200;
        newbottom =newtop + newwidth;
        stdcanvas.drawRect(newleft, newtop, newright, newbottom, stdpaint);
        stdcanvas2.drawRect(newleft, newtop, newright, newbottom, stdpaint);

        for (int i=1;i<=54;i++){
            if (testEye.equals("Left")){
                if(i!=21 && i!=30)
                    SORTED_ARRAY[i]=TOTAL_DEVIATION[i];             //NORMAL ARRAY
            }
            else{
                if (i!=26 && i!=35)
                    SORTED_ARRAY[i]=TOTAL_DEVIATION[i];             //NORMAL ARRAY
            }
        }

        for(int i=1; i<=54; i++)                                     //SORTED ARRAY
        {
            for(int j=i + 1; j<=54; j++)
            {
                if(SORTED_ARRAY[i] < SORTED_ARRAY[j])
                {
                    int temp = SORTED_ARRAY[i];
                    SORTED_ARRAY[i] = SORTED_ARRAY[j];
                    SORTED_ARRAY[j] = temp;
                }
            }
        }


        for (int i = 1; i <= 54; i++) {
            if (testEye.equals("Left")) {
                if (i != 21 && i != 30)
                    PATTERN_DEVIATION[i] = TOTAL_DEVIATION[i] + Math.abs(SORTED_ARRAY[7]);             //NORMAL ARRAY
                else
                    PATTERN_DEVIATION[i] = 0;
            } else {
                if (i != 26 && i != 35)
                    PATTERN_DEVIATION[i] = TOTAL_DEVIATION[i] + Math.abs(SORTED_ARRAY[7]);             //NORMAL ARRAY
                else
                    PATTERN_DEVIATION[i] = 0;
            }
        }
        psd = TemporaryVariables.calculatePSD(PATTERN_DEVIATION, 52);

        for(int i=1;i<55;i++) {
            if (testEye.equals("Left")) {
                if (i == 21 || i == 30)
                    continue;
            } else {
                if (i == 26 || i == 35)
                    continue;
            }
            int x = coordinate24_2.getCoordinates(i, String.valueOf(testEye))[0] + 600-34;
            int y = coordinate24_2.getCoordinates(i, String.valueOf(testEye))[1] + 600;

            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextSize(45);
            stdpaint.setStrokeWidth(5);

            if ( PATTERN_DEVIATION[i]>=0) {
                stdcanvas.drawText("  "+ PATTERN_DEVIATION[i], x, y, paint);
            }
            else
                stdcanvas.drawText(String.valueOf(PATTERN_DEVIATION[i]), x, y, paint);


            x = coordinate24_2.getCoordinates(i, String.valueOf(testEye))[0] + 600;
            y = coordinate24_2.getCoordinates(i, String.valueOf(testEye))[1] + 600;

            //FIRST CIRCLE

            if (i==23 || i==24 || i==32 || i==33){

                if (PATTERN_DEVIATION[i]>=-3) {
                    stdcanvas2.drawCircle(x, y, 2, stdpaint);
                }

                else if (PATTERN_DEVIATION[i]==-4) {
                    draw_1_perc(stdcanvas2, x,y,stdpaint);
                }

                else if (PATTERN_DEVIATION[i]==-5){
                    draw_2_perc(stdcanvas2, x,y,stdpaint);
                }

                else if (PATTERN_DEVIATION[i]==-6) {
                    draw_5_perc(stdcanvas2, x,y,stdpaint);
                }

                else {
                    draw_0_5_perc(stdcanvas2, x,y,stdpaint);
                }
            }

            // Second Circle

            else if (i==13 || i==14 || i==15 || i==16 || i==22 || i==25 || i==31 || i==34 || i==39 || i==40 || i==41 || i==42){

                if (PATTERN_DEVIATION[i]>=-4) {
                    stdcanvas2.drawCircle(x, y, 2, stdpaint);
                }
                else if (PATTERN_DEVIATION[i]==-5) {
                    draw_1_perc(stdcanvas2, x,y,stdpaint);
                }
                else if (PATTERN_DEVIATION[i]==-6){
                    draw_2_perc(stdcanvas2, x,y,stdpaint);
                }

                else if (PATTERN_DEVIATION[i]==-7) {
                    draw_5_perc(stdcanvas2, x,y,stdpaint);
                }


                else {
                    draw_0_5_perc(stdcanvas2, x,y,stdpaint);
                }
            }

            // Third Circle

            else if (i==5 || i==6 || i==7 || i==8 || i==9 || i==10 || i==12 || i==17 || i==21 || i==26 || i==30 || i==35 || i==38 || i==43 || i==45 || i==46 || i==47 || i==48 || i==49 || i==50 ){

                if (PATTERN_DEVIATION[i]>=-4) {
                    stdcanvas2.drawCircle(x, y, 2, stdpaint);
                }
                else if (PATTERN_DEVIATION[i]==-5) {
                    draw_1_perc(stdcanvas2, x,y,stdpaint);
                }
                else if (PATTERN_DEVIATION[i]==-6){
                    draw_2_perc(stdcanvas2, x,y,stdpaint);
                }
                else if (PATTERN_DEVIATION[i]==-7) {
                    draw_5_perc(stdcanvas2, x,y,stdpaint);
                }
                else {
                    draw_0_5_perc(stdcanvas2, x,y,stdpaint);
                }

            }

            //Fourth Circle

            else if (i==1 || i==2 || i==3 || i==4 || i==11 || i==18 || i==20 || i==27 || i==29 || i==36 || i==37 || i==44 || i==51 || i==52 || i==53 || i==54){

                if (PATTERN_DEVIATION[i]>=-5) {
                    stdcanvas2.drawCircle(x, y, 2, stdpaint);
                }
                else if (PATTERN_DEVIATION[i]==-6) {
                    draw_1_perc(stdcanvas2, x,y,stdpaint);
                }
                else if (PATTERN_DEVIATION[i]==-7){
                    draw_2_perc(stdcanvas2, x,y,stdpaint);
                }
                else if (PATTERN_DEVIATION[i]==-8) {
                    draw_5_perc(stdcanvas2, x,y,stdpaint);
                }
                else {
                    draw_0_5_perc(stdcanvas2, x,y,stdpaint);
                }
            }

            //FIFTH
            else {

                if (PATTERN_DEVIATION[i]>=-5) {
                    stdcanvas2.drawCircle(x, y, 2, stdpaint);
                }
                else if (PATTERN_DEVIATION[i]==-6) {
                    draw_1_perc(stdcanvas2, x,y,stdpaint);
                }
                else if (PATTERN_DEVIATION[i]==-7){
                    draw_2_perc(stdcanvas2, x,y,stdpaint);
                }
                else if (PATTERN_DEVIATION[i]==-8) {
                    draw_5_perc(stdcanvas2, x,y,stdpaint);
                }
                else {
                    draw_0_5_perc(stdcanvas2, x,y,stdpaint);
                }
            }
        }
        StringBuilder builder2 = new StringBuilder();
        for (int i = 0; i < PATTERN_DEVIATION.length; i++) {
            builder2.append(PATTERN_DEVIATION[i]);
            if (i < PATTERN_DEVIATION.length - 1) {
                builder2.append(",");
            }
        }
        patternDeviation = builder2.toString();
        String patterndeviationString = Arrays.toString(PATTERN_DEVIATION);

    }


    public void draw_0_5_perc(Canvas stdcanvas2, int x, int y, Paint stdpaint){
        stdcanvas2.drawRect(x - 20, y - 20, x + 20, y + 20, stdpaint);
    }
    public void draw_5_perc(Canvas stdcanvas2, int x, int y, Paint stdpaint){

        stdcanvas2.drawCircle(x - 20, y - 20, 4, stdpaint);
        stdcanvas2.drawCircle(x + 20, y - 20, 4, stdpaint);
        stdcanvas2.drawCircle(x - 20, y + 20, 4, stdpaint);
        stdcanvas2.drawCircle(x + 20, y + 20, 4, stdpaint);
    }
    public void draw_2_perc(Canvas stdcanvas2, int x, int y, Paint stdpaint){

        stdcanvas2.drawCircle(x - 20, y - 20, 4, stdpaint);
        stdcanvas2.drawCircle(x + 20, y - 20, 4, stdpaint);
        stdcanvas2.drawCircle(x, y - 20, 4, stdpaint);

        stdcanvas2.drawCircle(x - 20, y, 4, stdpaint);
        stdcanvas2.drawCircle(x + 20, y, 4, stdpaint);

        stdcanvas2.drawCircle(x - 20, y + 20, 4, stdpaint);
        stdcanvas2.drawCircle(x + 20, y + 20, 4, stdpaint);
        stdcanvas2.drawCircle(x, y + 20, 4, stdpaint);

        stdcanvas2.drawLine(x, y - 20, x - 20, y, stdpaint);
        stdcanvas2.drawLine(x - 20, y, x, y + 20, stdpaint);
        stdcanvas2.drawLine(x, y + 20, x + 20, y, stdpaint);
        stdcanvas2.drawLine(x + 20, y, x, y - 20, stdpaint);
    }
    public void draw_1_perc(Canvas stdcanvas2, int x, int y, Paint stdpaint){

        stdcanvas2.drawCircle(x - 20, y - 20, 4, stdpaint);
        stdcanvas2.drawCircle(x + 20, y - 20, 4, stdpaint);
        stdcanvas2.drawCircle(x, y - 20, 4, stdpaint);

        stdcanvas2.drawCircle(x - 20, y, 4, stdpaint);
        stdcanvas2.drawCircle(x + 20, y, 4, stdpaint);
        stdcanvas2.drawCircle(x, y, 4, stdpaint);

        stdcanvas2.drawCircle(x - 20, y + 20, 4, stdpaint);
        stdcanvas2.drawCircle(x + 20, y + 20, 4, stdpaint);
        stdcanvas2.drawCircle(x, y + 20, 4, stdpaint);
    }
    private double getAverageContrast(double c1, double c2, double c3){
        double avg;
        avg =  (c1+c2+c3)/3.0;
        return avg;
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
    private boolean checkSettingsPermission() {
        // Check if WRITE_SETTINGS permission is granted
        return Settings.System.canWrite(this);
    }

    private void requestPermission() {
        int PERMISSION_CODE = 200;
        ActivityCompat.requestPermissions(this, new String[]{CAMERA, VIBRATE}, PERMISSION_CODE);
    }
    void askPermission() {
        if (!flagSetting) {
            final AlertDialog alertDialog = new AlertDialog.Builder(ScanQRCodeActivity.this).create();
            alertDialog.setTitle("Access Required !");
            alertDialog.setMessage("App Need Access To Settings and All Files. Please Give Access");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Request the WRITE_SETTINGS permission
                    Intent writeSettingsIntent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    writeSettingsIntent.setData(Uri.parse("package:" + getPackageName()));
                    startActivityForResult(writeSettingsIntent, WRITE_SETTINGS_REQUEST_CODE);

                    // Request the ALL_FILES_ACCESS_PERMISSION permission
                    Intent allFilesAccessIntent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    allFilesAccessIntent.setData(Uri.parse("package:" + getPackageName()));
                    startActivityForResult(allFilesAccessIntent, ALL_FILES_ACCESS_REQUEST_CODE);
                }
            });

            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
        }
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
//            if (cameraAccepted && vibrationAccepted) {
//                Toast.makeText(this, "Permission Granted...", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(this, "Permission Denied \n You can't use the app without permissions", Toast.LENGTH_SHORT).show();
//            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @SuppressLint("StaticFieldLeak")
    public class MyTask extends AsyncTask<Void, Void, Void> {
        public void onPreExecute() {

        }

        public Void doInBackground(Void... unused) {

            getMappedBitmap();                  // Colored mapping bitmap
            setIntensityPattern();              // Raw data Numbered bitmap
            drawtotalDeviationPattern();        // Total deviation calculation and bitmap
            drawPatternDeviationPattern();      // Pattern deviation calculation and bitmap


            Log.d("FAST", "REPORT2");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    final_report.setImageBitmap(bitmap_mapped);
//                }
//            });

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

//        String pdffilepath=savePdf();
//        if (pdffilepath != null) {
//            viewPdf(pdffilepath);
//        } else {
//            Toast.makeText(this, "Failed to generate PDF", Toast.LENGTH_SHORT).show();
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, REQUEST_MANAGE_EXTERNAL_STORAGE);
            } else {
                savePdf();
            }

            }
         else {
            savePdf();

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
        createImages(document, bitmap_mapped, 270, 420, 200, 210);

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
        createHeadings(cb, 30,  30, "iVA -Intelligent Vision Analyzer " + " | Software: " +software_version +" | Hardware: "+hardware_version, 9, "normal");
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
        pdfFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + format + "scannedData.pdf";
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
         createImages(document, totalDeviationBmp1, 30, 250, 150, 155);
         createHeadings(cb, 75, 235, "Total Deviation", 9, "normal");
         createImages(document, totalDeviationBmp2, 30, 80, 150, 155);
         createImages(document, patternDeviationBmp1, 210, 250, 150, 155);
         createHeadings(cb, 260, 235, "Pattern Deviation", 9, "normal");
         createImages(document, patternDeviationBmp2, 210, 80, 150, 155);
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
       viewPdf(file);

            return pdfFile;

    }


//    private void viewPdf(final String pdfFilePath) {
//        try {
//            File pdfFile = new File(pdfFilePath);
//
//            // Get URI for the file using FileProvider
//            final Uri uri = FileProvider.getUriForFile(this,
//                    getPackageName() + ".provider", pdfFile);
//
//            // Check if URI is not null
//            if (uri != null) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        intent.setDataAndType(uri, "application/pdf");
//                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                        Uri URI = FileProvider.getUriForFile(ScanQRCodeActivity.this, ScanQRCodeActivity.this.getApplicationContext().getPackageName() + ".provider", pdfFile);
//
//
//                        // Verify that there are apps available to open this intent
//                        List<ResolveInfo> resolvedActivities = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
//                        if (resolvedActivities.size() > 0) {
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(ScanQRCodeActivity.this, "No PDF viewer app installed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            } else {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(ScanQRCodeActivity.this, "Failed to get PDF URI", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        } catch (final Exception e) {
//            e.printStackTrace();
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(ScanQRCodeActivity.this, "Error opening PDF: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }

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
