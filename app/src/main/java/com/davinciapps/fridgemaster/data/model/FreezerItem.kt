package com.davinciapps.fridgemaster.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "freezerItems"
)
data class FreezerItem(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val categoryID: String,
    val name: String,
    val exist: Boolean = false
): Serializable

data class FreezerItemList(
    val items: List<FreezerItem>
) : Serializable