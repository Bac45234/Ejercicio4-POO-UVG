
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author pepsi
 */
public class Controller {

    private View view;
    private Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        menuExecute();
    }

    private void menuExecute() {
        while (true) {
            int act = view.printMenu();
            actionExecute(act);
        }
    }

    private void actionExecute(int act) {
        switch (act) {
            case 1 ->
                addDevice();
            case 2 ->
                showInventory();
            case 3 ->
                mostrarCotizacionEquipo();
            case 4 ->
                alquilar();
            case 5 ->
                devolucion();
            case 6 -> reporte();
            case 7 -> {
                view.printSuccessMessage("Cerrando programa...");
                System.exit(0);
            }
            default ->
                view.printErrorMessage("No fue posible realizar la accion seleccionada");
        }
    }

    private void addDevice() {
        Object[] defaultData = view.getDefaultDeviceData();

        String codigo = (String) defaultData[1];
        String marca = (String) defaultData[2];
        String modelo = (String) defaultData[3];
        int tarifa = (int) defaultData[4];

        Equipo equipo = null;

        try {
            switch (defaultData[0].toString().toLowerCase()) {
                case "proyector" -> {
                    Object[] specialData = view.getProyectorSpecialData();

                    int luminosidad = (int) specialData[0];
                    boolean inalambrico = specialData[1].toString().toLowerCase().equals("si");
                    int recargo = (inalambrico) ? 50 : 0;

                    equipo = new Proyector(codigo, marca, modelo, tarifa, recargo, luminosidad, inalambrico);
                }
                case "camara" -> {
                    Object[] specialData = view.getCamaraSpecialData();

                    int resolusion = (int) specialData[0];
                    int recargo = (resolusion > 1080) ? 75 : 0;

                    equipo = new Camara(codigo, marca, modelo, tarifa, recargo, resolusion);
                }
                case "sonido" -> {
                    Object[] specialData = view.getSonidoSpecialData();

                    double nominal = (double) specialData[0];

                    equipo = new Sonido(codigo, marca, modelo, tarifa, nominal);
                }
            }

            if (equipo == null) {
                throw new IllegalArgumentException("Categoria de dispositivo no reconosida");
            }

            model.addDevice(equipo);
            view.printSuccessMessage("El equipo se ha registrado correctamente");

//            System.getLogger(Controller.class.getName()).log(System.Logger.Level.ERROR, (String) null, e);
        } catch (IllegalArgumentException ex) {
            view.printErrorMessage(ex.getMessage());
        } catch (Exception e) {
            view.printErrorMessage("Lo sentimos, ha ocurrido un error inesperado");
        }
    }

    private void showInventory() {
        try {
            Object[] datosTotales = model.getAllDevices();
            for (Object datos : datosTotales) {
                Object[] datosSingulares = (Object[]) datos;
                for (int i = 0; i < datosSingulares.length; i++) {
                    if (datosSingulares[i].getClass() == Boolean.class) {
                        datosSingulares[i] = ((boolean) datosSingulares[i]) ? "Si" : "No";
                    }
                }
            }

            view.printInventory(datosTotales);

//            System.getLogger(Controller.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (NoSuchMethodException ex) {
            view.printErrorMessage("Lo sentimos, ha ocurrido un error inesperado");
        } catch (IllegalAccessException ex) {
            view.printErrorMessage("Lo sentimos, ha ocurrido un error inesperado");
        } catch (InvocationTargetException ex) {
            view.printErrorMessage("Lo sentimos, ha ocurrido un error inesperado");
        }
    }

    private void mostrarCotizacionEquipo() {
        String codigo = view.getSingleText("el codigo");
        int diasAlquiler = view.getSingleInteger("dias de alquiler");

        cotizar(codigo, diasAlquiler);
    }

    private void alquilar() {
        String codigo = view.getSingleText("el codigo");
        int diasAlquiler = view.getSingleInteger("dias de alquiler");
        double monto = cotizar(codigo, diasAlquiler);

        boolean confirmacion = view.getRentalConfirmation();

        if (!confirmacion) {
            view.printSuccessMessage("Renta de equipo cancelada");
            return;
        }

        try {
            model.updateRentData(codigo, false, monto);
            view.printSuccessMessage("La renta fue registrada correctamente!");

        } catch (IllegalArgumentException ex) {
            view.printErrorMessage(ex.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            view.printErrorMessage(ex.getMessage());

        } catch (Exception e) {
            view.printErrorMessage("Lo sentimos, ha ocurrido un error inesperado");
        }

    }

    private void devolucion() {
        String codigo = view.getSingleText("el codigo");

        try {
            model.updateRentData(codigo, true, 0);
            view.printSuccessMessage("La devolucion fue registrada correctamente!");

        } catch (IllegalArgumentException ex) {
            view.printErrorMessage(ex.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            view.printErrorMessage(ex.getMessage());

        } catch (Exception e) {
            view.printErrorMessage("Lo sentimos, ha ocurrido un error inesperado");
        }
    }
    
    private void reporte(){
        Object[] disponibilidades = model.getAvailabilityDevices();
        double ganancias = model.getWinnings();
        
        view.printReporte(disponibilidades, ganancias);
    }

    private double cotizar(String codigo, int diasAlquiler) {
        try {
            Object[] datos = model.getSingleDevice(codigo);
            double recargo = switch (datos[1].toString().toLowerCase()) {
                case "proyector" -> diasAlquiler * ((int)datos[5]);
                case "camara" -> (int)datos[5];
                case "sonido" -> diasAlquiler * ((int)datos[5]) * ((double)datos[7]);
                default -> 0;
            };
            
            double precioTotal = diasAlquiler * ((int) datos[4]) + recargo;

            List<Object> cotizacionData = new ArrayList<>();
            cotizacionData.add(datos[0]);
            cotizacionData.add(datos[1]);
            cotizacionData.add(datos[4]);
            cotizacionData.add(datos[5]);
            cotizacionData.add(((boolean) datos[6]) ? "Si" : "No");
            cotizacionData.add(diasAlquiler);
            cotizacionData.add(precioTotal);
            for (int i = Equipo.class.getDeclaredFields().length; i < datos.length; i++) {
                cotizacionData.add(datos[i]);
            }

            view.printCotizacion(cotizacionData.toArray());
            return precioTotal;

//            System.getLogger(Controller.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IndexOutOfBoundsException ex) {
            view.printErrorMessage(ex.getMessage());
        } catch (NoSuchMethodException ex) {
            view.printErrorMessage("Lo sentimos, ha ocurrido un error inesperado");
        } catch (IllegalAccessException ex) {
            view.printErrorMessage("Lo sentimos, ha ocurrido un error inesperado");
        } catch (InvocationTargetException ex) {
            view.printErrorMessage("Lo sentimos, ha ocurrido un error inesperado");
        }
        return 0;
    }
}
