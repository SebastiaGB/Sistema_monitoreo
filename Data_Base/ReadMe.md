
 Se detalla la relación entre las tablas de la base de datos del proyecto utilizando el
 diagrama de datos presentado a continuación para una mejor comprensión del diseño.
 Labasededatos,denominadaiotib2,constadesietetablas:data,centros,paquetesperdidos,
 milesight_payload, adeunis_payload, senseCap_payloadydragino_payload.
 Tres atributos principales, id, devEUI y payload, se utilizan para relacionar las tablas.
 Aunquecadatabla contiene más atributos, estos tres son los comunes y se describirán
 endetalle en el siguiente capítulo.
 La Figura 3.1 muestra un diagrama de datos con todas las tablas de la base de da
tos.
 La tabla centros almacena los nombres de los centros simulados en las Baleares,
 junto con la LatitudyLongitud,ylossensores decadacentro.
 La tabla data guarda tanto los uplinks como los downlinks de los dispositivos. Los
 atributos se organizan en columnas, representando cada fila un paquete completo de
 undispositivo. Estos paquetes se identifican por una id que se incrementa de forma
 ascendente. El asterisco indica que la tabla posee la id de todos los dispositivos. La
 devEUIidentifica a qué dispositivo pertenece cada paquete y la direction determina
 si es un uplink o downlink. Estos atributos son clave para identificar cada paquete y de
21
3. DISEÑO DEL SEGUNDO BLOQUE: BASE DE DATOS, SCRIPTS Y GRAFANA
 Figura 3.1: Diagrama de Bloques Funcional Decoder, Gateway_prueba
 terminar en qué tabla adicional se debe almacenar. Counter se utiliza para determinar
 el orden de los uplinks.
 La tabla paquetesperdidos registra la cantidad de paquetes perdidos cada mes en
 forma deporcentaje. Utiliza la id de todos los uplinks de data para identificarlos y la
 devEUIparaidentificar el dispositivo correspondiente.
 Los uplinks se almacenan enlas tablas específicas a medida que ingresan en data, de
 maneraordenada.
 Lastablasmilesight_payload,adeunis_payload,sensecap_payloadydragino_payload
 contienen los tres atributos mostrados en el diagrama 3.1 para los uplinks de dispositi
vos del fabricante correspondiente al nombre de la tabla.
