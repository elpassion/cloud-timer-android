package pl.elpassion.cloudtimer.login

import pl.elpassion.cloudtimer.common.retrofit
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import rx.Observable

interface LoginService {

    @Headers("Content-Type: application/json")
    @POST("v1/sessions.json")
    fun login(@Body email: Login): Observable<User>
}

var loginService = retrofit.create(LoginService::class.java)