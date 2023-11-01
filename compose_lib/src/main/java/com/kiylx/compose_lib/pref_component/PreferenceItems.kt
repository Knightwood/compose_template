package com.kiylx.compose_lib.pref_component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kiylx.compose_lib.theme3.LocalColorScheme
import com.kiylx.compose_lib.theme3.applyOpacity
import com.kiylx.compose_lib.theme3.harmonizeWithPrimary

/**
 * preference item
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreferenceItem(
    title: String,
    description: String? = null,
    icon: Any? = null,
    enabled: Boolean = true,
    onLongClickLabel: String? = null,
    onLongClick: (() -> Unit)? = null,
    onClickLabel: String? = null,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.combinedClickable(
            onClick = onClick,
            onClickLabel = onClickLabel,
            enabled = enabled,
            onLongClickLabel = onLongClickLabel,
            onLongClick = onLongClick
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.all.horizontal.dp, Dimens.all.vertical.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ParseIcon(icon = icon, enabled = enabled)
            MediumTextContainer(icon = icon) {
                PreferenceItemTitleText(text = title, enabled = enabled, maxLines = 2)
                if (description != null) PreferenceItemDescriptionText(
                    text = description,
                    enabled = enabled
                )
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreferenceItemVariant(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    icon: Any? = null,
    enabled: Boolean = true,
    onLongClickLabel: String? = null,
    onLongClick: () -> Unit = {},
    onClickLabel: String? = null,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.combinedClickable(
            enabled = enabled,
            onClick = onClick,
            onClickLabel = onClickLabel,
            onLongClick = onLongClick,
            onLongClickLabel = onLongClickLabel
        )
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(Dimens.all.horizontal.dp, Dimens.all.vertical.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ParseIcon(icon, enabled)
            MediumTextContainer(icon) {
                with(MaterialTheme) {
                    Text(
                        text = title,
                        maxLines = 1,
                        style = typography.titleMedium,
                        color = colorScheme.onSurface.applyOpacity(enabled)
                    )
                    if (description != null) Text(
                        text = description,
                        color = colorScheme.onSurfaceVariant.applyOpacity(enabled),
                        maxLines = 2, overflow = TextOverflow.Ellipsis,
                        style = typography.bodyMedium,
                    )
                }
            }
        }
    }

}


@Composable
fun PreferencesCautionCard(
    title: String,
    description: String? = null,
    icon: Any? = null,
    onClick: () -> Unit = {},
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.small.dp, vertical = Dimens.medium.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.errorContainer.harmonizeWithPrimary())
            .clickable { onClick() }
            .padding(horizontal = Dimens.medium.dp, vertical = Dimens.large.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ParseIcon(icon = icon, enabled = true)
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = if (icon == null) Dimens.text.start.dp else 0.dp)
                .padding(end = Dimens.text.end.dp)
        ) {
            with(MaterialTheme) {

                Text(
                    text = title,
                    maxLines = 1,
                    style = typography.titleLarge,
                    color = colorScheme.onErrorContainer.harmonizeWithPrimary()
                )
                if (description != null) Text(
                    text = description,
                    color = colorScheme.onErrorContainer.harmonizeWithPrimary(),
                    maxLines = 2, overflow = TextOverflow.Ellipsis,
                    style = typography.bodyMedium,
                )
            }
        }
    }


}

@Composable
fun PreferencesHintCard(
    title: String = "Title ".repeat(2),
    description: String? = "Description text ".repeat(3),
    icon: ImageVector? = Icons.Outlined.Translate,
    backgroundColor: Color = LocalColorScheme.current.secondary,
    contentColor: Color = LocalColorScheme.current.onSecondary,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        icon?.let {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp)
                    .size(24.dp),
                tint = contentColor
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
                    maxLines = 1,
                    style = typography.titleLarge.copy(fontSize = 20.sp),
                    color = contentColor
                )
                if (description != null) Text(
                    text = description,
                    color = contentColor,
                    maxLines = 2, overflow = TextOverflow.Ellipsis,
                    style = typography.bodyMedium,
                )
            }
        }
    }
}

/**
 * preference item
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PreferenceItemLargeTitle(
    title: String,
    icon: Any? = null,
    enabled: Boolean = true,
    onLongClickLabel: String? = null,
    onLongClick: (() -> Unit)? = null,
    onClickLabel: String? = null,
    onClick: () -> Unit = {},
) {
    Surface(
        modifier = Modifier.combinedClickable(
            onClick = onClick,
            onClickLabel = onClickLabel,
            enabled = enabled,
            onLongClickLabel = onLongClickLabel,
            onLongClick = onLongClick
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.all.horizontal.dp, Dimens.all.vertical.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ParseIcon(icon = icon, enabled = enabled)
            MediumTextContainer(icon = icon) {
                PreferenceItemTitleText(
                    text = title,
                    enabled = enabled, maxLines = 1,
                    style = Typography.preferenceLargeTitle
                )
            }
        }
    }

}

/**
 * 类似小标题
 */
@Composable
fun PreferenceItemSubTitle(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimens.all.horizontal.dp, Dimens.all.vertical.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MediumTextContainer() {
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = Dimens.large_x.dp,
                        bottom = Dimens.medium.dp
                    ),
                color = color,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

//<editor-fold desc="公共组件方法">


@Composable
internal fun ParseIcon(icon: Any? = null, enabled: Boolean = true) {
    val iconModifier = Modifier
        .padding(start = Dimens.icon.start.dp, end = Dimens.icon.end.dp)
        .size(Dimens.icon.size.dp)
    when (icon) {

        is ImageVector -> {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = iconModifier,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.applyOpacity(enabled)
            )
        }

        is Painter -> {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = iconModifier,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.applyOpacity(enabled)
            )
        }

        is Int -> {
            CircularProgressIndicator(
                modifier = iconModifier.padding(Dimens.small_ss.dp)
            )
        }
    }
}

@Composable
internal fun PreferenceItemTitleText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = 2,
    style: TextStyle = Typography.preferenceMediumTitle,
    enabled: Boolean,
    color: Color = MaterialTheme.colorScheme.onBackground,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = maxLines,
        style = style,
        color = color.applyOpacity(enabled),
        overflow = overflow
    )
}

@Composable
internal fun PreferenceItemDescriptionText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = Typography.preferenceDescription,
    enabled: Boolean,
    color: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    overflow: TextOverflow = TextOverflow.Ellipsis
) {
    Text(
        modifier = modifier.padding(top = 2.dp),
        text = text,
        maxLines = maxLines,
        style = style,
        color = color.applyOpacity(enabled),
        overflow = overflow
    )
}

/**
 * 中间的文本，由item的标题和描述组成
 */
@Composable
internal fun RowScope.MediumTextContainer(
    icon: Any? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .padding(
                start = if (icon == null) Dimens.text.start.dp else 0.dp,
                end = Dimens.text.end.dp
            ),
    ) {
        content()
    }
}
//</editor-fold>