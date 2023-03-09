package com.example.composabe_master_detail.components

import com.arkivanov.decompose.ComponentContext
import com.example.composabe_master_detail.viewmodels.CategoriesVM
import kotlinx.coroutines.runBlocking

class DetailsComponentImpl (
    componentContext: ComponentContext,
    override val currentId: Int
) : DetailsComponent {

    override val vm: CategoriesVM = CategoriesVM()
    init {
        runBlocking {
            vm.load(currentId)
        }
    }
}