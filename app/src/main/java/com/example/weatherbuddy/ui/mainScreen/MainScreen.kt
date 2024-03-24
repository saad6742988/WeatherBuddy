package com.example.weatherbuddy.ui.mainScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherbuddy.R


@Composable
fun MainScreen()
{
    val viewModel:MainViewModel = hiltViewModel()
    MainScreenLayout()
}
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreenLayout()
{
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.sunny_background),
                contentScale = ContentScale.FillWidth
            )
    ) {
        val (searchBar,cityName) = createRefs()
        SearchBar(modifier = Modifier.constrainAs(searchBar){
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        })
        CityName(modifier = Modifier.constrainAs(cityName){
            top.linkTo(searchBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier)
{
    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = false,
        onActiveChange = {},
        placeholder = {
            Text(text = "Search City")
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null,
                modifier = Modifier.padding(top = 0.dp)
            )
        },
        tonalElevation = 0.dp,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(150.dp))
    ) {

    }
}
@Composable
fun CityName(modifier: Modifier = Modifier)
{
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = "Location")
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "City",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreenPrev()
{
    MainScreenLayout()
}