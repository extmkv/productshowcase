package com.example.adidas.ui.home

sealed class HomeUiState

object LoadingState : HomeUiState()
object ContentState : HomeUiState()
object ContentNextPageState : HomeUiState()
class ErrorState(val message: String) : HomeUiState()
