# Requisitos

## Requisitos funcionales

### RF1 - Representación del tablero
El sistema debe permitir representar una cuadrícula bidimensional de tamaño configurable. 

### RF2 - Tipos de entidades
El sistema debe soportar al menos tres tipos de entidades con comportamientos diferenciados. 

### RF3 - Entidad estática
El sistema debe permitir entidades que permanezcan en su posición sin moverse durante la simulación. 

### RF4 - Movimiento aleatorio constante
El sistema debe permitir entidades que se desplacen en cada iteración a una casilla adyacente (arriba, abajo, izquierda o derecha) de forma aleatoria. 

### RF5 - Movimiento probabilístico
El sistema debe permitir entidades que, en cada iteración, tengan una probabilidad configurable de moverse a una casilla adyacente. 

### RF6 - Reglas de ocupación
Cuando una entidad intenta moverse a una casilla ocupada, el sistema debe mantener el estado previo de la casilla (prevalece el color existente). 

### RF7 - Iteración de la simulación
El sistema debe permitir avanzar la simulación por iteraciones discretas. 

### RF8 - Exposición mediante API
El sistema debe exponer la funcionalidad mediante una API REST en formato JSON.

## Requisitos no funcionales 

### RNF1 - Portabilidad
La aplicación debe poder ejecutarse en cualquier equipo con Java 17 instalado.

### RNF2 - Mantenibilidad
El código debe estar estructurado y documentado para facilitar su comprensión y modificación.

### RNF3 - Testabilidad
El sistema debe incluir pruebas unitarias y/o de integración.

### RNF4 - Rendimiento
El sistema debe ser capaz de simular al menos un tablero de tamaño medio (por ejemplo 50x50) sin degradación significativa del rendimiento.

### RNF5 - Despliegue
La aplicación debe poder desplegarse en un entorno servidor accesible.
