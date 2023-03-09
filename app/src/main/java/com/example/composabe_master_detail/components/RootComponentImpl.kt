package com.example.composabe_master_detail.components

import android.annotation.SuppressLint
import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.example.composabe_master_detail.components.RootComponent.Child.DetailsChild
import com.example.composabe_master_detail.components.RootComponent.Child.EmptyContent
import com.example.composabe_master_detail.components.RootComponent.Child.ListChild
import com.example.composabe_master_detail.viewmodels.CategoriesVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.parcelize.Parcelize

class RootComponentImpl(componentContext: ComponentContext) : RootComponent,
    ComponentContext by componentContext {
    override val vm: CategoriesVM = CategoriesVM()


    private val navigation = StackNavigation<Config>()

    private val _stack = childStack(
        source = navigation,
        initialConfiguration = Config.EmptyContent,
        handleBackButton = true,
        childFactory = ::child,
    )

    override val stack: Value<ChildStack<*, RootComponent.Child>> = _stack
    override val isMultiPane = MutableStateFlow(false)

    override fun setMultiPane(boolean: Boolean) {
       isMultiPane.value = boolean
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.EmptyContent -> EmptyContent(EmptyComponentImpl(componentContext))
            is Config.List -> ListChild(listComponent(componentContext))
            is Config.Details -> DetailsChild(
                detailsComponent(componentContext, config)
            )
        }

    private fun listComponent(componentContext: ComponentContext): ListComponent =
        ListChildImpl(
            componentContext = componentContext,
            { item: Int -> // Supply dependencies and callbacks
                navigation.push(Config.List(item = item)) // Push the details component
            },
            { item: Int -> // Supply dependencies and callbacks
                navigation.push(Config.Details(item = item)) // Push the details component
            },
        )

    private fun detailsComponent(
        componentContext: ComponentContext,
        config: Config.Details
    ): DetailsComponent =
        DetailsComponentImpl(
            componentContext = componentContext
        )

    @Parcelize
    @SuppressLint("ParcelCreator")
    private sealed interface Config : Parcelable {
        object EmptyContent : Config
        data class List(val item: Int) : Config
        data class Details(val item: Int) : Config
    }
}