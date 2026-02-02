package data.spring.mybatis.domain

import java.time.Clock
import java.time.Duration

fun clock(): Clock {
    return Clock.systemDefaultZone()
}

fun testClock(): Clock {
    return Clock.tick(Clock.systemUTC(), Duration.ofMillis(1).dividedBy(1000))
}