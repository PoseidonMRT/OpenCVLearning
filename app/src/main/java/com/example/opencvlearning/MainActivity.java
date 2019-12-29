package com.example.opencvlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.osgi.OpenCVNativeLoader;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.sample_image);
        new OpenCVNativeLoader().init();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (!OpenCVLoader.initDebug()) {
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, getApplicationContext(), mLoaderCallback);
//        } else {
//            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//        }
        filterImage();
    }

    private void filterImage(){
        Mat imgMat = null;
        try {
            imgMat = Utils.loadResource(this,R.drawable.demo);
            Imgproc.cvtColor(imgMat,imgMat,Imgproc.COLOR_RGB2BGRA);
            Mat resultMat = imgMat.clone();
            Imgproc.Canny(imgMat,resultMat,80,90);
            Bitmap bitmap = Bitmap.createBitmap(resultMat.cols(),resultMat.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(resultMat, bitmap);
            mImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
