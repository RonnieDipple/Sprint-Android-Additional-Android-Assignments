package com.lambdaschool.starwarsapitransitionsdemo

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

import java.util.ArrayList

class SwApiSqlDao(context: Context) {
    private val db: SQLiteDatabase

    val allPlanets: List<Planet>
        get() {
            val cursor = db.rawQuery("SELECT * FROM " + SwApiDbContract.PlanetsEntry.TABLE_NAME,
                    arrayOf())

            val rows = ArrayList<Planet>()
            while (cursor.moveToNext()) {
                rows.add(getPlanetFromCursor(cursor))
            }

            cursor.close()
            return rows
        }

    val allPeople: List<Person>
        get() {
            val cursor = db.rawQuery("SELECT * FROM " + SwApiDbContract.PeopleEntry.TABLE_NAME,
                    arrayOf())

            val rows = ArrayList<Person>()
            while (cursor.moveToNext()) {
                rows.add(getPersonFromCursor(cursor))
            }

            cursor.close()
            return rows
        }

    init {
        val dbHelper = SwApiDbHelper(context)
        db = dbHelper.writableDatabase
    }

    fun getPlanetsBySize(min: Int, max: Int): List<Planet> {
        //        select * from planets where diameter between 11700 and 14700
        val cursor = db.rawQuery("SELECT * FROM " + SwApiDbContract.PlanetsEntry.TABLE_NAME + " WHERE " + SwApiDbContract.PlanetsEntry.COLUMN_NAME_DIAMETER + " BETWEEN ? AND ?", arrayOf(Integer.toString(min), Integer.toString(max)))

        val rows = ArrayList<Planet>()
        while (cursor.moveToNext()) {
            rows.add(getPlanetFromCursor(cursor))
        }
        return rows
    }

    fun getPlanet(i: Int): Planet {
        val cursor = db.rawQuery("SELECT * FROM " + SwApiDbContract.PlanetsEntry.TABLE_NAME + " WHERE _id=?", arrayOf(Integer.toString(i)))

        //        int id, String name, String rotationPeriod, String orbitalPeriod, String diameter, String climate, String gravity, String terrain
        cursor.moveToFirst()

        return getPlanetFromCursor(cursor)
    }

    private fun getPlanetFromCursor(cursor: Cursor): Planet {
        var index = cursor.getColumnIndexOrThrow(BaseColumns._ID)
        val id = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PlanetsEntry.COLUMN_NAME_NAME)
        val name = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PlanetsEntry.COLUMN_NAME_ROTATION_PERIOD)
        val rotationPeriod = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PlanetsEntry.COLUMN_NAME_ORBITAL_PERIOD)
        val orbitalPeriod = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PlanetsEntry.COLUMN_NAME_DIAMETER)
        val diameter = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PlanetsEntry.COLUMN_NAME_CLIMATE)
        val climate = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PlanetsEntry.COLUMN_NAME_GRAVITY)
        val gravity = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PlanetsEntry.COLUMN_NAME_TERRAIN)
        val terrain = cursor.getString(index)

        return Planet(id, name, Integer.toString(rotationPeriod),
                Integer.toString(orbitalPeriod),
                Integer.toString(diameter),
                climate, gravity, terrain)
    }

    fun createPerson(person: Person) {
        val values = getContentValues(person)

        val insert = db.insert(SwApiDbContract.PeopleEntry.TABLE_NAME, null, values)
    }

    fun updatePerson(person: Person) {
        val affectedRows = db.update(
                SwApiDbContract.PeopleEntry.TABLE_NAME,
                getContentValues(person),
                BaseColumns._ID + "=?", //id=1
                arrayOf(Integer.toString(person.id)))
    }

    fun deletePerson(person: Person) {
        val affectedRows = db.delete(SwApiDbContract.PeopleEntry.TABLE_NAME,
                BaseColumns._ID + "=?",
                arrayOf(Integer.toString(person.id)))
    }

    private fun getContentValues(person: Person): ContentValues {
        val values = ContentValues()
        //        int id, String name, String height, String mass, String hairColor, String skinColor, String eyeColor

        values.put(BaseColumns._ID, person.id)
        values.put(SwApiDbContract.PeopleEntry.COLUMN_NAME_NAME, person.name)
        values.put(SwApiDbContract.PeopleEntry.COLUMN_NAME_HEIGHT, Integer.parseInt(person.height!!))
        values.put(SwApiDbContract.PeopleEntry.COLUMN_NAME_MASS, Integer.parseInt(person.mass!!))
        values.put(SwApiDbContract.PeopleEntry.COLUMN_NAME_HAIR_COLOR, person.hairColor)
        values.put(SwApiDbContract.PeopleEntry.COLUMN_NAME_SKIN_COLOR, person.skinColor)
        values.put(SwApiDbContract.PeopleEntry.COLUMN_NAME_EYE_COLOR, person.eyeColor)
        return values
    }

    private fun getPersonFromCursor(cursor: Cursor): Person {
        var index: Int
        index = cursor.getColumnIndexOrThrow(BaseColumns._ID)
        val id = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PeopleEntry.COLUMN_NAME_NAME)
        val name = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PeopleEntry.COLUMN_NAME_HEIGHT)
        val height = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PeopleEntry.COLUMN_NAME_MASS)
        val mass = cursor.getInt(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PeopleEntry.COLUMN_NAME_HAIR_COLOR)
        val hairColor = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PeopleEntry.COLUMN_NAME_EYE_COLOR)
        val eyeColor = cursor.getString(index)

        index = cursor.getColumnIndexOrThrow(SwApiDbContract.PeopleEntry.COLUMN_NAME_SKIN_COLOR)
        val skinColor = cursor.getString(index)

        return Person(id, name, Integer.toString(height),
                Integer.toString(mass), hairColor,
                skinColor, eyeColor)
    }
}
