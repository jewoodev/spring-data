package data.spring.mybatis.domain.member

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemberTest {
    private lateinit var password: String
    private lateinit var member: Member
    private lateinit var passwordEncoder: PasswordEncoder

    @BeforeEach
    fun setUp() {
        password = "testPassword"
        member = MemberFixture.createMember(password)
        passwordEncoder = MemberFixture.createPasswordEncoder()
    }

    @Test
    fun registerSuccessfully() {
        assertThat(member.password).isEqualTo(passwordEncoder.encode(password))
        assertThat(member.role).isEqualTo(Role.UNVERIFIED)
    }

    @Test
    fun activateSuccessfully() {
        member.activate()
        assertThat(member.role).isEqualTo(Role.BUYER)
    }

    @Test
    fun activateWhenAlreadyActivated() {
        member.activate()
        Assertions.assertThatThrownBy { member.activate() }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("이미 활성화된 회원입니다.")
    }

    @Test
    fun changePasswordSuccessfully() {
        val newPassword = "newTestPassword"
        member.changePassword(newPassword, passwordEncoder)
        assertThat(member.password).isEqualTo(passwordEncoder.encode(newPassword))
    }
}