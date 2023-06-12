package org.pingpong.restquarkusjpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="t_orders")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Order {
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ord_id")
    private @Getter Long id; 
    
    @ManyToOne
	@JoinColumn(name="ord_wizard")
	private @Getter @Setter Wizard wizard;
	
	// JoinColumn indica que Order es la propietaria de la relacion
	// Al tener una columna "item_id" en la tabla "orders", se indica que Order es la entidad 
	// propietaria de la relación, ya que contiene la clave foránea que establece la relación con 
	// la tabla "items".
	// Con el esquela de la bbdd proporcionado (ver schema.sql)
	// Se establece una relación de uno a uno entre Order e Item, donde cada registro en la tabla
	// "orders" está vinculado a un único registro en la tabla "items". Order es la entidad 
	// propietaria de la relación al tener la clave foránea "item_id".
	@OneToOne
	@JoinColumn(name = "ord_item")
	private @Getter @Setter MagicalItem item;	
    
}
