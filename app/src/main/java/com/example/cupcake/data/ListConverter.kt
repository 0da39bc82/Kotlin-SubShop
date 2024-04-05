package com.example.cupcake.data

import androidx.room.TypeConverter


/**
 * * Type converter for converting lists of strings to and from strings.
 * Converts a list of strings to a single comma-separated string.
 * @param list The list of strings to be converted.
 * @return The comma-separated string representation of the list.
 */
class ListConverter {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(data: String): List<String> {
        return data.split(",").map { it.trim() }
    }
}