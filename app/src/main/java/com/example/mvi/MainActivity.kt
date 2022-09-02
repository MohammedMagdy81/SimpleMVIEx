package com.example.mvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var btnAdd:Button
    lateinit var textNumber:TextView

     private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel= ViewModelProvider(this).get(MainViewModel::class.java)
        btnAdd=findViewById(R.id.btnAdd)
        textNumber=findViewById(R.id.text_number)
        render()

        btnAdd.setOnClickListener {
            // send
            lifecycleScope.launch {
                viewModel.channel.send(MainIntent.AddNumber)
            }

        }
    }

    private fun render(){
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect{it->
                when(it){
                    is MainViewState.Error ->textNumber.text= it.error
                    is MainViewState.Ideal ->textNumber.text = "IDEAL"
                    is MainViewState.Number ->textNumber.text= it.number.toString()
                }
            }
        }

    }
}