package com.usktea.plainoldv2.domain.cart

import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity

@Entity
class Cart(
    id: Long = 0L,

    val userId: Long,

    @ElementCollection
    val cartItems:MutableList<CartItem> = mutableListOf()
): BaseEntity(id)
