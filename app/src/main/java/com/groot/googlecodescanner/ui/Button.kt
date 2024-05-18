package com.groot.googlecodescanner.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.groot.googlecodescanner.ui.theme.Firefly
import com.groot.googlecodescanner.ui.theme.MandysPink


@Composable
fun ButtonContentView(
    @StringRes textId: Int? = null,
    text: String? = null,
    @DrawableRes icon: Int? = null,
    tint: Color? = null,
) {
    Row(
        Modifier
            .wrapContentSize()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            if (tint != null) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                    colorFilter = ColorFilter.tint(tint)
                )
            } else {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp),
                )
            }
        }
        val btnTextVal = if (textId != null) stringResource(textId) else text ?: ""
        Text(btnTextVal, style = MaterialTheme.typography.titleMedium)
    }
}


@Composable
fun OutlinedButtonMainView(
    @StringRes textId: Int? = null,
    text: String? = null,
    @DrawableRes icon: Int? = null,
    fgColor: Color = Firefly,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small,
    tint: Color? = null,
    onClick: () -> Unit,
) {
    OutlinedButton(
        onClick = onClick,
        shape = shape,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(contentColor = fgColor),
        border = BorderStroke(color = fgColor, width = 1.dp),
        enabled = enable
    ) {
        ButtonContentView(textId, text, icon, tint)
    }
}

@Composable
fun OutlinedButtonFlatView(
    @StringRes textId: Int? = null,
    text: String? = null,
    @DrawableRes icon: Int? = null,
    bgColor: Color = MandysPink,
    fgColor: Color = Firefly,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enable: Boolean = true,
    onClick: () -> Unit,
) {
    OutlinedButtonMainView(
        textId, text, icon, fgColor, modifier.height(56.dp),
        enable, onClick = onClick
    )
}


@Composable
fun ButtonFlatView(
    @StringRes textId: Int? = null,
    text: String? = null,
    @DrawableRes icon: Int? = null,
    bgColor: Color = MandysPink,
    fgColor: Color? = null,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onClick: () -> Unit,
) {
    ButtonMainView(
        textId = textId, text = text, icon = icon, bgColor = bgColor, fgColor = fgColor,
        modifier = modifier, enable = enable, onClick = onClick
    )
}


@Composable
fun ButtonMainView(
    @StringRes textId: Int? = null,
    text: String? = null,
    @DrawableRes icon: Int? = null,
    bgColor: Color = MandysPink,
    fgColor: Color? = Firefly,
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    shape: Shape = MaterialTheme.shapes.extraSmall,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = shape,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = bgColor,
            contentColor = fgColor ?: Firefly,
            disabledContentColor = fgColor ?: Firefly,
            disabledContainerColor = bgColor
        ),
        enabled = enable
    ) {
        ButtonContentView(textId = textId, text = text, icon = icon, tint = fgColor)
    }
}