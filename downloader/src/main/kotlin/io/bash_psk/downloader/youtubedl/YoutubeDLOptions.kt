package io.bash_psk.downloader.youtubedl

import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.set

class YoutubeDLOptions {

    private val options: MutableMap<String, MutableList<String>> = LinkedHashMap()

    fun addOption(
        option: String,
        argument: String
    ) : YoutubeDLOptions {

        when (!options.containsKey(key = option)) {

            true -> {

                val arguments: MutableList<String> = ArrayList()

                arguments.add(element = argument)
                options[option] = arguments
            }

            false -> {

                options[option]?.add(element = argument)
            }
        }

        return this
    }

    fun addOption(
        option: String,
        argument: Number
    ) : YoutubeDLOptions {

        when (!options.containsKey(key = option)) {

            true -> {

                val arguments: MutableList<String> = ArrayList()

                arguments.add(element = argument.toString())
                options[option] = arguments
            }

            false -> {

                options[option]?.add(element = argument.toString())
            }
        }

        return this
    }

    fun addOption(
        option: String
    ) : YoutubeDLOptions {

        when (!options.containsKey(key = option)) {

            true -> {

                val arguments: MutableList<String> = ArrayList()

                arguments.add(element = "")
                options[option] = arguments
            }

            false -> {

                options[option]?.add(element = "")
            }
        }

        return this
    }

    fun getArgument(
        option: String
    ) : String? {

        when (options.containsKey(key = option).not()) {

            true -> {

                return null
            }

            false -> {

                val argument = options[option]?.get(index = 0)

                return argument?.ifEmpty {

                    null
                }
            }
        }
    }

    fun getArguments(
        option: String
    ) : List<String>? {

        return when (!options.containsKey(key = option)) {

            true -> {

                null
            }

            false -> {

                options[option]
            }
        }
    }

    fun hasOption(
        option: String
    ) : Boolean {

        return options.containsKey(key = option)
    }

    fun buildOptions() : List<String> {

        val commandList: MutableList<String> = mutableListOf()

        options.forEach { (option: String, value: MutableList<String>) ->

            value.forEach { argument: String ->

                commandList.add(element = option)

                when {

                    argument.isNotEmpty() -> {

                        commandList.add(element = argument)
                    }
                }
            }
        }

        return commandList
    }
}