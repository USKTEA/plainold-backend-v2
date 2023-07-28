package com.usktea.plainoldv2

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlainOldApplication

fun main(args: Array<String>) {
	runApplication<PlainOldApplication>(*args)
}
