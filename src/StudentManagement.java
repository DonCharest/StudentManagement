import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

/*******************************************************************************
 * 
 * @author Don Charest Date 03/11/17, Due: 03/7/17 CS342-B1 Spring 2017 Homework
 *         Assignment 5 StudentManagement Class
 * 
 *******************************************************************************
 */

public class StudentManagement
{ // Key = ID, Value = Student Object
	static HashMap<String, Student> hmap = new HashMap<String, Student>();
	// ArrayList of Student Objects
	static ArrayList<Student> students = new ArrayList<Student>();

	public static void main(String[] args)
	{
		RunMenu();
	} // End Method: main()

	public static class Student
	{ // class variables
		String name;
		String major;
		LinkedList<Course> courses;
		ArrayList<Integer> gradePoints;
		ArrayList<Integer> credits;

		// constructor
		public Student(String name, String major)
		{
			super();
			this.name = name;
			this.major = major;
			courses = new LinkedList<Course>();
			gradePoints = new ArrayList<Integer>();
			credits = new ArrayList<Integer>();
		}
	} // End Inner Class: Student

	public static class Course
	{ // class variables
		String courseNumber;
		String courseName;
		int numberOfCredits; // 1, 2, 3, or 4
		char grade; // valid value is A, B, C, D, or F
		// constructor

		public Course(String courseNumber, String courseName, int numberOfCredits, char grade)
		{
			super();
			this.courseNumber = courseNumber;
			this.courseName = courseName;
			this.numberOfCredits = numberOfCredits;
			this.grade = grade;
		}

		@Override
		public String toString()
		{
			return courseNumber + ", " + courseName + ", " + grade;
		}

	} // End Inner Class: Course

	/**
	 * Method: RunMenu - runs through the user menu options.
	 */
	public static void RunMenu()
	{
		boolean running = true;
		while (running)
		{
			Scanner keyboard = new Scanner(System.in);
			System.out.println("\nChoose an option:\n" + " \n1.  Add a student" + " \n2.  Add a course"
					+ " \n3.  Remove a student" + " \n4.  Find a student" + " \n5.  Display student information"
					+ " \n6.  Exit" + "	\n ");

			String input = keyboard.nextLine();
			switch (input)
			{

			case "1": // Add a student
				// get student id
				System.out.println("Enter Student's ID: ");
				String iD1 = keyboard.nextLine().toUpperCase();
				// get student name
				System.out.println("Enter Student's Name: ");
				String name = keyboard.nextLine().toUpperCase();
				// get students major
				System.out.println("Enter a Student's Major: ");
				String major = keyboard.nextLine().toUpperCase();
				// check if id already in use
				if (hmap.containsKey(iD1))
				{ // id already assigned to another student
					System.out.println("ID entered already assigned to a student. Please try again.\n");
					break;
				} else // id is unique
				{ // call method to add new student object
					addStudent(iD1, name, major);
				}

				break;

			case "2": // Add a course record
				// get student id
				System.out.println("Enter Student's ID: ");
				String iD2 = keyboard.nextLine().toUpperCase();
				// get course number
				System.out.println("Enter course number: ");
				String courseNumber = keyboard.nextLine().toUpperCase();
				// get course name
				System.out.println("Enter course name: ");
				String courseName = keyboard.nextLine().toUpperCase();
				// get credits
				System.out.println("Enter number of credits: ");
				int numberOfCredits = keyboard.nextInt();
				// get grade
				System.out.println("Enter grade: ");
				char grade = keyboard.next().toUpperCase().charAt(0);
				// Calculate course grade points
				int g = 0;
				if (grade == 'A')
				{
					g = 4;
				} else
					if (grade == 'B')
					{
						g = 3;
					} else
						if (grade == 'C')
						{
							g = 2;
						} else
							if (grade == 'D')
							{
								g = 1;
							} else
								if (grade == 'F')
								{
									g = 0;
								}
				int gradePoints = numberOfCredits * g;
				// check if student id is valid
				if (hmap.containsKey(iD2))
				{ // id valid
					Student student = hmap.get(iD2);
					addCourse(iD2, student, courseNumber, courseName, numberOfCredits, grade);
					// Add course grade point to ArrayList
					student.gradePoints.add(gradePoints);
					// Add course credits to ArrayList
					student.credits.add(numberOfCredits);
					break;
				} else
				{	// id not valid
					System.out.println("ID entered does not exist in database.\n");
				}
				break;

			case "3": // Remove a student
				// get student id
				System.out.println("Enter Student ID: ");
				String iD3 = keyboard.nextLine().toUpperCase();
				// check if id is valid
				if (hmap.containsKey(iD3))
				
				{ // id valid
					Student student = hmap.get(iD3);
					removeStudent(iD3, student);
					break;
				} else
				{ // id not valid
					System.out.println("ID entered does not exist in database.\n");
				}
				break;
					
			case "4": // Find a student
				// get student id
				System.out.println("Enter Student ID: ");
				String iD4 = keyboard.nextLine().toUpperCase();
				// check if id valid
				if (hmap.containsKey(iD4))
				
				{ // id valid
					Student student = hmap.get(iD4);
					findStudent(iD4, student);
					break;
				}else
				{ // id not valid
					System.out.println("ID entered does not exist in database.\n");	
				} 
				break;

			case "5": // Display student information
				// get student id
				System.out.println("Enter Student ID: ");
				String iD5 = keyboard.nextLine().toUpperCase();
				// check if id valid
				if (hmap.containsKey(iD5))
				{ // id valid
					Student student = hmap.get(iD5);
					findCourseRecord(iD5, student);	
					break;
				} else	
				{ // id not valid
					System.out.println("ID entered does not exist in database.\n");
				}
				break;

			case "6": // Exit
				System.out.println("Bye\n");
				keyboard.close(); // Close Scanner class
				System.exit(0); // Exit program
				break;

			default: // Invalid entry --> Valid response between "1" & "9".
				System.out.println("Please choose a valid menu option\n");
				break;
			}
		}
	}// End Method: RunMenu()

