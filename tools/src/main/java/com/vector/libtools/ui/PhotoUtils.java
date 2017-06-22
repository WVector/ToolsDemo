package com.vector.libtools.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoUtils {
    private PhotoUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /****************************************************************** 打开相册, 并且进行相片的裁剪 ***********************************************************/
    /**
     * 打开相册,
     *
     * @param activity
     * @param picSavePath
     * @param imageRequestCode
     */
    public static void openGalleryAndCutFree(Activity activity, String picSavePath, int imageRequestCode) {
        openGalleryAndCut(activity, fromFilePath(picSavePath, activity), false, 1, 1, imageRequestCode);
    }

    /**
     * @param activity
     * @param saveUri
     * @param imageRequestCode
     */
    public static void openGalleryAndCutFree(Activity activity, Uri saveUri, int imageRequestCode) {
        openGalleryAndCut(activity, saveUri, false, 1, 1, imageRequestCode);
    }

    /**
     * 打开相册,并且进行裁剪
     *
     * @param activity
     * @param picSavePath
     * @param hasRatio
     * @param outWidth
     * @param outHeight
     * @param imageRequestCode
     */
    public static void openGalleryAndCut(Activity activity, String picSavePath, Boolean hasRatio, int outWidth, int outHeight, int imageRequestCode) {
        openGalleryAndCut(activity, fromFilePath(picSavePath, activity), hasRatio, outWidth, outHeight, imageRequestCode);
    }

    /**
     * 打开相册并且, 进行裁剪
     *
     * @param activity
     * @param saveUri
     * @param hasRatio         是否按照比例裁剪
     * @param outWidth
     * @param outHeight
     * @param imageRequestCode 打开图片的请求码
     */
    public static void openGalleryAndCut(Activity activity, Uri saveUri, Boolean hasRatio, int outWidth, int outHeight, int imageRequestCode) {
        Intent intent = getContentIntent(saveUri, hasRatio, outWidth, outHeight);
        activity.startActivityForResult(intent, imageRequestCode);
    }

    private static Intent getContentIntent(Uri saveUri, boolean hasRatio, int outWidth, int outHeight) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", "true");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        if (hasRatio) {
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", outWidth);
            intent.putExtra("aspectY", outHeight);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", outWidth);
            intent.putExtra("outputY", outHeight);
        }
        intent.putExtra("scale", true);
        // 一般不用这个去传输, 因为当裁剪的图片的比例比较大的时候会造成OOM, 所以一般会吧他置为0
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", true);
        return intent;
    }

    /**
     * @param fragment
     * @param picSavePath
     * @param imageRequestCode
     */
    public static void openGalleryAndCutFree(Fragment fragment, String picSavePath, int imageRequestCode) {
        openGalleryAndCut(fragment, fromFilePath(picSavePath, fragment.getActivity()), false, 1, 1, imageRequestCode);
    }

    /**
     * @param fragment
     * @param saveUri
     * @param imageRequestCode
     */
    public static void openGalleryAndCutFree(Fragment fragment, Uri saveUri, int imageRequestCode) {
        openGalleryAndCut(fragment, saveUri, false, 1, 1, imageRequestCode);
    }

    /**
     * 打开相册,并且进行裁剪
     *
     * @param fragment
     * @param picSavePath
     * @param hasRatio
     * @param outWidth
     * @param outHeight
     * @param imageRequestCode
     */
    public static void openGalleryAndCut(Fragment fragment, String picSavePath, Boolean hasRatio, int outWidth, int outHeight, int imageRequestCode) {
        openGalleryAndCut(fragment, Uri.fromFile(new File(picSavePath)), hasRatio, outWidth, outHeight, imageRequestCode);
    }

    /**
     * 打开相册并且, 进行裁剪
     *
     * @param imageRequestCode
     * @param saveUri
     */
    public static void openGalleryAndCut(Fragment fragment, Uri saveUri, Boolean hasRatio, int outWidth, int outHeight, int imageRequestCode) {
        Intent intent = getContentIntent(saveUri, hasRatio, outWidth, outHeight);
        fragment.startActivityForResult(intent, imageRequestCode);
    }

    /******************************************************************************************************************************************************/
    /************************************************************************** 打开相册 *****************************************************************/
    /**
     * 打开相册, 不进行裁剪 通过这个方法去获取选择图片的位置 String path =
     * PhotoUtils.getPath(getActivity(), data.getData();
     *
     * @param activity
     * @param imageRequestCode
     */
    public static void openGallery(Activity activity, int imageRequestCode) {
        Intent intentFromGallery = new Intent(Intent.ACTION_GET_CONTENT);
        intentFromGallery.setType("image/*"); // 设置文件类型
        activity.startActivityForResult(intentFromGallery, imageRequestCode);
        // 通过这个方法去
        // String path = PhotoUtils.getPath(getActivity(), data.getData();
    }

    public static void openGallery(Fragment fragment, int imageRequestCode) {
        Intent intentFromGallery = new Intent(Intent.ACTION_GET_CONTENT);
        intentFromGallery.setType("image/*"); // 设置文件类型
        fragment.startActivityForResult(intentFromGallery, imageRequestCode);
        // 通过这个方法去
        // String path = PhotoUtils.getPath(getActivity(), data.getData();
    }

    /******************************************************************************************************************************************************/
    /************************************************************************** 打开相机 *****************************************************************/
    public static void openCamera(Activity activity, String picSavePath, int cameraRequestCode) {
        Uri picSaveUri;
        File file = new File(picSavePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        picSaveUri = fromFile(file, activity);
        openCamera(activity, picSaveUri, cameraRequestCode);

    }

    public static void openCamera(Activity activity, Uri picSaveUri, int cameraRequestCode) {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picSaveUri);
        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);

        openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        ResolveInfo reInfo = activity.getApplicationContext().getPackageManager().resolveActivity(openCameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (reInfo == null) {
            Toast.makeText(activity, "请先安装照相机", Toast.LENGTH_LONG).show();
            return;
        }
        activity.startActivityForResult(openCameraIntent, cameraRequestCode);
    }

    public static void openCamera(Fragment fragment, String picSavePath, int cameraRequestCode) {
        Uri picSaveUri;
        File file = new File(picSavePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        picSaveUri = fromFile(file, fragment.getActivity());
        openCamera(fragment, picSaveUri, cameraRequestCode);
    }

    public static void openCamera(Fragment fragment, Uri picSaveUri, int cameraRequestCode) {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, picSaveUri);
        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);

        openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        openCameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        ResolveInfo reInfo = fragment.getActivity().getApplicationContext().getPackageManager().resolveActivity(openCameraIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (reInfo == null) {
            Toast.makeText(fragment.getContext(), "请先安装照相机", Toast.LENGTH_LONG).show();
            return;
        }
        fragment.startActivityForResult(openCameraIntent, cameraRequestCode);
    }

    /******************************************************************************************************************************************************/
    /************************************************************************** 裁剪图片 *****************************************************************/
    /**
     * @param activity
     * @param picSrcPath
     * @param picSavePath
     * @param requestCropCode
     */
    public static void cutPhotoFree(Activity activity, String picSrcPath, String picSavePath, int requestCropCode) {
        cutPhoto(activity, picSrcPath, picSavePath, false, 1, 1, requestCropCode);
    }

    /**
     * @param picSrcPath
     * @param picSavePath
     * @param requestCropCode
     */
    public static void cutPhotoFree(Fragment fragment, String picSrcPath, String picSavePath, int requestCropCode) {
        cutPhoto(fragment, picSrcPath, picSavePath, false, 1, 1, requestCropCode);
    }

    /**
     * @param activity
     * @param srcUri
     * @param saveUri
     * @param requestCropCode
     */
    public static void cutPhotoFree(Activity activity, Uri srcUri, Uri saveUri, int requestCropCode) {
        cutPhoto(activity, srcUri, saveUri, false, 1, 1, requestCropCode);
    }

    /**
     * @param fragment
     * @param srcUri
     * @param saveUri
     * @param requestCropCode
     */
    public static void cutPhotoFree(Fragment fragment, Uri srcUri, Uri saveUri, int requestCropCode) {
        cutPhoto(fragment, srcUri, saveUri, false, 1, 1, requestCropCode);
    }

    /**
     * @param activity
     * @param picSrcPath
     * @param picSavePath
     * @param hasRatio
     * @param outWidth
     * @param outHeight
     * @param requestCropCode
     */
    public static void cutPhoto(Activity activity, String picSrcPath, String picSavePath, Boolean hasRatio, int outWidth, int outHeight, int requestCropCode) {
        Uri srcUri;
        Uri saveUri;
        File srcFile = new File(picSrcPath);
        File saveFile = new File(picSavePath);

        if (!saveFile.exists()) {
            Log.e("", "srcFile not exists");
            return;
        }

        if (saveFile.exists()) {
            saveFile.delete();
        }

        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        srcUri = fromFile(srcFile, activity);
        saveUri = fromFile(saveFile, activity);

        cutPhoto(activity, srcUri, saveUri, hasRatio, outWidth, outHeight, requestCropCode);
    }

    public static Uri fromFile(File file, Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".fileProvider", file);
        } else {
            return Uri.fromFile(file);
        }
    }

    public static Uri fromFilePath(String path, Context context) {
        return fromFile(new File(path), context);
    }

    /**
     * 裁剪图片 第二种方式 ,这种方式裁剪的大小比较大不会造成溢出
     *
     * @param activity
     * @param srcUri
     * @param saveUri
     * @param outWidth
     * @param outHeight
     */
    public static void cutPhoto(Activity activity, Uri srcUri, Uri saveUri, Boolean hasRatio, int outWidth, int outHeight, int requestCropCode) {
        Intent intent = getCropIntent(activity, srcUri, saveUri, hasRatio, outWidth, outHeight);
        activity.startActivityForResult(intent, requestCropCode);
    }

    /**
     * 得到
     *
     * @param srcUri
     * @param saveUri
     * @param hasRatio
     * @param outWidth
     * @param outHeight
     * @return
     */
    private static Intent getCropIntent(Context context, Uri srcUri, Uri saveUri, boolean hasRatio, int outWidth, int outHeight) {
        if (srcUri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        Intent intent = new Intent("com.android.camera.action.CROP");


//        intent.setDataAndType(srcUri, "image/*");


        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            String url = getPath(context, srcUri);//这个方法是处理4.4以上图片返回的Uri对象不同的处理方法
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        } else {
            intent.setDataAndType(srcUri, "image/*");
        }


        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);


        // 设置裁剪
        intent.putExtra("crop", "true");
        if (hasRatio) {
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", outWidth);
            intent.putExtra("aspectY", outHeight);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", outWidth);
            intent.putExtra("outputY", outHeight);
        }
        // 这里如果是false的话, 是不会返回数据的,
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 不启用人脸识别
        intent.putExtra("noFaceDetection", true);
        return intent;
    }

    /**
     * @param fragment
     * @param picSrcPath
     * @param picSavePath
     * @param hasRatio
     * @param outWidth
     * @param outHeight
     * @param requestCropCode
     */
    public static void cutPhoto(Fragment fragment, String picSrcPath, String picSavePath, Boolean hasRatio, int outWidth, int outHeight, int requestCropCode) {
        Uri srcUri;
        Uri saveUri;
        File srcFile = new File(picSrcPath);
        File saveFile = new File(picSavePath);

        if (!saveFile.exists()) {
            Log.e("", "srcFile not exists");
            return;
        }

        if (saveFile.exists()) {
            saveFile.delete();
        }

        try {
            saveFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        srcUri = fromFile(srcFile, fragment.getActivity());
        saveUri = fromFile(saveFile, fragment.getActivity());

        cutPhoto(fragment, srcUri, saveUri, hasRatio, outWidth, outHeight, requestCropCode);
    }

    /**
     * 裁剪图片 第二种方式 ,这种方式裁剪的大小比较大不会造成溢出
     *
     * @param srcUri
     * @param saveUri
     * @param outWidth
     * @param outHeight
     * @param requestCropCode
     */
    public static void cutPhoto(Fragment fragment, Uri srcUri, Uri saveUri, Boolean hasRatio, int outWidth, int outHeight, int requestCropCode) {
        Intent intent = getCropIntent(fragment.getActivity(), srcUri, saveUri, hasRatio, outWidth, outHeight);
        fragment.startActivityForResult(intent, requestCropCode);
    }

//    /**
//     * 裁剪图片 这里的宽高大小是有限制的 小于255x255
//     *
//     * @param activity
//     * @param srcUri
//     * @param outWidth
//     * @param outHeight
//     */
//    public static void startPhotoZoom(Activity activity, Uri srcUri, Boolean hasRatio, int outWidth, int outHeight, int requestCropCode) {
//        if (srcUri == null) {
//            Log.i("tag", "The uri is not exist.");
//        }
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(srcUri, "image/*");
//
//
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//
//
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        if (hasRatio != null) {
//            if (hasRatio) {
//                // aspectX aspectY 是宽高的比例
//                intent.putExtra("aspectX", outWidth);
//                intent.putExtra("aspectY", outHeight);
//                // outputX outputY 是裁剪图片宽高
//                intent.putExtra("outputX", outWidth);
//                intent.putExtra("outputY", outHeight);
//            }
//        }
//        // 这里如果是false的话, 是不会返回数据的,
//        intent.putExtra("return-data", true);
//        // 可以直接从uri中去取
//        // Bitmap bitmap =
//        // BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
//        // intent.putExtra("outputFormat",
//        // Bitmap.CompressFormat.JPEG.toString());
//        // 不启用人脸识别
//        intent.putExtra("noFaceDetection", true);
//        activity.startActivityForResult(intent, requestCropCode);
//    }

    /**
     * 保存裁剪之后的图片数据 这种方式只是适用 intent.putExtra("return-data", true);的情况
     */
    public static Bitmap setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            return photo;
        }
        return null;
    }

    /**
     * 裁剪图片 这里的宽高大小是有限制的 小于255x255
     *
     * @param fragment
     * @param srcUri
     * @param outWidth
     * @param outHeight
     * @param requestCropCode
     */
