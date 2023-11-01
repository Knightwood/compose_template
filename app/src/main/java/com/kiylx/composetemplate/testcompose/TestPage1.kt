package com.kiylx.composetemplate.testcompose

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusWeak
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.kiylx.compose_lib.common.navigateExt2
import com.kiylx.compose_lib.common.setSavedStateResult
import com.kiylx.compose_lib.component.PressIconButton
import com.kiylx.compose_lib.component.RippleAnimationState.AnimMode
import com.kiylx.compose_lib.component.autoRippleAnimation
import com.kiylx.compose_lib.component.rememberRippleAnimationState
import com.kiylx.compose_lib.pref_component.PreferenceCheckBoxGroup
import com.kiylx.compose_lib.pref_component.PreferenceItem
import com.kiylx.compose_lib.pref_component.PreferenceRadioGroup
import com.kiylx.compose_lib.pref_component.PreferenceSlider
import com.kiylx.compose_lib.pref_component.PreferenceItemSubTitle
import com.kiylx.compose_lib.pref_component.PreferenceSwitch
import com.kiylx.compose_lib.pref_component.PreferenceSwitchWithContainer
import com.kiylx.compose_lib.pref_component.PreferencesCautionCard
import com.kiylx.compose_lib.pref_component.PreferencesScope
import com.kiylx.compose_lib.theme3.DarkThemePrefs
import com.kiylx.compose_lib.theme3.LocalDarkThemePrefs
import com.kiylx.compose_lib.theme3.ThemeHelper.modifyDarkThemePreference
import com.kiylx.compose_lib.theme3.findWindow
import com.kiylx.composetemplate.AppCtx
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
    val window = LocalContext.current.findWindow() ?: throw IllegalArgumentException()
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .autoRippleAnimation(window, rippleAnimationState)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Button(onClick = {
                    navController.navigateExt2(Route.SETTINGS, block = {
                        this["arg1"] = "vvv"
                    })
                }) {
                    Text(text = "前往SecondPage")
                }
            }

            item {
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
                        rippleAnimationState.change() {
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
            }
            item {
                PressIconButton(
                    modifier = Modifier,
                    onClick = {},
                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = null) },
                    text = { Text("Add to cart") }
                )
            }

            item {
                PreferencesScope(dataStoreName = "test", ctx = AppCtx.instance) {
                    PreferenceItem(title = "TITLE")
                    PreferenceSwitch(
                        keyName = "bol",
                        title = "title",
                        description = "description"
                    )
                    PreferenceSwitch(
                        keyName = "bol2",
                        title = "title",
                        description = "description",
                        icon=Icons.Filled.CenterFocusWeak
                    )
                    PreferenceItemSubTitle(text = "subTitle")
                    PreferenceSwitchWithContainer(
                        keyName = "bol2",
                        title = "Title ".repeat(2),
                        icon = null
                    )
                    PreferenceItem(title = "PreferenceItem")
                    PreferenceRadioGroup(
                        keyName = "radioGroup",
                        labels = listOf(
                            "first",
                            "second"
                        ), changed = {
                            Log.d(TAG, "radio: ${it}")
                        }
                    )
                    PreferencesCautionCard(title = "PreferencesCautionCard")

                    PreferenceCheckBoxGroup(
                        keyName = "CheckBoxGroup",
                        labels = listOf(
                            "first",
                            "second"
                        ), changed = {
                            Log.d(TAG, "checkbox: ${it.joinToString(",")}")
                        }
                    )
                    PreferenceSlider(
                        keyName = "slider", min = 0f,
                        max = 10f, steps = 9, value = 0f,changed={
                            Log.d(TAG, "slider: $it")
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun SecondPage(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column {
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



