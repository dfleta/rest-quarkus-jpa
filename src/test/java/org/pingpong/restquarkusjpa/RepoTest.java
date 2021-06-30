package org.pingpong.restquarkusjpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pingpong.restquarkusjpa.domain.MagicalItem;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class RepoTest {

    @PersistenceContext
	EntityManager em;

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
	public void test_mapping_normalItem() {
		MagicalItem elixir = em.find(MagicalItem.class, 2L);
		Assertions.assertThat(elixir).isNotNull();
		Assertions.assertThat(elixir.toString()).containsIgnoringCase(("Elixir of the Mongoose"));
		Assertions.assertThat(elixir.toString()).contains("7");
		Assertions.assertThat(elixir.toString()).contains("MagicalItem");
		Assertions.assertThat(elixir.getId()).isEqualTo(2L);
	}
    
}
