package com.daishumovie.base.dto.audio;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yang on 2017/8/17.
 */

@Data
public class WordsResult implements Serializable {

    private List alternativeList;

    private String wc;

    private String wordBg;

    private String wordEd;

    private String wordsName;

    private String wp;
}
