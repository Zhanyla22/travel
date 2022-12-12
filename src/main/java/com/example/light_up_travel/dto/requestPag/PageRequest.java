package com.example.light_up_travel.dto.requestPag;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.print.PageFormat;
import java.awt.print.Pageable;
import java.awt.print.Printable;

@Getter
@Setter
@AllArgsConstructor
public class PageRequest implements Pageable {

    private int page;
    private int size;

    @Override
    public int getNumberOfPages() {
        return this.page;
    }

    @Override
    public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
        return null;
    }

    @Override
    public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
        return null;
    }
}
