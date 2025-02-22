package com.sdjic.gradnet.presentation.core.model

data class Job(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val salary: String?, // Nullable if salary is not mentioned
    val jobType: JobType, // Sealed class for job types
    val description: String,
    val requirements: List<String>, // List of job requirements
    val benefits: List<String>, // List of perks/benefits
    val postedDate: String,
    val applyLink: String, // URL to apply
    val companyLogo: String?, // URL of company logo
    val experienceRequired: String, // Experience needed (e.g., 2+ years)
    val skills: List<String> // Required skills
)


/*
Aspect	Skills	Requirements
Definition	Abilities or expertise a candidate should possess to perform the job effectively.	Conditions or qualifications a candidate must meet to be eligible for the job.
Examples	- Kotlin, Android Development, Jetpack Compose, REST API	- Bachelor’s Degree in Computer Science, 3+ years of experience in Android Development
 */
