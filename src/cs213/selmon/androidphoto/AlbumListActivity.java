package cs213.selmon.androidphoto;

import android.app.ListActivity;
import android.os.Bundle;

public class AlbumListActivity extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.album_list);
    }
}