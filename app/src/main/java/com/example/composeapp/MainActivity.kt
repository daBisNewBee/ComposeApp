package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import com.example.composeapp.edge.CallCounter_Hoisting
import com.example.composeapp.edge.Fundamental
import com.example.composeapp.edge.LaunchEffect_Params_Caller
import com.example.composeapp.edge.LaunchedEffect_DoOnce

/**
 * "Jetpack Compose 教程"
 * https://developer.android.google.cn/jetpack/compose/tutorial
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            Fundamental()
//            CallCounter_Hoisting(modifier = Modifier.fillMaxWidth())
//            LaunchedEffect_DoOnce()
            LaunchEffect_Params_Caller()
        }
    }
}

