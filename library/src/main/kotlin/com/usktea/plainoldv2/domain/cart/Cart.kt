package com.usktea.plainoldv2.domain.cart

import com.usktea.plainoldv2.exception.CartItemNotFoundException
import com.usktea.plainoldv2.support.BaseEntity
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity

@Entity
class Cart(
    id: Long = 0L,

    val userId: Long,

    @ElementCollection
    var cartItems: MutableList<CartItem> = mutableListOf()
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
                false -> increaseQuantity(found, it)
            }
        }
    }

    fun updateItems(items: List<CartItem>): List<Long> {
        return items.map {
            val found = findSameItem(it) ?: throw CartItemNotFoundException()

            updateQuantity(found, it)

            it.productId
        }
    }

    private fun updateQuantity(found: CartItem, current: CartItem) {
        val index = this.cartItems.indexOf(found)
        val updated = found.updateQuantity(current.quantity)

        this.cartItems[index] = updated
    }

    private fun increaseQuantity(found: CartItem, current: CartItem) {
        val index = this.cartItems.indexOf(found)
        val increased = found.increaseQuantity(current.quantity)

        this.cartItems[index] = increased
    }

    fun firstItem(): CartItem {
        return this.cartItems.first()
    }

    private fun findSameItem(item: CartItem): CartItem? {
        return this.cartItems.firstOrNull { it.checkIsSame(item) }
    }

    fun deleteItems(items: List<CartItem>): List<Long> {
        return items.map {
            val found = findSameItem(it) ?: throw CartItemNotFoundException()

            this.cartItems.remove(found)

            found.productId
        }
    }

    companion object {
        val NULL = Cart(userId = 0L, cartItems = mutableListOf())
    }
}
