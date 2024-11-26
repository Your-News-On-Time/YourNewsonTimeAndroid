package app.yournewsontime.ui.components

import android.app.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun AlertDialog(
    title: String = "Error",
    message: String,
    positiveButton: String = "OK",
) {
    val context = LocalContext.current
    val builder = AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(positiveButton, null)
    val dialog: AlertDialog = builder.create()
    dialog.show()
}