# MedTech - Sistema de Gestión de Citas Médicas

## 📋 Descripción
MedTech es una aplicación Java que busca solucionar la desorganización y errores en las citas médicas, proporcionando un sistema ordenado y organizado tanto para pacientes como médicos.

## 🎯 Objetivo
Crear una plataforma que permita:
- Gestión eficiente de citas médicas
- Reducción de errores en el proceso de agendamiento
- Organización centralizada de información médica
- Interfaz intuitiva para pacientes y médicos

## 🏗️ Arquitectura del Proyecto

### Clases Principales

#### 1. **Paciente**
**Atributos:**
- `nombre`: Nombre completo del paciente
- `rut`: RUT chileno (formato xx.xxx.xxx-x)
- `contraseña`: Contraseña para autenticación
- `email`: Correo electrónico
- `telefono`: Número telefónico
- `edad`: Edad del paciente
- `direccion`: Dirección residencial
- `citasAgendadas`: Lista de citas médicas agendadas
- `historialAtenciones`: Historial de atenciones médicas

**Funcionalidades:**
- ✅ Registro y autenticación
- ✅ Solicitud de citas médicas
- ✅ Modificación de datos personales
- ✅ Cancelación de citas
- ✅ Consulta de información de médicos
- ✅ Revisión de historial de atenciones
- ✅ Visualización de citas agendadas

#### 2. **Medico**
**Atributos:**
- `nombre`: Nombre completo del médico
- `rut`: RUT chileno
- `especialidad`: Especialidad médica
- `horariosDisponibles`: Lista de horarios disponibles
- `citasAgendadas`: Lista de citas agendadas
- `telefono`: Teléfono de contacto
- `email`: Correo electrónico

**Funcionalidades:**
- ✅ Gestión de horarios disponibles
- ✅ Visualización de citas agendadas
- ✅ Estadísticas de atención
- ✅ Cancelación y reagendamiento de citas

#### 3. **Cita**
**Atributos:**
- `paciente`: Referencia al paciente
- `medico`: Referencia al médico
- `fechaHora`: Fecha y hora de la cita
- `estado`: Estado actual de la cita
- `observaciones`: Notas adicionales

**Estados de Cita:**
- `AGENDADA`: Cita recién creada
- `CONFIRMADA`: Cita confirmada por el paciente
- `CANCELADA`: Cita cancelada
- `COMPLETADA`: Cita completada exitosamente
- `NO_ASISTIO`: Paciente no asistió

## 🚀 Casos de Uso Implementados

### 1. **Login Paciente**
- ✅ Validación de credenciales (RUT y contraseña)
- ✅ Verificación de paciente registrado
- ✅ Inicio de sesión exitoso

### 2. **Solicitar Cita Médica**
- ✅ Selección de especialidad médica
- ✅ Selección de médico disponible
- ✅ Visualización de horarios disponibles
- ✅ Validación de disponibilidad
- ✅ Confirmación de cita

### 3. **Cancelar Cita**
- ✅ Listado de citas agendadas
- ✅ Selección de cita a cancelar
- ✅ Validación de política de 24 horas
- ✅ Liberación de horario médico

### 4. **Modificar Datos Personales**
- ✅ Visualización de datos actuales
- ✅ Modificación de campos individuales
- ✅ Validación de datos ingresados
- ✅ Actualización en el sistema

## 📁 Estructura del Proyecto
```
MedTech/
├── src/
│   ├── MedTech.java      # Clase principal y menús
│   ├── Paciente.java     # Gestión de pacientes
│   ├── Medico.java       # Gestión de médicos
│   └── Cita.java         # Gestión de citas
└── README.md
```

## 🛠️ Requisitos del Sistema
- **Java JDK**: 11 o superior
- **Sistema Operativo**: Windows, macOS, Linux
- **Memoria**: Mínimo 512 MB RAM
- **Almacenamiento**: 10 MB de espacio libre

## ▶️ Cómo Ejecutar

### 1. Compilación
```bash
cd MedTech/src
javac *.java
```

### 2. Ejecución
```bash
java MedTech
```

## 🎮 Guía de Uso

