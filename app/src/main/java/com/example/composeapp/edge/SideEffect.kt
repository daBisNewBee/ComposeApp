package com.example.composeapp.edge

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

// 模拟：参数变化时，原来的回调会被取消丢失！
@Composable
fun LaunchEffect_Params_Caller() {
    var param by remember { mutableStateOf(1) }

    Column {
        Text(text = "Current param: $param")
        Button(onClick = {param++}) {
            Text(text = "Click to Increase Param")
        }
        LaunchEffect_Params(param, {
            println("Callback --> $param ")
        })
    }
}

@Composable
fun LaunchEffect_Params(param: Int, callback: ()->Unit) {
    LaunchedEffect(param) {
        println("Start --> $param ")
        try {
            delay(3000L)
            println("End --> $param ")
            callback()
        } catch (e: Exception) {
            println("Task cancelled! --> $param e:$e")
        }
    }
}

@Composable
fun LaunchedEffect_DoOnce(callback: () -> Unit) {
    // currentCallback可以保证永远是指向的是最新的callback参数。避免新的callback 丢失问题
    val currentCallback by rememberUpdatedState(callback)
    // 两个作用：1. 让你在Composable函数中的某些代码只执行一遍; 2. 提供一个协程作用域
    LaunchedEffect(Unit) {
        // 这里开始协程作用域
        println("Start --> ")
        delay(1000)
        // 对程序进行初始化
        println("End delay")
        currentCallback()
    }
    MainScreen()
}

/*
举个例子，抛出来问题：什么是Side Effect?
Side Effect指的就是，在一个Composable函数的内部发生了超出其作用域的状态变更。

Compose重组可能具备哪些特性?
1. 重组会尽可能跳过不必要的代码
2. 重组会导致Composable函数频繁重复执行
3. Composable函数可能会并行运行
4. Composable函数可以以任意顺序执行
 */
private var mInit = false

@Composable
fun MyApp() {
    Initialize()
    MainScreen()
}

@Composable
fun Initialize() {
    mInit  = true
//    上述对mInit变量的操作其实就是属于超出了各个Composable函数作用域的状态变更，因此一定会发生Side Effect。
}

@Composable
fun MainScreen() {
    // 要求一定要在Initialize后执行，不满足“Compose函数可以并行”的要求
    if (mInit) {
        // .....
    }
}