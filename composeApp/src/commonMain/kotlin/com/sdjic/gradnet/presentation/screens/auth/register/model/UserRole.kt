package com.sdjic.gradnet.presentation.screens.auth.register.model

import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_alumni1
import gradnet_graduatenetwork.composeapp.generated.resources.ic_faculty1
import gradnet_graduatenetwork.composeapp.generated.resources.ic_organization
import org.jetbrains.compose.resources.DrawableResource

sealed class UserRole(
    val id: Int,
    val name: String,
    val icon: DrawableResource
) {
    data object Alumni : UserRole(id = 1, name = "Alumni", icon = Res.drawable.ic_alumni1)
    data object Faculty : UserRole(id = 2, name = "Faculty", icon = Res.drawable.ic_faculty1)
    data object Organization : UserRole(id = 3, name = "Organization", icon = Res.drawable.ic_organization)
}

fun getUserRoles(): List<UserRole> = listOf(
    UserRole.Alumni,UserRole.Faculty,UserRole.Organization
)