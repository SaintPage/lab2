public class Dia {
    public String nombre;
    public int[] horario = new int[14];
    public Dia(String nombre){
        this.nombre = nombre;
    }

    public int[] getHorario(){
        return this.horario;
    }
}
