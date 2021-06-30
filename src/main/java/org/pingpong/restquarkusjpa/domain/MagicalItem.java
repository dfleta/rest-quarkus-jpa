package org.pingpong.restquarkusjpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="t_items")
@NoArgsConstructor @EqualsAndHashCode @ToString
public class MagicalItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private @Getter long id;

	@Column(name="item_name")
	private @Getter @Setter String name;
	
	@Column(name="item_quality")
	private @Getter @Setter int quality;

	@Column(name = "item_type", insertable = false, updatable = false)
	private @Getter @Setter String type;
    
}
