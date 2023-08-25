package com.usktea.plainoldv2.domain.review

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class ImageUrl(
    @Column(name = "imageUIrl")
    val value: String
)
