package org.example;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "tipo"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Tiempo.class, name = "Tiempo"),
        @JsonSubTypes.Type(value = Peso.class, name = "Peso"),
        @JsonSubTypes.Type(value = Puntuacion.class, name = "Puntuacion"),
        @JsonSubTypes.Type(value = Distancia.class, name = "Distancia")
})
public interface Entrenamiento {
    double getMarca();
    String getDescripcion();
}
