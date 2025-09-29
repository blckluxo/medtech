import java.util.*;

/**
 * Clase principal MedTech - Sistema de gestión de citas médicas
 * Punto                  System.out.println("5.                     case 8:
                        System.out.println("\n" + "=".repeat(50));
                        System.out.println("👤 MIS DATOS PERSONALES");
                        System.out.println("=".repeat(50));
                        paciente.mostrarDatosPersonales();
                        break;
                    case 9:
                        if (paciente.eliminarPerfil(pacientesRegistrados, medicosRegistrados)) {
                            return; // Salir del menú si se eliminó el perfil
                        }
                        break;
                    case 10:
                        System.out.println("👋 Sesión cerrada. ¡Hasta pronto!");
                        return;is datos personales");
            System.out.println("6. 👨‍⚕️ Consultar información de médicos");
            System.out.println("7. 📊 Ver mi historial de atenciones");
            System.out.println("8. 👤 Ver mis datos personales");
            System.out.println("9. 🗑️ Eliminar mi perfil");
            System.out.println("10. 🔙 Cerrar sesión");  System.out.println("6. ✏️ Modificar mis datos personales");
            System.out.println("7. 👨‍⚕️ Consultar información de médicos");
            System.out.println("8. 📊 Ver mi historial de atenciones");
            System.out.println("9. 👤 Ver mis datos personales");
            System.out.println("10. 🗑️ Eliminar mi perfil");
            System.out.println("11. 🔙 Cerrar sesión");trada de la aplicación
 */
public class MedTech {
    
    private static ArrayList<Paciente> pacientesRegistrados = new ArrayList<>();
    private static ArrayList<Medico> medicosRegistrados = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("🏥 ===============================================");
        System.out.println("🏥    BIENVENIDO A MEDTECH                     ");
        System.out.println("🏥    Sistema de Gestión de Citas Médicas      ");
        System.out.println("🏥 ===============================================");
        
        // Inicializar datos de ejemplo
        inicializarDatosDePrueba();
        
