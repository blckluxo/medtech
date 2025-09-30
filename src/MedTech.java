import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Medico - Representa un medico en el sistema MedTech
 * Maneja la informacion del medico y sus horarios disponibles
 */
public class Medico {
    // Atributos principales
    private String nombre;
    private String rut;
    private String especialidad;
    private ArrayList<LocalDateTime> horariosDisponibles;
    private ArrayList<Cita> citasAgendadas;
    private String telefono;
    private String email;
    
    // Constructor
    public Medico(String nombre, String rut, String especialidad) {
        this.nombre = nombre;
        this.rut = rut;
        this.especialidad = especialidad;
        this.horariosDisponibles = new ArrayList<>();
        this.citasAgendadas = new ArrayList<>();
        this.telefono = "";
        this.email = "";
        inicializarHorarios();
    }
    
    // Constructor con datos completos
    public Medico(String nombre, String rut, String especialidad, String telefono, String email) {
        this(nombre, rut, especialidad);
        this.telefono = telefono;
        this.email = email;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    
    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    
    public ArrayList<LocalDateTime> getHorariosDisponibles() { return horariosDisponibles; }
    public void setHorariosDisponibles(ArrayList<LocalDateTime> horariosDisponibles) { 
        this.horariosDisponibles = horariosDisponibles; 
    }
    
    public ArrayList<Cita> getCitasAgendadas() { return citasAgendadas; }
    public void setCitasAgendadas(ArrayList<Cita> citasAgendadas) { this.citasAgendadas = citasAgendadas; }
    
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    /**
     * Inicializa horarios disponibles para los próximos días
     * (Horarios de ejemplo: 9:00, 10:00, 11:00, 14:00, 15:00, 16:00)
     */
    private void inicializarHorarios() {
        LocalDateTime fechaBase = LocalDateTime.now().plusDays(1); // Empezar desde mañana
        
        for (int dia = 0; dia < 30; dia++) { // 30 días de disponibilidad
            LocalDateTime fecha = fechaBase.plusDays(dia);
            
            // Solo días de semana (lunes a viernes)
            if (fecha.getDayOfWeek().getValue() <= 5) {
                // Horarios de mañana: 9:00, 10:00, 11:00
                horariosDisponibles.add(fecha.withHour(9).withMinute(0).withSecond(0).withNano(0));
                horariosDisponibles.add(fecha.withHour(10).withMinute(0).withSecond(0).withNano(0));
                horariosDisponibles.add(fecha.withHour(11).withMinute(0).withSecond(0).withNano(0));
                
                // Horarios de tarde: 14:00, 15:00, 16:00
                horariosDisponibles.add(fecha.withHour(14).withMinute(0).withSecond(0).withNano(0));
                horariosDisponibles.add(fecha.withHour(15).withMinute(0).withSecond(0).withNano(0));
                horariosDisponibles.add(fecha.withHour(16).withMinute(0).withSecond(0).withNano(0));
            }
        }
        
        // Ordenar horarios por fecha
        Collections.sort(horariosDisponibles);
    }
    
    /**
     * Agrega un horario específico a los horarios disponibles
     */
    public void agregarHorario(LocalDateTime horario) {
        if (!horariosDisponibles.contains(horario)) {
            horariosDisponibles.add(horario);
            Collections.sort(horariosDisponibles);
        }
    }
    
    /**
     * Remueve un horario de los horarios disponibles (cuando se agenda una cita)
     */
    public boolean removerHorario(LocalDateTime horario) {
        return horariosDisponibles.remove(horario);
    }
    
    /**
     * Verifica si el médico tiene disponible un horario específico
     */
    public boolean tieneHorarioDisponible(LocalDateTime horario) {
        return horariosDisponibles.contains(horario);
    }
    
    /**
     * Obtiene los horarios disponibles formateados como strings
     */
    public ArrayList<String> getHorariosFormateados() {
        ArrayList<String> horariosFormateados = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (LocalDateTime horario : horariosDisponibles) {
            horariosFormateados.add(horario.format(formatter));
        }
        
        return horariosFormateados;
    }
    
    /**
     * Muestra los próximos horarios disponibles (máximo 10)
     */
    public void mostrarProximosHorarios() {
        if (horariosDisponibles.isEmpty()) {
            System.out.println("❌ No hay horarios disponibles");
            return;
        }
        
        System.out.println("📅 Próximos horarios disponibles para Dr/a. " + nombre + ":");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        int limite = Math.min(10, horariosDisponibles.size());
        for (int i = 0; i < limite; i++) {
            System.out.println("   " + (i + 1) + ". " + horariosDisponibles.get(i).format(formatter));
        }
        
        if (horariosDisponibles.size() > 10) {
            System.out.println("   ... y " + (horariosDisponibles.size() - 10) + " horarios más");
        }
    }
    
    /**
     * Muestra las citas agendadas del médico
     */
    public void verCitasAgendadas() {
        if (citasAgendadas.isEmpty()) {
            System.out.println("📅 No tiene citas agendadas");
            return;
        }
        
        System.out.println("=== CITAS AGENDADAS - Dr/a. " + nombre + " ===");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        // Ordenar citas por fecha
        citasAgendadas.sort((c1, c2) -> c1.getFechaHora().compareTo(c2.getFechaHora()));
        
        for (int i = 0; i < citasAgendadas.size(); i++) {
            Cita cita = citasAgendadas.get(i);
            System.out.println("📅 Cita " + (i + 1) + ":");
            System.out.println("   Fecha: " + cita.getFechaHora().format(formatter));
            System.out.println("   Paciente: " + cita.getPaciente().getNombre());
            System.out.println("   RUT Paciente: " + cita.getPaciente().getRut());
            System.out.println();
        }
    }
    
    /**
     * Obtiene el número de citas agendadas para hoy
     */
    public int getCitasHoy() {
        LocalDateTime hoy = LocalDateTime.now();
        int citasHoy = 0;
        
        for (Cita cita : citasAgendadas) {
            if (cita.getFechaHora().toLocalDate().equals(hoy.toLocalDate())) {
                citasHoy++;
            }
        }
        
        return citasHoy;
    }
    
    /**
     * Obtiene estadísticas básicas del médico
     */
    public void mostrarEstadisticas() {
        System.out.println("=== ESTADÍSTICAS - Dr/a. " + nombre + " ===");
        System.out.println("🏥 Especialidad: " + especialidad);
        System.out.println("📅 Citas agendadas: " + citasAgendadas.size());
        System.out.println("📆 Citas hoy: " + getCitasHoy());
        System.out.println("⏰ Horarios disponibles: " + horariosDisponibles.size());
        System.out.println("📞 Teléfono: " + (telefono.isEmpty() ? "No registrado" : telefono));
        System.out.println("📧 Email: " + (email.isEmpty() ? "No registrado" : email));
    }
    
    /**
     * Valida si una fecha está dentro del rango de disponibilidad del médico
     */
    public boolean esFechaValida(LocalDateTime fecha) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime limite = ahora.plusDays(60); // 60 días máximo de anticipación
        
        return fecha.isAfter(ahora) && fecha.isBefore(limite);
    }
    
