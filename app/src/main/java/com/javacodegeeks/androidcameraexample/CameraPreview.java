package com.javacodegeeks.androidcameraexample;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.support.annotation.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder mHolder;
	private Camera mCamera;

	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;
		mHolder = getHolder();
		mHolder.addCallback(this);
		// deprecated setting, but required on Android versions prior to 3.0
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// create the surface and start camera preview
			if (mCamera == null) {
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();
			}
		} catch (IOException e) {
			Log.d(VIEW_LOG_TAG, "Error setting camera preview: " + e.getMessage());
		}
	}

	public void refreshCamera(Camera camera) {
		Camera.Parameters parameters=camera.getParameters();
        Size s = null;

		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {

			Log.d("Cam Prop", "Width"+size.width+" Height:"+size.height);
            parameters.setPreviewSize(size.width, size.height);

		}

        parameters.setPreviewSize(1920, 1080);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
       // parameters.set("iso", "800");
        parameters.setExposureCompensation(parameters.getMaxExposureCompensation ()/2);
        Log.d("exposure", "refreshCamera: "+parameters.getMaxExposureCompensation ());
       // String mWhiteBalanceMode = Camera.Parameters.WHITE_BALANCE_CLOUDY_DAYLIGHT;
       // parameters.setWhiteBalance(mWhiteBalanceMode);


       // parameters.set("orientation", "portrait");
        //parameters.setRotation(90);



        // mCamera.stopPreview();
        //mCamera.setParameters(parameters);
        //mCamera.startPreview();
       // mCamera.autoFocus(null);


        if (mHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}
		// stop preview before making changes
		try {
			mCamera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}
		// set preview size and make any resize, rotate or
		// reformatting changes here
		// start preview with new settings
		setCamera(camera);
		try {
            //mCamera.setDisplayOrientation(180);
            //mCamera.setParameters(parameters);

			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();
		} catch (Exception e) {
			Log.d(VIEW_LOG_TAG, "Error starting camera preview: " + e.getMessage());
		}
	}


    public void cameraconfig(Camera camera){



    }

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.
		refreshCamera(mCamera);
	}

	public void setCamera(Camera camera) {
		//method to set a camera instance
		mCamera = camera;
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// mCamera.release();

	}
}