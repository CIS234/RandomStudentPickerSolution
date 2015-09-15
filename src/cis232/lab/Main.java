package cis232.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

	private static final String STUDENTS_FILE = "students.csv";
	//private static final String STUDENTS_DYNAMIC =	"students2.csv";
	private Random random = new Random();
	private ArrayList<Student> students;
	private ArrayList<Student> students_dynamic;
	private Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) throws IOException {
		Main main = new Main();
		main.run();

		System.out.println("Hope everyone enjoys their bonus points!");
	}
	
	public Main() throws IOException{
		loadStudentsFromFile();
		load_dynamic();
	}
	
	public void load_dynamic() throws IOException{
		File originalFile = new File(STUDENTS_FILE);
		Scanner input = new Scanner(originalFile);
		students_dynamic = new ArrayList<>();
		
		while(input.hasNextLine()){
			StringTokenizer tokens = new StringTokenizer(input.nextLine(), ",");
			students_dynamic.add(new Student(tokens.nextToken(), Integer.parseInt(tokens.nextToken())));
		}
		input.close();
		
	}
	
	public void run() throws IOException{
		do{
			while(askToPickStudent()){
					Student student = pickRandomStudent();
					System.out.println(student);
					System.out.printf("Did %s get it right? (y/n)%n", student);
					
					if(keyboard.nextLine().equalsIgnoreCase("y")){
						student.addPoint();
						System.out.printf("Great Job +1 point. %s has %d points.%n",
								student, student.getPoints());
						students_dynamic.remove(student);
						saveStudentsToFile();
						
					}else{
						System.out.printf("Better luck next time! %s has %d points.%n",
								student, student.getPoints());
						//students_dynamic.remove(student);
						
					}
					
					if(students_dynamic.isEmpty()){
						System.out.println("\nList empty. Restarting List\n");
						load_dynamic();
					}
			}
		}while(!students_dynamic.isEmpty());
	}

	private Student pickRandomStudent() {
		Student student = students_dynamic.get(random.nextInt(students_dynamic.size()));
		return student;
	}

	private boolean askToPickStudent() {
		System.out.println("Pick a student? (y/n)");
		String pickInput = keyboard.nextLine();
		return pickInput.equalsIgnoreCase("y");
	}

	private void loadStudentsFromFile() throws FileNotFoundException {
		File originalFile = new File(STUDENTS_FILE);
		Scanner input = new Scanner(originalFile);
		students = new ArrayList<>();
		
		while(input.hasNextLine()){
			StringTokenizer tokens = new StringTokenizer(input.nextLine(), ",");
			students.add(new Student(tokens.nextToken(), Integer.parseInt(tokens.nextToken())));
		}
		
		input.close();
	}
	
	private void saveStudentsToFile() throws IOException{
		PrintWriter output = new PrintWriter(STUDENTS_FILE);
		for(Student s : students){
			output.println(s.toCsvString());
		}
		output.close();
	}
	
	private void saveStudentsToFile2() throws IOException{
		PrintWriter output = new PrintWriter(STUDENTS_FILE);
		for(Student s : students_dynamic){
			output.println(s.toCsvString());
		}
		output.close();
	}

}
