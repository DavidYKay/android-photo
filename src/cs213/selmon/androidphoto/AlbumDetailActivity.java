package cs213.selmon.androidphoto;

import android.app.ListActivity;
import android.os.Bundle;

public class AlbumDetailActivity extends ListActivity {
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); 

    setContentView(R.layout.album_detail);
  }


}
