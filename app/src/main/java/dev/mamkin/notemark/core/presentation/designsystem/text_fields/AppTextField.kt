package dev.mamkin.notemark.core.presentation.designsystem.text_fields

import android.R.attr.textStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun AppTextField(
    text: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    supportingText: String? = null,
    placeholder: String? = null,
    maxLines: Int = Int.MAX_VALUE,
    singleLine: Boolean = false,
    error: Boolean = false,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val interactionSource = remember { MutableInteractionSource() }
    val focused by interactionSource.collectIsFocusedAsState()

    val shape = RoundedCornerShape(12.dp)

    val backgroundColor = if (focused) {
        Color.Transparent
    } else {
        MaterialTheme.colorScheme.surface
    }

    val textStyle = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.onSurface
    )

    val supportingTextColor = if (error) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val labelTextColor = MaterialTheme.colorScheme.onSurface

    val borderColor = when {
        focused -> MaterialTheme.colorScheme.primary
        error -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.surface
    }

    val placeholderTextColor = if (focused) {
        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }



    BasicTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        singleLine = singleLine,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        decorationBox = { innerTextField ->
            Column {
                if (label != null) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                        color = labelTextColor,
                    )
                    Spacer(modifier = Modifier.height(7.dp))
                }
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = borderColor,
                            shape = shape
                        )
                        .background(backgroundColor, shape)
                        .padding(horizontal = 16.dp, vertical = 12.dp)

                ) {
                    if (text.isBlank()) {
                        Text(
                            text = placeholder ?: "",
                            color = placeholderTextColor,
                            style = textStyle
                        )
                    }
                    innerTextField()
                }
                if (supportingText != null) {
                    Spacer(modifier = Modifier.height(7.dp))
                    Text(
                        text = supportingText,
                        style = MaterialTheme.typography.bodySmall,
                        color = supportingTextColor,
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun TransparentHintTextFieldPreview() {
    var text by remember { mutableStateOf("") }
    NoteMarkTheme {
        AppTextField(
            text = text,
            onValueChange = { text = it},
            placeholder = "Placeholder",
            supportingText = "Supporting text",
            label = "Label",
            error = true
        )
    }
}