package example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.server.RestfulServer;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;

@WebServlet("/*")
public class Server extends RestfulServer {

  @Override
  protected void initialize() throws ServletException {
    // Create a context for the appropriate version
    FhirContext ctx = FhirContext.forR4();
    setFhirContext(ctx);

    // Register resource providers
    registerProvider(new OrganizationProvider(ctx));
    registerInterceptor(new ResponseHighlighterInterceptor());
  }
}

