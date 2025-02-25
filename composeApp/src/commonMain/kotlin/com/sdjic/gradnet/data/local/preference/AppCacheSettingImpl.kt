package com.sdjic.gradnet.data.local.preference

import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import com.sdjic.gradnet.domain.AppCacheSetting
import com.sdjic.gradnet.presentation.core.model.UserProfile
import com.sdjic.gradnet.presentation.screens.auth.register.model.UserRole

class AppCacheSettingImpl : AppCacheSetting {

    private val settings: Settings by lazy { Settings() }
    private val observableSettings: ObservableSettings by lazy { settings as ObservableSettings }

    override var accessToken: String
        get() = settings[SettingStorageKeys.ACCESS_TOKEN.key] ?: ""
        set(value) {
            settings[SettingStorageKeys.ACCESS_TOKEN.key] = value
        }

    override val isLoggedIn: Boolean
        get() = (settings[SettingStorageKeys.ACCESS_TOKEN.key] ?: "").isNotEmpty()

    override var userId: String
        get() = settings[SettingStorageKeys.USER_ID.key] ?: ""
        set(value) {
            settings[SettingStorageKeys.USER_ID.key] = value
        }

    override var isVerified: Boolean
        get() = settings[SettingStorageKeys.IS_VERIFIED.key] ?: false
        set(value) {
            settings[SettingStorageKeys.IS_VERIFIED.key] = value
        }

    // Store simple fields
    override fun saveUserProfile(userProfile: UserProfile) {
        settings[SettingStorageKeys.USER_ID.key] = userProfile.userId
        settings[SettingStorageKeys.VERIFIED_ID.key] = userProfile.verificationId
        settings[SettingStorageKeys.NAME.key] = userProfile.name
        settings[SettingStorageKeys.EMAIL.key] = userProfile.email
        settings[SettingStorageKeys.PHONE_NO.key] = userProfile.phoneNumber
        settings[SettingStorageKeys.PROFILE_PIC.key] = userProfile.profilePic
        settings[SettingStorageKeys.BACKGROUND_PIC.key] = userProfile.backgroundPic
        settings[SettingStorageKeys.DOB.key] = userProfile.dob
        settings[SettingStorageKeys.GENDER.key] = userProfile.gender
        settings[SettingStorageKeys.ADDRESS.key] = userProfile.address
        settings[SettingStorageKeys.WEBSITE.key] = userProfile.website
        settings[SettingStorageKeys.ABOUT_SELF.key] = userProfile.about
        settings[SettingStorageKeys.PLUS_MEMBER.key] = userProfile.isPlusMember
        settings[SettingStorageKeys.IS_VERIFIED.key] = userProfile.isVerified
        settings[SettingStorageKeys.IS_ACTIVE.key] = userProfile.isActive
        settings[SettingStorageKeys.CREATED_AT.key] = userProfile.createdAt
        settings[SettingStorageKeys.UPDATED_AT.key] = userProfile.updatedAt
        settings[SettingStorageKeys.INDUSTRY_TYPE.key] = userProfile.industryType
        settings[SettingStorageKeys.DEPARTMENT.key] = userProfile.department
        settings[SettingStorageKeys.DESIGNATION.key] = userProfile.designation
        settings[SettingStorageKeys.EMPLOYEE.key] = userProfile.employee
        settings[SettingStorageKeys.ROLE.key] = userProfile.userRole.name
    }

    // Retrieve simple fields
    override fun getUserProfile(): UserProfile {
        return UserProfile(
            userId = settings[SettingStorageKeys.USER_ID.key] ?: "",
            name = settings[SettingStorageKeys.NAME.key] ?: "",
            email = settings[SettingStorageKeys.EMAIL.key] ?: "",
            phoneNumber = settings[SettingStorageKeys.PHONE_NO.key] ?: "",
            profilePic = settings[SettingStorageKeys.PROFILE_PIC.key] ?: "",
            backgroundPic = settings[SettingStorageKeys.BACKGROUND_PIC.key] ?: "",
            dob = settings[SettingStorageKeys.DOB.key] ?: "",
            gender = settings[SettingStorageKeys.GENDER.key] ?: "",
            address = settings[SettingStorageKeys.ADDRESS.key] ?: "",
            isPlusMember = settings[SettingStorageKeys.PLUS_MEMBER.key] ?: false,
            isVerified = settings[SettingStorageKeys.IS_VERIFIED.key] ?: false,
            isActive = settings[SettingStorageKeys.IS_ACTIVE.key] ?: false,
            createdAt = settings[SettingStorageKeys.CREATED_AT.key] ?: "",
            updatedAt = settings[SettingStorageKeys.UPDATED_AT.key] ?: "",
            industryType = settings[SettingStorageKeys.INDUSTRY_TYPE.key] ?: "",
            department = settings[SettingStorageKeys.DEPARTMENT.key] ?: "",
            designation = settings[SettingStorageKeys.DESIGNATION.key] ?: "",
            employee = settings[SettingStorageKeys.EMPLOYEE.key] ?: "",
            userRole = UserRole.getUserRole(settings[SettingStorageKeys.ROLE.key] ?: UserRole.Alumni.name),
            website = settings[SettingStorageKeys.WEBSITE.key] ?: "",
            about = settings[SettingStorageKeys.ABOUT_SELF.key] ?: "",
            verificationId = settings[SettingStorageKeys.VERIFIED_ID.key] ?: "",
        )
    }


    override fun logout(callBack: () -> Unit) {
        settings.clear()
        callBack()
    }
}