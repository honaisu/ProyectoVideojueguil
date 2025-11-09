# Not Hotline Miami

_Not Hotline Miami_ es un videojuego desarrollado con [libGDX](https://libgdx.com/), inspirado en los clásicos de acción con vista superior. El proyecto está organizado en módulos multiplataforma, siendo `core` el módulo principal con toda la lógica del juego y `lwjgl3` la plataforma de escritorio.

## Características principales

- **Acción arcade**: Controla a tu personaje, esquiva enemigos y dispara diferentes tipos de armas.
- **Gestión de recursos**: Sistema de texturas, sonidos y música centralizado mediante un singleton ([`managers.AssetManager`](core/src/main/java/managers/AssetManager.java)).
- **Pantallas navegables**: Menú principal, personalización de personaje, configuración, tutorial, pausa y game over, gestionadas por [`managers.ScreenManager`](core/src/main/java/managers/ScreenManager.java).
- **Sistema de armas**: Diferentes armas implementadas en [`armas`](core/src/main/java/armas/) y proyectiles en [`armas.proyectiles`](core/src/main/java/armas/proyectiles/).
- **Colisiones y enemigos**: Lógica de colisiones y gestión de enemigos en [`managers.CollisionManager`](core/src/main/java/managers/CollisionManager.java) y [`managers.EnemyManager`](core/src/main/java/managers/EnemyManager.java).
- **Personalización**: Selección de skins para el jugador.
- **Configuración de audio**: Ajuste de volúmenes de música y efectos.

## Estructura del proyecto

- **core/**: Lógica principal del juego, pantallas, managers, armas, personajes, etc.
- **lwjgl3/**: Lanzador y configuración para escritorio (Windows/Linux/macOS).
- **assets/**: Recursos del juego (imágenes, sonidos, música).

## Cómo ejecutar

1. **Requisitos**: Java 8+ y Gradle (o usar el wrapper incluido).
2. **Instalación**: Para instalarlo se puede clonar el repositorio a través de las opciones provistas por GitHub.
3. **Compilar y ejecutar**: Dentro de la carpeta del proyecto, ejecutar el siguiente comando.
   ```sh
   ./gradlew lwjgl3:run
   ```
