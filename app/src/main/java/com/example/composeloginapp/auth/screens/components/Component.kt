package com.example.composeloginapp.auth.screens.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Eclipse(modifier: Modifier = Modifier) {
    TopLeftEllipse(height = 150.dp, width = 150.dp, density = 0.7f)
    TopLeftEllipse(height = 200.dp, width = 200.dp, density = 0.4f)
}

@Composable
fun TopLeftEllipse(height: Dp, width: Dp, density: Float) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawEllipseHalfOutsideTopLeftCorner(height, width, density)
    }
}


fun DrawScope.drawEllipseHalfOutsideTopLeftCorner(height: Dp, width: Dp, density: Float) {
    val ellipseWidth = width.toPx()
    val ellipseHeight = height.toPx()
    drawOval(
        color = Color(0xFFA8A6A7).copy(density),
        topLeft = Offset(x = -ellipseWidth / 2, y = -ellipseHeight / 2),
        size = Size(ellipseWidth, ellipseHeight)
    )
}

@Composable
fun CustomTextField(
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardtype: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    OutlinedTextField(
        label = label,
        value = value,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardtype,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions.Default,
        singleLine = true,
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        modifier = Modifier.fillMaxWidth()
    )
}
