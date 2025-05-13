package com.example.studentsearch.data

object StudentData {
    val students = listOf(
        Student(
            id = "2023001",
            name = "John Doe",
            phone = "1234567890",
            mobile = "",
            email = "john.doe@example.com",
            gender = "Male",
            dateOfBirth = "2000-01-01",
            nationality = "US",
            currentStatus = "Active",
            currentLevel = "Senior",
            currentMajor = "Computer Science",
            currentCollege = "Engineering",
            currentDepartment = "Computer Science",
            currentGpa = "3.8",
            currentHours = "120",
            currentSemester = "Fall",
            currentYear = "2024",
            address = "123 University Ave, College Town, ST 12345",
            nationalId = "",
            courseHistory = listOf(
                Course("CS101", "Introduction to Programming", "A", "Fall 2021", "2021"),
                Course("CS102", "Data Structures", "A-", "Spring 2022", "2022"),
                Course("CS201", "Algorithms", "B+", "Fall 2022", "2022")
            )
        )
        // Add more sample students as needed
    )
} 