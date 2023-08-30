package com.usktea.plainoldv2.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class S3ClientProperties(
    @Value("\${cloud.aws.s3.bucket}")
    val bucket: String,

    @Value("\${cloud.aws.credentials.access-key}")
    val accessKey: String,

    @Value("\${cloud.aws.credentials.secret-key}")
    val secretKey: String,

    @Value("\${cloud.aws.region.static}")
    val region: String,
)
