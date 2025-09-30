import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Paciente - Representa un paciente en el sistema MedTech
 * Maneja el registro, autenticacion y gestion de citas medicas
 */
public class Paciente {
    // Atributos principales
    private String nombre;
    private String rut;
    private String contrasena;
    private String email;
    private String telefono;
    private int edad;
    private String direccion;
    private ArrayList<Cita> citasAgendadas;
    private ArrayList<String> historialAtenciones;
    
    // Constructor
    public Paciente(String nombre, String rut, String contrasena, String email, String telefono, int edad, String direccion) {
        this.nombre = nombre;
        this.rut = rut;
        this.contrasena = contrasena;
        this.email = email;
        this.telefono = telefono;
        this.edad = edad;
        this.direccion = direccion;
        this.citasAgendadas = new ArrayList<>();
        this.historialAtenciones = new ArrayList<>();
    }
    
    // Constructor basico
    public Paciente(String nombre, String rut, String contrasena) {
        this(nombre, rut, contrasena, "", "", 0, "");
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public ArrayList<Cita> getCitasAgendadas() { return citasAgendadas; }
    public ArrayList<String> getHistorialAtenciones() { return historialAtenciones; }
    
    /**
     * Metodo auxiliar para leer entrada de forma segura
     */
    private static String leerEntradaSegura(Scanner scanner) {
        try {
            if (!scanner.hasNextLine()) {
                return null;
            }
            return scanner.nextLine().trim();
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Valida el formato del RUT chileno (xx.xxx.xxx-x)
     */
    public static boolean validarRut(String rut) {
        if (rut == null || rut.length() < 11 || rut.length() > 12) {
            return false;
        }
        
        // Verificar formato basico
        String patron = "\\d{1,2}\\.\\d{3}\\.\\d{3}-[0-9kK]";
        if (!rut.matches(patron)) {
            return false;
        }
        
        // Extraer numero y digito verificador
        String[] partes = rut.split("-");
        String numero = partes[0].replace(".", "");
        char dv = partes[1].toLowerCase().charAt(0);
        
        // Calcular digito verificador
        int suma = 0;
        int multiplicador = 2;
        
        for (int i = numero.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(numero.charAt(i)) * multiplicador;
            multiplicador = multiplicador == 7 ? 2 : multiplicador + 1;
        }
        
        int resto = 11 - (suma % 11);
        char dvCalculado;
        
        if (resto == 11) dvCalculado = '0';
        else if (resto == 10) dvCalculado = 'k';
        else dvCalculado = Character.forDigit(resto, 10);
        
        return dv == dvCalculado;
    }
    
    /**
     * Formatea el nombre correctamente
     */
    public static String formatearNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }
        
        String[] palabras = nombre.trim().toLowerCase().split("\\s+");
        if (palabras.length < 2) {
            return null;
        }
        
        StringBuilder nombreFormateado = new StringBuilder();
        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i];
            if (palabra.length() > 0) {
                nombreFormateado.append(palabra.substring(0, 1).toUpperCase())
                               .append(palabra.substring(1).toLowerCase());
                if (i < palabras.length - 1) {
                    nombreFormateado.append(" ");
                }
            }
        }
        
