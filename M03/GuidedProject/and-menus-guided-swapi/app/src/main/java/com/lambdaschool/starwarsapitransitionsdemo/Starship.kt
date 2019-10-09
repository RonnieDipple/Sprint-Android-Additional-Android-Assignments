package com.lambdaschool.starwarsapitransitionsdemo

class Starship(id: Int,
               name: String,
               val model: String,
               val manufacturer: String,
               val costInCredits: String,
               val length: String) : SwApiObject(id, name) {

    /*
    * "model": "DS-1 Orbital Battle Station",
	"manufacturer": "Imperial Department of Military Research, Sienar Fleet Systems",
	"cost_in_credits": "1000000000000",
	"length": "120000",
    * */

    override fun toString(): String {
        return String.format("The %s, model %s is manufactured by %s is %s meters in length built at a cost of %s credits.", name, model, manufacturer, length, costInCredits)
    }
}
