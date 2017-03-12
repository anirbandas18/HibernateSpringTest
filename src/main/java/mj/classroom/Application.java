package mj.classroom;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import mj.classroom.exception.ServiceException;
import mj.classroom.service.SubjectService;
import mj.classroom.service.SubjectServiceImpl;

public class Application {

	public static void main(String[] args) throws ServiceException {
		// TODO Auto-generated method stub
		ApplicationContext appCtxt = new ClassPathXmlApplicationContext("application-context.xml");
		SubjectService subjectService = appCtxt.getBean("subjectService", SubjectServiceImpl.class);
		((ClassPathXmlApplicationContext) appCtxt).close();
	}

}
