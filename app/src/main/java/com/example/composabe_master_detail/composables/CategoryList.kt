package com.example.composabe_master_detail.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composabe_master_detail.R
import com.example.composabe_master_detail.components.ListComponent
import com.example.composabe_master_detail.viewmodels.CategoriesVM

@Composable
fun CategoryList(
    component: ListComponent,
    categoriesHeader: @Composable (() -> Unit)? = null,
) {
    val state by component.vm.state.collectAsState()
    Column {
        if (categoriesHeader != null) {
            categoriesHeader()
        } else {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(text = state?.name.orEmpty(), style = MaterialTheme.typography.headlineLarge)
                Button(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { component.showCategoryStatistics(component.currentId) },
                ) {
                    Text(text = "Show sales")
                }
            }
        }
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
                        .clickable {
                            if (drink.groups.isNotEmpty()) {
                                component.navigateToChildren(drink.id)
                            } else {
                                component.showCategoryStatistics(drink.id)
                            }
                        }
                        .padding(15.dp)
                    ) {
                        Text(text = drink.name)
                        val rightIcon =
                            if (drink.groups.isNotEmpty()) R.drawable.arrow_right else R.drawable.chart
                        Icon(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            painter = painterResource(id = rightIcon),
                            contentDescription = ""
                        )
                    }
                }
            }
        }
    }
}