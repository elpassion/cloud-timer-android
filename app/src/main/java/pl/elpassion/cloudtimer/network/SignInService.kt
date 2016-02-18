package pl.elpassion.cloudtimer.network

import rx.Observable


interface SignInService {
    fun singIn(email: String): Observable<Any>
}

var myService = object : SignInService {
    override fun singIn(email: String): Observable<Any> {
        return Observable.just(Any())
    }
}