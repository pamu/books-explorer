package com.locus.books;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pnagarjuna on 24/04/16.
 */
public class BooksAdapter extends RecyclerView.Adapter<BookViewHolder> {
    private List<Book> bookList = new ArrayList<>();

    public BooksAdapter(List<Book> bookList) {
        this.bookList.addAll(bookList);
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new BookViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.bind(bookList.get(position));
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}

class Book {
    private String title;
    private String cover;
    private String authors;
    private String desc;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

class BookViewHolder extends RecyclerView.ViewHolder {
    private View view;
    public BookViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
    }
    public void bind(Book book) {
        ImageView cover = (ImageView) view.findViewById(R.id.cover);
        if (!TextUtils.isEmpty(book.getCover())) {
            Picasso.with(view.getContext())
                    .load(book.getCover())
                    .fit()
                    .centerInside()
                    .into(cover);
        }
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(book.getTitle());
        TextView authors = (TextView) view.findViewById(R.id.authors);
        authors.setText(book.getAuthors());
        TextView desc = (TextView) view.findViewById(R.id.desc);
        desc.setText(book.getDesc());
    }
}