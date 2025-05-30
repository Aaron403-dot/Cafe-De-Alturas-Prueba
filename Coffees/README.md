# Café de Altura - Proyecto de Gestión de Cafetería

## Tareas Completadas ✅

1. **Estructura Base del Proyecto**
   - Configuración inicial del proyecto Spring Boot
   - Estructura de carpetas (Controllers, Services, Repositories, Entities)

2. **Entidades**
   - [x] Entidad Coffe (Café)
   - [x] Entidad Clients (Clientes)
   - [x] Entidad Orders (Pedidos)

3. **Repositorios**
   - [x] RepoCoffe
   - [x] RepoClient
   - [x] RepoOrder

4. **Servicios**
   - [x] ServiceCoffe
   - [x] ServiceClients
   - [x] ServiceOrders

5. **Controladores**
   - [x] CoffeShopController (CRUD completo para cafés)

## Tareas Pendientes 📝

1. **Controladores**
   - [X] ClientController
     - [X] GET /clients (Obtener lista de clientes)
     - [X] GET /clients/{id} (Obtener cliente por ID)
     - [X] POST /clients (Crear nuevo cliente)
     - [X] PUT /clients/{id} (Actualizar cliente)
     - [X] DELETE /clients/{id} (Eliminar cliente)
     - [X] PATCH /clients/{id} (Actualizar parcialmente cliente)

   - [X] OrderController
     - [X] GET /orders (Obtener lista de pedidos)
     - [X] GET /orders/{id} (Obtener pedido por ID)
     - [X] POST /orders (Crear nuevo pedido)
     - [X] PUT /orders/{id} (Actualizar pedido)
     - [X] DELETE /orders/{id} (Eliminar pedido)
     - [X] PATCH /orders/{id} (Actualizar parcialmente pedido)

2. **Validaciones**
   - [X] Implementar validaciones de datos en los controladores
   - [X] Manejo de excepciones personalizadas

3. **Documentación**
   - [ ] Documentar API con Swagger/OpenAPI
   - [X] Añadir comentarios en el código

4. **Testing**
   - [ ] Tests unitarios para servicios
   - [ ] Tests de integración para controladores

5. **Seguridad**
   - [X] Implementar autenticación
   - [X] Implementar autorización

6. **Despliegue**
   - [X] Configuración de base de datos
   - [ ] Scripts de despliegue
   - [ ] Documentación de despliegue
