# Sistema de Inventario Dental

## Descripción del proyecto

Este proyecto corresponde a un sistema de gestión de inventario dental desarrollado bajo una arquitectura de microservicios con Spring Boot. Su objetivo es administrar insumos dentales, proveedores, ingresos, egresos, stock, usuarios, alertas, reportes y destinos de despacho.

El sistema permite registrar movimientos de inventario, controlar stock disponible, consultar información de insumos y centralizar las solicitudes mediante un API Gateway.

## Integrantes

* Debora Bravo
* Diego Rojas

## Tecnologías utilizadas

* Java 21
* Spring Boot
* Spring Cloud
* Eureka Server
* Spring Cloud Gateway
* OpenFeign
* Spring Data JPA
* MySQL
* Flyway
* Maven
* Postman
* GitHub

## Arquitectura del sistema

El proyecto está compuesto por microservicios independientes que se comunican mediante API REST y Feign Client. Eureka Server permite el descubrimiento de servicios y API Gateway centraliza el acceso a los endpoints.

Flujo general:

Cliente / Postman
→ API Gateway
→ Microservicio correspondiente
→ Base de datos propia del servicio

## Microservicios implementados

  Microservicio      Puerto       Descripción                             
   
  Eureka Server        8761   Servidor de descubrimiento de servicios  
  Gateway Service      8080   Punto de entrada centralizado            
  Insumo Service       8081   Gestión de insumos dentales              
  Proveedor Service    8082   Gestión de proveedores                   
  Destino Service      8083   Gestión de destinos                      
  Egreso Service       8084   Gestión de egresos de insumos            
  Ingreso Service      8085   Gestión de ingresos de insumos           
  Stock Service        8086   Gestión de stock disponible              
  Alerta Service       8087   Gestión de alertas                       
  Reporte Service      8088   Gestión de reportes                      
  Usuario Service      8089   Gestión de usuarios                     

## Rutas principales mediante API Gateway

Todas las solicitudes se realizan desde el puerto 8080.

  Servicio      Ruta Gateway                             

  Insumos       `http://localhost:8080/api/insumos`      
  Proveedores   `http://localhost:8080/api/proveedores`  
  Destinos      `http://localhost:8080/api/destinos`     
  Egresos       `http://localhost:8080/api/egresos`      
  Egresos V2    `http://localhost:8080/api/v2/egresos`   
  Ingresos      `http://localhost:8080/api/ingresos`     
  Stock         `http://localhost:8080/api/stock`        
  Alertas       `http://localhost:8080/api/alertas`      
  Reportes      `http://localhost:8080/api/reportes`     
  Usuarios      `http://localhost:8080/api/usuarios`    

## Comunicación entre microservicios

El sistema utiliza OpenFeign para la comunicación REST entre servicios.

Ejemplos:

* `Ingreso Service` consulta `Insumo Service`.
* `Ingreso Service` consulta `Proveedor Service`.
* `Ingreso Service` actualiza `Stock Service`.
* `Egreso Service` consulta `Insumo Service`.
* `Egreso Service` consulta `Destino Service`.
* `Egreso Service` actualiza `Stock Service`.

Ejemplo de flujo:

Al registrar un ingreso de insumos, `Ingreso Service` guarda el ingreso y luego se comunica con `Stock Service` para actualizar la cantidad disponible del insumo.

## Ejemplo de prueba en Postman

### Crear ingreso

Método:

```http
POST http://localhost:8080/api/ingresos
```

Body:

```json
{
  "cantidad": 1,
  "numeroFactura": "FAC-PRUEBA-001",
  "observacion": "Prueba Feign Eureka",
  "insumoId": 1,
  "proveedorId": 1
}
```

Respuesta esperada:

```http
200 OK
```

o

```http
201 Created
```

## Swagger / OpenAPI

La documentación Swagger estará disponible en los microservicios configurados mediante las siguientes rutas locales:

```txt
http://localhost:{puerto}/swagger-ui/index.html
```

Ejemplo:

```txt
http://localhost:8084/swagger-ui/index.html
```

## Ejecución local


### Orden recomendado de ejecución

Para probar el flujo de ingreso de insumos:

1. Eureka Server
2. Gateway Service
3. Insumo Service
4. Proveedor Service
5. Stock Service
6. Ingreso Service

Para levantar todo el ecosistema:

1. Eureka Server
2. Gateway Service
3. Insumo Service
4. Proveedor Service
5. Destino Service
6. Stock Service
7. Usuario Service
8. Alerta Service
9. Reporte Service
10. Ingreso Service
11. Egreso Service

Luego verificar Eureka en:

```txt
http://localhost:8761
```

Los servicios deben aparecer con estado `UP`.

## Bases de datos

Cada microservicio utiliza su propia base de datos MySQL.

Ejemplos:

* `insumo_db`
* `proveedor_db`
* `stock_db`
* `ingreso_db`
* `egreso_db`
* `destino_db`
* `usuario_db`
* `alerta_db`
* `reporte_db`

## Control de versiones

El proyecto se encuentra versionado en GitHub. Se trabajó mediante commits técnicos y progresivos para evidenciar el avance del desarrollo.

Ejemplos de commits realizados:

* Agrega Eureka Server para descubrimiento de microservicios.
* Registra microservicios en Eureka.
* Agrega API Gateway con enrutamiento mediante Eureka.
* Migra Feign de egreso para usar Eureka.
* Implementa descubrimiento con Eureka, rutas Gateway y comunicación Feign entre ingreso, stock, insumo y proveedor.

## Estado actual

El sistema permite ejecutar microservicios de forma local, registrar servicios en Eureka, centralizar rutas mediante Gateway y probar comunicación REST entre microservicios usando Feign Client.

El flujo de ingreso de insumos fue validado mediante Postman, comprobando la actualización del stock a través de la comunicación entre `Ingreso Service` y `Stock Service`.
