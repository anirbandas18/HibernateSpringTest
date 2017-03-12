package mj.classroom.exception;

public class DAOException extends Exception{

	public DAOException(String message) {
		super("DAOException : " + message);
	}

	public DAOException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DAOException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
