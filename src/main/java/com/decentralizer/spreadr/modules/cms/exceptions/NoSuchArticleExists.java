package com.decentralizer.spreadr.modules.cms.exceptions;

public class NoSuchArticleExists extends RuntimeException {
    public NoSuchArticleExists() {
        super("No Such Article Exists");
    }
}
