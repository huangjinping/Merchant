/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.client.android;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.camera.CameraManager;
import com.google.zxing.client.android.decode.AmbientLightManager;
import com.google.zxing.client.android.decode.BeepManager;
import com.google.zxing.client.android.decode.CaptureActivityHandler;
import com.google.zxing.client.android.decode.DecodeThread;
import com.google.zxing.client.android.decode.InactivityTimer;
import com.google.zxing.client.android.view.ViewfinderView;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.hdfex.merchantqrshow.R;
import com.hdfex.merchantqrshow.base.BaseActivity;
import com.hdfex.merchantqrshow.utils.EventRxBus;
import com.hdfex.merchantqrshow.utils.QRScan.RGBLuminanceSource;
import com.hdfex.merchantqrshow.utils.QRScan.Utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Hashtable;
import java.util.Map;


/**
 * This activity opens the camera and does the actual scanning on a background thread. It draws a
 * viewfinder to help the user place the barcode correctly, shows feedback as the image processing
 * is happening, and then overlays the results when a scan is successful.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 * @author Sean Owen
 */
public final class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback {

    private static final String TAG = CaptureActivity.class.getSimpleName();

    //重新扫码间融时间
    private static final long BULK_MODE_SCAN_DELAY_MS = 1000L;

    //扫描相册二维码
    private final int REQUEST_CODE = 521;
    private String photo_path;
    private Bitmap scanBitmap;
    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String recode = bundle.getString("recode");
            photo_path = "";
            parseResoult((Result) msg.obj);
        }
    };

    private TextView text;
    private ImageView image;

    private TextView txt_album;

    /**
     * 相机管理器
     */
    private CameraManager cameraManager;
    /**
     * 消息管理器
     */
    private CaptureActivityHandler handler;
    private Result savedResultToShow;
    /**
     * 扫描框
     */
    // private ViewfinderView viewfinderView;
    private ViewfinderView viewfinderView;


    private boolean hasSurface;


    /**
     * 二维码编码集
     */
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;


    /**
     * 电量监控
     */
    private InactivityTimer inactivityTimer;
    /**
     * 峰鸣器
     */
    private BeepManager beepManager;
    /**
     * 光源管理
     */
    private AmbientLightManager ambientLightManager;

    private FrameLayout hd_fl_capture;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private String type;


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.zxing_activity_main);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        hd_fl_capture = (FrameLayout) findViewById(R.id.hd_fl_capture);
        txt_album = (TextView) findViewById(R.id.txt_album);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(CaptureActivity.this);
        beepManager = new BeepManager(CaptureActivity.this);
        ambientLightManager = new AmbientLightManager(CaptureActivity.this);
        text = (TextView) findViewById(R.id.text);
        image = (ImageView) findViewById(R.id.image);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //setStatus();
        }
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        text.setText("http://www.baidu.com/");
//
//        try {
//            image.setImageBitmap(encodeAsBitmap("http://www.baidu.com/"));
//        } catch (WriterException e) {
//            e.printStackTrace();
//        }
        txt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sancQRcode();
            }
        });

    }

    private void sancQRcode() {
        Intent typeIntent = new Intent();
        typeIntent.setType("image/*");
        typeIntent.addCategory(Intent.CATEGORY_OPENABLE);
//        if (Build.VERSION.SDK_INT < 19) {
        typeIntent.setAction(Intent.ACTION_GET_CONTENT);
//        } else {
//            typeIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//        }

        Intent chooseIntent = Intent.createChooser(typeIntent, "选择二维码图片");
        this.startActivityForResult(chooseIntent, REQUEST_CODE);
    }


    @Override
    protected void onResume() {
        super.onResume();

        // CameraManager must be initialized here, not in onCreate(). This is necessary because we don't
        // want to open the camera driver and measure the screen size if we're going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the wrong size and partially
        // off screen.
        //  cameraManager = new CameraManager(getApplication());

        cameraManager = new CameraManager(CaptureActivity.this);
        preScan();


    }

    //显示
    private void preScan() {
        //viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        FrameLayout.LayoutParams lytp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        viewfinderView = new ViewfinderView(CaptureActivity.this, null);
        viewfinderView.setLayoutParams(lytp);
        hd_fl_capture.addView(viewfinderView);
        viewfinderView.setCameraManager(cameraManager);
        //   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        //TODO 设置屏幕方向
        setRequestedOrientation(getCurrentOrientation());

        resetStatusView();


        beepManager.updatePrefs();
        ambientLightManager.start(cameraManager);

        inactivityTimer.onResume();

        decodeFormats = null;
        characterSet = null;

        //TODO 设置 扫描框 宽高
        //   cameraManager.setManualFramingRect(300,200);//

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(surfaceHolder);
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            surfaceHolder.addCallback(this);
        }
    }

