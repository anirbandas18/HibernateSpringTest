package mj.classroom.exception;

public class ServiceException extends Exception {

	public ServiceException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ServiceException(String message) {
		super("ServiceException : " + message);
		// TODO Auto-generated constructor stub
	}

	public ServiceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	
}
