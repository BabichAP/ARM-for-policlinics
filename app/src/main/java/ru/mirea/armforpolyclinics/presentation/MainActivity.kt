package ru.mirea.armforpolyclinics.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.mirea.armforpolyclinics.R
import ru.mirea.armforpolyclinics.data.TokenManager
import ru.mirea.armforpolyclinics.presentation.fragments.LoginFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TokenManager.setContext(applicationContext)
    }


}