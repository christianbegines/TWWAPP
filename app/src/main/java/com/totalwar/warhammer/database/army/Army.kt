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
    var id: Int,

    @ColumnInfo(name = "lordId")
    var lordId:String,

    @ColumnInfo(name = "faction")
    var faction:String,

    @ColumnInfo(name = "army")
    var army:List<String>,

    @ColumnInfo(name = "name")
    var name:String,
) : Parcelable
