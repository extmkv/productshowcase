package com.example.adidas.ui.details

sealed class DetailsUiState

object LoadingState : DetailsUiState()
object ContentState : DetailsUiState()
class SubmissionState(val message: String) : DetailsUiState()
class ErrorState(val message: String) : DetailsUiState()
