# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
   static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
   static **$* *;
}
-keepclassmembers class <2>$<3> {
   kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
   public static ** INSTANCE;
}
-keepclassmembers class <1> {
   public static <1> INSTANCE;
   kotlinx.serialization.KSerializer serializer(...);
}

# ============ Hilt & Dagger Rules ============
-keep class dagger.hilt.** { *; }
-keep class * extends dagger.hilt.android.lifecycle.HiltViewModel { *; }
-keepclassmembers class * {
    @dagger.hilt.android.lifecycle.HiltViewModel *;
}

# ============ Room Database Rules ============
-keep class * extends androidx.room.RoomDatabase { *; }
-keepclassmembers class * extends androidx.room.RoomDatabase {
    public <init>(...);
}

# ============ Apollo GraphQL Rules ============
-keep class com.apollographql.apollo3.** { *; }
-keepclassmembers class * {
    @com.apollographql.apollo3.api.* *;
}

# ============ Jetpack Compose Rules ============
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** {
    public <init>(...);
}

# ============ ViewModel Rules ============
-keep class androidx.lifecycle.ViewModel { *; }
-keep class androidx.lifecycle.AndroidViewModel { *; }
-keepclassmembers class androidx.lifecycle.** {
    public <init>(...);
}

# ============ DataStore Rules ============
-keep class androidx.datastore.** { *; }
-keepclassmembers class androidx.datastore.** {
    public <init>(...);
}

# ============ Retrofit & OkHttp Rules ============
-keep class com.squareup.okhttp3.** { *; }
-keep class retrofit2.** { *; }
-keepclassmembers class com.squareup.okhttp3.** {
    public <init>(...);
}
-keepclassmembers class retrofit2.** {
    public <init>(...);
}

# ============ Coil Image Loading Rules ============
-keep class io.coil.** { *; }
-keepclassmembers class io.coil.** {
    public <init>(...);
}

# ============ Navigation Rules ============
-keep class androidx.navigation.** { *; }
-keepclassmembers class androidx.navigation.** {
    public <init>(...);
}

# ============ Kotlin Coroutines Rules ============
-keep class kotlinx.coroutines.** { *; }
-keepclassmembers class kotlinx.coroutines.** {
    public <init>(...);
}

# ============ Don't warn about missing classes ============
-dontwarn android.content.pm.ApplicationInfo
-dontwarn android.content.Context
-dontwarn com.google.protobuf.**
-dontwarn okhttp3.**
-dontwarn okio.**
