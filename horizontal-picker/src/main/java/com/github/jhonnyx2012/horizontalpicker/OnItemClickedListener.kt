package com.github.jhonnyx2012.horizontalpicker

import android.view.View

/**
 * Created by jhonny on 02/03/2017.
 */
interface OnItemClickedListener {
    fun onClickView(view: View, adapterPosition: Int)
}