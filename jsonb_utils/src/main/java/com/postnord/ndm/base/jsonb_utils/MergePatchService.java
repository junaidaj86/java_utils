package com.postnord.ndm.base.jsonb_utils;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.JsonMergePatch;
import jakarta.validation.Valid;

@ApplicationScoped
public class MergePatchService {
    public <T> @Valid T mergePatch(final JsonMergePatch mergePatch, final @Valid T targetBean, final Class<T> beanClass) {
        return JsonbHelper.mergePatch(mergePatch, targetBean, beanClass);
    }
}
