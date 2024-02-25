package com.jspiders.cardekhoJPA.dao;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jspiders.cardekhoJPA.ato.CarDTO;

public class CarDAO {
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static EntityTransaction entityTransaction;

	public void viewAll() {
		openConnection();
		entityTransaction.begin();

		List<CarDTO> carList = findAll();
		for (CarDTO car : carList) {
			System.out.println(car);
		}

		entityTransaction.commit();
		closeConnection();
	}

	@SuppressWarnings("unchecked")
	private static List<CarDTO> findAll() {
		Query query = entityManager.createQuery("SELECT car FROM CarDTO car");
		List carList = query.getResultList();
		return carList;
	}

	public void addCar(Scanner scanner) {
		CarDTO car = new CarDTO();

		System.out.println("Enter Car Name : ");
		String name = scanner.next();
		car.setName(name);

		System.out.println("Enter Car Brand : ");
		String brand = scanner.next();
		car.setBrand(brand);

		System.out.println("Enter Car Model : ");
		String model = scanner.next();
		car.setModel(model);

		System.out.println("Enter Car Fuel Type : ");
		String fuelType = scanner.next();
		car.setFuelType(fuelType);

		System.out.println("Enter Car Price : ");
		Double price = scanner.nextDouble();
		car.setPrice(price);

		System.out.println("Enter Car Color : ");
		String color = scanner.next();
		car.setColor(color);

		openConnection();
		entityTransaction.begin();

		entityManager.persist(car);

		entityTransaction.commit();
		closeConnection();

	}

	public void remove(Scanner scanner) {

		System.out.println("Enter Car Id : ");
		int id = scanner.nextInt();

		openConnection();
		entityTransaction.begin();

		CarDTO carFound = entityManager.find(CarDTO.class, id);
		entityManager.remove(carFound);

		entityTransaction.commit();
		closeConnection();
	}

