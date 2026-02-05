package data.spring.mybatis.adapter.out.persistence.member.typehandler

import data.spring.mybatis.domain.member.Email
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedTypes
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.jvm.java

@MappedTypes(Email::class)
class EmailTypeHandler : BaseTypeHandler<Email>() {
    override fun setNonNullParameter(
        ps: PreparedStatement?,
        i: Int,
        parameter: Email?,
        jdbcType: JdbcType?
    ) {
        ps?.setString(i, parameter?.value)
    }

    override fun getNullableResult(
        rs: ResultSet?,
        columnName: String?
    ): Email? {
        return rs?.getString(columnName)?.let { Email(it) }
    }

    override fun getNullableResult(
        rs: ResultSet?,
        columnIndex: Int
    ): Email? {
        return rs?.getString(columnIndex)?.let { Email(it) }
    }

    override fun getNullableResult(
        cs: CallableStatement?,
        columnIndex: Int
    ): Email? {
        return cs?.getString(columnIndex)?.let { Email(it) }
    }
}