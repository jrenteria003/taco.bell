package resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Util {

	public static String fechaToString(LocalDate fecha) {
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String wfecha;
		
		wfecha = fecha.format(formateador);
		
		return wfecha;
	}
	public static LocalDate leerFechaDMA() {
		boolean error;
		LocalDate date = null;
		String dateString;
		DateTimeFormatter formateador=DateTimeFormatter.ofPattern("dd/MM/yyyy");
		do{
			error=false;
			dateString=introducirCadena();
			try{
				date=LocalDate.parse(dateString, formateador);
			}catch(DateTimeParseException e){
				System.out.println("Error, introduce una fecha en formato dd/mm/aaaa ");
				error=true;
			}
		}while (error);
		return date;
	}

	public static LocalDate leerFechaAMD() {
		boolean error;
		LocalDate date = null;
		String dateString;
		DateTimeFormatter formateador=DateTimeFormatter.ofPattern("yyyy/MM/dd");
		do{
			error=false;
			dateString=introducirCadena();
			try{
				date=LocalDate.parse(dateString, formateador);
			}catch(DateTimeParseException e){
				System.out.println("Error, uuuu/hh/ee ");
				error=true;
			}
		}while (error);
		return date;
	}
	public static char leerChar(char opt1, char opt2) {
		char letra=' ';
		String cadena;
		boolean error;
		do{
			error=false;
			cadena=introducirCadena();
			if (cadena.length()!=1){
				System.out.println("Error, introduce un �nico caracter: ");
				error=true;
			}
			else{
				letra=cadena.charAt(0);
				letra=Character.toUpperCase(letra);
				if (letra!=opt1 && letra!=opt2){
					System.out.println("Error, la opci�n introducida no es correcta, introduce "+ opt1+ " o "+ opt2);
					error=true;
				}
			}
		}while (error);
			
		return letra;
	}

	public static char leerChar() {
		char letra=' ';
		String cadena;
		boolean error;
		do{
			error=false;
			cadena=introducirCadena();
			if (cadena.length()!=1){
				System.out.println("Error, introduce un �nico caracter: ");
				error=true;
			}
		}while (error);
		letra=cadena.charAt(0);
		return letra;
	}

	public static float leerFloat() {
		float num = 0;
		boolean error;
		do{
			error=false;
			try{
				num=Float.parseFloat(introducirCadena());
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
			}
		}while (error);
		return num;
	}

	public static float leerFloat(String message, float min, float max) {
		float num = 0;
		boolean error;
		System.out.println(message);
		do{
			error=false;
			try{
				num=Float.parseFloat(introducirCadena());
				
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
				num=min;
			}
			if(num<min || num>max){
				System.out.println("N� fuera de rango, introduce n� entre "+ min+ " y "+ max+": ");
				error=true;
			}
		}while (error);
		return num;
	}

	public static float leerFloat(float min, float max) {
		float num = 0;
		boolean error;
		do{
			error=false;
			try{
				num=Float.parseFloat(introducirCadena());
				
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
				num=min;
			}
			if(num<min || num>max){
				System.out.println("N� fuera de rango, introduce n� entre "+ min+ " y "+ max+": ");
				error=true;
			}
		}while (error);
		return num;
	}

	public static int leerInt(String message, int min, int max) {
		int num = 0;
		boolean error;
		System.out.println(message);
		do{
			error=false;
			try{
				num=Integer.parseInt(introducirCadena());
				
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
				num=min;
			}
			if(num<min || num>max){
				System.out.println("N� fuera de rango, introduce n� entre "+ min+ " y "+ max+": ");
				error=true;
			}
		}while (error);
		return num;
	}

	public static int leerInt(int min, int max) {
		int num = 0;
		boolean error;
		do{
			error=false;
			try{
				num=Integer.parseInt(introducirCadena());
				
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
				num=min;
			}
			if(num<min || num>max){
				System.out.println("N� fuera de rango, introduce n� entre "+ min+ " y "+ max+": ");
				error=true;
			}
		}while (error);
		return num;
	}

	public static int leerInt() {
		int num = 0;
		boolean error;
		do{
			error=false;
			try{
				num=Integer.parseInt(introducirCadena());
			}catch (NumberFormatException e){
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error=true;
			}
		}while (error);
		return num;
	}

	public static String introducirCadena() {
		String cadena = "";
		boolean error;
		InputStreamReader entrada =new InputStreamReader(System.in);
		BufferedReader teclado= new BufferedReader(entrada);
		do{
			error=false;
			try {
				cadena=teclado.readLine();
			} catch (IOException e) {
				System.out.println("Error en la entrada de datos");
				error=true;
			}
		}while (error);
		return cadena;
	}
}