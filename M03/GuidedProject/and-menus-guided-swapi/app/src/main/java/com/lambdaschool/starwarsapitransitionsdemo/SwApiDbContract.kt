package com.lambdaschool.starwarsapitransitionsdemo

import android.provider.BaseColumns

class SwApiDbContract {
    class PlanetsEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "planets"
            val COLUMN_NAME_NAME = "name"
            val COLUMN_NAME_ROTATION_PERIOD = "rotation_period"
            val COLUMN_NAME_ORBITAL_PERIOD = "orbital_period"
            val COLUMN_NAME_DIAMETER = "diameter"
            val COLUMN_NAME_CLIMATE = "climate"
            val COLUMN_NAME_GRAVITY = "gravity"
            val COLUMN_NAME_TERRAIN = "terrain"
            val COLUMN_NAME_SURFACE_WATER = "surface_water"
            val COLUMN_NAME_POPULATION = "population"

            // This is how a create table query would look if the data types were constants as well
            /*public static final String PRIMARY_KEY = "PRIMARY KEY";
        public static final String INTEGER = "INTEGER";
        public static final String TEXT = "TEXT";

        public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
                                                      + " ( " +
                                                      _ID + " " + INTEGER + " " + PRIMARY_KEY + ", " +
                                                      COLUMN_NAME_NAME + " " + TEXT + ", " +
                                                      COLUMN_NAME_ROTATION_PERIOD + " " + INTEGER + ", " +
                                                      COLUMN_NAME_ORBITAL_PERIOD + " " + INTEGER + ", " +
                                                      COLUMN_NAME_DIAMETER + " " + INTEGER + ", " +
                                                      COLUMN_NAME_CLIMATE + " " + TEXT + ", " +
                                                      COLUMN_NAME_GRAVITY + " " + TEXT + ", " +
                                                      COLUMN_NAME_TERRAIN + " " + TEXT + ", " +
                                                      COLUMN_NAME_SURFACE_WATER + " " + INTEGER + ", " +
                                                      COLUMN_NAME_POPULATION + " " + INTEGER + "" +
                                                      " );";*/

            val SQL_CREATE_TABLE = ("CREATE TABLE " + TABLE_NAME
                    + " ( " +
                    BaseColumns._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAME_NAME + " TEXT," +
                    COLUMN_NAME_ROTATION_PERIOD + " INTEGER," +
                    COLUMN_NAME_ORBITAL_PERIOD + " INTEGER," +
                    COLUMN_NAME_DIAMETER + " INTEGER," +
                    COLUMN_NAME_CLIMATE + " TEXT," +
                    COLUMN_NAME_GRAVITY + " TEXT," +
                    COLUMN_NAME_TERRAIN + " TEXT," +
                    COLUMN_NAME_SURFACE_WATER + " INTEGER," +
                    COLUMN_NAME_POPULATION + " INTEGER" +
                    "  );")

            val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        }
    }

    class PeopleEntry : BaseColumns {
        companion object {

            val TABLE_NAME = "people"

            val COLUMN_NAME_NAME = "name"
            val COLUMN_NAME_HEIGHT = "height"
            val COLUMN_NAME_MASS = "mass"
            val COLUMN_NAME_HAIR_COLOR = "hair_color"
            val COLUMN_NAME_SKIN_COLOR = "skin_color"
            val COLUMN_NAME_EYE_COLOR = "eye_color"
            val COLUMN_NAME_BIRTH_YEAR = "birth_year"
            val COLUMN_NAME_GENDER = "gender"
            val COLUMN_NAME_HOMEWORLD = "homeworld"

            val SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
                    BaseColumns._ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME_NAME + " TEXT, " +
                    COLUMN_NAME_HEIGHT + " INTEGER, " +
                    COLUMN_NAME_MASS + " INTEGER, " +
                    COLUMN_NAME_HAIR_COLOR + " TEXT, " +
                    COLUMN_NAME_SKIN_COLOR + " TEXT, " +
                    COLUMN_NAME_EYE_COLOR + " TEXT, " +
                    COLUMN_NAME_BIRTH_YEAR + " TEXT, " +
                    COLUMN_NAME_GENDER + " TEXT, " +
                    COLUMN_NAME_HOMEWORLD + " INTEGER " + ");"


            val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        }
    }

    class SpeciesEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "species"
            val COLUMN_NAME_NAME = "name"
            val COLUMN_NAME_CLASSIFICATION = "classification"
            val COLUMN_NAME_DESIGNATION = "designation"
            val COLUMN_NAME_AVERAGE_HEIGHT = "average_height"
            val COLUMN_NAME_SKIN_COLORS = "skin_colors"
            val COLUMN_NAME_HAIR_COLORS = "hair_colors"
            val COLUMN_NAME_EYE_COLORS = "eye_colors"
            val COLUMN_NAME_AVERAGE_LIFESPAN = "average_lifespan"
            val COLUMN_NAME_NATIVE_LANGUAGE = "native_language"

            val SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                    "  id INTEGER PRIMARY KEY," +
                    "  " + COLUMN_NAME_NAME + " TEXT," +
                    "  " + COLUMN_NAME_CLASSIFICATION + " TEXT," +
                    "  " + COLUMN_NAME_DESIGNATION + " TEXT," +
                    "  " + COLUMN_NAME_AVERAGE_HEIGHT + " INTEGER," +
                    "  " + COLUMN_NAME_SKIN_COLORS + " INTEGER," +
                    "  " + COLUMN_NAME_HAIR_COLORS + " TEXT," +
                    "  " + COLUMN_NAME_EYE_COLORS + " TEXT," +
                    "  " + COLUMN_NAME_AVERAGE_LIFESPAN + " INTEGER," +
                    "  " + COLUMN_NAME_NATIVE_LANGUAGE + " TEXT" +
                    "  );"

            val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        }
    }
}
