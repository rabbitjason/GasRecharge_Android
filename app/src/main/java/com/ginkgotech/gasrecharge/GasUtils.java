package com.ginkgotech.gasrecharge;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/11/2.
 */

public class GasUtils {

    public static Bitmap byteToBitmap(byte[] imgByte) {
        //InputStream input = null;
        Bitmap bitmap = null;
        //BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.outWidth = 333;
        options.outHeight = 333;
        options.outMimeType = "PNG";
        bitmap = BitmapFactory.decodeByteArray(imgByte, 0 , imgByte.length, options);

//        options.inSampleSize = 2;
//        input = new ByteArrayInputStream(imgByte);
//        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
//                input, null, options));
//        bitmap = (Bitmap) softRef.get();
////        if (imgByte != null) {
////            imgByte = null;
////        }
//
//        try {
//            if (input != null) {
//                input.close();
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
        return bitmap;
    }

    public static String getCurrentTime() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("HHmmss");
        String str = format.format(new Date(time));
        return str;
    }

    public static String getCurrentDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String str = format.format(new Date(time));
        return str;
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /**
     * Convert hex string to byte[]
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static void showMessageDialog(Context context, String title, String msg) {
        new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(title)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    // width height 实际需要的图片尺寸，为了保证裁剪的图片不变形，最好还是算一下缩放比例，这里就不写了
    public static Bitmap loadBitmap(Resources res, int resId, int width, int height)
    {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateSampleSize(options, width, height);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeResource(res, resId, options);

//        Bitmap bitmap = null;
//        if (url == null || !new File(url).exists()) return bitmap;
//        try
//        {
//            BitmapFactory.Options opts = new BitmapFactory.Options();
//            opts.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(url, opts);
//            opts.inSampleSize = calculateSampleSize(opts, width, height);
//            opts.inJustDecodeBounds = false;
//            opts.inPreferredConfig = Bitmap.Config.RGB_565;
//            bitmap = BitmapFactory.decodeStream(new FileInputStream(url), null, opts);
//            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height);
//            return bitmap;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return bitmap;
    }

    private static int calculateSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth)
        {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
