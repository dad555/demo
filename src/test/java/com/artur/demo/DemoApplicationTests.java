package com.artur.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

//@formatter:off
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//@formatter.on

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

//@formatter:off
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//@formatter.on

/** Это блочный тест, использующий испольнитель тестов среды Spring. Кроме того,
 * мы настроили его на успешное взаимодействие с инструментарием тестирования
 * Spring Boot, справляющимся с имитационным веб-приложением */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class DemoApplicationTests {

	/** Вставка клиента MockMvc теста Spring MVC, из которого выполняются вызовы
	 * к конечным точкам REST */
	@Autowired
	private MockMvc mvc;

	/** Здесь можно сослаться на любые другие управляемые компоненты в контексте нашего
	 * Spring-приложения, включая CatRepository */
	@Autowired
	private CatRepository catRepository;

	/** Установка в базу данных информационного образца */
	@Before
	public void before() throws Exception {
		Stream.of("Felix", "Garfield", "Whiskers").forEach(n -> catRepository.save(new Cat(n)));
	}

	/** Вызов конечной точки HTTP GET для ресурса /cats */
	@Test
	public void catsReflectedInRead() throws Exception {
		MediaType halJson = MediaType.parseMediaType("application/hal+json;charset=UTF-8");
		this.mvc
				.perform(get("/cats"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(halJson))
				.andExpect(
						mvcResult -> {
							String contentAsString = mvcResult.getResponse().getContentAsString();
							assertTrue(contentAsString.split("totalElements")[1].split(":")[1].trim()
							.split(",")[0].equals("3"));
						});
	}
}
