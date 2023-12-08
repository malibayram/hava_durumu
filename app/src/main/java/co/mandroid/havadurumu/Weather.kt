package co.mandroid.havadurumu

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("current")
    val current: Current
)