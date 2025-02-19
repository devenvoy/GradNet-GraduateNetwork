package com.sdjic.posts

import com.sdjic.domain.model.response.UserDto

val DummyBgImage =
    "https://images.wallpaperscraft.com/image/single/starry_sky_milky_way_stars_118495_1600x900.jpg"
val DummyDpImage =
    "https://images.unsplash.com/photo-1639149888905-fb39731f2e6c?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
val LOREM =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."


fun getEmptyUserDto() = UserDto(
    userId = "1",
    email = "test@gmail.com",
    isVerified = true,
    userType = "alumni",
    username = "Devansh amdavadwala",
    createdAt = "",
    updatedAt = "",
)
