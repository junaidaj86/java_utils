package com.postnord.ndm.base.jsonb_utils;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonMergePatch;
import javax.validation.Valid;

@ApplicationScoped
public class MergePatchService {
    public <T> @Valid T mergePatch(final JsonMergePatch mergePatch, final @Valid T targetBean, final Class<T> beanClass) {
        return JsonbHelper.mergePatch(mergePatch, targetBean, beanClass);
    }
}
