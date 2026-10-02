/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pepsi
 */
public class Sonido extends Equipo{
    private double nominal;
    
    public Sonido(String codigo, String marca, String modelo, int tarifa, double nominal) {
        super(codigo, "Sonido", marca, modelo, tarifa, 100);
        verifyParameters(nominal, "La potencia nominal");
        
        this.nominal = nominal;
    }

    public double getNominal(){ return nominal; }
}
