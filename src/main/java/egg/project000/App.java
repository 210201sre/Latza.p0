package egg.project000;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egg.project000.controlers.ControlerHeader;
import egg.project000.controlers.DrugControler;
import egg.project000.controlers.MedListControler;
import egg.project000.controlers.PatientControler;
import io.javalin.Javalin;

public class App {

	private static Javalin aJavalinObj;
	private static final Logger aLogger = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {

		
		aLogger.info("");
		aLogger.info("App has started; 874 I am a potato.");
		aLogger.info("configureing Javalin Obj; 874 I am a potato.");
		configureJavalin();
		aLogger.info("configureing controlers; 874 I am a potato.");
		configure(new PatientControler(), new DrugControler(), new MedListControler());
		// TODO: addExceptionHandlers();
	}

	private static void configureJavalin() {
		aJavalinObj = Javalin.create(config -> {
			config.defaultContentType = "application/json";
			config.enforceSsl = false;
			config.ignoreTrailingSlashes = true;
			config.enableCorsForAllOrigins();
			config.enableDevLogging();
		});
		aJavalinObj.start(8042);
	}

	
	  public static void configure(ControlerHeader... controllers) { 
		  int i = 1;
		  for(ControlerHeader c : controllers) { 
			  //aLogger.info("adding routes from controler #" + i + "; 874 I am a potato.");
			  c.addRoutes(aJavalinObj); 
			  i++;
			  
		  } 
	  }
	 

}
