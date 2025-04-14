package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.example.composeapp.edge.CallCounter_Hoisting
import com.example.composeapp.edge.Fundamental
import com.example.composeapp.edge.LaunchEffect_Params_Caller
import com.example.composeapp.edge.LaunchedEffect_DoOnce
import com.example.composeapp.edge.LocalExample

/**
 * "Jetpack Compose 教程"
 * https://developer.android.google.cn/jetpack/compose/tutorial
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // 一次定义，层层使用，避免显示传递参数
            CompositionLocalProvider(LocalExample provides "Hello world!") {
                // 只影响"Fundamental"的子树：在这个"Fundamental"树中，LocalExample 的值是 "Hello, World!"
                Fundamental()
            }
//            CallCounter_Hoisting(modifier = Modifier.fillMaxWidth())
//            LaunchedEffect_DoOnce()
            LaunchEffect_Params_Caller()
        }
    }
}

