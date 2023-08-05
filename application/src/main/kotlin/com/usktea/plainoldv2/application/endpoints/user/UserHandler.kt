package com.usktea.plainoldv2.application.endpoints.user

import com.usktea.plainoldv2.domain.user.*
import com.usktea.plainoldv2.domain.user.application.UserService
import com.usktea.plainoldv2.exception.ParameterNotFoundException
import com.usktea.plainoldv2.exception.RequestAttributeNotFoundException
import com.usktea.plainoldv2.exception.RequestBodyNotFoundException
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.created
import org.springframework.web.reactive.function.server.ServerResponse.ok
import java.net.URI

@Component
class UserHandler(
    private val userService: UserService
) {
    suspend fun findMe(request: ServerRequest): ServerResponse {
        val username = extractAttribute(request)
        val userDetail = userService.getUserInformation(username)

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(userDetail)
    }

    suspend fun count(request: ServerRequest): ServerResponse {
        val username = request.queryParamOrNull("username") ?: throw ParameterNotFoundException()
        val count = userService.count(Username(username))

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(CountUserDto(count))
    }

    suspend fun signUp(request: ServerRequest): ServerResponse {
        val signUpRequest = request.awaitBodyOrNull<SignUpRequestDto>()?.let { SignUpRequest.from(it) }
            ?: throw RequestBodyNotFoundException()

        val signupResult = userService.signUp(signUpRequest).let { SignUpResultDto.from(it) }

        return created(URI.create("/users/${signupResult.id}")).contentType(MediaType.APPLICATION_JSON)
            .bodyValueAndAwait(signupResult)
    }

    private fun extractAttribute(request: ServerRequest): Username {
        val attribute = request.attributes()["username"]

        if (attribute == null || attribute !is Username) {
            throw RequestAttributeNotFoundException()
        }

        return attribute
    }
}
