package org.kongit.hsnotice.commands.handler

import com.github.horangshop.lib.plugin.command.CommandData
import com.github.horangshop.lib.plugin.config.Configurations
import org.bukkit.command.CommandSender
import org.kongit.hsnotice.HSNotice
import org.kongit.hsnotice.manager.NoticeManager
import org.kongit.hsnotice.manager.ResourcesManager
import org.kongit.hsnotice.model.CommandFormat
import org.kongit.hsnotice.util.MessageUtil

class ReloadPlugin constructor(private val command: CommandData?,private val sender: CommandSender) : CommandFormat {

    private val config: Configurations = HSNotice.instance.configurations
    private val plugin = HSNotice.instance

    override fun isCommand(): Boolean? {
        if (command == null) return null
        if (command.args(0) == config.command.get("reload")) { return true
        } else { return null }
    }

    override fun runCommand() {

        if (sender.isOp) {

            ResourcesManager()
            NoticeManager()

            plugin.configurations.reload()

            MessageUtil.sendAnnounce(sender,config.message.get("reload"))

            return
        }

        MessageUtil.sendAnnounce(sender,config.message.get("permission"))

    }

    override fun tabComplete(): MutableList<String> {
        return when (command?.length()) {
            1 -> {
                if (sender.isOp) { mutableListOf(config.command.get("reload")) }
                else { mutableListOf() }
            }
            else -> mutableListOf()
        }
    }

}