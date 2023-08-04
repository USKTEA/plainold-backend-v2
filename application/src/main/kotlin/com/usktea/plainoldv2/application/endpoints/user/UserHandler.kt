package com.usktea.plainoldv2.application.endpoints.user

import com.usktea.plainoldv2.domain.user.Username
import com.usktea.plainoldv2.domain.user.application.UserService
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import javax.management.AttributeNotFoundException

@Component
class UserHandler(
    private val userService: UserService
) {
    suspend fun findMe(request: ServerRequest): ServerResponse {
        val username = extractAttribute(request)
        val userDetail = userService.getUserInformation(username)

        return ok().contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(userDetail)
    }

    private fun extractAttribute(request: ServerRequest): Username {
        val attribute = request.attributes()["username"]

        if (attribute == null || attribute !is Username) {
            throw AttributeNotFoundException()
        }

        return attribute
    }
}
