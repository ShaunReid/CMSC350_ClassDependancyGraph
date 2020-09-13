/*CMSC350 
 8 Mar 2020
 Shaun Reid
 
 Custom exception that is used if a cycle is found*/
@SuppressWarnings("serial")
public class CycleFoundException extends Exception {

	public CycleFoundException(String message) {
		super(message);
	}
	
}
