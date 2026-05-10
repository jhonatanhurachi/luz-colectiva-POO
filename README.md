# Luz Colectiva

Aplicación para la gestión de la luz colectiva de la Urbanización de Interés Social Embajada de Japón Sector B. El sistema permite organizar el registro de clientes, lecturas mensuales de medidor, cálculo de consumo eléctrico, generación de recibos internos, control de pagos, cargos extraordinarios, cortes, reconexiones y reportes básicos de cobranza.

## Requisitos

- [JDK 17](https://adoptium.net/) 
- [Maven 3.9+](https://maven.apache.org/download.cgi)  
- [Docker](https://docs.docker.com/get-docker/) (para MySQL)

## Base de datos (Docker)

Desde el root del proyecto:

```bash
docker compose up -d
```

Eso construye la imagen definida en `docker/mysql/Dockerfile` y aplica los scripts en `docker/mysql/init/`:

1. `01_users_and_profiles.sql` — tablas `users` y `profiles` (identidad local, sin Supabase Auth).  
2. `02_domain_tables.sql` — resto del esquema (`customers`, `receipts`, `payments`, etc.).

Credenciales por defecto (solo desarrollo):

| Variable | Valor |
|----------|--------|
| Base de datos | `luz_colectiva` |
| Usuario app | `luzcolectiva` / `luzcolectiva` |
| Root | `root` / `root` |

**Nota:** los scripts de `init` solo se ejecutan la **primera vez** que se crea el volumen de datos. Si cambias el SQL y necesitas recrear todo:

```bash
docker compose down -v
docker compose up -d
```

### Seed de demostración (datos de prueba)

Los scripts en `docker/mysql/init/` **solo** crean el esquema y la fila inicial de `app_settings`. **No** cargan clientes, recibos ni el resto de datos de ejemplo.

El archivo `docker/mysql/seed/demo_seed.sql` añade usuarios, clientes, lecturas, factura proveedor, recibos, ítems, pagos, cargos extra y eventos de servicio. Ese archivo **no** se copia al contenedor: hay que ejecutarlo **a mano**.

**Requisito:** el contenedor MySQL debe estar en marcha (`docker compose up -d`).

PowerShell:

```powershell
Get-Content -Raw .\docker\mysql\seed\demo_seed.sql | docker exec -i luz-colectiva-mysql mysql -uluzcolectiva -pluzcolectiva luz_colectiva
```

Bash:

```bash
docker exec -i luz-colectiva-mysql mysql -uluzcolectiva -pluzcolectiva luz_colectiva < docker/mysql/seed/demo_seed.sql
```

El seed **borra** antes las filas de demostración que él mismo define (usuarios fijos, clientes, recibos, etc.) y las vuelve a insertar, así se puede ejecutar varias veces sin duplicar PKs. No toca la fila `app_settings` salvo `updated_by` al usuario admin de demo.

## Configuración JDBC

1. Copia el ejemplo de propiedades:

   ```bash
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ```

2. Ajusta `db.url`, `db.user` y `db.password` si no usas los valores por defecto.

## Compilar y ejecutar

```bash
mvn -q compile
```

Ejecutar la clase principal **con el classpath de Maven** (incluye el driver MySQL):

```bash
mvn -q exec:java
```

Sin argumentos solo muestra la **ayuda** de la CLI. Para listar datos, pasa un comando con `-Dexec.args`.

### CLI de listados

Comandos disponibles: `ayuda`, `clientes`, `usuarios`, `recibos`, `lecturas <uuid-cliente>`, `config`.

**Bash / cmd (un solo argumento):**

```bash
mvn -q exec:java -Dexec.args=clientes
mvn -q exec:java -Dexec.args=config
```

**PowerShell** (entrecomillar el parámetro `-D…`):

```powershell
mvn -q exec:java "-Dexec.args=clientes"
mvn -q exec:java "-Dexec.args=lecturas aaaaaaaa-aaaa-aaaa-aaaa-000000000001"
```

Con el **seed de demo**, un UUID de cliente válido es por ejemplo `aaaaaaaa-aaaa-aaaa-aaaa-000000000001` (Juan Pérez).

## Estructura del código

| Paquete / carpeta | Contenido |
|-------------------|-----------|
| `com.luzcolectiva.modelo` | Entidades (POJOs) alineadas a tablas |
| `com.luzcolectiva.dao` | Interfaces DAO y `DaoFactory` |
| `com.luzcolectiva.dao.impl` | Implementaciones JDBC (`*DaoJdbc`) |
| `com.luzcolectiva.dao.jdbc` | `RowMappers` (`ResultSet` → modelo) |
| `com.luzcolectiva.util` | `JdbcConfig`, `JdbcSupport` |
| `com.luzcolectiva.validation` | Reglas que en MySQL no van en CHECK (p. ej. `CustomerServiceEventRules`) |
| `com.luzcolectiva.app` | `Main`, `ListCli` (listados por consola) |

Los errores de acceso a datos se envuelven en `com.luzcolectiva.DaoException`.

## Probar la conexión y los DAO

Con MySQL en marcha, datos de seed cargados y JDBC configurado, usa por ejemplo:

```bash
mvn -q exec:java -Dexec.args=config
```

Ver la configuración global. Otros listados: `clientes`, `usuarios`, `recibos`, `lecturas <uuid>`.
