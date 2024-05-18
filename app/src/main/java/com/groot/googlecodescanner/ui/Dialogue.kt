package com.groot.mlkitScanner.ui

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.common.GoogleApiAvailability.GOOGLE_PLAY_SERVICES_PACKAGE


@Composable
fun PlayServiceDisabledDialog(
    dismissAction: () -> Unit,
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = {
            dismissAction()
        },
        dismissButton = {

        },
        confirmButton = {
            TextButton(onClick = {
                val packageName = "com.google.android.gms" //google play services package name

                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri: Uri = Uri.fromParts("package", GOOGLE_PLAY_SERVICES_PACKAGE, null)
                intent.data = uri
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)

                dismissAction()
            }) {
                Text(text = "Enable")
            }
        },
        text = {
            Text(
                text = "Google play services is disabled Please enable it to continue.\nTo enable go to Settings > Apps > Manage Apps > Google Play Services > Tap Enable or  Tap Enable to confirm"
            )
        })
}

@Composable
fun ClearPlayServiceCacheDialog(
    dismissAction: () -> Unit,
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = {
            dismissAction()
        },
        dismissButton = {

        },
        confirmButton = {
            TextButton(onClick = {
                val packageName = "com.google.android.gms" //google play services package name

                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri: Uri = Uri.fromParts("package", GOOGLE_PLAY_SERVICES_PACKAGE, null)
                intent.data = uri
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)

                dismissAction()
            }) {
                Text(text = "Go to settings")
            }
        },
        text = {
            Text(
                text = "Kindlt clear your play services data and restart the app.\nGo to Settings > Apps > Manage Apps > Google Play Services > Tap Storage usage > Tap Manage Space and than tap clear data  or  Tap Go to settings"
            )
        })
}