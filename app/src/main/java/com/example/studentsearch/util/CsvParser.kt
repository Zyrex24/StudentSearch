package com.example.studentsearch.util

import com.example.studentsearch.model.Course
import com.example.studentsearch.model.Student
import com.opencsv.CSVReader
import java.io.InputStreamReader

class CsvParser {
    fun parseCsv(inputStream: InputStreamReader): List<Student> {
        val reader = CSVReader(inputStream)
        val students = mutableListOf<Student>()
        
        // Skip header row
        reader.readNext()
        
        var currentRow: Array<String>?
        while (reader.readNext().also { currentRow = it } != null) {
            currentRow?.let { row ->
                try {
                    val student = Student(
                        id = row[0],
                        name = row[1],
                        gpa = row[2].toDoubleOrNull() ?: 0.0,
                        program = row[3],
                        level = row[4],
                        totalCourses = row[5].toIntOrNull() ?: 0,
                        phone = row[6],
                        mobile = row[7],
                        address = row[8],
                        email = row[9],
                        nationalId = row[10],
                        courses = parseCourses(row)
                    )
                    students.add(student)
                } catch (e: Exception) {
                    // Log error and continue with next row
                    e.printStackTrace()
                }
            }
        }
        
        return students
    }
    
    private fun parseCourses(row: Array<String>): List<Course> {
        val courses = mutableListOf<Course>()
        // Assuming courses start from index 11 and come in groups of 4
        var index = 11
        while (index + 3 < row.size) {
            try {
                val courseName = row[index]
                val course = Course(
                    id = courseName, // Using course name as ID, consistent with ViewModel
                    name = courseName,
                    year = row[index + 1],
                    letterGrade = row[index + 2],
                    numericGrade = row[index + 3].toDoubleOrNull() ?: 0.0
                )
                courses.add(course)
                index += 4
            } catch (e: Exception) {
                // Skip invalid course entries
                index += 4
            }
        }
        return courses
    }
} 