### Menú Principal
1. **Registrarse como paciente**: Crear una nueva cuenta
2. **Iniciar sesión**: Acceder con RUT y contraseña
3. **Ver médicos disponibles**: Consultar médicos y especialidades
4. **Ayuda**: Información sobre el sistema
5. **Salir**: Cerrar la aplicación

### Menú Paciente (después del login)
1. **Solicitar cita médica**: Agendar nueva cita
2. **Ver mis citas agendadas**: Revisar citas programadas
3. **Cancelar cita**: Cancelar cita existente
4. **Modificar datos personales**: Actualizar información
5. **Consultar médicos**: Ver información de médicos
6. **Ver historial**: Revisar atenciones anteriores
7. **Ver datos personales**: Mostrar información personal
8. **Cerrar sesión**: Salir del menú paciente

## 🔐 Validaciones Implementadas

### RUT Chileno
- Formato válido: `xx.xxx.xxx-x`
- Validación de dígito verificador
- Verificación de unicidad en registro

### Contraseña
- Mínimo 6 caracteres
- Validación en registro y modificación

### Citas Médicas
- No superposición de horarios
- Validación de disponibilidad médica
- Política de cancelación 24 horas

## 📊 Datos de Prueba
El sistema incluye 5 médicos predefinidos:
- **Dr/a. Ana García López** - Cardiología
- **Dr/a. Carlos Rodríguez Silva** - Dermatología  
- **Dr/a. María Fernández Castro** - Pediatría
- **Dr/a. José Martínez Pérez** - Traumatología
- **Dr/a. Laura Sánchez Morales** - Ginecología

## ⚠️ Políticas del Sistema

### Cancelaciones
- ✅ Permitidas hasta 24 horas antes
- ⚠️ Sanción por cancelación tardía (< 24 horas)
- 🔄 Liberación automática de horario médico

### Horarios
- 📅 Disponibilidad: Lunes a Viernes
- ⏰ Horarios: 9:00, 10:00, 11:00, 14:00, 15:00, 16:00
- 📆 Rango: 30 días de anticipación

## 🚀 Características Destacadas

### Validación de RUT
```java
public static boolean validarRut(String rut) {
    // Implementación completa de validación de RUT chileno
    // Incluye verificación de formato y dígito verificador
}
```

### Gestión de Horarios
```java
private void inicializarHorarios() {
    // Genera automáticamente horarios disponibles
    // Solo días laborales con horarios establecidos
}
```

### Interfaz de Usuario
- 🎨 Menús intuitivos con emojis
- 📋 Información organizada y clara
- ✅ Confirmaciones y mensajes de estado
- ❌ Manejo de errores descriptivo

## 🔄 Flujos Principales

### Flujo de Registro
1. Solicitud de datos personales
2. Validación de RUT único
3. Validación de contraseña
4. Creación de cuenta exitosa

### Flujo de Cita
1. Login del paciente
2. Selección de especialidad
3. Selección de médico
4. Selección de horario
5. Confirmación de cita

### Flujo de Cancelación
1. Visualización de citas
2. Selección de cita a cancelar
3. Verificación de política 24h
4. Confirmación de cancelación

## 📈 Escalabilidad Futura

### Funcionalidades Pendientes
- 🔔 Sistema de notificaciones
- 📧 Envío de recordatorios por email
- 💾 Persistencia en base de datos
- 🌐 Interfaz web
- 📱 Aplicación móvil
- 👨‍⚕️ Panel administrativo para médicos

### Mejoras Técnicas
- 🔒 Encriptación de contraseñas
- 🎯 Patrón MVC
- 🗄️ Integración con base de datos
- 🔄 API REST
- 📊 Reportes y analytics

## 👨‍💻 Desarrollo
- **Lenguaje**: Java
- **Paradigma**: Programación Orientada a Objetos
- **Patrón**: Modelo-Vista-Controlador (básico)
- **Estructura**: Clases independientes con responsabilidades específicas

---
**MedTech v1.0** - Sistema de Gestión de Citas Médicas  
*Desarrollado para mejorar la organización y eficiencia en la atención médica* 🏥