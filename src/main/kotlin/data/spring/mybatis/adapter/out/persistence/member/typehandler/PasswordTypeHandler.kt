package data.spring.mybatis.adapter.out.persistence.member.typehandler

import data.spring.mybatis.domain.member.Password
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedTypes
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.jvm.java

@MappedTypes(Password::class)
class PasswordTypeHandler : BaseTypeHandler<Password>() {
    override fun setNonNullParameter(
        ps: PreparedStatement?,
        i: Int,
        parameter: Password?,
        jdbcType: JdbcType?
    ) {
        ps?.setString(i, parameter?.value)
    }

    override fun getNullableResult(
        rs: ResultSet?,
        columnName: String?
    ): Password? {
        return rs?.getString(columnName)?.let { Password(it) }
    }

    override fun getNullableResult(
        rs: ResultSet?,
        columnIndex: Int
    ): Password? {
        return rs?.getString(columnIndex)?.let { Password(it) }
    }

    override fun getNullableResult(
        cs: CallableStatement?,
        columnIndex: Int
    ): Password? {
        return cs?.getString(columnIndex)?.let { Password(it) }
    }
}