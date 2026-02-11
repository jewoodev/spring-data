package data.spring.mybatis.domain.member

import data.spring.mybatis.adapter.`in`.member.SimplePasswordEncoder
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class MemberTest {
    private val password = "testPassword"
    private val passwordEncoder = SimplePasswordEncoder()
    private val duplicationVerifier = MemberDuplicationVerifier { username, email -> false }

    @Test
    fun `register member successfully`() {
        val sut = Member.register(username = "testUser",
            email = "test@example.com",
            password = password,
            passwordEncoder = passwordEncoder,
            duplicationVerifier = duplicationVerifier
        )

        assertThat(sut.role).isEqualTo(Role.UNVERIFIED)
        assertThat(
            passwordEncoder.matches(rawPassword = password, encodedPassword = sut.password.value)
        ).isTrue()
    }

    @Test
    fun `activate member successfully`() {
        val sut = Member.register(username = "testUser",
            email = "test@example.com",
            password = password,
            passwordEncoder = passwordEncoder,
            duplicationVerifier = duplicationVerifier
        )

        val isActivated = sut.activate()

        assertThat(isActivated.role).isEqualTo(Role.BUYER)
    }

    @Test
    fun `throw exception when member is already activated`() {
        val sut = Member.register(username = "testUser",
            email = "test@example.com",
            password = password,
            passwordEncoder = passwordEncoder,
            duplicationVerifier = duplicationVerifier
        )

        val isActivated = sut.activate()

        assertThatThrownBy { isActivated.activate() }
            .isInstanceOf(IllegalStateException::class.java)
            .hasMessage("이미 활성화된 회원입니다.")
    }

    @Test
    fun `change member's password successfully`() {
        val sut = Member.register(username = "testUser",
            email = "test@example.com",
            password = password,
            passwordEncoder = passwordEncoder,
            duplicationVerifier = duplicationVerifier
        )
        val newPassword = "newTestPassword"

        val changed = sut.changePassword(newPassword, passwordEncoder)

        assertThat(
            passwordEncoder.matches(rawPassword = newPassword, encodedPassword = changed.password.value)
        ).isTrue()
    }

    @Test
    fun `leave member successfully`() {
        val sut = Member.register(username = "testUser",
            email = "test@example.com",
            password = password,
            passwordEncoder = passwordEncoder,
            duplicationVerifier = duplicationVerifier
        )

        val leftMember = sut.leave()

        assertThat(leftMember.leftAt).isNotNull()
        assertThat(leftMember.updatedAt).isAfter(leftMember.leftAt)
    }
}