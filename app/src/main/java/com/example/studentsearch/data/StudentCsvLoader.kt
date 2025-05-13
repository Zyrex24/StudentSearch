package com.example.studentsearch.data

import android.content.Context
import com.opencsv.CSVReader
import java.io.InputStreamReader

object StudentCsvLoader {
    fun loadStudents(context: Context): List<Student> {
        val students = mutableListOf<Student>()
        val inputStream = context.assets.open("student_courses_with_contacts.csv")
        val reader = CSVReader(InputStreamReader(inputStream))
        val rows = reader.readAll()
        for (row in rows.drop(1)) { // skip header
            val id = row[0]
            val name = row[1]
            val gpa = row[2]
            val program = row[3]
            val level = row[4]
            val totalCourses = row[5]
            val phone = row[6]
            val mobile = row[7]
            val address = row[8]
            val email = row[9]
            val nationalId = row[10]

            val courseHistory = mutableListOf<Course>()
            var i = 11
            while (i + 3 < row.size && row[i].isNotBlank()) {
                val courseName = row[i]
                val courseYear = row[i + 1]
                val courseGrade = row[i + 2]
                val courseMark = row[i + 3]
                courseHistory.add(
                    Course(
                        name = courseName,
                        code = "", // Not present in CSV
                        grade = courseGrade,
                        semester = "", // Not present in CSV
                        year = courseYear
                    )
                )
                i += 4
            }

            students.add(
                Student(
                    id = id,
                    name = name,
                    phone = phone,
                    mobile = mobile,
                    email = email,
                    gender = "",
                    dateOfBirth = "",
                    nationality = "",
                    currentStatus = "",
                    currentLevel = level,
                    currentMajor = program,
                    currentCollege = "",
                    currentDepartment = "",
                    currentGpa = gpa,
                    currentHours = totalCourses,
                    currentSemester = "",
                    currentYear = "",
                    address = address,
                    nationalId = nationalId,
                    courseHistory = courseHistory
                )
            )
        }
        return students
    }
} 