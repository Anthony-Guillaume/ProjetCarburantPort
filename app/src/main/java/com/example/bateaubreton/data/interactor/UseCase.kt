package com.example.bateaubreton.data.interactor

abstract class UseCase<REQ, RESP, ERROR>
{
    interface Result<RESP, ERROR>
    {
        fun onSuccess(response: RESP)
        fun onError(error: ERROR)
    }

    abstract fun execute(request: REQ, onResult: Result<RESP, ERROR>)
}