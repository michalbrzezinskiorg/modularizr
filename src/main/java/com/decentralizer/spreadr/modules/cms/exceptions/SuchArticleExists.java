package com.decentralizer.spreadr.modules.cms.exceptions;

public class SuchArticleExists extends RuntimeException {
    public SuchArticleExists() {
        super("Such Article Already Exists");
    }
}
