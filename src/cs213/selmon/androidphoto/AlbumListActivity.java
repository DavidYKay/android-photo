package cs213.selmon.androidphoto;

import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import cs213.selmon.androidphoto.model.Album;
import cs213.selmon.androidphoto.util.DataStore;

public class AlbumListActivity extends ListActivity {


  private DataStore mDataStore;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mDataStore = DataStore.getInstance();

    setContentView(R.layout.album_list);

    Button addButton = (Button) findViewById(R.id.add_button);
    addButton.setOnClickListener( new View.OnClickListener() {      
      @Override
      public void onClick(View v) {
        showNewAlbumDialog();
      }
    });

    setListAdapter(new AlbumListAdapter(
        mDataStore.getAlbums()
        ));
  }
  

  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    super.onListItemClick(l, v, position, id);
    
    AlbumListAdapter listAdapter = (AlbumListAdapter) getListAdapter();
    Album album = (Album) listAdapter.getItem(position);

    // Load new activity
    
    Intent intent = new Intent(this, AlbumDetailActivity.class);
    startActivity(intent);
  }

  private void addNewAlbum(String name) {
    Album newAlbum = new Album(name);

    mDataStore.addAlbum(newAlbum);

    setListAdapter(new AlbumListAdapter(
        mDataStore.getAlbums()
        ));
  }

  private void showNewAlbumDialog() {

    final EditText textEntryView = new EditText(this);
    AlertDialog.Builder alert = new AlertDialog.Builder(this);
    alert.setTitle("New Album")
    .setView(textEntryView)
    .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int whichButton) {
        /* User clicked OK so do some stuff */
        addNewAlbum(textEntryView.getText().toString());
      }
    })
    .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int whichButton) {
        /* User clicked cancel so do some stuff */
      }
    })
    .show();
  }

  private class AlbumListAdapter implements ListAdapter {

    private List<Album> mAlbums;

    public AlbumListAdapter(List<Album> albums) {
      mAlbums = albums;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
      // TODO Implement this later
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
      // TODO Implement this later      
    }

    @Override
    public int getCount() {
      return mAlbums.size();
    }

    @Override
    public Object getItem(int position) {
      return mAlbums.get(position);
    }

    @Override
    public long getItemId(int position) {
      //return 0;
      //return position;
      return mAlbums.get(position).hashCode();
    }

    @Override
    public boolean hasStableIds() {
      return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      LayoutInflater inflater = getLayoutInflater();
      View row = inflater.inflate(R.layout.album_row, null);
      TextView text = (TextView) row.findViewById(R.id.text);
      Album album = mAlbums.get(position);
      text.setText(album.getName());

      return(row);
    }

    @Override
    public int getItemViewType(int position) {
      return 1;
    }

    @Override
    public int getViewTypeCount() {
      return 1;
    }

    @Override
    public boolean isEmpty() {
      return getCount() == 0;
    }

    @Override
    public boolean areAllItemsEnabled() {      
      return true;
    }

    @Override
    public boolean isEnabled(int position) {
      return true;
    }

  }

}
