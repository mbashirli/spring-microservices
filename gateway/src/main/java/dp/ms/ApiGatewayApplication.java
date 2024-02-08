package dp.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.gateway.route.RouteLocator;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication
{
    public static void main( String[] args ) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("order-service", r -> r.path("/api/order/**")
                        .uri("lb://order-service"))
                .route("product-service", r -> r.path("/api/product/**")
                        .uri("lb://product-service"))
                .route("discovery-server", r -> r.path("/eureka/**")
                        .filters(f -> f.rewritePath("/eureka/web/(?<segment>.*)", "/${segment}")
                                .stripPrefix(2))
                        .uri("http://localhost:8761"))
                .route("inventory-service", r -> r.path("/api/inventory/**")
                        .uri("lb://inventory-service"))
                .build();
    }
}
