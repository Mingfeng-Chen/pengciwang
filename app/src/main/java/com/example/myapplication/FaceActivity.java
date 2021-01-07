package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arcsoft.arcfacedemo.common.Constants;
import com.arcsoft.arcfacedemo.util.ConfigUtil;
import com.arcsoft.arcfacedemo.util.DrawHelper;
import com.arcsoft.arcfacedemo.util.camera.CameraListener;
import com.arcsoft.face.ErrorInfo;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceSimilar;

import java.util.ArrayList;
import java.util.List;

public class FaceActivity extends AppCompatActivity {
    private static final int MAX_DETECT_NUM = 10;
    private static final String TAG = "FaceActivity";
    private Camera.Size previewSize;
    private DrawHelper drawHelper;
    private View previewView;
    private  FaceEngine mFaceEngine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face);
        mFaceEngine = new FaceEngine();
        int activeCode = mFaceEngine.active(FaceActivity.this, Constants.APP_ID, Constants.SDK_KEY);
        if (activeCode == ErrorInfo.MOK || activeCode == ErrorInfo.MERR_ASF_ALREADY_ACTIVATED) {
            //激活成功或者已激活过的情况
        }else{
            //激活失败的情况
        }
        int afCode = mFaceEngine.init(this, FaceEngine.ASF_DETECT_MODE_VIDEO, ConfigUtil.getFtOrient(this),
                16, MAX_DETECT_NUM, FaceEngine.ASF_FACE_RECOGNITION | FaceEngine.ASF_FACE_DETECT | FaceEngine.ASF_LIVENESS);
        if (afCode != ErrorInfo.MOK) {
            //初始化成功

        }else{
            //初始化失败

        }
        CameraListener cameraListener=new CameraListener() {
            @Override
            public void onCameraOpened(Camera camera, int cameraId, int displayOrientation, boolean isMirror) {
                Log.i(TAG, "onCameraOpened: " + cameraId + "  " + displayOrientation + " " + isMirror);
                previewSize = camera.getParameters().getPreviewSize();
                drawHelper = new DrawHelper(previewSize.width, previewSize.height, previewView.getWidth(), previewView.getHeight(), displayOrientation
                        , cameraId, isMirror);
            }

            @Override
            public void onPreview(byte[] nv21, Camera camera) {
                List faceInfoList = new ArrayList<>();
                int code = mFaceEngine.detectFaces(nv21, previewSize.width, previewSize.height, FaceEngine.CP_PAF_NV21, faceInfoList);
                if (code == ErrorInfo.MOK && faceInfoList.size() >0) {

                    //人脸检测成功并且检测到了人脸的情况

                }else{

                    //人脸检测失败或未检测到人脸的情况
                    Toast.makeText(getApplicationContext(),"检测失败",Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onCameraClosed() {
                Log.i(TAG, "onCameraClosed: ");
            }

            @Override
            public void onCameraError(Exception e) {
                Log.i(TAG, "onCameraError: " + e.getMessage());
            }

            @Override
            public void onCameraConfigurationChanged(int cameraID, int displayOrientation) {
                if (drawHelper != null) {
                    drawHelper.setCameraDisplayOrientation(displayOrientation);
                }
                Log.i(TAG, "onCameraConfigurationChanged: " + cameraID + "  " + displayOrientation);
            }
        };
        FaceFeature faceFeature = new FaceFeature();
        //code = mFaceEngine.extractFaceFeature(nv21, width, height, FaceEngine.CP_PAF_NV21, faceInfo, faceFeature);

        /*if (code == ErrorInfo.MOK) {

            //特征提取成功

        }else{

            //特征提取失败，可根据code查看原因

        }*/
    }
    public void compareFace(FaceFeature faceFeature1,FaceFeature faceFeature2){

        FaceSimilar faceSimilar = new FaceSimilar();

        int code = mFaceEngine.compareFaceFeature(faceFeature1, faceFeature2, faceSimilar);

        if (code == ErrorInfo.MOK){

            //比对成功，可查看faceSimilar中的相似度

        }else{

            //比对失败，可根据code查看原因

        }

    }
}
