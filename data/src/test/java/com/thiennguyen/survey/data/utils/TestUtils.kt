package com.thiennguyen.survey.data.utils

import java.nio.file.Paths

open class TestUtils {
    companion object {

        // get string json from `resources` folder
        fun getStringJson(fileName: String): String {
            val res = this::class.java.classLoader?.getResource(fileName)
            val file = Paths.get(res?.toURI()).toFile()
            return String(file.readBytes())
        }
    }
}
