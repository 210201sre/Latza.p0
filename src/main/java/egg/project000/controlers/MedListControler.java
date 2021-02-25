package egg.project000.controlers;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egg.project000.models.MedListElement;
import egg.project000.service.DrugService;
import egg.project000.service.MedListService;
import egg.project000.service.PatientService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

public class MedListControler implements ControlerHeader {
	
	private static final Logger aLogger = LoggerFactory.getLogger(MedListControler.class);
	private MedListService MLS = MedListService.getMLS();
	private PatientService PS = PatientService.getPS();
	private DrugService DS = DrugService.getDS();
	
	
	private Handler addDrugToMedlist = ctx -> {
		aLogger.info("Handler named addDrugToMedlist has been accessed; 874 i am a potato.");
		MedListElement aMLE = ctx.bodyAsClass(MedListElement.class);
		aLogger.info("A MedListElement has been parsed from HTTP body");
		MLS.insert(aMLE);
		ctx.json(aMLE);
		ctx.status(201);
		MDC.clear();
	};
	private Handler incFill = ctx -> {
		aLogger.info("Handler named incFill has been accessed; 874 i am a potato.");
		MedListElement MLE = ctx.bodyAsClass(MedListElement.class);
		MLS.incFill(MLE);
		ctx.json(MLE);
		ctx.status(200);
		MDC.clear();
	};
	private Handler decFill = ctx -> {
		aLogger.info("Handler named incFill has been accessed; 874 i am a potato.");
		MedListElement MLE = ctx.bodyAsClass(MedListElement.class);
		MLS.decFill(MLE);
		ctx.json(MLE);
		ctx.status(200);
		MDC.clear();
	};
	private Handler getMedList = ctx -> {
		aLogger.info("Handler named incFill has been accessed; 874 i am a potato.");
		ctx.json(MLS.getMedList(Integer.parseInt(ctx.pathParam("id"))));
		MDC.clear();
	};
	
	@Override
	public void addRoutes(Javalin aJavalinObj) {
		aLogger.info("'addRoutes' method has been accessed; 874 i am a potato.");
		aJavalinObj.post("patient_meds/perscribe", this.addDrugToMedlist);
		aJavalinObj.post("/patient_meds/addFill", this.incFill);
		aJavalinObj.post("/patient_meds/removeFill", this.decFill);
		aJavalinObj.get("patient_meds/getMedListById/:id", this.getMedList);
		// TODO: get patient med list 

	}


	

}
