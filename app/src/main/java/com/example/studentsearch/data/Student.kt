package com.example.studentsearch.data

data class Student(
    val id: String,
    val name: String,
    val phone: String,
    val mobile: String,
    val email: String,
    val gender: String,
    val dateOfBirth: String,
    val nationality: String,
    val currentStatus: String,
    val currentLevel: String,
    val currentMajor: String,
    val currentCollege: String,
    val currentDepartment: String,
    val currentGpa: String,
    val currentHours: String,
    val currentSemester: String,
    val currentYear: String,
    val address: String,
    val nationalId: String,
    val courseHistory: List<Course>
)

data class Course(
    val code: String,
    val name: String,
    val grade: String,
    val semester: String,
    val year: String
) 