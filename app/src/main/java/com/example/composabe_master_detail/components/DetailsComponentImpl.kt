package com.example.composabe_master_detail.components

import com.arkivanov.decompose.ComponentContext
import com.example.composabe_master_detail.viewmodels.CategoriesVM

class DetailsComponentImpl (
    componentContext: ComponentContext
) : DetailsComponent {
    override val vm: CategoriesVM = CategoriesVM()
}