package com.locus.books;

public interface Callback<T> {
    void onSuccess(T t);

    void onFailure(Exception ex);
}