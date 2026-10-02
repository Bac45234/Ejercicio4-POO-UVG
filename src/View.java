
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author pepsi
 */
public class View {

    private Scanner scan = new Scanner(System.in);

    public View() {
        System.out.println("   ,ggggggg,                 ,ggggggg,                                                       \n"
                + " ,dP\"\"\"\"\"\"Y8b              ,dP\"\"\"\"\"\"Y8b                                                      \n"
                + " d8'    a  Y8              d8'    a  Y8                                                      \n"
                + " 88     \"Y8P'              88     \"Y8P'                                                      \n"
                + " `8baaaa                   `8baaaa                                                           \n"
                + ",d8P\"\"\"\"      ,ggg,,ggg,  ,d8P\"\"\"\"       ,g,       ,gggg,   ,ggg,    ,ggg,,ggg,     ,gggg,gg \n"
                + "d8\"          ,8\" \"8P\" \"8, d8\"           ,8'8,     dP\"  \"Yb i8\" \"8i  ,8\" \"8P\" \"8,   dP\"  \"Y8I \n"
                + "Y8,          I8   8I   8I Y8,          ,8'  Yb   i8'       I8, ,8I  I8   8I   8I  i8'    ,8I \n"
                + "`Yba,,_____,,dP   8I   Yb,`Yba,,_____,,8'_   8) ,d8,_    _ `YbadP' ,dP   8I   Yb,,d8,   ,d8b,\n"
                + "  `\"Y88888888P'   8I   `Y8  `\"Y8888888P' \"YY8P8PP\"\"Y8888PP888P\"Y8888P'   8I   `Y8P\"Y8888P\"`Y8\n"
                + "                                                                                             \n");
    }

    public int printMenu() {
        System.out.print(
                "Que accion desea realizar?\n"
                + "(1) Registrar equipo\n"
                + "(2) Consultar inventario\n"
                + "(3) Cotizar\n"
                + "(4) Confirmar alquiler\n"
                + "(5) Registrar devolucion\n"
                + "(6) Imprimir reporte\n"
                + "(7) Salir\n"
                + ">> "
        );
        return getIntInput();
    }

    public Object[] getDefaultDeviceData() {
        System.out.print("\nIngrese la categoria del dispositivo: ");
        String categoria = scan.next();
        System.out.print("Codigo de inventario: ");
        String codigo = scan.next();
        System.out.print("Marca: ");
        String marca = scan.next();
        System.out.print("Modelo: ");
        String modelo = scan.next();
        System.out.print("Tarifa diaria: ");
        int tarifa = getIntInput();
        return new Object[]{categoria, codigo, marca, modelo, tarifa};
    }

    public Object[] getProyectorSpecialData() {
        System.out.print("Luminosidad: ");
        int luminosidad = getIntInput();
        System.out.print("Tiene conectividad inalambrica? (Si/No): ");
        String inalambrico = scan.next();

        return new Object[]{luminosidad, inalambrico};
    }

    public Object[] getCamaraSpecialData() {
        System.out.print("Resolusion: ");
        int resolucion = getIntInput();
        return new Object[]{resolucion};
    }

    public Object[] getSonidoSpecialData() {
        System.out.print("Potencia nominal: ");
        double nominal = getDoubleInput();
        return new Object[]{nominal};
    }
    
    public String getSingleText(String tipo) {
        System.out.print("\nIngrese " + tipo + ": ");
        return scan.next();
    }

    public int getSingleInteger(String tipo) {
        System.out.print("Ingrese el numero de " + tipo + ": ");
        return getIntInput();
    }

