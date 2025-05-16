# 🧩 Spring Boot + Docker Compose Deployment

Este proyecto contiene una aplicación Spring Boot que se puede desplegar fácilmente utilizando Docker Compose. El
entorno está diseñado para ser alojado en un servidor privado con Ubuntu Server.

## 📦 Requisitos

Antes de comenzar, asegúrate de tener los siguientes requisitos instalados en tu servidor Ubuntu:

- Git
- Docker
- Docker Compose

### Instalación de Docker y Docker Compose en Ubuntu

```bash
  sudo apt update
```

```bash
  sudo apt install -y docker.io docker-compose
```

```bash
  sudo systemctl enable docker
```

```bash
  sudo systemctl start docker
```

# 🐳 Verifica que Docker funcione correctamente

```bash
  docker --version
```

```bash
  docker-compose --version
```

## 🚀 Clonar el repositorio

Clona este repositorio en tu servidor:

```bash
  git clone https://github.com/Chris-THC/FTP-Project.git
```

```bash
  cd proyecto-springboot-docker
```

## ⚙️ Estructura del proyecto

```bash
.
├── docker-compose.yml
├── Dockerfile
├── src/
├── pom.xml
└── README.md
```

- `Dockerfile`: Contiene las instrucciones para construir la imagen de Spring Boot.
- `docker-compose.yml`: Orquesta el contenedor de Spring Boot y otros servicios (como base de datos, si aplica).
- `src/`: Código fuente de la aplicación.
- `pom.xml`: Archivo de configuración de Maven.

## 🏗️ Construir y levantar el proyecto

Desde la raíz del proyecto, ejecuta:

1. Inicia la aplicación usando Docker Compose:
   ```bash
   docker-compose up --build
1. Termina el proceso en la consola:
   ```bash
   Ctrl + C

1. Termina el proceso en docker:
   ```bash
   docker-compose down

Esto construirá la imagen de Spring Boot y levantará los servicios definidos en `docker-compose.yml`.

## 🐳 Verificar que el contenedor está corriendo

```bash
  docker ps
```

Deberías ver un contenedor en ejecución con el nombre definido en tu `docker-compose.yml`.

## 🌐 Acceder a la aplicación

La aplicación estará disponible en el puerto configurado (por defecto, `http://localhost:8080`).

Si estás accediendo desde una máquina distinta, asegúrate de que el puerto esté abierto en el firewall del servidor:

```bash
  sudo ufw allow 8080
```

## 🛠️ Comandos útiles

```bash
# Ver logs del contenedor
docker-compose logs -f

# Detener contenedores
docker-compose down

# Volver a levantar sin cache
docker-compose build --no-cache
docker-compose up -d
```

## 🛡️ Seguridad recomendada

- Usa un archivo `.env` para manejar variables sensibles como contraseñas y secretos.
- Considera usar HTTPS con NGINX como proxy inverso.
- Desactiva el acceso root por SSH y usa autenticación por llaves.

## 🤝 Contribuciones

Si deseas mejorar el proyecto, ¡eres bienvenido a hacer un fork y enviar un pull request!

## 📄 Licencia

Este proyecto está bajo la licencia MIT.