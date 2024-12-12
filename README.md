# PDES-2024-APC
Aplicación Asesor Personal de Compras - PRÁCTICAS DE DESARROLLO DE SOFTWARE  2er Cuatrimestre – 2024

## Stack tecnológico
- Gradle
- Lenguaje Java 17
- Spring Boot 3.3.2
- Docker
- K6
- Prometheus
- Grafana

## Instalación
```
git clone https://github.com/ivanJoshua98/PDES-2024-APC.git
```

## Backend 
```
cd backend-apc
```

## Setup 
### Prerequisitos
Para poder levantar la base de datos y la aplicación, se necesita tener [Docker](http://www.docker.com) instalado.

Nota en caso de usar Windows: Es mucho mas facil no instalar Docker de forma directa, sino instalarlo usando [Windows Subsystem for Linux](http://www.learn.microsoft.com/en-us/windows/wsl/install).

### Procedimiento

Este comando levantara la base de datos PostgreSQL, la aplicación Java, la aplicación React, un contenedor con Prometheus, un contenedor con K6 y un contenedor con Grafana.
```
docker compose up
```

### En caso de que querramos ejecutar solo la aplicación Java
En primer lugar deberiamos modificar la URL de conexion a la base de datos en el archivo application.properties, ya que la configuración por defecto apunta al contenedor Docker.
```
spring.datasource.url=jdbc:postgresql://<HOST>:5432/apc_dev
```

Comando para iniciar la aplicación Java de forma individual
```
./gradlew run
```

En caso de que querramos ejecutar todos los test
```
./gradlew test
```

## Datos de prueba

### Usuarios en la aplicación
En la aplicación aparecerán disponibles 7 usuarios.
- 6 usuarios de perfil comprador
- 1 usuario de perfil administrador

Los usuarios con perfil comprador tendrán productos agregados como favoritos

| Nombre de usuario | Email               | Contraseña   | Productos Favoritos                                               |
| ----------------- | ------------------- | ------------ | ----------------------------------------------------------------- |
| userBuyer1        | userBuyer1@mail.com | Credential.  | Auriculares, Computadora, Cocina, Heladera, Notebook, Smartphone  |
| userBuyer2        | userBuyer2@mail.com | Credential.  | Auriculares, Computadora, Cocina, Heladera, Notebook              |
| userBuyer3        | userBuyer3@mail.com | Credential.  | Auriculares, Computadora, Cocina, Heladera                        |
| userBuyer4        | userBuyer4@mail.com | Credential.  | Auriculares, Computadora, Cocina                                  |
| userBuyer5        | userBuyer5@mail.com | Credential.  | Auriculares, Computadora                                          |
| userBuyer6        | userBuyer6@mail.com | Credential.  | Auriculares                                                       |
| userAdmin         | userAdmin@mail.com  | Credential.  |                                                                   |

### Carritos de Compra en la Aplicación

En la aplicación aparecerán finalizados diferentes carritos en diferentes usuarios

Solo el userBuyer6 aparecerá con un carrito de compras en proceso

| Carrito de compra | Nombre de usuario | Productos                        | Estado       |
| ----------------- | ----------------- | -----------------                | ------------ |
| carritoAU1        | userBuyer1        | Notebook (x2)                    | Finalizado   | 
| carritoBU1        | userBuyer1        | SmartPhone (x2)                  | Finalizado   | 
| carritoCU1        | userBuyer1        | Heladera (x1)                    | Finalizado   | 
| carritoDU1        | userBuyer1        | Cocina (x1)                      | Finalizado   | 
| carritoEU1        | userBuyer1        | Computadora (x1)                 | Finalizado   | 
| carritoFU1        | userBuyer1        | Auriculares (x1)                 | Finalizado   | 
| carritoAU2        | userBuyer2        | Notebook (x1)                    | Finalizado   | 
| carritoBU2        | userBuyer2        | Smartphone (x1)                  | Finalizado   | 
| carritoCU2        | userBuyer2        | Heladera (x1)                    | Finalizado   | 
| carritoDU2        | userBuyer2        | Cocina (x1)                      | Finalizado   | 
| carritoEU2        | userBuyer2        | Computadora (x1)                 | Finalizado   | 
| carritoAU3        | userBuyer3        | Notebook (x1)                    | Finalizado   | 
| carritoBU3        | userBuyer3        | Smartphone (x1)                  | Finalizado   | 
| carritoCU3        | userBuyer3        | Heladera (x1)                    | Finalizado   | 
| carritoDU3        | userBuyer3        | Cocina (x1)                      | Finalizado   |
| carritoAU4        | userBuyer4        | Notebook (x1)                    | Finalizado   | 
| carritoBU4        | userBuyer4        | Smartphone (x1)                  | Finalizado   |
| carritoCU4        | userBuyer4        | Heladera (x1)                    | Finalizado   |
| carritoAU5        | userBuyer5        | Notebook (x1)                    | Finalizado   |
| carritoBU5        | userBuyer5        | Smartphone (x1)                  | Finalizado   | 
| carritoAU6        | userBuyer6        | Notebook (x1), Auriculares (x15) | Finalizado   |
| carritoBU6        | userBuyer6        | Smartphone (x1)                  | En proceso   |

### Reportes del sistema

Al tener estos datos iniciales de prueba, si accedemos como usuario administrador los reportes del sistema deben darnos los siguientes resultados:

Productos más comprados:
- 1ro: Auriculares
- 2ro: Notebook
- 3ro: Smartphone
- 4to: Heladera
- 5to: Cocina

Usuarios con más compras por carritos finalizados:
- 1ro: userBuyer1
- 2ro: userBuyer2
- 3ro: userBuyer3
- 4to: userBuyer4
- 5to: userBuyer5

Usuarios con más compras por productos comprados:
- 1ro: userBuyer6
- 2ro: userBuyer1
- 3ro: userBuyer2
- 4to: userBuyer3
- 5to: userBuyer4

Top 5 Productos Favoritos:
- 1ro: Auriculares
- 2do: Computadora
- 3ro: Cocina
- 4to: Heladera
- 5to: Notebook

## Test de Carga

Ubicación de los test:
```
cd k6/src
```

Si iniciamos la aplicación con Docker Compose, K6 estará en un contenedor. Dependiendo si utilizamos Windows o Linux, los comandos para ejecutar los test serán diferentes

Ejecución en Windows
```
Get-Content test.js | docker run --rm -i grafana/k6 run -
```

Ejecución en Linux
```
docker run --rm -i grafana/k6 run - <test.js
```

## Observabilidad

Si iniciamos la aplicación con Docker Compose, Grafana estará en un contenedor.

Podemos acceder a Grafana en el puerto 3000.

Debemos configurar la fuente de datos:

- En el menú desplegable ir a la opción ‘DATA SOURCES’
- Seleccionar Prometheus
- Especificar el origen de las métricas: http://prometheus:9090
- En el menú desplegable ir a la opción ‘DASHBOARDS’
- Se pueden importar o crear visualizaciones. Podemos importar un dashboard ya creado ubicado en la carpera 'grafana' del repositorio


