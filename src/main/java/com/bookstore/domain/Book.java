package com.bookstore.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@JsonIgnoreProperties({"author", "genre","createdTime","updatedTime","pagesCount","id"})
@Slf4j
@Table(name = "book")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Book {

	@Id
	@ApiModelProperty(value = "id", hidden = true)
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "is required")
	@Size(min=2, max=50)
	@Column(name = "title", unique = true)
	private String title;

	@ApiModelProperty(value = "pagesCount", hidden = true)
	@NotEmpty(message = "is required")
	@Size(min=2, max=30)
	@Column(name = "author")
	private String author;

	@ApiModelProperty(value = "createdTime", hidden = true)
	@NotEmpty(message = "is required")
	@Column(name = "genre")
	private String genre;

	@ApiModelProperty(value = "pagesCount", hidden = true)
	@NotNull(message = "is required")
	@Min(value=2, message = "must be greater than or equal to 2")
	@Column(name = "pages_count")
	private Integer pagesCount;

	@JsonFormat(shape=JsonFormat.Shape.STRING)
	@NotNull(message = "is required")
	@DecimalMin(value = "0.00", message = "Price format is 0.00")
	@Digits(integer=6, fraction=2)
	private BigDecimal price;

	@ApiModelProperty(value = "createdTime", hidden = true)
	@CreationTimestamp
	@Column(name = "created_time", insertable=false, updatable=false)
	private ZonedDateTime createdTime;

	@ApiModelProperty(value = "updatedTime", hidden = true)
	@UpdateTimestamp
	@Column(name = "updated_time")
	private ZonedDateTime updatedTime;
}
