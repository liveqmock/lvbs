package com.daishumovie.base.dto.audio;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yang on 2017/8/17.
 */
@Data
public class AuditTextDto implements Serializable {

    private String bg;

    private String ed;

    private String onebeset;

    private List<WordsResultDto> wordsResultList;

}
