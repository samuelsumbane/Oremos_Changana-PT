package com.samuelsumbane.oremoscatolico

import java.util.*

//object Lang {
////    private var locale: Locale = Locale("pt")
////
////    fun setLocale(language: String, country: String = "") {
////        locale = if (country.isEmpty()) Locale(language) else Locale(language, country)
////    }
////
////    fun getString(key: String): String {
////        val bundle = ResourceBundle.getBundle("strings", locale)
////        return bundle.getString(key)
////    }
//
//    private var locale: Locale = Locale("en")
//
//    fun setLocale(language: String, country: String = "") {
//        locale = if (country.isEmpty()) Locale(language) else Locale(language, country)
//    }
//
//    fun getString(key: String): String {
//        val bundle = ResourceBundle.getBundle("strings", locale)
//        return bundle.getString(key)
//    }
//
//}


//expect class StringResources {
//    fun getString(key: String, vararg args: Any): String
//}
//
//// commonMain/kotlin/utils/StringResources.kt (continuação)
//actual class StringResources {
//    private val bundle: ResourceBundle by lazy {
//        ResourceBundle.getBundle("messages", Locale.getDefault())
//    }
//
//    actual fun getString(key: String, vararg args: Any): String {
//        return try {
//            String.format(bundle.getString(key), *args)
//        } catch (e: Exception) {
//            "Missing: $key" // Fallback
//        }
//    }
//}