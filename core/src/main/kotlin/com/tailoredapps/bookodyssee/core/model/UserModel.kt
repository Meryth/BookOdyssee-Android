package com.tailoredapps.bookodyssee.core.model

import androidx.room.Entity
import androidx.room.PrimaryKey


//TODO: add list of saved books after the api has been integrated

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    val password: String,
)