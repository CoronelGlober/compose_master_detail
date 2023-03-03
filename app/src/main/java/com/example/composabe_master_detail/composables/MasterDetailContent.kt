package com.example.composabe_master_detail.composables

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import com.example.composabe_master_detail.routes.Routes
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composabe_master_detail.MASTER_PANE_WIDTH
import com.example.composabe_master_detail.routes.defineRoutes
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MasterDetailContent(id: Int) {
    val childNavController = rememberAnimatedNavController()
    Row {
        Column(
            modifier = Modifier
                .width(MASTER_PANE_WIDTH.dp)
                .fillMaxHeight()
        ) {
            CategoryList(id = id,
                categoriesHeader = {
                    Text(
                        text = "Drinks of the world",
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                navigateToChildren = { childNavController.navigate(Routes.CATEGORIES.withArgs(it.toString())) },
                showCategoryStatistics = { childNavController.navigate(Routes.DETAILS.withArgs(it.toString())) }
            )
        }

        Box(
            modifier = Modifier
                .width(2.dp)
                .fillMaxHeight()
                .background(color = Color.Gray)
        )


        AnimatedNavHost(
            modifier = Modifier.weight(1f),
            navController = childNavController,
            startDestination = Routes.DETAILS.route
        ) {
            defineRoutes(false, childNavController)
        }
    }
}
