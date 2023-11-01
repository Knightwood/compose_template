package com.kiylx.compose_lib.pref_component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kiylx.compose_lib.common.asDataFlow
import com.kiylx.compose_lib.theme3.ThemeHelper.modifyDarkThemePreference
import kotlinx.coroutines.launch

class Slider {
}

/**
 * @param steps 将min到max平均分为几份
 */
@Composable
fun PreferenceSlider(
    keyName: String,
    min: Float,
    max: Float,
    steps: Int,
    value: Float,
    enabled: Boolean = true,
    changed: (newIndex: Float) -> Unit = {},
) {
    val key = floatPreferencesKey(keyName)
    val dataStore = LocalPrefs.current.dataStore()
    val scope = rememberCoroutineScope()
    var progress by remember {
        mutableFloatStateOf(value)
    }

    //读取prefs
    LaunchedEffect(key1 = Unit, block = {
        dataStore.asDataFlow(key, min).collect { s ->
            progress = s
            changed(progress)
        }
    })

    //写入prefs
    fun write(value: Float) {
        scope.launch {
            dataStore.edit {
                it[key] = value
            }
        }
    }

    Slider(
        value = progress,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        onValueChange = {
            progress = it
        },
        steps = steps,
        valueRange = min..max, onValueChangeFinished = {
            write(progress)
        }
    )

}