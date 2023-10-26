package com.kiylx.composetemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.kiylx.compose_lib.common.animatedComposable
import com.kiylx.compose_lib.pages.appearance.AppearancePreferences
import com.kiylx.compose_lib.pages.appearance.DarkThemePreferences
import com.kiylx.compose_lib.theme3.DynamicTheme
import com.kiylx.composetemplate.ui.theme.ComposeTsetTheme
val LocalNavController = staticCompositionLocalOf<NavHostController> { error("没有提供值！") }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            CompositionLocalProvider(
                LocalNavController provides navController,
            ) {
                DynamicTheme {
                    HomeEntity(navController = navController)
                }
            }
        }
    }
    @Composable
    private fun HomeEntity(navController: NavHostController) {
        //构建导航
        NavHost(navController = navController, startDestination = Route.HOME) {
            animatedComposable(route = Route.HOME) {
              //调用home的composable函数，以展示home页面
            }
            //构建设置页面的嵌套导航
            buildSettingsPage(navController)
        }
    }
    /**
     * 主页面导航到设置页面，设置页面有很多的其他页面导航，
     * 所以将设置页面的导航在这里设置。即嵌套导航。
     */
    private fun NavGraphBuilder.buildSettingsPage(
        navController: NavHostController
    ) {
        navigation(startDestination = Route.SETTINGS_PAGE, route = Route.SETTINGS) {
            animatedComposable(Route.SETTINGS_PAGE) {
                //展示设置页面，设置页面还可以导航到主题设置页面
                //SettingPage(navController)
            }
            //主题设置
            animatedComposable(Route.THEME) {
                AppearancePreferences(
                    back = { navController.popBackStack() },
                    navToDarkMode = { -> navController.navigate(Route.DARK_THEME) },
                    window=window
                )
            }
            animatedComposable(Route.DARK_THEME) {
                DarkThemePreferences(window=window) { navController.popBackStack() }
            }
        }
    }

}
