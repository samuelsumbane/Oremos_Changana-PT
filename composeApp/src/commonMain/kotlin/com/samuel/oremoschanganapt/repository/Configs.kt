package com.samuel.oremoschanganapt.repository

enum class AppMode(val value: String) {
    LIGHT("Light"),
    DARK("Dark"),
    SYSTEM("System")
}


object Configs {
//    var fontSize: String = FontSize.BIG.string
//    var font: State<String>
    var thememode: String = ""
    var appLocale: String = "pt"
}


enum class FontSizeName(val string: String) {
    SMALL("Small"),
    NORMAL("Normal"),
    BIG("Big"),
    HUGE("Huge")
}
