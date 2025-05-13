package com.example.studentsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.material3.*
import com.example.studentsearch.data.Student
import com.example.studentsearch.data.StudentData
import com.example.studentsearch.data.StudentCsvLoader
import com.example.studentsearch.ui.StudentDetailScreen
import com.example.studentsearch.ui.StudentListScreen
import com.example.studentsearch.ui.theme.StudentSearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentSearchTheme {
                var selectedStudent by remember { mutableStateOf<Student?>(null) }
                val students = remember { StudentCsvLoader.loadStudents(this) }
                var showExitDialog by remember { mutableStateOf(false) }

                if (selectedStudent != null) {
                    // Handle system back to go back to list
                    BackHandler { selectedStudent = null }
                    StudentDetailScreen(
                        student = selectedStudent!!,
                        onBackClick = { selectedStudent = null }
                    )
                } else {
                    // Handle system back to show exit dialog
                    BackHandler { showExitDialog = true }
                    StudentListScreen(
                        students = students,
                        onStudentClick = { student -> selectedStudent = student }
                    )
                    if (showExitDialog) {
                        AlertDialog(
                            onDismissRequest = { showExitDialog = false },
                            title = { Text("Exit App") },
                            text = { Text("Are you sure you want to exit the app?") },
                            confirmButton = {
                                TextButton(onClick = { showExitDialog = false; finish() }) {
                                    Text("Yes")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showExitDialog = false }) {
                                    Text("No")
                                }
                            }
                        )
                    }
                }
            }
        }
    }
} 