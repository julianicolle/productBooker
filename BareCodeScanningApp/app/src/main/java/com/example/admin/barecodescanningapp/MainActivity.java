package com.example.admin.barecodescanningapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class MainActivity extends AppCompatActivity implements PostOnServerTask.PostOnServerTaskListener {

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private SurfaceView cameraView;
    private TextView barcodeInfo;
    private TextView dbInfo;
    private Boolean isResponseReceived = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        cameraView = (SurfaceView)findViewById(R.id.camera_view);
        barcodeInfo = (TextView)findViewById(R.id.textView);
        dbInfo =(TextView)findViewById(R.id.textView2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        barcodeDetector =
                new BarcodeDetector.Builder(this)
                        .setBarcodeFormats(Barcode.QR_CODE)
                        .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

                cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    try {
                        cameraSource.start(cameraView.getHolder());
                        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                            @Override
                            public void release() {
                            }

                            @Override
                            public void receiveDetections(Detector.Detections<Barcode> detections) {

                                if (isResponseReceived) {

                                    final SparseArray<Barcode> barcodes = detections.getDetectedItems();

                                    if (barcodes.size() != 0) {
                                        isResponseReceived = false;
                                        barcodeInfo.post(new Runnable() {    // Use the post method of the TextView
                                            public void run() {
                                                barcodeInfo.setText(    // Update the TextView
                                                        barcodes.valueAt(0).displayValue);

                                                PostOnServerTask task = new PostOnServerTask(MainActivity.this);
                                                JSONObject json = new JSONObject();

                                                try {
                                                    URI url = new URI(barcodes.valueAt(0).displayValue);
                                                    String urlToSend = url.toString();
                                                    //json.put("info", urlToSend.replace("\"", ""));
                                                    json.put("info", urlToSend);
                                                    task.execute(json.toString());
                                                } catch (URISyntaxException | JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                }

                            }
                        });
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    cameraSource.stop();
                }
            });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPostExecute(Boolean success, String result) {
        isResponseReceived = true;

        if (result.equals("true")) {
            Toast.makeText(MainActivity.this, "product in DB", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this,"product not in DB",Toast.LENGTH_SHORT).show();
        }

    }
}
