package com.example.mvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var txtNumber: TextView
    lateinit var addNumber: Button
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        txtNumber = findViewById(R.id.text_number)
        addNumber = findViewById(R.id.btnAdd)
        render()

        addNumber.setOnClickListener {
            // send intent to View Model
            lifecycleScope.launch {
                viewModel.channel.send(MainIntent.AddNumber)
            }
        }


    }

    private fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.viewState.collect {
                when (it) {
                    is MainViewState.NumberAdded -> {
                        txtNumber.text=it.number.toString()
                    }
                    is MainViewState.Ideal -> {
                        txtNumber.text = "Ideal Ya man "
                    }
                    is MainViewState.Error -> {
                        Toast.makeText(this@MainActivity,it.message, Toast.LENGTH_LONG).show()
                    }

                }
            }
        }
    }
}