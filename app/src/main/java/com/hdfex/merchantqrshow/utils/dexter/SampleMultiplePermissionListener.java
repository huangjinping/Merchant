package com.hdfex.merchantqrshow.utils.dexter;

import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

/**
 * Created by harrishuang on 16/6/17.
 */
public class SampleMultiplePermissionListener implements MultiplePermissionsListener {


    private   OnPermissionListener onPermissionListener;

    public SampleMultiplePermissionListener(OnPermissionListener onPermissionListener) {
        this.onPermissionListener = onPermissionListener;
    }

    public void setOnPermissionListener(OnPermissionListener onPermissionListener) {
        this.onPermissionListener = onPermissionListener;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {



        for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
            onPermissionListener.showPermissionGranted(response.getPermissionName());
        }

        for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
            onPermissionListener.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        onPermissionListener.showPermissionRationale(token);

    }
}
