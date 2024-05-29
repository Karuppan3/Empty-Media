package io.bash_psk.downloader.youtubedl

class YoutubeDLRequest {

    private val urls: List<String>
    private val options = YoutubeDLOptions()
    private val customCommandList: MutableList<String> = ArrayList()

    constructor(url: String) {

        urls = listOf(url)
    }

    constructor(urls: List<String>) {

        this.urls = urls
    }

    fun addOption(
        option: String,
        argument: String
    ) : YoutubeDLRequest {

        options.addOption(
            option = option,
            argument = argument
        )

        return this
    }

    fun addOption(
        option: String,
        argument: Number
    ) : YoutubeDLRequest {

        options.addOption(
            option = option,
            argument = argument
        )

        return this
    }

    fun addOption(
        option: String
    ) : YoutubeDLRequest {

        options.addOption(option = option)

        return this
    }

    fun addCommands(
        commands: List<String>
    ) : YoutubeDLRequest {

        customCommandList.addAll(elements = commands)

        return this
    }

    fun getOption(
        option: String
    ) : String? {

        return options.getArgument(option = option)
    }

    fun getArguments(
        option: String
    ) : List<String?>? {

        return options.getArguments(option = option)
    }

    fun hasOption(
        option: String
    ) : Boolean {

        return options.hasOption(option = option)
    }

    fun buildCommand(): List<String> {

        val commandList: MutableList<String> = ArrayList()

        commandList.addAll(elements = options.buildOptions())
        commandList.addAll(elements = customCommandList)
        commandList.addAll(elements = urls)

        return commandList
    }
}