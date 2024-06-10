package com.example.qr_code_generatorscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
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

public class GenerateQRCodeActivity extends AppCompatActivity {

    private TextView qrCodeTextView;
    private ImageView qrCodeImageView;
    private TextInputEditText qrCodeNameInput, qrCodeAgeInput, qrCodeGenderInput, qrCodePhoneNumberInput;
    private Button qrCodeGeneratorButton;

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
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating QR Code", Toast.LENGTH_SHORT).show();
        }
    }
}
