package com.kdwu.lightninggo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "PROD_BRAND")
public class ProductBrand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BRAND_SEQ")
    @SequenceGenerator(sequenceName = "PROD_BRAND_SEQ", name = "BRAND_SEQ", allocationSize = 1 )
    @Column(name = "BRAND_ID", unique = true, nullable = false, length = 15)
    private Integer id;

    @NonNull
    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @NonNull
    @Column(name = "NAME", nullable = false, length = 200)
    private String name;

    @Column(name = "URL", length = 100)
    private String url;

    @Column(name = "LOGO", length = 50)
    private String logo;

    @Column(name = "IMAGE", length = 50)
    private String imgUrl;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SORT", length = 10)
    private Integer sort;

    @Column(name = "STATE", length = 1)
    private Boolean state = Boolean.FALSE;

    @Column(name = "CREATOR", length = 50)
    private String creator;

    @Column(name = "CREATED_DATE")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date createdDate;

    @Column(name = "MODIFIER", length = 50)
    private String modifier;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductBrand that = (ProductBrand) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
