package com.artur.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/** Выполнение аннотации класса в качестве приложения Spring Boot */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		/** Запуск приложения Spring Boot */
		SpringApplication.run(DemoApplication.class, args);
	}
}

/** Обычный JPA-элемент для моделирования элемента cat */
@Entity
class Cat {
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	Cat() {

	}

	public Cat(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Cat{" + "id=" + id + ", name='" + name + '\'' + '}';
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}

/** Хранилище Spring Data JPA (в котором обрабатываются все общие операции
 * создания, чтения, обновления и удаления), эскпортированное в качестве REST API
 */
@RepositoryRestResource
interface CatRepository extends JpaRepository<Cat, Long> {

}