	public void search(Scanner scanner) {

		boolean flag = true;
		while (flag) {
			System.out.println("=====Search Car Menu====\n" + "1. Search Car By Id  \n" + "2. Search Car By Name  \n"
					+ "3. Search Car By Brand \n" + "4. Search Car By Fuel Type \n" + "5. Search Car By Price \n"
					+ "6. Bake To Main Menu \n");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter Car Id : ");
				int id = scanner.nextInt();

				openConnection();
				entityTransaction.begin();

				CarDTO carFound = entityManager.find(CarDTO.class, id);
				System.out.println(carFound);

				entityTransaction.commit();
				closeConnection();
				break;
			case 2:
				openConnection();
				entityTransaction.begin();

				List<CarDTO> carByName = findByName(scanner);
				for (CarDTO car : carByName) {
					System.out.println(car);
				}

				entityTransaction.commit();
				closeConnection();
				break;
			case 3:
				openConnection();
				entityTransaction.begin();

				List<CarDTO> carByBrand = findByBrand(scanner);
				for (CarDTO car : carByBrand) {
					System.out.println(car);
				}

				entityTransaction.commit();
				closeConnection();
				break;
			case 4:
				openConnection();
				entityTransaction.begin();

				List<CarDTO> carByFuelType = findByFuelType(scanner);
				for (CarDTO car : carByFuelType) {
					System.out.println(car);
				}

				entityTransaction.commit();
				closeConnection();
				break;
			case 5:
				openConnection();
				entityTransaction.begin();

				List<CarDTO> carByPrice = findByPrice(scanner);
				for (CarDTO car : carByPrice) {
					System.out.println(car);
				}

				entityTransaction.commit();
				closeConnection();
				break;

			case 6:
				System.out.println("Search Menu Closed!!!");
				flag = false;
				break;

			default:
				System.out.println("Invalid Choice");
				break;

			}
		}
	}

	@SuppressWarnings("unchecked")
	private static List<CarDTO> findByName(Scanner scanner) {
		System.out.println("Enter Car Name : ");
		String name = scanner.next();

		Query query = entityManager.createQuery("SELECT car FROM CarDTO car WHERE name = ?1");
		query.setParameter(1, name);
		List carList = query.getResultList();
		return carList;
	}

	@SuppressWarnings("unchecked")
	private static List<CarDTO> findByBrand(Scanner scanner) {
		System.out.println("Enter Car Brand : ");
		String brand = scanner.next();

		Query query = entityManager.createQuery("SELECT car FROM CarDTO car WHERE brand = ?1");
		query.setParameter(1, brand);
		List carList = query.getResultList();
		return carList;
	}

	@SuppressWarnings("unchecked")
	private static List<CarDTO> findByFuelType(Scanner scanner) {
		System.out.println("Enter Car Brand Fuel Type: ");
		String fuelType = scanner.next();

		Query query = entityManager.createQuery("SELECT car FROM CarDTO car WHERE fuelType = ?1");
		query.setParameter(1, fuelType);
		List carList = query.getResultList();
		return carList;
	}

	@SuppressWarnings("unchecked")
	private static List<CarDTO> findByPrice(Scanner scanner) {
		System.out.println("Enter Min Price: ");
		Double minPrice = scanner.nextDouble();

		System.out.println("Enter Max Price: ");
		Double maxPrice = scanner.nextDouble();

		Query query = entityManager.createQuery("SELECT car FROM CarDTO car WHERE price BETWEEN ?1 AND ?2");
		query.setParameter(1, minPrice);
		query.setParameter(2, maxPrice);
		List carList = query.getResultList();
		return carList;
	}

	public void editCar(Scanner scanner) {
		System.out.println("Enter The Id To Edit The Car :");
		int id = scanner.nextInt();
			
		boolean flag = true;
		while (flag) {
			System.out.println("=====Edit Menu====\n" + "1. Edit Name  \n" + "2. Edit Brand \n" + "3. Edit Model \n"
					+ "4. Edit Fuel Type \n" + "5. Edit Price \n" + "6. Edit Color \n" + "7. Back To Main Menu \n");
			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
			{
				System.out.println("Enter New Name :");
				String name = scanner.next();
				
				openConnection();
				entityTransaction.begin();
				CarDTO car = entityManager.find(CarDTO.class, id);
				
				car.setName(name);
				entityManager.persist(car);
				
				entityTransaction.commit();
				closeConnection();
				break;
			}
		
			case 2:
			{
				System.out.println("Enter New Brand :");
				String brand = scanner.next();
				
				openConnection();
				entityTransaction.begin();
				CarDTO car = entityManager.find(CarDTO.class, id);
				
				car.setBrand(brand);
				entityManager.persist(car);
		
				entityTransaction.commit();	
				closeConnection();
				break;
			}
			case 3:
			{
				System.out.println("Enter New Model :");
				String model = scanner.next();
				
				openConnection();
				entityTransaction.begin();
				CarDTO car = entityManager.find(CarDTO.class, id);
				
				car.setModel(model);
				entityManager.persist(car);
				
				entityTransaction.commit();	
				closeConnection();
				break;
			}
			case 4:
			{
				System.out.println("Enter New Fuel Type :");
				String fuelType = scanner.next();
				
				openConnection();
				entityTransaction.begin();
				CarDTO car = entityManager.find(CarDTO.class, id);
				
				car.setFuelType(fuelType);
				entityManager.persist(car);
				
				entityTransaction.commit();	
				closeConnection();
				break;
			}
				
			case 5:
			{
				System.out.println("Enter New Price :");
				Double price = scanner.nextDouble();
				
				openConnection();
				entityTransaction.begin();
				CarDTO car = entityManager.find(CarDTO.class, id);
				
				car.setPrice(price);
				entityManager.persist(car);
				
				entityTransaction.commit();	
				closeConnection();
				break;
			}
				
			case 6:
			{
				System.out.println("Enter New Color :");
				String color = scanner.next();
				
				openConnection();
				entityTransaction.begin();
				CarDTO car = entityManager.find(CarDTO.class, id);
				
				car.setName(color);
				entityManager.persist(car);
				
				entityTransaction.commit();	
				closeConnection();
				break;
			}
				
			case 7:
				System.out.println("Edit Menu Closed!!!");
				flag = false;
				break;
				
			default:
				System.out.println("Invalid Choice");
				break;
			}
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
