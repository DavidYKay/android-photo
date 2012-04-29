package cs213.selmon.androidphoto.util;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

public class PhotoHelper {
  private static final String TAG = "PhotoHelper";

  private static final int IMAGE_MAX_SIZE = 100;

  private Context mContext;
  private ContentResolver mContentResolver;

  public PhotoHelper(Context context) {
    mContext = context;
    mContentResolver = mContext.getContentResolver();
  }

  // /files/photos/drkay.png
  // content:/images/photos/1
  // PATH != URI


  //  public Bitmap decodeImageAtPath(String path) {
  //    File file = new File(path);
  //    return decodeFile(file);
  //  }

  //Bitmap bmp=BitmapFactory.decodeStream(
  //mContentResolver.openInputStream(uri)
  //);

  //  public Bitmap decodeFile(File f) throws FileNotFoundException{
  //    InputStream fis = new InputStream(f);
  //    return decodeInputStream(fis);
  //  }
  
  public Bitmap decodeUri(Uri uri) throws FileNotFoundException{
    return decodeUri(uri, IMAGE_MAX_SIZE);
  }

  public Bitmap decodeUri(Uri uri, int maxImageSize) throws FileNotFoundException{
    InputStream fis = mContentResolver.openInputStream(uri);
    Bitmap b = null;
    BitmapFactory.Options o = null;
    //Decode image size
    o = new BitmapFactory.Options();
    o.inJustDecodeBounds = true;

    try {
      BitmapFactory.decodeStream(fis, null, o);
      fis.close();
    } catch (IOException e) {
      Log.e(TAG, "Could NOT decode file: " + uri);
      return null;
    }

    try {
      int scale = 1;
      if (o.outHeight > maxImageSize || o.outWidth > maxImageSize) {
        scale = (int)Math.pow(2, (int) Math.round(Math.log(maxImageSize / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
      }

      //Decode with inSampleSize
      BitmapFactory.Options o2 = new BitmapFactory.Options();
      o2.inSampleSize = scale;
      fis = mContentResolver.openInputStream(uri);
      b = BitmapFactory.decodeStream(fis, null, o2);
      fis.close();
    } catch (IOException e) {
      Log.e(TAG, "Could NOT decode bitmap: " + uri);
      return null;
    }
    return b;
  }

  //public Bitmap decodeInputStream(InputStream fis){
  //  Bitmap b = null;
  //  BitmapFactory.Options o = null;
  //  try {
  //    //Decode image size
  //    o = new BitmapFactory.Options();
  //    o.inJustDecodeBounds = true;

  //  } catch (IOException e) {
  //    Log.e(TAG, "Could NOT open filestream: " + f);
  //    return null;
  //  }
  //  try {
  //    BitmapFactory.decodeStream(fis, null, o);
  //    fis.close();
  //  } catch (IOException e) {
  //    Log.e(TAG, "Could NOT decode file: " + f);
  //    return null;
  //  }

  //  try {
  //    int scale = 1;
  //    if (o.outHeight > maxImageSize || o.outWidth > maxImageSize) {
  //      scale = (int)Math.pow(2, (int) Math.round(Math.log(maxImageSize / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
  //    }

  //    //Decode with inSampleSize
  //    BitmapFactory.Options o2 = new BitmapFactory.Options();
  //    o2.inSampleSize = scale;
  //    fis = new InputStream(f);
  //    b = BitmapFactory.decodeStream(fis, null, o2);
  //    fis.close();
  //  } catch (IOException e) {
  //    Log.e(TAG, "Could NOT decode bitmap: " + f);
  //    return null;
  //  }
  //  return b;
  //}
}
