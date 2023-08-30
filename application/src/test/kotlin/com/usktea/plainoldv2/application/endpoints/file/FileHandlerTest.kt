package com.usktea.plainoldv2.application.endpoints.file

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.SpykBean
import com.usktea.plainoldv2.domain.file.application.FileService
import com.usktea.plainoldv2.utils.JwtUtil
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

const val FOLDER = "review-image"
const val USERNAME = "tjrxo1234@gmail.com"
const val URL = "www.aaa.com"

@WebFluxTest(FileRouter::class, FileHandler::class)
class FileHandlerTest {
    @Autowired
    private lateinit var client: WebTestClient

    @MockkBean
    private lateinit var fileService: FileService

    @SpykBean
    private lateinit var jwtUtil: JwtUtil

    @Test
    fun `파일을 업로드하면 파일 경로를 응답 값으로 내려준다`() {
        val token = jwtUtil.encode(USERNAME)

        val formData = MockMultipartFile(
            "file", "image.png", MediaType.IMAGE_PNG_VALUE, "image".toByteArray()
        )
        val bodyBuilder = MultipartBodyBuilder()
        bodyBuilder.part("file", formData.resource)

        coEvery { fileService.upload(any(), any()) } returns URL

        client.mutateWith(csrf()).mutateWith(mockUser())
            .post()
            .uri("/files?folder=$FOLDER")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .header("Authorization", "Bearer $token")
            .body(BodyInserters.fromMultipartData(bodyBuilder.build()))
            .exchange()
            .expectStatus().isCreated
            .expectBody()
            .jsonPath("$.url").isEqualTo(URL)
    }
}
