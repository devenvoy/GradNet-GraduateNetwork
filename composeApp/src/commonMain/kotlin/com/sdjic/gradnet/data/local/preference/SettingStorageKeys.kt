package com.sdjic.gradnet.data.local.preference

enum class SettingStorageKeys {
    ACCESS_TOKEN,
    USER_ID,
    NAME,
    EMAIL,
    PHONE_NO,
    PROFILE_PIC,
    BACKGROUND_PIC,
    DOB,
    GENDER,
    ADDRESS,
    WEBSITE,
    ABOUT_SELF,
    INDUSTRY_TYPE,
    DEPARTMENT,
    DESIGNATION,
    EMPLOYEE,
    ROLE,
    PLUS_MEMBER,
    IS_VERIFIED,
    FIRST_INITIALIZED,
    VERIFIED_ID,
    IS_ACTIVE,
    LANGUAGES,
    SKILLS,
    CREATED_AT,
    UPDATED_AT;

    val key get() = this.name
}