
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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
public class Model {

    private double ganancias = 0;
    private List<Equipo> equipos = new ArrayList<>();

    public Model() {
        // ***** Testing *****
        equipos.add(new Proyector("0101", "Pixel", "E904", 125, 0, 20, false));
        equipos.add(new Proyector("0102", "Sony", "Kl563", 300, 50, 450, true));

        equipos.add(new Camara("0201", "Pixel", "uxh9", 100, 0, 720));
        equipos.add(new Camara("0202", "Canon", "ubx2", 250, 75, 2160));

        equipos.add(new Sonido("0301", "Steren", "UT430", 75, 0.7));
        equipos.add(new Sonido("0302", "Sony", "XA990", 200, 1.5));
        //*****
    }

    public void addDevice(Equipo newDevice) {
        for (Equipo ExistingDevice : equipos) {
            if (ExistingDevice.getCodigo().equals(newDevice.getCodigo())) {
                throw new IllegalArgumentException("El codigo ya se encuentra registrado");
            }
        }
        this.equipos.add(newDevice);
    }

    public Object[] getAllDevices() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        List<Object[]> datosTotales = new ArrayList<>();

        for (Equipo equipo : equipos) {
            datosTotales.add(recolectObjectData(equipo));
        }

        return datosTotales.toArray();
    }

    public Object[] getSingleDevice(String codigo) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for (Equipo equipo : equipos) {
            if (equipo.getCodigo().equals(codigo)) {
                return recolectObjectData(equipo);
            }
        }

        throw new IndexOutOfBoundsException("Equipo no encontrado");
    }

    public Object[] getAvailabilityDevices(){
        List<Object[]> disponibilidades = new ArrayList<>();
        
        for (Equipo equipo : equipos) {
            disponibilidades.add(new Object[]{equipo.getCategoria(), equipo.getDisponibilidad()});
        }
        
        return disponibilidades.toArray();
    }
    
    public double getWinnings(){
        return ganancias;
    }
    
    public void updateRentData(String codigo, boolean disponibilidad, double monto){
        for (Equipo equipo : equipos) {
            if (equipo.getCodigo().equals(codigo)) {
                equipo.setDisponibilidad(disponibilidad);
                ganancias += monto;
                return;
            }
        }

        throw new IndexOutOfBoundsException("Equipo no encontrado");
    }
    
    
    private Object[] recolectObjectData(Equipo equipo) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Field[] atributosHijo = equipo.getClass().getDeclaredFields();
        Field[] atributosPadre = equipo.getClass().getSuperclass().getDeclaredFields();

        int atributosCounter = atributosHijo.length + atributosPadre.length;
        Object[] datos = new Object[atributosCounter];

        for (int i = 0; i < atributosCounter; i++) {
            Field atributo = (i < atributosPadre.length) ? atributosPadre[i] : atributosHijo[i - atributosPadre.length];

            if (Modifier.isStatic(atributo.getModifiers()) || atributo.isSynthetic()) {
                continue;
            }

            String atributoNombre = atributo.getName();
            String metodoNombre = "get" + Character.toUpperCase(atributoNombre.charAt(0)) + atributoNombre.substring(1);

            Method getter = equipo.getClass().getMethod(metodoNombre);
            datos[i] = getter.invoke(equipo);
        }

        return datos;
    }

}
