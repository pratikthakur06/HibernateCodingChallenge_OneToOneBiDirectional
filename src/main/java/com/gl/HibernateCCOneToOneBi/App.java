package com.gl.HibernateCCOneToOneBi;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.gl.HibernateCCOneToOneBi.config.HibernateConfig;
import com.gl.HibernateCCOneToOneBi.entity.Address;
import com.gl.HibernateCCOneToOneBi.entity.Student;

/**
 * Hello world!
 *
 */
public class App {
	private static SessionFactory factory = HibernateConfig.getSessionFactory();

	public static void main(String[] args) {
		// Inserting student and address details
		Address address = new Address("Bangalore");
		Student student = new Student("Pratik", address);
		Session session;
		Transaction tx;
		try {
			session = factory.openSession();
			tx = session.beginTransaction();
			session.persist(student);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Fetching address details from student
		try {
			session = factory.openSession();
			student = session.get(Student.class, 1);
			session.close();
			System.out.println(student.getStudentId());
			System.out.println(student.getStudentName());
			System.out.println(student.getAddress().getCity());
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Fetching student details from address
		try {
			session = factory.openSession();
			address = session.get(Address.class, 1);
			session.close();
			System.out.println(address.getAddressId());
			System.out.println(address.getCity());
			System.out.println(address.getStudent().getStudentName());
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Deleting address and cascading student
		address = new Address();
		address.setAddressId(2);
		try {
			session = factory.openSession();
			address = session.get(Address.class, address.getAddressId());
			tx = session.beginTransaction();
			session.remove(address);
			tx.commit();
			session.close();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
