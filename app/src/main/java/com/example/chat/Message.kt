package com.example.chat

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Message {
    var textMessage: String? = null
    var autor: String? = null
    var timeMessage: String? = null

    constructor(textMessage: String?, autor: String?) {
        this.textMessage = textMessage
        this.autor = autor
        val timeMessag = Date().time
        val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss,dd.MM.yyyy", Locale.getDefault())
        timeMessage = timeFormat.format(timeMessag)
    }

    constructor() {}
}