package org.pingpong.restquarkusjpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="t_items")
@NoArgsConstructor @EqualsAndHashCode @ToString @RequiredArgsConstructor
public class MagicalItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private @Getter long id;

	@Column(name="item_name")
	@NotEmpty
	private @NonNull @Getter @Setter String name;
	
	@Column(name="item_quality")
	@NotNull
	private @NonNull @Getter @Setter int quality;

	@Column(name = "item_type", insertable = false, updatable = false)
	@NotEmpty
	private @NonNull @Getter @Setter String type;

}
