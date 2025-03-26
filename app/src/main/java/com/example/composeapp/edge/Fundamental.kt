package com.example.composeapp.edge

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.composeapp.R
import com.example.composeapp.ui.theme.ComposeAppTheme

@Composable
fun Fundamental() {
    val scrollState = rememberScrollState() // 它是用于保证在手机横竖屏旋转的情况下滚动位置不会丢失的
    val configuration = LocalConfiguration.current

    // 只有在 “@Composable”中才能调用“@Composable”;
    // "@Composable" 函数名必须大写开头
    Box(modifier = Modifier.fillMaxSize()) {// 相当于"FrameLayout",
        Column(
            modifier = Modifier
                .verticalScroll(scrollState) // 使得内容支持滑动
                .height(1000.dp), // 需要限制高度，不然报错:"java.lang.IllegalStateException:
            // Vertically scrollable component was measured with an infinity maximum height constraints, which is disallowed"
//                        .height(configuration.screenHeightDp.dp)
            horizontalAlignment = Alignment.CenterHorizontally, // 指定Column当中的子控件在水平方向上的对齐方式, 有"verticalAlignment"吗？没有！
            verticalArrangement = Arrangement.SpaceEvenly // 注意这里有多种分布方式："让Column中的每个子控件平分Column在垂直方向上的空间"
        ) {
            ComposeAppTheme {
                MessageCard(Message("Android", "JetPack"))
                Conversation(
                    message = listOf(
                        Message("11", "11-1\n11-1"),
                        Message("22", "22-2"),
                        Message("22", "22-3"),
                        Message("22", "22-4"),
                        Message("22", "22-5"),
                        Message("22", "22-6"),
                    ),
                )
                Text(text = "这是自定义Text")
                Greeting(name = "这是组合函数")
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android 1234")
                }
                val context = LocalContext.current
                Button(onClick = {
                    Toast.makeText(context, "Button clicked!", Toast.LENGTH_SHORT).show()
                }, modifier = Modifier.align(Alignment.End)) { // 子项不同于父项Column的对齐方式
                    Text(
                        text = "This is button",
                        color = Color.White,
                        fontSize = 26.sp
                    )
                }
                TextField(
                    value = "", onValueChange = {

                    }, placeholder = {
                        Text(text = "Type something here")
                    }, colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White
                    )
                )
                SimpleWidgetColumn()
                CircularProgressIndicator(
                    color = Color.Green,
                    strokeWidth = 6.dp
                )
                LinearProgressIndicator(
                    color = Color.Blue,
                    backgroundColor = Color.Gray
                )
                Image(
                    // Also could import as "bitmap"
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "A dog image from drawable"
                )
                AsyncImage(
                    model = "https://www.runoob.com/wp-content/uploads/2025/02/vscode-deepseek-4.webp",
                    contentDescription = "This is Async load Image demo from library coil"
                )
            }
        }
    }
}

@Composable
fun SimpleWidgetColumn() {
    // 一个State的例子：解决了TextField输入无法上屏的问题
    var userInput by rememberSaveable { mutableStateOf("") }
    Column {
        TextFieldWidget(userInput, onValueChange = {newValue -> userInput = newValue})
    }
}

@Composable
fun TextFieldWidget(value:String, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    TextField(
        modifier = modifier,
        value = value, onValueChange = onValueChange, placeholder = {
            Text(text = "Type something here")
        }, colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        )
    )
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
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
    LazyColumn {
        items(message) { it ->
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
@Preview(showBackground = true)
@Composable
fun PreviewMessageCard() {
    ComposeAppTheme {
        MessageCard(msg = Message("preview-author", "preview-body"))
    }
}

@Preview
@Composable
fun PreviewConversation() {
}