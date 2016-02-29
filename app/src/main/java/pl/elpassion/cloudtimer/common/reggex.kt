package pl.elpassion.cloudtimer.common

val emailRegex by lazy { Regex("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$") }


val isEmailValid = fun(email: String): Boolean {
    return emailRegex.matches(email.trim())
}

val splitAndTrimEmails = fun(emailString: String): List<String> = emailString.split("[\\n\\s\\r]+".toRegex())
