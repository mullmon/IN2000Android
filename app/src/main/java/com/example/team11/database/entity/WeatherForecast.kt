package com.example.team11.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.team11.Place
import com.example.team11.util.DbConstants
import com.google.gson.annotations.SerializedName


// For versjon 2.0 av apiet

/**
 * Deklarerer at primary key er en foreign key fra place databasen
 */
@Entity(tableName = DbConstants.FORECAST_TABLE_NAME,
    foreignKeys = [ForeignKey(entity = Place::class, parentColumns = arrayOf("id"), childColumns = arrayOf("placeId"), onDelete = ForeignKey.CASCADE)])
data class WeatherForecast(
    @PrimaryKey val placeId: Int,
    @SerializedName("properties")
    val weatherForecastTimeSlotList: WeatherForecastTimeSlotList

)

data class WeatherForecastTimeSlotList(
    @SerializedName("timeseries")
    val list: List<WeatherForecastTimeSlot>
)

data class WeatherForecastTimeSlot(
    @SerializedName("time")
    val time: String,

    @SerializedName("data")
    val types: WeatherForecastTypes

) : Comparable<WeatherForecastTimeSlot> {
    override operator fun compareTo(other: WeatherForecastTimeSlot): Int {
        if (this.time > other.time) return 1
        if (this.time < other.time) return -1
        return 0
    }
}

data class WeatherForecastTypes(
    @SerializedName("instant")
    val instantWeatherForecast: InstantWeatherForecast,

    @SerializedName("next_1_hour")
    val nextOneHourForecast: NextOneHourForecast
)

data class InstantWeatherForecast(
    @SerializedName("details")
    val details: WeatherForecastDetails
)
data class WeatherForecastDetails(
    @SerializedName("air_temperature")
    val temp: Float,

    @SerializedName("ultraviolet_index_clear_sky")
    val uv: Float,

    @SerializedName("precipitation_amount")
    val rainAmount: Float,

    @SerializedName("probability_of_thunder")
    val thunderProbability: Float
)

data class NextOneHourForecast(
    @SerializedName("summary")
    val summary: WeatherForecastSymbol,

    @SerializedName("details")
    val details: WeatherForecastDetails
)

data class WeatherForecastSymbol(val symbol: String)

