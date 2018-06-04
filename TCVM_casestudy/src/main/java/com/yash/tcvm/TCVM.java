package com.yash.tcvm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import com.yash.tcvm.builder.BlackCoffeeBuilder;
import com.yash.tcvm.builder.BlackTeaBuilder;
import com.yash.tcvm.builder.CoffeeBuilder;
import com.yash.tcvm.builder.TeaBuilder;
import com.yash.tcvm.builder.interfaces.IDrinkBuilder;
import com.yash.tcvm.dao.ContainerDao;
import com.yash.tcvm.dao.OrderDao;
import com.yash.tcvm.daoimpl.ContainerDaoImpl;
import com.yash.tcvm.daoimpl.OrderDaoImpl;
import com.yash.tcvm.enumeration.Drink;
import com.yash.tcvm.exception.ContainerUnderflowException;
import com.yash.tcvm.exception.EmptyException;
import com.yash.tcvm.literal.TCVMMenuConstants;
import com.yash.tcvm.model.Container;
import com.yash.tcvm.model.Order;
import com.yash.tcvm.service.ContainerService;
import com.yash.tcvm.service.OrderService;
import com.yash.tcvm.serviceimpl.ContainerServiceImpl;
import com.yash.tcvm.serviceimpl.OrderServiceImpl;
import com.yash.tcvm.util.FileUtil;

/**
 * This class performs the basic console functionality which have user
 * interaction .
 * 
 * @author soumya.gupta
 *
 */
public class TCVM {

	private Scanner scanner;

	private static TCVM tcvm;

	private ContainerDao containerDao;
	private ContainerService containerService;

	private OrderDao orderDao;
	private OrderService orderService;

	private TCVM() {
		scanner = new Scanner(System.in);

		containerDao = new ContainerDaoImpl();
		containerService = new ContainerServiceImpl(containerDao);

		orderDao = new OrderDaoImpl();
		orderService = new OrderServiceImpl(orderDao);
	}

	public static TCVM getTCVM() {
		tcvm = new TCVM();
		return tcvm;
	}

	public void start() throws EmptyException {
		List<String> menuOptions = null;
		String choice = null;

		do {

			try {
				menuOptions = getMenu(TCVMMenuConstants.MENU_FILE_PATH);
			} catch (FileNotFoundException fileNotFoundException) {
				fileNotFoundException.printStackTrace();
			}

			for (String option : menuOptions) {
				System.out.println(option);
			}

			int selectedMenuOption = getUserSelectedMenuOption();

			getOperationToBePerformedBasedOnMenu(selectedMenuOption);

			System.out.println("Do you want to continue: y/n");
			scanner.nextLine();
			choice = scanner.nextLine();

			if (!choice.equalsIgnoreCase("y")) {
				System.out.println("Thank you for using TCVM");
			}

		} while (choice.equalsIgnoreCase("y"));

	}

	public List<String> getMenu(String filePath) throws FileNotFoundException, EmptyException {

		checkIfFilePathIsNull(filePath);

		checkIfFilePathIsEmpty(filePath);

		File menuFile = new File(filePath);

		checkIfFileExists(menuFile);

		checkIfFileIsEmpty(menuFile);

		List<String> menuOptions = FileUtil.readFile(filePath);

		return menuOptions;
	}

	private void checkIfFileIsEmpty(File menuFile) throws EmptyException {
		if (menuFile.length() == 0) {
			throw new EmptyException("File cannot be empty");
		}
	}

	private void checkIfFileExists(File menuFile) throws FileNotFoundException {
		if (!menuFile.exists()) {
			throw new FileNotFoundException("File not found in given location");
		}
	}

	private void checkIfFilePathIsEmpty(String filePath) throws EmptyException {
		if (filePath.isEmpty()) {
			throw new EmptyException("File location cannot be empty");
		}
	}

	private void checkIfFilePathIsNull(String filePath) {
		if (filePath == null) {
			throw new NullPointerException("File location cannot be null");
		}
	}

	private int getUserSelectedMenuOption() {
		int selectedMenuOption = 0;
		System.out.println("Enter your choice: ");
		selectedMenuOption = scanner.nextInt();
		if (selectedMenuOption < 0 || selectedMenuOption > TCVMMenuConstants.MAX_NO_OF_MENU_OPTIONS) {
			System.out.println("Invalid Input!");
		}
		return selectedMenuOption;
	}

