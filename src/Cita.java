// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Cita {
   private Paciente paciente;
   private Medico medico;
   private LocalDateTime fechaHora;
   private String estado;
   private String observaciones;
   public static final String ESTADO_AGENDADA = "AGENDADA";
   public static final String ESTADO_CONFIRMADA = "CONFIRMADA";
   public static final String ESTADO_CANCELADA = "CANCELADA";
   public static final String ESTADO_COMPLETADA = "COMPLETADA";
   public static final String ESTADO_NO_ASISTIO = "NO_ASISTIO";

   public Cita(Paciente var1, Medico var2, LocalDateTime var3) {
      this.paciente = var1;
      this.medico = var2;
      this.fechaHora = var3;
      this.estado = "AGENDADA";
      this.observaciones = "";
   }

   public Cita(Paciente var1, Medico var2, LocalDateTime var3, String var4) {
      this(var1, var2, var3);
      this.observaciones = var4;
   }

   public Paciente getPaciente() {
      return this.paciente;
   }

   public void setPaciente(Paciente var1) {
      this.paciente = var1;
   }

   public Medico getMedico() {
      return this.medico;
   }

   public void setMedico(Medico var1) {
      this.medico = var1;
   }

   public LocalDateTime getFechaHora() {
      return this.fechaHora;
   }

   public void setFechaHora(LocalDateTime var1) {
      this.fechaHora = var1;
   }

   public String getEstado() {
      return this.estado;
   }

   public void setEstado(String var1) {
      this.estado = var1;
   }

   public String getObservaciones() {
      return this.observaciones;
   }

   public void setObservaciones(String var1) {
      this.observaciones = var1;
   }

   public void confirmarCita() {
      this.estado = "CONFIRMADA";
   }

   public void cancelarCita() {
      this.estado = "CANCELADA";
   }

   public void completarCita() {
      this.estado = "COMPLETADA";
   }

   public void marcarNoAsistio() {
      this.estado = "NO_ASISTIO";
   }

   public boolean estaActiva() {
      return this.estado.equals("AGENDADA") || this.estado.equals("CONFIRMADA");
   }

   public boolean puedeSerCancelada() {
      return this.estaActiva();
   }

   public String getInformacionCompleta() {
      DateTimeFormatter var1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
      StringBuilder var2 = new StringBuilder();
      var2.append("=== INFORMACIÓN DE CITA ===\n");
      var2.append("\ud83d\udcc5 Fecha y Hora: ").append(this.fechaHora.format(var1)).append("\n");
      var2.append("\ud83d\udc64 Paciente: ").append(this.paciente.getNombre()).append("\n");
      var2.append("\ud83c\udd94 RUT Paciente: ").append(this.paciente.getRut()).append("\n");
      var2.append("\ud83d\udc68\u200d⚕️ Médico: Dr/a. ").append(this.medico.getNombre()).append("\n");
      var2.append("\ud83c\udfe5 Especialidad: ").append(this.medico.getEspecialidad()).append("\n");
      var2.append("\ud83d\udccb Estado: ").append(this.estado).append("\n");
      if (!this.observaciones.isEmpty()) {
         var2.append("\ud83d\udcdd Observaciones: ").append(this.observaciones).append("\n");
      }

      return var2.toString();
   }

   public String getResumen() {
      DateTimeFormatter var1 = DateTimeFormatter.ofPattern("dd/MM HH:mm");
      String var10000 = this.fechaHora.format(var1);
      return var10000 + " - " + this.medico.getNombre() + " (" + this.medico.getEspecialidad() + ") - " + this.estado;
   }

   public long getHorasParaCita() {
      LocalDateTime var1 = LocalDateTime.now();
      return Duration.between(var1, this.fechaHora).toHours();
   }

   public boolean esHoy() {
      return this.fechaHora.toLocalDate().equals(LocalDateTime.now().toLocalDate());
   }

   public boolean esManana() {
      return this.fechaHora.toLocalDate().equals(LocalDateTime.now().plusDays(1L).toLocalDate());
   }

   public boolean yaPaso() {
      return this.fechaHora.isBefore(LocalDateTime.now());
   }

   public String getTiempoRestante() {
      if (this.yaPaso()) {
         return "Cita pasada";
      } else {
         LocalDateTime var1 = LocalDateTime.now();
         Duration var2 = Duration.between(var1, this.fechaHora);
         long var3 = var2.toDays();
         long var5 = var2.toHours() % 24L;
         long var7 = var2.toMinutes() % 60L;
         if (var3 > 0L) {
            return "" + var3 + " día(s), " + var5 + " hora(s)";
         } else {
            return var5 > 0L ? "" + var5 + " hora(s), " + var7 + " minuto(s)" : "" + var7 + " minuto(s)";
         }
      }
   }

   public String toString() {
      DateTimeFormatter var1 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
      String var10000 = this.paciente.getNombre();
      return "Cita{paciente=" + var10000 + ", medico=" + this.medico.getNombre() + ", fechaHora=" + this.fechaHora.format(var1) + ", estado='" + this.estado + "'}";
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         Cita var2 = (Cita)var1;
         return this.paciente.equals(var2.paciente) && this.medico.equals(var2.medico) && this.fechaHora.equals(var2.fechaHora);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(new Object[]{this.paciente.getRut(), this.medico.getRut(), this.fechaHora});
   }
}
