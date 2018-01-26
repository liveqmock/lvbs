package com.daishumovie.base.dto.audio;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by yang on 2017/8/17.
 */
@Data
public class WordsResultDto implements Serializable {

    private String wordBg;
    private String wordEd;
    private String wordsName;
    private String toneNumber;

}
