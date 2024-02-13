//used to fix gradle warning, high level gradle can delete this row. and when build should delete
//@Suppress("DSL_SCOPE_VIOLATION")

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(buildLibs.plugins.android.application) apply false
    alias(buildLibs.plugins.kotlin.android) apply false
    alias(buildLibs.plugins.kotlin.serialization) apply false
    alias(buildLibs.plugins.google.hilt.android) apply false
}