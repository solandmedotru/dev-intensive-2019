package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?,String?> {
        val parts : List<String>? = fullName?.split(" ")
        var firstName = parts?.getOrNull(0)
        firstName =  when(firstName=="") {
            true  -> null
            false -> firstName
        }
        var lastName = parts?.getOrNull(1)
        lastName =  when(lastName=="") {
            true  -> null
            false -> lastName
        }
        return firstName to lastName
    }
}