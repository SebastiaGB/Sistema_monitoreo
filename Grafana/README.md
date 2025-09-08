# Visualización en Grafana por Centros

Se ha diseñado una carpeta `Sensores/` en Grafana que contiene **cuatro dashboards**:  
- **Centro 1**
- **Centro 2**
- **Centro 3**
- **Dashboard Principal**

Cada uno muestra de forma visual, clara e interactiva, los datos captados por los sensores en cada ubicación.

---

## 🗺️ Dashboard Principal

- Muestra un **mapa de Mallorca e Ibiza** con la ubicación de los tres centros.
- Los centros están señalizados con triángulos azules.
- Al pasar el cursor sobre cada centro, se muestra:
  - Nombre
  - Coordenadas GPS
  - Lista de sensores instalados

---

## 🏢 Dashboards por Centro

Cada centro tiene un dashboard independiente con la **misma estructura visual**, que incluye:

### 🔋 Información General

- Mapa del centro con su ubicación (esquina superior izquierda)
- Porcentaje de batería de cada sensor
- Valores actuales de cada parámetro medido:
  - Temperatura
  - Humedad
  - Presencia
  - Estado de la puerta
  - CO₂, presión (según el centro)
- Indicadores de estado (`Hay presencia`, `Open`) con cambio de color automático

### 📉 Métricas Avanzadas

- **Paquetes perdidos** mensuales por sensor (en %)
- **Tabla de aperturas recientes de puerta** (últimas 10, indicando si superaron el tiempo límite)
- **Gráficos temporales** de todas las variables medidas
- **Parámetros de red**:
  - SF (Spreading Factor)
  - SNR (Signal-to-Noise Ratio)
  - RSSI (Received Signal Strength Indicator)

---

## 🧭 Sensores por Centro

| Centro   | Sensores Instalados                                |
|----------|----------------------------------------------------|
| Centro 1 | SenseCap S2101, Dragino CPL01, Milesight AM107     |
| Centro 2 | Milesight EM320, Adeunis Motion V2                 |
| Centro 3 | Milesight WS202, Milesight EM500                   |

---

## 🎯 Beneficios del Diseño

- Estructura homogénea → facilita comparación entre centros
- Visualización clara y en tiempo real
- Monitorización eficiente de estado, calidad de red y datos históricos
