package com.rohantaneja.wheelstreetbot.util;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * Created by rohantaneja on 16/04/18.
 */

public class ImagePickerUtils extends android.support.v4.app.Fragment {

    public interface OnImagePickerListener {

        void success(String name, String path);

        void fail(String message);
    }

    public static final String TAG = ImagePickerUtils.class.getSimpleName();
    private static final int CAMERA_PIC_REQUEST = 2000;
    private static final int IMAGE_PICKER_REQUEST = CAMERA_PIC_REQUEST + 1;
    private static final int MEMORY_PERMISSION_REQUEST = IMAGE_PICKER_REQUEST + 1;
    private OnImagePickerListener listener;
    private Boolean isCamera;
    private String mediaPath;

    public static void add(boolean isCamera, @NonNull android.support.v4.app.FragmentManager manager, @NonNull OnImagePickerListener listener) {
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(ImagePickerUtils.newInstance(isCamera, listener), TAG);
        transaction.commit();
    }

    private static ImagePickerUtils newInstance(boolean isCamera, @NonNull OnImagePickerListener listener) {
        ImagePickerUtils fragment = new ImagePickerUtils();

        fragment.setOnImagePickerListener(listener);
        fragment.setCamera(isCamera);
        return fragment;
    }

    public void setOnImagePickerListener(OnImagePickerListener listener) {
        this.listener = listener;
    }


    public void setCamera(Boolean camera) {
        isCamera = camera;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        if (isStoragePermissionGranted()) {
//            showGalleryDialog();
//        }


        if (isCamera) {
            mediaPath = BitmapUtils.scaledImagePath();
            File file = new File(mediaPath);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //Some devices doesn't work without it.
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                    Utils.getUri(getContext(),file,true));
                    Utils.getUri(getContext(), file, false));
            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
        } else {
            Intent galleryIntent = new Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent, IMAGE_PICKER_REQUEST);
        }
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission is granted");
                return true;
            } else {
                Log.d(TAG, "Permission is revoked");
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MEMORY_PERMISSION_REQUEST);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.d(TAG, "Permission is granted");
            return true;
        }
    }

    private void showGalleryDialog() {
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Take Photo")) {
                    dialog.dismiss();
                    mediaPath = BitmapUtils.scaledImagePath();
                    File file = new File(mediaPath);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //Some devices doesn't work without it.

                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
//                            Utils.getUri(getContext(),file,true));
                            Utils.getUri(getContext(), file, false));
//                    cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                } else if (items[which].equals("Choose from Library")) {
                    dialog.dismiss();
                    Intent galleryIntent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(galleryIntent, IMAGE_PICKER_REQUEST);
                } else if (items[which].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private String handleCameraResult() {
        //Scale down the image to reduce size.
        mediaPath = BitmapUtils.scaleImage(getContext(), mediaPath, BitmapUtils.DEFAULT_PHOTO_WIDH,
                BitmapUtils.DEFAULT_PHOTO_HEIGHT);
        return mediaPath != null ? /*"file:///" +*/ mediaPath : null;
    }

    private String handleGalleryResult(Intent intent) {
        String path = BitmapUtils.getImagePath(getContext(), intent);
        if (StringUtil.isNullOrEmpty(path)) {
            listener.fail("Please select proper image.");
            return null;
        } else {
            mediaPath = BitmapUtils.scaleImage(getContext(), path, BitmapUtils.DEFAULT_PHOTO_WIDH,
                    BitmapUtils.DEFAULT_PHOTO_HEIGHT);
            return /*"file:///" +*/ mediaPath;
        }
    }

    //Runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (MEMORY_PERMISSION_REQUEST == requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            showGalleryDialog();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String filePath = null;
            switch (requestCode) {
                case CAMERA_PIC_REQUEST:
                    filePath = handleCameraResult();
                    break;
                case IMAGE_PICKER_REQUEST:
                    filePath = handleGalleryResult(data);
                    break;
            }
            if (filePath != null) {
                listener.success(filePath.substring(filePath.lastIndexOf("/") + 1), filePath);
            } else {
                listener.fail("Unable to get path");
            }
        } else {
            listener.fail("Unable to get path");
        }
    }

}
