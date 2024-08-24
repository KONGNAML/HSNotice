package org.kongit.hsnotice.commands

import com.github.horangshop.lib.plugin.command.CommandData
import com.github.horangshop.lib.plugin.command.HSCommand
import org.kongit.hsnotice.commands.handler.ReloadPlugin
import org.kongit.hsnotice.HSNotice
import org.kongit.hsnotice.model.CommandFormat

class Commands : HSCommand(HSNotice.instance, listOf("HSNotice","자동공지","Notice")) {

    private lateinit var commandFormat: CommandFormat

    /**
     * 커맨드 인터페이스 설정
     */

    private fun getCommandClasses(data: CommandData?): List<CommandFormat> {

        return listOf(
            ReloadPlugin(data,sender),
        )

    }


    /**
     * 커맨드 실행
     */

    override fun command(data: CommandData?): Boolean {

        val commandClasses = getCommandClasses(data)
        var isCommand : Boolean = true
        for (index in commandClasses) {
            commandFormat = index
            isCommand = commandFormat.isCommand() ?: continue
            commandFormat.runCommand()
            return isCommand
        }
        return true

    }

    /**
     * 커맨드 탭
     */

    override fun tabComplete(data: CommandData?): MutableList<String> {

        val commandClasses = getCommandClasses(data)
        val tabComplete = mutableListOf<String>()
        for (index in commandClasses) {
            commandFormat = index
            tabComplete.addAll(commandFormat.tabComplete())
        }

        return tabComplete

    }

}