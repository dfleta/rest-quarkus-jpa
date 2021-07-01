package org.pingpong.restquarkusjpa;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pingpong.repositorio.Repositorio;
import org.pingpong.restquarkusjpa.domain.MagicalItem;
import org.pingpong.restquarkusjpa.domain.Order;
import org.pingpong.restquarkusjpa.domain.Wizard;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class RepoTest {

    @PersistenceContext
	EntityManager em;

	@Inject
	Repositorio repo;

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
	 * que devuelve el mago/a con el nombre indicado
	 */
	@Test
	public void test_load_wizard() {
		Assertions.assertThat(repo).isNotNull();
		Wizard squib = repo.loadWizard("Hermione");
		Assertions.assertThat(squib).isNotNull();
		Assertions.assertThat(squib.toString()).contains("Hermione");
		Assertions.assertThat(squib.toString()).contains("100");
		Assertions.assertThat(squib.toString()).contains("MUDBLOOD");
	}

}
