package com.example.alris.models.imageresponse

data class GenerateChat(
    val candidates: List<Candidate>,
    val modelVersion: String,
    val usageMetadata: UsageMetadata
)