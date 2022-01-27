package example;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.annotation.Search;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.server.IResourceProvider;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hl7.fhir.instance.model.api.IBaseResource;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Organization;

public class OrganizationProvider implements IResourceProvider {

  private final FhirContext ctx;
  private final String serverUrl = "http://hapi.fhir.org/baseR4";
  private final IGenericClient client;

  public OrganizationProvider(FhirContext ctx) {
    this.ctx = ctx;
    client = ctx.getRestfulClientFactory().newGenericClient(serverUrl);
  }

  @Search(type = Organization.class, allowUnknownParams = true)
  public List<Organization> findOrganizations(HttpServletRequest theRequest,
      HttpServletResponse theResponse) {
    String searchString = theRequest.getRequestURI() + "?" + theRequest.getQueryString();
    Bundle returnBundle = client.search().byUrl(searchString).returnBundle(Bundle.class).execute();
    List<Organization> list = returnBundle.getEntry().stream()
        .map(e -> (Organization) e.getResource())
        .collect(Collectors.toList());

    return list;
  }

  @Override
  public Class<? extends IBaseResource> getResourceType() {
    return Organization.class;
  }
}
