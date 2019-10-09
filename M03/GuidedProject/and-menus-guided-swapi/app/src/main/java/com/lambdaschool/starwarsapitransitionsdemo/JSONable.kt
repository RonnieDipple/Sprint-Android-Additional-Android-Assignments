package com.lambdaschool.starwarsapitransitionsdemo

interface JSONable {
    fun toJsonString(): String
    fun fromJsonString(jsonString: String)
}
