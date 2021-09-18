package com.labs.Lab6;

import javax.transaction.Transactional;

import com.labs.Lab6.providers.*;
import com.labs.Lab6.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;


@SpringBootApplication
public class Lab6Application {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Lab6Application.class);
	
	@Bean
	public PeopleProvider PeopleProvider() {
		return new PeopleService();
	}
	
	@Bean
	public ChairProvider ChairProvider() {
		return new ChairService();
	}

	@Bean
	public RoomProvider RoomProvider() {
		return new RoomService();
	}

	@Bean
	public TableProvider TableProvider() {
		return new TableService();
	}

	@Bean
	public ComputerProvider ComputerProvider() {
		return new ComputerService();
	}

	@Bean
	public ServerProvider ServerProvider() {
		return new ServerService();
	}


	public static void main(String[] args) {
		SpringApplication.run(Lab6Application.class, args);
	}
	
	
	@Bean
	@Transactional
	public CommandLineRunner run(final ChairProvider chairService,
								 final PeopleProvider peopleService,
								 final RoomProvider roomService,
								 final TableProvider tableService,
								 final ComputerProvider computerService,
								 final ServerProvider serverService) {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... arg0) throws Exception {
				LOGGER.info("*** started ***");
				Scanner menu = new Scanner(System.in);
				while (true) {
					System.out.println("1) Show...");
					System.out.println("2) Add...");
					System.out.println("3) Update...");
					System.out.println("4) Delete...");
					System.out.println("5) Find...");
					System.out.println("0) Exit");
					int chosen = menu.nextInt();
					if (chosen < 0 || chosen > 5) {
						System.out.println("Incorrect value");
						continue;
					}
					switch (chosen) {
						case 1:
							System.out.println("1) Show all managers");
							System.out.println("2) Show all rooms");
							System.out.println("3) Show all chairs");
							System.out.println("4) Show all tables");
							System.out.println("5) Show all computers");
							System.out.println("6) Show all servers");
							int subChoseShow = menu.nextInt();
							if (subChoseShow < 1 || subChoseShow > 6) {
								System.out.println("Incorrect value");
								continue;
							}
							switch (subChoseShow) {
								case 1:
									peopleService.getAll().forEach(System.out::println);
									break;
								case 2:
									roomService.getAll().forEach(System.out::println);
									break;
								case 3:
									chairService.getAll().forEach(System.out::println);
									break;
								case 4:
									tableService.getAll().forEach(System.out::println);
									break;
								case 5:
									computerService.getAll().forEach(System.out::println);
									break;
								case 6:
									serverService.getAll().forEach(System.out::println);
									break;
							}
							break;
						case 2:
							System.out.println("1) Add manager");
							System.out.println("2) Add room");
							System.out.println("3) Add chair");
							System.out.println("4) Add table");
							System.out.println("5) Add computer");
							System.out.println("6) Add server");
							int subChoseAdd = menu.nextInt();
							if (subChoseAdd < 1 || subChoseAdd > 6) {
								System.out.println("Incorrect value");
								continue;
							}
							switch (subChoseAdd) {
								case 1:
									peopleService.addOne();
									break;
								case 2:
									roomService.addOne();
									break;
								case 3:
									chairService.addOne();
									break;
								case 4:
									tableService.addOne();
									break;
								case 5:
									computerService.addOne();
									break;
								case 6:
									serverService.addOne();
									break;
							}
							break;
						case 3:
							System.out.println("1) Update manager");
							System.out.println("2) Update room");
							System.out.println("3) Update chair");
							System.out.println("4) Update table");
							System.out.println("5) Update computer");
							System.out.println("6) Update server");
							int subChoseUpdate = menu.nextInt();
							if (subChoseUpdate < 1 || subChoseUpdate > 6) {
								System.out.println("Incorrect value");
								continue;
							}
							switch (subChoseUpdate) {
								case 1:
									peopleService.updateOne();
									break;
								case 2:
									roomService.updateOne();
									break;
								case 3:
									chairService.updateOne();
									break;
								case 4:
									tableService.updateOne();
									break;
								case 5:
									computerService.updateOne();
									break;
								case 6:
									serverService.updateOne();
									break;
							}
							break;
						case 4:
							System.out.println("1) Delete manager");
							System.out.println("2) Delete room");
							System.out.println("3) Delete chair");
							System.out.println("4) Delete table");
							System.out.println("5) Delete computer");
							System.out.println("6) Delete server");
							int subChoseDelete = menu.nextInt();
							if (subChoseDelete < 1 || subChoseDelete > 6) {
								System.out.println("Incorrect value");
								continue;
							}
							switch (subChoseDelete) {
								case 1:
									peopleService.deleteOne();
									break;
								case 2:
									roomService.deleteOne();
									break;
								case 3:
									chairService.deleteOne();
									break;
								case 4:
									tableService.deleteOne();
									break;
								case 5:
									computerService.deleteOne();
									break;
								case 6:
									serverService.deleteOne();
									break;
							}
							break;
						case 5:
							System.out.println("1) Find staff by manager");
							System.out.println("2) Find staff by room");
							int subChoseFind = menu.nextInt();
							if (subChoseFind < 1 || subChoseFind > 2) {
								System.out.println("Incorrect value");
								continue;
							}
							switch (subChoseFind) {
								case 1:
									peopleService.search();
									menu.nextLine();
									break;
								case 2:
									roomService.search();
									menu.nextLine();
									break;
							}
							break;
						case 0:
							return;
					}
				}
			}
		};
	}
}
