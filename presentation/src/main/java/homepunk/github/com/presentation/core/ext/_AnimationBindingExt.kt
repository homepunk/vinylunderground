package homepunk.github.com.presentation.core.ext

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.core.wrapper.AnimationListenerWrapper
import homepunk.github.com.presentation.core.wrapper.AnimatorListenerWrapper
import homepunk.github.com.presentation.feature.widget.animation.AnimationTextView
import homepunk.github.com.presentation.feature.widget.animation.ReverseInterpolator
import homepunk.github.com.presentation.util.DimensionUtil
import timber.log.Timber
import kotlin.math.roundToInt


/**Created by Homepunk on 31.01.2019. **/


@BindingAdapter(requireAll = false, value = ["animationResId", "visibility", "animationStartOffset"])
fun ViewGroup.bindAnimationResId(resId: Int, visibilityValue: Int, startOffset: Int) {
    if (resId != 0) {
        val set: AnimationSet = AnimationUtils.loadAnimation(context, resId) as AnimationSet
        if (visibilityValue == GONE) {
            set.interpolator = AnticipateInterpolator()
        } else {
            set.startOffset = startOffset.toLong()
            set.interpolator = ReverseInterpolator(set.interpolator)
        }
        set.animations[0]?.setAnimationListener(object : AnimationListenerWrapper() {
            override fun onAnimationStart(animation: Animation?) {
                if (visibilityValue != GONE) {
                    visibility = visibilityValue
                }
            }

            override fun onAnimationEnd(animation: Animation?) {
                if (visibilityValue == GONE) {
                    visibility = visibilityValue
                }
            }
        })
        startAnimation(set)
    } else {
        visibility = visibilityValue
    }
}

@BindingAdapter(requireAll = true, value = ["animationResId", "isVisible", "animationDuration"])
fun LinearLayout.swapVisiblityWithAnimation(resId: Int, isVisible: Boolean, duration: Int) {
    if (resId != 0) {
        val set: AnimationSet = AnimationUtils.loadAnimation(context, resId) as AnimationSet
        if (isVisible) {
            visibility = INVISIBLE
            set.interpolator = AccelerateInterpolator()
        } else {
            set.interpolator = ReverseInterpolator(set.interpolator)
        }
        set.duration = duration.toLong()
        set.animations[0]?.setAnimationListener(object : AnimationListenerWrapper() {
            override fun onAnimationStart(animation: Animation?) {
                if (isVisible) {
                    visibility = VISIBLE
                }
            }

            override fun onAnimationEnd(animation: Animation?) {
                if (!isVisible) {
                    visibility = GONE
                }
            }
        })
        startAnimation(set)
    } else {
        isVisible(isVisible)
    }
}

@BindingAdapter(requireAll = true, value = ["animationResId", "isVisible", "animationDuration"])
fun ViewGroup.swapVisiblityWithAnimation(resId: Int, isVisible: Boolean, duration: Int) {
    if (resId != 0) {
        val set: AnimationSet = AnimationUtils.loadAnimation(context, resId) as AnimationSet
        if (isVisible) {
            visibility = INVISIBLE
            set.interpolator = AccelerateInterpolator()
        } else {
            set.interpolator = ReverseInterpolator(set.interpolator)
        }
        set.duration = duration.toLong()
        set.animations[0]?.setAnimationListener(object : AnimationListenerWrapper() {
            override fun onAnimationStart(animation: Animation?) {
                if (isVisible) {
                    visibility = VISIBLE
                }
            }

            override fun onAnimationEnd(animation: Animation?) {
                if (!isVisible) {
                    visibility = GONE
                }
            }
        })
        startAnimation(set)
    } else {
        isVisible(isVisible)
    }
}

fun TextView.setTextWithAnimation(text: String) {
    animate().translationY(height.toFloat()).setDuration(200).start()
}

@BindingAdapter("swapOnClick")
fun View.swapOnClick(booleanLiveData: MutableLiveData<Boolean>) {
//    setOnClickListener {
//        booleanLiveData.swap()
//    }
}

