package com.dyippay.data.converters

import androidx.room.TypeConverter
import com.dyippay.data.types.ListItemType
import com.dyippay.data.types.toTrackType

class ItemConverter {

    @TypeConverter
    fun convertToTrackType(value: String): ListItemType? {
        return toTrackType(value)
    }

    @TypeConverter
    fun convertFromTrackType(value: ListItemType): String {
        return value.type
    }
}
