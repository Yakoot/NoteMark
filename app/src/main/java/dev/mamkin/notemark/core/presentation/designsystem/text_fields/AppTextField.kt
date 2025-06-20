package dev.mamkin.notemark.core.presentation.designsystem.text_fields

import android.R.attr.label
import android.R.attr.maxLines
import android.R.attr.singleLine
import android.R.attr.text
import android.R.attr.textStyle
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    supportingText: String? = null,
    focusSupportingText: String? = null,
    placeholder: String? = null,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    trailingIcon: @Composable (() -> Unit)? = null,
    error: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isErrorStateVisible = remember(error, isFocused) {
        error && !isFocused
    }
    val displayedSupportingText = remember(isFocused, supportingText, focusSupportingText) {
        if (isFocused) focusSupportingText
        else supportingText
    }
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.height(7.dp))
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            keyboardActions = keyboardActions,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            placeholder = {
                if (placeholder != null) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

            },
            trailingIcon = trailingIcon,
            supportingText = {
                if (displayedSupportingText != null) {
                    Text(
                        text = displayedSupportingText,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }

            },
            isError = isErrorStateVisible,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                errorBorderColor = MaterialTheme.colorScheme.error,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                errorSupportingTextColor = MaterialTheme.colorScheme.error,
                focusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledSupportingTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                errorTextColor = MaterialTheme.colorScheme.onSurface,
                disabledTextColor = MaterialTheme.colorScheme.onSurface
            ),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Preview
@Composable
private fun AppTextFieldPreview() {
    var value by remember { mutableStateOf("") }
    NoteMarkTheme {
        AppTextField(
            value = value,
            onValueChange = { value = it },
            placeholder = "Placeholder",
            focusSupportingText = "Use between 3 and 20 characters for your username.",
            label = "Label",
            error = false
        )
    }
}