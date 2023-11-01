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
import kotlinx.coroutines.launch

/**
 * @param labels :Pair<id,text> pair包含着id值和文本
 */
@Composable
fun PreferenceRadioGroup(
    keyName :String,
    labels: List<String>,
    contentPadding: PaddingValues = PaddingValues(
        start = 8.dp,
        top = 8.dp,
        bottom = 12.dp,
        end = 8.dp
    ),
    changed: (newIndex: Int) -> Unit = {},
) {
    val key = intPreferencesKey(keyName)
    val scope = rememberCoroutineScope()
    val dataStore = LocalPrefs.current.dataStore()
    var selectedPos by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = Unit, block = {
        dataStore.asDataFlow(key,0).collect {
            selectedPos = it
            changed(it)
        }
    })

    fun write(pos: Int) {
        scope.launch {
            dataStore.edit {
                it[key] = pos
            }
        }
    }

    Column {
        repeat(labels.size) { pos: Int ->
            PreferenceSingleChoiceItem(
                text = labels[pos],
                selected = (pos==selectedPos),
                contentPadding=contentPadding,
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
    contentPadding: PaddingValues = PaddingValues(
        start = 8.dp,
        top = 8.dp,
        bottom = 12.dp,
        end = 8.dp
    ),
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.selectable(
            selected = selected, onClick = onClick
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RadioButton(
                selected = selected,
                onClick = { onClick.invoke() },
                modifier = Modifier
                    .padding()
                    .clearAndSetSemantics { },
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                text = text,
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis
            )

        }
    }
}