package com.tugasakhir;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TugasAkhirApplication {

	private static volatile ConfigurableApplicationContext context;
	private static ClassLoader mainThreadClassLoader;

	public static void main(String[] args) {
		mainThreadClassLoader = Thread.currentThread().getContextClassLoader();
		context = SpringApplication.run(TugasAkhirApplication.class, args);
	}

	public static void restart() {
		ApplicationArguments args = context.getBean(ApplicationArguments.class);

		Thread thread = new Thread(() -> {
			context.close();
			context = SpringApplication.run(TugasAkhirApplication.class, args.getSourceArgs());
		});

		thread.setContextClassLoader(mainThreadClassLoader);
		thread.setDaemon(false);
		thread.start();
	}

}
