package com.example.composabe_master_detail.components

import android.annotation.SuppressLint
import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
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
    override val initialListComponent: ListComponent = listComponent(childContext("asd"), 0)

    override fun setMultiPane(boolean: Boolean) {
        isMultiPane.value = boolean
    }

    private fun child(config: Config, componentContext: ComponentContext): RootComponent.Child =
        when (config) {
            is Config.EmptyContent -> EmptyContent(EmptyComponentImpl(componentContext.childContext("no_content")))
            is Config.List -> ListChild(listComponent(componentContext.childContext(config.item.toString()), config.item))
            is Config.Details -> DetailsChild(
                detailsComponent(componentContext.childContext(config.item.toString()), config)
            )
        }

    private fun listComponent(componentContext: ComponentContext, currentId: Int): ListComponent =
        ListChildImpl(
            componentContext = componentContext,
            { item: Int -> // Supply dependencies and callbacks
                navigation.push(Config.List(item = item)) // Push the details component
            },
            { item: Int -> // Supply dependencies and callbacks
                navigation.push(Config.Details(item = item)) // Push the details component
            },
            currentId = currentId
        )

    private fun detailsComponent(
        componentContext: ComponentContext,
        config: Config.Details
    ): DetailsComponent =
        DetailsComponentImpl(
            componentContext = componentContext,
            config.item
        )

    @Parcelize
    @SuppressLint("ParcelCreator")
    private sealed interface Config : Parcelable {
        object EmptyContent : Config
        data class List(val item: Int, val salt:Double = Math.random()) : Config
        data class Details(val item: Int, val salt:Double = Math.random()) : Config
    }
}