package homepunk.github.com.presentation.core.ext

import android.graphics.Color
import android.graphics.PointF
import android.graphics.Rect
import androidx.databinding.BindingAdapter
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton
import com.nightonke.boommenu.BoomMenuButton
import com.nightonke.boommenu.ButtonEnum
import com.nightonke.boommenu.OnBoomListener
import com.nightonke.boommenu.Piece.PiecePlaceEnum
import com.nightonke.boommenu.Util
import homepunk.github.com.presentation.common.model.menu.MenuModel
import homepunk.github.com.presentation.feature.widget.countrypicker.CountryModel
import homepunk.github.com.presentation.util.DimensionUtil

/**Created by Homepunk on 17.01.2019. **/

@BindingAdapter(
        requireAll = false,
        value = [
            "menuList",
            "menuIconPaddingLeft",
            "menuIconPaddingRight",
            "menuIconPaddingTop",
            "menuIconPaddingBottom"
        ])
fun BoomMenuButton.bindAppModeMenu(menuList: List<MenuModel>, iconPaddingLeft: Float = 0f, iconPaddingRight: Float = 0f, iconPaddingTop: Float = 0f, iconPaddingBottom: Float = 0f) {
    if (menuList.size != 4) {
        throw ArrayIndexOutOfBoundsException("Menu buttons count = ${menuList.size}, please pass 3 buttons or change PiecePlaceEnum.DOT_X_N to X match count of buttons")
    } else {
        buttonEnum = ButtonEnum.TextOutsideCircle
        piecePlaceEnum = PiecePlaceEnum.DOT_4_2
        buttonPlaceEnum = ButtonPlaceEnum.Custom
    }
    val w = Util.dp2px(80f).toFloat()
    val h = Util.dp2px(96f).toFloat()
    val h_0_5 = h / 2
    val h_1_5 = h * 1.5f
    val vm_0_5 = buttonHorizontalMargin / 2
    val vm_1_5 = buttonVerticalMargin * 1.5f

    menuList.forEach { menuItem ->
        val builder = TextOutsideCircleButton.Builder()
                .normalImageRes(menuItem.iconResId)
                .normalColorRes(menuItem.colorResId)
                .imagePadding(Rect(iconPaddingLeft.toInt(), iconPaddingRight.toInt(), iconPaddingTop.toInt(), iconPaddingBottom.toInt()))
                .normalTextRes(menuItem.titleResId)
        addBuilder(builder)
    }
    customButtonPlacePositions.add(PointF(-w - buttonHorizontalMargin, -h_0_5 - vm_0_5))
    customButtonPlacePositions.add(PointF(0F, -h_0_5 - vm_0_5))
    customButtonPlacePositions.add(PointF(w + buttonHorizontalMargin, -h_0_5 - vm_0_5))
    customButtonPlacePositions.add(PointF(0F, +h_0_5 + vm_0_5))
}

@BindingAdapter("onBoomListener")
fun BoomMenuButton.bindOnBoomListener(listener: OnBoomListener) {
    onBoomListener = listener
}


@BindingAdapter("countryList")
fun BoomMenuButton.bindCountryList(countryList: List<CountryModel>) {
    piecePlaceEnum = PiecePlaceEnum.DOT_3_1
    buttonPlaceEnum = ButtonPlaceEnum.Horizontal

    val padding = DimensionUtil.dpToPx<Int>(context, 4f)
    countryList.forEach { countryModel ->
        val builder = TextOutsideCircleButton.Builder()
                .normalImageDrawable(countryModel.getFlagDrawable(context))
                .imagePadding(Rect(0, padding, padding, padding))
                .normalColor(Color.TRANSPARENT)
                .buttonRadius(DimensionUtil.dpToPx(this@bindCountryList.context, 36f))
                .normalText(countryModel.countryName)

        addBuilder(builder)
    }
}