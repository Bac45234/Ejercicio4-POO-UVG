/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pepsi
 */
public class Camara extends Equipo{
    private int resolusion;
    
    public Camara(String codigo, String marca, String modelo, int tarifa, int recargo, int resolucion) {
        super(codigo, "Camara", marca, modelo, tarifa, recargo);
        verifyParameters(resolucion, "La resolucion");
        
        this.resolusion = resolucion;
    }

    public int getResolusion(){ return resolusion; }
}
