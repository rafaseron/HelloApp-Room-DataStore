package br.com.alura.helloapp.localData.hashConverter

import java.security.MessageDigest

class HashConverter {
    fun String.convertSToSHA256(): String{
        return MessageDigest
            .getInstance("SHA-256")
            .digest(this.toByteArray())
            .fold("") { str, it -> str + "%02x".format(it) }
    }

}