//    /**
//     * 设置状态栏
//     */
//    private void setStatus() {
//        // setTranslucentStatus(true);
//        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //透明导航栏
//        //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        tintManager.setStatusBarTintEnabled(true);
//        //  tintManager.setNavigationBarTintEnabled(true);
//        tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
//        tintManager.setNavigationBarTintResource(R.color.colorPrimaryDark);
//    }


    /**
     * 获取当前屏幕状态
     *
     * @return
     */
    private int getCurrentOrientation() {
        int rotation = getWindowManager().getDefaultDisplay().getRotation();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            switch (rotation) {
                case Surface.ROTATION_0:
                case Surface.ROTATION_90:
                    return ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                default:
                    return ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
            }
        } else {
            switch (rotation) {
                case Surface.ROTATION_0:
                case Surface.ROTATION_270:
                    return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                default:
                    return ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
            }
        }
    }


    @Override
    protected void onPause() {

        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
        beepManager.close();
        cameraManager.closeDriver();
        //historyManager = null; // Keep for onActivityResult
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                restartPreviewAfterDelay(0L);
                finish();
                return true;

            case KeyEvent.KEYCODE_FOCUS:
            case KeyEvent.KEYCODE_CAMERA:
                // Handle these events so they don't launch the Camera app
                return true;
            // Use volume up/down to turn on light
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                cameraManager.setTorch(false);
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                cameraManager.setTorch(true);
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        // Bitmap isn't used yet -- will be used soon
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
            }
            if (savedResultToShow != null) {
                //    Message message = Message.obtain(handler, R.id.decode_succeeded, savedResultToShow);
                //   handler.sendMessage(message);


                //  Toast.makeText(CaptureActivity.this, savedResultToShow.toString(), Toast.LENGTH_LONG).show();
            }
            savedResultToShow = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e(TAG, "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    /**
     * A valid barcode has been found, so give an indication of success and show the results.
     *
     * @param rawResult   The contents of the barcode.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param barcode     A greyscale bitmap of the camera data which was decoded.
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();

        boolean fromLiveScan = rawResult != null;
        if (fromLiveScan) {
            // Then not from history, so beep/vibrate and we have an image to draw on
            beepManager.playBeepSoundAndVibrate();
            drawResultPoints(barcode, scaleFactor, rawResult);
            //  Toast.makeText(CaptureActivity.this, rawResult.getText().toString(), Toast.LENGTH_LONG).show();
            //  image.setImageBitmap(barcode);

            //  Intent in = new Intent();
            // in.setClassName("com.sttxtech.hdbanking", "com.sttxtech.hdbanking.home.MainActivity");
            //TODO 跳转到商品详情页
            parseResoult(rawResult);

        } else {
            restartPreviewAfterDelay(BULK_MODE_SCAN_DELAY_MS);
        }

    }


    /**
     * Superimpose a line for 1D or dots for 2D to highlight the key features of the barcode.
     *
     * @param barcode     A bitmap of the captured image.
     * @param scaleFactor amount by which thumbnail was scaled
     * @param rawResult   The decoded results which contains the points to draw.
     */
    private void drawResultPoints(Bitmap barcode, float scaleFactor, Result rawResult) {
        ResultPoint[] points = rawResult.getResultPoints();
        if (points != null && points.length > 0) {
            Canvas canvas = new Canvas(barcode);
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.colorPrimary));
            if (points.length == 2) {
                paint.setStrokeWidth(4.0f);
                drawLine(canvas, paint, points[0], points[1], scaleFactor);
            } else if (points.length == 4 &&
                    (rawResult.getBarcodeFormat() == BarcodeFormat.UPC_A ||
                            rawResult.getBarcodeFormat() == BarcodeFormat.EAN_13)) {
                // Hacky special case -- draw two lines, for the barcode and metadata
                drawLine(canvas, paint, points[0], points[1], scaleFactor);
                drawLine(canvas, paint, points[2], points[3], scaleFactor);
            } else {
                paint.setStrokeWidth(10.0f);
                for (ResultPoint point : points) {
                    if (point != null) {
                        canvas.drawPoint(scaleFactor * point.getX(), scaleFactor * point.getY(), paint);
                    }
                }
            }
        }
    }

    private static void drawLine(Canvas canvas, Paint paint, ResultPoint a, ResultPoint b, float scaleFactor) {
        if (a != null && b != null) {
            canvas.drawLine(scaleFactor * a.getX(),
                    scaleFactor * a.getY(),
                    scaleFactor * b.getX(),
                    scaleFactor * b.getY(),
                    paint);
        }
    }


    private void sendReplyMessage(int id, Object arg, long delayMS) {
        if (handler != null) {
            Message message = Message.obtain(handler, id, arg);
            if (delayMS > 0L) {
                handler.sendMessageDelayed(message, delayMS);
            } else {
                handler.sendMessage(message);
            }
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.w(TAG, "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            Log.w(TAG, ioe);

        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.w(TAG, "Unexpected error initializing camera", e);

        }
    }

    /**
     * 重新开始
     *
     * @param delayMS
     */
    public void restartPreviewAfterDelay(long delayMS) {

        if (handler != null) {
            handler.sendEmptyMessageDelayed(R.id.restart_preview, delayMS);
        }
        resetStatusView();
    }

    private void resetStatusView() {

        viewfinderView.setVisibility(View.VISIBLE);

    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }


    Bitmap encodeAsBitmap(String contents) throws WriterException {

        final int WHITE = 0xFFFFFFFF;
        final int BLACK = 0xFF000000;

        // This assumes the view is full screen, which is a good assumption
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point displaySize = new Point();
        //  display.getSize(displaySize);
        int width = display.getWidth();  //displaySize.x;
        int height = display.getHeight();//  displaySize.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 7 / 8;


        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(contentsToEncode, BarcodeFormat.QR_CODE, smallerDimension, smallerDimension, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        width = result.getWidth();
        height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }


    Handler getHandler(DecodeThread decodeThread) {
        try {
            decodeThread.handlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return decodeThread.handler;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    // 获取选中图片的路径
                    ContentResolver contentResolver = getContentResolver();
                    Uri originalUri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = contentResolver.query(originalUri, proj, null, null, null);
                    if (cursor != null) {
                        int columnindex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        photo_path = cursor.getString(columnindex);
                    }
                    if (TextUtils.isEmpty(photo_path)) {
                        photo_path = Utils.getPath(getApplicationContext(), data.getData());
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    new Thread(new Runnable() {

                        @Override
                        public void run() {


                            Result result = scanningImage(photo_path);

                            // String result = decode(photo_path);
                            if (result == null) {
                                Looper.prepare();
                                Toast.makeText(getApplicationContext(), "二维码图片不清晰，无法识别", Toast.LENGTH_SHORT)
                                        .show();
                                Looper.loop();
                            } else {
                                Log.i("123result", result.toString());
                                // Log.i("123result", result.getText());
                                // 数据返回
                                String recode = recode(result.toString());
                                Message msg = Message.obtain();
                                Bundle bundle = new Bundle();
                                msg.obj = result;

                                bundle.putCharSequence("recode", recode);
                                msg.setData(bundle);
                                mhandler.sendMessage(msg);
                            }
                        }
                    }).start();
                    break;
            }
        }
    }

    /**
     * 中文乱码
     * 暂时解决大部分的中文乱码 但是还有部分的乱码无法解决 .
     */
    private String recode(String str) {
        String formart = "";

        try {
            boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
                    .canEncode(str);
            if (ISO) {
                formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
                Log.i("1234      ISO8859-1", formart);
            } else {
                formart = str;
                Log.i("1234      stringExtra", str);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return formart;
    }

    private Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 先获取原大小
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false; // 获取新的大小
        int sampleSize = (int) (options.outHeight / (float) 500);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        try {
            LuminanceSource source1 = new PlanarYUVLuminanceSource(
                    rgb2YUV(scanBitmap), scanBitmap.getWidth(),
                    scanBitmap.getHeight(), 0, 0, scanBitmap.getWidth(),
                    scanBitmap.getHeight(), false);
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                    source1));
            MultiFormatReader reader1 = new MultiFormatReader();
            Result result1;

            result1 = reader1.decode(binaryBitmap);
            String content = result1.getText();
            Log.i("123content", content);
        } catch (Exception e1) {

            e1.printStackTrace();
            return null;

        }


        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * @param bitmap 转换的图形
     * @return YUV数据
     */
    public byte[] rgb2YUV(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        int len = width * height;
        byte[] yuv = new byte[len * 3 / 2];
        int y, u, v;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int rgb = pixels[i * width + j] & 0x00FFFFFF;

                int r = rgb & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb >> 16) & 0xFF;

                y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
                u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
                v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

                y = y < 16 ? 16 : (y > 255 ? 255 : y);
                u = u < 0 ? 0 : (u > 255 ? 255 : u);
                v = v < 0 ? 0 : (v > 255 ? 255 : v);

                yuv[i * width + j] = (byte) y;
            }
        }
        return yuv;
    }


    private void parseResoult(Result rawResult) {

        try {
//            text.setText(rawResult.getText().toString());

            Log.d("hjp", rawResult.toString());
            EventRxBus.getInstance().post(type, rawResult.toString());
            finish();
//            Map<String, String> urlRequestParam = RegexUtils.getURLRequestParam(rawResult.getText().toString());

//            rawResult.getText().startsWith("SP-")

//            String sp = urlRequestParam.get("SP");
//            String sh = urlRequestParam.get("SH");
//            String commodityId = urlRequestParam.get("commodityId");
//            String businessId = urlRequestParam.get("businessId");
//            String packageId = urlRequestParam.get("packageId");
//            String type = urlRequestParam.get("type");
//                restartPreviewAfterDelay(BULK_MODE_SCAN_DELAY_MS);

//            if (!TextUtils.isEmpty(sp)) {
////                toProduct(sp);
//            } else {
//                restartPreviewAfterDelay(BULK_MODE_SCAN_DELAY_MS);
////                showToast(getApplicationContext(), "二维码编号无法解析!").show();
//            }
        } catch (Exception e) {
            restartPreviewAfterDelay(BULK_MODE_SCAN_DELAY_MS);
//            showToast(getApplicationContext(), "二维码编号无法解析!").show();
            e.printStackTrace();
        }

    }


    public static void startAction(Context context, String type) {
        Intent intent = new Intent(context, CaptureActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }
}
