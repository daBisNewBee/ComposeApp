package com.example.composeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.composeapp.edge.CallCounter_Hoisting
import com.example.composeapp.edge.Fundamental
import com.example.composeapp.edge.FundamentalWithProvider
import com.example.composeapp.edge.LaunchEffect_Params_Caller
import com.example.composeapp.edge.LaunchedEffect_DoOnce
import com.example.composeapp.edge.LocalExample
import com.example.composeapp.edge.MyScreenWithStateFlow
import com.example.composeapp.edge.MyViewModel
import kotlinx.coroutines.launch

/**
 * "Jetpack Compose 教程"
 * https://developer.android.google.cn/jetpack/compose/tutorial
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            FundamentalWithProvider()
//            CallCounter_Hoisting(modifier = Modifier.fillMaxWidth())
//            LaunchedEffect_DoOnce()
//            LaunchEffect_Params_Caller()
            MyScreenWithStateFlow()
        }
    }
}

