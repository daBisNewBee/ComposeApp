package com.example.composeapp

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Date
import java.util.Timer

/**
 *
 */
class XmActivity : ComponentActivity() {

    private val _boxHeightFlow = MutableStateFlow("This is default value")

    @Composable
    fun MyButton(btnText:String, callback:() -> Unit) {
        Button(onClick = callback) {
            Text(text = btnText)
        }
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var count1 = 1
        var count2 = mutableStateOf(1)
        // "compose的modifier更友好，flutter写起来嵌套太多"
        setContent {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                println("update")
                // 为什么要用"remember"？不用行不行？
                var count3 by remember {
                    mutableStateOf(1)
                }

                Text(text = "常规写法")
                Row {
                    Text(text = "count1 = $count1")
                    MyButton(btnText = "累加count1") {
                        count1++
                    }
                }
                Divider()
                Text(text = "Compose mutableStateOf 写法")
                Row {
                    Text(text = "count2 = ${count2.value}")
                    MyButton(btnText = "累加count2") {
                        count2.value++
                    }
                }
                Divider()
                // 效果同上，写起来更简单
                Text(text = "Compose mutableStateOf 委托模式写法")
                Row {
                    Text(text = "count3 = $count3")
                    MyButton(btnText = "累加count3") {
                        count3++
                    }
                }
                Divider()
                Row {
                    Text(text= "MutableStateFlow = ${_boxHeightFlow.value}")
                    MyButton(btnText = "修改") {
                        val format = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z")
                        } else {
                            TODO("VERSION.SDK_INT < N")
                        }
                        val date = Date(System.currentTimeMillis())
                        _boxHeightFlow.value = format.format(date)
                    }
                }

            }
        }
    }
}