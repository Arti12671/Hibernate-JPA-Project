package com.jspiders.cardekhoJPA.dao;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.jspiders.cardekhoJPA.dto.CarDTO;

public class CarDAO1 {

	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	private static EntityTransaction entityTransaction;

	public void viewAll() {
		List<CarDTO> carList = findAll();
		if (carList.isEmpty()) {
			System.out.println("No Car is Available!");
		} else {
			for (CarDTO car : carList) {
				System.out.println(car);
			}
		}
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

		CarDTO carFound = entityManager.find(CarDTO.class, id);
		if (carFound != null) {
			entityManager.remove(carFound);
		} else {
			System.out.println("Car with id " + id + " is not found!");
		}
	}

	public void search(Scanner scanner) {
		boolean flag = true;
		while (flag) {
			System.out.println("=====Search Car Menu====\n" + "1. Search Car By Id  \n" + "2. Search Car By Name  \n"
					+ "3. Search Car By Brand \n" + "4. Search Car By Fuel Type \n" + "5. Search Car By Price \n"
					+ "6. Back To Main Menu \n");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				System.out.println("Enter Car Id : ");
				int id = scanner.nextInt();

				CarDTO carFound = entityManager.find(CarDTO.class, id);
				if (carFound != null) {
					System.out.println(carFound);
				} else {
					System.out.println("Car with id " + id + " is not found!");
				}

				break;
			case 2:

				List<CarDTO> carByName = findByName(scanner);
				for (CarDTO car : carByName) {
					System.out.println(car);
				}

				break;
			case 3:

				List<CarDTO> carByBrand = findByBrand(scanner);
				for (CarDTO car : carByBrand) {
					System.out.println(car);
				}

				break;
			case 4:

				List<CarDTO> carByFuelType = findByFuelType(scanner);
				for (CarDTO car : carByFuelType) {
					System.out.println(car);
				}

				break;
			case 5:

				List<CarDTO> carByPrice = findByPrice(scanner);
				for (CarDTO car : carByPrice) {
					System.out.println(car);
				}

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
		if (carList.isEmpty()) {
			System.out.println("Car with name " + name + " is not available");
		}
		return carList;
	}

	@SuppressWarnings("unchecked")
	private static List<CarDTO> findByBrand(Scanner scanner) {
		System.out.println("Enter Car Brand : ");
		String brand = scanner.next();

		Query query = entityManager.createQuery("SELECT car FROM CarDTO car WHERE brand = ?1");
		query.setParameter(1, brand);
		List carList = query.getResultList();
		if (carList.isEmpty()) {
			System.out.println("Car with brand " + brand + " is not available");
		}

		return carList;
	}

	@SuppressWarnings("unchecked")
	private static List<CarDTO> findByFuelType(Scanner scanner) {
		System.out.println("Enter Car Fuel Type: ");
		String fuelType = scanner.next();

		Query query = entityManager.createQuery("SELECT car FROM CarDTO car WHERE fuelType = ?1");
		query.setParameter(1, fuelType);
		List carList = query.getResultList();
		if (carList.isEmpty()) {
			System.out.println("Car with fuelType " + fuelType + " is not available");
		}
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
		if (carList.isEmpty()) {
			System.out.println("Car with price between " + minPrice + " and " + maxPrice + " is not available");
		}
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
			case 1: {
				System.out.println("Enter New Name :");
				String name = scanner.next();

				CarDTO car = entityManager.find(CarDTO.class, id);

				car.setName(name);
				entityManager.persist(car);

				break;
			}

			case 2: {
				System.out.println("Enter New Brand :");
				String brand = scanner.next();

				CarDTO car = entityManager.find(CarDTO.class, id);

				car.setBrand(brand);
				entityManager.persist(car);

				break;
			}
			case 3: {
				System.out.println("Enter New Model :");
				String model = scanner.next();

				CarDTO car = entityManager.find(CarDTO.class, id);

				car.setModel(model);
				entityManager.persist(car);

				break;
			}
			case 4: {
				System.out.println("Enter New Fuel Type :");
				String fuelType = scanner.next();

				CarDTO car = entityManager.find(CarDTO.class, id);

				car.setFuelType(fuelType);
				entityManager.persist(car);

				break;
			}

			case 5: {
				System.out.println("Enter New Price :");
				Double price = scanner.nextDouble();

				CarDTO car = entityManager.find(CarDTO.class, id);

				car.setPrice(price);
				entityManager.persist(car);

				break;
			}

			case 6: {
				System.out.println("Enter New Color :");
				String color = scanner.next();

				CarDTO car = entityManager.find(CarDTO.class, id);

				car.setName(color);
				entityManager.persist(car);
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
//-67
