package com.example.studentsearch.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentsearch.data.Student

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(
    students: List<Student>,
    onStudentClick: (Student) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var courseFilter by remember { mutableStateOf("") }
    var yearFilter by remember { mutableStateOf("") }

    val filteredStudents = students.filter { student ->
        val matchesSearch =
            student.name.contains(searchQuery, ignoreCase = true) ||
            student.id.contains(searchQuery, ignoreCase = true) ||
            student.address.contains(searchQuery, ignoreCase = true) ||
            student.phone.contains(searchQuery, ignoreCase = true) ||
            student.mobile.contains(searchQuery, ignoreCase = true)

        val matchesCourse =
            courseFilter.isBlank() || yearFilter.isBlank() ||
            student.courseHistory.any { course ->
                course.name.contains(courseFilter, ignoreCase = true) &&
                course.year.contains(yearFilter, ignoreCase = true)
            }

        matchesSearch && matchesCourse
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student Search") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by name, ID, address, phone, or mobile") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = courseFilter,
                    onValueChange = { courseFilter = it },
                    label = { Text("Course Name") },
                    modifier = Modifier.weight(1f).padding(end = 8.dp)
                )
                OutlinedTextField(
                    value = yearFilter,
                    onValueChange = { yearFilter = it },
                    label = { Text("Year") },
                    modifier = Modifier.weight(1f)
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredStudents) { student ->
                    StudentListItem(
                        student = student,
                        onClick = { onStudentClick(student) }
                    )
                }
            }
        }
    }
}

@Composable
fun StudentListItem(
    student: Student,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = student.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "ID: ${student.id}",
                fontSize = 14.sp
            )
            Text(
                text = "Major: ${student.currentMajor}",
                fontSize = 14.sp
            )
            Text(
                text = "GPA: ${student.currentGpa}",
                fontSize = 14.sp
            )
        }
    }
} 