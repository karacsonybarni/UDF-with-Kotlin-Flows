package com.example.beerapp.ui.model

data class Beer(val id: Int, val name: String, val likeAction: () -> Unit)