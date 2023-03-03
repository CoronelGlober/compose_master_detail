package com.example.composabe_master_detail

import android.content.res.AssetManager
import com.example.composabe_master_detail.ui.theme.Drink
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object DB {
    private lateinit var data: List<Drink>

    fun initialize(assetManager: AssetManager) {
        data = Json.decodeFromString(assetManager.readAssetsFile("drinks.json"))
    }


    fun getById(id: Int): Drink {
        return data.find { it.id == id }!!
    }

    fun getByParent(id: Int): List<Drink> {
        return data.filter { it.parentId == id }
    }

}

private fun AssetManager.readAssetsFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }