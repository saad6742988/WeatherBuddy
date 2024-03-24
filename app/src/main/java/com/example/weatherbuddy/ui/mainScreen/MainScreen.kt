package com.example.weatherbuddy.ui.mainScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherbuddy.R
import kotlin.reflect.KFunction1


@Composable
fun MainScreen()
{
    val viewModel:MainViewModel = hiltViewModel()
    val isSearching = viewModel.isSearching.collectAsState()
    val citiesList = viewModel.citiesList.collectAsState()
    val searchText = viewModel.searchText.collectAsState()
    val selectedCity = viewModel.selectedCity.collectAsState()
    MainScreenLayout(
        searchText,
        isSearching,
        citiesList,
        viewModel::onSearchTextChange,
        viewModel::onToggleSearch,
        viewModel::setSelectedCity,
        selectedCity
    )
}
@Composable
fun MainScreenLayout(
    searchText: State<String>,
    isSearching: State<Boolean>,
    citiesList: State<Any>,
    onSearchTextChange: KFunction1<String, Unit>,
    onToggleSearch: KFunction1<Boolean, Unit>,
    setSelectedCity: (String) -> Unit,
    selectedCity: State<String>,
)
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
        SearchBar(
            searchText,
            isSearching,
            citiesList,
            onSearchTextChange,
            onToggleSearch,
            setSelectedCity,
            modifier = Modifier.constrainAs(searchBar){
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        })
        CityName(
            selectedCity,
            modifier = Modifier.constrainAs(cityName){
            top.linkTo(searchBar.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: State<String>,
    isSearching: State<Boolean>,
    citiesList: State<Any>,
    onSearchTextChange: KFunction1<String, Unit>,
    onToggleSearch: KFunction1<Boolean, Unit>,
    setSelectedCity: (String) -> Unit,
    modifier: Modifier = Modifier
)
{
    Log.d("citiesList", "SearchBar: ${citiesList.value}")
    SearchBar(
        query = searchText.value,
        onQueryChange = onSearchTextChange,
        onSearch = onSearchTextChange,
        active = isSearching.value,
        onActiveChange =  onToggleSearch ,
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
//            .height(56.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
            LazyColumn {
                items(citiesList.value as List<*>) { city ->
                    Text(
                        text = city.toString(),
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                Log.d("SearchBar", "SearchBar: $city")
                                setSelectedCity(city.toString())
                                onToggleSearch(false)
                            }
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                    Spacer(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                }
        }

    }
}
@Composable
fun CityName(selectedCity: State<String>, modifier: Modifier = Modifier)
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
            text = selectedCity.value,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreenPrev()
{
    val viewModel = DummyModel()
//    val viewModel:MainViewModel = hiltViewModel()
    MainScreenLayout(
        viewModel.searchText.collectAsState(),
        viewModel.isSearching.collectAsState(),
        viewModel.citiesList.collectAsState(),
        viewModel::onSearchTextChange,
        viewModel::onToggleSearch,
        viewModel::setSelectedCity,
        viewModel.selectedCity.collectAsState()
    )
}