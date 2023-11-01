package com.kiylx.compose_lib.pref_component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.kiylx.compose_lib.R
import com.kiylx.compose_lib.common.asDataFlow
import com.kiylx.compose_lib.pref_component.Dimens.horizontal
import com.kiylx.compose_lib.pref_component.Dimens.vertical
import com.kiylx.compose_lib.theme3.LocalColorScheme
import com.kiylx.compose_lib.theme3.preferenceTitle
import kotlinx.coroutines.launch


/**
 * @param changed 将会得到新值
 */
@Composable
fun PreferenceSwitch(
    keyName: String,
    defaultValue: Boolean = false,
    title: String,
    description: String? = null,
    icon: Any? = null,
    enabled: Boolean = true,
    checkedIcon: ImageVector = Icons.Outlined.Check,
    changed: (newValue: Boolean) -> Unit = {},
) {
    val key = booleanPreferencesKey(keyName)
    val scope = rememberCoroutineScope()
    val dataStore = LocalPrefs.current.dataStore()
    var isChecked by remember {
        mutableStateOf(defaultValue)
    }
    LaunchedEffect(key1 = Unit, block = {
        dataStore.asDataFlow(key, defaultValue).collect {
            isChecked = it
            changed(it)
        }
    })

    fun write(checked: Boolean) {
        scope.launch {
            dataStore.edit {
                it[key] = checked
            }
        }
    }

    val thumbContent: (@Composable () -> Unit)? = if (isChecked) {
        {
            Icon(
                imageVector = checkedIcon,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    } else {
        null
    }
    Surface(
        modifier = Modifier.toggleable(
            value = isChecked,
            enabled = enabled,
            onValueChange = { checked ->
                write(checked)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.all.horizontal.dp, Dimens.all.vertical.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ParseIcon(
                icon = icon,
                enabled = enabled
            )
            MediumTextContainer(icon = icon) {
                PreferenceItemTitleText(text = title, enabled = enabled, maxLines = 2)
                if (description != null) PreferenceItemDescriptionText(
                    text = description,
                    enabled = enabled
                )
            }
            Switch(
                checked = isChecked,
                onCheckedChange = null,
                modifier = Modifier.padding(start = Dimens.end.start.dp, end = Dimens.end.end.dp),
                enabled = enabled,
                thumbContent = thumbContent
            )
        }
    }
}

/**
 * @param onClick 点击除了switch之外的部分，例如点击后，触发跳转另一个页面
 * @param changed 将得到switch的新值
 *
 */
@Composable
fun PreferenceSwitchWithDivider(
    keyName: String,
    defaultValue: Boolean = false,
    title: String,
    description: String? = null,
    icon: Any? = null,
    enabled: Boolean = true,
    isSwitchEnabled: Boolean = enabled,
    checkedIcon: ImageVector = Icons.Outlined.Check,
    changed: (it: Boolean) -> Unit = {},
    onClick: (() -> Unit) = {},
) {
    val key = booleanPreferencesKey(keyName)
    val scope = rememberCoroutineScope()
    val dataStore = LocalPrefs.current.dataStore()
    var isChecked by remember {
        mutableStateOf(defaultValue)
    }
    LaunchedEffect(key1 = Unit, block = {
        dataStore.asDataFlow(key, defaultValue).collect {
            isChecked = it
            changed(it)
        }
    })

    fun write(checked: Boolean) {
        scope.launch {
            dataStore.edit {
                it[key] = checked
            }
        }
    }

    val thumbContent: (@Composable () -> Unit)? = if (isChecked) {
        {
            Icon(
                imageVector = checkedIcon,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    } else {
        null
    }
    Surface(
        modifier = Modifier.clickable(
            enabled = enabled,
            onClick = onClick,
            onClickLabel = stringResource(id = R.string.open_settings)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal.dp, vertical.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ParseIcon(icon = icon, enabled = enabled)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                PreferenceItemTitleText(text = title, enabled = enabled)
                if (!description.isNullOrEmpty()) PreferenceItemDescriptionText(
                    text = description,
                    enabled = enabled
                )
            }
            Divider(
                modifier = Modifier
                    .height(Dimens.large_xx.dp)
                    .padding(horizontal = Dimens.small.dp)
                    .width(1f.dp)
                    .align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
            )
            Switch(
                checked = isChecked,
                onCheckedChange = { checked ->
                    write(checked)
                },
                modifier = Modifier
                    .padding(start = Dimens.end.start.dp, end = Dimens.end.end.dp)
                    .semantics {
                        contentDescription = title
                    },
                enabled = isSwitchEnabled,
                thumbContent = thumbContent
            )
        }
    }
}

@Composable
fun PreferenceSwitchWithContainer(
    keyName: String,
    defaultValue: Boolean = false,
    title: String,
    icon: ImageVector? = null,
    changed: (it: Boolean) -> Unit = {},
) {
    val key = booleanPreferencesKey(keyName)
    val scope = rememberCoroutineScope()
    val dataStore = LocalPrefs.current.dataStore()
    var isChecked by remember {
        mutableStateOf(defaultValue)
    }
    LaunchedEffect(key1 = Unit, block = {
        dataStore.asDataFlow(key, defaultValue).collect {
            isChecked = it
            changed(it)
        }
    })
    fun write(checked: Boolean) {
        scope.launch {
            dataStore.edit {
                it[key] = checked
            }
        }
    }

    val thumbContent: (@Composable () -> Unit)? = if (isChecked) {
        {
            Icon(
                imageVector = Icons.Outlined.Check,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    } else {
        null
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(
                if (isChecked) LocalColorScheme.current.primary else MaterialTheme.colorScheme.outline
            )
            .toggleable(value = isChecked) { checked ->
                write(checked)
            }
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {

        icon?.let {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .size(24.dp),
                tint = if (isChecked) LocalColorScheme.current.onPrimary else LocalColorScheme.current.surface
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = if (icon == null) 12.dp else 0.dp, end = 12.dp)
        ) {
            with(MaterialTheme) {
                Text(
                    text = title,
                    maxLines = 2,
                    style = preferenceTitle,
                    color = if (isChecked) LocalColorScheme.current.onPrimary else colorScheme.surface
                )
            }
        }
        Switch(
            checked = isChecked,
            onCheckedChange = null,
            modifier = Modifier.padding(start = 12.dp, end = 6.dp),
            thumbContent = thumbContent,
            colors = SwitchDefaults.colors(
                checkedIconColor = LocalColorScheme.current.onPrimary,
                checkedThumbColor = LocalColorScheme.current.primary,
                checkedTrackColor = LocalColorScheme.current.onPrimary,
                uncheckedBorderColor = Color.Transparent
            )
        )
    }
}