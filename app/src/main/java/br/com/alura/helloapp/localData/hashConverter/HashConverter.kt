package br.com.alura.helloapp.localData.hashConverter

import java.security.MessageDigest

class HashConverter {
    companion object{
        fun convertStringToSHA256(string: String): String{
            return MessageDigest
                .getInstance("SHA-256")
                .digest(string.toByteArray())
                .fold("") { str, it -> str + "%02x".format(it) }
        }
    }

}