@BindingAdapter(requireAll = false, value = ["animationResId", "text"])
fun TextView.bindAnimationResId(resId: Int, text: String) {
    if (resId != 0 && getText().isNotEmpty()) {
        val set = AnimationUtils.loadAnimation(context, resId) as AnimationSet
        set.interpolator = AnticipateInterpolator()
        set.animations[0]?.setAnimationListener(object : AnimationListenerWrapper() {
            override fun onAnimationRepeat(animation: Animation?) {
                setText(text)
            }
        })
        startAnimation(set)
    } else {
        setText(text)
    }
}

var lastSet: AnimatorSet? = null
var maxScale: Float = 1.2f
var defScale: Float = 1.0f

@BindingAdapter("scale")
fun AnimationTextView.scale(oldScale: Float, scale: Float) {
    if (oldScale != scale) {
        if (oldScale == 0f) {
            if (scale > 1.0f) {
                animateScaleChange(1f, scale)
            }
        } else {
            animateScaleChange(oldScale, scale)
        }
    }
}


@BindingAdapter(requireAll = false, value = ["scale"])
fun View.scale(oldScale: Float, scale: Float) {
    if (oldScale != scale) {
        if (oldScale == 0f) {
            if (scale > 1.0f) {
                getScaleAnimation(1f, scale).start()
            }
        } else {
            getScaleAnimation(oldScale, scale).start()
        }
    }
}

@BindingAdapter("stateListScale")
fun AnimationTextView.stateListScale(scale: Float) {
    setOnClickListener {
        if (it.isSelected) {
            getScaleAnimation(1f, scale).start()
        } else {
            getScaleAnimation(scale, 1f).start()
        }
    }
}

@BindingAdapter(requireAll = false, value = ["scaleWithSize"])
fun View.scaleWithSize(oldScale: Float, scale: Float) {
    pivotX = 0f
    pivotY = 0f
    if (oldScale != scale) {
        if (oldScale == 0f) {
            if (scale > 1.0f) {
                val animation = getScaleAnimation(1f, scale)
            }
        } else {
            val scaleAnimation = getScaleAnimation(oldScale, scale)
            val newHeight = (if (scale > oldScale) height * scale else height / oldScale).roundToInt()
//            val newWidth = if (height != width) {
//                (if (oldScale > scale) width * scale else width / oldScale).roundToInt()
//            } else newHeight
            val sizeAnimation = getSizeAnimation(newHeight)
            AnimatorSet().apply {
                playTogether(scaleAnimation, sizeAnimation)
                start()
            }
        }
    }
}


fun View.getWidthAnimation(newSize: Int): Animator {
    val sizeAnimator = ValueAnimator.ofInt(width, newSize)
    sizeAnimator.addUpdateListener { animation ->
        val animatedValue = animation.animatedValue as Int
        with(layoutParams) {
            width = animatedValue
            requestLayout()
        }
    }
    return sizeAnimator
}

fun View.getHeightAnimation(newSize: Int): Animator {
    val sizeAnimator = ValueAnimator.ofInt(height, newSize)
    sizeAnimator.addUpdateListener { animation ->
        val animatedValue = animation.animatedValue as Int
        with(layoutParams) {
            height = animatedValue
        }
    }
    return sizeAnimator
}

fun View.getScaleAnimation(oldScale: Float, newScale: Float): ValueAnimator {
    val scaleAnimator = ValueAnimator.ofFloat(oldScale, newScale)
    scaleAnimator.addUpdateListener { animation ->
        val animatedValue = animation.animatedValue as Float
        scaleX = animatedValue
        scaleY = animatedValue
    }
    return scaleAnimator
}

fun View.getSizeAnimation(newSize: Int): Animator {
    val sizeAnimator = ValueAnimator.ofInt(height, newSize)
    sizeAnimator.addUpdateListener { animation ->
        val animatedValue = animation.animatedValue as Int
        with(layoutParams) {
            height = animatedValue
            width = animatedValue
        }
        requestLayout()
    }
    sizeAnimator.duration = 400
    return sizeAnimator
}

@BindingAdapter(
        requireAll = false,
        value = [
            "visibility",
            "itemAnimationEnabled"])
