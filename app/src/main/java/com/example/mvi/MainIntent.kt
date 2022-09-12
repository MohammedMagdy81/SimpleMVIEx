package com.example.mvi

sealed class MainIntent {
    object AddNumber : MainIntent()// first intention the user want .
}