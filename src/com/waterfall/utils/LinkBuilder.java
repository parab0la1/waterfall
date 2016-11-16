package com.waterfall.utils;

import javax.ws.rs.core.UriInfo;

import com.waterfall.models.LinkModel;

public class LinkBuilder {

	public LinkBuilder() {

	}

	public static LinkModel buildSelfLink(Class entityClass, UriInfo uriInfo, Long id, String relation) {
		String uri = uriInfo.getBaseUriBuilder().path(entityClass).path(Long.toString(id)).build().toString();

		LinkModel linkModel = new LinkModel();
		linkModel.setUri(uri);
		linkModel.setRel(relation);

		return linkModel;

	}

	public static LinkModel buildDropLink(Class entityClass, UriInfo uriInfo, Long id, String relation) {
		String uri = uriInfo.getBaseUriBuilder().path(entityClass).path(Long.toString(id)).path("drops").build()
				.toString();

		LinkModel linkModel = new LinkModel();
		linkModel.setUri(uri);
		linkModel.setRel(relation);

		return linkModel;

	}

	public static LinkModel buildCommentLink(Class entityClass, UriInfo uriInfo, Long id, String relation) {
		String uri = uriInfo.getBaseUriBuilder().path(entityClass).path(Long.toString(id)).path("comments").build()
				.toString();

		LinkModel linkModel = new LinkModel();
		linkModel.setUri(uri);
		linkModel.setRel(relation);

		return linkModel;
	}

	public static LinkModel buildOwnerLink(Class entityClass, UriInfo uriInfo, Long id, String relation) {
		String uri = uriInfo.getBaseUriBuilder().path(entityClass).path(Long.toString(id)).build().toString();

		LinkModel linkModel = new LinkModel();
		linkModel.setUri(uri);
		linkModel.setRel(relation);

		return linkModel;
	}

}
