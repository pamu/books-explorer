package com.rxbytes.books.explorer.ui.main

import android.widget.{LinearLayout, TextView}
import com.rxbytes.books.explorer.ui.commons.ToolbarLayout
import macroid.ActivityContextWrapper
import macroid.FullDsl._

/**
  * Created by pnagarjuna on 05/05/16.
  */
trait Layout extends ToolbarLayout {

  def layout(implicit context: ActivityContextWrapper) = {
    getUi(
      l[LinearLayout](
        toolBarLayout(),
        w[TextView] <~ text("Hello world")
      ) <~ vertical
    )
  }

}