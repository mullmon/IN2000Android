package com.example.team11.database.entity

import androidx.room.*
import com.example.team11.util.DbConstants


@Entity(tableName = "weather_forecast",
    foreignKeys = [ForeignKey(entity = Place::class, parentColumns = arrayOf("id"), childColumns = arrayOf("place_id"), onDelete = ForeignKey.CASCADE)],
    primaryKeys = ["place_id","time"])
data class WeatherForecastDb(
    @ColumnInfo(name = "place_id")
    val placeId: Int,
    var time: String,
    var symbol: String,
    @ColumnInfo(name = "temp_air")
    var tempAir: Float,
    var precipitation: Float,
    var uv: Float
)