package com.usktea.plainoldv2.domain.cart

import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity

@Entity
class Cart(
    id: Long = 0L,

    val userId: Long,

    @ElementCollection
    val cartItems: MutableList<CartItem> = mutableListOf()
) : BaseEntity(id) {
    fun isEmpty(): Boolean {
        return this.cartItems.isEmpty()
    }

    fun countItems(): Int {
        return this.cartItems.size
    }

    fun addItems(items: List<CartItem>) {
        items.forEach {
            val found = findSameItem(it)

            when (found == null) {
                true -> this.cartItems.add(it)
                false -> updateItem(found, it)
            }
        }
    }

    private fun updateItem(found: CartItem, current: CartItem) {
        val index = this.cartItems.indexOf(found)
        val increased = found.increaseQuantity(current.quantity)

        this.cartItems.removeAt(index)
        this.cartItems.add(index, increased)
    }

    fun firstItem(): CartItem {
        return this.cartItems.first()
    }

    private fun findSameItem(item: CartItem): CartItem? {
        return this.cartItems.firstOrNull { it.checkIsSame(item) }
    }
}
