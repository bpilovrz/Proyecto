package org.example;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipoPago"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Planilla.class, name = "Planilla")
})
public interface PagoAtleta {
    double calcularSueldoBase();

    double calcularBonos(Atleta a);

    double calcularPagoTotal(Atleta a);

    double calcularPagoMensual(Atleta aPago);
}


