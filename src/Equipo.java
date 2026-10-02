/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author pepsi
 */
public class Equipo {
    private String codigo;
    private String categoria;
    private String marca;
    private String modelo;
    private int tarifa;
    private int recargo;
    private boolean disponibilidad;

    public Equipo(String codigo, String categoria, String marca, String modelo, int tarifa, int recargo) {
        codeVerify(codigo);
        verifyParameters(tarifa, "La tarifa");
        
        this.codigo = codigo;
        this.categoria = categoria;
        this.marca = marca;
        this.modelo = modelo;
        this.tarifa = tarifa;
        this.recargo = recargo;
        this.disponibilidad = true;
    }

    public String getCodigo() { return codigo; }
    public String getCategoria(){ return categoria; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public int getTarifa() { return tarifa; }
    public int getRecargo() { return recargo; }
    public boolean getDisponibilidad() { return disponibilidad; }
    
    public void setDisponibilidad(boolean estado){
        if ((disponibilidad & estado) | (!disponibilidad & !estado)){
            throw new IllegalArgumentException("Actualizacion de disponibilidad invalida");
        }
        
        this.disponibilidad = estado;
    }
    
    private void codeVerify(String codigo){
        if (codigo.equals("")) {
            throw new IllegalArgumentException("El codigo no puede estar vacio");
        }
    }
    
    protected void verifyParameters(double argument, String atributeName){
        if (argument <= 0) {
            throw new IllegalArgumentException(atributeName+ "no puede ser menor o igual a 0");
        }
    }
}
