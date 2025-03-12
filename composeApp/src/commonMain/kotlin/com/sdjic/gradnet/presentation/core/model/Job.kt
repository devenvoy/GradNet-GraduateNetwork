package com.sdjic.gradnet.presentation.core.model

import com.maxkeppeker.sheets.core.utils.JvmSerializable

data class Job(
    val id: String,    // Unique identifier for the job
    val title: String,  //
    val company: String, //
    val jobType: String?, // Sealed class for job types
    val location: String,  //
    val description: String,
    val salary: String?, // Nullable if salary is not mentioned
    val requirements: List<String>, // List of job requirements
    val benefits: List<String>, // List of perks/benefits
    val postedDate: String,
    val applyLink: String, // URL to apply
    val companyLogo: String?, // URL of company logo
    val category: String, // industry
    val skills: List<String> // Required skills
) : JvmSerializable


/*
Aspect	Skills	Requirements
Definition	Abilities or expertise a candidate should possess to perform the job effectively.	Conditions or qualifications a candidate must meet to be eligible for the job.
Examples	- Kotlin, Android Development, Jetpack Compose, REST API	- Bachelor’s Degree in Computer Science, 3+ years of experience in Android Development
 */
