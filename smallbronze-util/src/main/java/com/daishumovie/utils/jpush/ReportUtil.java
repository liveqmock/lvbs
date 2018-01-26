package com.daishumovie.utils.jpush;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.ReportClient;
import com.daishumovie.utils.JacksonUtil;
import com.daishumovie.utils.StringUtil;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by feiFan.gou on 2017/10/19 14:16.
 */
public class ReportUtil extends Base {

    private static final ReportClient CLIENT;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportUtil.class);
    public static ReportUtil INSTANCE;

    static {
        CLIENT = new ReportClient(master_secret, app_key, null, ClientConfig.getInstance());
    }

    private ReportUtil() {

    }
    public static ReportUtil instance() {

        if (null == INSTANCE) {
            INSTANCE = new ReportUtil();
        }
        return INSTANCE;
    }

    public Result report(String ...msgId) {

        Result result;
        try {
            ReceivedsResult received = CLIENT.getReceiveds(msgId);
            if (received.getResponseCode() == HttpStatus.SC_OK) {
                result = Result.instance.success(received.received_list);
            } else {
                result = Result.instance.fail("获取报告失败");
            }
            String responseJson = StringUtil.trim(JacksonUtil.obj2json(received));
            LOGGER.info("report response === > \r {}",StringUtil.formatJson(responseJson));
        } catch (Exception e) {
            LOGGER.info("report exception ===> \r" + e.getMessage(), e);
            result = Result.instance.fail("获取报告失败");
        }
        return result;
    }
}
