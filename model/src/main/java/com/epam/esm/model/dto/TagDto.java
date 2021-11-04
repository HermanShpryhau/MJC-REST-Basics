package com.epam.esm.model.dto;

import com.epam.esm.model.validation.ValidationErrorCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TagDto implements DataTransferObject {
    private Long id;

    @NotNull(message = ValidationErrorCode.TAG_NAME_NOT_NULL)
    @Size(min = 1, max = 45, message = ValidationErrorCode.INVALID_TAG_NAME)
    private String name;

    public TagDto() {
    }

    public TagDto(String name) {
        this.name = name;
    }

    public TagDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagDto)) return false;

        TagDto tagDto = (TagDto) o;

        if (!id.equals(tagDto.id)) return false;
        return name.equals(tagDto.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TagDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