        // Menú principal
        mostrarMenuPrincipal();
    }
    
    /**
     * Inicializa algunos médicos de ejemplo para probar el sistema
     */
    private static void inicializarDatosDePrueba() {
        // Crear médicos de ejemplo
        Medico medico1 = new Medico("Ana García López", "12.345.678-9", "Cardiología", "+56912345678", "ana.garcia@medtech.cl");
        Medico medico2 = new Medico("Carlos Rodríguez Silva", "13.456.789-0", "Dermatología", "+56923456789", "carlos.rodriguez@medtech.cl");
        Medico medico3 = new Medico("María Fernández Castro", "14.567.890-1", "Pediatría", "+56934567890", "maria.fernandez@medtech.cl");
        Medico medico4 = new Medico("José Martínez Pérez", "15.678.901-2", "Traumatología", "+56945678901", "jose.martinez@medtech.cl");
        Medico medico5 = new Medico("Laura Sánchez Morales", "16.789.012-3", "Ginecología", "+56956789012", "laura.sanchez@medtech.cl");
        
        medicosRegistrados.add(medico1);
        medicosRegistrados.add(medico2);
        medicosRegistrados.add(medico3);
        medicosRegistrados.add(medico4);
        medicosRegistrados.add(medico5);
        
        System.out.println("✅ Sistema inicializado con " + medicosRegistrados.size() + " médicos disponibles");
    }
    
    /**
     * Muestra el menú principal de la aplicación
     */
    private static void mostrarMenuPrincipal() {
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("🏥 MEDTECH - MENÚ PRINCIPAL");
            System.out.println("=".repeat(50));
            System.out.println("1. 👤 Registrarse como paciente");
            System.out.println("2. 🔑 Iniciar sesión");
            System.out.println("3. 👨‍⚕️ Ver médicos disponibles");
            System.out.println("4. ❓ Ayuda");
            System.out.println("5. 🚪 Salir");
            System.out.println("=".repeat(50));
            System.out.print("Seleccione una opción: ");
            
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                
                switch (opcion) {
                    case 1:
                        registrarNuevoPaciente();
                        break;
                    case 2:
                        iniciarSesion();
                        break;
                    case 3:
                        mostrarMedicosDisponibles();
                        break;
                    case 4:
                        mostrarAyuda();
                        break;
                    case 5:
                        System.out.println("👋 ¡Gracias por usar MedTech!");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("❌ Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor ingrese un número válido.");
            }
        }
    }
    
    /**
     * Registra un nuevo paciente en el sistema
     */
    private static void registrarNuevoPaciente() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("👤 REGISTRO DE NUEVO PACIENTE");
        System.out.println("=".repeat(50));
        
        Paciente nuevoPaciente = Paciente.registrarPaciente(pacientesRegistrados);
        if (nuevoPaciente != null) {
            System.out.println("🎉 ¡Registro exitoso! Ya puede iniciar sesión.");
        }
    }
    
    /**
     * Maneja el proceso de inicio de sesión
     */
    private static void iniciarSesion() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🔑 INICIO DE SESIÓN");
        System.out.println("=".repeat(50));
        
        if (pacientesRegistrados.isEmpty()) {
            System.out.println("⚠️ No hay pacientes registrados. Debe registrarse primero.");
            return;
        }
        
        Paciente pacienteAutenticado = Paciente.login(pacientesRegistrados);
        if (pacienteAutenticado != null) {
            mostrarMenuPaciente(pacienteAutenticado);
        }
    }
    
    /**
     * Muestra el menú específico para un paciente autenticado
     */
    private static void mostrarMenuPaciente(Paciente paciente) {
        while (true) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("🏥 MEDTECH - MENÚ PACIENTE");
            System.out.println("👤 Bienvenido/a: " + paciente.getNombre());
            System.out.println("=".repeat(50));
            System.out.println("1. 📅 Solicitar cita médica");
            System.out.println("2. 📋 Ver mis citas agendadas");
            System.out.println("3. ❌ Cancelar cita");
            System.out.println("4. ✏️ Modificar mis datos personales");
            System.out.println("5. 👨‍⚕️ Consultar información de médicos");
            System.out.println("6. 📊 Ver mi historial de atenciones");
            System.out.println("7. 👤 Ver mis datos personales");
            System.out.println("8. �️ Eliminar mi perfil");
            System.out.println("9. �🔙 Cerrar sesión");
            System.out.println("=".repeat(50));
            System.out.print("Seleccione una opción: ");
            
            try {
                int opcion = Integer.parseInt(scanner.nextLine().trim());
                
                switch (opcion) {
                    case 1:
                        paciente.solicitarCita(medicosRegistrados);
                        break;
                    case 2:
                        paciente.verCitasAgendadas();
                        break;
                    case 3:
                        paciente.cancelarCita();
                        break;
                    case 4:
                        paciente.modificarDatosPersonales();
                        break;
                    case 5:
                        paciente.consultarMedicos(medicosRegistrados);
                        break;
                    case 6:
                        paciente.verHistorialAtenciones();
                        break;
                    case 7:
                        System.out.println("\n" + "=".repeat(50));
                        System.out.println("👤 MIS DATOS PERSONALES");
                        System.out.println("=".repeat(50));
                        paciente.mostrarDatosPersonales();
                        break;
                    case 8:
                        if (paciente.eliminarPerfil(pacientesRegistrados, medicosRegistrados)) {
                            return; // Salir del menú si se eliminó el perfil
                        }
                        break;
                    case 9:
                        System.out.println("👋 Sesión cerrada. ¡Hasta pronto!");
                        return;
                    default:
                        System.out.println("❌ Opción inválida. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Por favor ingrese un número válido.");
            }
        }
    }
    
    /**
     * Muestra los médicos disponibles en el sistema
     */
    private static void mostrarMedicosDisponibles() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("👨‍⚕️ MÉDICOS DISPONIBLES EN MEDTECH");
        System.out.println("=".repeat(50));
        
        if (medicosRegistrados.isEmpty()) {
            System.out.println("❌ No hay médicos registrados en el sistema");
            return;
        }
        
        // Agrupar médicos por especialidad
        Map<String, List<Medico>> medicosPorEspecialidad = new HashMap<>();
        
        for (Medico medico : medicosRegistrados) {
            medicosPorEspecialidad
                .computeIfAbsent(medico.getEspecialidad(), _ -> new ArrayList<>())
                .add(medico);
        }
        
        // Mostrar médicos agrupados por especialidad
        for (Map.Entry<String, List<Medico>> entry : medicosPorEspecialidad.entrySet()) {
            System.out.println("\n🏥 " + entry.getKey().toUpperCase());
            System.out.println("-".repeat(30));
            
            for (Medico medico : entry.getValue()) {
                System.out.println("👨‍⚕️ Dr/a. " + medico.getNombre());
                System.out.println("   RUT: " + medico.getRut());
                System.out.println("   Horarios disponibles: " + medico.getHorariosDisponibles().size());
                if (!medico.getTelefono().isEmpty()) {
                    System.out.println("   Teléfono: " + medico.getTelefono());
                }
                System.out.println();
            }
        }
        
        System.out.println("💡 Para agendar una cita, debe registrarse e iniciar sesión.");
    }
    
    /**
     * Muestra información de ayuda sobre el sistema
     */
    private static void mostrarAyuda() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("❓ AYUDA - SISTEMA MEDTECH");
        System.out.println("=".repeat(50));
        
        System.out.println("🎯 OBJETIVO:");
        System.out.println("   MedTech es un sistema que facilita la gestión de citas médicas,");
        System.out.println("   reduciendo errores y desorganización en el proceso.");
        
        System.out.println("\n📋 FUNCIONALIDADES PRINCIPALES:");
        System.out.println("   • Registro e inicio de sesión de pacientes");
        System.out.println("   • Solicitud de citas médicas por especialidad");
        System.out.println("   • Visualización de citas agendadas");
        System.out.println("   • Cancelación de citas (con política de 24 horas)");
        System.out.println("   • Modificación de datos personales");
        System.out.println("   • Consulta de información de médicos");
        System.out.println("   • Historial de atenciones médicas");
        
        System.out.println("\n🔐 FORMATO DE RUT:");
        System.out.println("   El RUT debe ingresarse en formato chileno: xx.xxx.xxx-x");
        System.out.println("   Ejemplo: 12.345.678-9");
        
        System.out.println("\n⚠️ POLÍTICAS IMPORTANTES:");
        System.out.println("   • Solo se puede tener una cita por horario");
        System.out.println("   • Cancelaciones con menos de 24 horas generan sanción");
        System.out.println("   • Las contraseñas deben tener mínimo 6 caracteres");
        
        System.out.println("\n📞 ESPECIALIDADES DISPONIBLES:");
        Set<String> especialidades = new HashSet<>();
        for (Medico medico : medicosRegistrados) {
            especialidades.add(medico.getEspecialidad());
        }
        for (String especialidad : especialidades) {
            System.out.println("   • " + especialidad);
        }
        
        System.out.println("\n💡 CONSEJOS DE USO:");
        System.out.println("   • Mantenga sus datos actualizados");
        System.out.println("   • Revise regularmente sus citas agendadas");
        System.out.println("   • Cancele con anticipación para evitar sanciones");
        
        System.out.print("\n📚 Presione Enter para continuar...");
        scanner.nextLine();
    }
    
    /**
     * Obtiene estadísticas generales del sistema
     */
    public static void mostrarEstadisticasGenerales() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 ESTADÍSTICAS DEL SISTEMA MEDTECH");
        System.out.println("=".repeat(50));
        
        System.out.println("👥 Pacientes registrados: " + pacientesRegistrados.size());
        System.out.println("👨‍⚕️ Médicos disponibles: " + medicosRegistrados.size());
        
        // Contar citas totales
        int citasTotales = 0;
        for (Paciente paciente : pacientesRegistrados) {
            citasTotales += paciente.getCitasAgendadas().size();
        }
        System.out.println("📅 Citas agendadas: " + citasTotales);
        
        // Especialidades
        Set<String> especialidades = new HashSet<>();
        for (Medico medico : medicosRegistrados) {
            especialidades.add(medico.getEspecialidad());
        }
        System.out.println("🏥 Especialidades: " + especialidades.size());
        
        // Horarios disponibles totales
        int horariosDisponibles = 0;
        for (Medico medico : medicosRegistrados) {
            horariosDisponibles += medico.getHorariosDisponibles().size();
        }
        System.out.println("⏰ Horarios disponibles: " + horariosDisponibles);
    }
}