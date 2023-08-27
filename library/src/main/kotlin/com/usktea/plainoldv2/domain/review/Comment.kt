package com.usktea.plainoldv2.domain.review

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Comment(
    @Column(name = "comment")
    val value: String
) {
    companion object {
        fun from(value: String): Comment {
            return Comment(value)
        }
    }
}
