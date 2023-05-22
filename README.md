# CHALLENGE

## Contexto:
Recientemente varios hospitales de la zona han recibido una serie de ataques informáticos y se ha propuesto renovar el sistema de control de citas en el hospital AccWe, ya que es lo que mas tiempo lleva sin actualizarse. Los desarrolladores han salvado parte del proyecto anterior y lo han limpiado para evitar posibles errores, actualizando sobretodo librerías. Tu tarea sera implementar, arreglar y desarrollar las diferentes necesidades del hospital en cuanto a la gestión de citas.

## 🎯 Objetivos:

El objetivo principal es terminar de implementar y desarrollar la gestión de citas del proyecto de Backend. Este proyecto utiliza versiones antiguas de JAVA, en concreto JAVA 8. Se pide por tanto desarrollar las siguientes tareas:

1. Tu primera tarea va a ser crear citas mediante la API. Debes tener en cuenta las limitaciones en cuanto a la creación de citas. Asegúrate de cumplir con las especificaciones hechas en JUnit, ya que son una ayuda esencial para el desarrollo de código. Solamente se requiere modificar el archivo **AppointmentController.java**.
 
2. La segunda tarea tiene relación con que las entidades creadas no están siendo "testeadas", y por lo tanto, pueden surgir posibles errores debido a una mala implementación. Para solucionar esto, se deben crear diferentes pruebas "JUnit" para cada una de las entidades en el archivo **EntityUnitTest.java**, así como cada uno de los controladores de estas entidades en el archivo **EntityControllerUnitTest.java**, que se encuentra en el directorio de pruebas.

3. Asegúrate realizar **código limpio**. Como estás trabajando en un hospital, se espera que sigas medidas estrictas para garantizar que el código sea aceptable. Por lo tanto, debes escribir código que esté libre de errores y vulnerabilidades, especialmente de vulnerabilidades.

4. Se quiere hacer un **despliegue escalable de la API**, para ello se plantea usar Kubernetes. Cree un Dockerfile que permita ejecutar una base de datos MySQL y el microservicio. Para este último requerimiento, se solicita un Dockerfile Multistage donde se ejecuten primero las pruebas, y si todas pasan, se compile y ejecute el microservicio. Los nombres de los Dockerfiles serán **Dockerfile.mysql** y **Dockerfile.maven**, respectivamente.

***Extra OPCIONAL:** Desarrolla un diagrama UML con todas las relaciones entre clases. Se valorará positivamente la documentación dentro del repositorio.

## ℹ️ Desarrollo y entrega de la solución:

1. Clona el repositorio y crea una nueva rama a partir de la rama "main".
2. Desarrolla tu solución en la nueva rama que has creado.
3. Cuándo hayas terminado el código, haz pull request y merge a la rama "main" para combinar tus cambios.
4. Verifica que todas las GitHub Actions hayan finalizado.
5. Por último, regresa aquí y clica el botón "ENVIAR SOLUCIÓN".

### ✍️ Evaluación:

La evaluación tendrá en cuenta principalmente los objetivos cumplidos, la calidad del código y la documentación presentada, con un total de 1200 puntos, distribuidos de la siguiente manera:
* Objetivos -> 900 PTS
* Calidad -> 200 PTS
* Documentación -> 100 PTS

- - -

# CHALLENGE TODOs

- Muy Cloud Native y bricolage, mas que programación de algoritmos.

- Podría ser evaluado una sección de TODOs/Refactorización de la aplicación para ver el nivel de conocimiento de buenas prácticas arquitecturales como parte de la documentación.

- En general con el enunciado de las tareas no se sabe si es requerido o evaluado realizar algún tipo de modificación más allá de las tareas y ficheros indicadas.

- La BBDD de test es H2 y la de ejecución es MySQL por lo que puede haber problemas en el testeo por incompatibilidad.

- No se evalúa conocimiento de OpenAPI y Swagger

- En el mismo microservicio se gestionan los maestros (Doctor, Patient, Room), y el negocio (Appointment)

- Se usan persistence entities en vez de DTOs en los controllers, se expone la entidad de negocio en vez de una entidad/esquema de presentación.

- No se incluye la dependencia de JavaBean validation  

- No hay separación de capa de servicio

- La entity Doctor no tiene un Id en H2 (incompatibilidad de anotación) por lo que no se puede realizar un findbyId que permita verificar si el doctor existe en un appointment, no es compatible la autogeneración del id en H2 con mySQL

- shouldCreateAppointment y en general las creaciones deberían verificar HttpStatus.CREATED en vez de HttpStatus.OK, para mejorar la monitorización del service mesh

- shouldCreateOneAppointmentOutOfTwoConflictDate obliga a realizar un findAll para verificar conflicto de room , no es muy performance

- En la creación del appointment se debe de obtener el id del doctor y del room para poder crearlo, tanto el doctor como el room tienen que existir previamente y se debe validar 

- Response entity debería dejar algún tipo de mensaje de error en el body, la gestión del response se debería centralizar.

- La entidad Doctor necesitaría algún tipo de validación de equals para verificar que son iguales o setear el Id en el test que no es posible por la estrategia de creación

- La entidad Room necesitaría algún tipo de validación de equals para verificar que son iguales o setear el Id en el test que no es posible por la estrategia de creación

- Para verificar el overlapping se tendría que realizar un servicio que lo realice por doctor y/o por room

- Evaluar las consideraciones personales sobre la prueba que no esté verificado en ella

- La documentación UML no es algo muy demandado en servicios sencillos, no debería ser determinante

- El uso de Dockerfile multistage es algo muy de bricolage que normalmente está soportado por equipos de DevOps y ARQ incluyéndolo en las pipelines

- La gran mayoría del código a entregar reside en JUnits, en los que hay mucho código "boilerplate" al repetirse las acciones/tests entre entidades





