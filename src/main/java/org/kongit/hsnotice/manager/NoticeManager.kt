package org.kongit.hsnotice.manager

import org.bukkit.Bukkit
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.scheduler.BukkitTask
import org.kongit.hsnotice.HSNotice
import org.kongit.hsnotice.model.Notice
import org.kongit.hsnotice.model.Scheduler
import org.kongit.hsnotice.model.Worlds
import org.kongit.hsnotice.util.ConfigUtil
import org.kongit.hsnotice.util.MessageUtil.sendMessage
import java.io.File
import kotlin.random.Random

class NoticeManager {


    private val plugin = HSNotice.instance
    companion object{
        private var notice : MutableList<Notice> = mutableListOf()
        private var tasks: MutableList<BukkitTask> = mutableListOf()
    }

    init {
        load()
        run()
    }



    private fun load() {

        notice = mutableListOf()

        val folder = File(plugin.dataFolder,"Configs/Notice")

        if (folder.isDirectory) {
            folder.walk().forEach {
                if (it.isFile) {

                    val config = YamlConfiguration.loadConfiguration(it)

                    notice.add(
                        Notice(
                            worlds = Worlds(

                                mode = config.getString("worlds.mode","whitelist") == "whitelist",
                                list = config.getStringList("worlds.list")

                            ),
                            scheduler = Scheduler(

                                timer = config.getString("scheduler.timer","0h 0m 30s")!!,
                                random = config.getBoolean("scheduler.random"),
                                loop = config.getBoolean("scheduler.loop")

                            ),
                            messages = config.getStringList("messages")
                        )
                    )



                }
            }
        }

    }

    private fun run() {

        tasks.forEach { it.cancel() }

        fun task(index : Notice) {
            for (world in ConfigUtil.getWorlds(index.worlds)) {
                world.players.forEach {
                    for (message in index.messages) {
                        it.sendMessage(message,0)
                    }
                }
            }
        }
        println(notice)
        for (index in notice){
            val scheduler = ConfigUtil.getScheduler(index.scheduler)
            val timer = if (scheduler.random) { Random.nextInt(1440) } else { scheduler.timer }
            if (scheduler.loop) {
                tasks.add(
                    Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
                        task(index)
                    },timer * 20L,timer * 20L)
                )
            } else {
                tasks.add(
                    Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                        task(index)
                    },timer * 20L)
                )
            }

        }



    }


}