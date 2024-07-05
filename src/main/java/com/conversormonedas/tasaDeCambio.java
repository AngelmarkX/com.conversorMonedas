/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.conversormonedas;

/**
 *
 * @author Miguel
 */


public class tasaDeCambio {
    private String monedaBase;
    private String monedaObjetivo;
    private double tipoCambio;

    public tasaDeCambio(String monedaBase, String monedaObjetivo, double tipoCambio) {
        this.monedaBase = monedaBase;
        this.monedaObjetivo = monedaObjetivo;
        this.tipoCambio = tipoCambio;
    }

    public String getMonedaBase() {
        return monedaBase;
    }

    public String getMonedaObjetivo() {
        return monedaObjetivo;
    }

    public double getTipoCambio() {
        return tipoCambio;
    }
}
