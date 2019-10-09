package com.lambdaschool.starwarsapitransitionsdemo

import android.content.Context
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.provider.BaseColumns
import android.support.annotation.RequiresApi

class SwApiDbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    constructor(context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int) : this(context) {}

    constructor(context: Context, name: String, factory: SQLiteDatabase.CursorFactory, version: Int, errorHandler: DatabaseErrorHandler) : this(context) {}

    @RequiresApi(api = Build.VERSION_CODES.P)
    constructor(context: Context, name: String, version: Int, openParams: SQLiteDatabase.OpenParams) : this(context) {
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SwApiDbContract.PeopleEntry.SQL_CREATE_TABLE)
        db.execSQL(SwApiDbContract.SpeciesEntry.SQL_CREATE_TABLE)
        db.execSQL(SwApiDbContract.PlanetsEntry.SQL_CREATE_TABLE)
        populateStarterData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SwApiDbContract.PeopleEntry.SQL_DELETE_TABLE)
        db.execSQL(SwApiDbContract.SpeciesEntry.SQL_DELETE_TABLE)
        db.execSQL(SwApiDbContract.PlanetsEntry.SQL_DELETE_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    private fun populateStarterData(db: SQLiteDatabase) {
        db.execSQL(String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)" +
                "VALUES " +
                "(2, \"Alderaan\", 24, 364, 12500, \"temperate\", \"1 standard\", \"grasslands, mountains\", 40, 2000000000),\n" +
                "(3, \"Yavin IV\", 24, 4818, 10200, \"temperate, tropical\", \"1 standard\", \"jungle, rainforests\", 8, 1000),\n" +
                "(4, \"Hoth\", 23, 549, 7200, \"frozen\", \"1.1 standard\", \"tundra, ice caves, mountain ranges\", 100, -1),\n" +
                "(5, \"Dagobah\", 23, 341, 8900, \"murky\", \"n/a\", \"swamp, jungles\", 8, -1),\n" +
                "(6, \"Bespin\", 12, 5110, 118000, \"temperate\", \"1.5 (surface), 1 standard (Cloud City)\", \"gas giant\", 0, 6000000),\n" +
                "(7, \"Endor\", 18, 402, 4900, \"temperate\", \"0.85 standard\", \"forests, mountains, lakes\", 8, 30000000),\n" +
                "(8, \"Naboo\", 26, 312, 12120, \"temperate\", \"1 standard\", \"grassy hills, swamps, forests, mountains\", 12, 4500000000),\n" +
                "(9, \"Coruscant\", 24, 368, 12240, \"temperate\", \"1 standard\", \"cityscape, mountains\", -1, 1000000000000),\n" +
                "(10, \"Kamino\", 27, 463, 19720, \"temperate\", \"1 standard\", \"ocean\", 100, 1000000000),\n" +
                "(11, \"Geonosis\", 30, 256, 11370, \"temperate, arid\", \"0.9 standard\", \"rock, desert, mountain, barren\", 5, 100000000000);",
                SwApiDbContract.PlanetsEntry.TABLE_NAME,
                BaseColumns._ID,
                SwApiDbContract.PlanetsEntry.COLUMN_NAME_NAME,
                SwApiDbContract.PlanetsEntry.COLUMN_NAME_ROTATION_PERIOD,
                SwApiDbContract.PlanetsEntry.COLUMN_NAME_ORBITAL_PERIOD,
                SwApiDbContract.PlanetsEntry.COLUMN_NAME_DIAMETER,
                SwApiDbContract.PlanetsEntry.COLUMN_NAME_CLIMATE,
                SwApiDbContract.PlanetsEntry.COLUMN_NAME_GRAVITY,
                SwApiDbContract.PlanetsEntry.COLUMN_NAME_TERRAIN,
                SwApiDbContract.PlanetsEntry.COLUMN_NAME_SURFACE_WATER,
                SwApiDbContract.PlanetsEntry.COLUMN_NAME_POPULATION))

        db.execSQL(String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)" +
                "VALUES " +
                "(84, \"Finn\", -1, -1, \"black\", \"dark\", \"dark\", \"unknown\", \"male\", 28)" +
                "(85, \"Rey\", -1, -1, \"brown\", \"light\", \"hazel\", \"unknown\", \"female\", 28)," +
                "(86, \"Poe Dameron\", -1, -1, \"brown\", \"light\", \"brown\", \"unknown\", \"male\", 28)," +
                "(87, \"BB8\", -1, -1, \"none\", \"none\", \"black\", \"unknown\", \"none\", 28)," +
                "(33, \"Nute Gunray\", 191, 90, \"none\", \"mottled green\", \"red\", \"unknown\", \"male\", 18)," +
                "(32, \"Qui-Gon Jinn\", 193, 89, \"brown\", \"fair\", \"blue\", \"92BBY\", \"male\", 28)," +
                "(31, \"Nien Nunb\", 160, 68, \"none\", \"grey\", \"black\", \"unknown\", \"male\", 33)," +
                "(30, \"Wicket Systri Warrick\", 88, 20, \"brown\", \"brown\", \"brown\", \"8BBY\", \"male\", 7);",
                SwApiDbContract.PeopleEntry.TABLE_NAME,
                BaseColumns._ID,
                SwApiDbContract.PeopleEntry.COLUMN_NAME_NAME,
                SwApiDbContract.PeopleEntry.COLUMN_NAME_HEIGHT,
                SwApiDbContract.PeopleEntry.COLUMN_NAME_MASS,
                SwApiDbContract.PeopleEntry.COLUMN_NAME_HAIR_COLOR,
                SwApiDbContract.PeopleEntry.COLUMN_NAME_SKIN_COLOR,
                SwApiDbContract.PeopleEntry.COLUMN_NAME_EYE_COLOR,
                SwApiDbContract.PeopleEntry.COLUMN_NAME_BIRTH_YEAR,
                SwApiDbContract.PeopleEntry.COLUMN_NAME_GENDER,
                SwApiDbContract.PeopleEntry.COLUMN_NAME_HOMEWORLD))

        db.execSQL(String.format("INSERT INTO %s(%s, %s, %s, %s, %s, %s, %s, %s, %s, %s)" +
                "VALUES " +
                "('Yoda''s species', 'mammal', 'sentient', 66, 'green, yellow', 'brown, white', 'brown, green, yellow', 900, 'Galactic basic')," +
                "(\"Hutt\", \"gastropod\", \"sentient\", 300, \"green, brown, tan\", \"n/a\", \"yellow, red\", 1000, \"Huttese\")" +
                "(\"Trandoshan\", \"reptile\", \"sentient\", 200, \"brown, green\", \"none\",\"yellow, orange\", \"unknown\", \"Dosh\");",
                SwApiDbContract.SpeciesEntry.TABLE_NAME,
                BaseColumns._ID,
                SwApiDbContract.SpeciesEntry.COLUMN_NAME_NAME,
                SwApiDbContract.SpeciesEntry.COLUMN_NAME_CLASSIFICATION,
                SwApiDbContract.SpeciesEntry.COLUMN_NAME_DESIGNATION,
                SwApiDbContract.SpeciesEntry.COLUMN_NAME_AVERAGE_HEIGHT,
                SwApiDbContract.SpeciesEntry.COLUMN_NAME_SKIN_COLORS,
                SwApiDbContract.SpeciesEntry.COLUMN_NAME_HAIR_COLORS,
                SwApiDbContract.SpeciesEntry.COLUMN_NAME_EYE_COLORS,
                SwApiDbContract.SpeciesEntry.COLUMN_NAME_AVERAGE_LIFESPAN,
                SwApiDbContract.SpeciesEntry.COLUMN_NAME_NATIVE_LANGUAGE))
    }

    companion object {

        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "SwApiDatabase.db"
    }
}
