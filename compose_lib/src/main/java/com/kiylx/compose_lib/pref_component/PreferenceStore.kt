package com.kiylx.compose_lib.pref_component

import android.app.Application
import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.preferencesDataStore

val LocalPrefs = compositionLocalOf<PreferenceStore> { error("没有提供值！") }

class PreferenceStore internal constructor(
    dataStoreName: String,
    private val ctx: Application
) {
    private val Context.myDataStore by preferencesDataStore(dataStoreName)
    fun dataStore() = ctx.myDataStore

    companion object {
        @Volatile
        var ps: PreferenceStore? = null
        fun instance(
            dataStoreName: String,
            ctx: Application
        ): PreferenceStore {
            return ps ?: synchronized(this) {
                ps ?: PreferenceStore(dataStoreName, ctx).also { ps = it }
            }
        }
    }
}

@Composable
fun PreferencesScope(dataStoreName: String, ctx: Application, content: @Composable () -> Unit) {
    val preferenceStore by remember {
        mutableStateOf(PreferenceStore.instance(dataStoreName, ctx))
    }
    CompositionLocalProvider(LocalPrefs provides preferenceStore) {
        Column {
            content()
        }
    }
}