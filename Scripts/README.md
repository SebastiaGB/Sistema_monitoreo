# 📜 Explicación de los Scripts

En la carpeta `Scripts_Java/src/main/java/Iotib` se encuentran dos directorios principales: `decoder` y `paquetesperdidos`. A continuación, se describe brevemente el funcionamiento del código.

---

## 1. 🧹 Decoder

Este módulo gestiona la descarga, decodificación y almacenamiento de paquetes provenientes del gateway IoT. Sus clases principales son:

* `Decoder`
* `Gateway_prueba`
* `Database`
* `AdeunisPayload`
* `MilesightPayload`
* `SenseCapPayload`
* `DRAGINO_Payload`

### 🔄 Flujo General

* La clase principal `Decoder` inicia el proceso conectándose al gateway y solicitando los paquetes.
* Se valida el token de autenticación usando `Gateway_prueba`.
* Los paquetes descargados se almacenan en la tabla `data` mediante la clase `Database`.
* Según el tipo de dispositivo (appEUI), el payload se envía a la clase de decodificación correspondiente.

### 🧠 Descripción de Clases

#### Decoder

Clase principal que controla el flujo. Accede al gateway, obtiene un token, descarga paquetes, los decodifica y los almacena en la base de datos. Usa un bucle para iterar sobre los datos obtenidos.

#### Gateway\_prueba

Gestiona la autenticación y comunicación con el gateway. Utiliza conexiones seguras SSL y métodos `POST`/`GET` para obtener el token y los paquetes, evitando duplicados comparando fechas con la base de datos.

#### Database

Gestiona la conexión y operaciones sobre la base de datos MySQL. Inserta paquetes en `data` y actualiza las tablas específicas como `adeunis_payload`, `milesight_payload`, etc., usando `PreparedStatements`.

#### AdeunisPayload

Convierte los datos hexadecimales en información útil como estado del botón, retardo, presencia, luminosidad, etc. Los datos se preparan para ser insertados en la base.

#### MilesightPayload

Procesa los datos recibidos de sensores Milesight. Realiza transformaciones de arrays binarios a valores útiles y los almacena en la base.

#### DRAGINO\_Payload

Extrae e interpreta los datos de sensores Dragino, convirtiéndolos en información numérica legible y estructurada para su registro en la base.

#### SenseCap\_Payload

Se encarga de los sensores SenseCap. Interpreta los payloads recibidos y los guarda de forma segura y estructurada en la base de datos.

---

## 2. 📉 Cálculo de Paquetes Perdidos

Este módulo calcula la pérdida mensual de paquetes por sensor. Se compone de dos clases:

* `Database`
* `PaquetesPerdidos`

### 🔍 Detalles del Funcionamiento

#### Conexión a la Base de Datos

* Se inicializa la clase `Database` con los parámetros de conexión.
* Se abre y cierra la conexión usando métodos propios según el contexto.

#### Cálculo de Pérdidas

1. **Rango Temporal**: Se obtiene el mes actual como rango de análisis.
2. **Contar Dispositivos (devEUIs)**: Se identifican los dispositivos activos.
3. **Tiempo de Transmisión (trx\_time)**: Se analiza el tiempo entre transmisiones actuales y anteriores.
4. **Counter Mínimo**: Se detecta si un paquete es el primero en una sesión.
5. **Paquetes Esperados y Totales**: Se calculan ambas métricas para cada dispositivo.
6. **Cálculo de Pérdida**: Se aplica la fórmula `(esperados - totales) / esperados`.
7. **Almacenamiento**: Se guarda el porcentaje mensual de pérdida en la tabla `paquetes_perdidos`.

---

📄 Este sistema de scripts permite la gestión automatizada de los datos recibidos por los sensores, su transformación y almacenamiento, y el análisis de calidad del enlace en función de los paquetes perdidos. Perfecto para aplicaciones industriales y monitorización remota de infraestructuras.
