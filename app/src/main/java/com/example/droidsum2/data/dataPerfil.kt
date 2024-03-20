package com.example.droidsum2.data

import org.simpleframework.xml.Element
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root

@Root(name = "Envelope", strict = false)
@Namespace(reference = "http://schemas.xmlsoap.org/soap/envelope/")
data class PerfilRequest(
    @field:Element(name = "Body")
    var body: GetAlumnoAcademicoWithLineamiento? = null
)

data class PerfilData(
    val estatus: String,
    val tipoUsuario: Int,
    val contrasenia: String,
    val matricula: String
)

@Root(name = "getAlumnoAcademicoWithLineamiento")
data class GetAlumnoAcademicoWithLineamiento(
    @field:Element(name = "placeholder") var placeholder: String = ""
)

data class PerfilResponse(
    @field:Element(name = "getAlumnoAcademicoWithLineamientoResult")
    var resultado: String
)

