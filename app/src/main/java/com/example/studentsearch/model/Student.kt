package com.example.studentsearch.model

data class Student(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val mobile: String,
    val address: String,
    val nationalId: String,
    val program: String,
    val level: String,
    val gpa: Double,
    val totalCourses: Int,
    val courses: List<Course>
)

data class Course(
    val id: String,
    val name: String,
    val year: String,
    val letterGrade: String,
    val numericGrade: Double
) 