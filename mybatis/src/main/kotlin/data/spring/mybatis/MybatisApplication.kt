package data.spring.mybatis

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MybatisApplication {
    fun main(args: Array<String>) {
        SpringApplication.run(MybatisApplication::class.java, *args)
    }
}