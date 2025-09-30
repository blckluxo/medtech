import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase Cita - Representa una cita médica en el sistema MedTech
 * Conecta un paciente con un médico en un horario específico
 */
public class Cita {
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private String estado;
    private String observaciones;
    
    // Estados posibles de una cita
    public static final String ESTADO_AGENDADA = "AGENDADA";
    public static final String ESTADO_CONFIRMADA = "CONFIRMADA";
    public static final String ESTADO_CANCELADA = "CANCELADA";
    public static final String ESTADO_COMPLETADA = "COMPLETADA";
    public static final String ESTADO_NO_ASISTIO = "NO_ASISTIO";
    
    // Constructor
    public Cita(Paciente paciente, Medico medico, LocalDateTime fechaHora) {
        this.paciente = paciente;
        this.medico = medico;
        this.fechaHora = fechaHora;
        this.estado = ESTADO_AGENDADA;
        this.observaciones = "";
    }
    
    // Constructor con observaciones
    public Cita(Paciente paciente, Medico medico, LocalDateTime fechaHora, String observaciones) {
        this(paciente, medico, fechaHora);
        this.observaciones = observaciones;
    }
    
    // Getters y Setters
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    
    /**
     * Confirma la cita (cambia el estado a CONFIRMADA)
     */
    public void confirmarCita() {
        this.estado = ESTADO_CONFIRMADA;
    }
    
    /**
     * Cancela la cita (cambia el estado a CANCELADA)
     */
    public void cancelarCita() {
        this.estado = ESTADO_CANCELADA;
    }
    
    /**
     * Marca la cita como completada
     */
    public void completarCita() {
        this.estado = ESTADO_COMPLETADA;
    }
    
    /**
     * Marca que el paciente no asistió
     */
    public void marcarNoAsistio() {
        this.estado = ESTADO_NO_ASISTIO;
    }
    
    /**
     * Verifica si la cita está activa (agendada o confirmada)
     */
    public boolean estaActiva() {
        return estado.equals(ESTADO_AGENDADA) || estado.equals(ESTADO_CONFIRMADA);
    }
    
    /**
     * Verifica si la cita puede ser cancelada
     */
    public boolean puedeSerCancelada() {
        return estaActiva();
    }
    
    /**
     * Obtiene información formateada de la cita
     */
    public String getInformacionCompleta() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        StringBuilder info = new StringBuilder();
        
        info.append("=== INFORMACIÓN DE CITA ===\n");
        info.append("📅 Fecha y Hora: ").append(fechaHora.format(formatter)).append("\n");
        info.append("👤 Paciente: ").append(paciente.getNombre()).append("\n");
        info.append("🆔 RUT Paciente: ").append(paciente.getRut()).append("\n");
        info.append("👨‍⚕️ Médico: Dr/a. ").append(medico.getNombre()).append("\n");
        info.append("🏥 Especialidad: ").append(medico.getEspecialidad()).append("\n");
        info.append("📋 Estado: ").append(estado).append("\n");
        
        if (!observaciones.isEmpty()) {
            info.append("📝 Observaciones: ").append(observaciones).append("\n");
        }
        
        return info.toString();
    }
    
    /**
     * Obtiene un resumen corto de la cita
     */
    public String getResumen() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        return fechaHora.format(formatter) + " - " + medico.getNombre() + 
               " (" + medico.getEspecialidad() + ") - " + estado;
    }
    
    /**
     * Calcula las horas que faltan para la cita
     */
    public long getHorasParaCita() {
        LocalDateTime ahora = LocalDateTime.now();
        return java.time.Duration.between(ahora, fechaHora).toHours();
    }
    
    /**
     * Verifica si la cita es el mismo día
     */
    public boolean esHoy() {
        return fechaHora.toLocalDate().equals(LocalDateTime.now().toLocalDate());
    }
    
    /**
     * Verifica si la cita es mañana
     */
    public boolean esManana() {
        return fechaHora.toLocalDate().equals(LocalDateTime.now().plusDays(1).toLocalDate());
    }
    
    /**
     * Verifica si la cita ya pasó
     */
    public boolean yaPaso() {
        return fechaHora.isBefore(LocalDateTime.now());
    }
    
    /**
     * Obtiene el tiempo restante hasta la cita en formato legible
     */
    public String getTiempoRestante() {
        if (yaPaso()) {
            return "Cita pasada";
        }
        
        LocalDateTime ahora = LocalDateTime.now();
        java.time.Duration duracion = java.time.Duration.between(ahora, fechaHora);
        
        long dias = duracion.toDays();
        long horas = duracion.toHours() % 24;
        long minutos = duracion.toMinutes() % 60;
        
        if (dias > 0) {
            return dias + " día(s), " + horas + " hora(s)";
        } else if (horas > 0) {
            return horas + " hora(s), " + minutos + " minuto(s)";
        } else {
            return minutos + " minuto(s)";
        }
    }
    
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return "Cita{" +
                "paciente=" + paciente.getNombre() +
                ", medico=" + medico.getNombre() +
                ", fechaHora=" + fechaHora.format(formatter) +
                ", estado='" + estado + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cita cita = (Cita) obj;
        return paciente.equals(cita.paciente) && 
               medico.equals(cita.medico) && 
               fechaHora.equals(cita.fechaHora);
    }
    
    @Override
    public int hashCode() {
        return java.util.Objects.hash(paciente.getRut(), medico.getRut(), fechaHora);
    }
}