package org.pingpong.restquarkusjpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pingpong.restquarkusjpa.domain.MagicalItem;
import org.pingpong.restquarkusjpa.domain.Order;
import org.pingpong.restquarkusjpa.domain.Wizard;
import org.pingpong.restquarkusjpa.repositorio.Repositorio;
import org.pingpong.restquarkusjpa.service.ServiceItem;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class RepoTest {

    @PersistenceContext
	EntityManager em;

	@Inject
	Repositorio repo;

	@Inject
    ServiceItem servicio;

    /**
	 * Tests sobre los mappings
	 * 
	 * Observa el esquema de la base de datos que espera 
	 * la aplicacion en el fichero:
	 * src/main/resources/schema.sql
	 */
	
	/**
     * Completa la definicion y el mapping
     * de la clase MagicalItem a la tabla t_items.
     * El id de esta clase ha de seguir una estrategia Identity
     */


	@Test
	public void test_mapping_MagicalItem() {
		MagicalItem elixir = em.find(MagicalItem.class, 2L);
		Assertions.assertThat(elixir).isNotNull();
		Assertions.assertThat(elixir.toString()).containsIgnoringCase("Elixir of the Mongoose");
		Assertions.assertThat(elixir.toString()).contains("7"); // item_quality
		Assertions.assertThat(elixir.toString()).contains("MagicalItem");
		Assertions.assertThat(elixir.getId()).isEqualTo(2L);
	}
    
	/** 
	 * Completa la definicion y el mapping
	 * de la clase Wizards a la tabla t_wizards
	 * 
	 * Los Wizards tiene una propiedad "person" de
	 * un tipo enumerado con los valores:
	 * MUGGLE, SQUIB, NOMAJ, MUDBLOOD
	 * 
	 * La anotacion javax.persistence para mapear 
	 * una propiedad Enum es
	 * 	@Enumerated(EnumType.STRING)
	 */
	@Test
	public void test_mapping_wizard() {
		Wizard squib = em.find(Wizard.class, "Marius Black");
		Assertions.assertThat(squib).isNotNull();
		Assertions.assertThat(squib.toString()).contains("Marius Black");
		Assertions.assertThat(squib.toString()).contains("15"); //wizard_dexterity
		Assertions.assertThat(squib.toString()).contains("SQUIB");  //tipo enumerado
	}

	/**
	 * Completa la definicion y el mapping
	 * de la clase Order a la tabla t_orders
	 * El id de esta clase ha de seguir una estrategia
	 * Identity
	 */
	@Test
	public void test_mapping_order() {
		Order pedido = em.find(Order.class, 1L);
		Assertions.assertThat(pedido).isNotNull();
		Assertions.assertThat(pedido.toString()).contains("Marius Black");
		Assertions.assertThat(pedido.toString()).containsIgnoringCase("Elixir of the Mongoose");
	}

	/**
	 * Crea una clase llamada Repositorio
	 * e inyectala en los casos test
	 * (ha de ser un bean) 
	 */
	@Test
	public void test_repositorio_existe() {
		Assertions.assertThat(repo).isNotNull();
	}

	/**
	 * Implementa el metodo loadWizard del repositorio
	 * que devuelve un Optional del mago/a con el nombre indicado
	 */
	@Test
	public void test_load_wizard() {
		Assertions.assertThat(repo).isNotNull();
		Wizard squib = repo.loadWizard("Hermione").get();
		Assertions.assertThat(squib).isNotNull();
		Assertions.assertThat(squib.toString()).contains("Hermione");
		Assertions.assertThat(squib.toString()).contains("100");
		Assertions.assertThat(squib.toString()).contains("MUDBLOOD");

		// no existe el mago
		Assertions.assertThat(repo.loadWizard("Severus Snape")).isEmpty();
	}

	/**
	 * Implementa el metodo loadItem() del repositorio
	 * que devuelve un Optional del Item con el nombre indicado
	 * 
	 * Ojo que el nombre del item no es la clave primaria.
	 * 
	 * El metodo devueve el primer item cuyo nombre
	 * coincida con el especificado.
	 */
	@Test
	public void test_load_item() {
		Assertions.assertThat(repo).isNotNull();
		MagicalItem item = repo.loadItem("Aged Brie").get();
		Assertions.assertThat(item).isNotNull();
		Assertions.assertThat(item.getName()).isEqualTo("Aged Brie");
		Assertions.assertThat(item.getQuality()).isEqualTo(10);

		// no existe el item
		Assertions.assertThat(repo.loadItem("Varita de Sauco")).isEmpty();
	}

	/**
	 * Implementa el metodo loadItems() del repositorio
	 * que devuelve una lista de Items 
	 * con el nombre indicado
	 * 
	 * Ojo que el nombre del item no es la clave primaria.
	 */
	@Test
	public void test_load_items() {
		Assertions.assertThat(repo).isNotNull();

		List<MagicalItem> items = repo.loadItems("Aged Brie");
		Assertions.assertThat(items).isNotEmpty().hasSize(2);
		Assertions.assertThat(items.get(0)).hasFieldOrPropertyWithValue("name", "Aged Brie");
		Assertions.assertThat(items.get(1)).hasFieldOrPropertyWithValue("quality", 0);

		// no existe el item
		Assertions.assertThat(repo.loadItems("Varita de Sauco")).isEmpty();
	}

	/**
	 * Implementa el metodo createItem() del repositorio
	 * que crea un item en la base de datos.
	 */
	@Test
	@Transactional
	public void test_create_item() {
		Assertions.assertThat(repo).isNotNull();

		repo.createItem("Guardapelo", 100, "MagicalItem");

		MagicalItem relic = repo.loadItem("Guardapelo").get();
		Assertions.assertThat(relic).isNotNull();
		Assertions.assertThat(relic.getName()).isEqualTo("Guardapelo");
		Assertions.assertThat(relic.getQuality()).isEqualTo(100);
		Assertions.assertThat(relic.getType()).isEqualTo("MagicalItem");

	}

	/**
	 * Implementa el metodo createItems() del repositorio
	 * que crea los items indicados en la base de datos.
	 * 
	 * Asegurate de que el metodo loadItem() anterior
	 * devueve el primer item cuyo nombre
	 * coincida con el especificado, sino, tu codigo
	 * devolvera uno de los pases a backstage que no
	 * es el que buscamos.
	 */

	@Test
	@Transactional
	public void test_create_items() {
		Assertions.assertThat(repo).isNotNull();

		List<MagicalItem> items = List.of(
			new MagicalItem("Sulfuras, Hand of Ragnaros", 0, "MagicalItem"),
			new MagicalItem("Sulfuras, Hand of Ragnaros", -1, "MagicalItem"),
			new MagicalItem("Backstage passes to a TAFKAL80ETC concert", 15, "MagicalItem"),
			new MagicalItem("Backstage passes to a TAFKAL80ETC concert", 10, "MagicalItem"),
			new MagicalItem("Backstage passes to a TAFKAL80ETC concert", 5, "MagicalItem"));
		
		repo.createItems(items);

		// los pases se han guardado
		MagicalItem backstage = repo.loadItem("Backstage passes to a TAFKAL80ETC concert").get();
		Assertions.assertThat(backstage).isNotNull();
		Assertions.assertThat(backstage.getName()).isEqualTo("Backstage passes to a TAFKAL80ETC concert");
		Assertions.assertThat(backstage.getQuality()).isEqualTo(15);
		Assertions.assertThat(backstage.getType()).isEqualTo("MagicalItem");

		// los sulfuras se han guardado
		List<MagicalItem> manos = repo.loadItems("Sulfuras, Hand of Ragnaros");
		Assertions.assertThat(manos).isNotNull().isNotEmpty().hasSize(2);
		Assertions.assertThat(items.get(0)).hasFieldOrPropertyWithValue("name", "Sulfuras, Hand of Ragnaros");
		Assertions.assertThat(items.get(1)).hasFieldOrPropertyWithValue("quality", -1);
	}

	/**
	 * Implementa un servicio, 
	 * indica que es un bean
	 * e inyectalo en los casos test
	 */
	@Test
	public void test_servicio() {
		Assertions.assertThat(servicio).isNotNull();
	}
}
