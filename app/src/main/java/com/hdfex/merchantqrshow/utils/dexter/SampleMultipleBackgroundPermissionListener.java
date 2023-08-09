package com.hdfex.merchantqrshow.utils.dexter;

import android.os.Handler;
import android.os.Looper;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

/**
 * Created by harrishuang on 16/6/20.
 */
public class SampleMultipleBackgroundPermissionListener implements MultiplePermissionsListener {

    private Handler handler = new Handler(Looper.getMainLooper());
    private   OnPermissionListener onPermissionListener;

    public SampleMultipleBackgroundPermissionListener(OnPermissionListener onPermissionListener) {
        this.onPermissionListener=onPermissionListener;

    }

    @Override
    public void onPermissionsChecked(final MultiplePermissionsReport report) {

        handler.post(new Runnable() {
            @Override public void run() {
                for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
                    onPermissionListener.showPermissionGranted(response.getPermissionName());
                }

                for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
                    onPermissionListener.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
                }
            }
        });



    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions,final PermissionToken token) {
        handler.post(new Runnable() {
            @Override public void run() {
                onPermissionListener.showPermissionRationale(  token);

            }
        });

    }

}
