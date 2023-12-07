package co.mandroid.havadurumu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.mandroid.havadurumu.ui.theme.HavaDurumuTheme
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val temperatureState: MutableState<Int?> = mutableStateOf(null)
val weatherIconState: MutableIntState = mutableIntStateOf(R.drawable.sun)
val weatherState: MutableIntState = mutableIntStateOf(R.string.sunny)

class MainActivity : ComponentActivity() {

    private val retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.open-meteo.com/v1/")
            .build()

    private fun changeWeatherIcon(weatherCode: Int) {
        when (weatherCode) {
            1, 2, 3 -> {
                weatherIconState.intValue = R.drawable.bi_cloud_sun
                weatherState.intValue = R.string.partial_cloud
            }

            61, 63, 65, 66, 67, 80, 81, 82 -> {
                weatherIconState.intValue = R.drawable.bi_cloud_rain
                weatherState.intValue = R.string.rainy
            }

            else -> {
                weatherIconState.intValue = R.drawable.sun
                weatherState.intValue = R.string.sunny
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val weatherService = retrofit.create(WeatherService::class.java)
        weatherService.getWeather().enqueue(object : Callback<Weather> {
            override fun onResponse(
                call: retrofit2.Call<Weather>, response: retrofit2.Response<Weather>
            ) {
                val weather = response.body()
                if (weather != null) {
                    temperatureState.value = weather.current.temperature2m.toInt()
                    changeWeatherIcon(weather.current.weatherCode)
                }
            }

            override fun onFailure(call: retrofit2.Call<Weather>, t: Throwable) {
                println("Error: ${t.message}")
            }
        })

        setContent {
            HavaDurumuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
        // Add a circular progress indicator to the center of the screen if the temperature is null
        if (temperatureState.value == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        } else {
            WeatherContent()
        }
        // add padding to the column

    }
}

@Composable
fun WeatherContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 48.dp)
    ) {
        Text(
            text = "Tuesday",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "20 Jun 2022",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )
        Row(verticalAlignment = Alignment.Bottom) {
            // Location icon
            Image(
                painter = painterResource(id = R.drawable.location),
                contentDescription = "location",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(24.dp)
            )
            Text(
                text = "Biarritz, FR",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
        }
        Box(modifier = Modifier.weight(1f))
        Image(
            painter = painterResource(id = weatherIconState.intValue),
            contentDescription = stringResource(id = weatherState.intValue),
            modifier = Modifier.size(72.dp)
        )
        Text(
        text = stringResource(id = R.string.degree, temperatureState.value!!),
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 72.sp
        )
        Text(
            text = stringResource(id = weatherState.intValue),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HavaDurumuTheme {
        MainScreen()
    }
}