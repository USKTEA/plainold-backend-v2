package com.usktea.plainoldv2.domain.file.application

import org.springframework.http.codec.multipart.Part

interface FileService {
    suspend fun upload(file: Part, folder: String): String
}
