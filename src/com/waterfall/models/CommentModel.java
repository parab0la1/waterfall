package com.waterfall.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name="commentmodel")
@NamedQuery(name="CommentModel.findAll", query="SELECT c FROM CommentModel c")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CommentModel implements Serializable {

	private static final long serialVersionUID = -2986930687226854493L;

	@Id
	private Long commentid;

	private String content;

	private LocalDateTime creationdate;
	
	@ManyToOne
	@JoinColumn(name = "drophostid")
	@XmlTransient
	private DropModel dropHost;
	
	@Transient
	private Long drophostid;
	
	@OneToOne
	@JoinColumn(name = "commentownerid")
	@XmlTransient
	private UserModel owner;
	
	@Transient
	private Long commentownerid;
	
	@Transient
	private List<LinkModel> links = new ArrayList<>();

	public CommentModel() {
	}

	public UserModel getOwner() {
		return owner;
	}

	public void setOwner(UserModel owner) {
		this.owner = owner;
	}

	public Long getCommentid() {
		return this.commentid;
	}

	public void setCommentid(Long commentid) {
		this.commentid = commentid;
	}

	public DropModel getDropHost() {
		return dropHost;
	}

	public void setDropHost(DropModel dropHost) {
		this.dropHost = dropHost;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreationdate() {
		return creationdate;
	}
	
	public String getCreationDateFormatted() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return formatter.format(creationdate.toLocalDate());
	}

	public void setCreationdate(LocalDateTime creationdate) {
		this.creationdate = creationdate;
	}

	public Long getDropHostid() {
		drophostid = dropHost.getDropId();
		return drophostid;
	}

	public void setDropHostid(Long dropHostid) {
		this.drophostid = dropHostid;
	}

	public Long getCommentownerid() {
		commentownerid = owner.getUserid();
		return commentownerid;
	}

	public void setCommentownerid(Long commentownerid) {
		this.commentownerid = commentownerid;
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