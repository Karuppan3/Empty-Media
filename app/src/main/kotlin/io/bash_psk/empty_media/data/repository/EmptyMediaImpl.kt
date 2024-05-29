package io.bash_psk.empty_media.data.repository

import android.app.Application
import io.bash_psk.downloader.mapper.VideoFormat
import io.bash_psk.downloader.mapper.VideoInfo
import io.bash_psk.downloader.youtubedl.YoutubeDL
import io.bash_psk.downloader.youtubedl.YoutubeDLRequest
import io.bash_psk.empty_media.domain.media.MediaData
import io.bash_psk.empty_media.domain.media.MediaFormatData
import io.bash_psk.empty_media.domain.media.PlaylistMediaData
import io.bash_psk.empty_media.domain.repository.EmptyMedia
import io.bash_psk.empty_media.domain.resource.ConstantString
import io.bash_psk.utils.log.SetLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Collections
import java.util.regex.Pattern

class EmptyMediaImpl(
    private val application: Application
) : EmptyMedia {

    override suspend fun getMediaData(
        searchUrl: String
    ) : Flow<MediaData> {

        return flow {

            val mediaData = MediaData()

            val searchStartedMediaData = mediaData.copy(isSearch = true)

            emit(value = searchStartedMediaData)

            val commandRegex = "\"([^\"]*)\"|(\\S+)"

            val request = YoutubeDLRequest(urls = Collections.emptyList())

            val matcher = Pattern.compile(commandRegex).matcher(searchUrl)

            while (matcher.find()) {

                when (matcher.group(1) != null) {

                    true -> {

                        matcher.group(1)?.let { matchRequest: String ->

                            request.addOption(option = matchRequest)
                        }
                    }

                    false -> {

                        matcher.group(2)?.let { matchRequest: String ->

                            request.addOption(option = matchRequest)
                        }
                    }
                }
            }

            try {

                val youtubeDLResponse = YoutubeDL.getInstance().getInfo(url = searchUrl)

                val mediaList = youtubeDLResponse.formats?.map { videoFormat: VideoFormat ->

                    MediaFormatData(
                        actualUrl = searchUrl,
                        asr = videoFormat.asr,
                        tbr = videoFormat.tbr,
                        abr = videoFormat.abr,
                        url = videoFormat.url ?: ConstantString.EMPTY,
                        formatId = videoFormat.formatId ?: ConstantString.EMPTY,
                        format = videoFormat.format ?: ConstantString.EMPTY,
                        formatNote = videoFormat.formatNote ?: ConstantString.EMPTY,
                        resolution = "${videoFormat.height} x ${videoFormat.width}",
                        fileSize = videoFormat.fileSize,
                        approximateSize = videoFormat.fileSizeApproximate,
                        fps = videoFormat.fps,
                        vCodec = videoFormat.vcodec ?: ConstantString.EMPTY,
                        aCodec = videoFormat.acodec ?: ConstantString.EMPTY,
                        ext = videoFormat.ext ?: ConstantString.EMPTY,
                        isVideoOnly = videoFormat.vcodec != "none" && videoFormat.acodec == "none"
                    )
                } ?: emptyList()

                val thumbnail = youtubeDLResponse.thumbnails?.get(
                    index = youtubeDLResponse.thumbnails?.lastIndex ?: 0
                )?.url ?: ConstantString.EMPTY

                val newMediaData = mediaData.copy(
                    actualUrl = youtubeDLResponse.url ?: ConstantString.EMPTY,
                    title = youtubeDLResponse.title ?: ConstantString.EMPTY,
                    thumbnail = thumbnail,
                    duration = youtubeDLResponse.duration * 1000L,
                    mediaFormatList = mediaList,
                    isSearch = false,
                    isEmpty = mediaList.isEmpty(),
                    message = ConstantString.EMPTY
                )

                emit(value = newMediaData)

            } catch (throwable: Throwable) {

                val searchFailedMediaData = mediaData.copy(
                    isSearch = false,
                    isEmpty = true,
                    message = throwable.localizedMessage ?: ConstantString.EMPTY
                )

                emit(value = searchFailedMediaData)

                SetLog.setError(message = throwable.localizedMessage, throwable = throwable)
            }
        }.flowOn(context = Dispatchers.IO)
    }

    override suspend fun getPlaylistMediaData(
        searchUrl: String
    ) : Flow<PlaylistMediaData> {

        return flow {

            val playlistMediaData = PlaylistMediaData()

            val searchStartedPlaylistMediaData = playlistMediaData.copy(isSearch = true)

            emit(value = searchStartedPlaylistMediaData)

            val commandRegex = "\"([^\"]*)\"|(\\S+)"

            val request = YoutubeDLRequest(urls = Collections.emptyList())

            val matcher = Pattern.compile(commandRegex).matcher(searchUrl)

            while (matcher.find()) {

                when (matcher.group(1) != null) {

                    true -> {

                        matcher.group(1)?.let { matchRequest: String ->

                            request.addOption(option = matchRequest)
                        }
                    }

                    false -> {

                        matcher.group(2)?.let { matchRequest: String ->

                            request.addOption(option = matchRequest)
                        }
                    }
                }
            }

            try {

                val youtubeDLResponse = YoutubeDL.getInstance().getPlaylistInfo(url = searchUrl)

                val mediaList = youtubeDLResponse.map { videoInfo: VideoInfo ->

                    val mediaFormatList = videoInfo.formats?.map { videoFormat: VideoFormat ->

                        MediaFormatData(
                            actualUrl = videoInfo.url ?: ConstantString.EMPTY,
                            asr = videoFormat.asr,
                            tbr = videoFormat.tbr,
                            abr = videoFormat.abr,
                            url = videoFormat.url ?: ConstantString.EMPTY,
                            formatId = videoFormat.formatId ?: ConstantString.EMPTY,
                            format = videoFormat.format ?: ConstantString.EMPTY,
                            formatNote = videoFormat.formatNote ?: ConstantString.EMPTY,
                            resolution = "${videoFormat.height} x ${videoFormat.width}",
                            fileSize = videoFormat.fileSize,
                            approximateSize = videoFormat.fileSizeApproximate,
                            fps = videoFormat.fps,
                            vCodec = videoFormat.vcodec ?: ConstantString.EMPTY,
                            aCodec = videoFormat.acodec ?: ConstantString.EMPTY,
                            ext = videoFormat.ext ?: ConstantString.EMPTY,
                            isVideoOnly = videoFormat.vcodec != "none" && videoFormat.acodec == "none"
                        )
                    } ?: emptyList()

                    val thumbnail = videoInfo.thumbnails?.get(
                        index = videoInfo.thumbnails?.lastIndex ?: 0
                    )?.url ?: ConstantString.EMPTY

                    MediaData(
                        actualUrl = videoInfo.url ?: ConstantString.EMPTY,
                        title = videoInfo.title ?: ConstantString.EMPTY,
                        thumbnail = thumbnail,
                        duration = videoInfo.duration * 1000L,
                        mediaFormatList = mediaFormatList,
                        isSearch = true,
                        message = ConstantString.EMPTY
                    )
                }

                val newPlaylistMediaData = playlistMediaData.copy(
                    title = youtubeDLResponse.get(index = 0).playlistTitle,
                    mediaList = mediaList,
                    isSearch = false,
                    isEmpty = mediaList.isEmpty(),
                    message = ConstantString.EMPTY
                )

                emit(value = newPlaylistMediaData)

            } catch (throwable: Throwable) {

                val searchFailedPlaylistMediaData = playlistMediaData.copy(
                    isSearch = false,
                    isEmpty = true,
                    message = throwable.localizedMessage ?: ConstantString.EMPTY
                )

                emit(value = searchFailedPlaylistMediaData)

                SetLog.setError(message = throwable.localizedMessage, throwable = throwable)
            }
        }.flowOn(context = Dispatchers.IO)
    }
}