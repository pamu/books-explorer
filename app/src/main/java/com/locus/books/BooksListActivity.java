package com.locus.books;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

/**
 * Created by pnagarjuna on 24/04/16.
 */
public class BooksListActivity extends Activity {
    private RecyclerView mRecyclerView;
    private ProgressWheel wheel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        mRecyclerView = (RecyclerView) findViewById(R.id.books_list);
        if (getIntent() != null) {
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            String searchText = extras.getString(MainActivity.SEARCH_TEXT);
            loadBooks(searchText, new Callback<List<Book>>() {
                @Override
                public void onSuccess(List<Book> books) {

                }

                @Override
                public void onFailure(Exception ex) {

                }
            });
        } else finish();
    }

    public void loadBooks(final String searchText, final Callback<List<Book>> callback) {

    }

}
