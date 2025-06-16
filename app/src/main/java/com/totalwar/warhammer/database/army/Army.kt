package com.totalwar.warhammer.database.army

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "armies")
data class Army(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = null,

    @ColumnInfo(name = "lordId")
    var lordId: String? = null,

    @ColumnInfo(name = "faction")
    var faction: String?,

    @ColumnInfo(name = "army")
    var army: List<String> = emptyList(),

    @ColumnInfo(name = "name")
    var name:String,
) : Parcelable
