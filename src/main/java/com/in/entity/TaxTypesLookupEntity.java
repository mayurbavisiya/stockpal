package com.in.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity(name = "tax_type_lookup")
public class TaxTypesLookupEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "ID")
	private Long tax_type_id;

	@Column(name = "type_desc")
	private String typeDesc;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "tax_type")
	private Set<TaxEntity> tax;
	
	public Long getId() {
		return tax_type_id;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setId(Long id) {
		this.tax_type_id = id;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

}
