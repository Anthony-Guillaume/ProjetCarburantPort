package com.example.bateaubreton.data.interactor

enum class Error { A, B }

class SignIn : UseCase<Int, Int, Error>()
{
    override fun execute(request: Int, onResult: Result<Int, Error>)
    {
        TODO("Not yet implemented")
    }

}