# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# For using GSON @Expose annotation
-keepattributes *Annotation*

# Preserve Gson related classes and fields
-keep class com.google.gson.** { *; }

# Preserve Gson TypeToken usage
-keepclassmembers class com.google.gson.reflect.TypeToken {
    <fields>;
    <methods>;
}

# Preserve Gson annotations
-keepattributes *Annotation*

# Preserve model classes for Gson
-keep class com.example.yourpackage.** { *; }
-keep class com.google.gson.** { *; }
-keepclassmembers class com.google.gson.reflect.TypeToken {
    <fields>;
    <methods>;
}
-keep class ir.hoseinahmadi.myapplication.data.model.** { *; }

