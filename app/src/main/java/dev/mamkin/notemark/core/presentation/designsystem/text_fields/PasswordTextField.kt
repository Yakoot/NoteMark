package dev.mamkin.notemark.core.presentation.designsystem.text_fields

import android.R.attr.maxLines
import android.R.attr.singleLine
import android.R.attr.text
import android.R.attr.textStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.notemark.R
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String? = null,
    error: Boolean = false,
    focusSupportingText: String? = null,
    supportingText: String? = null,
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    ) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    AppTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = label,
        error = error,
        supportingText = supportingText,
        focusSupportingText = focusSupportingText,
        placeholder = placeholder,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = if (isPasswordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        trailingIcon = if (isPasswordVisible) {
            {
                Icon(
                    painter = painterResource(id = R.drawable.eye_off),
                    contentDescription = "Hide password",
                    modifier = Modifier.clickable {
                        isPasswordVisible = false
                    }
                )
            }
        } else {
            {
                Icon(
                    painter = painterResource(id = R.drawable.eye),
                    contentDescription = "Hide password",
                    modifier = Modifier.clickable {
                        isPasswordVisible = true
                    }
                )
            }
        }
    )
}

@Preview
@Composable
private fun PasswordTextFieldPreview() {
    var text by remember { mutableStateOf("") }
    NoteMarkTheme {
        PasswordTextField(
            value = text,
            onValueChange = { text = it},
            placeholder = "Placeholder",
            supportingText = "Supporting text",
            label = "Label",
        )
    }
}