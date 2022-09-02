package com.example.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.internal.ChannelFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel : ViewModel() {

    val channel= Channel<MainIntent>(Channel.UNLIMITED)
    private val _viewState= MutableStateFlow<MainViewState>(MainViewState.Ideal)
    val viewState: StateFlow<MainViewState> get() = _viewState

    init {
        processIntent()
    }

    private var number=0


    private fun addNumber(){
        viewModelScope.launch {
            _viewState.value=
                try {
                    MainViewState.Number(number++)
                }catch (e:Exception){
                    MainViewState.Error(e.message.toString())
                }
        }
    }

    private fun processIntent(){
        viewModelScope.launch {
            channel.consumeAsFlow().collect{intent->
                when(intent){
            is MainIntent.AddNumber->{
                    addNumber()
                    }
                }
            }
        }
    }
}