fun RecyclerView.bindItemAnimation(newValue: Int, itemAnimationEnabled: Boolean) {
    if (itemAnimationEnabled) {
        fun updateChildren(value: Float) {
            var koef = 1.0f
            for (i in 0 until childCount) {
                koef += 0.3f
                getChildAt(i)?.apply {
                    translationX = if (i == 0) {
                        DimensionUtil.screenWidth.toFloat()
                    } else {
                        value * koef
                    }
                }
            }
        }

        val translationAnimator = ValueAnimator.ofFloat(80f, 0f)
        translationAnimator.duration = 520L
        translationAnimator.interpolator = AnticipateInterpolator()
        translationAnimator.addUpdateListener { updateChildren(it.animatedValue as Float) }
        if (newValue == GONE) {
            translationAnimator.reverse()
        } else {
            translationAnimator.startDelay = 200
            translationAnimator.start()
        }
    } else {
        visibility = newValue
    }
}

@BindingAdapter(
        requireAll = true,
        value = [
            "isAppearanceEnabled",
            "isVisible"])
fun RecyclerView.animateAppearanceOnVisibilityChange(oldAnimationEnabled: Boolean, oldIsVisible: Boolean,
                                                     animationEnabled: Boolean, isVisible: Boolean) {
    // DO FIRST RUN
    if (oldIsVisible == isVisible) {
        Timber.w("DO FIRST RUN oldIsVisible = $oldIsVisible, isVisible = $isVisible, oldAnimationEnabled = $oldAnimationEnabled, animationEnabled = $animationEnabled")
        isVisible(isVisible)
    }

    if (isVisible && animationEnabled) {
        val isHorizontal: Boolean
        try {
            isHorizontal = (layoutManager as LinearLayoutManager).orientation == HORIZONTAL
        } catch (e: Exception) {
            throw UnsupportedOperationException("Please define layout manager ORIENTATION for recycler view")
        }
        measure(MATCH_PARENT, WRAP_CONTENT)
        translationY = if (isVisible) -measuredHeight.toFloat() else 0f
        translationX = if (isVisible) DimensionUtil.screenWidth.toFloat() else 0f
        visibility = VISIBLE
        val heightAnimator: ObjectAnimator?

        if (isHorizontal) {
            heightAnimator = ObjectAnimator.ofFloat(this, "translationY", if (isVisible) -measuredHeight.toFloat() else 0f, if (!isVisible) -measuredHeight.toFloat() else 0f)
            Timber.w("animateAppearanceOnVisibilityChange measuredHeight $measuredHeight")
            heightAnimator.addListener(object : AnimatorListenerWrapper() {
                override fun onAnimationStart(animation: Animator?) {
                    Timber.w("heightAnimator onAnimationStart measuredHeight $measuredHeight")
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Timber.w("heightAnimator onAnimationEnd measuredHeight $measuredHeight, translationY = ${translationY}, translationX = ${translationX}, isVisible = ${isVisible}")
                }

                override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                    Timber.w("heightAnimator onAnimationStart measuredHeight $measuredHeight [isReverse  = $isReverse]")
                }

                override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                    Timber.w("heightAnimator onAnimationEnd measuredHeight $measuredHeight [isReverse  = $isReverse]")
                    if (isReverse) {
                        visibility = GONE
                    }
                }

            })

            AnimatorSet().apply set@{
                duration = 600
                interpolator = AnticipateOvershootInterpolator()
                playTogether(heightAnimator)
                if (isVisible) {
                    start()
                } else {
                    interpolator = ReverseInterpolator(interpolator as Interpolator)
                    start()
                }
            }
        }
    }
}

