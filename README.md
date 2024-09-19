# PDES-2024-APC
Aplicación Asesor Personal de Compras - PRÁCTICAS DE DESARROLLO DE SOFTWARE  2er Cuatrimestre – 2024

## Stack tecnológico
- Gradle
- Lenguaje Java 17
- Spring Boot 3.3.2
- Docker

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

Además, debemos generar un archivo .env con las credenciales necesarias para el proyecto. El archivo .env.example contiene las variables necesarias para completar.

### Procedimiento

Este comando levantara la base de datos y la aplicación java.
```
docker compose up
```

### En caso de que querramos ejecutar solo la aplicación java
```
./gradlew run
```

### En caso de que querramos ejecutar todos los test
```
./gradlew test
```
