package com.example.composabe_master_detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.composabe_master_detail.Routes.CATEGORIES
import com.example.composabe_master_detail.Routes.DETAILS
import com.example.composabe_master_detail.composables.CategoryList
import com.example.composabe_master_detail.composables.StatisticsList
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

// Same size set in LogBaseFragment in dpi
internal const val MASTER_PANE_WIDTH = 320

// Same size used in the xml configuration file in dpi
internal const val MASTER_DETAIL_MINIMUM_SCREEN_WIDTH = 738

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DB.initialize(applicationContext.assets)
        setContent {

            val navController = rememberAnimatedNavController()
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val supportsMasterDetail = maxWidth > MASTER_DETAIL_MINIMUM_SCREEN_WIDTH.dp
                AnimatedNavHost(
                    modifier = Modifier,
                    navController = navController,
                    startDestination = Routes.MASTER.route
                ) {
                    setupScreens(supportsMasterDetail, navController)
                }
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class)
private fun NavGraphBuilder.setupScreens(
    supportsMasterDetail: Boolean,
    navController: NavController
) {

    composable(Routes.MASTER.route) {
        if (supportsMasterDetail) {
            MasterDetailContent(id = 0)
        } else {
            CategoryList(id = 0) {
                navController.navigate(CATEGORIES.withArgs(it.toString()))
            }
        }
    }

    composable(CATEGORIES.route) {
        val categoryUid = it.arguments?.getString(CATEGORIES.arg).orEmpty()
        CategoryList(id = categoryUid.toInt()) {
            navController.navigate(CATEGORIES.withArgs(it.toString()))
        }
    }

    composable(DETAILS.route) {
        val categoryUid = it.arguments?.getString(DETAILS.arg).orEmpty()
        StatisticsList(id = categoryUid.toIntOrNull() ?: 0)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MasterDetailContent(id: Int) {

    val childNavController = rememberAnimatedNavController()
    Row {
        Box(
            modifier = Modifier
                .width(MASTER_PANE_WIDTH.dp)
                .fillMaxHeight()
        ) {
            CategoryList(id = id) {
                childNavController.navigate(CATEGORIES.withArgs(id.toString()))
            }
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
            setupScreens(false, childNavController)
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