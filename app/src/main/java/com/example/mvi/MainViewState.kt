package com.example.mvi

// list of all views that displays for user
sealed class MainViewState {

    // ideal
    object Ideal:MainViewState()
    // number
    data class NumberAdded(val number:Int):MainViewState()
    // error
    data class Error(val message:String):MainViewState()
}