    public boolean getRentalConfirmation() {
        System.out.print(
                "\nConfirme la renta del equipo (confirm/cancel):"
                + "\n>> "
        );
        return (scan.next().toLowerCase().equals("confirm"));
    }

    
    public void printInventory(Object[] InfoTotal) {
        System.out.println("\n========================\n");

        for (Object info : InfoTotal) {
            Object[] datos = (Object[]) info;
            System.out.println(
                    "Categoria: " + datos[1]
                    + "\nCodigo: " + datos[0]
                    + "\n\tMarca: " + datos[2]
                    + "\n\tModelo: " + datos[3]
                    + "\n\tTarifa diaria: Q" + datos[4]
                    + "\n\tRecargo: Q" + datos[5]
                    + "\n\tDisponibilidad: " + datos[6]
            );

            switch ((String) datos[1]) {
                case "Proyector" ->
                    System.out.println(
                            "\tLuminosidad: " + datos[7] + " lumen"
                            + "\n\tConextividad inalambrica: " + datos[8]
                    );
                case "Camara" ->
                    System.out.println("\tResolusion: " + datos[7] + " px");
                case "Sonido" ->
                    System.out.println("\tPotencia nominal: " + datos[7] + " kv");
            }
        }

        System.out.println("\n========================\n");

    }

    public void printCotizacion(Object[] datos) {
        System.out.println("\n========================\n");

        System.out.println(
                "Codigo: " + datos[0]
                + "\nCategoria: " + datos[1]
                + "\n\tTarifa diaria: Q" + datos[2]
                + "\n\tRecargo: Q" + datos[3]
                + "\n\tDisponibilidad: " + datos[4]
        );

        switch ((String) datos[1]) {
            case "Proyector" ->
                System.out.println(
                        "\tLuminosidad: " + datos[7] + " lumen"
                        + "\n\tConextividad inalambrica: " + datos[8]
                );
            case "Camara" ->
                System.out.println("\tResolusion: " + datos[7] + " px");
            case "Sonido" ->
                System.out.println("\tPotencia nominal: " + datos[7] + " kv");
        }

        System.out.println(
                "\nDias de alquiler: " + datos[5]
                + "\n> Cotizacion: Q" + datos[6]
        );

        System.out.println("\n========================\n");
    }

    public void printReporte(Object[] datos, double ganancias) {
        System.out.println("\n========================\n");

        System.out.println("Equipos registrados: " + datos.length);

        int disponibles = 0;
        int proyectores = 0;
        int camaras = 0;
        int sonidos = 0;
        
        for (Object dato : datos) {
            Object[] equipo = (Object[]) dato;
            
            if((boolean) equipo[1]){
               disponibles += 1;
               continue;
            }
            
            switch (equipo[0].toString().toLowerCase()) {
                case "proyector" -> proyectores += 1;
                case "camara" -> camaras += 1;
                case "sonido" -> sonidos += 1;
            }
        }
        System.out.println(
                "Equipos disponibles actualmente: "+ disponibles
                +"\nProyectores alquilados actualmente: "+ proyectores
                + "\nCamaras alquiladas actualmente: "+ camaras
                + "\nSonidos alquilados actualmente: "+ sonidos
        );
        
        System.out.println("Ganancias actuales: " + ganancias);

        System.out.println("\n========================\n");
    }

    public void printSuccessMessage(String msg) {
        System.out.println(
                "\n=============================\n"
                + msg
                + "\n=============================\n"
        );
    }

    public void printErrorMessage(String msg) {
        System.out.println(
                "\n**************************\n"
                + msg
                + "\n**************************\n"
        );
    }
    

    private int getIntInput() {
        try {
            return scan.nextInt();

        } catch (InputMismatchException ex) {
            System.out.println(
                    "********************\n"
                    + "El tipo de dato ingresado no es valido\n"
                    + "********************"
            );
            scan.next();
            return -1;

        } catch (Exception e) {
            System.out.println(
                    "********************\n"
                    + "Lo sentimos, ha ocurrido un error inesperado\n"
                    + "********************"
            );
            scan.next();
            return -1;
        }
    }

    private double getDoubleInput() {
        try {
            return scan.nextDouble();

        } catch (InputMismatchException ex) {
            System.out.println(
                    "********************\n"
                    + "El tipo de dato ingresado no es valido\n"
                    + "********************"
            );
            scan.next();
            return -1;

        } catch (Exception e) {
            System.out.println(
                    "********************\n"
                    + "Lo sentimos, ha ocurrido un error inesperado\n"
                    + "********************"
            );
            scan.next();
            return -1;
        }
    }

}
