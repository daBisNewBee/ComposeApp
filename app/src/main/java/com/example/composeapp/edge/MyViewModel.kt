package com.example.composeapp.edge

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel(){

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    // StateFlow 持有一个可观察的状态值，当值发生变化时，所有活跃的收集者都会收到更新。
    val uiState: StateFlow<UiState> = _uiState

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = UiState.Start("Prepare to start")
            println("Prepare to start")
            delay(2000)
            _uiState.value = UiState.Success("Data Loaded")
            println("Data Loaded")
        }
    }

    // 密封类
    sealed class UiState {
        // SingleInstance
        object Loading: UiState()
        data class Start(val info:String):UiState()
        data class Success(val data: String):UiState()
        data class Error(val message: String):UiState()
    }
}

@Composable
fun MyScreenWithStateFlow(viewModel: MyViewModel = viewModel()) { // 注意这里要用“viewModel() 委托”，为什么 MyViewModel() 不行？那就是每次都是新建实例了
    // 将 StateFlow 转换为 Compose State: "StateFlow 是 Kotlin 协程库（kotlinx.coroutines）中的一个组件，属于 Flow API 的一部分"
    val uiState = viewModel.uiState.collectAsState(initial = MyViewModel.UiState.Loading)

    println("uiState:${uiState.value}")
    Column(modifier = Modifier.padding(16.dp)) {
        when(uiState.value) {
            is MyViewModel.UiState.Success-> Text(text = (uiState.value as MyViewModel.UiState.Success).data)
            is MyViewModel.UiState.Start -> Text(text = (uiState.value as MyViewModel.UiState.Start).info)
            is MyViewModel.UiState.Error -> Text(text = (uiState.value as MyViewModel.UiState.Error).message)
            is MyViewModel.UiState.Loading -> Text(text = "Loading...")
        }
        Button(onClick = {viewModel.loadData()}) { Text("Reload data") }
    }

}