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

package com.google.zxing.client.android.encode;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.decode.Intents;
import com.google.zxing.client.result.AddressBookParsedResult;
import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ResultParser;
import com.google.zxing.common.BitMatrix;
import com.hdfex.merchantqrshow.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.EnumMap;
import java.util.Map;


/**
 * This class does the work of decoding the user's request and extracting all the data
 * to be encoded in a barcode.
 *
 * @author dswitkin@google.com (Daniel Switkin)
 */
final class QRCodeEncoder {

    private static final String TAG = QRCodeEncoder.class.getSimpleName();

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    private final Context activity;
    private String contents;
    private String displayContents;
    private String title;
    private BarcodeFormat format;
    private final int dimension;
    private final boolean useVCard;

    QRCodeEncoder(Context activity, Intent intent, int dimension, boolean useVCard) throws WriterException {
        this.activity = activity;
        this.dimension = dimension;
        this.useVCard = useVCard;
        String action = intent.getAction();

    }

    String getContents() {
        return contents;
    }

    String getDisplayContents() {
        return displayContents;
    }

    String getTitle() {
        return title;
    }


    // Handles send intents from multitude of Android applications
    private void encodeContentsFromShareIntent(Intent intent) throws WriterException {
        // Check if this is a plain text encoding, or contact
        if (intent.hasExtra(Intent.EXTRA_STREAM)) {
            encodeFromStreamExtra(intent);
        } else {
            encodeFromTextExtras(intent);
        }
    }

    private void encodeFromTextExtras(Intent intent) throws WriterException {
        // Notice: Google Maps shares both URL and details in one text, bummer!
        String theContents = ContactEncoder.trim(intent.getStringExtra(Intent.EXTRA_TEXT));
        if (theContents == null) {
            theContents = ContactEncoder.trim(intent.getStringExtra("android.intent.extra.HTML_TEXT"));
            // Intent.EXTRA_HTML_TEXT
            if (theContents == null) {
                theContents = ContactEncoder.trim(intent.getStringExtra(Intent.EXTRA_SUBJECT));
                if (theContents == null) {
                    String[] emails = intent.getStringArrayExtra(Intent.EXTRA_EMAIL);
                    if (emails != null) {
                        theContents = ContactEncoder.trim(emails[0]);
                    } else {
                        theContents = "?";
                    }
                }
            }
        }

        // Trim text to avoid URL breaking.
        if (theContents == null || theContents.isEmpty()) {
            throw new WriterException("Empty EXTRA_TEXT");
        }
        contents = theContents;
        // We only do QR code.
        format = BarcodeFormat.QR_CODE;
        if (intent.hasExtra(Intent.EXTRA_SUBJECT)) {
            displayContents = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        } else if (intent.hasExtra(Intent.EXTRA_TITLE)) {
            displayContents = intent.getStringExtra(Intent.EXTRA_TITLE);
        } else {
            displayContents = contents;
        }
        title = activity.getString(R.string.contents_text);
    }

    // Handles send intents from the Contacts app, retrieving a contact as a VCARD.
    private void encodeFromStreamExtra(Intent intent) throws WriterException {
        format = BarcodeFormat.QR_CODE;
        Bundle bundle = intent.getExtras();
        if (bundle == null) {
            throw new WriterException("No extras");
        }
        Uri uri = bundle.getParcelable(Intent.EXTRA_STREAM);
        if (uri == null) {
            throw new WriterException("No EXTRA_STREAM");
        }
        byte[] vcard;
        String vcardString;
        InputStream stream = null;
        try {
            stream = activity.getContentResolver().openInputStream(uri);
            if (stream == null) {
                throw new WriterException("Can't open stream for " + uri);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = stream.read(buffer)) > 0) {
                baos.write(buffer, 0, bytesRead);
            }
            vcard = baos.toByteArray();
            vcardString = new String(vcard, 0, vcard.length, "UTF-8");
        } catch (IOException ioe) {
            throw new WriterException(ioe);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    // continue
                }
            }
        }
        Log.d(TAG, "Encoding share intent content:");
        Log.d(TAG, vcardString);
        Result result = new Result(vcardString, vcard, null, BarcodeFormat.QR_CODE);
        ParsedResult parsedResult = ResultParser.parseResult(result);
        if (!(parsedResult instanceof AddressBookParsedResult)) {
            throw new WriterException("Result was not an address");
        }

        if (contents == null || contents.isEmpty()) {
            throw new WriterException("No content to encode");
        }
    }

    private void encodeQRCodeContents(Intent intent, String type) {
        switch (type) {
            default:
                String textData = intent.getStringExtra(Intents.Encode.DATA);
                if (textData != null && !textData.isEmpty()) {
                    contents = textData;
                    displayContents = textData;
                    title = activity.getString(R.string.contents_text);
                }
                break;


        }
    }


    Bitmap encodeAsBitmap() throws WriterException {
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
            result = new MultiFormatWriter().encode(contentsToEncode, format, dimension, dimension, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
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


    /**
     * @param str       需要编码的内容
     * @param resWidth  编码后生成图片的宽度(像素)
     * @param resHeight 编码后生成图片的高度(像素)
     * @param bitmap    编码进二维码图片中间的小图片
     * @return
     * @throws WriterException
     */
    public Bitmap encoderToQrBitmapWrapBitmap(String str, int resWidth,
                                              int resHeight, Bitmap bitmap) throws WriterException {

        if (TextUtils.isEmpty(str)) {

            throw new IllegalArgumentException(
                    "content is empty or null,please check it");

        }

        BitMatrix matrix = null;
        Matrix m = new Matrix();
        int IMAGE_HALFWIDTH = 20;
        float sx = (float) 2 * IMAGE_HALFWIDTH / bitmap.getWidth();
        float sy = (float) 2 * IMAGE_HALFWIDTH / bitmap.getHeight();
        m.setScale(sx, sy);
        Bitmap bitmapTmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), m, false);
        try {
            matrix = new MultiFormatWriter().encode(new String(str.getBytes(),
                    "ISO-8859-1"), BarcodeFormat.QR_CODE, resWidth, resHeight);
        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
        }
        int width = matrix.getWidth();
        int height = matrix.getHeight();

        int halfW = width / 2;
        int halfH = height / 2;

        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
                        && y > halfH - IMAGE_HALFWIDTH
                        && y < halfH + IMAGE_HALFWIDTH) {
                    pixels[y * width + x] = bitmapTmp.getPixel(x - halfW
                            + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
                } else {
                    if (matrix.get(x, y)) {
                        pixels[y * width + x] = 0xff000000;
                    }
                }

            }
        }
        Bitmap finalBitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);

        finalBitmap.setPixels(pixels, 0, width, 0, 0, width, height);

        return finalBitmap;
    }

}
