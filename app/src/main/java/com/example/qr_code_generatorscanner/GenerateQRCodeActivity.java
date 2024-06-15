package com.example.qr_code_generatorscanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateQRCodeActivity extends AppCompatActivity {

    private TextView qrCodeTextView;
    private ImageView qrCodeImageView;
    private TextInputEditText qrCodeNameInput, qrCodeAgeInput, qrCodeGenderInput, qrCodePhoneNumberInput;
    private Button qrCodeGeneratorButton, shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qrcode);

        qrCodeTextView = findViewById(R.id.frameText);
        qrCodeImageView = findViewById(R.id.QRCodeImg);
        qrCodeNameInput = findViewById(R.id.inputName);
        qrCodeAgeInput = findViewById(R.id.inputAge);
        qrCodeGenderInput = findViewById(R.id.inputGender);
        qrCodePhoneNumberInput = findViewById(R.id.inputPhoneNumber);
        qrCodeGeneratorButton = findViewById(R.id.QRCodeGeneratorBtn);
        shareButton = findViewById(R.id.shareBtn);

        qrCodeGeneratorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = qrCodeNameInput.getText().toString().trim();
                String age = qrCodeAgeInput.getText().toString().trim();
                String gender = qrCodeGenderInput.getText().toString().trim();
                String phoneNumber = qrCodePhoneNumberInput.getText().toString().trim();

                if (name.isEmpty() || age.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(GenerateQRCodeActivity.this, "Please fill all fields to generate QR Code", Toast.LENGTH_SHORT).show();
                } else {
                    String data = "Name: " + name + "\nAge: " + age + "\nGender: " + gender + "\nPhone Number: " + phoneNumber;
                    generateQRCode(data);
                }
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareQRCode();
            }
        });
    }

    private void generateQRCode(String data) {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE, 250, 250);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            qrCodeImageView.setImageBitmap(bitmap);

            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(qrCodeNameInput.getWindowToken(), 0);

            qrCodeTextView.setVisibility(View.GONE);

            // Save the QR code image for sharing
            saveQRCodeImage(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating QR Code", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveQRCodeImage(Bitmap bitmap) {
        File path = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "QRCode");
        if (!path.exists()) {
            path.mkdirs();
        }
        File file = new File(path, "qrcode.png");
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving QR Code image", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareQRCode() {
        File path = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "QRCode");
        File file = new File(path, "qrcode.png");
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(intent, "Share QR Code"));
    }
}
