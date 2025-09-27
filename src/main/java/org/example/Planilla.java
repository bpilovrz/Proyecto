package org.example;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Planilla")
public class Planilla implements PagoAtleta {

    private static final double SUELDO_BASE = 3500;
    private static final double BONO_ENTRENAMIENTO = 100;
    private static final double BONO_MEJOR_MARCA = 250;
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

        // 🔹 Mostrar desglose automáticamente
        System.out.println(generarDesglose(a));

        return total;
    }

    @Override
    public double calcularPagoMensual(Atleta aPago) {
        return calcularPagoTotal(aPago); // Ya incluye el desglose
    }

    public double getUltimoPago() {
        return ultimoPago;
    }

    public void setUltimoPago(double ultimoPago) {
        this.ultimoPago = ultimoPago;
    }

    public String getDescripcion() {
        return "Planilla estándar con sueldo base y bonificaciones (BASE: 3500)";
    }

    // 🔹 Método que crea el desglose detallado
    private String generarDesglose(Atleta a) {
        int totalSesiones = a.getTotalSesiones();
        double sueldoBase = calcularSueldoBase();
        double bonos = calcularBonos(a);

        StringBuilder desglose = new StringBuilder();
        desglose.append("---- Desglose de Pago ----\n");
        desglose.append("Sueldo Base: Q.").append(sueldoBase).append("\n");

        desglose.append("Bonos:\n");
        if (totalSesiones >= 8) {
            desglose.append(" - Bono entrenamiento: Q.").append(BONO_ENTRENAMIENTO).append("\n");
        } else {
            desglose.append(" - Bono entrenamiento (por sesión): Q.")
                    .append(totalSesiones * BONO_ENTRENAMIENTO).append("\n");
        }

        if (totalSesiones > 0) {
            double mejorHist = Estadistica.mejorMarca(a);
            SesionEntrenamiento[] sesiones = a.getSesiones();
            double ultimaMarca = sesiones[totalSesiones - 1].getEntrenamiento().getMarca();
            if (ultimaMarca > mejorHist) {
                desglose.append(" - Bono por mejor marca: Q.").append(BONO_MEJOR_MARCA).append("\n");
            }
        }

        if (totalSesiones > 8) {
            desglose.append(" - Bonos extra por ").append(totalSesiones - 8)
                    .append(" sesiones: Q.").append((totalSesiones - 8) * BONO_EXTRA).append("\n");
        }

        desglose.append("--------------------------\n");
        desglose.append("TOTAL A PAGAR: Q.").append(sueldoBase + bonos).append("\n");

        return desglose.toString();
    }
}





