package com.sdjic.gradnet.presentation.screens.auth.register.model

import gradnet_graduatenetwork.composeapp.generated.resources.Res
import gradnet_graduatenetwork.composeapp.generated.resources.ic_alumni1
import gradnet_graduatenetwork.composeapp.generated.resources.ic_faculty1
import gradnet_graduatenetwork.composeapp.generated.resources.ic_organization
import org.jetbrains.compose.resources.DrawableResource

data class UserRole(
    val id: Int,
    val name: String,
    val icon: DrawableResource
)

fun getUserRoles(): List<UserRole> = listOf(
    UserRole(id = 1, name = "Alumni", icon = Res.drawable.ic_alumni1),
    UserRole(id = 2, name = "Faculty", icon = Res.drawable.ic_faculty1),
    UserRole(id = 3, name = "Organization", icon = Res.drawable.ic_organization),
)