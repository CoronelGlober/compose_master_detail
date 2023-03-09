package com.example.composabe_master_detail.components

import com.arkivanov.decompose.ComponentContext
import com.example.composabe_master_detail.viewmodels.CategoriesVM
import kotlinx.coroutines.runBlocking

class ListChildImpl(
    componentContext: ComponentContext,
    private val _navigateToChildren: (Int) -> Unit,
    private val _showCategoryStatistics: (Int) -> Unit,
    override val currentId: Int
) : ListComponent {

    override val vm: CategoriesVM = CategoriesVM()

    init {
        runBlocking {
            vm.load(currentId)
        }
    }
    override fun navigateToChildren(id: Int): Unit = _navigateToChildren(id)


    override fun showCategoryStatistics(id: Int): Unit = _showCategoryStatistics(id)
}