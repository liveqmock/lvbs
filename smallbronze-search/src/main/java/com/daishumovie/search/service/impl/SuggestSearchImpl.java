package com.daishumovie.search.service.impl;

import com.daishumovie.search.service.SuggestSearch;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.client.JestResult;
import io.searchbox.core.Suggest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cheng
 */
@Service
@Slf4j
public class SuggestSearchImpl implements SuggestSearch {

    @Autowired
    private JestClient jestClient;

    @Override
    public String completionSuggest(String keyword) throws IOException {

        List<String> resultList = new ArrayList<>();
        try {
            String context = XContentFactory.jsonBuilder()
                    .startObject().startObject("suggest")
                    .field("prefix", keyword)
                    .field("completion").startObject()
                    .field("field", "suggest")
                    .field("size", 10)
                    .field("contexts").startObject()
                    // TODO: 2017/11/1
                    .array("place_type", "practitioner", "tag", "videoName")
                    .endObject()
                    .endObject()
                    .endObject().string();
            log.info("suggest DSL : {}",context);
            Suggest suggest = new Suggest.Builder(context).build();
            JestResult jestResult = jestClient.execute(suggest);
            JsonObject jsonObject = jestResult.getJsonObject();
            Set<Map.Entry<String, JsonElement>> set = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> elementEntry : set) {
                if ("suggest".equals(elementEntry.getKey())) {
                    JsonElement element = elementEntry.getValue();
                    JsonArray jsonArray = element.getAsJsonArray();
                    JsonObject object = jsonArray.get(0).getAsJsonObject();
                    JsonElement jsonElement = object.get("options");
                    JsonArray array = jsonElement.getAsJsonArray();
                    if (null != array && array.size() > 0) {
                        for (int j = 0; j < array.size(); j++) {
                            resultList.add(array.get(j).getAsJsonObject().get("_source").getAsJsonObject().get("name").getAsString());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("suggest 结果集：{}", resultList.toString());
        return resultList.toString();
    }
}
