package com.groot.googlecodescanner.util

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallClient
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.android.gms.tflite.client.TfLiteClient
import com.google.android.gms.tflite.java.TfLite


object GooglePlayServiceUtil {
    fun isGooglePlayServicesAvailable(context: Context): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(context)

        return status == ConnectionResult.SUCCESS
    }

    //Check the availability of an optional module using its OptionalModuleApi
    fun checkAvailability(context: Context) {
        val moduleInstallClient: ModuleInstallClient = ModuleInstall.getClient(context) //Get an instance of ModuleInstallClient
        val optionalModuleApi: TfLiteClient = TfLite.getClient(context)

        moduleInstallClient
            .areModulesAvailable(optionalModuleApi)
            .addOnSuccessListener {
                if (it.areModulesAvailable()) {
                    // Modules are present on the device...
                    println("xcxcx xcxcxc xcxcxcx Modules are available")
                } else {
                    println("xcxcx xcxcxc xcxcxcx Modules are not available")

                    // Modules are not present on the device...

                    //Send an urgent module install request
                    val moduleInstallRequest = ModuleInstallRequest.newBuilder()
                        .addApi(optionalModuleApi)
                        .build()


                    moduleInstallClient
                        .installModules(moduleInstallRequest)
                        .addOnSuccessListener {
                            if (it.areModulesAlreadyInstalled()) {
                                // Modules are already installed when the request is sent.
                                println("xcxcx xcxcxc xcxcxcx Modules installed")
                            }
                        }
                        .addOnFailureListener {
                            // Handle failure…
                        }
                }
            }
            .addOnFailureListener {
                // Handle failure...
            }

    }

}