package com.example.composabe_master_detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetpack.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.arkivanov.decompose.router.stack.backStack
import com.example.composabe_master_detail.components.RootComponent
import com.example.composabe_master_detail.components.RootComponentImpl
import com.example.composabe_master_detail.composables.CategoryList
import com.example.composabe_master_detail.composables.EmptyContent
import com.example.composabe_master_detail.composables.StatisticsList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB.initialize(applicationContext.assets)
        val root = RootComponentImpl(componentContext = defaultComponentContext())
        setContent {
            MaterialTheme {
                Surface {
                    RootContent(component = root, modifier = Modifier.fillMaxSize())
                }
            }
        }
    }

    @Composable
    fun RootContent(component: RootComponent, modifier: Modifier = Modifier) {
        val isMultiPane by component.isMultiPane.collectAsState()
        val stack by component.stack.subscribeAsState()
        BoxWithConstraints(modifier = modifier) {
            when {
                isMultiPane -> Row(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.4F)
                    ) {
                        CategoryList(component.initialListComponent)
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(0.6F)
                    ) {
                        Children(stack = stack) {
                            when (val child = it.instance) {
                                is RootComponent.Child.DetailsChild -> StatisticsList(child.component)
                                is RootComponent.Child.EmptyContent -> EmptyContent()
                                is RootComponent.Child.ListChild -> CategoryList(child.component)
                            }
                        }
                    }
                }

                else -> {
                    if (stack.backStack.size > 1) {
                        Children(stack = stack, animation = stackAnimation(fade() + scale())) {
                            when (val child = it.instance) {
                                is RootComponent.Child.DetailsChild -> StatisticsList(child.component)
                                is RootComponent.Child.EmptyContent -> EmptyContent()
                                is RootComponent.Child.ListChild -> CategoryList(child.component)
                            }
                        }
                    } else {
                        CategoryList(component.initialListComponent)
                    }
                }

            }
            val isMultiPaneRequired = this@BoxWithConstraints.maxWidth >= 800.dp

            DisposableEffect(isMultiPaneRequired) {
                component.setMultiPane(isMultiPaneRequired)
                onDispose {}
            }
        }
    }
}
