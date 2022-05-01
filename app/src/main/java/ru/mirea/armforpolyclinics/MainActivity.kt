package ru.mirea.armforpolyclinics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*
import java.util.Queue
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView.text = "1"


        textView.text = "oldText"



    }

    suspend fun main () {
        textView.text = "newText"
        delay(10000)
    }

}