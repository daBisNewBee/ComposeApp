package com.example.composeapp.edge

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

/*
State hoisting: 状态提升
一个状态提升的例子，

通常意义上来讲，像这种状态向下传递、事件向上传递的编程模式，我们称之为单向数据流模式（Unidirectional Data Flow）。
而状态提升就是这种单向数据流模式在Compose中的具体应用。
 */

@Composable
fun CallCounter_Hoisting(modifier: Modifier = Modifier) {
    // 在基类的上一层，这里，记录状态
    var count by rememberSaveable { mutableStateOf(0) }
    var doubleCount by rememberSaveable { mutableStateOf(0) }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        // 由于定义了无状态的几类函数，这里可以实现多种算法的计数器
        Counter_Hositing_Base(count, onIncrement = { count++ }, modifier)
        Counter_Hositing_Base(doubleCount, onIncrement = { doubleCount += 2 }, modifier)
    }
}

// 尽可能在在基类使用“参数传递”、“无状态”的函数，增加复用性
@Composable
fun Counter_Hositing_Base(count: Int, onIncrement: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "$count",
            fontSize = 50.sp,
        )
        Button(onClick = onIncrement) {
            Text(text = "Click me", fontSize = 26.sp)
        }
    }
}


/*
有状态的Composable函数：
根据Google给出的最佳实践准则，有状态的Composable函数通常在复用性和可测试性方面都会表现得比较差。
 */
@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableStateOf(0) }

    Text(text = "$count", fontSize = 50.sp)
}

/*
无状态的Composable函数：
当我们编写Composable函数时，最理想的情况就是尽可能地让它成为一个无状态的Composable函数。
 */
@Composable
fun StatelessCounter(count: Int, modifier: Modifier = Modifier) {
    Text(text = "$count", fontSize = 50.sp)
}

/*
   Compose更新UI界面的核心逻辑 = "刷新界面" = called "重组" in compose
 */
@Composable
fun Counter_State(modifier: Modifier = Modifier) {
    // count is MutableState<Int>
    val count = remember { mutableStateOf(0) } //1. 使用"State"来追踪某个数据之后
    // 4. remember函数的作用是让其包裹住的变量在重组的过程中得到保留，从而就不会出现变量被重新初始化的情况了。否则，每次重组进来，count都会被初始化，之前的设置无效
    println("Get count: ${count.value}")

    // count is Int
    var count2 by remember { mutableStateOf(0) } // 注意：这里使用了"委托语法"的好处：直接对变量操作，不用再set or get *.value!

    // 推荐！rememberSaveable 包裹的数据可以跨越Activity重建，因此可以在手机横竖屏切换的时候保存下来！
    var count3 by rememberSaveable { mutableStateOf(0) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "${count.value}__${count2}__${count3}", // 3. Compose就会自动触发重组来更新所有读取这个值的地方，从而界面就会得到更新了
            fontSize = 50.sp,
        )
        Button(onClick = {
            count.value++ // 2. 当这个数据的值发生变化
            count2++
            count3++
            println("Onclick set count: ${count.value}, count2: $count2, count3: $count3")
        }) {
            Text(text = "Click me", fontSize = 26.sp)
        }
    }
}

// 引入“State”的好处，为什么下面这个设置无效？
@Composable
fun Counter_Wrong(modifier: Modifier = Modifier) {
    var count = 0
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$count",
            fontSize = 50.sp,
        )
        Button(onClick = {
            count++
        }) {
            Text(text = "Click me", fontSize = 26.sp)
        }
    }
}