    /**
     * Busca el próximo horario disponible
     */
    public LocalDateTime getProximoHorarioDisponible() {
        if (horariosDisponibles.isEmpty()) {
            return null;
        }
        
        LocalDateTime ahora = LocalDateTime.now();
        for (LocalDateTime horario : horariosDisponibles) {
            if (horario.isAfter(ahora)) {
                return horario;
            }
        }
        
        return null;
    }
    
    /**
     * Cancela una cita específica (devuelve el horario a disponibles)
     */
    public boolean cancelarCita(Cita cita) {
        if (citasAgendadas.remove(cita)) {
            agregarHorario(cita.getFechaHora());
            return true;
        }
        return false;
    }
    
    /**
     * Reagenda una cita a un nuevo horario
     */
    public boolean reagendarCita(Cita cita, LocalDateTime nuevoHorario) {
        if (!tieneHorarioDisponible(nuevoHorario)) {
            return false;
        }
        
        LocalDateTime horarioAnterior = cita.getFechaHora();
        cita.setFechaHora(nuevoHorario);
        
        // Devolver horario anterior a disponibles
        agregarHorario(horarioAnterior);
        // Quitar nuevo horario de disponibles
        removerHorario(nuevoHorario);
        
        return true;
    }
    
    @Override
    public String toString() {
        return "Medico{" +
                "nombre='" + nombre + '\'' +
                ", rut='" + rut + '\'' +
                ", especialidad='" + especialidad + '\'' +
                ", horariosDisponibles=" + horariosDisponibles.size() +
                ", citasAgendadas=" + citasAgendadas.size() +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Medico medico = (Medico) obj;
        return Objects.equals(rut, medico.rut);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(rut);
    }
}
