package com.daishumovie.search.service;

import java.io.IOException;

/**
 * Created by cheng
 */
public interface SuggestSearch {
    String completionSuggest(String keyword) throws IOException;
}
