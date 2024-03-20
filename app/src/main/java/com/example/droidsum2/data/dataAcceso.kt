package com.example.droidsum2.data

import org.simpleframework.xml.Element

data class AccessRequest(
    @field:Element(name = "strMatricula")
    var matricula: String,
    @field:Element(name = "strContrasenia")
    var contrasenia: String,
    @field:Element(name = "tipoUsuario")
    var tipoUsuario: String
)

data class AccessResponse(
    @field:Element(name = "accesoLoginResult")
    var resultado: String
)
