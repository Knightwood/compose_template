package com.kiylx.compose_lib.pref_component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.kiylx.compose_lib.common.asDataFlow
import com.kiylx.compose_lib.pref_component.Typography.preferenceMediumTitle
import com.kiylx.compose_lib.theme3.applyOpacity
import kotlinx.coroutines.launch

/**
 * @param labels :Pair<id,text> pair包含着id值和文本
 */
@Composable
fun PreferenceRadioGroup(
    keyName :String,
    labels: List<String>,
    enabled: Boolean = true,
    dependenceKey:String?=null,
    changed: (newIndex: Int) -> Unit = {},
) {
    val scope = rememberCoroutineScope()
    val prefStoreHolder = LocalPrefs.current
    val pref =prefStoreHolder.getReadWriteTool(keyName = keyName, defaultValue = 0)
    //注册自身节点，并且获取目标节点的状态
    val dependenceState = prefStoreHolder.getDependence(keyName,enabled,dependenceKey).enableState

    var selectedPos by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit, block = {
        pref.read().collect {
            selectedPos = it
            changed(it)
        }
    })

    fun write(pos: Int) {
        scope.launch {
            pref.write(pos)
        }
    }

    Column {
        repeat(labels.size) { pos: Int ->
            PreferenceSingleChoiceItem(
                text = labels[pos],
                enabled=dependenceState.value,
                selected = (pos==selectedPos),
                onClick = {
                   write(pos)
                }
            )
        }
    }
}

@Composable
fun PreferenceSingleChoiceItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.selectable(
            enabled=enabled,
            selected = selected, onClick = onClick
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = Dimens.all.horizontal.dp, vertical =Dimens.small_s.dp ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = selected,
                enabled=enabled,
                onClick = { onClick.invoke() },
                modifier = Modifier
                    .clearAndSetSemantics { },
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = Dimens.text.start.dp,end =Dimens.text.end.dp),
                text = text,
                maxLines = 1,
                style = preferenceMediumTitle,
                color =MaterialTheme.colorScheme.onSurface.applyOpacity(enabled) ,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}