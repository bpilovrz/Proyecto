package org.example;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Planilla")
public class Planilla implements PagoAtleta {

    private static final double SUELDO_BASE = 3500;
    private static final double BONO_ENTRENAMIENTO = 500;
    private static final double BONO_MEJOR_MARCA = 100;
    private static final double BONO_EXTRA = 100;

    private double ultimoPago;

    public Planilla() {}

    @Override
    public double calcularSueldoBase() {
        return SUELDO_BASE;
    }

    @Override
    public double calcularBonos(Atleta a) {
        int totalSesiones = a.getTotalSesiones();
        double bonos = 0;

        if (totalSesiones >= 8) {
            bonos += BONO_ENTRENAMIENTO;
        } else {
            bonos += totalSesiones * BONO_ENTRENAMIENTO;
        }

        if (totalSesiones > 0) {
            double mejorHist = Estadistica.mejorMarca(a);
            SesionEntrenamiento[] sesiones = a.getSesiones();
            double ultimaMarca = sesiones[totalSesiones - 1].getEntrenamiento().getMarca();
            if (ultimaMarca > mejorHist) {
                bonos += BONO_MEJOR_MARCA;
            }
        }

        if (totalSesiones > 8) {
            bonos += (totalSesiones - 8) * BONO_EXTRA;
        }

        return bonos;
    }

    @Override
    public double calcularPagoTotal(Atleta a) {
        double total = calcularSueldoBase() + calcularBonos(a);
        this.ultimoPago = total;
        return total;
    }

    @Override
    public double calcularPagoMensual(Atleta aPago) {
        // Redirige al método calcularPagoTotal
        return calcularPagoTotal(aPago);
    }

    public double getUltimoPago() {
        return ultimoPago;
    }

    public void setUltimoPago(double ultimoPago) {
        this.ultimoPago = ultimoPago;
    }

    public String getDescripcion() {
        return "Planilla estándar con sueldo base y bonificaciones";
    }
}



