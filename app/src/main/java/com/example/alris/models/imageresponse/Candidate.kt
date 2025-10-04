package com.example.alris.models.imageresponse

data class Candidate(
    val avgLogprobs: Double,
    val citationMetadata: CitationMetadata,
    val content: Content,
    val finishReason: String
)