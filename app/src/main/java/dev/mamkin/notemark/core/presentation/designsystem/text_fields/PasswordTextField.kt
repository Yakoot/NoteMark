package dev.mamkin.notemark.core.presentation.designsystem.text_fields

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
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