package com.lambdaschool.starwarsapitransitionsdemo

import org.json.JSONException
import org.json.JSONObject

object SwApiDao {
    private val BASE_URL = "https://swapi.co/api/"
    private val PERSON_URL = BASE_URL + "people/"
    private val STARSHIP_URL = BASE_URL + "starships/"
    private val PLANET_URL = BASE_URL + "planets/"

    interface SwApiCallback {
        fun processObject(`object`: SwApiObject?)
    }

    fun getPerson(id: Int): Person? {
        val result = NetworkAdapter.httpRequest(PERSON_URL + id)

        var `object`: Person? = null
        try {
            val json = JSONObject(result)

            val name = json.getString("name")
            val height = json.getString("height")
            val mass = json.getString("mass")
            val hairColor = json.getString("hair_color")
            val skinColor = json.getString("skin_color")
            val eyeColor = json.getString("eye_color")

            `object` = Person(id, name, height, mass, hairColor, skinColor, eyeColor)
            `object`.category = DrawableResolver.CHARACTER

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return `object`
    }

    fun getStarship(id: Int): Starship? {
        val result = NetworkAdapter.httpRequest(STARSHIP_URL + id)

        var `object`: Starship? = null
        try {
            val json = JSONObject(result)

            val name = json.getString("name")
            val model = json.getString("model")
            val mfg = json.getString("manufacturer")
            val cost = json.getString("cost_in_credits")
            val length = json.getString("length")

            `object` = Starship(id, name, model, mfg, cost, length)
            `object`.category = DrawableResolver.STARSHIP

        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return `object`
    }

    fun getPlanet(id: Int, callback: SwApiCallback) {
        NetworkAdapter.backgroundHttpRequest(PLANET_URL + id, object : NetworkAdapter.NetworkCallback {
            override fun processResult(result: String) {

                val planet: Planet?
                try {
                    val json = JSONObject(result)

                    val name = json.getString("name")
                    val rotationPeriod = json.getString("rotation_period")
                    val orbitalPeriod = json.getString("orbital_period")
                    val diameter = json.getString("diameter")
                    val climate = json.getString("climate")
                    val gravity = json.getString("gravity")
                    val terrain = json.getString("terrain")

                    planet = Planet(id, name, rotationPeriod, orbitalPeriod, diameter, climate, gravity, terrain)
                    planet.category = DrawableResolver.PLANET

                    callback.processObject(planet)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        })
    }
}
