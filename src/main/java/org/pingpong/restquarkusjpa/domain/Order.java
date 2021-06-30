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
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="t_orders")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Order {
    
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ord_id")
    private Long id; 
    
    @ManyToOne
	@JoinColumn(name="ord_wizard")
	private Wizard wizard;
	
	@OneToOne
	@JoinColumn(name="ord_item")
	private MagicalItem item;	
    
}
