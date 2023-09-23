import java.util.ArrayList;

public class Salon {
    private int idSede;
    private char edificio;
    private int nivel; //1-10
    private int idSalon; // 1-99
    private int capacidad;//10-150
    private ArrayList<Dia> dias = new ArrayList<Dia>();

    public Salon(){
        dias.add(new Dia("lunes"));
        dias.add(new Dia("martes"));
        dias.add(new Dia("miercoles"));
        dias.add(new Dia("jueves"));
        dias.add(new Dia("viernes"));
        dias.add(new Dia("sabado"));
    }
    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    
    public void setIdSalon(int idSalon) {
        this.idSalon = idSalon;
    }
    
    public int getCapacidad() {
        return capacidad;
    }
    
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }
    
    public ArrayList<Dia> getDias() {
        return dias;
    }
    
    public void setDias(ArrayList<Dia> dias) {
        this.dias = dias;
    }
    
    public void setIdSede(int idSede) {
        this.idSede = idSede;
    }

    public void setEdificio(char edificio) {
        this.edificio = edificio;
    }
    public int getIdSede(){
        return this.idSede;
    }
    public char getEdificio(){
        return this.edificio;
    }
    public int getNivel(){
        return this.nivel;
    }
    public int getIdSalon(){
        return this.idSalon;
    }
    @Override
    public String toString(){
        return this.idSalon + " salon-> sede:"+ this.idSede;
    }
}