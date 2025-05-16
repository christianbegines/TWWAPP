package com.totalwar.warhammer.database

//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.TypeConverters
//import com.totalwar.warhammer.database.army.Army
//import com.totalwar.warhammer.database.army.ArmyDao

//@Database(entities = [(Army::class)], version = 1, exportSchema = false)
//@TypeConverters(Converters::class)
//abstract class WarHammerDataBase : RoomDatabase() {
//
//    abstract fun armyDao(): ArmyDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: WarHammerDataBase? = null
//
//        fun getInstance(context: Context): WarHammerDataBase {
//            // only one thread of execution at a time can enter this block of code
//            synchronized(this) {
//                var instance = INSTANCE
//
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        WarHammerDataBase::class.java,
//                        "war_hammer_database"
//                    ).fallbackToDestructiveMigration(true)
//                        .build()
//
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
//    }
//}