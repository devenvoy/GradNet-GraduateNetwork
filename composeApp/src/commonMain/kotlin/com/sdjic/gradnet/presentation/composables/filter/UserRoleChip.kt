package com.sdjic.gradnet.presentation.composables.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole

@Composable
fun UserRoleChip(
    modifier: Modifier = Modifier,
    userRole: UserRole
) {
    Box(
        modifier = modifier
            .background(getRoleColor(userRole), shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 12.dp, vertical = 2.dp)
    ) {
        Text(
            text = userRole.name.lowercase().replaceFirstChar { it.uppercaseChar() },
            color = Color.White,
            fontSize = 10.sp
        )
    }
}

// Function to get the color for each role
fun getRoleColor(userRole: UserRole): Color {
    return when (userRole) {
        is UserRole.Alumni -> Color(0xFF4CAF50) // Green
        is UserRole.Faculty -> Color(0xFF2196F3) // Blue
        is UserRole.Organization -> Color(0xFFFFC107) // Yellow
        is UserRole.Admin -> Color(0xFFF44336) // Red
    }
}
