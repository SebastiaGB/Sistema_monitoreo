# 🌐 Remote Monitoring LoRa Project

Sistema inteligente de monitorización remota en tiempo real para centros de telecomunicaciones mediante sensores IoT y tecnología LoRaWAN.

> 🎓 Proyecto desarrollado como Trabajo de Fin de Grado (TFG) en colaboración con **FUEIB** mientras trabajaba como técnico en telecomunicaciones.

---

## 🎯 Objetivos del Proyecto

* Monitorizar en tiempo real variables críticas en centros de telecomunicaciones.
* Seleccionar sensores LoRa adecuados para entornos interiores y exteriores.
* Configurar pasarelas seguras para la comunicación inalámbrica.
* Decodificar, almacenar y visualizar los datos de forma eficaz.
* Implementar dashboards interactivos con **Grafana**.

---

## 📦 Estructura del Repositorio

```
Documentació_Dispositius/   # Datasheets y manuales de los sensores
Data_base/                  # Backup y diseño estructural de la base de datos MySQL
Scripts_Java/               # Código fuente de decodificación y análisis de datos
JSON_Grafana_Dashboards/   # Dashboards exportados desde Grafana
TFG.pdf                     # Documento completo del trabajo de fin de grado
```

---

## 🔌 Sensores Utilizados

| Sensor            | Función                                      |
| ----------------- | -------------------------------------------- |
| SenseCap S2101    | Temperatura exterior                         |
| Milesight EM320   | Temperatura y humedad interior               |
| Milesight WS202   | Detección de presencia                       |
| Dragino CPL01     | Detección de apertura de puertas             |
| Milesight AM107   | Sensor ambiental multifunción                |
| Adeunis Motion V2 | Movimiento y luminosidad                     |
| Milesight EM500   | CO₂, humedad, presión y temperatura exterior |

✅ Todos configurados en modo **OTAA**, frecuencia **868 MHz**, clase **A** o **C**, según dispositivo.

---

## 🧠 Funcionamiento Técnico

1. Los sensores transmiten mediante LoRa al **gateway**.
2. Este reenvía los datos a la plataforma IoT.
3. Un script Java captura, decodifica y almacena los datos en **MySQL**.
4. Grafana accede a la base para visualizar en dashboards.

---

## 📄 Estructura de Base de Datos (MySQL)

| Tabla              | Descripción                                                        |
| ------------------ | ------------------------------------------------------------------ |
| `centros`          | Identificadores de centros, ubicación y sensores asociados         |
| `data`             | Registro bruto de uplinks y downlinks                              |
| `paquetesperdidos` | Paquetes perdidos identificados por contador                       |
| `*_payload`        | Decodificación estructurada según tipo de sensor (Milesight, etc.) |

🔗 Claves primarias: `id`, `devEUI`, `payload`.

---

## 🧹 Scripts Java

### 📁 `Scripts_Java`

* `Decoder.java`: captura y decodifica uplinks.
* `Database.java`: gestiona conexión con MySQL.
* `Gateway.java`: conexión con la API del gateway.
* `*_Payload.java`: lógica de decodificación por tipo de sensor.
* `PaquetesPerdidos.java`: analiza pérdidas en la transmisión.

> 📘️ Código limpio, modular, reutilizable y documentado para facilitar el mantenimiento.

---

## 📊 Dashboards en Grafana

| Dashboard        | Contenido                                                      |
| ---------------- | -------------------------------------------------------------- |
| Principal (Mapa) | Vista geolocalizada de centros con resumen de sensores activos |
| Centro 1         | Sensecap, AM107, CPL01                                         |
| Centro 2         | Adeunis Motion V2, EM320                                       |
| Centro 3         | WS202, EM500                                                   |

> 📁 JSONs exportados disponibles en `JSON_Grafana_Dashboards/`.

---

## 🔐 Seguridad y Buenas Prácticas

* ❌ No se incluye ningún dato sensible (AppKeys, DevEUIs reales, etc.).
* ✅ Los dispositivos están anonimizados y configurados para fines educativos.
* 🔒 Acceso y tráfico cifrado mediante protocolo HTTPS / LoRa seguro.

---

## 📄 Documentación Técnica

* 📚 `Documentació_Dispositius/` contiene todos los datasheets oficiales y documentación de fabricantes.
* 📖 `TFG.pdf` expone la metodología, resultados y justificación técnica.

---

## 🚀 Tecnologías Usadas

* **LoRaWAN (OTAA, SF12, 868 MHz)**
* **Java** para scripts de backend
* **MySQL** como base de datos relacional
* **Grafana** para visualización avanzada
* **Toolbox**, **SenseCap Mate**, **IOTConfigurator** para configuración

---

## 🌾 Resultado Final

✅ Sistema desplegado con éxito en entorno real
✅ Transmisión fiable y eficiente
✅ Visualización en tiempo real y trazabilidad histórica
✅ Escalable y adaptable a otros entornos (granjas, edificios, etc.)
