package com.kiylx.compose_lib.pref_component

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.kiylx.compose_lib.common.asDataFlow
import kotlinx.coroutines.launch

private const val TAG = "PrefCheckBoxGroup"


/**
 * @param labels :Pair<id,text> pair包含着id值和文本
 */
@Composable
fun PreferenceCheckBoxGroup(
    keyName: String,
    labels: List<String>,
    contentPadding: PaddingValues = PaddingValues(
        start = 8.dp,
        top = 8.dp,
        bottom = 12.dp,
        end = 8.dp
    ),
    enabled: Boolean = true,
    changed: (newIndex: List<Int>) -> Unit = {},
) {
    fun getStr(list: List<Int>): String {
        return list.joinToString(",")
    }

    fun genList(s: String): List<Int> {
        return try {
            if (s.isEmpty()) {
                emptyList()
            } else {
                val tmp = s.split(",").map { it.toInt() }
                tmp
            }
        } catch (e: Exception) {
            Log.e(TAG, "genList: $e")
            emptyList()
        }

    }

    val key = stringPreferencesKey(keyName)
    val scope = rememberCoroutineScope()
    val dataStore = LocalPrefs.current.dataStore()
    val selectedList = remember {
        mutableStateListOf<Int>();
    }
    //读取prefs
    LaunchedEffect(key1 = Unit, block = {
        dataStore.asDataFlow(key, "").collect { s ->
            selectedList.clear()
            selectedList.addAll(genList(s))//根据字符串重新生成list
            changed(selectedList)
        }
    })

    //写入prefs
    fun write() {
        scope.launch {
            dataStore.edit {
                it[key] = getStr(selectedList)
            }
        }
    }

    Column {
        repeat(labels.size) { pos: Int ->
            val checked by remember {
                derivedStateOf {
                    selectedList.contains(pos)
                }
            }
            PreferenceCheckBoxItem(
                text = labels[pos],
                selected = checked,
                contentPadding = contentPadding,
                enabled = enabled,
                onCheckedChanged = { bol ->
                    if (bol) {
                        selectedList.add(pos)
                    } else {
                        selectedList.remove(pos)
                    }
                    write()
                }
            )
        }
    }
}

@Composable
fun PreferenceCheckBoxItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    enabled: Boolean,
    contentPadding: PaddingValues = PaddingValues(
        start = 8.dp,
        top = 8.dp,
        bottom = 12.dp,
        end = 8.dp
    ),
    onCheckedChanged: (newValue: Boolean) -> Unit
) {
    var checked = selected
    Surface(
        modifier = Modifier.toggleable(
            value = checked,
            enabled = enabled,
        ) {
            checked = it
            onCheckedChanged(checked)
        }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Checkbox(
                checked = checked,
                onCheckedChange = {
                    checked = it
                    onCheckedChanged(checked)
                },
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