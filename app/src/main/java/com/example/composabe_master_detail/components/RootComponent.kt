package com.example.composabe_master_detail.components

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.composabe_master_detail.ui.theme.Drink
import com.example.composabe_master_detail.viewmodels.CategoriesVM
import kotlinx.coroutines.flow.StateFlow

interface RootComponent {
    val initialListComponent: ListComponent
    val vm: CategoriesVM
    val stack: Value<ChildStack<*, Child>>
    val isMultiPane: StateFlow<Boolean>

    fun setMultiPane(boolean: Boolean)

    sealed class Child {
        class EmptyContent(val component: EmptyComponent): Child()
        class ListChild(val component: ListComponent) : Child()
        class DetailsChild(val component: DetailsComponent) : Child()
    }
}

interface ListComponent {
    val currentId:Int
    val vm: CategoriesVM
    fun navigateToChildren(id: Int)
    fun showCategoryStatistics(id: Int)
}

interface DetailsComponent {
    val vm: CategoriesVM
    val currentId:Int
}

interface EmptyComponent