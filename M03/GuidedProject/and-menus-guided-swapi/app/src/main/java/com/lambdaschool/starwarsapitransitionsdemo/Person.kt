package com.lambdaschool.starwarsapitransitionsdemo

import org.json.JSONException
import org.json.JSONObject

class Person : SwApiObject, JSONable {

    var height: String? = null
        private set
    var mass: String? = null
        private set
    var hairColor: String? = null
        private set
    var skinColor: String? = null
        private set
    var eyeColor: String? = null

    constructor(id: Int, name: String) : super(id, name) {}

    constructor(id: Int, name: String, height: String, mass: String, hairColor: String, skinColor: String, eyeColor: String) : super(id, name) {
        this.height = height
        this.mass = mass
        this.hairColor = hairColor
        this.skinColor = skinColor
        this.eyeColor = eyeColor
    }

    override fun toString(): String {
        return String.format("%s, is %scm tall, they have %s skin, and %s hair with %s eyes.",
                name, height, skinColor, hairColor, eyeColor)
    }

    override fun toJsonString(): String {
        try {
            val json = JSONObject()
            json.put("name", this.name)
            json.put("height", height)
            json.put("hair_color", hairColor)
            json.put("skin_color", skinColor)
            json.put("eye_color", eyeColor)
            return json.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
            return ""
        }

    }

    override fun fromJsonString(jsonString: String) {
        try {
            val json = JSONObject(jsonString)

            this.name = json.getString("name")
            this.height = json.getString("height")
            this.mass = json.getString("mass")
            this.hairColor = json.getString("hair_color")
            this.skinColor = json.getString("skin_color")
            this.eyeColor = json.getString("eye_color")
            val urlParts = json.getString("url").split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
            this.id = Integer.parseInt(urlParts[urlParts.size - 2])
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}

