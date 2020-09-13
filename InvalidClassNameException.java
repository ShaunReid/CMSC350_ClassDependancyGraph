/*CMSC350 
 8 Mar 2020
 Shaun Reid
 
 Custom exception that is used if an invalid class name is used*/
@SuppressWarnings("serial")
public class InvalidClassNameException extends Exception {

	
	public InvalidClassNameException(String message) {
		super(message);
	}
}
