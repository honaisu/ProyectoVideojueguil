package pantallas.opciones;

import interfaces.INavigableOption;
// Opciones de la pantalla de victoria
public enum EWinOption implements INavigableOption {
    VOLVER_A_JUGAR("Volver a Jugar"),
    MENU_PRINCIPAL("Menu Principal");

    private final String nombre; // como lo haciamos con Pause Option

    private EWinOption(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getNombre() { 
        return nombre;
    }
}