# **Explicació scripts**

A la carpeta 'Scripts_Java\src\main\java\Iotib' es troben les carpetes de 'decoder' i 'paquetesperdidos'.  
A continuació es realitzarà una breu explicació del funcionament del codi:

## **1. Decoder**

En aquesta secció s'explicarà la construcció de les classes del script mitjançant diagrames de flux. Les classes són: **Decoder**, **Gateway_prueba**, **Database**, **AdeunisPayload**, **MilesightPayload**, **SenseCapPayload** i **DRAGINO_Payload**.

### Diagrama de Bloc Funcional del Script

El diagrama mostra el funcionament del script. En executar-se la classe principal **Decoder**, aquesta accedeix directament a la classe **Gateway** per verificar les credencials, establir connexió i descarregar els paquets que conté. Per desar els paquets en `data`, és necessari accedir a la classe **Database**.

Les quatre classes específiques per decodificar el payload obtingut de **Gateway** seran seleccionades depenent de l'**appEUI** del dispositiu, que determinarà a quina classe específica s'ha de passar el payload per actualitzar les dades decodificades a la seva taula.

### Classe **Decoder**

La classe **Decoder** és la classe principal del programa. S'executa només si el gateway Multitech està connectat a la màquina. Conté una cadena amb la URL per accedir a la configuració del gateway i crea una instància **conexionAPI** per accedir a la classe **Gateway**. S'obté un token d'accés i es realitza un bucle per gestionar els paquets.

### Classe **Gateway_prueba**

La classe **Gateway_prueba** conté mètodes per gestionar la connexió segura amb el gateway i per obtenir el token d'autenticació. Utilitza SSL per establir una connexió segura i realitza una petició **POST** per obtenir el token. Després, mitjançant un mètode **GET**, obté els paquets del gateway i els compara amb la data de la base de dades per assegurar-se que els paquets no es processin repetidament. Si es troben nous paquets, els decodifica i els guarda a la base de dades.

### Classe **Database**

La classe **Database** gestiona les operacions de connexió amb la base de dades i l'actualització de les taules amb la informació obtinguda dels paquets. Té mètodes per inserir dades a la taula **data** i actualitzar taules específiques per cada tipus de payload, com **adeunis_payload**, **DRAGINO_payload**, **milesight_payload** i **SenseCap_payload**. Cada mètode utilitza un **preparedStatement** per actualitzar les taules segons el tipus de decodificació.

### Classe **AdeunisPayload**

La classe **AdeunisPayload** s'encarrega de la decodificació dels payloads per als dispositius Adeunis. Converteix les dades hexadecimals a decimals i realitza diverses operacions per obtenir els valors corresponents de cada atribut del payload. Cada mètode té un propòsit específic, com obtenir l'estat del botó d'alarma, el temps de retard després d'un canvi d'estat, i el cicle de treball i ADR. També hi ha mètodes per calcular la presència i el percentatge de llum. Tots aquests valors es guarden en un conjunt de variables per al posterior processament i emmagatzematge.

### Classe **MilesightPayload**

La classe **MilesightPayload** gestiona el processament dels paquets **Milesight** i la conversió de les dades en un format comprensible. Té un conjunt de mètodes que inclouen el constructor i mètodes per realitzar la conversió de dades i la seva aplicació en la base de dades. El constructor permet la inicialització de les variables necessàries per al processament de les dades. Els mètodes de conversió realitzen la transformació de dades de tipus array a formats utilitzables, mentre que els mètodes d'aplicació gestionen l'emmagatzematge de la informació en la base de dades mitjançant sentències preparades.

### Classe **DRAGINO_Payload**

La classe **DRAGINO_Payload** es dedica a processar els paquets **Dragino** i a convertir-los a un format que sigui entenedor per la base de dades. Aquesta classe inclou un conjunt de mètodes per desglossar la informació continguda en els paquets i convertir-la en valors útils. Els mètodes de la classe inclouen la conversió dels valors de les dades a formats específics com a dades numèriques o decimals, així com la seva posterior aplicació i inserció en la base de dades. S'utilitzen sentències preparades per garantir la seguretat i eficiència en el procés d'emmagatzematge.

