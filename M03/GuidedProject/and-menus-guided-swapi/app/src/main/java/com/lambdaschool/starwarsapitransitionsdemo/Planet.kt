package com.lambdaschool.starwarsapitransitionsdemo

class Planet(id: Int, name: String, val rotationPeriod: String, val orbitalPeriod: String, val diameter: String, val climate: String, val gravity: String, val terrain: String) : SwApiObject(id, name) {

    override fun toString(): String {
        return "Planet{" +
                "rotationPeriod='" + rotationPeriod + '\''.toString() +
                ", orbitalPeriod='" + orbitalPeriod + '\''.toString() +
                ", diameter='" + diameter + '\''.toString() +
                ", climate='" + climate + '\''.toString() +
                ", gravity='" + gravity + '\''.toString() +
                ", terrain='" + terrain + '\''.toString() +
                ", category='" + category + '\''.toString() +
                ", name='" + name + '\''.toString() +
                '}'.toString()
    }
}
