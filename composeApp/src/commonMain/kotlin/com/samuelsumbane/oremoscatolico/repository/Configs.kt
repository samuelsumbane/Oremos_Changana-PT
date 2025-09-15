package com.samuelsumbane.oremoscatolico.repository

enum class AppMode(val value: String) {
    LIGHT("Light"),
    DARK("Dark"),
    SYSTEM("System")
}


object Configs {
    var fontSize: String = "Large"
//    var font: State<String>
    var thememode: String = ""
    var appLocale: String = "pt"
}


enum class FontSize(val string: String) {
    SMALL("Small"),
    NORMAL("Normal"),
    BIG("Big"),
    HUGE("Huge")
}
