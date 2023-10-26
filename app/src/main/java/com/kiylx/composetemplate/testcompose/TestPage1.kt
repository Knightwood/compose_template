package com.kiylx.composetemplate.testcompose

import android.os.Bundle
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.kiylx.compose_lib.common.navigateExt2
import com.kiylx.compose_lib.common.setSavedStateResult
import com.kiylx.compose_lib.component.PressIconButton
import com.kiylx.compose_lib.component.RippleAnimationState.AnimMode
import com.kiylx.compose_lib.component.autoRippleAnimation
import com.kiylx.compose_lib.component.extendClick
import com.kiylx.compose_lib.component.metro.rememberTurnAnimState
import com.kiylx.compose_lib.component.metro.turnAnim
import com.kiylx.compose_lib.component.rememberRippleAnimationState
import com.kiylx.compose_lib.component.rippleAnimation
import com.kiylx.compose_lib.theme3.DarkThemePrefs
import com.kiylx.compose_lib.theme3.LocalDarkThemePrefs
import com.kiylx.compose_lib.theme3.ThemeHelper.modifyDarkThemePreference
import com.kiylx.compose_lib.theme3.findWindow
import com.kiylx.composetemplate.Route

const val TAG = "TestPage1"

@Composable
fun FirstPage(navController: NavController) {
    val scope = rememberCoroutineScope()
    val isDark = LocalDarkThemePrefs.current.isDarkTheme()
    val rippleAnimationState = rememberRippleAnimationState {
        animTime = 5000
        moveUpSystemBarInsts = true
    }
    val turnAnimState = rememberTurnAnimState()
    var offset by remember{
        mutableStateOf(Offset.Zero)
    }
    val window = LocalContext.current.findWindow() ?: throw IllegalArgumentException()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .extendClick {
                offset = it.position
            }
            //.rippleAnimation(window, rippleAnimationState)
            .turnAnim(turnAnimState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            repeat(5) {
                Text(text = "文本----${it + 1}")
            }
            Button(onClick = {
                navController.navigateExt2(Route.SETTINGS, block = {
                    this["arg1"] = "vvv"
                })
            }) {
                Text(text = "前往SecondPage")
            }
            Button(onClick = {
                turnAnimState.change(offset) {

                }
            }) {
                Text(text = "反转动画")
            }

            Button(
                modifier = Modifier,
                onClick = {
                    //设置动画效果
                    if (isDark) {
                        rippleAnimationState.animMode = AnimMode.shrink
                    } else {
                        rippleAnimationState.animMode = AnimMode.expend
                    }
                    //调用此方法执行动画
                    rippleAnimationState.change(offset) {
                        //主题切换
                        if (isDark) {
                            scope.modifyDarkThemePreference(darkThemeMode = DarkThemePrefs.OFF)
                        } else {
                            scope.modifyDarkThemePreference(darkThemeMode = DarkThemePrefs.ON)
                        }
                    }
                }) {
                Text(text = "切换主题")
            }

            PressIconButton(
                modifier = Modifier,
                onClick = {},
                icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = null) },
                text = { Text("Add to cart") }
            )
        }
    }
}

@Composable
fun SecondPage(navController: NavController) {
    Scaffold {
        Column(Modifier.padding(it)) {
            Button(onClick = {
                navController.setSavedStateResult("code1", Bundle().apply {
                    this.putString("data", "www")
                })
                navController.popBackStack()
            }) {
                Text(text = "返回")
            }
        }
    }

}



