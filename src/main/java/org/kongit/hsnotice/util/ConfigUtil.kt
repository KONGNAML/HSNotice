package org.kongit.hsnotice.util

import org.bukkit.Bukkit
import org.bukkit.World
import org.kongit.hsnotice.model.Scheduler
import org.kongit.hsnotice.model.Worlds

object ConfigUtil {

    fun getWorlds(worlds: Worlds) : List<World> {

        val list: MutableList<World> = mutableListOf()
        for (world in Bukkit.getWorlds()) {
            if (worlds.mode) { if (worlds.list.contains(world.name)) { list.add(world) }
            } else { if (!worlds.list.contains(world.name)) { list.add(world) } }
        }
        return list

    }
    data class IntScheduler(val timer: Int, val random:Boolean, val loop:Boolean)
    fun getScheduler(scheduler: Scheduler) : IntScheduler {
        return IntScheduler(

            timer = timeStringToSeconds(scheduler.timer),
            random = scheduler.random,
            loop = scheduler.loop

        )
    }

    private fun timeStringToSeconds(timeString: String): Int {
        val timePattern = Regex("""(?:(\d+)h)?\s*(?:(\d+)m)?\s*(?:(\d+)s)?""")
        val matchResult = timePattern.matchEntire(timeString.trim())

        return if (matchResult != null) {
            val (hours, minutes, seconds) = matchResult.destructured

            val totalSeconds = (hours.toIntOrNull() ?: 0) * 3600 +
                    (minutes.toIntOrNull() ?: 0) * 60 +
                    (seconds.toIntOrNull() ?: 0)

            totalSeconds
        } else {
            0
        }
    }


}