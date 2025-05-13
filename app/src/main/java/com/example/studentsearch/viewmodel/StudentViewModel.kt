package com.example.studentsearch.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studentsearch.model.Student
import com.example.studentsearch.model.Course
import com.opencsv.CSVReader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.InputStreamReader

class StudentViewModel : ViewModel() {
    private val _searchResults = MutableStateFlow<List<Student>>(emptyList())
    val searchResults: StateFlow<List<Student>> = _searchResults

    private val _selectedStudent = MutableStateFlow<Student?>(null)
    val selectedStudent: StateFlow<Student?> = _selectedStudent

    private var allStudents: List<Student> = emptyList()

    private data class CourseInfo(
        val name: String,
        val year: String,
        val letterGrade: String,
        val numericGrade: Double
    )

    fun loadStudents(context: Context) {
        viewModelScope.launch {
            try {
                val inputStream = context.assets.open("student_courses_with_contacts.csv")
                val reader = CSVReader(InputStreamReader(inputStream))
                
                // Skip header row
                reader.readNext()
                
                val students = mutableListOf<Student>()
                val studentMap = mutableMapOf<String, MutableList<Course>>()
                
                reader.forEach { row ->
                    if (row.isNotEmpty() && row.size >= 11) {
                        val studentId = row[0]
                        
                        // Parse course information (starts from index 11, in groups of 4)
                        val courses = mutableListOf<CourseInfo>()
                        var i = 11
                        while (i + 3 < row.size && row[i].isNotEmpty()) {
                            courses.add(
                                CourseInfo(
                                    name = row[i],
                                    year = row[i + 1],
                                    letterGrade = row[i + 2],
                                    numericGrade = row[i + 3].toDoubleOrNull() ?: 0.0
                                )
                            )
                            i += 4
                        }
                        
                        // Add courses to student's course list
                        val studentCourses = courses.map { courseInfo ->
                            Course(
                                id = courseInfo.name, // Using course name as ID
                                name = courseInfo.name,
                                year = courseInfo.year,
                                letterGrade = courseInfo.letterGrade,
                                numericGrade = courseInfo.numericGrade
                            )
                        }
                        if (studentCourses.isNotEmpty()) {
                            studentMap[studentId] = studentCourses.toMutableList()
                        }
                        
                        // Create student if not exists
                        if (!students.any { it.id == studentId }) {
                            val student = Student(
                                id = row[0],
                                name = row[1],
                                gpa = row[2].toDoubleOrNull() ?: 0.0,
                                program = row[3],
                                level = row[4],
                                totalCourses = row[5].toIntOrNull() ?: 0,
                                phone = row[6].takeIf { it.isNotEmpty() } ?: "N/A",
                                mobile = row[7].takeIf { it.isNotEmpty() } ?: "N/A",
                                address = row[8].takeIf { it.isNotEmpty() } ?: "N/A",
                                email = row[9].takeIf { it.isNotEmpty() } ?: "N/A",
                                nationalId = row[10].takeIf { it.isNotEmpty() } ?: "N/A",
                                courses = emptyList()
                            )
                            students.add(student)
                        }
                    }
                }
                
                // Update students with their courses
                allStudents = students.map { student ->
                    student.copy(courses = studentMap[student.id] ?: emptyList())
                }
                
                _searchResults.value = allStudents
                
            } catch (e: Exception) {
                e.printStackTrace()
                println("Error loading students: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    fun search(query: String) {
        if (query.isEmpty()) {
            _searchResults.value = allStudents
            return
        }
        
        _searchResults.value = allStudents.filter { student ->
            student.id.contains(query, ignoreCase = true) ||
            student.name.contains(query, ignoreCase = true) ||
            student.phone.contains(query, ignoreCase = true) ||
            student.mobile.contains(query, ignoreCase = true)
        }
    }

    fun selectStudent(student: Student) {
        _selectedStudent.value = student
    }

    fun clearSelection() {
        _selectedStudent.value = null
    }
} 