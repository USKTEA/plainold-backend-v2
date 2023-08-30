package com.usktea.plainoldv2.domain.file.application

import com.usktea.plainoldv2.exception.ErrorMessage
import com.usktea.plainoldv2.exception.FileUploadFailException
import com.usktea.plainoldv2.exception.IllegalFileFormatException
import com.usktea.plainoldv2.properties.S3ClientProperties
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.Part
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.S3Utilities
import software.amazon.awssdk.services.s3.model.ObjectCannedACL
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectResponse
import java.nio.ByteBuffer
import java.util.*

@Service
class S3FileService(
    private val s3client: S3AsyncClient,
    private val s3Utilities: S3Utilities,
    private val s3ClientProperties: S3ClientProperties
) : FileService {
    override suspend fun upload(file: Part, folder: String): String {
        val filename = createFileName(file, folder)
        val mediaType = file.headers().contentType ?: MediaType.APPLICATION_OCTET_STREAM
        val byteArray = getByteArray(file)

        try {
            sendDataToS3(filename, mediaType, byteArray).awaitSingle()
        } catch (exception: Exception) {
            throw FileUploadFailException()
        }

        return s3Utilities.getUrl {
            it.bucket(s3ClientProperties.bucket).key(filename)
        }.toString()
    }

    private fun sendDataToS3(filename: String, mediaType: MediaType, byteArray: ByteArray): Mono<PutObjectResponse> {
        return Mono.fromFuture(
            s3client.putObject(
                PutObjectRequest.builder()
                    .bucket(s3ClientProperties.bucket)
                    .contentLength(byteArray.size.toLong())
                    .key(filename)
                    .contentType(mediaType.toString())
                    .metadata(mapOf("filename" to filename))
                    .acl(ObjectCannedACL.PUBLIC_READ)
                    .build(), AsyncRequestBody.fromBytes(byteArray)
            )
        )
    }

    private suspend fun getByteArray(file: Part): ByteArray {
        return file.content().map { dataBuffer ->
            val byteBuffer = ByteBuffer.allocate(dataBuffer.readableByteCount()).array()
            dataBuffer.read(byteBuffer)
            byteBuffer
        }.collectList().map { list ->
            list.fold(ByteArray(0)) { acc, byteArray ->
                acc + byteArray
            }
        }.awaitSingle()
    }

    private fun createFileName(file: Part, folder: String): String {
        return "$folder/" + UUID.randomUUID()
            .toString() + getFileExtension(file.headers().contentDisposition.filename)
    }

    private fun getFileExtension(filename: String?): String {
        requireNotNull(filename) { ErrorMessage.FILENAME_NOT_FOUND.value }

        try {
            return filename.substring(filename.lastIndexOf("."))
        } catch (exception: StringIndexOutOfBoundsException) {
            throw IllegalFileFormatException(filename)
        }
    }
}