### Classe **SenseCap_Payload**

La classe **SenseCap_Payload** s'encarrega de processar els paquets **SenseCap** i convertir les dades a un format adequat per ser emmagatzemat a la base de dades. Aquesta classe inclou diversos mètodes que ajuden a interpretar les dades rebudes i a transformar-les en un format utilitzable. Com en les altres classes, es fa ús de sentències preparades per garantir una inserció eficaç i segura a la base de dades, mantenint la integritat de la informació i prevenint errors.


## **2. Paquets Perduts**

El script **PaquetesPerdidos** consta de dues classes: **database** i **PaquetesPerdidos**. Aquest script s'encarrega de gestionar la connexió amb la base de dades, calcular els paquets perduts mensualment i actualitzar la informació a la base de dades.

### Classe Database

La classe **Database** conté variables, un constructor i mètodes per gestionar la connexió amb la base de dades. Aquesta classe fa el següent:

- **Variables:** Definició de variables privades per a l'accés a la base de dades, com la URL, el nom d'usuari i la contrasenya.
- **Constructor:** Inicialitza els atributs per establir una connexió a la base de dades, rebent la URL, el nom d'usuari i la contrasenya.
- **Establir connexió:** Utilitza `DriverManager.getConnection()` per establir la connexió amb la base de dades.
- **Tancar connexió:** Inclou dos mètodes per tancar la connexió, un per a la connexió amb sentència SQL i un altre per a tancar la connexió general.

### Classe PaquetesPerdidos

La classe **PaquetesPerdidos** realitza els següents passos per calcular els paquets perduts mensualment:

#### Connexió amb la base de dades
- Es crea un objecte de la classe **Database** anomenat `iotib` i s'inicia la connexió mitjançant un mètode dedicat per obrir-la.

#### Obtenir Rango de Temps Mensual
- S'aconsegueix la data i hora actual mitjançant `LocalDateTime`, i es defineixen dues dates per calcular els **Paquetes Totales** i els **Paquetes Perdidos** en el rang temporal especificat.

#### Comptar devEUIs
- Es realitza una consulta per obtenir la quantitat de **devEUIs** distintes amb el valor de contador igual a 0 o 1. A continuació, es crea un array amb els valors de **devEUIs** obtinguts.

#### Introduir devEUIs
- Es fa una consulta per obtenir les **devEUIs** amb un contador igual a 0 i, si no existeixen, es recuperen les de contador 1. Els resultats es guarden en l'array de **devEUIs** creat.

#### Calcular trxTime
- S'obté el **trx_time** per al següent paquet en la sessió de cada **dev_eui** i es guarda en una llista **trx_time**.

#### Calcular trxTimeAnterior
- Per cada **trx_time**, es recupera el **trx_time** anterior de la sessió anterior i es guarda en una nova llista **trx_time_anterior**.

#### Mínim Valor del Counter
- Es calcula el valor mínim de **counter** de cada **devEUI** per determinar si el paquet és el primer de la sessió o no.

#### Paquetes Esperados
- S'inicialitza una variable `suma_counters` i es fa una consulta per obtenir els valors del **counter** dins del rang de temps mensual. Si el valor mínim del counter és 0, es considera que s'ha de sumar un paquet.

#### Paquetes Totales
- Es compten els paquets totals mitjançant una consulta a la base de dades per obtenir el nombre de paquets per a cada **devEUI**. Es suma la quantitat total de paquets a la variable **npaquetes**.

#### Paquetes Perdidos
- Es calculen les pèrdues de paquets restando els **paquetes esperados** dels **paquetes totales**. Si hi ha pèrdues, es calcula el percentatge de pèrdues i es guarda en **paquetes_perdidos**.

#### Actualització a la Base de Dades
- Es prepara una sentència SQL per desar el valor de la pèrdua de paquets i el període de temps en què es van perdre en la taula **paquetes_perdidos** de la base de dades. Aquest procés es repeteix per a cada **devEUI**.

