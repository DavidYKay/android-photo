package cs213.selmon.androidphoto.util;

import java.io.IOException;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.MediaScannerConnection;
import android.net.Uri;

public class PhotoScanner {

  private Context mContext;
  
  private MediaScannerConnection mConnection;

  public PhotoScanner(Context context) {
    mContext = context;
  }

  public void scanDefaultPhotos() {
    mConnection = new android.media.MediaScannerConnection(mContext, new MyClient()); 
    mConnection.connect();
  }

  private class MyClient implements MediaScannerConnection.MediaScannerConnectionClient {

    public MyClient() {

    }

    @Override
    public void onMediaScannerConnected() {        
      AssetManager assetManager = mContext.getAssets();
      String[] filePaths;
      try {
        filePaths = assetManager.list("");
        for (String filePath : filePaths) {
          mConnection.scanFile(filePath, null);
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        mConnection.disconnect();
      }
    }
    @Override
    public void onScanCompleted(String path, Uri uri) {
      // TODO Auto-generated method stub

    }
  };     
}
