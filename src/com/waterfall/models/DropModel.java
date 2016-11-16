package com.waterfall.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="dropmodel")
@NamedQueries({ @NamedQuery(name = "DropModel.findAll", query = "SELECT d FROM DropModel d"),
		@NamedQuery(name = "DropModel.findDropContentFromSearch", query = "SELECT d FROM DropModel d WHERE d.content LIKE :content"),
		})
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DropModel implements Serializable {

	private static final long serialVersionUID = -2443095827173416242L;

	@Id
	private Long dropid;

	private String content;

	private LocalDateTime creationDate;
	
	@Transient
	private List<LinkModel> links = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "dropownerid")
	@XmlTransient
	private UserModel dropowner;
	
	@Transient
	private Long dropownerid;

	@OneToMany(mappedBy = "dropHost",fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@XmlTransient
	private List<CommentModel> comments;

	public DropModel() {
	}
	
	public UserModel getOwner() {
		return dropowner;
	}

	public void setOwner(UserModel owner) {
		this.dropowner = owner;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public List<CommentModel> getComments() {
		return comments;
	}

	public void setComments(List<CommentModel> comments) {
		this.comments = comments;
	}

	public Long getDropId() {
		return this.dropid;
	}

	public void setDropId(Long dropid) {
		this.dropid = dropid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreationDateFormatted() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return formatter.format(creationDate.toLocalDate());
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public Long getDropownerid() {
		dropownerid = dropowner.getUserid();
		return dropownerid;
	}

	public void setDropownerid(Long dropownerid) {
		this.dropownerid = dropownerid;
	}

	public List<LinkModel> getLinks() {
		return links;
	}

	public void setLinks(List<LinkModel> links) {
		this.links = links;
	}

	public void addLink(LinkModel link) {
		this.links.add(link);
	}
	
	

}