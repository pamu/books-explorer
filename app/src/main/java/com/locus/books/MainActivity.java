package com.locus.books;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText searchBox;
    private Button btn;
    public final static String SEARCH_TEXT = "search_text";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchBox = (EditText) findViewById(R.id.search_text);
        btn = (Button) findViewById(R.id.search_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = searchBox.getEditableText().toString();
                if (!TextUtils.isEmpty(text)) {
                    Intent intent = new Intent(getApplicationContext(), BooksListActivity.class);
                    intent.putExtra(SEARCH_TEXT, text);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Text Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
