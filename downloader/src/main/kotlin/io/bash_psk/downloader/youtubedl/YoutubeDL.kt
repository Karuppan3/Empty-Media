package io.bash_psk.downloader.youtubedl

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import io.bash_psk.commons.SharedPrefsHelper
import io.bash_psk.commons.utils.ZipUtils.unzip
import io.bash_psk.downloader.R
import io.bash_psk.downloader.mapper.VideoInfo
import io.bash_psk.downloader.utils.SetLog
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.util.Collections
import kotlin.collections.set

object YoutubeDL {

    @JvmStatic
    fun getInstance() = this

    const val BASE_NAME = "empty media"
    private const val PACKAGES_ROOT = "packages"
    private const val PYTHON_BIN_NAME = "libpython.so"
    private const val PYTHON_LIB_NAME = "libpython.zip.so"
    private const val PYTHON_DIRECTORY_NAME = "python"
    private const val FFMPEG_DIRECTORY_NAME = "ffmpeg"
    private const val FFMPEG_BIN_NAME = "libffmpeg.so"
    private const val ARIA2C_DIRECTORY_NAME = "aria2c"
    const val YTDLP_DIRECTORY_NAME = "yt-dlp"
    const val YTDLP_BIN = "yt-dlp"
    private const val PYTHON_LIB_VERSION = "pythonLibVersion"

    private var isInitialized = false
    private var pythonPath: File? = null
    private var ffmpegPath: File? = null
    private var ytdlpPath: File? = null
    private var binDirectory: File? = null
    private var ENV_LD_LIBRARY_PATH: String? = null
    private var ENV_SSL_CERT_FILE: String? = null
    private var ENV_PYTHON_HOME: String? = null
    private val idProcessMap = Collections.synchronizedMap(HashMap<String, Process>())

