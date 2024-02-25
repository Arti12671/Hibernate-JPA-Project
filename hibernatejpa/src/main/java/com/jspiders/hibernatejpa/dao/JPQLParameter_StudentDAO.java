package com.jspiders.hibernatejpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jspiders.hibernatejpa.dto.StudentDTO;

public class JPQLParameter_StudentDAO {
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static EntityTransaction entityTransaction;
	
	public static void main(String[] args) {
		openConnection();
		entityTransaction.begin();
		
		List<StudentDTO> student = findAll();
		for (StudentDTO studentDTO : student) {
			System.out.println(studentDTO);
		}
		
		entityTransaction.commit();
		closeConnection();
	}
	
	@SuppressWarnings("unchecked")
	private static List<StudentDTO> findAll() {
		Query query = entityManager.createQuery("SELECT student FROM StudentDTO student ");
		List student = query.getResultList();
		return student;
	}
	
	private static void openConnection() {
		entityManagerFactory = Persistence.createEntityManagerFactory("student");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}
	
	public static void closeConnection() {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
		if (entityManager != null) {
			entityManager.close();
		}
		if (entityTransaction != null) {
			if (entityTransaction.isActive()) {
				entityTransaction.rollback();
			}
		}
	}
}
