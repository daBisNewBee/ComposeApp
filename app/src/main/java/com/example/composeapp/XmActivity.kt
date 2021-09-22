package com.example.composeapp

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

/**
 *
 * 大前端的核心两个诉求：
 *  在跨平台、跨应用上的统一：一份代码实现复用！
 *  1. 跨平台：Android、IOS、IOT
 *  2. 跨应用：单个平台上，不同应用的统一：app、浏览器等
 *  但是统一了过后，UI性能问题就来了，比如h5，
 *  因此，出现了rn、flutter，做到了即统一、性能友好
 *  由于上述都为"声明式编程"，因此"Compose"的出现让"命令式编程"的原生开发的也对齐了"声明式编程"，
 *  表明了谷歌的大前端的态度
 *
 *
 * 大前端：
 *  所有前端的统称；
 *  跨平台：最大的特点在于一次开发，同时适用于所有平台
 *
 * 大前端的几个阶段：
 *
 * 1. h5 + webView
 *  页面渲染：webview渲染：H5+JS 通过 webview（仍然是系统的控件）实现；
 *  需要Bridge的内容：系统能力
 *  缺点：性能差
 *  TODO: 由于node的出现，前端工程师不需要依赖于后端程序而直接运行?
 *
 *
 * 2. react Native、weex、快应用
 *    Facebook的RN：
 *      背景：解决webView的性能问题
 *      原理：js 的数据结构 map 成 native 的数据结构；执行逻辑还是在js上
 *      页面渲染: 原生渲染，比h5好很多
 *      需要Bridge的内容：系统能力、系统Widget
 *      语言：react.js
 *      优点：动态化较好，支持热更新
 *      缺点：渲染时需要JavaScript和原生之间通信，因此对bridge依赖较重的场合性能差。
 *           ps：拖动
 *           JavaScript为脚本语言，执行时需要JIT，执行效率和AOT代码仍有差距
 *
 *    阿里的WEEX：
 *      思想及原理和React Native类似，最大的不同是语法层面：react.js和vue.js
 *      语言：vue.js
 *
 *    快应用：
 *      背景：华为、小米、OPPO、魅族等国内9大主流手机厂商共同制定的轻量级应用标准，对标"微信小程序"
 *      语言：js(不支持react、vue)
 *      优点：包体积小，快速分发(渲染/排版引擎是集成到ROM中，而rn等是将渲染/排版引擎集成到app中)
 *
 *
 * 3. flutter
 *      自渲染引擎，效率适用于"强展示类的应用"
 *      自渲染引擎：不同平台实现一个统一接口的渲染引擎来绘制UI，
 *      需要Bridge的内容: 无！！
 *      缺点：动态性差，需要AOT模式提前编译
 *      Skia作为其2D渲染引擎：Android系统内置，而IOS没有，所以iPA会比apk大一些
 *
 *
 * Compose 与 Flutter：
 *  区别：
 *      Compose：
 *          a. 声明式编程，解决"当前View的架构体系不能再继续适应申明式编程的范式而做出的重构"；
 *          b. Android团队搞出来的；
 *          c. "原生开发"范围内，因此可以借助原生的优势：协程、Flow，JetPack组件都可以提供给Compose，
 *           以至于状态管理、数据流的问题迎刃而解，特别是ConstraintLayout和MotionLayout的Compose化，让原生的布局编程相比Flutter有了质的飞跃。
 *          d. 让原生开发，了解了"什么是声明式编程"，为了后面更好的为大前端统一做准备？
 *      Flutter：
 *          a. 解决的是"跨平台问题"；
 *          b. Chrome团队搞出来的
 *  相同：
 *      都是声明式编程，风格类似；
 *      都是"编译器加上一个 Skia 的工作模式"
 *
 * 推测：
 *  Android开发的趋势为大前端；
 *  大前端的特点为：声明式UI
 *  因此，Android开发的趋势：为"响应式编程+声明式UI"为主线；
 *  对原生开发来说，技术框架为：Kotlin + LiveData + Compose + （其他 JetPack 功能性组件如 Room 等）
 *
 *
 * "原生渲染和webview渲染"：
 *  a. 原生的渲染方式：
        view->layout->renderNode ->合成->GPU渲染

    b. webview目前渲染方式：
        html->dom tree ->render tree ->render layer + 栅格化 ->合成->gpu渲染

    1：native layout算法比浏览器快；
    2：JS Thread、DOM Thread、Native MainThread，并行化进行；
    3：webview本身其实更像一个容器或者盒子，它是装在在OS里面的一个套件而已，程序（HTML+js）要到OS必须走出这个盒子,
        多走了几步，自然比人家慢一些，而且关键还不是多走一步；
 *
 *
 * 参考：
 * 看透这几点，你就懂大前端了：
 * https://zhuanlan.zhihu.com/p/346858995
 *
 * Android 大前端进化史：
 * https://blog.csdn.net/a31081314/article/details/88342552
 *
 * Android Compose UI 自学总结:
 * https://www.jianshu.com/p/aecc9b99253a
 *
 */
class XmActivity : ComponentActivity() {

    @Composable
    fun MyButton(btnText:String, callback:() -> Unit) {
        Button(onClick = callback) {
            Text(text = btnText)
        }
    }

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

            }
        }
    }
}