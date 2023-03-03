package com.example.composabe_master_detail.viewmodels

import androidx.lifecycle.ViewModel
import com.example.composabe_master_detail.DB
import com.example.composabe_master_detail.ui.theme.Drink
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

class CategoriesVM : ViewModel() {

    val state = MutableStateFlow<Drink?>(null)

    suspend fun load(id: Int) {
        delay(500)
        val children = DB.getByParent(id).map { it.copy(groups = DB.getByParent(it.id)) }
        state.emit(DB.getById(id).copy(groups = children))
    }
}