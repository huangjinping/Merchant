package com.hdfex.merchantqrshow.utils.dexter;

import com.karumi.dexter.PermissionToken;

/**
 * Created by harrishuang on 16/5/29.
 */
public interface OnPermissionListener {

    public void showPermissionGranted(String permission);
    public void showPermissionDenied(String permission, boolean isPermanentlyDenied);
    public void showPermissionRationale(PermissionToken token);

    }
