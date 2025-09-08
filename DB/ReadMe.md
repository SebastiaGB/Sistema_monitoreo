
# Estructura de la Base de Datos

Este apartado documenta el diseño y la relación entre las tablas utilizadas en la base de datos del proyecto. Para facilitar la comprensión, se incluye el diagrama de bloques en la imagen `DiagramaDB.jpg`.

---

## 🧩 Tablas de la Base de Datos

La base de datos consta de **siete tablas** principales:

- `data`
- `centros`
- `paquetesperdidos`
- `milesight_payload`
- `adeunis_payload`
- `senseCap_payload`
- `dragino_payload`

Estas tablas se relacionan principalmente mediante **tres atributos clave**:

- `id`
- `devEUI`
- `payload`

Aunque cada tabla contiene más columnas, estos tres atributos son comunes y fundamentales para la organización de los datos.

---

## 🗂️ Descripción de las Tablas

###  `centros`

- Almacena información de los centros simulados en las Islas Baleares.
- Incluye nombre del centro, latitud, longitud y sensores asociados.

### `data`

- Contiene los **uplinks** y **downlinks** enviados por los dispositivos.
- Cada fila representa un paquete completo con una `id` ascendente.
- Atributos destacados:
  - `id`: clave única del paquete.
  - `devEUI`: identifica el dispositivo que lo envía o recibe.
  - `direction`: define si el paquete es uplink o downlink.
  - `counter`: permite ordenar los uplinks en secuencia.

### `paquetesperdidos`

- Registra la pérdida mensual de paquetes en **porcentaje**.
- Se basa en la `id` de `data` para vincular los registros.
- El campo `devEUI` permite identificar el dispositivo correspondiente.

---

## 🧾 Tablas de Payload Específico

Las siguientes tablas almacenan los datos decodificados de los uplinks según el fabricante del dispositivo:

- `milesight_payload`
- `adeunis_payload`
- `sensecap_payload`
- `dragino_payload`

Cada una contiene:

- `id` del paquete original en `data`
- `devEUI` del dispositivo
- `payload` con los datos específicos del sensor

Estas tablas permiten separar y organizar la información según el tipo de dispositivo, facilitando su interpretación y análisis.

---

📌 **Nota:** La imagen `DiagramaDB.jpg` incluida en esta carpeta muestra gráficamente cómo se relacionan estas tablas entre sí.

