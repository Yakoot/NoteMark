package dev.mamkin.notemark.notes.presentation.notes.components

import android.R.attr.text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.mamkin.notemark.core.presentation.designsystem.theme.NoteMarkTheme

@Composable
fun ProfileIcon(modifier: Modifier = Modifier, username: String) {
    val initials = remember(username) {
        username.toInitials()
    }

    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials.toUpperCase(Locale.current),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

fun String.toInitials(): String {
    if (this.isEmpty()) return ""
    val words = this.split(" ")
    return when (words.size) {
        1 -> words[0].take(2)
        else -> words.first().take(1) + words.last().take(1)
    }
}

@Preview
@Composable
private fun Preview() {
    NoteMarkTheme {
        ProfileIcon(username = "Anton Test Mamkin")
        ProfileIcon(username = "Anton Mamkin")
        ProfileIcon(username = "Anton")
        ProfileIcon(username = "")
    }
}