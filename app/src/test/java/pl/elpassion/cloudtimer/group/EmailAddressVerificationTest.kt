package pl.elpassion.cloudtimer.group

import org.junit.Assert.*
import org.junit.Test
import pl.elpassion.cloudtimer.common.isEmailValid
import pl.elpassion.cloudtimer.common.splitAndTrimEmails


class EmailAddressVerificationTest {

    private val correctOneEmail = "email@example.com"
    private val incorrectEmail = "abcd"
    private val emptyEmail = ""
    private val emailWithWhiteSpaces = "email@example.com  "
    private val emails = "$correctOneEmail\n$incorrectEmail\n$emptyEmail\n$emailWithWhiteSpaces"



    @Test
    fun isEmailValidFunctionExecutes() {
        isEmailValid(correctOneEmail)
    }

    @Test
    fun isEmailValidReturnTrueIfEmailIsCorrect() {
        assertTrue(isEmailValid(correctOneEmail))
    }

    @Test
    fun isEmailValidReturnFalseOnEmptyEmail() {
        assertFalse(isEmailValid(emptyEmail))
    }

    @Test
    fun isEmailValidReturnFalseOnIncorrectEmail() {
        assertFalse(isEmailValid(incorrectEmail))
    }

    @Test
    fun isEmailValidReturnsTrueOnEmailWithWhiteSpaces() {
        assertTrue(isEmailValid(emailWithWhiteSpaces))
    }

    @Test
    fun splitEmailsReturnsListOfEmailsFromOneEmail() {
        assertEquals(1, splitAndTrimEmails(correctOneEmail).size)
    }

    @Test
    fun splitEmailsShouldReturnsEmptyListOnOneIncorrectEmail() {
        assertEquals(1, splitAndTrimEmails(incorrectEmail).size)
    }

    @Test
    fun splitEmailShouldReturnListOf2Emails() {
        assertEquals(4, splitAndTrimEmails(emails).size)
    }
}
