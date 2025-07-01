package dev.mamkin.notemark.notes.presentation.notes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.mamkin.notemark.core.presentation.designsystem.dialogs.AppConfirmDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteNoteConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
) {
    AppConfirmDialog(
        onDismiss = onDismissRequest,
        onConfirm = onConfirm,
        title = "Delete Note?",
        text = "Are you sure you want to delete this note?\n" +
                "This action cannot be undone.",
        confirmButtonText = "Delete",
        dismissButtonText = "Cancel"
    )
}