package com.example.weatherbuddy.ui.mainScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherbuddy.R


@Composable
fun MainScreen()
{
    val viewModel:MainViewModel = hiltViewModel()
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.sunny_background),
                contentScale = ContentScale.FillWidth
            )
    ) {

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreenPrev()
{
    MainScreen()
}