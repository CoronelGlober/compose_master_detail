package com.example.composabe_master_detail.composables

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composabe_master_detail.viewmodels.CategoriesVM

@Composable
fun CategoryList(id: Int, itemClicked: (Int) -> Unit) {
    val categoriesVM = viewModel<CategoriesVM>(key = id.toString())
    LaunchedEffect(key1 = categoriesVM) {
        categoriesVM.load(id)
    }
    val currentContext = LocalContext.current
    val state by categoriesVM.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state == null) {
            CircularProgressIndicator()
        } else {
            state!!.groups.forEach { drink ->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = drink.groups.isNotEmpty()) {
                        Toast
                            .makeText(
                                currentContext,
                                "Clicked ${drink.name}!",
                                Toast.LENGTH_LONG
                            )
                            .show()
                        itemClicked(drink.id)
                    }
                    .padding(15.dp)
                ) {
                    val rightArrow = if (drink.groups.isNotEmpty()) " ->" else ""
                    Text(text = drink.name + rightArrow)
                }
            }
        }
    }
}