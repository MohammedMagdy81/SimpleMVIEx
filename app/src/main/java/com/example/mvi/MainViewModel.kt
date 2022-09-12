package com.example.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.Exception

class MainViewModel : ViewModel() {

    val channel = Channel<MainIntent>(Channel.UNLIMITED)   // open link between view model and activity

    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Ideal)
    val viewState: StateFlow<MainViewState> get() = _viewState
    private var number= 0


    init {
        processIntent()
    }

    // process
    private fun processIntent() {
        viewModelScope.launch {
            channel.consumeAsFlow().collect{
                when(it){
                    is MainIntent.AddNumber->{
                        addNumber()
                    }

                }

            }
        }
    }

    // reduce
    private fun addNumber() {
        viewModelScope.launch {
           _viewState.value= try {
               MainViewState.NumberAdded(number++)
           }catch (e:Exception){
               MainViewState.Error(e.localizedMessage!!)
           }
        }
    }
}




