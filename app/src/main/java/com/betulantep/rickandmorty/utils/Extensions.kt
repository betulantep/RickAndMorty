package com.betulantep.rickandmorty.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageView
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun Int.dpToPx(context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return this * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun View.hide(){
    this.visibility = View.GONE
}
fun View.show(){
    this.visibility = View.VISIBLE
}

fun ImageView.changeHeight(context: Context,height : Int){
    this.layoutParams.height = height.dpToPx(context)
    this.requestLayout()
}

fun Navigation.actionFragment(view: View,navDirections: NavDirections){
    this.findNavController(view).navigate(navDirections)
}
fun Navigation.actionFragment(view: View,id: Int){
    this.findNavController(view).navigate(id)
}
