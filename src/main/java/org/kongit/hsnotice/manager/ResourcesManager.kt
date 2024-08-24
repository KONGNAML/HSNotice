package org.kongit.hsnotice.manager

import org.kongit.hsnotice.HSNotice
import java.io.File

class ResourcesManager {

    private val plugin = HSNotice.instance

    init {
        setup()
    }

    private fun setup() {


        val folder = File(plugin.dataFolder,"Configs/Notice")

        if (!folder.exists()) {
            folder.mkdirs()
            plugin.saveResource("Configs/Notice/example.yml",false)
        }


    }


}