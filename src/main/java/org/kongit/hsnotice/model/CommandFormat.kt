package org.kongit.hsnotice.model

interface CommandFormat {

    fun isCommand() : Boolean?
    fun runCommand()
    fun tabComplete() : MutableList<String>

}