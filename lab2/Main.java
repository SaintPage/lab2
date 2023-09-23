////Ángel de Jesús Mérida Jiménez Carné:23661
// Programa de tener cursos y salones por separados y poder asignarlos entre si
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import com.opencsv.CSVReader;

/*
 * para compilar:
 * javac -cp ".;./lib/commons-lang3-3.13.0.jar;./lib/opencsv-5.8.jar" Main.java Curso.java Dia.java Salon.java 
 * para correr:
 *  java -cp ".;opencsv-5.8.jar;commons-lang3-3.13.0.jar" Main 
 */
public class Main{
    public static void main(String[] args){
        boolean salir = false;
    
        ArrayList<Salon> salones = new ArrayList<Salon>();
        ArrayList<Curso> cursos = new ArrayList<Curso>();
        ArrayList<Curso> cursosAsignados = new ArrayList<Curso>();
        ArrayList<Curso> cursosNoAsignados = new ArrayList<Curso>();

        while (!salir) {
            mostrarMenu();
            Scanner sc = new Scanner(System.in);
            int option = sc.nextInt();
            String[] fila = null;
            switch (option) {
                case 1:
                    try{
                        salones.clear();
                        CSVReader csvReader = new CSVReader(new FileReader("./files/salones.csv"));
                        int count = 0;
                        
                        while( (fila = csvReader.readNext()) != null ){
                            if (count == 0){
                                count++;
                                continue;
                            }
                            Salon aux = new Salon();
                            //usar setters
                            aux.setIdSede(Integer.parseInt(fila[0]));
                            aux.setEdificio(fila[1].charAt(0));
                            aux.setNivel( Integer.parseInt(fila[2])); 
                            aux.setIdSalon(Integer.parseInt(fila[3])); 
                            aux.setCapacidad( Integer.parseInt(fila[4]));
                            salones.add(aux);
                            // System.out.println(fila[0]+"|"+ fila[1] +"|"+ fila[2] +"|"+ fila[3] +"|"+ fila[4]);
                        }
                        System.out.println("Archivo de salones cargado existosamente!!");
                    }
                    catch(Exception e){
                        System.out.println("Error en la fila-> "+fila);
                        System.out.println("error");
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    try{
                        cursos.clear();
                        CSVReader csvReader = new CSVReader(new FileReader("./files/cursos.csv"));
                        int count = 0;
                        fila = null;
                        while( (fila = csvReader.readNext()) != null ){
                            if (count == 0){
                                count++;
                                continue;
                            }
                            Curso aux = new Curso();
                            aux.setIdCurso(Integer.parseInt(fila[0]));;
                            aux.setIdSede(Integer.parseInt(fila[1]));;
                            aux.setNombreCurso(fila[2]);
                            aux.setHorario((Integer.parseInt(fila[3])-7));;
                            aux.setDuracion(Integer.parseInt(fila[4]));;
                            aux.setDias(fila[5].trim().split(","));;
                            aux.setCantEstudiantes(Integer.parseInt(fila[6]));;
                            System.out.println(aux);
                            cursos.add(aux);
                        }
                        System.out.println("Archivo de cursos cargado existosamente!!");
                        // asignarCursosASalones(cursos, salones);
                        // boolean seguir = true;
                        boolean asignado = false;
                        for(int i=0; i< cursos.size(); i++){
                            Curso cursoAux = cursos.get(i);
                            // seguir = true;
                            asignado = false;
                            for(int j=0; j<salones.size(); j++){
                                Salon salonAux = salones.get(j);
                                if(salonAux.getIdSede() == cursoAux.getIdSede() && salonAux.getCapacidad()>= cursoAux.getCantEstudiantes()){
                                    ArrayList<Dia> copiaDias = new ArrayList<Dia>(salonAux.getDias());
                                    String[] diasArray = cursoAux.getDias().split(",");
                                    for (String dia : diasArray) {
                                        if(validarHorario(dia, salonAux, cursoAux)){
                                            asignado = true;
                                        }
                                        else{
                                            asignado = false;
                                            salonAux.setDias(copiaDias);
                                        }
                                    }
                                }
                                if(asignado){
                                    cursosAsignados.add(cursoAux);
                                    break;
                                }
                                if(j==salones.size()){
                                    cursosNoAsignados.add(cursoAux);
                                }
                            }
                        }
                        //
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    System.out.println("Ingrese el id del salon a buscar");
                    Scanner scs = new Scanner(System.in);
                    int idSalon = scs.nextInt();
                    Salon salonAux = null;
                    for(int i=0; i< salones.size(); i++){
                        if(idSalon == salones.get(i).getIdSalon()){
                            salonAux = salones.get(i);
                            break;
                        }
                    }
                    if(salonAux == null){
                        System.out.println("Ese salon no esta registrado");
                        break;
                    }
                    System.out.println("---------------");
                    System.out.println("Sede: " +salonAux.getIdSede());
                    System.out.println("---------------");
                    System.out.println("Edificio: "+ salonAux.getEdificio());
                    System.out.println("---------------");
                    System.out.println("Nivel: "+ salonAux.getNivel());
                    System.out.println("---------------");
                    System.out.println("Id del salon: "+ salonAux.getIdSalon());
                    System.out.println("---------------");

                    System.out.println();
                    System.out.println();
                    
                    dibujarTablaSalon(salonAux);

                    break;
                case 4:
                    System.out.println("Ingrese el id del curso a buscar");
                    Scanner scc = new Scanner(System.in);
                    int idCurso = scc.nextInt();
                    Curso cursoAux = null;
                    for(int i=0; i< cursos.size(); i++){
                        if(idCurso == cursos.get(i).getIdCurso()){
                            cursoAux = cursos.get(i);
                            break;
                        }
                    }
                    if(cursoAux == null){
                        System.out.println("El curso no esta registrado");
                        break;
                    }

                    System.out.println("id: "+cursoAux.getIdCurso());
                    System.out.println("Sede: "+cursoAux.getIdSede());
                    System.out.println("Nombre del curso: "+ cursoAux.getNombreCurso());
                    System.out.println("Horario: "+cursoAux.getHorioCompleto());
                    System.out.println("Cantidad de estudiantes: "+ cursoAux.getCantEstudiantes());
                    break;
                case 5:
                    System.out.println("Cursos Asignados");
                    // System.out.println(cursosAsignados.size());
                    for (Curso cursoA : cursosAsignados) {
                        System.out.println(cursoA);
                    }
                    break;
                case 6:
                    System.out.println("Cursos no asignados");
                    for (Curso cursoNA : cursosNoAsignados) {
                        System.out.println(cursoNA);
                    }
                    break;
                case 7:
                    salir = true;
                    break;
                default:
                    System.out.println("OPCION NO VALIDA");
            }
            System.out.println("-----------------------------");
        }
    }

    public static void asignarCursosASalones(ArrayList<Curso> cursos, ArrayList<Salon> salones){
        boolean seguir = true;
        for(int i=0; i< cursos.size(); i++){
            Curso cursoAux = cursos.get(i);
            seguir = true;
            for(int j=0; j<salones.size(); j++){
                Salon salonAux = salones.get(j);
                if(salonAux.getIdSede() == cursoAux.getIdSede() && salonAux.getCapacidad()>= cursoAux.getCantEstudiantes()){
                    ArrayList<Dia> copiaDias = new ArrayList<Dia>(salonAux.getDias());
                    String[] diasArray = cursoAux.getDias().split(",");
                    for (String dia : diasArray) {
                        if(!validarHorario(dia, salonAux, cursoAux)){
                            seguir = false;
                            salonAux.setDias(copiaDias);
                            
                        }
                    }
                }
                if(!seguir){
                    break;
                }
            }
        }
    }

    public static boolean validarHorario(String dia, Salon salon, Curso curso){
        boolean res = true;
        if(dia=="lunes"){
            if(salon.getDias().get(0).getHorario()[curso.getHorario()] != 0){
                res = false;
            }
            salon.getDias().get(0).getHorario()[curso.getHorario()] = curso.getIdCurso();
            if(curso.getDuracion() == 2)
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
            if(curso.getDuracion() == 3){
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
                salon.getDias().get(0).getHorario()[curso.getHorario() + 2] = curso.getIdCurso();
            }    
        }
        if(dia=="martes"){
            if(salon.getDias().get(0).getHorario()[curso.getHorario()] != 0){
                res = false;
            }
            salon.getDias().get(0).getHorario()[curso.getHorario()] = curso.getIdCurso();
            if(curso.getDuracion() == 2)
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
            if(curso.getDuracion() == 3){
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
                salon.getDias().get(0).getHorario()[curso.getHorario() + 2] = curso.getIdCurso();
            }    
        }
        if(dia=="miercoles"){
            if(salon.getDias().get(0).getHorario()[curso.getHorario()] != 0){
                res = false;
            }
            salon.getDias().get(0).getHorario()[curso.getHorario()] = curso.getIdCurso();
            if(curso.getDuracion() == 2)
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
            if(curso.getDuracion() == 3){
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
                salon.getDias().get(0).getHorario()[curso.getHorario() + 2] = curso.getIdCurso();
            }    
        }
        if(dia=="jueves"){
            if(salon.getDias().get(0).getHorario()[curso.getHorario()] != 0){
                res = false;
            }
            salon.getDias().get(0).getHorario()[curso.getHorario()] = curso.getIdCurso();
            if(curso.getDuracion() == 2)
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
            if(curso.getDuracion() == 3){
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
                salon.getDias().get(0).getHorario()[curso.getHorario() + 2] = curso.getIdCurso();
            }    
        }
        if(dia=="viernes"){
            if(salon.getDias().get(0).getHorario()[curso.getHorario()] != 0){
                res = false;
            }
            salon.getDias().get(0).getHorario()[curso.getHorario()] = curso.getIdCurso();
            if(curso.getDuracion() == 2)
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
            if(curso.getDuracion() == 3){
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
                salon.getDias().get(0).getHorario()[curso.getHorario() + 2] = curso.getIdCurso();
            }    
        }
        if(dia=="sabado"){
            if(salon.getDias().get(0).getHorario()[curso.getHorario()] != 0){
                res = false;
            }
            salon.getDias().get(0).getHorario()[curso.getHorario()] = curso.getIdCurso();
            if(curso.getDuracion() == 2)
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
            if(curso.getDuracion() == 3){
                salon.getDias().get(0).getHorario()[curso.getHorario() + 1] = curso.getIdCurso();
                salon.getDias().get(0).getHorario()[curso.getHorario() + 2] = curso.getIdCurso();
            }    
        }

        return res;
    }

    public static void mostrarMenu(){
        System.out.println("Menu");
        System.out.println("Elija una opcion");
        System.out.println("1. Cargar archivo de salones");
        System.out.println("2. Cargar archivo de cursos");
        System.out.println("3. Consultar salon");
        System.out.println("4. Consultar curso");
        System.out.println("5. Consultar cursos asignados");
        System.out.println("6. Consultar cursos no asignados");
        System.out.println("7. Salir");
    }

    public static void dibujarTablaSalon(Salon salon){
        int lenRow = 13;
        int separator = 9;
        System.out.print("|"+completeTable(lenRow, "dia/horario")+"|");
        System.out.print(completeTable(lenRow, "7:00 a 8:00")+"|");
        System.out.print(completeTable(lenRow, "8:00 a 9:00")+"|");
        System.out.print(completeTable(lenRow, "9:00 a 10:00")+"|");
        System.out.print(completeTable(lenRow, "10:00 a 11:00")+"|");
        System.out.print(completeTable(lenRow, "11:00 a 12:00")+"|");
        System.out.print(completeTable(lenRow, "12:00 a 13:00")+"|");
        System.out.print(completeTable(lenRow, "13:00 a 14:00")+"|");
        System.out.print(completeTable(lenRow, "14:00 a 15:00")+"|");
        System.out.print(completeTable(lenRow, "15:00 a 16:00")+"|");

        System.out.println();
        for(int i=0;i<salon.getDias().size(); i++){
            Dia diaInfo = salon.getDias().get(i);
            System.out.print("|"+completeTable(lenRow, diaInfo.nombre)+"|");
            for(int j=0;j<diaInfo.horario.length; j++){
                if(j==separator) break;
                if(diaInfo.horario[j] == 0){
                    System.out.print(completeTable(lenRow, "libre")+"|");
                }
                else{
                    System.out.print(completeTable(lenRow, "id: "+diaInfo.horario[j]+"")+"|");
                }
            }
            System.out.println();
        }

        System.out.println();
        System.out.print("|"+completeTable(lenRow, "dia/horario")+"|");
        System.out.print(completeTable(lenRow, "16:00 a 17:00")+"|");
        System.out.print(completeTable(lenRow, "17:00 a 18:00")+"|");
        System.out.print(completeTable(lenRow, "18:00 a 19:00")+"|");
        System.out.print(completeTable(lenRow, "19:00 a 20:00")+"|");
        System.out.println(completeTable(lenRow, "20:00 a 21:00")+"|");

        for(int i=0;i<salon.getDias().size(); i++){
            Dia diaInfo = salon.getDias().get(i);
            System.out.print("|"+completeTable(lenRow, diaInfo.nombre)+"|");
            for(int j=separator;j<diaInfo.horario.length; j++){
                if(diaInfo.horario[j] == 0){
                    System.out.print(completeTable(lenRow, "libre")+"|");
                }
                else{
                    System.out.print(completeTable(lenRow, "id: "+diaInfo.horario[j]+"")+"|");
                }
            }
            System.out.println();
        }

    }

    public static String completeTable(int len, String message){
        return String.format("%-"+len+"s", message);
    }
}