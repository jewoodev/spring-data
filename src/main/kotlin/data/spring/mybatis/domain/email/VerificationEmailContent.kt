package data.spring.mybatis.domain.email

class VerificationEmailContent(
    val verificationCode: String = createVerificationCode()
) : EmailContent(
    title = "Email Verification",
    content = "Please verify your email address with code: $verificationCode"
) {
    companion object {
        private fun createVerificationCode(): String {
            return (0..999999).random().toString().padStart(6, '0')
        }
    }
}
