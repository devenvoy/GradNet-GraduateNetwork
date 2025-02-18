package com.sdjic.gradnet.presentation.screens.auth.register.model

import com.sdjic.shared.resources.Res
import com.sdjic.shared.resources.ic_alumni
import com.sdjic.shared.resources.ic_faculty
import com.sdjic.shared.resources.ic_organization
import org.jetbrains.compose.resources.DrawableResource

sealed class UserRole(
    val id: Int,
    val name: String,
    val icon: DrawableResource
) {
    data object Alumni : UserRole(id = 1, name = "ALUMNI", icon = Res.drawable.ic_alumni)
    data object Faculty : UserRole(id = 2, name = "FACULTY", icon = Res.drawable.ic_faculty)
    data object Organization :
        UserRole(id = 3, name = "ORGANIZATION", icon = Res.drawable.ic_organization)

    companion object{
        fun getUserRole(name: String): UserRole {
            return when (name) {
                "ALUMNI" -> Alumni
                "FACULTY" -> Faculty
                "ORGANIZATION" -> Organization
                else -> throw IllegalArgumentException("Invalid user role: $name")
            }
        }
    }
}

fun getUserRoles(): List<UserRole> = listOf(
    UserRole.Alumni, UserRole.Faculty, UserRole.Organization
)