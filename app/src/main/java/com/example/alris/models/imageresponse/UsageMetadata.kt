package com.example.alris.models.imageresponse

data class UsageMetadata(
    val candidatesTokenCount: Int,
    val candidatesTokensDetails: List<CandidatesTokensDetail>,
    val promptTokenCount: Int,
    val promptTokensDetails: List<PromptTokensDetail>,
    val totalTokenCount: Int
)