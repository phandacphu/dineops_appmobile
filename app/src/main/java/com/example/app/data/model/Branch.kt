package com.example.app.data.model

data class Branch(
    var id: Int,
    var name: String,
    var address: String,
    var phone: String,
    var vat: Double,
    var serviceFee: Double
) {
    override fun toString(): String = name
}