	private void getOperationToBePerformedBasedOnMenu(int selectedMenuOption) {
		switch (selectedMenuOption) {
		case TCVMMenuConstants.MAKE_COFFEE:
			makeCoffee();
			break;

		case TCVMMenuConstants.MAKE_TEA:
			makeTea();
			break;

		case TCVMMenuConstants.MAKE_BLACK_COFFEE:
			makeBlackCoffee();
			break;

		case TCVMMenuConstants.MAKE_BLACK_TEA:
			makeBlackTea();
			break;

		case TCVMMenuConstants.REFILL_CONTAINER:
			refillcontainer();
			break;

		case TCVMMenuConstants.CHECK_TOTAL_SALE:
			checkTotalSale();
			break;

		case TCVMMenuConstants.CONTAINER_STATUS:
			containerStatus();
			break;

		case TCVMMenuConstants.SHOW_REPORTS:
			showReports();
			break;

		case TCVMMenuConstants.EXIT_TCVM:
			System.out.println("Thank you for using TCVM");
			System.exit(0);
			break;

		default:
			break;
		}
	}

	private void makeCoffee() {
		System.out.println(" PREPARING COFFEE ");
		IDrinkBuilder drinkBuilder = CoffeeBuilder.getDrinkBuilder();
		makeDrink(drinkBuilder, Drink.COFFEE);
		System.out.println(" Your Coffee is ready! ");
	}

	private void makeTea() {
		System.out.println(" PREPARING TEA ");
		IDrinkBuilder drinkBuilder = TeaBuilder.getDrinkBuilder();
		makeDrink(drinkBuilder, Drink.TEA);
		System.out.println("Your Tea is ready! ");
	}

	private void makeBlackCoffee() {
		System.out.println(" PREPARING BLACK COFFEE ");
		IDrinkBuilder drinkBuilder = BlackCoffeeBuilder.getDrinkBuilder();
		makeDrink(drinkBuilder, Drink.BLACK_COFFEE);
		System.out.println("Your Black coffee is ready! ");
	}

	private void makeBlackTea() {
		System.out.println(" PREPARING Black TEA ");
		IDrinkBuilder drinkBuilder = BlackTeaBuilder.getDrinkBuilder();
		makeDrink(drinkBuilder, Drink.BLACK_TEA);
		System.out.println(" Your Black Tea is ready! ");
	}

	private void refillcontainer() {
		System.out.println(" REFILLING CONTAINER ");
		List<Container> containers = null;
		try {
			containers = containerService.getContainers();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EmptyException e) {
			e.printStackTrace();
		}
		for (Container container : containers) {
			container.setCurrentAvailability(container.getMaxCapacity());
			try {
				containerService.updateContainer(container);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (EmptyException e) {
				e.printStackTrace();
			}
		}
		containerStatus();
	}

	private void checkTotalSale() {
		System.out.println("--- TOTAL SALE ---");
		calculateSaleOfDrink(TeaBuilder.getDrinkBuilder(), Drink.TEA);
		calculateSaleOfDrink(CoffeeBuilder.getDrinkBuilder(), Drink.COFFEE);
		calculateSaleOfDrink(BlackTeaBuilder.getDrinkBuilder(), Drink.BLACK_TEA);
		calculateSaleOfDrink(BlackCoffeeBuilder.getDrinkBuilder(), Drink.BLACK_COFFEE);
	}

	private void containerStatus() {
		System.out.println(" CONTAINERS STATUS ");
		List<Container> containers = null;
		try {
			containers = containerService.getContainers();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EmptyException e) {
			e.printStackTrace();
		}
		for (Container container : containers) {
			System.out.println(container.getIngredient() + " CONTAINER");
			System.out.println("Max capacity: " + container.getMaxCapacity());
			System.out.println("Current availability: " + container.getCurrentAvailability() + "\n");
		}
	}

	private void showReports() {
		System.out.println("REPORTS ");
	}

	private void makeDrink(IDrinkBuilder drinkBuilder, Drink drink) {
		System.out.println("Enter no of cups: ");
		int qtyOrdered = scanner.nextInt();

		Order order = new Order();
		order.setDrink(drink);
		order.setQuantity(qtyOrdered);

		try {
			drinkBuilder.prepareDrink(order);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ContainerUnderflowException e) {
			System.out.println("Not enough ingredients");
			e.printStackTrace();
		} catch (EmptyException e) {
			e.printStackTrace();
		}
	}

	private void calculateSaleOfDrink(IDrinkBuilder drinkBuilder, Drink drink) {
		double price = drinkBuilder.getDrinkRate();
		double totalSale = 0;
		List<Order> orders = null;

		try {
			orders = orderService.getOrdersByDrink(drink);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (EmptyException e) {
			e.printStackTrace();
		}
		for (Order order : orders) {
			totalSale = totalSale + (order.getQuantity() * price);
		}
		System.out.println("Drink: " + drink + " -- Total sale: Rs." + totalSale);
	}

}
