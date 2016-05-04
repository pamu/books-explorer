package com.rxbytes.books.explorer.ui.main

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import macroid.FullDsl._
import macroid._


class MainActivity
  extends AppCompatActivity
    with Contexts[FragmentActivity]
    with Layout
    with IdGeneration {
  self =>

  override def onCreate(savedInstanceState: Bundle) = {
    super.onCreate(savedInstanceState)

    setContentView(layout)
    toolBar foreach setSupportActionBar
    getSupportActionBar.setDisplayHomeAsUpEnabled(true)
    getSupportActionBar.setHomeButtonEnabled(true)

  }
}
