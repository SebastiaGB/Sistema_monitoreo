# 🚁 Monitoreo Remoto de Centros con Tecnología IoT y LoRaWAN

Este proyecto consiste en el desarrollo de una solución de **monitoreo remoto** para centros de telecomunicaciones, utilizando sensores IoT conectados mediante tecnología **LoRaWAN** (comunicaciones de largo alcance y bajo consumo).

El sistema permite recopilar en tiempo real variables ambientales como temperatura, humedad, presencia de personas y estado de puertas, facilitando una supervisión remota eficaz y sostenible.

---

## 🧹 Componentes del Sistema

- **Sensores IoT**: Dispositivos distribuidos en los centros para captar datos clave.
- **Gateway LoRaWAN**: Punto central de comunicación con los sensores.
- **Base de datos MySQL**: Almacenamiento estructurado y persistente de los datos.
- **Scripts Java**: Procesamiento, decodificación y análisis de los paquetes LoRa.
- **Grafana Dashboards**: Visualización clara e interactiva de los datos en tiempo real.

---

## ⚙️ Objetivos del Proyecto

- Mejorar la supervisión de entornos remotos sin intervención continua.
- Centralizar los datos de múltiples sensores en una única plataforma visual.
- Detectar incidencias o anomalías (puertas abiertas, temperaturas elevadas, etc.) de forma inmediata.

> 📁 Este repositorio recoge los scripts de decodificación, modelo de base de datos, dashboards de Grafana y documentación técnica.

---

## LoRa

1. Los sensores transmiten los datos vía LoRa al **gateway**. Lora es una tecnología que utiliza tres claves para implenetar su segueridad: la deveui, la appeui y la appkey, la appeui es menos usada ya que en la mayoria de casos no es necesaria para establecer la conexión pero las otros dos si. La deveui es el identificador único del dispositivo proporcionado por el fabricante y la appkey es una cvlave de 32 bites a veces proporcionada por el fabricante, pero normalmente debe ser creada por el usuario accediendo al dispositivo. Estas claves se usarán para conectar los dispositivos al gateway. 

---

## 🔌 Dispositivos IoT Utilizados

| Sensor              | Fabricante | Variables Monitorizadas                          |
|---------------------|------------|--------------------------------------------------|
| SenseCap S2101      | Seeed      | Temperatura exterior                             |
| Milesight EM320     | Milesight  | Temperatura y humedad interior                   |
| Milesight WS202     | Milesight  | Presencia de personas                            |
| Dragino CPL01       | Dragino    | Estado de apertura de puertas                    |
| Milesight AM107     | Milesight  | Temp., CO₂, presión y humedad                    |
| Adeunis Motion V2   | Adeunis    | Movimiento y luminosidad                         |
| Milesight EM500     | Milesight  | Temp., CO₂, humedad y presión exteriores         |

📁 `Documentación_Dispositivos/` → Datasheets y manuales técnicos.

---

## 🧾 Uso de la  Base de Datos (MySQL)

La base de datos almacena los datos obtenidos por los sensores mediate los scripts java en diferentes tablas para facilitar su gestión.

📁 `DB/` → Backup de la base de datos con estructura vacía (sin datos) y estructura de esta.

---

## 💻 Scripts Java para Procesamiento de Datos

Creación de dos programas unio para obtener, decodificar y almacenar datos y otro para calcular los paquetes perdido  mensualmente. 

### 🔍 `Decoder.java`

- Obtiene paquetes del gateway
- Llama al decodificador según el tipo de sensor
- Almacena resultados en MySQL

### 📉 `PaquetesPerdidos.java`

- Calcula paquetes perdidos por devEUI
- Guarda la métrica en la tabla correspondiente

📁 `Scrips_Java/` → Contiene clases, documentación y lógica de decodificación.

---

## 📊 Visualización con Grafana

### Dashboards Personalizados:

- 🌍 Mapa global de centros (Mallorca) geolocalizados.
- **Centro 1**: SenseCap S2101, Dragino CPL01, AM107.
- **Centro 2**: EM320, Adeunis Motion V2.
- **Centro 3**: WS202, EM500.

📁 `JSON_Grafana_Dashboards/` → Exportación de dashboards en formato JSON.

---

## 🛠️ Configuración de Sensores

| Sensor     | App de configuración        |
|------------|-----------------------------|
| Milesight  | Milesight Toolbox           |
| SenseCap   | SenseCAP Mate               |
| Adeunis    | IOTConfigurator             |

**Parámetros comunes:**

- Frecuencia: 868 MHz (EU868)
- Modo: OTAA
- Clase: A
- SF: 12 (para mayor alcance)

---

## 📎 Documentación Adicional

- 📄 `TFG.pdf` → Informe completo del proyecto (memoria, anexos, pruebas).
- 📐 Diagramas UML → Arquitectura, BBDD, flujo de datos.
- ⚙️ Especificaciones → Extraídas de los datasheets oficiales.

---

## 🚀 Tecnologías Usadas

- 🛰️ **LoRaWAN** (OTAA, SF12, 868 MHz)
- 🧠 **Java** para backend y lógica de decodificación
- 🗃️ **MySQL** como base de datos relacional
- 📈 **Grafana** para dashboards
- 🧰 **Toolbox**, **SenseCAP Mate**, **IOTConfigurator** para configuración de sensores

---

## 🌾 Resultado Final

✅ Sistema desplegado y funcional en entorno real  
✅ Visualización en tiempo real y trazabilidad histórica  
✅ Eficiente y escalable a nuevos entornos (granjas, edificios, etc.)  
✅ Datos protegidos y arquitectura robusta
