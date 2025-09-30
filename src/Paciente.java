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
     * Formatea el nombre correctamente: primera letra en mayuscula por palabra
     * y valida que tenga al menos dos palabras (nombre y apellido)
     */
    public static String formatearNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }
        
        // Limpiar espacios extra y convertir a minusculas
        String nombreLimpio = nombre.trim().toLowerCase();
        
        // Dividir en palabras
        String[] palabras = nombreLimpio.split("\\s+");
        
        // Verificar que tenga al menos dos palabras
        if (palabras.length < 2) {
            return null;
        }
        
        // Formatear cada palabra: primera letra mayuscula
        StringBuilder nombreFormateado = new StringBuilder();
        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i];
            if (palabra.length() > 0) {
                // Primera letra mayuscula, resto minusculas
                String palabraFormateada = palabra.substring(0, 1).toUpperCase() + 
                                          palabra.substring(1).toLowerCase();
                nombreFormateado.append(palabraFormateada);
                
                // Agregar espacio entre palabras (excepto la ultima)
                if (i < palabras.length - 1) {
                    nombreFormateado.append(" ");
                }
            }
        }
        
        return nombreFormateado.toString();
    }
    
    /**
     * Valida que el email tenga el formato correcto xxx@xxx.xxx
     */
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return true; // Email vacio es valido (opcional)
        }
        
        email = email.trim().toLowerCase();
        
        // Patron basico para email: xxx@xxx.xxx
        String patron = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        
        return email.matches(patron);
    }
    
    /**
     * Valida que el telefono tenga minimo 8 caracteres y solo numeros
     */
    public static boolean validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return true; // Telefono vacio es valido (opcional)
        }
        
        // Limpiar espacios y caracteres especiales comunes
        String telefonoLimpio = telefono.trim().replaceAll("[\\s\\-\\+\\(\\)]", "");
        
        // Verificar que tenga al menos 8 digitos y solo numeros
        return telefonoLimpio.matches("^\\d{8,}$");
    }
    
    /**
     * Formatea el telefono removiendo espacios y caracteres especiales
     */
    public static String formatearTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return "";
        }
        
        // Limpiar el telefono manteniendo solo numeros
        String telefonoLimpio = telefono.trim().replaceAll("[^\\d]", "");
        
        // Agregar formato si es numero chileno (+56)
        if (telefonoLimpio.startsWith("56") && telefonoLimpio.length() >= 10) {
            return "+" + telefonoLimpio;
        } else if (telefonoLimpio.length() >= 8) {
            return "+56" + telefonoLimpio;
        }
        
        return telefonoLimpio;
    }
    
    /**
     * Valida que la direccion tenga al menos una palabra y un numero
     */
    public static boolean validarDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            return true; // Direccion vacia es valida (opcional)
        }
        
        String direccionLimpia = direccion.trim();
        
        // Verificar que tenga al menos una letra (palabra) y un numero
        boolean tieneLetra = direccionLimpia.matches(".*[a-zA-Z].*");
        boolean tieneNumero = direccionLimpia.matches(".*\\d.*");
        
        return tieneLetra && tieneNumero;
    }
    
    /**
     * Valida que la edad sea un numero valido entre 1 y 120
     */
    public static boolean validarEdad(int edad) {
        return edad >= 1 && edad <= 120;
    }
    
    /**
     * Formatea la direccion: primera letra de cada palabra en mayuscula
     */
    public static String formatearDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            return "";
        }
        
        String[] palabras = direccion.trim().toLowerCase().split("\\s+");
        StringBuilder direccionFormateada = new StringBuilder();
        
        for (int i = 0; i < palabras.length; i++) {
            String palabra = palabras[i];
            if (palabra.length() > 0) {
                // Si la palabra es un numero, mantenerla asi
                if (palabra.matches("\\d+")) {
                    direccionFormateada.append(palabra);
                } else {
                    // Primera letra mayuscula para palabras
                    direccionFormateada.append(palabra.substring(0, 1).toUpperCase())
                                      .append(palabra.substring(1));
                }
                
                if (i < palabras.length - 1) {
                    direccionFormateada.append(" ");
                }
            }
        }
        
        return direccionFormateada.toString();
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
     * Registra un nuevo paciente en el sistema
     */
    public static Paciente registrarPaciente(ArrayList<Paciente> pacientesRegistrados) {
        Scanner scanner = MedTech.scanner;
        
        System.out.println("=== REGISTRO DE PACIENTE ===");
        
        // Validar RUT unico
        String rut;
        boolean rutValido = false;
        boolean rutExiste = false;
        
        do {
            System.out.print("Ingrese su RUT (formato: xx.xxx.xxx-x): ");
            rut = scanner.nextLine().trim();
            
            rutValido = validarRut(rut);
            if (!rutValido) {
                System.out.println("RUT invalido. Use el formato correcto.");
                continue;
            }
            
            // Verificar si el RUT ya existe
            rutExiste = false;
            for (Paciente p : pacientesRegistrados) {
                if (p.getRut().equals(rut)) {
                    rutExiste = true;
                    break;
                }
            }
            
            if (rutExiste) {
                System.out.println("Este RUT ya esta registrado en el sistema.");
            }
        } while (!rutValido || rutExiste);
        
        // Solicitar y validar nombre
        String nombre;
        do {
            System.out.print("Ingrese su nombre completo (nombre y apellido): ");
            String nombreIngresado = scanner.nextLine().trim();
            
            nombre = formatearNombre(nombreIngresado);
            
            if (nombre == null) {
                System.out.println("El nombre debe tener al menos dos palabras (nombre y apellido).");
                System.out.println("Ejemplo: Juan Perez o Maria Gonzalez Lopez");
            }
        } while (nombre == null);
        
        System.out.println("Nombre formateado: " + nombre);
        
        String contrasena;
        do {
            System.out.print("Ingrese su contrasena (minimo 6 caracteres): ");
            contrasena = scanner.nextLine().trim();
            if (contrasena.length() < 6) {
                System.out.println("La contrasena debe tener al menos 6 caracteres.");
            }
        } while (contrasena.length() < 6);
        
        // Solicitar y validar email
        String email;
        boolean emailValido = false;
        
        do {
            System.out.print("Ingrese su email (formato: usuario@dominio.com) [Opcional - Enter para omitir]: ");
            email = scanner.nextLine().trim().toLowerCase();
            
            if (email.isEmpty()) {
                System.out.println("Email no ingresado (opcional)");
                emailValido = true;
            } else if (validarEmail(email)) {
                System.out.println("Email valido: " + email);
                emailValido = true;
            } else {
                System.out.println("Formato de email invalido.");
                System.out.println("Ejemplo: juan.perez@gmail.com");
                emailValido = false;
            }
        } while (!emailValido);
        
        // Solicitar y validar telefono
        String telefono = "";
        boolean telefonoValido = false;
        
        do {
            System.out.print("Ingrese su telefono (minimo 8 digitos) [Opcional - Enter para omitir]: ");
            String telefonoIngresado = scanner.nextLine().trim();
            
            if (telefonoIngresado.isEmpty()) {
                telefono = "";
                System.out.println("Telefono no ingresado (opcional)");
                telefonoValido = true;
            } else if (validarTelefono(telefonoIngresado)) {
                telefono = formatearTelefono(telefonoIngresado);
                System.out.println("Telefono valido: " + telefono);
                telefonoValido = true;
            } else {
                System.out.println("Telefono invalido. Debe tener minimo 8 digitos y solo numeros.");
                System.out.println("Ejemplo: 987654321 o +56987654321");
                telefonoValido = false;
            }
        } while (!telefonoValido);
        
        // Solicitar y validar edad (obligatoria)
        int edad = 0;
        boolean edadValida = false;
        
        do {
            System.out.print("Ingrese su edad (debe ser un numero entre 1 y 120): ");
            String edadIngresada = scanner.nextLine().trim();
            
            try {
                edad = Integer.parseInt(edadIngresada);
                
                if (edad >= 1 && edad <= 120) {
                    System.out.println("Edad valida: " + edad + " anos");
                    edadValida = true;
                } else {
                    System.out.println("La edad debe estar entre 1 y 120 anos.");
                    edadValida = false;
                }
            } catch (NumberFormatException e) {
                System.out.println("La edad debe ser un numero valido.");
                System.out.println("Ejemplo: 25, 30, 45");
                edadValida = false;
            }
        } while (!edadValida);
        
        // Solicitar y validar direccion
        String direccion = "";
        boolean direccionValida = false;
        
        do {
            System.out.print("Ingrese su direccion (debe incluir nombre de calle y numero) [Opcional - Enter para omitir]: ");
            String direccionIngresada = scanner.nextLine().trim();
            
            if (direccionIngresada.isEmpty()) {
                direccion = "";
                System.out.println("Direccion no ingresada (opcional)");
                direccionValida = true;
            } else if (validarDireccion(direccionIngresada)) {
                direccion = formatearDireccion(direccionIngresada);
                System.out.println("Direccion valida: " + direccion);
                direccionValida = true;
            } else {
                System.out.println("Direccion invalida. Debe incluir al menos una palabra y un numero.");
                System.out.println("Ejemplo: Lautaro 255, Av. Libertador 1234, Pedro de Valdivia 2580");
                direccionValida = false;
            }
        } while (!direccionValida);
        
        Paciente nuevoPaciente = new Paciente(nombre, rut, contrasena, email, telefono, edad, direccion);
        pacientesRegistrados.add(nuevoPaciente);
        
        System.out.println("Paciente registrado exitosamente!");
        return nuevoPaciente;
    }
    
    /**
     * Autentica un paciente con RUT y contrasena
     */
    public static Paciente login(ArrayList<Paciente> pacientesRegistrados) {
        Scanner scanner = MedTech.scanner;
        
        System.out.println("=== LOGIN PACIENTE ===");
        System.out.print("Ingrese su RUT: ");
        String rut = scanner.nextLine().trim();
        
        System.out.print("Ingrese su contrasena: ");
        String contrasena = scanner.nextLine().trim();
        
        for (Paciente paciente : pacientesRegistrados) {
            if (paciente.getRut().equals(rut) && paciente.getContrasena().equals(contrasena)) {
                System.out.println("Bienvenido/a " + paciente.getNombre());
                return paciente;
            }
        }
        
        System.out.println("Credenciales invalidas. RUT o contrasena incorrectos.");
        return null;
    }
    
    /**
     * Solicita una nueva cita medica
     */
    public void solicitarCita(ArrayList<Medico> medicos) {
        Scanner scanner = MedTech.scanner;
        
        System.out.println("=== SOLICITAR CITA MEDICA ===");
        
        // Mostrar especialidades disponibles
        Set<String> especialidades = new HashSet<>();
        for (Medico medico : medicos) {
            especialidades.add(medico.getEspecialidad());
        }
        
        System.out.println("Especialidades disponibles:");
        int i = 1;
        ArrayList<String> listaEspecialidades = new ArrayList<>(especialidades);
        for (String especialidad : listaEspecialidades) {
            System.out.println(i + ". " + especialidad);
            i++;
        }
        
        System.out.print("Seleccione la especialidad (numero): ");
        int opcionEsp;
        try {
            opcionEsp = Integer.parseInt(scanner.nextLine()) - 1;
            if (opcionEsp < 0 || opcionEsp >= listaEspecialidades.size()) {
                System.out.println("Opcion invalida");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero valido");
            return;
        }
        
        String especialidadSeleccionada = listaEspecialidades.get(opcionEsp);
        
        // Mostrar medicos de esa especialidad
        ArrayList<Medico> medicosEspecialidad = new ArrayList<>();
        for (Medico medico : medicos) {
            if (medico.getEspecialidad().equals(especialidadSeleccionada)) {
                medicosEspecialidad.add(medico);
            }
        }
        
        if (medicosEspecialidad.isEmpty()) {
            System.out.println("No hay medicos disponibles para esa especialidad");
            return;
        }
        
        System.out.println("Medicos disponibles:");
        for (int j = 0; j < medicosEspecialidad.size(); j++) {
            Medico med = medicosEspecialidad.get(j);
            System.out.println((j + 1) + ". Dr/a. " + med.getNombre() + " - " + med.getEspecialidad());
        }
        
        System.out.print("Seleccione el medico (numero): ");
        int opcionMed;
        try {
            opcionMed = Integer.parseInt(scanner.nextLine()) - 1;
            if (opcionMed < 0 || opcionMed >= medicosEspecialidad.size()) {
                System.out.println("Opcion invalida");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero valido");
            return;
        }
        
        Medico medicoSeleccionado = medicosEspecialidad.get(opcionMed);
        
        // Mostrar horarios disponibles del medico
        ArrayList<LocalDateTime> horariosDisponibles = medicoSeleccionado.getHorariosDisponibles();
        if (horariosDisponibles.isEmpty()) {
            System.out.println("El medico no tiene horarios disponibles");
            return;
        }
        
        System.out.println("Horarios disponibles:");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (int k = 0; k < horariosDisponibles.size(); k++) {
            System.out.println((k + 1) + ". " + horariosDisponibles.get(k).format(formatter));
        }
        
        System.out.print("Seleccione el horario (numero): ");
        int opcionHorario;
        try {
            opcionHorario = Integer.parseInt(scanner.nextLine()) - 1;
            if (opcionHorario < 0 || opcionHorario >= horariosDisponibles.size()) {
                System.out.println("Opcion invalida");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero valido");
            return;
        }
        
        LocalDateTime horarioSeleccionado = horariosDisponibles.get(opcionHorario);
        
        // Verificar que el paciente no tenga cita en ese horario
        for (Cita cita : this.citasAgendadas) {
            if (cita.getFechaHora().equals(horarioSeleccionado)) {
                System.out.println("Ya tiene una cita agendada en ese horario");
                return;
            }
        }
        
        // Crear y agendar la cita
        Cita nuevaCita = new Cita(this, medicoSeleccionado, horarioSeleccionado);
        this.citasAgendadas.add(nuevaCita);
        medicoSeleccionado.getCitasAgendadas().add(nuevaCita);
        medicoSeleccionado.getHorariosDisponibles().remove(horarioSeleccionado);
        
        System.out.println("Cita agendada exitosamente!");
        System.out.println("Fecha: " + horarioSeleccionado.format(formatter));
        System.out.println("Medico: Dr/a. " + medicoSeleccionado.getNombre());
        System.out.println("Especialidad: " + medicoSeleccionado.getEspecialidad());
    }
    
    /**
     * Cancela una cita medica agendada
     */
    public void cancelarCita() {
        if (citasAgendadas.isEmpty()) {
            System.out.println("No tiene citas agendadas para cancelar");
            return;
        }
        
        Scanner scanner = MedTech.scanner;
        System.out.println("=== CANCELAR CITA ===");
        
        // Mostrar citas agendadas
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (int i = 0; i < citasAgendadas.size(); i++) {
            Cita cita = citasAgendadas.get(i);
            System.out.println((i + 1) + ". " + cita.getFechaHora().format(formatter) + 
                             " - Dr/a. " + cita.getMedico().getNombre() + 
                             " (" + cita.getMedico().getEspecialidad() + ")");
        }
        
        System.out.print("Seleccione la cita a cancelar (numero): ");
        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine()) - 1;
            if (opcion < 0 || opcion >= citasAgendadas.size()) {
                System.out.println("Opcion invalida");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Debe ingresar un numero valido");
            return;
        }
        
        Cita citaACancelar = citasAgendadas.get(opcion);
        
        // Verificar si la cancelacion es con menos de 24 horas
        LocalDateTime ahora = LocalDateTime.now();
        long horasAntes = java.time.Duration.between(ahora, citaACancelar.getFechaHora()).toHours();
        
        if (horasAntes < 24) {
            System.out.println("ATENCION: Esta cancelando con menos de 24 horas de anticipacion.");
            System.out.println("Se aplicara una sancion por cancelacion tardia.");
        }
        
        // Remover la cita
        citasAgendadas.remove(citaACancelar);
        citaACancelar.getMedico().getCitasAgendadas().remove(citaACancelar);
        citaACancelar.getMedico().getHorariosDisponibles().add(citaACancelar.getFechaHora());
        
        System.out.println("Cita cancelada exitosamente");
    }
    
    /**
     * Modifica los datos personales del paciente
     */
    public void modificarDatosPersonales() {
        Scanner scanner = MedTech.scanner;
        
        System.out.println("=== MODIFICAR DATOS PERSONALES ===");
        System.out.println("Datos actuales:");
        mostrarDatosPersonales();
        
        System.out.println("\nQue desea modificar?");
        System.out.println("1. Nombre");
        System.out.println("2. Email");
        System.out.println("3. Telefono");
        System.out.println("4. Edad");
        System.out.println("5. Direccion");
        System.out.println("6. Contrasena");
        System.out.print("Seleccione una opcion: ");
        
        int opcion;
        try {
            opcion = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opcion invalida");
            return;
        }
        
        switch (opcion) {
            case 1:
                String nuevoNombre;
                do {
                    System.out.print("Nuevo nombre completo (nombre y apellido): ");
                    String nombreIngresado = scanner.nextLine().trim();
                    nuevoNombre = formatearNombre(nombreIngresado);
                    
                    if (nuevoNombre == null) {
                        System.out.println("El nombre debe tener al menos dos palabras (nombre y apellido).");
                    }
                } while (nuevoNombre == null);
                
                this.nombre = nuevoNombre;
                System.out.println("Nombre actualizado: " + nuevoNombre);
                break;
            case 2:
                String nuevoEmail;
                boolean emailValido = false;
                
                do {
                    System.out.print("Nuevo email (formato: usuario@dominio.com): ");
                    nuevoEmail = scanner.nextLine().trim().toLowerCase();
                    
                    if (nuevoEmail.isEmpty()) {
                        this.email = "";
                        System.out.println("Email eliminado");
                        emailValido = true;
                    } else if (validarEmail(nuevoEmail)) {
                        this.email = nuevoEmail;
                        System.out.println("Email actualizado: " + nuevoEmail);
                        emailValido = true;
                    } else {
                        System.out.println("Formato de email invalido.");
                        System.out.println("Ejemplo: juan.perez@gmail.com");
                    }
                } while (!emailValido);
                break;
            case 3:
                String nuevoTelefono;
                boolean telefonoValido = false;
                
                do {
                    System.out.print("Nuevo telefono (minimo 8 digitos) [Enter para eliminar]: ");
                    String telefonoIngresado = scanner.nextLine().trim();
                    
                    if (telefonoIngresado.isEmpty()) {
                        this.telefono = "";
                        System.out.println("Telefono eliminado");
                        telefonoValido = true;
                    } else if (validarTelefono(telefonoIngresado)) {
                        nuevoTelefono = formatearTelefono(telefonoIngresado);
                        this.telefono = nuevoTelefono;
                        System.out.println("Telefono actualizado: " + nuevoTelefono);
                        telefonoValido = true;
                    } else {
                        System.out.println("Telefono invalido. Debe tener minimo 8 digitos y solo numeros.");
                        System.out.println("Ejemplo: 987654321");
                    }
                } while (!telefonoValido);
                break;
            case 4:
                int nuevaEdad = 0;
                boolean edadValida = false;
                
                do {
                    System.out.print("Nueva edad (debe ser un numero entre 1 y 120): ");
                    String edadIngresada = scanner.nextLine().trim();
                    
                    try {
                        nuevaEdad = Integer.parseInt(edadIngresada);
                        
                        if (nuevaEdad >= 1 && nuevaEdad <= 120) {
                            this.edad = nuevaEdad;
                            System.out.println("Edad actualizada: " + nuevaEdad + " anos");
                            edadValida = true;
                        } else {
                            System.out.println("La edad debe estar entre 1 y 120 anos.");
                            edadValida = false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("La edad debe ser un numero valido.");
                        System.out.println("Ejemplo: 25, 30, 45");
                        edadValida = false;
                    }
                } while (!edadValida);
                break;
            case 5:
                String nuevaDireccion;
                boolean direccionValida = false;
                
                do {
                    System.out.print("Nueva direccion (debe incluir calle y numero) [Enter para eliminar]: ");
                    String direccionIngresada = scanner.nextLine().trim();
                    
                    if (direccionIngresada.isEmpty()) {
                        this.direccion = "";
                        System.out.println("Direccion eliminada");
                        direccionValida = true;
                    } else if (validarDireccion(direccionIngresada)) {
                        nuevaDireccion = formatearDireccion(direccionIngresada);
                        this.direccion = nuevaDireccion;
                        System.out.println("Direccion actualizada: " + nuevaDireccion);
                        direccionValida = true;
                    } else {
                        System.out.println("Direccion invalida. Debe incluir al menos una palabra y un numero.");
                        System.out.println("Ejemplo: Lautaro 255, Av. Libertador 1234");
                    }
                } while (!direccionValida);
                break;
            case 6:
                System.out.print("Nueva contrasena: ");
                String nuevaContrasena = scanner.nextLine().trim();
                if (nuevaContrasena.length() >= 6) {
                    this.contrasena = nuevaContrasena;
                    System.out.println("Contrasena actualizada");
                } else {
                    System.out.println("La contrasena debe tener al menos 6 caracteres");
                }
                break;
            default:
                System.out.println("Opcion invalida");
        }
    }
    
    /**
     * Muestra las citas medicas agendadas
     */
    public void verCitasAgendadas() {
        if (citasAgendadas.isEmpty()) {
            System.out.println("No tiene citas agendadas");
            return;
        }
        
        System.out.println("=== SUS CITAS AGENDADAS ===");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (int i = 0; i < citasAgendadas.size(); i++) {
            Cita cita = citasAgendadas.get(i);
            System.out.println("Cita " + (i + 1) + ":");
            System.out.println("   Fecha: " + cita.getFechaHora().format(formatter));
            System.out.println("   Medico: Dr/a. " + cita.getMedico().getNombre());
            System.out.println("   Especialidad: " + cita.getMedico().getEspecialidad());
            System.out.println();
        }
    }
    
    /**
     * Consulta informacion de medicos disponibles
     */
    public void consultarMedicos(ArrayList<Medico> medicos) {
        if (medicos.isEmpty()) {
            System.out.println("No hay medicos registrados en el sistema");
            return;
        }
        
        System.out.println("=== INFORMACION DE MEDICOS ===");
        
        for (Medico medico : medicos) {
            System.out.println("Dr/a. " + medico.getNombre());
            System.out.println("   RUT: " + medico.getRut());
            System.out.println("   Especialidad: " + medico.getEspecialidad());
            System.out.println("   Horarios disponibles: " + medico.getHorariosDisponibles().size());
            System.out.println();
        }
    }
    
    /**
     * Muestra el historial de atenciones medicas
     */
    public void verHistorialAtenciones() {
        if (historialAtenciones.isEmpty()) {
            System.out.println("No tiene historial de atenciones");
            return;
        }
        
        System.out.println("=== HISTORIAL DE ATENCIONES ===");
        for (int i = 0; i < historialAtenciones.size(); i++) {
            System.out.println((i + 1) + ". " + historialAtenciones.get(i));
        }
    }
    
    /**
     * Elimina el perfil del paciente del sistema
     */
    public boolean eliminarPerfil(ArrayList<Paciente> pacientesRegistrados, ArrayList<Medico> medicos) {
        Scanner scanner = MedTech.scanner;
        
        System.out.println("=== ELIMINAR PERFIL ===");
        System.out.println("ATENCION: Esta accion eliminara permanentemente su cuenta y todos sus datos.");
        
        // Verificar si tiene citas agendadas
        if (!citasAgendadas.isEmpty()) {
            System.out.println("No puede eliminar su perfil mientras tenga citas agendadas.");
            System.out.println("Citas pendientes: " + citasAgendadas.size());
            System.out.println("Cancele todas sus citas antes de eliminar el perfil.");
            return false;
        }
        
        System.out.println("Para confirmar, ingrese su contrasena:");
        String contrasenaConfirmacion = scanner.nextLine().trim();
        
        if (!this.contrasena.equals(contrasenaConfirmacion)) {
            System.out.println("Contrasena incorrecta. Eliminacion cancelada.");
            return false;
        }
        
        System.out.print("Esta seguro que desea eliminar su perfil? (escriba 'ELIMINAR' para confirmar): ");
        String confirmacion = scanner.nextLine().trim();
        
        if (!confirmacion.equals("ELIMINAR")) {
            System.out.println("Eliminacion cancelada.");
            return false;
        }
        
        // Eliminar el paciente de la lista
        pacientesRegistrados.remove(this);
        
        System.out.println("Perfil eliminado exitosamente.");
        System.out.println("Gracias por usar MedTech.");
        return true;
    }
    
    /**
     * Muestra los datos personales del paciente
     */
    public void mostrarDatosPersonales() {
        System.out.println("Nombre: " + nombre);
        System.out.println("RUT: " + rut);
        System.out.println("Email: " + (email.isEmpty() ? "No registrado" : email));
        System.out.println("Telefono: " + (telefono.isEmpty() ? "No registrado" : telefono));
        System.out.println("Edad: " + (edad == 0 ? "No registrada" : edad + " anos"));
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
