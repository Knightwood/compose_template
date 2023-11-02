package com.kiylx.composetemplate.testcompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CenterFocusWeak
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.kiylx.compose_lib.common.navigateExt2
import com.kiylx.compose_lib.common.setSavedStateResult
import com.kiylx.compose_lib.component.autoRippleAnimation
import com.kiylx.compose_lib.component.rememberRippleAnimationState
import com.kiylx.compose_lib.pref_component.CollapsePreferenceItem
import com.kiylx.compose_lib.pref_component.PreferenceCheckBoxGroup
import com.kiylx.compose_lib.pref_component.PreferenceItem
import com.kiylx.compose_lib.pref_component.PreferenceItemLargeTitle
import com.kiylx.compose_lib.pref_component.PreferenceRadioGroup
import com.kiylx.compose_lib.pref_component.PreferenceSlider
import com.kiylx.compose_lib.pref_component.PreferenceItemSubTitle
import com.kiylx.compose_lib.pref_component.PreferenceItemVariant
import com.kiylx.compose_lib.pref_component.PreferenceSwitch
import com.kiylx.compose_lib.pref_component.PreferenceSwitchWithContainer
import com.kiylx.compose_lib.pref_component.PreferenceSwitchWithDivider
import com.kiylx.compose_lib.pref_component.PreferencesCautionCard
import com.kiylx.compose_lib.pref_component.PreferencesHintCard
import com.kiylx.compose_lib.pref_component.PreferencesScope
import com.kiylx.compose_lib.pref_component.core.datastore.DataStorePreferenceHolder
import com.kiylx.compose_lib.pref_component.core.mmkv.MMKVPreferenceHolder
import com.kiylx.compose_lib.pref_component.core.prefs.OldPreferenceHolder
import com.kiylx.compose_lib.theme3.LocalDarkThemePrefs
import com.kiylx.compose_lib.theme3.findWindow
import com.kiylx.composetemplate.AppCtx
import com.kiylx.composetemplate.Route
import com.tencent.mmkv.MMKV

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

//            item {
//                Button(
//                    modifier = Modifier,
//                    onClick = {
//                        //设置动画效果
//                        if (isDark) {
//                            rippleAnimationState.animMode = AnimMode.shrink
//                        } else {
//                            rippleAnimationState.animMode = AnimMode.expend
//                        }
//                        //调用此方法执行动画
//                        rippleAnimationState.change() {
//                            //主题切换
//                            if (isDark) {
//                                scope.modifyDarkThemePreference(darkThemeMode = DarkThemePrefs.OFF)
//                            } else {
//                                scope.modifyDarkThemePreference(darkThemeMode = DarkThemePrefs.ON)
//                            }
//                        }
//                    }) {
//                    Text(text = "切换主题")
//                }
//            }
//            item {
//                PressIconButton(
//                    modifier = Modifier,
//                    onClick = {},
//                    icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = null) },
//                    text = { Text("Add to cart") }
//                )
//            }

            item {
                //1. 使用dataStore存储偏好值
//                  val holder=  DataStorePreferenceHolder.instance(
//                        dataStoreName = "test",
//                        ctx = AppCtx.instance
//                    )
                //1. 使用mmkv存储偏好值
//                   val holder= MMKVPreferenceHolder.instance(MMKV.defaultMMKV())
                //3. 使用sharedprefrence存储偏好值
                val holder = OldPreferenceHolder.instance(
                    AppCtx.instance.getSharedPreferences(
                        "ddd",
                        Context.MODE_PRIVATE
                    )
                )
                PreferencesScope(
                    holder
                ) {
                    //创建一个自定义节点
                    val node =holder.registerDependence("customNode",true)

                    PreferenceItem(title = "PreferenceItem")
                    PreferenceItemVariant(title = "PreferenceItemVariant")
                    PreferencesHintCard(title = "PreferencesHintCard")
                    PreferenceItemLargeTitle(title = "PreferenceItemLargeTitle")
                    PreferenceItemSubTitle(text = "PreferenceItemSubTitle")
                    PreferencesCautionCard(title = "PreferencesCautionCard")
                    PreferenceSwitch(
                        keyName = "bol",
                        title = "title",
                        dependenceKey = "Pref_Dependence_Node_Root"
                        description = "description"
                    ) { state ->
                        //这里获取并修改了自身对应节点的enable状态，
                        //只有依赖这个节点的会改变显示，
                        //但自身不会受到影响
                        holder.getDependence("bol")?.let {
                            it.enableState.value = state
                        }
                    }

                    CollapsePreferenceItem(
                        title = "title",
                        description = "description"
                    ) {

                        PreferenceSwitch(
                            keyName = "bol2",
                            title = "title",
                            dependenceKey = "bol", //依赖key为bol的状态
                            description = "description",
                            icon = Icons.Filled.CenterFocusWeak
                        ){
                            node.enableState.value=it
                        }
                        //依赖于自定义的节点
                        PreferenceSwitch(
                            keyName = "bol3",
                            title = "title",
                            dependenceKey = "customNode", //依赖key为customNode的状态
                            description = "description",
                            icon = Icons.Filled.CenterFocusWeak
                        )

                    }
                    PreferenceSwitchWithContainer(
                        keyName = "bol4",
                        dependenceKey = "customNode",
                        title = "Title ".repeat(2),
                        icon = null
                    )
                    PreferenceSwitchWithDivider(
                        keyName = "bol3",
                        title = "title",
                        dependenceKey = "customNode",
                        description = "description",
                        icon = Icons.Filled.CenterFocusWeak
                    )

                    PreferenceRadioGroup(
                        keyName = "radioGroup",
                        dependenceKey = "customNode",
                        labels = listOf(
                            "first",
                            "second"
                        ), changed = {
                            Log.d(TAG, "radio: ${it}")
                        }
                    )
                    PreferenceCheckBoxGroup(
                        keyName = "CheckBoxGroup",
                        dependenceKey = "customNode",
                        labels = listOf(
                            "first",
                            "second"
                        ), changed = {
                            Log.d(TAG, "checkbox: ${it.joinToString(",")}")
                        }
                    )
                    PreferenceSlider(
                        keyName = "slider",
                        dependenceKey = "customNode", //依赖key为customNode的状态
                        min = 0f,
                        max = 10f, steps = 9, value = 0f, changed = {
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



