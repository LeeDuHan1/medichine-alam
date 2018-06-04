package com.example.lee.medichine_alarm;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;

public class mp3Search extends ListActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> artists = getArtists();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, artists);
        setListAdapter(adapter);
    }

    public List<String> getArtists() {
        List<String> list = new ArrayList<String>();
        String[] cursorColumns = new String[] {
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST
        };
        Cursor cursor = (Cursor) getContentResolver().query(
                MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,
                cursorColumns, null, null, null);

        if (cursor == null) {
            return list;
        }
        if (cursor.moveToFirst()) {
            int idColumn = cursor.getColumnIndex(MediaStore.Audio.Artists._ID);
            int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Artists.ARTIST);
            do
            {
                String artist = cursor.getString(artistColumn);
                list.add(artist);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}