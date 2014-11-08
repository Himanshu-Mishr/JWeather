class ForCharacter {
	public static void main ( String args[]) 
			throws java.io.IOException{


		int i ;
		char choice = 0;

		for ( i = 0 ; choice != 'S' ; i++) {
			System.out.println("-------------------------");
			System.out.println("Enter S to end the for loop :> " );
			choice = (char) System.in.read();
			System.out.println("i is " + i );
		}
	}
}
