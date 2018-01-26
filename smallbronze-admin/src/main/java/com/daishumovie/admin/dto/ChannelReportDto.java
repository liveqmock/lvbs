package com.daishumovie.admin.dto;

import com.daishumovie.dao.model.UDTjVideoReport;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by yang on 2017/10/24.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ChannelReportDto extends UDTjVideoReport {


    private String t_f;

    private String name;

}
