package org.kongit.hsnotice

import com.github.horangshop.lib.plugin.HSPlugin
import org.bukkit.plugin.java.JavaPlugin
import org.kongit.hsnotice.commands.Commands
import org.kongit.hsnotice.manager.NoticeManager
import org.kongit.hsnotice.manager.ResourcesManager

class HSNotice : HSPlugin() {

    companion object{

        lateinit var instance : HSNotice
            private set

    }


    override fun enable() {
        instance = this
        ResourcesManager()
        NoticeManager()
        registerCommand(Commands())
    }

    override fun disable() {

    }
}
