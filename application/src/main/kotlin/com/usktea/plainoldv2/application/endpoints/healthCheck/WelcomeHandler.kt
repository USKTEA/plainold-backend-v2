package com.usktea.plainoldv2.application.endpoints.healthCheck

import com.usktea.plainoldv2.domain.option.repository.OptionRepository
import com.usktea.plainoldv2.domain.product.repository.ProductRepository
import com.usktea.plainoldv2.domain.user.repository.UserRepository
import com.usktea.plainoldv2.utils.JwtUtil
import org.springframework.http.MediaType
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait

@Component
class WelcomeHandler(
    val productRepository: ProductRepository,
    val optionRepository: OptionRepository,
    val userRepository: UserRepository,
    val passwordEncoder: PasswordEncoder,
    val jwtUtil: JwtUtil
) {
    suspend fun welcome(request: ServerRequest): ServerResponse {
        //TODO 지워야한다.
//        Product.fake().also {
//            productRepository.save(it)
//        }
//
        optionRepository.deleteAll()
//
//        OptionData.fake().also {
//            optionRepository.save(it)
//        }

//        User.fake().also {
//            it.changePassword(it.password, passwordEncoder)
//            userRepository.save(it)
//        }

        return ok().contentType(MediaType.TEXT_PLAIN).bodyValueAndAwait("welcome")
    }
}
