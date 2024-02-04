package com.ourproject.gojekclone.ui.compose

import Purple200
import Purple500
import Purple700
import Shapes
import Teal200
import TypoGraphyCustom
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val DarkColorPalette =
    darkColors(primary = Purple200, primaryVariant = Purple700, secondary = Teal200)

private val LightColorPalette =
    lightColors(primary = Purple500, primaryVariant = Purple700, secondary = Teal200

        /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */)

@Composable
fun GoFoodTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(colors = colors, typography = TypoGraphyCustom, shapes = Shapes, content = content)
}