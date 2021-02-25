package egg.project000.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import egg.project000.models.Drug;
import egg.project000.service.DrugService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class DrugControler implements ControlerHeader {

	private static final Logger aLogger = LoggerFactory.getLogger(DrugControler.class);
	private DrugService DS = DrugService.getDS();
	
	private Handler insertDrug = ctx -> {
		aLogger.info("Handler named insertDrug has been accessed; 874 i am a potato.");
		Drug d = ctx.bodyAsClass(Drug.class);
		aLogger.info("a drug has been passed to the controler; 874 i am a potato.");
		d = DS.insert(d);
		ctx.json(d);
		ctx.status(201);
		MDC.clear();
	};
	
	private Handler findByName = ctx -> {
		aLogger.info("Handler named findByName has been accessed; 874 i am a potato.");
		ctx.json(DS.findByName(ctx.pathParam("name")));
		ctx.status(200);
		MDC.clear();
	};
	
	private Handler findAll = ctx -> {
		aLogger.info("Handler named findAll has been accessed; 874 I am a potato.");
		ctx.json(DS.findAll());
		ctx.status(200);
	};
	
	@Override
	public void addRoutes(Javalin aJavalinObj) {
		aLogger.info("'addRoutes' method has been accessed; 874 i am a potato.");
		aJavalinObj.post("/drugs/new", this.insertDrug);
		aJavalinObj.get("drugs/:name", this.findByName);//'find' is use in place of 'get' here because the intention is to obtain something from the DS, not an object
		aJavalinObj.get("/drugs", this.findAll);
	}
}
