
# compose 的preference界面

## 存储偏好值的工具
由三种可用的存储偏好值的工具

1. DataStore
2. MMKV
3. SharedPreference

## preference的界面

```kotlin
//使用PreferencesScope 包裹 preference的compose函数，并且传入存储偏好值的设置工具
PreferencesScope(
    //1. 使用DataStore存储偏好值
//                    DataStorePreferenceHolder.instance(
//                        dataStoreName = "test",
//                        ctx = AppCtx.instance
//                    )
    //1. 使用MMKV存储偏好值
//                    MMKVPreferenceHolder.instance(MMKV.defaultMMKV())
    //3. 使用SharedPreference存储偏好值
    OldPreferenceHolder.instance(
        AppCtx.instance.getSharedPreferences(
            "ddd",
            Context.MODE_PRIVATE
        )
    )
) {
    //这里就可以使用一些compose函数构造界面
    
    PreferenceItem(title = "PreferenceItem")
    PreferenceItemVariant(title = "PreferenceItemVariant")
    PreferencesHintCard(title = "PreferencesHintCard")
    PreferenceItemLargeTitle(title = "PreferenceItemLargeTitle")
    PreferenceItemSubTitle(text = "PreferenceItemSubTitle")
    PreferencesCautionCard(title = "PreferencesCautionCard")
    PreferenceSwitch(
        keyName = "bol",
        title = "title",
        description = "description"
    )
    //可折叠
    CollapsePreferenceItem(
        title = "title",
        description = "description"
    ) {
        PreferenceSwitch(
            keyName = "bol2",
            title = "title",
            description = "description",
            icon = Icons.Filled.CenterFocusWeak
        )

    }
    PreferenceSwitchWithDivider(
        keyName = "bol2",
        title = "title",
        description = "description",
        icon = Icons.Filled.CenterFocusWeak
    )
    PreferenceSwitchWithContainer(
        keyName = "bol2",
        title = "Title ".repeat(2),
        icon = null
    )
    PreferenceRadioGroup(
        keyName = "radioGroup",
        labels = listOf(
            "first",
            "second"
        ), changed = {
            Log.d(TAG, "radio: ${it}")
        }
    )
    PreferenceCheckBoxGroup(
        keyName = "CheckBoxGroup",
        labels = listOf(
            "first",
            "second"
        ), changed = {
            Log.d(TAG, "checkbox: ${it.joinToString(",")}")
        }
    )
    PreferenceSlider(
        keyName = "slider", min = 0f,
        max = 10f, steps = 9, value = 0f, changed = {
            Log.d(TAG, "slider: $it")
        }
    )
}


```