package com.waterfall.RESTResources;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Vector;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.waterfall.EJB.interfaces.LocalDrop;
import com.waterfall.EJB.interfaces.LocalUser;
import com.waterfall.models.CommentModel;
import com.waterfall.models.DropModel;
import com.waterfall.utils.LinkBuilder;
import com.waterfall.validators.CreateDropValidator;

@Path("/drops")
public class DropRestResource {

	@EJB
	private LocalDrop dropEjb;

	@EJB
	private LocalUser userEjb;

	@EJB
	private CreateDropValidator createDropValidator;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{userId}")
	public Response createDropModel(@PathParam("userId") Long userId, DropModel dropModel) {
		dropModel.setOwner(userEjb.getUserById(userId));
		dropModel.setCreationDate(LocalDateTime.now());

		if (createDropValidator.validateRestDrop(dropModel.getContent())) {
			dropEjb.storeDrop(dropModel);
			return Response.status(Response.Status.CREATED).entity(dropModel).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{dropModelId}")
	public Response getDropModel(@PathParam("dropModelId") Long dropModelId, @Context UriInfo uriInfo) {
		DropModel dropModel = dropEjb.getDrop(dropModelId);

		if (dropModel == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		dropModel.addLink(LinkBuilder.buildSelfLink(DropRestResource.class, uriInfo, dropModel.getDropId(), "Self"));
		dropModel.addLink(
				LinkBuilder.buildOwnerLink(UserRestResource.class, uriInfo, dropModel.getOwner().getUserid(), "Owner"));
		dropModel.addLink(
				LinkBuilder.buildCommentLink(DropRestResource.class, uriInfo, dropModel.getDropId(), "Comments"));

		return Response.status(Response.Status.OK).entity(dropModel).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDrops(@Context UriInfo uriInfo) {

		// A generic wrapper for returning a messagebody that works with
		// java.util.Vector
		GenericEntity<List<DropModel>> dropListForPresentation = new GenericEntity<List<DropModel>>(
				provideLinksForDrops(dropEjb.getAllDrops(), uriInfo)) {
		};

		return Response.status(Response.Status.OK).entity(dropListForPresentation).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{dropModelId}/comments")
	public Response getDropComments(@PathParam("dropModelId") Long dropModelId, @Context UriInfo uriInfo) {
		List<CommentModel> comments = dropEjb.getDrop(dropModelId).getComments();

		for (CommentModel commentModel : comments) {
			commentModel.addLink(LinkBuilder.buildOwnerLink(DropRestResource.class, uriInfo,
					commentModel.getDropHost().getDropId(), "DropHost"));
			commentModel.addLink(LinkBuilder.buildOwnerLink(UserRestResource.class, uriInfo, commentModel.getOwner().getUserid(), "Owner"));
		}

		// A generic wrapper for returning a messagebody that works with
		// java.util.Vector
		GenericEntity<List<CommentModel>> commentListForPresentation = new GenericEntity<List<CommentModel>>(comments) {
		};

		return Response.status(Response.Status.OK).entity(commentListForPresentation).build();
	}

	// @PUT
	// @Consumes(MediaType.APPLICATION_JSON)
	// @Produces(MediaType.APPLICATION_JSON)
	// @Path("/{dropModelId}")
	// public Response updateDropModel(@PathParam("dropModelId") Long
	// dropModelId, DropModel dropModel) {
	// DropModel dropModelToUpdate = dropEjb.getDrop(dropModelId);
	// dropModelToUpdate.setContent(dropModel.getContent());
	//
	// if (createDropValidator.validateRestDrop(dropModelToUpdate.getContent()))
	// {
	// dropEjb.storeDrop(dropModelToUpdate);
	// return
	// Response.status(Response.Status.OK).entity(dropModelToUpdate).build();
	// } else {
	// return Response.status(Response.Status.BAD_REQUEST).build();
	// }
	// }

	// @DELETE
	// @Path("/{dropModelId}")
	// public Response deleteDropModel(@PathParam("dropModelId") Long
	// dropModelId) {
	// DropModel dropModel = dropEjb.getDrop(dropModelId);
	//
	// dropEjb.deleteDrop(dropModel);
	// return Response.status(Response.Status.OK).build();
	// }

	private List<DropModel> provideLinksForDrops(List<DropModel> drops, UriInfo uriInfo) {
		for (DropModel dropModel : drops) {
			dropModel
					.addLink(LinkBuilder.buildSelfLink(DropRestResource.class, uriInfo, dropModel.getDropId(), "Self"));
			dropModel.addLink(
					LinkBuilder.buildCommentLink(DropRestResource.class, uriInfo, dropModel.getDropId(), "Comments"));
			dropModel.addLink(LinkBuilder.buildOwnerLink(UserRestResource.class, uriInfo,
					dropModel.getOwner().getUserid(), "Owner"));
		}

		return drops;
	}
}
