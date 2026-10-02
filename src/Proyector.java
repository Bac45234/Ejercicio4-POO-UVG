/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pepsi
 */
public class Proyector extends Equipo{
    private int luminosidad;
    private boolean inalambrico;

    public Proyector(String codigo, String marca, String modelo, int tarifa, int recargo, int luminosidad, boolean inalambrico) {
        super(codigo, "Proyector", marca, modelo, tarifa, recargo);
        verifyParameters(luminosidad, "La luminosidad");
        
        this.luminosidad = luminosidad;
        this.inalambrico = inalambrico;
    }
    
    public int getLuminosidad() { return luminosidad; }
    public boolean getInalambrico() { return inalambrico; }
}
