package com.hdfex.merchantqrshow.utils.camera.easyphotopicker;


import com.hdfex.merchantqrshow.utils.camera.EasyImage;

import java.io.File;

/**
 * 回调
 */
public class DefaultCallback implements EasyImage.Callbacks {

    @Override
    public void onImagePickerError(Exception e, EasyImage.ImageSource source) {
    }

    @Override
    public void onImagePicked(File imageFile, EasyImage.ImageSource source) {
    }

    @Override
    public void onCanceled(EasyImage.ImageSource source) {
    }
}
