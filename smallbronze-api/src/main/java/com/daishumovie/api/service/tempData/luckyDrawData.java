package com.daishumovie.api.service.tempData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daishumovie.base.dto.AttributeEntry;

public enum luckyDrawData {
	
	instance;
	
	@SuppressWarnings("unused")
	private String purpose = "临时";
	
	public List<AttributeEntry<String, Integer>> userList = new ArrayList<AttributeEntry<String, Integer>>() {
		private static final long serialVersionUID = -3407035835575202289L;

		{
			add(new AttributeEntry<>("我就是郑合惠子本人啊", 5));
			add(new AttributeEntry<>("全幼儿园最帅的就是我", 3));
			add(new AttributeEntry<>("冷情绪", 4));
			add(new AttributeEntry<>("落尘墨羽", 1));
			add(new AttributeEntry<>("总攻不颜笑", 3));
			add(new AttributeEntry<>("隔壁老实人阿王", 1));
			add(new AttributeEntry<>("月半仙子", 2));
			add(new AttributeEntry<>("帅癌晚期", 5));
			add(new AttributeEntry<>("被鱼欺负的猫", 3));
			add(new AttributeEntry<>("孤独老战友", 1));
			add(new AttributeEntry<>("你大爷永远是你大妈", 2));
			add(new AttributeEntry<>("酱油仔", 2));
			add(new AttributeEntry<>("一杯敬自由", 1));
			add(new AttributeEntry<>("铁头酱", 1));
			add(new AttributeEntry<>("我爱抹茶冰淇淋", 3));
			add(new AttributeEntry<>("我就是静静", 2));
			add(new AttributeEntry<>("高冷靡歌", 1));
			add(new AttributeEntry<>("你若安好那还了得", 2));
			add(new AttributeEntry<>("漂流瓶de小世界", 5));
			add(new AttributeEntry<>("咕咕小白爷", 1));
			add(new AttributeEntry<>("请叫我小仙男", 1));
			add(new AttributeEntry<>("网瘾失学美少女", 4));
			add(new AttributeEntry<>("巨炮叔叔", 2));
			add(new AttributeEntry<>("优秀少先队员", 2));
			add(new AttributeEntry<>("你是个妖怪吧", 3));
			add(new AttributeEntry<>("摸鱼的小菜鸟", 1));
			add(new AttributeEntry<>("好少年风流四方", 2));
			add(new AttributeEntry<>("诸葛小仙女", 2));
			add(new AttributeEntry<>("国民夫婿", 5));
			add(new AttributeEntry<>("萌小赞", 1));
			add(new AttributeEntry<>("一个小菇凉", 3));
			add(new AttributeEntry<>("独孤求撩", 1));
			add(new AttributeEntry<>("暴走聚乙烯", 1));
			add(new AttributeEntry<>("Potato先生", 2));
			add(new AttributeEntry<>("法号无趣", 4));
			add(new AttributeEntry<>("温柔陈七", 1));
			add(new AttributeEntry<>("吃饺子的汤圆", 3));
			add(new AttributeEntry<>("张武当", 1));
			add(new AttributeEntry<>("吸猫专业户", 2));
			add(new AttributeEntry<>("秦岭神医熊凤山", 3));
			add(new AttributeEntry<>("Lily皆辛苦", 2));
			add(new AttributeEntry<>("没肉不吃饭", 1));
			add(new AttributeEntry<>("放了一颗冲天屁", 1));
			add(new AttributeEntry<>("绯闻后爹", 2));
			add(new AttributeEntry<>("八级床震", 2));
			add(new AttributeEntry<>("依然饭特香", 4));
			add(new AttributeEntry<>("求约小瘪三", 1));
			add(new AttributeEntry<>("保护我方阿呆", 3));
			add(new AttributeEntry<>("板栗柚子饼", 1));
			add(new AttributeEntry<>("过敏的橙子", 4));

		}
	};

	public Map<Integer, String> awardMap = new HashMap<Integer, String>() {

		private static final long serialVersionUID = -8018090500825226921L;

		{
			put(1, "电影票一张");
			put(2, "腾讯视频会员");
			put(3, "爱奇艺视频会员");
			put(4, "优酷视频会员");
			put(5, "iPhone X一部");
		}
	};
}