	/**
	 * Method to add a new Student object to the management system
	 * 
	 * @param id
	 *            - hashMap key (unique string)
	 * @param name
	 *            - Student object String
	 * @param major
	 *            - Student object String
	 * 
	 * @precondition - Student ID must be unique - cannot already exist in data
	 *              structure
	 * @postcondition - Student object is added to hasMap as the key, and to
	 *                ArrayList (students).
	 */
	private static void addStudent(String id, String name, String major)
	{
		Student student = new Student(name, major);
		// add new student to ArrayList (student)
		students.add(student);
		// add key/value from hashMap
		hmap.put(id, student);
		System.out.println("New Student Record Added\n");
	}

	/**
	 * Method to add a new course record to a student object
	 * 
	 * @param id
	 *            - hashMap key (unique string)
	 * @param student
	 *            - hashMap value: Student object contains String name, Sting
	 *            major, linkedList courses, ArrayList gradePoints, and
	 *            ArrayList credits.
	 * @param courseNumber
	 *            - Course object String
	 * @param courseName
	 *            - Course object String
	 * @param numberOfCredits
	 *            - Course object Integer
	 * @param grade
	 *            - Course object Char
	 * @precondition - Student object must exist in ArrayList (students)
	 * @postcondition - Course object is added to Student object LinkedList
	 *                (courses).
	 */
	private static void addCourse(String id, Student student, String courseNumber, String courseName,
			int numberOfCredits, char grade)
	{
		// findStudent(id, student);
		for (int i = 0; i < students.size(); i++)
		{
			if (students.get(i).name.equals(student.name))
			{ // Add course information to student object linkedList(courses)
				student.courses.add(new Course(courseNumber, courseName, numberOfCredits, grade));
			}
		}
	}

	/**
	 * Method to remove a student object
	 * 
	 * @param id
	 *            - hashMap key (unique string)
	 * @param student
	 *            - hashMap value: Student object contains String name, Sting
	 *            major, linkedList courses, ArrayList gradePoints, and
	 *            ArrayList credits.
	 * @precondition - student id must exist in hasMap
	 * @postcondition - student object is removed from hashMap and ArrayList
	 */
	private static void removeStudent(String id, Student student)
	{
		System.out.println("Student Record Removed.\n");
		// remove key/value from hashMap
		hmap.remove(id);
		for (int i = 0; i < students.size(); i++)
		{
			if (students.get(i).name.equals(student.name))
			{
				students.remove(students.get(i));
			}
		}
	}

	/**
	 * Method to find & display a student object using the hashMap key
	 * 
	 * @param id
	 *            - hashMap key (unique string)
	 * @param student
	 *            - hashMap value: Student object contains String name, Sting
	 *            major, linkedList courses, ArrayList gradePoints, and
	 *            ArrayList credits.
	 * @precondition - student id must exist in hasMap
	 * @postcondition - student information is displayed (ID, Name, & Major)
	 */
	private static void findStudent(String id, Student student)
	{
		for (int i = 0; i < students.size(); i++)
		{
			if (students.get(i).name.equals(student.name))
			{ // display student id, name, major
				System.out.println("\nSTUDENT ID: " + id + ", STUDENT NAME: " + students.get(i).name
						+ ", STUDENT MAJOR: " + students.get(i).major + "\n");
			}
		}
	}

	/**
	 * Method to find & display a student object and details using the hashMap
	 * key
	 * 
	 * @param id
	 *            - hashMap key (unique string)
	 * @param student
	 *            - hashMap value: Student object contains String name, Sting
	 *            major, linkedList courses, ArrayList gradePoints, and
	 *            ArrayList credits.
	 * @precondition - student id must exist in hasMap
	 * @postcondition - student information (ID, Name, & Major), course record
	 *                (course number, course name, grade received), and
	 *                cumulative GPA is displayed
	 */
	private static void findCourseRecord(String id, Student student)
	{
		for (int i = 0; i < students.size(); i++)
		{
			if (students.get(i).name.equals(student.name))
			{ // display student id, name, major
				System.out.println("\nSTUDENT ID: " + id + ", STUDENT NAME: " + students.get(i).name
						+ ", STUDENT MAJOR: " + students.get(i).major + "\n");

				// display course records
				for (Course s : student.courses)
					System.out.println(s);
			}
		}
		// Output Student Cumulative GPA
		double totalGradePoints = 0.0;
		for (int j = 0; j < student.gradePoints.size(); j++)
		{
			totalGradePoints += student.gradePoints.get(j);
		}
		double totalCredits = 0.0;
		for (int k = 0; k < student.credits.size(); k++)
		{
			totalCredits += student.credits.get(k);
		}
		double cumulativeGPA = (totalGradePoints / totalCredits);
		System.out.printf("Cumulative GPA: %.2f\n", cumulativeGPA);
	}

} // End Outer Class: StudentRecord
