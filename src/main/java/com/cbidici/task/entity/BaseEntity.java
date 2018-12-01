package com.cbidici.task.entity;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.hibernate.annotations.GenericGenerator;

/**
 * Base class to be extended by all entity classes
 * 2014-11-29
 * @author cbidici
 * @since 0.0.1
 */
@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "uuid2")
	@Column(updatable = false, nullable = false)
	private String id;
	
	@Column(name="create_date", updatable=false)
	private Timestamp createDate;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	@PrePersist
	public void onCreate(){
		this.setCreateDate(new Timestamp(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis()));
	}
	
	
}
