package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.composeapp.edge.Fundamental

/**
 * "Jetpack Compose 教程"
 * https://developer.android.google.cn/jetpack/compose/tutorial
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Fundamental()
        }
    }
}

