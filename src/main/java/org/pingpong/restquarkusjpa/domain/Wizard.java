package org.pingpong.restquarkusjpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="t_wizards")
@NoArgsConstructor @EqualsAndHashCode @ToString
public class Wizard {
    
    @Id
	@Column(name="wizard_name")
	private @Getter String name;
	
	@Column(name="wizard_dexterity")
	private @Getter @Setter int dexterity;

	@Column(name = "wizard_person")
	@Enumerated(EnumType.STRING)
	private @Getter @Setter Person person;

}
