package data.spring.mybatis.adapter.out.persistence.member.typehandler

import data.spring.mybatis.domain.member.Username
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import org.apache.ibatis.type.MappedTypes
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet
import kotlin.jvm.java

@MappedTypes(Username::class)
class UsernameTypeHandler : BaseTypeHandler<Username>() {
    override fun setNonNullParameter(
        ps: PreparedStatement?,
        i: Int,
        parameter: Username?,
        jdbcType: JdbcType?
    ) {
        ps?.setString(i, parameter?.value)
    }

    override fun getNullableResult(
        rs: ResultSet?,
        columnName: String?
    ): Username? {
        return rs?.getString(columnName)?.let { Username(it) }
    }

    override fun getNullableResult(
        rs: ResultSet?,
        columnIndex: Int
    ): Username? {
        return rs?.getString(columnIndex)?.let { Username(it) }
    }

    override fun getNullableResult(
        cs: CallableStatement?,
        columnIndex: Int
    ): Username? {
        return cs?.getString(columnIndex)?.let { Username(it) }
    }
}