    @Synchronized
    @Throws(YoutubeDLException::class)
    fun init(
        appContext: Context
    ) {

        when (isInitialized) {

            true -> {

                return
            }

            false -> {

                val baseDir = File(appContext.noBackupFilesDir, BASE_NAME)

                when {

                    !baseDir.exists() -> {

                        baseDir.mkdir()
                    }
                }

                val packagesDir = File(baseDir, PACKAGES_ROOT)

                binDirectory = File(appContext.applicationInfo.nativeLibraryDir)

                pythonPath = File(binDirectory, PYTHON_BIN_NAME)

                ffmpegPath = File(binDirectory, FFMPEG_BIN_NAME)

                val pythonDir = File(packagesDir, PYTHON_DIRECTORY_NAME)

                val ffmpegDir = File(packagesDir, FFMPEG_DIRECTORY_NAME)

                val aria2cDir = File(packagesDir, ARIA2C_DIRECTORY_NAME)

                val ytdlpDir = File(baseDir, YTDLP_DIRECTORY_NAME)

                ytdlpPath = File(ytdlpDir, YTDLP_BIN)

                ENV_LD_LIBRARY_PATH = "${
                    pythonDir.absolutePath
                }/usr/lib:${
                    ffmpegDir.absolutePath
                }/usr/lib:${
                    aria2cDir.absolutePath
                }/usr/lib"

                ENV_SSL_CERT_FILE = "${pythonDir.absolutePath}/usr/etc/tls/cert.pem"
                ENV_PYTHON_HOME = "${pythonDir.absolutePath}/usr"

                initPython(
                    appContext = appContext,
                    pythonDir = pythonDir
                )

                initYtdlp(
                    appContext = appContext,
                    ytdlpDir = ytdlpDir
                )

                isInitialized = true
            }
        }
    }

    @Throws(YoutubeDLException::class)
    fun initYtdlp(
        appContext: Context,
        ytdlpDir: File
    ) {

        when {

            ytdlpDir.exists().not() -> {

                ytdlpDir.mkdirs()
            }
        }

        val ytdlpBinary = File(ytdlpDir, YTDLP_BIN)

        when {

            ytdlpBinary.exists().not() -> {

                try {

                    val inputStream = appContext.resources.openRawResource(R.raw.kdlp)

                    FileUtils.copyInputStreamToFile(inputStream, ytdlpBinary)
                } catch (exception: Exception) {

                    FileUtils.deleteQuietly(ytdlpDir)

                    throw YoutubeDLException(
                        message = "Failed To Initialize",
                        e = exception
                    )
                }
            }
        }
    }

    @Throws(YoutubeDLException::class)
    fun initPython(
        appContext: Context,
        pythonDir: File
    ) {

        val pythonLib = File(binDirectory, PYTHON_LIB_NAME)

        val pythonSize = pythonLib.length().toString()

        when {

            !pythonDir.exists() || shouldUpdatePython(
                appContext = appContext,
                version = pythonSize
            ) -> {

                FileUtils.deleteQuietly(pythonDir)
                pythonDir.mkdirs()

                try {

                    unzip(
                        sourceFile = pythonLib,
                        targetDirectory = pythonDir
                    )
                } catch (exception: Exception) {

                    FileUtils.deleteQuietly(pythonDir)

                    throw YoutubeDLException(
                        message = "Failed To Initialize",
                        e = exception
                    )
                }

                updatePython(
                    appContext = appContext,
                    version = pythonSize
                )
            }
        }
    }

    private fun shouldUpdatePython(
        appContext: Context,
        version: String
    ) : Boolean {

        return version != SharedPrefsHelper.get(
            appContext = appContext,
            key = PYTHON_LIB_VERSION
        )
    }

    private fun updatePython(
        appContext: Context,
        version: String
    ) {

        SharedPrefsHelper.update(
            appContext = appContext,
            key = PYTHON_LIB_VERSION,
            value = version
        )
    }

    private fun assertInit() {

        check(value = isInitialized) {

            "Instance Not Initialized"
        }
    }

    @Throws(
        YoutubeDLException::class,
        InterruptedException::class,
        CanceledException::class
    )
    fun getInfo(
        url: String
    ) : VideoInfo {

        val request = YoutubeDLRequest(url = url)

        return getInfo(request = request)
    }

    @Throws(
        YoutubeDLException::class,
        InterruptedException::class,
        CanceledException::class
    )
    fun getPlaylistInfo(
        url: String
    ) : List<VideoInfo> {

        val request = YoutubeDLRequest(url = url)

        return getPlaylistInfo(request = request)
    }

    @Throws(
        YoutubeDLException::class,
        InterruptedException::class,
        CanceledException::class
    )
    fun getInfo(
        request: YoutubeDLRequest
    ) : VideoInfo {

        request.addOption(option = "--dump-json")

        val response = execute(
            request = request,
            processId = null,
            callback = { progress: Float, eta: Long, line: String ->

            }
        )

        val videoInfo: VideoInfo = try {

            ObjectMapper().readValue(response.out, VideoInfo::class.java)
        } catch (ioException: IOException) {

            throw YoutubeDLException(
                message = "Unable To Parse Video Information",
                e = ioException
            )
        } ?: throw YoutubeDLException(
            message = "Failed To Fetch Video Information"
        )

        return videoInfo
    }

    @Throws(
        YoutubeDLException::class,
        InterruptedException::class,
        CanceledException::class
    )
    fun getPlaylistInfo(
        request: YoutubeDLRequest
    ) : List<VideoInfo> {

        request.addOption(option = "--flat-playlist")
        request.addOption(option = "-j")

        val response = execute(
            request = request,
            processId = null,
            callback = { progress: Float, eta: Long, line: String ->

            }
        )

        SetLog.setInfo(message = "RES - ${response.out}")

        val videoInfoList: List<VideoInfo> = try {

            val newVideoInfoList = response.out.reader().readLines().map { jsonLine: String ->

                ObjectMapper().readValue(jsonLine, VideoInfo::class.java)
            }

            SetLog.setInfo(message = "VID_INFO_LIST - $newVideoInfoList")

            newVideoInfoList
        } catch (ioException: IOException) {

            throw YoutubeDLException(
                message = "Unable To Parse Playlist Information",
                e = ioException
            )
        }

        return videoInfoList
    }

    private fun ignoreErrors(
        request: YoutubeDLRequest,
        out: String
    ) : Boolean {

        return request.hasOption(
            option = "--dump-json"
        ) && out.isNotEmpty() && request.hasOption(
            option = "--ignore-errors"
        )
    }

    fun destroyProcessById(
        id: String
    ) : Boolean {

        return when (idProcessMap.containsKey(key = id)) {

            true -> {

                val process = idProcessMap[id]

                when (process != null && process.isAlive) {

                    true -> {

                        process.destroy()
                        idProcessMap.remove(id)

                        return true
                    }

                    false -> {

                        false
                    }
                }
            }

            false -> {

                false
            }
        }
    }

    class CanceledException : Exception()

    @JvmOverloads
    @Throws(
        YoutubeDLException::class,
        InterruptedException::class,
        CanceledException::class
    )
    fun execute(
        request: YoutubeDLRequest,
        processId: String? = null,
        callback: (
            progress: Float,
            eta: Long,
            line: String
        ) -> Unit
    ) : YoutubeDLResponse {

        assertInit()

        when {

            processId != null && idProcessMap.containsKey(
                key = processId
            ) -> {

                throw YoutubeDLException(message = "Process ID Already Exists")
            }

            request.hasOption(
                option = "--cache-dir"
            ).not() || request.getOption(
                option = "--cache-dir"
            ) == null -> {

                request.addOption(option = "--no-cache-dir")
            }
        }

        ffmpegPath?.let { path: File ->

            request.addOption(
                option = "--ffmpeg-location",
                path.absolutePath
            )
        }

        val youtubeDLResponse: YoutubeDLResponse
        val process: Process
        val exitCode: Int
        val outBuffer = StringBuffer()
        val errorBuffer = StringBuffer()
        val startTime = System.currentTimeMillis()
        val args = request.buildCommand()
        val command: MutableList<String?> = mutableListOf()

        command.addAll(
            elements = listOf(
                pythonPath!!.absolutePath,
                ytdlpPath!!.absolutePath
            )
        )

        command.addAll(elements = args)

        val processBuilder = ProcessBuilder(command)

        processBuilder.environment().apply {

            this["LD_LIBRARY_PATH"] = ENV_LD_LIBRARY_PATH
            this["SSL_CERT_FILE"] = ENV_SSL_CERT_FILE
            this["PATH"] = "${System.getenv("PATH")}:${binDirectory!!.absolutePath}"
            this["PYTHONHOME"] = ENV_PYTHON_HOME
            this["HOME"] = ENV_PYTHON_HOME
        }

        process = try {

            processBuilder.start()
        } catch (ioException: IOException) {

            throw YoutubeDLException(e = ioException)
        }

        when {

            processId != null -> {

                idProcessMap[processId] = process
            }
        }

        val outStream = process.inputStream
        val errStream = process.errorStream

        val stdOutProcessor = StreamProcessExtractor(
            buffer = outBuffer,
            stream = outStream,
            callback = callback
        )

        val stdErrProcessor = StreamGobbler(
            buffer = errorBuffer,
            stream = errStream
        )

        exitCode = try {

            stdOutProcessor.join()
            stdErrProcessor.join()
            process.waitFor()
        } catch (interruptedException: InterruptedException) {

            process.destroy()

            when {

                processId != null -> {

                    idProcessMap.remove(key = processId)
                }
            }

            throw interruptedException
        }

        val out = outBuffer.toString()
        val error = errorBuffer.toString()

        when {

            exitCode > 0 -> {

                when {

                    processId != null && !idProcessMap.containsKey(
                        key = processId
                    ) -> {

                        throw CanceledException()
                    }

                    !ignoreErrors(
                        request = request,
                        out = out
                    ) -> {

                        idProcessMap.remove(key = processId)

                        throw YoutubeDLException(message = error)
                    }
                }
            }
        }

        idProcessMap.remove(key = processId)

        val elapsedTime = System.currentTimeMillis() - startTime

        youtubeDLResponse = YoutubeDLResponse(
            command = command,
            exitCode = exitCode,
            elapsedTime = elapsedTime,
            out = out,
            error = error
        )

        return youtubeDLResponse
    }

    @Synchronized
    @Throws(YoutubeDLException::class)
    fun updateYoutubeDL(
        appContext: Context,
        updateChannel: UpdateChannel = UpdateChannel.STABLE
    ) : UpdateStatus? {

        assertInit()

        return try {

            YoutubeDLUpdater.update(
                appContext = appContext,
                youtubeDLChannel = updateChannel
            )
        } catch (ioException: IOException) {

            throw YoutubeDLException(
                message = "Failed To Update Youtube-DL",
                e = ioException
            )
        }
    }

    fun version(
        appContext: Context?
    ) : String? {

        return YoutubeDLUpdater.version(appContext = appContext)
    }

    fun versionName(
        appContext: Context?
    ) : String? {

        return YoutubeDLUpdater.versionName(appContext = appContext)
    }

    enum class UpdateStatus {

        DONE,
        ALREADY_UP_TO_DATE
    }

    sealed class UpdateChannel(
        val apiUrl: String
    ) {

        data object STABLE : UpdateChannel(
            apiUrl = "https://api.github.com/repos/yt-dlp/yt-dlp/releases/latest"
        )

        data object NIGHTLY : UpdateChannel(
            apiUrl = "https://api.github.com/repos/yt-dlp/yt-dlp-nightly-builds/releases/latest"
        )
    }
}