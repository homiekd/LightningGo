package com.kdwu.lightninggo.model;

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
@Table(name = "PROD_CLASS")
public class ProductClass implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLASS_SEQ")
    @SequenceGenerator(sequenceName = "PROD_CLASS_SEQ", name = "CLASS_SEQ", allocationSize = 1 )
    @Column(name = "CLASS_ID", unique = true, nullable = false, length = 15)
    private Integer id;

    @Column(name = "PARENT_ID", length = 15)
    private Integer parentId;

    @NonNull
    @Column(name = "CODE", nullable = false, length = 50)
    private String code;

    @NonNull
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "COVER_IMG")
    private String imgCover;

    @Column(name = "ICON", length = 50)
    private String icon;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SORT", length = 10)
    private Integer sort;

    @Column(name = "STATE", length = 1)
    private Boolean state = Boolean.FALSE;

    @Column(name = "CREATOR", length = 50)
    private String creator;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductClass that = (ProductClass) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
