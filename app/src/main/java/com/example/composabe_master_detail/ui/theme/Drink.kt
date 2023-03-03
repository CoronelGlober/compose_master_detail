package com.example.composabe_master_detail.ui.theme

import kotlinx.serialization.Serializable

@Serializable
data class Drink(
    val id: Int,
    val parentId: Int,
    val name: String,
    val groups: List<Drink>,
    val countrySales: List<String>
)

