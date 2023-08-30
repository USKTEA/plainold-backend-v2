package com.usktea.plainoldv2.application.endpoints.file

import com.usktea.plainoldv2.domain.file.FileUploadResultDto
import com.usktea.plainoldv2.domain.file.application.FileService
import com.usktea.plainoldv2.exception.ErrorMessage
import com.usktea.plainoldv2.exception.ParameterNotFoundException
import com.usktea.plainoldv2.exception.UploadFileNotFoundException
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.created
import java.net.URI

@Component
class FileHandler(
    private val fileService: FileService
) {

    suspend fun upload(request: ServerRequest): ServerResponse {
        checkNotNull(request.attributeOrNull("username")) { ErrorMessage.REQUEST_ATTRIBUTE_NOT_FOUND.value }

        val filePart = request.awaitMultipartData()["file"]?.firstOrNull() ?: throw UploadFileNotFoundException()
        val folder = request.queryParamOrNull("folder") ?: throw ParameterNotFoundException()
        val url = fileService.upload(file = filePart, folder = folder)

        return created(URI.create(url)).contentType(MediaType.APPLICATION_JSON).bodyValueAndAwait(
            FileUploadResultDto(url)
        )
    }
}
