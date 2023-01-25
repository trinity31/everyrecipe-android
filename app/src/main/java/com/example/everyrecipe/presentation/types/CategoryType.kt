package com.example.everyrecipe.presentation.types

enum class CategoryType(val categoryId: String) {
    VEG("1e1f0b80-9423-11ec-b5d2-43970bc42d5d"),
    MEAT("1ddc5d30-9423-11ec-b5d2-43970bc42d5d"),
    SEAFOOD("1e350480-9423-11ec-b5d2-43970bc42d5d"),
    BEAN("1e4705e0-9423-11ec-b5d2-43970bc42d5d"),
    MUSHROOM("1e28cf80-9423-11ec-b5d2-43970bc42d5d"),
    GRAIN("1e874330-9423-11ec-b5d2-43970bc42d5d"),
    POTATO("1ea55280-9423-11ec-b5d2-43970bc42d5d"),
    EGG("1e6b7dd0-9423-11ec-b5d2-43970bc42d5d"),
    MILK("1e1658f0-9423-11ec-b5d2-43970bc42d5d"),
    SEWEED("1e806560-9423-11ec-b5d2-43970bc42d5d"),
    POWDER("1e3d68f0-9423-11ec-b5d2-43970bc42d5d"),
    FRUIT("1e93c650-9423-11ec-b5d2-43970bc42d5d"),
    NUT("1e5054b0-9423-11ec-b5d2-43970bc42d5d"),
    SAUCE("1e0d3130-9423-11ec-b5d2-43970bc42d5d"),
    SEASONING("1dfe1600-9423-11ec-b5d2-43970bc42d5d"),
    OIL("1e77d9e0-9423-11ec-b5d2-43970bc42d5d"),
    PROCESSED("1eb7a200-9423-11ec-b5d2-43970bc42d5d"),
    BEVERAGE("1e597c70-9423-11ec-b5d2-43970bc42d5d"),
    ETC("1e9c2ac0-9423-11ec-b5d2-43970bc42d5d"),
    NONAME("1e627d20-9423-11ec-b5d2-43970bc42d5d");

    fun getId(): String {
        return categoryId
    }

    fun isAllowed(vegType: String): Boolean {
        return when (this) {
            VEG -> true
            MEAT -> (vegType == Constants.NONE)
            SEAFOOD -> (vegType == Constants.NONE || vegType == Constants.PESCO)
            BEAN -> true
            MUSHROOM -> true
            GRAIN -> false
            POTATO -> true
            EGG -> (vegType == Constants.NONE || vegType == Constants.PESCO || vegType == Constants.LACTOOVO || vegType == Constants.OVO)
            MILK -> (vegType == Constants.NONE || vegType == Constants.PESCO || vegType == Constants.LACTOOVO || vegType == Constants.LACTO)
            SEWEED -> true
            POWDER -> false
            FRUIT -> true
            NUT -> true
            SAUCE -> false
            SEASONING -> false
            OIL -> false
            PROCESSED -> true
            BEVERAGE -> false
            ETC -> false
            NONAME -> false
        }
    }

    companion object {
        fun getValue(id: String): CategoryType {
            return when (id) {
                "1e1f0b80-9423-11ec-b5d2-43970bc42d5d" -> VEG
                "1ddc5d30-9423-11ec-b5d2-43970bc42d5d" -> MEAT
                "1e350480-9423-11ec-b5d2-43970bc42d5d" -> SEAFOOD
                "1e4705e0-9423-11ec-b5d2-43970bc42d5d" -> BEAN
                "1e28cf80-9423-11ec-b5d2-43970bc42d5d" -> MUSHROOM
                "1e874330-9423-11ec-b5d2-43970bc42d5d" -> GRAIN
                "1ea55280-9423-11ec-b5d2-43970bc42d5d" -> POTATO
                "1e6b7dd0-9423-11ec-b5d2-43970bc42d5d" -> EGG
                "1e1658f0-9423-11ec-b5d2-43970bc42d5d" -> MILK
                "1e806560-9423-11ec-b5d2-43970bc42d5d" -> SEWEED
                "1e3d68f0-9423-11ec-b5d2-43970bc42d5d" -> POWDER
                "1e93c650-9423-11ec-b5d2-43970bc42d5d" -> FRUIT
                "1e5054b0-9423-11ec-b5d2-43970bc42d5d" -> NUT
                "1e0d3130-9423-11ec-b5d2-43970bc42d5d" -> SAUCE
                "1dfe1600-9423-11ec-b5d2-43970bc42d5d" -> SEASONING
                "1e77d9e0-9423-11ec-b5d2-43970bc42d5d" -> OIL
                "1eb7a200-9423-11ec-b5d2-43970bc42d5d" -> PROCESSED
                "1e597c70-9423-11ec-b5d2-43970bc42d5d" -> BEVERAGE
                "1e9c2ac0-9423-11ec-b5d2-43970bc42d5d" -> ETC
                "1e627d20-9423-11ec-b5d2-43970bc42d5d" -> NONAME
                else -> NONAME
            }
        }
    }
}