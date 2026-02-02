package data.spring.mybatis.adapter.out.persistence

import data.spring.mybatis.domain.clock
import java.time.LocalDateTime

abstract class BaseEntity(
    val createdAt: LocalDateTime = LocalDateTime.now(clock()),
    val updatedAt: LocalDateTime = LocalDateTime.now(clock())
)