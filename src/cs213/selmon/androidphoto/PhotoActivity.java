package cs213.selmon.androidphoto;

import android.app.ListActivity;
import android.os.Bundle;

public class PhotoActivity extends ListActivity {
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); 

    setContentView(R.layout.photo);
  }


}
