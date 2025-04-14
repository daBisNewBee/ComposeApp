package com.example.composeapp.edge

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf

val LocalExample = compositionLocalOf { "Default value" }

val LocalExampleNoProvider = compositionLocalOf { "Default value with no provdier" }

// 带state，更高阶一点的用法
val LocalCounter = staticCompositionLocalOf { mutableStateOf(0) }