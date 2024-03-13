package com.jspiders.cardekhoJPA.dao;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class CarMain1 {
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static EntityTransaction entityTransaction;
	
	private static CarDAO car = new CarDAO(); 
	static boolean loop = true;

	public static void main(String[] args) {
		while (loop) {
			menu();
		}
	}

	private static void menu() {
		System.out.println("Welcome to car dekho app");
		System.out.println("=====Main Menu====\n" + "1. View All Cars \n" + "2. Search Car \n" + "3. Add Car \n"
				+ "4. Remove Car \n" + "5. Edit Car \n" + "6. Exit \n");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		
		openConnection();
		entityTransaction.begin();
		
		switch (choice) {
		case 1: car.viewAll();
			break;
		case 2:	car.search(scanner);
			break;
		case 3: car.addCar(scanner);
			break;
		case 4: car.remove(scanner);
			break;
		case 5: car.editCar(scanner);
			break;
			
		case 6:
			entityTransaction.commit();
			closeConnection();
			
			System.out.println("Thank You For Visiting");
			loop = false;
			break;

		default:
			System.out.println("Invalid Choice");
			break;
		}
	}
	
	private static void openConnection() {
		entityManagerFactory = Persistence.createEntityManagerFactory("car");
		entityManager = entityManagerFactory.createEntityManager();
		entityTransaction = entityManager.getTransaction();
	}

	private static void closeConnection() {
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
