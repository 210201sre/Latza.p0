package egg.project000.controlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import egg.project000.models.Patient;
import egg.project000.service.PatientService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class PatientControler implements ControlerHeader {

	private static final Logger aLogger = LoggerFactory.getLogger(PatientControler.class);
	private PatientService aPatientService = new PatientService();
	
	private Handler insertPatient = ctx -> {
		Patient p = ctx.bodyAsClass(Patient.class);
		aLogger.info("printing the patient passed to controller's insert method" + p.toString());
		// Once again, requires Jackson Databind to work properly
		// Save the new User in the DB
		p = aPatientService.insert(p);		
		ctx.json(p); // Send back the registered User with the updated ID
		ctx.status(201); // 201 Created
		MDC.clear();
	};
	
	private Handler findAll = ctx -> {
		ctx.json(aPatientService.findAll());
		ctx.status(200);
		MDC.clear();
	};
	
	/**
	 * snippit from ctx.pathParam("String");
     * Gets a path param by name (ex: pathParam("param").
     *
     * Ex: If the handler path is /users/:user-id,
     * and a browser GETs /users/123,
     * pathParam("user-id") will return "123"
     */
	private Handler findByName = ctx ->{
	//	String nameString = ctx.pathParam("name");
		ctx.json(aPatientService.findPatientByName(ctx.pathParam("name")));
		ctx.status(200);
	};
	
	@Override
	public void addRoutes(Javalin aJavalinObj) {
		aLogger.info("'addRoutes' method has been accessed; 874 i am a potato.");
		aJavalinObj.post("/patients/new", this.insertPatient);
		aJavalinObj.get("/patients", this.findAll);
		aJavalinObj.get("/patients/:name", this.findByName);
		
	}

}
