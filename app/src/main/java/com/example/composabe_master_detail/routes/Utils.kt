package com.example.composabe_master_detail.routes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.example.composabe_master_detail.composables.CategoryList
import com.example.composabe_master_detail.composables.EmptyContent
import com.example.composabe_master_detail.composables.MasterDetailContent
import com.example.composabe_master_detail.composables.StatisticsList
import com.example.composabe_master_detail.routes.Routes.CATEGORIES
import com.example.composabe_master_detail.routes.Routes.DETAILS
import com.google.accompanist.navigation.animation.composable

@OptIn(ExperimentalAnimationApi::class)
internal fun NavGraphBuilder.defineRoutes(
    supportsMasterDetail: Boolean,
    navController: NavController
) {

    composable(Routes.MASTER.route) {
        if (supportsMasterDetail) {
            MasterDetailContent(id = 0)
        } else {
            CategoryList(id = 0,
                categoriesHeader = {
                    Text(
                        text = "Drinks of the world",
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                navigateToChildren = { navController.navigate(CATEGORIES.withArgs(it.toString())) },
                showCategoryStatistics = { navController.navigate(DETAILS.withArgs(it.toString())) }
            )
        }
    }

    composable(CATEGORIES.route) {
        val categoryUid = it.arguments?.getString(CATEGORIES.arg).orEmpty()
        CategoryList(id = categoryUid.toInt(),
            navigateToChildren = { navController.navigate(CATEGORIES.withArgs(it.toString())) },
            showCategoryStatistics = { navController.navigate(DETAILS.withArgs(it.toString())) }
        )
    }

    composable(DETAILS.route) {
        val categoryUid = it.arguments?.getString(DETAILS.arg).orEmpty()
        val categoryId = categoryUid.toIntOrNull() ?: 0
        if (categoryId == 0) {
            EmptyContent()
        } else {
            StatisticsList(id = categoryId)
        }
    }
}


sealed class Routes(val route: String, val arg: String) {
    object CATEGORIES : Routes("categories_list/{uid}", "uid")
    object DETAILS : Routes("category_details/{uid}", "uid")
    object MASTER : Routes("master", "")

    fun withArgs(args: String): String {
        return route.replace("{$arg}", args)
    }
}