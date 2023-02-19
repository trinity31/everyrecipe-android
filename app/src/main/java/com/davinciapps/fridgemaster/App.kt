package com.davinciapps.fridgemaster

import android.app.Application
import android.util.Log
import com.amplifyframework.AmplifyException
import com.amplifyframework.api.aws.AWSApiPlugin
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import dagger.hilt.android.HiltAndroidApp
import com.google.android.gms.ads.MobileAds

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        try {
            Amplify.addPlugin(AWSApiPlugin())
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.configure(applicationContext)

            Log.i("App", "Initialized Amplify")

            MobileAds.initialize(this) {}

            Log.i("App", "Initialized Admob")
        } catch (error: AmplifyException) {
            Log.e("App", "Could not initialize Amplify", error)
        }
    }
}