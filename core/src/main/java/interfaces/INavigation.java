package interfaces;

/**
 * @deprecated
 * Interfaz de navegación. Provee direcciones hacia las que se puede mover una selección
 * junto con un método default para navegar.
 */
@Deprecated
public interface INavigation {
	/**
	 * Método encargado de navegar menús de manera fácil e intuitiva.
	 */
	default public int navegar(boolean vaAdelante, int indiceActual, int length) {
    	int eleccion = 1;
    	
    	if (vaAdelante) eleccion = -1;
    	
        int nuevoIndice = (indiceActual + eleccion + length) % length;
        return nuevoIndice;
    }
	
	/**
     * Enum simple que indica que direcciones puede tener cierta selección
     * (en caso de menús con movimiento entre pantallas)
     */
    //public enum Direction { ARRIBA, ABAJO, IZQUIERDA, DERECHA; }
    
    /**
     * Método que permite navegar menús de forma intuitiva y fácil :D
     * 
     * @param direccion Donde "apuntará" la nueva selección
     * @param indiceActual El índice actual de la selección
     * @param length El tamaño completo de las opciones de selección
     * @return El índice de la opción a elegir
     */
	/*
    default public int navegar(Direction direccion, int indiceActual, int length) {
    	int eleccion = 1;
    	
    	if (direccion.equals(Direction.ARRIBA)
    			|| direccion.equals(Direction.IZQUIERDA)) eleccion = -1;
    	
        int nuevoIndice = (indiceActual + eleccion + length) % length;
        return nuevoIndice;
    }*/
}
