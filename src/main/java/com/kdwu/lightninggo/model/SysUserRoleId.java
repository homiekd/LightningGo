package com.kdwu.lightninggo.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class SysUserRoleId implements Serializable {
    private int uId;
    private int rId;
}
