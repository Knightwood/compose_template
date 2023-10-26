package com.kiylx.compose_lib.component.metro

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalView
import com.kiylx.compose_lib.component.RippleAnimationState
import com.kiylx.compose_lib.component.autoRippleAnimation

private const val TAG="TurnAnim"

class TurnAnimState {
    var animMode: RippleAnimationState.AnimMode = RippleAnimationState.AnimMode.expend
    var animTime: Long = 5000
        set(value) {
            if (value < 50) {
                return
            } else {
                field = value
            }
        }
    internal var innerPos = mutableStateOf(Offset.Zero)

    //when need change theme,will switch to true,when change done,will restore to false
    internal var runAnim = false
    internal var offset = mutableStateOf(Offset.Zero)

    /**
     * 动画执行后执行的方法块
     */
    internal var block: () -> Unit = {}

    /**
     * 设置主题切换的逻辑，传入坐标，以运行动画以及触发主题切换
     * 若使用了[autoRippleAnimation]，可以不传坐标,若传坐标，会覆盖自动获取的点击坐标
     */
    fun change(pointerOffset: Offset = innerPos.value, func: () -> Unit) {
        if (runAnim) {
            return
        }
        if (pointerOffset == Offset.Zero) {
            throw IllegalArgumentException("坐标错误")
        }
        offset.value = pointerOffset
        block = func
    }


    enum class AnimMode {
        shrink, expend
    }
}

@Composable
fun rememberTurnAnimState(block: TurnAnimState.() -> Unit = {}): TurnAnimState {
    return remember {
        TurnAnimState().also(block)
    }
}

@Composable
@Stable
fun Modifier.turnAnim(state: TurnAnimState): Modifier {
    val mRootView : View = LocalView.current.rootView
    val mMaxWidth =mRootView.width
    val result = this.composed {

        var xDelta by remember {
            mutableFloatStateOf(0.0f)
        }
        var rotateDelta by remember {
            mutableFloatStateOf(0.0f)
        }
        var anim: ValueAnimator?
        val mAnimatorListener: Animator.AnimatorListener = object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                state.block.invoke()
                state.runAnim = false
            }
        }

        LaunchedEffect(key1 = state.offset.value, block = {
            if (!state.runAnim && state.offset.value != Offset.Zero) {
                state.runAnim = true
                anim = when (state.animMode) {
                    RippleAnimationState.AnimMode.shrink -> {
                        ValueAnimator.ofFloat(mMaxWidth.toFloat(), 0f)
                    }

                    RippleAnimationState.AnimMode.expend -> {
                        ValueAnimator.ofFloat(0f, mMaxWidth.toFloat())
                    }
                }.apply {
                    duration = state.animTime
                    interpolator = AccelerateDecelerateInterpolator()
                    addUpdateListener { valueAnimator ->
                        Log.d(TAG, "turnAnim: update-${valueAnimator.animatedFraction}")
                        xDelta =valueAnimator.animatedFraction*mMaxWidth
                        rotateDelta =valueAnimator.animatedFraction*90
                    }
                    addListener(mAnimatorListener)
                }
                anim?.start()

            }
        })

        this.then(Modifier.graphicsLayer {
            translationX=xDelta
            rotationY =rotateDelta
        })
    }
    return result
}