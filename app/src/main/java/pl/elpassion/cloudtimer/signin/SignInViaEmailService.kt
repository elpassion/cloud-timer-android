package pl.elpassion.cloudtimer.signin

import pl.elpassion.cloudtimer.common.retrofit
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import rx.Observable


interface SignInViaEmailService {

    @Headers("Content-Type: application/json")
    @POST("v1/sessions/sign_in.json")
    fun singIn(@Body email: SignInViaEmail): Observable<Any>
}

var signInViaEmailService = retrofit.create(SignInViaEmailService::class.java)