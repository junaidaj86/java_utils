package com.postnord.ndm.base.jpa_utils.utils;

import com.postnord.ndm.base.jwt_handler.model.AllowedAccounts;
import com.postnord.ndm.base.rsql_parser.RsqlGenerator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.*;

@ApplicationScoped
public class MultiTenancyService {

    @Inject
    AllowedAccounts allowedAccounts;

    /**
     * Check if allowedAccounts provides access to one specific asset attribute
     *
     * @param assetAccountId The accountId of the asset attribute
     * @return true if access should be granted, false otherwise
     */
    public boolean isAuthorized(final String assetAccountId) {
        return allowedAccounts.containsSuperUser() || allowedAccounts.toIds().contains(assetAccountId);
    }

    /**
     * Check if allowedAccounts provides access to asset with many asset attributes, e.g. accountId, provider and users
     *
     * @param assetAccountIds The collection of accountIds of all asset attributes
     * @return true if access should be granted, false otherwise
     */
    public boolean isAuthorized(final Collection<String> assetAccountIds) {
        return Objects.requireNonNullElse(assetAccountIds, new ArrayList<String>()).stream().anyMatch(this::isAuthorized);
    }

    /**
     * Check if allowedAccounts provides access to asset with many asset attributes, e.g. accountId, provider and users
     *
     * @param assetAccountIds The collection of accountIds of all asset attributes
     * @return true if access should be granted, false otherwise
     */
    public boolean isAuthorized(final String... assetAccountIds) {
        return isAuthorized(Arrays.asList(assetAccountIds));
    }

    /**
     * Create RSQL filter for query of asset(s) with a given attribute.
     *
     * @param assetAttribute The asset attribute
     * @param single         Attribute has single accountId or a collection of them (e.g. users)
     * @return The created RSQL filter
     */
    public String createRsqlFilter(final String assetAttribute, final boolean single) {
        return appendCriteria(new RsqlGenerator(), assetAttribute, single).end().execute();
    }

    /**
     * Create RSQL filter for query of asset(s) with a given collection of attributes.
     *
     * @param assetAttributeMap The asset attributes. The key holds the actual attribute name and the value indicates if the attribute has
     *                          single accountId or a collection of them (e.g. users)
     * @return The created RSQL filter
     */
    public String createRsqlFilter(final Map<String, Boolean> assetAttributeMap) {
        final RsqlGenerator rsqlGenerator = new RsqlGenerator();
        final Iterator<Map.Entry<String, Boolean>> iterator = Objects.requireNonNull(assetAttributeMap).entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<String, Boolean> assetAttributeEntry = iterator.next();
            final RsqlGenerator.Combiner combiner = appendCriteria(rsqlGenerator, assetAttributeEntry.getKey(), assetAttributeEntry.getValue());
            if (iterator.hasNext()) {
                combiner.or();
            } else {
                combiner.end();
            }
        }

        return rsqlGenerator.execute();
    }

    private RsqlGenerator.Combiner appendCriteria(final RsqlGenerator rsqlGenerator, final String assetAttribute, final boolean single) {
        if (single) {
            return rsqlGenerator.eq(assetAttribute, allowedAccounts.toIds(), RsqlGenerator.CombineOperation.OR);
        }
        return rsqlGenerator.contains(assetAttribute, allowedAccounts.toIds(), RsqlGenerator.CombineOperation.OR);
    }
}
