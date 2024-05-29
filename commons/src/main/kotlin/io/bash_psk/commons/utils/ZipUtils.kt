package io.bash_psk.commons.utils

import android.system.Os
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream
import org.apache.commons.compress.archivers.zip.ZipFile
import org.apache.commons.io.IOUtils
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets

object ZipUtils {

    fun unzip(
        sourceFile: File?,
        targetDirectory: File
    ) {

        ZipFile(sourceFile).use { zipFile: ZipFile ->

            val entries = zipFile.entries

            while (entries.hasMoreElements()) {

                val entry = entries.nextElement()
                val entryDestination = File(targetDirectory, entry.name)

                when {

                    !entryDestination.canonicalPath.startsWith(
                        prefix = targetDirectory.canonicalPath + File.separator
                    ) -> {

                        throw IllegalAccessException(
                            "Entry Is Outside Of The Target Dir : ${entry.name}"
                        )
                    }

                    entry.isDirectory -> {

                        entryDestination.mkdirs()
                    }

                    entry.isUnixSymlink -> {

                        zipFile.getInputStream(entry).use { inputStream: InputStream ->

                            val symlink = IOUtils.toString(inputStream, StandardCharsets.UTF_8)

                            Os.symlink(symlink, entryDestination.absolutePath)
                        }
                    }

                    else -> {

                        entryDestination.parentFile?.mkdirs()

                        zipFile.getInputStream(entry).use { inputStream: InputStream ->

                            FileOutputStream(entryDestination).use { fileOutputStream: FileOutputStream ->

                                IOUtils.copy(inputStream, fileOutputStream)
                            }
                        }
                    }
                }
            }
        }
    }

    fun unzip(
        inputStream: InputStream?,
        targetDirectory: File
    ) {

        ZipArchiveInputStream(
            BufferedInputStream(inputStream)
        ).use { zipArchiveInputStream: ZipArchiveInputStream ->

            val entry = zipArchiveInputStream.nextZipEntry

            while (entry != null) {

                val entryDestination = File(targetDirectory, entry.name)

                when {

                    !entryDestination.canonicalPath.startsWith(
                        prefix = targetDirectory.canonicalPath + File.separator
                    ) -> {

                        throw IllegalAccessException(
                            "Entry Is Outside Of The Target Dir : ${entry.name}"
                        )
                    }

                    entry.isDirectory -> {

                        entryDestination.mkdirs()
                    }

                    else -> {

                        entryDestination.parentFile?.mkdirs()

                        FileOutputStream(entryDestination).use { fileOutputStream: FileOutputStream ->

                            IOUtils.copy(zipArchiveInputStream, fileOutputStream)
                        }
                    }
                }
            }
        }
    }
}