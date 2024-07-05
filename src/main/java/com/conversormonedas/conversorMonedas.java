/**
 *
 * @author Miguel
 */
package com.conversormonedas;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class conversorMonedas {
    private static final String CLAVE_API = "410f283074e7382f43a18d7d"; 

    public tasaDeCambio obtenerTipoCambio(String monedaBase, String monedaObjetivo) throws IOException {
        URL url = new URL("https://v6.exchangerate-api.com/v6/" + CLAVE_API + "/latest/" + monedaBase);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setRequestMethod("GET");

        int codigoRespuesta = conexion.getResponseCode();
        if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
            try (BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()))) {
                StringBuilder respuesta = new StringBuilder();
                String linea;
                while ((linea = lector.readLine()) != null) {
                    respuesta.append(linea);
                }

                // Imprime la respuesta del JSON 
                System.out.println("Respuesta JSON: " + respuesta.toString());

                Gson gson = new Gson();
                respuestaTasaDeCambios respuestaTasaDeCambios = gson.fromJson(respuesta.toString(), respuestaTasaDeCambios.class);

                if (respuestaTasaDeCambios.getResultado() == null || !respuestaTasaDeCambios.getResultado().equals("success")) {
                    throw new IOException("Código de moneda inválido o respuesta de la API");
                }

                Map<String, Double> tasasDeConversion = respuestaTasaDeCambios.getTasasDeConversion();
                Double tasa = tasasDeConversion.get(monedaObjetivo.toUpperCase());
                if (tasa == null) {
                    System.err.println("Moneda no encontrada en las tasas de conversión: " + monedaObjetivo);
                    throw new IOException("Moneda no encontrada: " + monedaObjetivo);
                }

                return new tasaDeCambio(monedaBase, monedaObjetivo, tasa);
            }
        } else {
            throw new IOException("Error al obtener el tipo de cambio - Código de respuesta: " + codigoRespuesta);
        }
    }

    public double convertir(double cantidad, String monedaBase, String monedaObjetivo) throws IOException {
        tasaDeCambio tipoCambio = obtenerTipoCambio(monedaBase, monedaObjetivo);
        return cantidad * tipoCambio.getTipoCambio();
    }

    public static void main(String[] args) {
        conversorMonedas conversor = new conversorMonedas();
        try (Scanner escaner = new Scanner(System.in)) {
            System.out.print("Ingrese la moneda base (ej. USD): ");
            String monedaBase = escaner.nextLine().toUpperCase();

            System.out.print("Ingrese la moneda objetivo (ej. EUR): ");
            String monedaObjetivo = escaner.nextLine().toUpperCase();

            System.out.print("Ingrese la cantidad a convertir: ");
            double cantidad = escaner.nextDouble();

            double cantidadConvertida = conversor.convertir(cantidad, monedaBase, monedaObjetivo);
            System.out.println(cantidad + " " + monedaBase + " es igual a " + cantidadConvertida + " " + monedaObjetivo);
        } catch (IOException e) {
            System.err.println("Error al obtener el tipo de cambio: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error: Ingrese una cantidad válida.");
        }
    }
}
