package com.example.mealna.classes

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val photoUrl: String = "", // Bisa diisi nanti
    val profile: UserProfile? = null
)

data class UserProfile(
    val height: Int = 0,
    val weight: Int = 0,
    val age: Int = 0,
    val gender: String = "",
    val diseases: List<String> = emptyList(),
    val allergies: List<String> = emptyList(),
    val dietPreferences: List<String> = emptyList(),
    val goal: String = ""
)