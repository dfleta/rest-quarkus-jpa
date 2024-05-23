package org.pingpong.restquarkusjpa.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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
