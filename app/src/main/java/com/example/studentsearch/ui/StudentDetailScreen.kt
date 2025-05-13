package com.example.studentsearch.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
fun StudentDetailScreen(
    student: Student,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Student Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Personal Information", style = MaterialTheme.typography.titleMedium)
            Card(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(Modifier.padding(8.dp)) {
                    InfoRow("Name:", student.name)
                    InfoRow("ID:", student.id)
                    InfoRow("Phone:", student.phone)
                    InfoRow("Mobile:", student.mobile)
                    InfoRow("Email:", student.email)
                    InfoRow("Address:", student.address)
                    InfoRow("National ID:", student.nationalId)
                }
            }
            Text("Academic Information", style = MaterialTheme.typography.titleMedium)
            Card(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                Column(Modifier.padding(8.dp)) {
                    InfoRow("Program:", student.currentMajor)
                    InfoRow("Level:", student.currentLevel)
                    InfoRow("GPA:", student.currentGpa)
                    InfoRow("Total Courses:", student.currentHours)
                }
            }
            Text("Course History", style = MaterialTheme.typography.titleMedium)
            student.courseHistory.forEach { course ->
                Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    Column(Modifier.padding(8.dp)) {
                        Text(course.name, fontWeight = FontWeight.Bold)
                        InfoRow("Year:", course.year)
                        InfoRow("Grade:", course.grade)
                        // Optionally show mark if you want
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            modifier = Modifier.weight(2f)
        )
    }
} 