        return nombreFormateado.toString();
    }
    
    /**
     * Registra un nuevo paciente en el sistema - VERSION SIMPLIFICADA
     */
    public static Paciente registrarPaciente(ArrayList<Paciente> pacientesRegistrados) {
        Scanner scanner = MedTech.scanner;
        
        System.out.println("=== REGISTRO DE PACIENTE ===");
        System.out.println("NOTA: Para pruebas, ingrese datos basicos o presione Ctrl+C para cancelar");
        
        try {
            // RUT
            System.out.print("Ingrese su RUT (formato: xx.xxx.xxx-x): ");
            String rut = leerEntradaSegura(scanner);
            if (rut == null || rut.isEmpty()) {
                System.out.println("Registro cancelado.");
                return null;
            }
            
            if (!validarRut(rut)) {
                System.out.println("RUT invalido. Registro cancelado.");
                return null;
            }
            
            // Verificar RUT unico
            for (Paciente p : pacientesRegistrados) {
                if (p.getRut().equals(rut)) {
                    System.out.println("RUT ya registrado.");
                    return null;
                }
            }
            
            // Nombre
            System.out.print("Ingrese su nombre completo: ");
            String nombreInput = leerEntradaSegura(scanner);
            if (nombreInput == null || nombreInput.isEmpty()) {
                System.out.println("Registro cancelado.");
                return null;
            }
            
            String nombre = formatearNombre(nombreInput);
            if (nombre == null) {
                System.out.println("Nombre invalido. Debe tener nombre y apellido.");
                return null;
            }
            
            // Contrasena
            System.out.print("Ingrese su contrasena (minimo 6 caracteres): ");
            String contrasena = leerEntradaSegura(scanner);
            if (contrasena == null || contrasena.length() < 6) {
                System.out.println("Contrasena invalida. Debe tener al menos 6 caracteres.");
                return null;
            }
            
            // Edad
            System.out.print("Ingrese su edad: ");
            String edadStr = leerEntradaSegura(scanner);
            int edad = 25; // Valor por defecto
            try {
                if (edadStr != null && !edadStr.isEmpty()) {
                    edad = Integer.parseInt(edadStr);
                }
            } catch (NumberFormatException e) {
                System.out.println("Edad invalida, usando 25 por defecto.");
            }
            
            Paciente nuevoPaciente = new Paciente(nombre, rut, contrasena, "", "", edad, "");
            pacientesRegistrados.add(nuevoPaciente);
            
            System.out.println("Paciente registrado exitosamente!");
            return nuevoPaciente;
            
        } catch (Exception e) {
            System.out.println("Error en el registro: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Autentica un paciente con RUT y contrasena
     */
    public static Paciente login(ArrayList<Paciente> pacientesRegistrados) {
        Scanner scanner = MedTech.scanner;
        
        System.out.println("=== LOGIN PACIENTE ===");
        
        try {
            System.out.print("Ingrese su RUT: ");
            String rut = leerEntradaSegura(scanner);
            if (rut == null) {
                System.out.println("Login cancelado.");
                return null;
            }
            
            System.out.print("Ingrese su contrasena: ");
            String contrasena = leerEntradaSegura(scanner);
            if (contrasena == null) {
                System.out.println("Login cancelado.");
                return null;
            }
            
            for (Paciente paciente : pacientesRegistrados) {
                if (paciente.getRut().equals(rut) && paciente.getContrasena().equals(contrasena)) {
                    System.out.println("Bienvenido/a " + paciente.getNombre());
                    return paciente;
                }
            }
            
            System.out.println("Credenciales invalidas.");
            return null;
            
        } catch (Exception e) {
            System.out.println("Error en el login: " + e.getMessage());
            return null;
        }
    }
    
    // Metodo para solicitar cita medica
    public void solicitarCita(ArrayList<Medico> medicos) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SOLICITAR CITA MEDICA");
        System.out.println("=".repeat(50));
        
        if (medicos.isEmpty()) {
            System.out.println("No hay medicos disponibles en el sistema.");
            return;
        }
        
        // Mostrar especialidades disponibles
        Map<String, List<Medico>> medicosPorEspecialidad = new HashMap<>();
        for (Medico medico : medicos) {
            String especialidad = medico.getEspecialidad();
            if (!medicosPorEspecialidad.containsKey(especialidad)) {
                medicosPorEspecialidad.put(especialidad, new ArrayList<>());
            }
            medicosPorEspecialidad.get(especialidad).add(medico);
        }
        
        System.out.println("Especialidades disponibles:");
        List<String> especialidades = new ArrayList<>(medicosPorEspecialidad.keySet());
        for (int i = 0; i < especialidades.size(); i++) {
            System.out.println((i + 1) + ". " + especialidades.get(i));
        }
        
        System.out.print("Seleccione una especialidad (numero): ");
        String input = leerEntradaSegura(MedTech.scanner);
        if (input == null) return;
        
        try {
            int opcionEspecialidad = Integer.parseInt(input.trim());
            if (opcionEspecialidad < 1 || opcionEspecialidad > especialidades.size()) {
                System.out.println("Opcion invalida.");
                return;
            }
            
            String especialidadSeleccionada = especialidades.get(opcionEspecialidad - 1);
            List<Medico> medicosEspecialidad = medicosPorEspecialidad.get(especialidadSeleccionada);
            
            System.out.println("\nMedicos disponibles en " + especialidadSeleccionada + ":");
            for (int i = 0; i < medicosEspecialidad.size(); i++) {
                Medico medico = medicosEspecialidad.get(i);
                System.out.println((i + 1) + ". Dr/a. " + medico.getNombre());
                System.out.println("   Horarios disponibles: " + medico.getHorariosDisponibles().size());
            }
            
            System.out.print("Seleccione un medico (numero): ");
            input = leerEntradaSegura(MedTech.scanner);
            if (input == null) return;
            
            int opcionMedico = Integer.parseInt(input.trim());
            if (opcionMedico < 1 || opcionMedico > medicosEspecialidad.size()) {
                System.out.println("Opcion invalida.");
                return;
            }
            
            Medico medicoSeleccionado = medicosEspecialidad.get(opcionMedico - 1);
            
            if (medicoSeleccionado.getHorariosDisponibles().isEmpty()) {
                System.out.println("El medico seleccionado no tiene horarios disponibles.");
                return;
            }
            
            System.out.println("\nHorarios disponibles para Dr/a. " + medicoSeleccionado.getNombre() + ":");
            List<LocalDateTime> horarios = medicoSeleccionado.getHorariosDisponibles();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            
            for (int i = 0; i < Math.min(horarios.size(), 10); i++) {
                System.out.println((i + 1) + ". " + horarios.get(i).format(formatter));
            }
            
            System.out.print("Seleccione un horario (numero): ");
            input = leerEntradaSegura(MedTech.scanner);
            if (input == null) return;
            
            int opcionHorario = Integer.parseInt(input.trim());
            if (opcionHorario < 1 || opcionHorario > Math.min(horarios.size(), 10)) {
                System.out.println("Opcion invalida.");
                return;
            }
            
            LocalDateTime horarioSeleccionado = horarios.get(opcionHorario - 1);
            
            // Crear la cita
            Cita nuevaCita = new Cita(this, medicoSeleccionado, horarioSeleccionado);
            citasAgendadas.add(nuevaCita);
            
            // Remover el horario del medico
            medicoSeleccionado.getHorariosDisponibles().remove(horarioSeleccionado);
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("CITA AGENDADA EXITOSAMENTE");
            System.out.println("=".repeat(50));
            System.out.println("Paciente: " + this.nombre);
            System.out.println("Medico: Dr/a. " + medicoSeleccionado.getNombre());
            System.out.println("Especialidad: " + medicoSeleccionado.getEspecialidad());
            System.out.println("Fecha y Hora: " + horarioSeleccionado.format(formatter));
            System.out.println("=".repeat(50));
            
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un numero valido.");
        }
    }
    
    public void verCitasAgendadas() {
        if (citasAgendadas.isEmpty()) {
            System.out.println("No tiene citas agendadas");
            return;
        }
        
        System.out.println("=== SUS CITAS AGENDADAS ===");
        for (int i = 0; i < citasAgendadas.size(); i++) {
            Cita cita = citasAgendadas.get(i);
            System.out.println("Cita " + (i + 1) + ": " + cita.getMedico().getNombre());
        }
    }
    
    public void cancelarCita() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("CANCELAR CITA MEDICA");
        System.out.println("=".repeat(50));
        
        if (citasAgendadas.isEmpty()) {
            System.out.println("No tiene citas agendadas para cancelar.");
            return;
        }
        
        System.out.println("Sus citas agendadas:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (int i = 0; i < citasAgendadas.size(); i++) {
            Cita cita = citasAgendadas.get(i);
            System.out.println((i + 1) + ". Dr/a. " + cita.getMedico().getNombre());
            System.out.println("   Especialidad: " + cita.getMedico().getEspecialidad());
            System.out.println("   Fecha y Hora: " + cita.getFechaHora().format(formatter));
            System.out.println();
        }
        
        System.out.print("Seleccione el numero de la cita a cancelar (0 para volver): ");
        String input = leerEntradaSegura(MedTech.scanner);
        if (input == null) return;
        
        try {
            int opcion = Integer.parseInt(input.trim());
            
            if (opcion == 0) {
                System.out.println("Operacion cancelada.");
                return;
            }
            
            if (opcion < 1 || opcion > citasAgendadas.size()) {
                System.out.println("Opcion invalida.");
                return;
            }
            
            Cita citaACancelar = citasAgendadas.get(opcion - 1);
            
            // Verificar si quedan más de 24 horas para la cita
            LocalDateTime ahora = LocalDateTime.now();
            LocalDateTime fechaCita = citaACancelar.getFechaHora();
            long horasRestantes = java.time.Duration.between(ahora, fechaCita).toHours();
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("CONFIRMACION DE CANCELACION");
            System.out.println("=".repeat(50));
            System.out.println("Cita a cancelar:");
            System.out.println("Dr/a. " + citaACancelar.getMedico().getNombre());
            System.out.println("Especialidad: " + citaACancelar.getMedico().getEspecialidad());
            System.out.println("Fecha y Hora: " + fechaCita.format(formatter));
            
            if (horasRestantes < 24) {
                System.out.println("\n¡ATENCION! Esta cita se realizara en menos de 24 horas.");
                System.out.println("La cancelacion puede generar una sancion.");
            }
            
            System.out.print("\n¿Confirma la cancelacion? (s/n): ");
            input = leerEntradaSegura(MedTech.scanner);
            if (input == null) return;
            
            if (input.trim().toLowerCase().equals("s")) {
                // Devolver el horario al medico
                citaACancelar.getMedico().getHorariosDisponibles().add(fechaCita);
                
                // Remover la cita de la lista
                citasAgendadas.remove(citaACancelar);
                
                System.out.println("\n" + "=".repeat(50));
                System.out.println("CITA CANCELADA EXITOSAMENTE");
                System.out.println("=".repeat(50));
                System.out.println("La cita con Dr/a. " + citaACancelar.getMedico().getNombre());
                System.out.println("ha sido cancelada correctamente.");
                
                if (horasRestantes < 24) {
                    System.out.println("\nNOTA: Se ha registrado una cancelacion tardía en su historial.");
                    historialAtenciones.add("CANCELACION TARDIA: " + fechaCita.format(formatter) + 
                                          " - Dr/a. " + citaACancelar.getMedico().getNombre());
                } else {
                    historialAtenciones.add("CANCELACION: " + fechaCita.format(formatter) + 
                                          " - Dr/a. " + citaACancelar.getMedico().getNombre());
                }
                
                System.out.println("El horario ha sido liberado para otros pacientes.");
            } else {
                System.out.println("Cancelacion no confirmada. La cita se mantiene agendada.");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un numero valido.");
        }
    }
    
    public void modificarDatosPersonales() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MODIFICAR DATOS PERSONALES");
        System.out.println("=".repeat(50));
        
        System.out.println("Datos actuales:");
        mostrarDatosPersonales();
        
        System.out.println("\n¿Que desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Email");
        System.out.println("3. Telefono");
        System.out.println("4. Edad");
        System.out.println("5. Direccion");
        System.out.println("6. Contrasena");
        System.out.println("0. Volver al menu anterior");
        
        System.out.print("Seleccione una opcion: ");
        String input = leerEntradaSegura(MedTech.scanner);
        if (input == null) return;
        
        try {
            int opcion = Integer.parseInt(input.trim());
            
            switch (opcion) {
                case 0:
                    return;
                case 1:
                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = leerEntradaSegura(MedTech.scanner);
                    if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                        this.nombre = nuevoNombre.trim();
                        System.out.println("Nombre actualizado correctamente.");
                    }
                    break;
                case 2:
                    System.out.print("Nuevo email: ");
                    String nuevoEmail = leerEntradaSegura(MedTech.scanner);
                    if (nuevoEmail != null) {
                        this.email = nuevoEmail.trim();
                        System.out.println("Email actualizado correctamente.");
                    }
                    break;
                case 3:
                    System.out.print("Nuevo telefono: ");
                    String nuevoTelefono = leerEntradaSegura(MedTech.scanner);
                    if (nuevoTelefono != null) {
                        this.telefono = nuevoTelefono.trim();
                        System.out.println("Telefono actualizado correctamente.");
                    }
                    break;
                case 4:
                    System.out.print("Nueva edad: ");
                    String edadStr = leerEntradaSegura(MedTech.scanner);
                    if (edadStr != null) {
                        try {
                            int nuevaEdad = Integer.parseInt(edadStr.trim());
                            if (nuevaEdad > 0 && nuevaEdad < 120) {
                                this.edad = nuevaEdad;
                                System.out.println("Edad actualizada correctamente.");
                            } else {
                                System.out.println("Edad invalida.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Por favor ingrese un numero valido para la edad.");
                        }
                    }
                    break;
                case 5:
                    System.out.print("Nueva direccion: ");
                    String nuevaDireccion = leerEntradaSegura(MedTech.scanner);
                    if (nuevaDireccion != null) {
                        this.direccion = nuevaDireccion.trim();
                        System.out.println("Direccion actualizada correctamente.");
                    }
                    break;
                case 6:
                    System.out.print("Nueva contrasena (minimo 6 caracteres): ");
                    String nuevaContrasena = leerEntradaSegura(MedTech.scanner);
                    if (nuevaContrasena != null && nuevaContrasena.length() >= 6) {
                        this.contrasena = nuevaContrasena;
                        System.out.println("Contrasena actualizada correctamente.");
                    } else {
                        System.out.println("La contrasena debe tener al menos 6 caracteres.");
                    }
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor ingrese un numero valido.");
        }
    }
    
    public void consultarMedicos(ArrayList<Medico> medicos) {
        System.out.println("=== INFORMACION DE MEDICOS ===");
        for (Medico medico : medicos) {
            System.out.println("Dr/a. " + medico.getNombre() + " - " + medico.getEspecialidad());
        }
    }
    
    public void verHistorialAtenciones() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("HISTORIAL DE ATENCIONES MEDICAS");
        System.out.println("=".repeat(50));
        
        if (historialAtenciones.isEmpty()) {
            System.out.println("No tiene registros en su historial de atenciones.");
            System.out.println("El historial se actualizara cuando:");
            System.out.println("• Complete una cita medica");
            System.out.println("• Cancele una cita");
            System.out.println("• Realice cambios importantes en su perfil");
        } else {
            System.out.println("Registros encontrados: " + historialAtenciones.size());
            System.out.println("-".repeat(50));
            for (int i = 0; i < historialAtenciones.size(); i++) {
                System.out.println((i + 1) + ". " + historialAtenciones.get(i));
            }
            System.out.println("-".repeat(50));
            System.out.println("Total de registros: " + historialAtenciones.size());
        }
    }
    
    public boolean eliminarPerfil(ArrayList<Paciente> pacientesRegistrados, ArrayList<Medico> medicos) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ELIMINAR PERFIL DE PACIENTE");
        System.out.println("=".repeat(50));
        
        // Verificar si tiene citas agendadas
        if (!citasAgendadas.isEmpty()) {
            System.out.println("¡ATENCION! No puede eliminar su perfil mientras tenga citas agendadas.");
            System.out.println("Citas pendientes: " + citasAgendadas.size());
            System.out.println("\nPrimero debe:");
            System.out.println("1. Cancelar todas sus citas agendadas, o");
            System.out.println("2. Esperar a que se completen sus citas");
            System.out.println("\nOperacion cancelada.");
            return false;
        }
        
        System.out.println("¡ADVERTENCIA! Esta accion es IRREVERSIBLE.");
        System.out.println("Al eliminar su perfil se perdera toda la informacion:");
        System.out.println("• Datos personales");
        System.out.println("• Historial de atenciones");
        System.out.println("• Registro en el sistema");
        
        System.out.println("\nDatos del perfil a eliminar:");
        System.out.println("Nombre: " + this.nombre);
        System.out.println("RUT: " + this.rut);
        System.out.println("Historial de atenciones: " + this.historialAtenciones.size() + " registros");
        
        System.out.print("\n¿Esta COMPLETAMENTE SEGURO de eliminar su perfil? (escriba 'ELIMINAR' para confirmar): ");
        String confirmacion = leerEntradaSegura(MedTech.scanner);
        if (confirmacion == null) return false;
        
        if (!confirmacion.trim().equals("ELIMINAR")) {
            System.out.println("\nConfirmacion incorrecta. Operacion cancelada.");
            System.out.println("Su perfil no ha sido eliminado.");
            return false;
        }
        
        System.out.print("\nConfirmacion final. Ingrese su contrasena para proceder: ");
        String contrasenaConfirm = leerEntradaSegura(MedTech.scanner);
        if (contrasenaConfirm == null) return false;
        
        if (!contrasenaConfirm.equals(this.contrasena)) {
            System.out.println("\nContrasena incorrecta. Operacion cancelada por seguridad.");
            return false;
        }
        
        // Proceder con la eliminacion
        System.out.println("\n" + "=".repeat(50));
        System.out.println("PROCESANDO ELIMINACION...");
        System.out.println("=".repeat(50));
        
        // Liberar cualquier horario que pudiera estar reservado (por seguridad)
        for (Cita cita : citasAgendadas) {
            cita.getMedico().getHorariosDisponibles().add(cita.getFechaHora());
        }
        
        // Eliminar el paciente de la lista
        boolean eliminado = pacientesRegistrados.remove(this);
        
        if (eliminado) {
            System.out.println("✓ Perfil eliminado exitosamente del sistema.");
            System.out.println("✓ Todos los datos han sido removidos permanentemente.");
            System.out.println("✓ Los horarios reservados han sido liberados.");
            
            System.out.println("\n" + "=".repeat(50));
            System.out.println("PERFIL ELIMINADO COMPLETAMENTE");
            System.out.println("Gracias por usar MedTech.");
            System.out.println("Sera redirigido al menu principal...");
            System.out.println("=".repeat(50));
            
            // Pausa breve para que el usuario lea el mensaje
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                // Ignorar interrupcion
            }
            
            return true; // Indica que se debe salir del menu de paciente
        } else {
            System.out.println("✗ Error al eliminar el perfil. Intente nuevamente.");
            return false;
        }
    }
    
    public void mostrarDatosPersonales() {
        System.out.println("Nombre: " + nombre);
        System.out.println("RUT: " + rut);
        System.out.println("Email: " + (email.isEmpty() ? "No registrado" : email));
        System.out.println("Telefono: " + (telefono.isEmpty() ? "No registrado" : telefono));
        System.out.println("Edad: " + edad + " anos");
        System.out.println("Direccion: " + (direccion.isEmpty() ? "No registrada" : direccion));
        System.out.println("Citas agendadas: " + citasAgendadas.size());
    }
    
    @Override
    public String toString() {
        return "Paciente{" +
                "nombre='" + nombre + '\'' +
                ", rut='" + rut + '\'' +
                ", email='" + email + '\'' +
                ", citasAgendadas=" + citasAgendadas.size() +
                '}';
    }
}