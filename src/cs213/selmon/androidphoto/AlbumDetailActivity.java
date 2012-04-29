package cs213.selmon.androidphoto;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.TextView;
import cs213.selmon.androidphoto.model.Album;
import cs213.selmon.androidphoto.util.DataStore;

public class AlbumDetailActivity extends ListActivity {
  
  private DataStore mDataStore;
  private Album mAlbum;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState); 

    setContentView(R.layout.album_detail);
    
    mDataStore = ((PhotoApplication) this.getApplication()).getDataStore();
    mAlbum = ((PhotoApplication) this.getApplication()).getCurrentAlbum();

    TextView albumTitle = (TextView) findViewById(R.id.album_title);
    albumTitle.setText(mAlbum.getName());

  }
  
  @Override
  protected void onStop() {
    mDataStore.saveStateToDisk();    
    
    super.onStop();
  }

}
