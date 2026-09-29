package com.khodier.dominoscoretracker.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DisplayScore = TextStyle(
    fontSize = 48.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 52.sp,
)

val HeadlineLarge = TextStyle(
    fontSize = 26.sp,
    fontWeight = FontWeight.Bold,
    lineHeight = 34.sp,
)

val HeadlineMedium = TextStyle(
    fontSize = 22.sp,
    fontWeight = FontWeight.SemiBold,
    lineHeight = 28.sp,
)

val BodyLarge = TextStyle(
    fontSize = 18.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 26.sp,
)

val BodyMedium = TextStyle(
    fontSize = 15.sp,
    fontWeight = FontWeight.Normal,
    lineHeight = 22.sp,
)

val LabelMedium = TextStyle(
    fontSize = 13.sp,
    fontWeight = FontWeight.SemiBold,
    lineHeight = 16.sp,
)

val LabelSmall = TextStyle(
    fontSize = 11.sp,
    fontWeight = FontWeight.Medium,
    lineHeight = 14.sp,
)

val DominoTypography = Typography(
    headlineLarge = HeadlineLarge,
    headlineMedium = HeadlineMedium,
    bodyLarge = BodyLarge,
    bodyMedium = BodyMedium,
    labelMedium = LabelMedium,
    labelSmall = LabelSmall,
)
