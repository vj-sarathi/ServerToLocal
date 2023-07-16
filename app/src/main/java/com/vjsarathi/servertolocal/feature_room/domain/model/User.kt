package com.vjsarathi.servertolocal.feature_room.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val email: String,
    val mobile: String,
    val gender: String
)