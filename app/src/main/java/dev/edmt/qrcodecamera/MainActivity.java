package dev.edmt.qrcodecamera;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView txtResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            break;
        }
    }

    Button FileChoose;
    private File currentDirectory;
    public Button btnFileChoose;
    public Button btnUploadData;
    public void FileChooseF(View view) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            int permissionCheck = this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1001); //Any number
            }
        } else {

        }

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        //  Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        //intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".

        //intent.setType("*/*");

        //startActivityForResult(Intent.createChooser(intent, "Выберите файл"), 1);


        btnUploadData.setVisibility(View.VISIBLE);
        btnFileChoose.setVisibility(View.GONE);
        txtResult.setText("Нажмите загрузить базу и ждите, база должна находиться в корневой папке и иметь название data.csv");

    }

    public void onActivityResult(int requestCode, int resultCode, Intent result) {


    }

    public String filepath1, Sfs;
    public Uri uri;
    public TextView one;
    public TextView two;
    public TextView three;
    public TextView four;
    public Scanner x;

    public void findNeeds() {


    }
    public void bntAll(View view){

    }
    Button btnSearchDeff;
    public void uploadData(View view){
        addToArray();

        btnSearchDeff.setVisibility(View.VISIBLE);
        btnUploadData.setVisibility(View.GONE);
        txtResult.setText("Отсканируйте штрихкод и нажмите Поиск");

    }
    public void searchDeff(View view){
        searchInArray((String) txtResult.getText());
    }
    public int i = 0;
    public String mail;
    public void searchInArray(String bardercode){
        count--;
        for (int i = 1; i<count; i++) {
            if (arr[i][0].replaceAll("\n", "").equals(bardercode)){
                //
                one.setText((arr[i][0]));
                two.setText(arr[i][1]);
                three.setText(arr[i][2]);
                four.setText(arr[i][3]);
                continue;
            }



        }
    }
    public String[][] arr = new String[80000][4];
    public void addToArray() {
        boolean found = false;
        int row, coll;
        row = 0;
        coll = 0;

        try {
            x = new Scanner(new File("/storage/emulated/0/data.csv"));
            while (!found & x.hasNext()) {
                count++;
                coll++;
                counts.setText("Кол-во: " + String.valueOf(count));
                arr[coll][0] = x.next();
                if (x.hasNext()) {
                    arr[coll][1] = (x.next().replaceAll("\n", ""));
                }
                if (x.hasNext()) {
                    arr[coll][2] = x.next();
                    x.useDelimiter("\n");
                }
                if (x.hasNext()) {
                    arr[coll][3] = x.next();
                }
                x.useDelimiter(";");
            }
        } catch (FileNotFoundException e) {
            one.setText("Файл не найден");
        }
        one.setText("Done");
    }

    public void readRecord(View view) {
        boolean found = false;
        String svoy1 = "", svoy2 = "", svoy3 = "", ID = "";
        try {

            x = new Scanner(new File("/storage/emulated/0/baze3.csv"));
            x.useDelimiter(";");
            Sfs = "";
            counts.setText("Кол-во: " + String.valueOf(count));
            while (!found & x.hasNext()) {
                count++;
                counts.setText("Кол-во: " + String.valueOf(count));
                ID = x.next();
                Sfs = Sfs + "|" + ID.replaceAll("\n", "");
                if (x.hasNext()) {
                    svoy1 = (x.next());
                }
                if (x.hasNext()) {
                    svoy2 = x.next();
                    x.useDelimiter("\n");
                }
                if (x.hasNext()) {
                    svoy3 = x.next();
                }
                x.useDelimiter(";");

                if (String.valueOf(searchTerm).equals(ID.replaceAll("\n", ""))) {
                    found = true;
                    one.setText(searchTerm);
                    two.setText(svoy1);
                    three.setText(svoy2);
                    four.setText(svoy3.replaceAll(";", ""));
                }
                if (!found) {
                    one.setText("Нет данных");
                    two.setText("");
                    three.setText("");
                    four.setText("");
                }

            }


        } catch (FileNotFoundException e) {
            one.setText("SlavaUkraine");
        }
        x.close();


    }

    public int count = 0;
    public TextView counts;
    public String code = "";
    public String fileName = "/sdcard/baza.txt";
    // public String fileName = "/storage/emulated/0/baza.csv";
    public String searchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent uploadDataintent = getIntent();
        //String[] Dataintent = uploadDataintent.getStringArrayExtra("strings");
        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        txtResult = (TextView) findViewById(R.id.txtResult);
        one = (TextView) findViewById(R.id.one);
        two = (TextView) findViewById(R.id.two);
        three = (TextView) findViewById(R.id.three);
        four = (TextView) findViewById(R.id.four);
        counts = (TextView) findViewById(R.id.count);
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1920, 1080)
                .build();
        btnFileChoose = (Button)findViewById(R.id.FileChoose);
        btnUploadData = (Button)findViewById(R.id.UploadData);
        btnSearchDeff = (Button)findViewById(R.id.SearchDeff);

        //Add Event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA}, RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();

            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if (qrcodes.size() != 0) {
                    txtResult.post(new Runnable() {
                        @Override
                        public void run() {
                            //Create vibrate
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            txtResult.setText(qrcodes.valueAt(0).displayValue);
                            searchTerm = (String) txtResult.getText();

                        }
                    });
                }
            }
        });
    }
}

