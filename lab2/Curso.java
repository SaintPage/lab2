public class Curso {
    private int idCurso;
    private int idSede;
    private String nombreCurso;
    private int horario; //arhicvo 7-21, clase 0-14
    private int duracion;//1-3
    private String[] dias;
    private int cantEstudiantes; //1-60

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdSede() {
        return idSede;
    }

    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String nombreCurso) {
        this.nombreCurso = nombreCurso;
    }

    public int getHorario() {
        return horario;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getDias(){
        return String.join(",", this.dias);
    }

    public void setDias(String[] dias) {
        this.dias = dias;
    }

    public int getCantEstudiantes() {
        return cantEstudiantes;
    }

    public void setCantEstudiantes(int cantEstudiantes) {
        this.cantEstudiantes = cantEstudiantes;
    }

    public String getHorioCompleto(){
        return (this.horario + 7) + ":00 hrs " +getDias();
    }

    @Override
    public String toString(){
        return "curso: "+this.nombreCurso + " horario: "+(this.horario + 7)+":00 estudiantes: "+this.cantEstudiantes;
    }
}