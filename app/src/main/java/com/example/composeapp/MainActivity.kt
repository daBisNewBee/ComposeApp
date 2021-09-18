package com.example.composeapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeapp.ui.theme.ComposeAppTheme

/**
 * "Jetpack Compose 教程"
 * https://developer.android.google.cn/jetpack/compose/tutorial
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                ComposeAppTheme{
                    MessageCard(Message("Android","JetPack"))
                    Conversation(message = listOf(
                        Message("11","11-1\n11-1"),
                        Message("22","22-2"),
                        Message("22","22-3"),
                        Message("22","22-4"),
                        Message("22","22-5"),
                        Message("22","22-6"),
                    ),)
                    Text(text = "这是自定义Text")
                    Greeting(name = "这是组合函数")
                }
            }
        }
//        setContent {
//            ComposeAppTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
//                    Greeting("Android 1234")
//                }
//            }
//        }
    }
}

data class Message(val author:String, val body:String)

@Composable
fun MessageCard(msg:Message) {
    Row {
       Image(
           painter = painterResource(id = R.drawable.ic_launcher_background),
           contentDescription = "this contentDescription",
           modifier = Modifier
               .size(40.dp)
               .clip(CircleShape)
               .border(1.5.dp, MaterialTheme.colors.primary, CircleShape)
       )
        Spacer(modifier = Modifier.width(8.dp))
        // 在展开消息时显示动画效果
        var isExpanded by remember { mutableStateOf(false) }
       // TODO: 背景颜色
//        var surfaceColor: Color by animateColorAsState(
//            if (isExpanded) MaterialTheme.colors.surface else
//                MaterialTheme.colors.surface)
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                Text(
                    text = msg.body,
                    // 内边距
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

// 创建消息列表
@Composable
fun Conversation(message: List<Message>) {
    LazyColumn{
        items(message) {it ->
            MessageCard(msg = it)
        }
    }
}

//定义可组合函数
@Composable
fun Greeting(name: String) {
    Text(text = "Hello Hello Haha fuck you $name!")
}

// 深色模式：创建多个预览，也可以向同一个函数中添加多个注解
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)

//在 Android Studio 中预览函数
//@Preview(showBackground = true)
@Composable
fun PreviewMessageCard() {
    ComposeAppTheme {
        MessageCard(msg = Message("preview-author", "preview-body"))
//        Greeting("Android 222")
//        Text(text = "          1234565")
    }
}

@Preview
@Composable
fun PreviewConversation() {

}