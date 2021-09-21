package com.kdwu.lightninggo.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * page包裝類
 * @param <T>
 */
@Getter
@Setter
public class PageUtil<T> {
    private List<T> data = new ArrayList<>(); //content
    private long totalCount; //totalElements
}
