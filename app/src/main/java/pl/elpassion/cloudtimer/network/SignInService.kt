package pl.elpassion.cloudtimer.network

import android.util.Log


interface SignInService {
    fun singIn(email : String)
}

var myPointlessService = object: SignInService {
    override fun singIn(email: String) {
        Log.e("Log","e!")
    }
}