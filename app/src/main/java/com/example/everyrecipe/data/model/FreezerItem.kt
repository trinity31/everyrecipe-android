package com.example.everyrecipe.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "freezerItems"
)
data class FreezerItem(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val categoryID: String,
    val name: String,
    val exist: Boolean = false
)
