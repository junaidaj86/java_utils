package com.postnord.ndm.base.jsonb_utils;

import com.postnord.ndm.api.common.validation.constraints.NdmNotNullParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestData<T> {
    @Valid
    @NdmNotNullParam
    private T data;
}