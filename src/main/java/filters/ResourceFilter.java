package filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class ResourceFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final String beginTimeProp = "filters.ResourceFilter.beginTime";
    private final Logger logger = LoggerFactory.getLogger(ResourceFilter.class);

    public ResourceFilter() {
        System.out.println("Вызван конструктор ResourceFilter!");
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        requestContext.setProperty(beginTimeProp, System.currentTimeMillis());

        StringBuilder sb = new StringBuilder("HTTP запрос: ");
        sb.append(" - HttpMethod: ").append(requestContext.getMethod());
        sb.append(" - Path: ").append(requestContext.getUriInfo().getPath());
        sb.append(" - QueryParams: ").append(requestContext.getUriInfo().getQueryParameters());
        sb.append(" - Header: ").append(requestContext.getHeaders());
        //sb.append(" - Entity: ").append());

        logger.info(sb.toString());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (requestContext.getProperty(beginTimeProp) != null) {
            long leadTime = System.currentTimeMillis() - (long)requestContext.getProperty(beginTimeProp);

            StringBuilder sb = new StringBuilder("HTTP ответ: ");
            sb.append(" - Header: ").append(responseContext.getHeaders());
            sb.append(" - Entity: ").append(responseContext.getEntity());
            sb.append(" - Status: ").append(responseContext.getStatus());
            sb.append(" - Время обработки: ").append((double)leadTime / 1000).append(" сек");

            logger.info(sb.toString());

            requestContext.removeProperty(beginTimeProp);
        }
    }
}
