package com.kiylx.compose_lib.pref_component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import com.kiylx.compose_lib.common.asDataFlow
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
    dependenceKey:String?=null,
    changed: (newIndex: Float) -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val prefStoreHolder = LocalPrefs.current
    val pref =prefStoreHolder.getReadWriteTool(keyName = keyName, defaultValue = value)
    //注册自身节点，并且获取目标节点的状态
    val dependenceState = prefStoreHolder.getDependence(keyName,enabled,dependenceKey).enableState

    var progress by remember {
        mutableFloatStateOf(value)
    }

    //读取prefs
    LaunchedEffect(key1 = Unit, block = {
        pref.read().collect { s ->
            progress = s
            changed(progress)
        }
    })

    //写入prefs
    fun write(value: Float) {
        scope.launch {
            pref.write(value)
        }
    }
    Surface {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.all.horizontal.dp, Dimens.small.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Slider(
                value = progress,
                enabled=dependenceState.value,
                modifier = Modifier.padding(
                    start = Dimens.text.start.dp,
                    end = Dimens.text.end.dp,
                ),
                onValueChange = {
                    progress = it
                },
                steps = steps,
                valueRange = min..max, onValueChangeFinished = {
                    write(progress)
                }
            )
        }
    }


}