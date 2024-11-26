package com.example.weatherbuddy.ui.mainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.weatherbuddy.R
import com.example.weatherbuddy.openWeatherService.CityData
import com.example.weatherbuddy.openWeatherService.WeatherData
import kotlin.math.round
import kotlin.reflect.KFunction1


@Composable
fun MainScreen()
{
    val viewModel:MainViewModel = hiltViewModel()
    val isSearching = viewModel.isSearching.collectAsState()
    val citiesList = viewModel.citiesList.collectAsState()
    val searchText = viewModel.searchText.collectAsState()
    val selectedCity = viewModel.selectedCity.collectAsState()
    val weatherData = viewModel.weatherData.collectAsState()
    MainScreenLayout(
        searchText,
        isSearching,
        citiesList,
        viewModel::onSearchTextChange,
        viewModel::onToggleSearch,
        viewModel::setSelectedCity,
        selectedCity,
        weatherData
    )
}
@Composable
fun MainScreenLayout(
    searchText: State<String>,
    isSearching: State<Boolean>,
    citiesList: State<List<CityData>>,
    onSearchTextChange: KFunction1<String, Unit>,
    onToggleSearch: KFunction1<Boolean, Unit>,
    setSelectedCity: KFunction1<CityData, Unit>,
    selectedCity: State<CityData>,
    weatherData: State<WeatherData>,
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
        val (searchBar,cityName,weatherDetails) = createRefs()
        val guidelineTop = createGuidelineFromTop(80.dp)
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
            top.linkTo(guidelineTop)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })
        WeatherDetails(
            weatherData,
            modifier = Modifier.constrainAs(weatherDetails){
                top.linkTo(cityName.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.wrapContent
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: State<String>,
    isSearching: State<Boolean>,
    citiesList: State<List<CityData>>,
    onSearchTextChange: KFunction1<String, Unit>,
    onToggleSearch: KFunction1<Boolean, Unit>,
    setSelectedCity: KFunction1<CityData, Unit>,
    modifier: Modifier = Modifier
)
{
    Log.d("citiesList", "SearchBar: ${citiesList.value}")
    DockedSearchBar(
        query = searchText.value,
        onQueryChange = onSearchTextChange,
        onSearch = onSearchTextChange,
        active = isSearching.value,
        onActiveChange =  onToggleSearch ,
        colors = SearchBarDefaults.colors(containerColor = Color(240, 240, 240, 220)),
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
            .clip(RoundedCornerShape(10.dp))
    ) {

        if (citiesList.value.isNotEmpty()) {
            LazyColumn {
                items(citiesList.value) { city ->
                    Text(
                        text = "${city.name}${city.state?.let {",${city.state}"}?:""},${city.country}",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                Log.d("SearchBar", "SearchBar: $city")
                                setSelectedCity(city)
                                onToggleSearch(false)
                            }
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        } else {

            Text(
                text = "No Results",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
@Composable
fun CityName(selectedCity: State<CityData>, modifier: Modifier = Modifier)
{
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = "Location")
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = selectedCity.value.name,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}
@Composable
fun WeatherDetails(weatherData: State<WeatherData>, modifier: Modifier = Modifier)
{
    Column(
        modifier = modifier.padding(horizontal = 12.dp)
    ) {
        Row (
            modifier= Modifier
                .weight(0.5F)
                .padding(top = 8.dp)
        ){
            MainWeatherDetails(weatherData)
        }

        Row(
            modifier= Modifier
                .weight(0.3F)
                .padding(vertical = 8.dp)
        ) {
            DayAndDate()
        }
        Row(
            modifier= Modifier
                .weight(1F)
                .padding(bottom = 10.dp)
        ) {
            OtherWeatherDetails()
        }
    }
}
@Composable
fun MainWeatherDetails(weatherData: State<WeatherData>)
{
    Row() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.8F)
        ){
            //Render Lottie Animations for weather
            if (weatherData.value.weather.isNotEmpty()){
            WeatherConditionAnimation(weatherData.value.weather[0].main)
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1F)
        ){
            //Weather temperature details
            WeatherTemperatureDetails(
                weatherData.value.main.temp,
                weatherData.value.main.temp_max,
                weatherData.value.main.temp_min,
            )
        }
    }
}
@Composable
fun WeatherConditionAnimation(condition: String)
{
    val weather_anim = when(condition){
        "Thunderstorm"-> R.raw.thunderstrom
        "Drizzle"-> R.raw.rainy
        "Rain"-> R.raw.rainy
        "Snow"-> R.raw.snow
        "Atmosphere"-> R.raw.mist
        "Clear"-> R.raw.clear_sun
        "Clouds"-> R.raw.splash_animation
        else->R.raw.clear_sun
    }
    val preloaderWeatherAnimComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            weather_anim
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderWeatherAnimComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )
    LottieAnimation(
        composition = preloaderWeatherAnimComposition,
        progress = preloaderProgress,
        modifier = Modifier.size(150.dp)
    )
    Text(
        text = condition,
        fontSize = 24.sp,
        fontWeight = FontWeight.ExtraBold
    )
}
@Composable
fun WeatherTemperatureDetails(temp: Double, tempMax: Double, tempMin: Double)
{
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .weight(1F)
        ) {
            //Main Temperature Data
            Text(
                text = "${round(temp).toInt()}",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.End,
//                modifier = Modifier.weight(1F)
            )
            Text(
                text = "°C",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
//                modifier = Modifier.weight(0.6F)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .weight(0.6F)
        )  {
            // Min Max Temperature Data
            Column {
                Text(
                    text = "Min: "+"${round(tempMin).toInt()}"+"°C",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Max: "+"${round(tempMax).toInt()}"+"°C",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
    }
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OtherWeatherDetails()
{
    Card(
        shape = CardDefaults.outlinedShape,
        border = BorderStroke(1.dp, Color.White),
        colors = CardDefaults.outlinedCardColors(containerColor = Color(160,160,160,50)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.SpaceAround,
            maxItemsInEachRow = 3,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp)
        ) {
            OtherDetailsCard(
                name = "Humidity",
                icon = R.drawable.humidity,
                value = "70",
            )
            OtherDetailsCard(
                name = "Wind",
                icon = R.drawable.wind,
                value = "70",
            )
            OtherDetailsCard(
                name = "Condition",
                icon = R.drawable.conditions,
                value = "70",
            )
            OtherDetailsCard(
                name = "Sun Rise",
                icon = R.drawable.sunrise,
                value = "70",
            )
            OtherDetailsCard(
                name = "Sun Set",
                icon = R.drawable.sunset,
                value = "70",
            )
            OtherDetailsCard(
                name = "Sea Level",
                icon = R.drawable.sea,
                value = "70",
            )
        }
    }
}
@Composable
fun OtherDetailsCard(name: String, icon: Int, value: String)
{
    Box(
        modifier = Modifier.padding(vertical = 10.dp)
    ){
        Card(
            shape = CardDefaults.outlinedShape,
            border = BorderStroke(1.dp, Color.White),
            colors = CardDefaults.outlinedCardColors(containerColor = Color(160, 160, 160, 50)),
            modifier = Modifier
                .size(90.dp)
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp)
            ){
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = name,
                    modifier = Modifier.weight(1F)
                )
                Spacer(modifier = Modifier
                    .height(4.dp)
                    .weight(0.1F))
                Text(
                    text = value,
                    fontWeight = FontWeight.SemiBold,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    modifier = Modifier.weight(0.5F)
                )
                Text(
                    text = name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    ),
                    modifier = Modifier.weight(0.5F)
                )
            }
        }
    }
}
@Composable
fun DayAndDate()
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Tuesday",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "27 March 2024",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(showBackground = true, showSystemUi = true)
fun MainScreenPrev()
{
    val viewModel = DummyModel()
//    val viewModel:MainViewModel = hiltViewModel()
    MainScreenLayout(
        viewModel.searchText.collectAsState(),
        viewModel.isSearching.collectAsState(),
        mutableStateOf(listOf<CityData>()),
        viewModel::onSearchTextChange,
        viewModel::onToggleSearch,
        viewModel::setSelectedCity,
        mutableStateOf(CityData("DE",52.517,13.388,"Berlin",null)),
        mutableStateOf(WeatherData())
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true, wallpaper = Wallpapers.NONE)
fun CardPrev()
{
   OtherDetailsCard(
       name = "Humidity",
       icon = R.drawable.humidity,
       value = "70")
}