@BindingAdapter("isVisibleWithAnimation")
fun RecyclerView.isVisibleWithAnimation(oldIsVisible: Boolean, isVisible: Boolean) {
    // DO FIRST RUN
    if (oldIsVisible == isVisible) {
        visibility = INVISIBLE
//        isVisible(isVisible)
    } else {
        measure(MATCH_PARENT, WRAP_CONTENT)
        translationY = if (isVisible) -measuredHeight.toFloat() else 0f
        val heightAnimator: ObjectAnimator? = ObjectAnimator.ofFloat(this, "translationY", if (isVisible) -measuredHeight.toFloat() else 0f, if (!isVisible) -measuredHeight.toFloat() else 0f)

        Timber.w("animateAppearanceOnVisibilityChange measuredHeight $measuredHeight")
        heightAnimator!!.addListener(object : AnimatorListenerWrapper() {
            override fun onAnimationStart(animation: Animator?) {
                visibility = VISIBLE
                Timber.w("heightAnimator onAnimationStart measuredHeight $measuredHeight")
            }

            override fun onAnimationEnd(animation: Animator?) {
                Timber.w("heightAnimator onAnimationEnd measuredHeight $measuredHeight, translationY = ${translationY}, translationX = ${translationX}, isVisible = ${isVisible}")
            }

            override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                Timber.w("heightAnimator onAnimationStart measuredHeight $measuredHeight [isReverse  = $isReverse]")
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                Timber.w("heightAnimator onAnimationEnd measuredHeight $measuredHeight [isReverse  = $isReverse]")
                if (isReverse) {
                    visibility = GONE
                }
            }

        })

        AnimatorSet().apply set@{
            duration = 600
            interpolator = AnticipateOvershootInterpolator()
            playTogether(heightAnimator)
            if (isVisible) {
                start()
            } else {
                interpolator = ReverseInterpolator(interpolator as Interpolator)
                start()
            }
        }
    }
}

@BindingAdapter("isVisibleWithSlideDownAnimation")
fun RecyclerView.isVisibleWithSlideDownAnimation(oldIsVisible: Boolean, isVisible: Boolean) {
    // DO FIRST RUN
    if (oldIsVisible == isVisible) {
        visibility = INVISIBLE
        measure(MATCH_PARENT, WRAP_CONTENT)
        onGlobalLayout {
            translationY = -height.toFloat()
        }
    } else {
        animate()
                .translationY(if (isVisible) 0f else -height.toFloat())
                .setDuration(300)
                .setListener(object : AnimatorListenerWrapper() {
                    override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                        isVisible(isVisible)
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        isVisible(isVisible)
                    }
                })
    }
}


@BindingAdapter("translateDownAnimation")
fun View.translateDownAnimation(oldIsVisible: Boolean, isVisible: Boolean) {
    // DO FIRST RUN
    if (oldIsVisible == isVisible) {
        measure(MATCH_PARENT, MATCH_PARENT)
    } else {
        animate()
                .translationY(if (isVisible) dpToPx(300f) else 0f)
                .setDuration(500).start()
    }
}

@BindingAdapter(value = ["animation_translationY", "animation_start"], requireAll = true)
fun View.animation_translateByY(oldTranslateToY: Float, oldStart: Boolean,
                                translateToY: Float, start: Boolean) {
    // DO FIRST RUN
    if (oldStart == start) {

    } else {
        animate()
                .translationY(if (start) translateToY else 0f)
                .setDuration(500)
                .start()
    }
}

@BindingAdapter(value = ["animation_translationByHeight", "animation_start"], requireAll = true)
fun View.animation_translationByHeight(oldViewId: Int, oldStart: Boolean,
                                       viewId: Int, start: Boolean) {
    // DO FIRST RUN
    if (oldStart == start) {
    } else {
        rootView.findViewById<View>(viewId).let { view ->
            Timber.w("view height = ${view.height}, measuredHeight = ${view.measuredHeight}")
            val animator = animate().setDuration(500)

            if (start) {
                view.onGlobalLayout {
                    Timber.w("onGlobalLayout view height = ${view.height}, measuredHeight = ${view.measuredHeight}")
                    animator.translationY(view.height.toFloat())
                }
            } else {
                animator.translationY(0f)
            }
            animator.start()

        }
    }
}

@BindingAdapter(value = ["animation_alphaFrom", "animation_alphaTo", "animation_start"], requireAll = true)
fun View.animateAlpha(oldAlphaFrom: Float, oldAlphaTo: Float, oldStart: Boolean,
                      alphaFrom: Float, alphaTo: Float, start: Boolean) {
    // DO FIRST RUN
    if (oldStart == start && !start) {
        alpha = alphaFrom
    } else {
        animate()
                .alpha(if (start) alphaTo else alphaFrom)
                .setDuration(500)
                .start()
    }
}

