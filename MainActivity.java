package tr.com.m.basiccamera;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback, Camera.PreviewCallback {
    private static final int PERMISSION_REQUEST_CODE = 0x123;

    private SurfaceView mSurfaceView;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            //request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CODE);
        }

        mSurfaceView = findViewById(R.id.surfaceViewCamera);
        mSurfaceView.getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            mCamera = Camera.open();

            //mCamera.setDisplayOrientation(90);

        } catch (Exception e) {
            // check for exceptions
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        Toast.makeText(MainActivity.this, "Surface CHANGED!", Toast.LENGTH_LONG).show();
        if (mSurfaceView.getHolder().getSurface() == null) {
            // preview surface does not exist
            Toast.makeText(MainActivity.this, "Surface not EXIST!", Toast.LENGTH_LONG).show();
            return;
        }
        // stop preview before making changes
        try {
            mCamera.stopPreview();
            Toast.makeText(MainActivity.this, "Preview Stopped", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
            Toast.makeText(MainActivity.this, "Preview Cannot STOPPED!!", Toast.LENGTH_LONG).show();
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mSurfaceView.getHolder());
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Cannot Start Preview (SC)!", Toast.LENGTH_LONG).show();
            Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Toast.makeText(MainActivity.this, "PREVIEW!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
