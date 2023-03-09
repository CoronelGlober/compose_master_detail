package com.example.composabe_master_detail.components

import com.arkivanov.decompose.ComponentContext
import com.example.composabe_master_detail.viewmodels.CategoriesVM

class ListChildImpl(
    componentContext: ComponentContext,
    private val navigateToChildren: (Int) -> Unit,
    private val showCategoryStatistics: (Int) -> Unit
) : ListComponent {
    override val vm: CategoriesVM = CategoriesVM()

    override fun navigateToChildren(id: Int): Unit = navigateToChildren(id)

    override fun showCategoryStatistics(id: Int): Unit = showCategoryStatistics(id)
}