package com.usktea.plainoldv2.application.config

import com.usktea.plainoldv2.properties.S3ClientProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider
import software.amazon.awssdk.http.async.SdkAsyncHttpClient
import software.amazon.awssdk.http.nio.netty.NettyNioAsyncHttpClient
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.S3Configuration
import software.amazon.awssdk.services.s3.S3Utilities
import java.time.Duration

@Configuration
class AwsS3Configuration(
    private val s3ClientProperties: S3ClientProperties
) {

    @Bean
    fun s3AsyncClient(awsCredentialsProvider: AwsCredentialsProvider): S3AsyncClient {
        return S3AsyncClient.builder()
            .httpClient(sdkAsyncHttpClient())
            .region(Region.of(s3ClientProperties.region))
            .credentialsProvider(awsCredentialsProvider)
            .forcePathStyle(true)
            .serviceConfiguration(s3Configuration())
            .build()
    }

    private fun s3Configuration(): S3Configuration {
        return S3Configuration.builder()
            .checksumValidationEnabled(false)
            .chunkedEncodingEnabled(true)
            .build()
    }

    private fun sdkAsyncHttpClient(): SdkAsyncHttpClient {
        return NettyNioAsyncHttpClient.builder()
            .writeTimeout(Duration.ZERO)
            .maxConcurrency(64)
            .build()
    }

    @Bean
    fun s3Utilities(): S3Utilities {
        return S3Utilities.builder()
            .region(Region.of(s3ClientProperties.region))
            .build()
    }

    @Bean
    fun awsCredentialProvider(): AwsCredentialsProvider = AwsCredentialsProvider {
        AwsBasicCredentials.create(s3ClientProperties.accessKey, s3ClientProperties.secretKey)
    }
}
