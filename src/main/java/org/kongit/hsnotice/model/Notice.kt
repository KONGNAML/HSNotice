package org.kongit.hsnotice.model

data class Notice(

    val worlds: Worlds,

    val scheduler: Scheduler,

    val messages: MutableList<String>


)
