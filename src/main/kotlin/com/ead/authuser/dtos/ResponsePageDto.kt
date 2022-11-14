package com.ead.authuser.dtos

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

class ResponsePageDto<T>(content: MutableList<T>, pageable: Pageable, total: Long) :
    PageImpl<T>(content, pageable, total) {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    constructor(
        @JsonProperty("content") content: MutableList<T> = mutableListOf(),
        @JsonProperty("number") number: Int = 0,
        @JsonProperty("size") size: Int = 0,
        @JsonProperty("totalElements") totalElements: Long = 10,
        @JsonProperty("pageable") pageable: JsonNode? = null,
        @JsonProperty("last") last: Boolean = false,
        @JsonProperty("totalPages") totalPages: Int = 0,
        @JsonProperty("sort") sort: JsonNode? = null,
        @JsonProperty("first") first: Boolean = false,
        @JsonProperty("empty") empty: Boolean = false,
    ) : this(content, PageRequest.of(number, size), totalElements)
}