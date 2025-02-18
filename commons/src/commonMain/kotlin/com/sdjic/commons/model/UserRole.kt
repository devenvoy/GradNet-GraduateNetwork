package com.sdjic.commons.model

import com.sdjic.shared.Resource as Res
import org.jetbrains.compose.resources.DrawableResource

sealed class UserRole(
    val id: Int,
    val name: String,
    val icon: DrawableResource
) {
    data object Alumni : UserRole(id = 1, name = "ALUMNI", icon = Res.drawable.icAlumni)
    data object Faculty : UserRole(id = 2, name = "FACULTY", icon = Res.drawable.icFaculty)
    data object Organization :
        UserRole(id = 3, name = "ORGANIZATION", icon = Res.drawable.icOrganization)

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