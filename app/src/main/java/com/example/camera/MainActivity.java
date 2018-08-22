package com.example.camera;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.socks.library.KLog;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    final static String TAG = "MainActivity";
    private SurfaceView surfaceView;//预览摄像头
    private SurfaceHolder surfaceHolder;
    private Button button;//拍照按钮
    private Camera camera;//摄像头

    @ViewInject(R.id.imageview)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initListener();
    }

    //初始化View的方法,其实少的话都放到
    private void initView() {
        x.view().inject(this);
        surfaceView = (SurfaceView) findViewById(R.id.main_surface_view);
        button = (Button) findViewById(R.id.main_button);
    }

    private void initData() {
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
    }

    private void initListener() {
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "surfaceView", Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initCamera() {
        camera.startPreview();

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewFormat(ImageFormat.NV21);
            parameters.setPreviewFpsRange(5, 8);


            List<Integer> list = parameters.getSupportedPreviewFormats();
            KLog.i(TAG, list);

            camera.setParameters(parameters);
            camera.setPreviewDisplay(surfaceHolder);
            camera.setPreviewCallback(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    KLog.i(data.length);
                    KLog.i(camera.getParameters().getPreviewFormat());
                    KLog.i(camera.getParameters().getPreviewFrameRate());
                    KLog.i(camera.getParameters().getPreviewSize());




                }
            });

        } catch (Exception e) {
            if (null != camera) {
                camera.release();
                camera = null;
            }
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "启动摄像头失败,请开启摄像头权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != camera) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
