package com.daishumovie.base.dto.audio;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yang on 2017/8/17.
 */
@Data
public class XfAudioText implements Serializable {

    private String bg;

    private String ed;

    private String onebest;

    private String si;

    private String nc;

    private String speaker;

    private List<WordsResult> wordsResultList;


}