//    public static void startPhotoZoom(Fragment fragment, Uri srcUri, Boolean hasRatio, int outWidth, int outHeight, int requestCropCode) {
//        startPhotoZoom(fragment.getActivity(), srcUri, hasRatio, outWidth, outHeight, requestCropCode);
//    }

    /******************************************************************************************************************************************************/
    /************************************************************************** 获取Bitmap, 保存Bitmap, 压缩Bitmap *************************************************/
    /**
     * 获取Bitmap
     *
     * @param srcPath
     * @return
     */
    public static Bitmap obtainBitmapFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;// 只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // TODO 现在常用手机的屏幕
        // 过大会造成OOM
        float hh = 1280f;//
        float ww = 720f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置采样率

        newOpts.inPreferredConfig = Config.ARGB_8888;// 该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;// 。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        // return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        // 其实是无效的,大家尽管尝试
        return bitmap;
    }

    /**
     * 保存一张BItmap位图
     */
    public static boolean saveBitmapToFile(Bitmap bitmap, String savePath) {
        boolean saveStatus = true;
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(savePath));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            saveStatus = false;
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

        return saveStatus;

    }

    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image, int compressQuality) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > compressQuality) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /******************************************************************************************************************************************************/
    /************************************************************************** 通过uri获取Path *************************************************/

    /**
     * 根据Uri获取路径
     *
     * @param context
     * @param uri
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
