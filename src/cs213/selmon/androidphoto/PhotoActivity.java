package cs213.selmon.androidphoto;

import java.io.FileNotFoundException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import cs213.selmon.androidphoto.model.Photo;
import cs213.selmon.androidphoto.util.DataStore;
import cs213.selmon.androidphoto.util.PhotoHelper;

public class PhotoActivity extends Activity {
  
  private static final int IMAGE_SIZE = 500;
  
  private DataStore mDataStore;
  private PhotoHelper mPhotoHelper;
  
  private ImageView mImageView;

  private Photo mPhoto;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); 

    setContentView(R.layout.photo);
    
    mDataStore = ((PhotoApplication) this.getApplication()).getDataStore();
        
    PhotoApplication application = ((PhotoApplication) this.getApplication());
    mPhoto = application.getCurrentPhoto();

    mPhotoHelper = new PhotoHelper(this);

    mImageView = (ImageView) findViewById(R.id.image);
    try {
      Uri photoUri = Uri.parse(mPhoto.getPath());
      Bitmap bmp = mPhotoHelper.decodeUri(
          photoUri,
          IMAGE_SIZE
          );
      mImageView.setImageBitmap(bmp);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
  
  @Override
  protected void onStop() {
    mDataStore.saveStateToDisk();   
    //Recompile!
    
    super.onStop();
  }

}
