package com.groot.googlecodescanner.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.groot.googlecodescanner.R
import com.groot.googlecodescanner.ui.ButtonFlatView
import com.groot.googlecodescanner.ui.theme.Firefly
import com.groot.googlecodescanner.util.GooglePlayServiceUtil
import com.groot.mlkitScanner.ui.ClearPlayServiceCacheDialog
import com.groot.mlkitScanner.ui.PlayServiceDisabledDialog

val buttonMod = Modifier
    .fillMaxWidth()
    .padding(horizontal = 25.dp)
    .height(55.dp)

//Google code scanner
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoogleCodeScannerView(innerPadding: PaddingValues) {
    val context = LocalContext.current
    val scannedText = remember { mutableStateOf("") }
    val showScannedString = remember { mutableStateOf(false) }
    val showPlayServicesDisabledAlertDialog = remember { mutableStateOf(false) }
    val showPlayServicesAlertDialog = remember { mutableStateOf(false) }
    val clipboardManager = LocalClipboardManager.current

    //1: Configure the code scanner. (Optional)
    val options = GmsBarcodeScannerOptions.Builder()
        .enableAutoZoom() // available on 16.1.0 and higher
        .build()

    //2: Get an instance of GmsBarcodeScanner, with  configured options
    val scanner = GmsBarcodeScanning.getClient(context, options)

    LaunchedEffect(Unit) {
        if (GooglePlayServiceUtil.isGooglePlayServicesAvailable(context)) {
            GooglePlayServiceUtil.checkAvailability(context)
        } else {
            showPlayServicesDisabledAlertDialog.value = true
        }
    }

    if (showPlayServicesDisabledAlertDialog.value) {
        PlayServiceDisabledDialog {
            showPlayServicesDisabledAlertDialog.value = false
        }
    }

    if (showPlayServicesAlertDialog.value) {
        ClearPlayServiceCacheDialog {
            showPlayServicesAlertDialog.value = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .padding(horizontal = 12.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (showScannedString.value) {
            Image(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(150.dp)
                    .clickable {

                    },
                painter = painterResource(id = R.drawable.ic_world_wide_web),
                contentDescription = "open url"
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = scannedText.value,
                modifier = Modifier,
            )

            Spacer(modifier = Modifier.weight(0.1f))


            ButtonFlatView(
                modifier = buttonMod,
                bgColor = Firefly,
                textId = R.string.open_url,
                fgColor = Color.White,
            ) {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(scannedText.value))
                context.startActivity(webIntent)
            }
            Spacer(modifier = Modifier.height(12.dp))

            ButtonFlatView(
                modifier = buttonMod,
                bgColor = Firefly,
                textId = R.string.copy,
                fgColor = Color.White,
            ) {
                val annotatedString = AnnotatedString(scannedText.value)
                clipboardManager.setText(annotatedString)
            }

            Spacer(modifier = Modifier.height(12.dp))

        } else {
            Spacer(modifier = Modifier.weight(1f))
        }

        ButtonFlatView(
            modifier = buttonMod
                .align(Alignment.CenterHorizontally),
            bgColor = Firefly,
            textId = R.string.scan_any_qr_code,
            icon = R.drawable.ic_scan_qr,
            fgColor = Color.White,
        ) {
            if (GooglePlayServiceUtil.isGooglePlayServicesAvailable(context)) {
                scanQrCode(scanner, scannedText, showScannedString, showPlayServicesAlertDialog)
            } else {
                showPlayServicesDisabledAlertDialog.value = true
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

    }

}

private fun scanQrCode(
    scanner: GmsBarcodeScanner,
    scannedText: MutableState<String>,
    showScannedString: MutableState<Boolean>,
    showPlayServicesAlertDialog: MutableState<Boolean>
) {
    scanner.startScan()
        .addOnSuccessListener { barcode ->
            // Task completed successfully
            val rawValue: String? = barcode.rawValue
            println(rawValue)
            scannedText.value = rawValue ?: ""
            showScannedString.value = true
        }
        .addOnCanceledListener {
            // Task canceled
            scannedText.value = "Cancelled by User"
        }
        .addOnFailureListener { e ->
            showPlayServicesAlertDialog.value = true
            // Task failed with an exception
            scannedText.value = "Failed with exception ${e.message}"
        }
}