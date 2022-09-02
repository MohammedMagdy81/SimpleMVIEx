package com.example.mvi

sealed class MainViewState {
    // Ideal
    object Ideal:MainViewState()

    // Number
    data class Number(val number :Int):MainViewState()

    //Error
    data class Error(val error :String):MainViewState()
}