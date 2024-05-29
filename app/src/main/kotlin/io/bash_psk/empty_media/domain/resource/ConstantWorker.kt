package io.bash_psk.empty_media.domain.resource

object ConstantWorker {

    object ID {

        const val COMMAND_WORKER = "COMMAND WORKER"
        const val UPDATE_WORKER = "UPDATE WORKER"
    }

    object Key {

        const val COMMAND = "COMMAND"
        const val TITLE = "TITLE"

        const val COMMAND_PROGRESS = "COMMAND PROGRESS"
        const val COMMAND_ELAPSED_TIME = "COMMAND ELAPSED TIME"
        const val COMMAND_LINE = "COMMAND LINE"
        const val COMMAND_OUTPUT_FILE = "COMMAND OUTPUT FILE"
        const val UPDATE_OUTPUT_FILE = "UPDATE OUTPUT FILE"
    }

    object Message {

        const val PROCESS_STARTING = "Process Starting..."
        const val LIBRARY_UPDATING = "Library Updating..."
        const val SCAN_MEDIA_FILE = "Scan Media File..."

        const val ENQUEUED = "Enqueued"
        const val RUNNING = "Running"
        const val COMPLETED = "Completed"
        const val FAILED = "Failed"
        const val CANCELLED = "Cancelled"
    }

    object Data {

        val BUFFER_SIZE_LOW = ByteArray(size = 64 * 1024)
        val BUFFER_SIZE_MEDIUM = ByteArray(size = 256 * 1024)
        val BUFFER_SIZE_HIGH = ByteArray(size = 512 * 1024)
    }

    object Intent {

        const val CANCEL = "Cancel"
    }

    object MediaKey {

        const val ID = "ID"
        const val MEDIA_ID = "MEDIA ID"
        const val TITLE = "TITLE"
        const val COMMAND = "COMMAND"
        const val PATH = "PATH"
        const val PLATFORM = "PLATFORM"
        const val STATUS = "STATUS"
        const val SIZE = "SIZE"
        const val PROGRESS = "PROGRESS"
        const val TIME_STAMP = "TIME STAMP"
    }
}