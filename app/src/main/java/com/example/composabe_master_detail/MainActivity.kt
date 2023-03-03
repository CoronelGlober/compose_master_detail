package com.example.composabe_master_detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composabe_master_detail.routes.defineRoutes
import com.example.composabe_master_detail.routes.Routes
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

// Same size set in LogBaseFragment in dpi
internal const val MASTER_PANE_WIDTH = 300

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
                    defineRoutes(supportsMasterDetail, navController)
                }
            }
        }
    }
}
