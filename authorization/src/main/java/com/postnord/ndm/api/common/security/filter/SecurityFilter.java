package com.postnord.ndm.api.common.security.filter;

import com.postnord.ndm.api.common.problem.Problem;
import com.postnord.ndm.api.common.problem.ProblemResponse;
import com.postnord.ndm.api.common.security.context.NdmSecurityContext;

import org.eclipse.microprofile.config.ConfigProvider;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.stream.Collectors;

import jakarta.json.Json;
import jakarta.json.JsonString;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.PreMatching;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.ext.Provider;

import static com.postnord.ndm.api.common.security.util.ConstantsHelper.BEARER_RESERVED_WORD;
import static com.postnord.ndm.api.common.security.util.ConstantsHelper.UTC_ZONE;
import static jakarta.ws.rs.core.Response.Status.UNAUTHORIZED;

@Provider
@PreMatching
public class SecurityFilter implements ContainerRequestFilter {

    @Override
    public void filter(final ContainerRequestContext containerRequestContext) throws IOException {

        final var authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (Objects.isNull(authorizationHeader) || !authorizationHeader.startsWith(BEARER_RESERVED_WORD + " ")) {
            containerRequestContext.abortWith(ProblemResponse
                    .builder()
                    .problem(Problem.builder()
                            .type(URI.create("https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2"))
                            .title(UNAUTHORIZED.getReasonPhrase())
                            .status(UNAUTHORIZED.getStatusCode())
                            .detail("Invalid Authorization Header")
                            .build())
                    .build());
        } else {

            final var ndmSecurityContext =
                    extractValuesFromToken(authorizationHeader.substring(BEARER_RESERVED_WORD.length()).trim());

            if (maxAge(ndmSecurityContext.getExp()) <= 0) {
                containerRequestContext.abortWith(ProblemResponse.builder()
                        .problem(Problem
                                .builder()
                                .type(URI.create("https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.2"))
                                .title(UNAUTHORIZED.getReasonPhrase())
                                .status(UNAUTHORIZED.getStatusCode())
                                .detail("Token has expired")
                                .build())
                        .build());
            }

            final SecurityContext currentSecurityContext = containerRequestContext.getSecurityContext();

            containerRequestContext.setSecurityContext(new SecurityContext() {
                @Override
                public Principal getUserPrincipal() {
                    return () -> ndmSecurityContext.getUpn().trim();
                }

                @Override
                public boolean isUserInRole(final String role) {
                    final var currentTokenRoles = Arrays.asList(ConfigProvider.getConfig().getValue("ndm_jwt.roles-allowed", String[].class));
                    return ndmSecurityContext.getRoles().stream().anyMatch(currentTokenRoles::contains);
                }

                @Override
                public boolean isSecure() {
                    return currentSecurityContext.isSecure();
                }

                @Override
                public String getAuthenticationScheme() {
                    return BEARER_RESERVED_WORD;
                }
            });

        }
    }

    private long maxAge(final long tokenExpires) {
        return Duration.between(
                Instant.now().atZone(UTC_ZONE),
                Instant.ofEpochSecond(tokenExpires).atZone(UTC_ZONE)
        ).getSeconds();
    }

    private String getJson(final String strEncoded) {
        return new String(Base64.getDecoder().decode(strEncoded), StandardCharsets.UTF_8);
    }

    private NdmSecurityContext extractValuesFromToken(final String token) {
        final var split = token.split("\\.", -1);

        try (var jsonReader = Json.createReader(new StringReader(getJson(split[1])))) {

            final var claims = ConfigProvider.getConfig().getValue("ndm_jwt.claims", String[].class);
            final var tokenInstance = jsonReader.readObject();

            final var roles = tokenInstance
                    .getJsonArray(claims[0].trim())
                    .getValuesAs(JsonString.class)
                    .stream()
                    .map(JsonString::getString)
                    .collect(Collectors.toSet());

            final var exp = tokenInstance
                    .getJsonNumber(claims[1].trim())
                    .longValue();

            String upn = "upn";
            if (tokenInstance.containsKey(claims[2].trim())) {
                upn = tokenInstance
                        .getString(claims[2].trim());
            }

            return NdmSecurityContext
                    .builder()
                    .roles(roles)
                    .exp(exp)
                    .upn(upn)
                    .build();
        }

    }
}
