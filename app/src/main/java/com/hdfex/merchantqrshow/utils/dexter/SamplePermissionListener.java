/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hdfex.merchantqrshow.utils.dexter;

import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class SamplePermissionListener implements PermissionListener {

  private   OnPermissionListener onPermissionListener;


  public SamplePermissionListener(OnPermissionListener onPermissionListener) {
    this.onPermissionListener = onPermissionListener;
  }

  @Override public void onPermissionGranted(PermissionGrantedResponse response) {
    if (onPermissionListener!=null){
      onPermissionListener.showPermissionGranted(response.getPermissionName());
    }
  }

  @Override public void onPermissionDenied(PermissionDeniedResponse response) {
    if (onPermissionListener!=null) {
      onPermissionListener.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
    }
  }
  @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permission,
      PermissionToken token) {
    if (onPermissionListener!=null){
      onPermissionListener.showPermissionRationale(token);
    }
  }
}
