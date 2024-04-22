package apis;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ejbs.Calculation;

@Stateless
@Path("")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class Service {
	@PersistenceContext(unitName = "hello")
	private EntityManager em;

	@POST
	@Path("/calc")
	public Response calculate(Calculation calc) {
		try {

			int result = 0;
			if (calc.getOperation().equals("+")) {
				result = calc.addition();
			}

			if (calc.getOperation().equals("-")) {
				result = calc.subtract();
			}

			if (calc.getOperation().equals("/")) {
				result = calc.division();
			}

			if (calc.getOperation().equals("*")) {
				result = calc.mult();
			}
			em.persist(calc);
			return Response.ok(result).build();
		} catch (Exception e) {

			return Response.serverError().entity(e.getMessage()).build();
		}
	}
                   
	@GET
	@Path("/calculations")
	public Response calculations() {
		
		
		try {
			return Response.ok(em.createQuery("select c from Calculation c"  ).getResultList()).build();
		} catch (Exception e) {

			return Response.serverError().entity(e.getMessage()).build();
		}
	}

}