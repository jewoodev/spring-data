package data.spring.mybatis.domain.email

fun createVerificationCode(): String {
    return (0..999999).random().toString().padStart(6, '0')
}

fun createVerificationEmail(vfcCode: String): EmailContent {
    return EmailContent(title = "Email Verification", content = "Please verify your email address with code: $vfcCode")
}

data class EmailContent(
    val title: String,
    val content: String
)
