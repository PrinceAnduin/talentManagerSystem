package com.tema.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class getKeyWords {//加载字典中的所有词汇
	static Set<String> dic  = new HashSet<String>();
    /*
        匹配句子中词典中存在的所有词汇
     */
    public static List<String> getAllWordsMatched(String sentence){
    	if (dic.size() == 0) {
    		dic.add("北京");dic.add("广东");dic.add("山东");dic.add("江苏");dic.add("河南");dic.add("上海");dic.add("河北");dic.add("浙江");dic.add("香港");dic.add("陕西");
          dic.add("湖南");dic.add("重庆");dic.add("福建");dic.add("天津");dic.add("云南");dic.add("四川"); dic.add("广西");dic.add("安徽");dic.add("海南");dic.add("江西");
          dic.add("湖北");dic.add("山西");dic.add("辽宁");dic.add("台湾");dic.add("黑龙江");dic.add("内蒙古");dic.add("澳门");dic.add("贵州");dic.add("甘肃");dic.add("青海");
          dic.add("新疆");dic.add("西藏");dic.add("吉林");dic.add("宁夏");
            //市级字典
          dic.add("北京");dic.add("东莞");dic.add("广州");dic.add("中山");dic.add("深圳");dic.add("惠州");dic.add("江门");dic.add("珠海");dic.add("汕头");dic.add("梅州");
          dic.add("佛山");dic.add("湛江");dic.add("河源");dic.add("肇庆");dic.add("潮州");dic.add("清远");dic.add("韶关");dic.add("揭阳");dic.add("阳江");dic.add("云浮");
          dic.add("茂名");dic.add("济南");dic.add("青岛");dic.add("临沂");dic.add("济宁");dic.add("菏泽");dic.add("烟台");dic.add("泰安");dic.add("淄博");dic.add("潍坊");
          dic.add("日照");dic.add("威海");dic.add("滨州");dic.add("东营");dic.add("聊城");dic.add("德州");dic.add("莱芜");dic.add("枣庄");dic.add("苏州");dic.add("徐州");
          dic.add("盐城");dic.add("无锡");dic.add("南京");dic.add("南通");dic.add("连云港");dic.add("常州");dic.add("扬州");dic.add("镇江");dic.add("淮安");dic.add("泰州");
          dic.add("宿迁");dic.add("上海");dic.add("郑州");dic.add("南阳");dic.add("新乡");dic.add("安阳");dic.add("洛阳");dic.add("信阳");dic.add("平顶山");dic.add("周口");
          dic.add("商丘");dic.add("开封");dic.add("焦作");dic.add("驻马店");dic.add("濮阳");dic.add("三门峡");dic.add("漯河");dic.add("许昌");dic.add("鹤壁");dic.add("济源");
          dic.add("石家庄");dic.add("唐山");dic.add("承德");dic.add("邯郸");dic.add("邢台");dic.add("沧州");dic.add("秦皇岛");dic.add("张家口");dic.add("衡水");dic.add("廊坊");
          dic.add("保定");dic.add("温州");dic.add("宁波");dic.add("杭州");dic.add("台州");dic.add("嘉兴");dic.add("金华");dic.add("湖州");dic.add("绍兴");dic.add("舟山");
          dic.add("丽水");dic.add("衢州");dic.add("西安");dic.add("咸阳");dic.add("宝鸡");dic.add("汉中");dic.add("渭南");dic.add("安康");dic.add("榆林");dic.add("商洛");
          dic.add("延安");dic.add("铜川");dic.add("长沙");dic.add("邵阳");dic.add("常德");dic.add("衡阳");dic.add("株洲");dic.add("湘潭");dic.add("永州");dic.add("岳阳");
          dic.add("怀化");dic.add("郴州");dic.add("娄底");dic.add("益阳");dic.add("张家界");dic.add("湘西");dic.add("重庆");dic.add("漳州");dic.add("泉州");dic.add("厦门");
          dic.add("福州");dic.add("莆田");dic.add("宁德");dic.add("三明");dic.add("南平");dic.add("龙岩");dic.add("天津");dic.add("昆明");dic.add("曲靖");dic.add("昭通");
          dic.add("保山");dic.add("玉溪");dic.add("丽江");dic.add("西双版纳");dic.add("成都");dic.add("绵阳");dic.add("广元");dic.add("达州");dic.add("南充");dic.add("德阳");
          dic.add("广安");;dic.add("巴中");dic.add("遂宁");dic.add("内江");dic.add("凉山州");dic.add("攀枝花");dic.add("乐山");dic.add("自贡");dic.add("泸州");dic.add("贵港");
          dic.add("玉林");dic.add("北海");dic.add("南宁");dic.add("眉山");dic.add("资阳");dic.add("宜宾");dic.add("雅安");dic.add("柳州");dic.add("桂林");dic.add("梧州");
          dic.add("钦州");dic.add("来宾");dic.add("河池");dic.add("百色");dic.add("贺州");dic.add("崇左");dic.add("防城港");dic.add("芜湖");dic.add("合肥");dic.add("六安");
          dic.add("宿州");dic.add("阜阳");dic.add("安庆");dic.add("马鞍山");dic.add("蚌埠");dic.add("宣城");dic.add("黄山");dic.add("铜陵");dic.add("亳州");dic.add("池州");
          dic.add("三亚");dic.add("海口");dic.add("琼海");dic.add("文昌");dic.add("东方");dic.add("昌江");dic.add("陵水");dic.add("南昌");dic.add("赣州");dic.add("上饶");
          dic.add("吉安");dic.add("九江");dic.add("新余");dic.add("抚州");dic.add("武汉");dic.add("宜昌");dic.add("荆州");dic.add("孝感");dic.add("黄冈");dic.add("十堰");
          dic.add("黄石");dic.add("荆门");dic.add("天门");dic.add("太原");dic.add("大同");dic.add("焦作");dic.add("运城");dic.add("长治");dic.add("晋城");dic.add("大连");
          dic.add("沈阳");dic.add("丹东");dic.add("辽阳");dic.add("葫芦岛");dic.add("鞍山");dic.add("锦州");dic.add("抚顺");dic.add("台北");dic.add("高雄");dic.add("台中");
          dic.add("新竹");dic.add("齐齐哈尔");dic.add("哈尔滨");dic.add("大庆");dic.add("双鸭山");dic.add("牡丹江");dic.add("黑河");dic.add("鸡西");dic.add("佳木斯");
          dic.add("赤峰");dic.add("包头");dic.add("通辽");dic.add("呼和浩特");dic.add("梅州");dic.add("鄂尔多斯");dic.add("呼伦贝尔");dic.add("贵阳");dic.add("六盘水");
          dic.add("安顺");dic.add("清远");dic.add("遵义");dic.add("兰州");dic.add("天水");dic.add("庆阳");dic.add("酒泉");dic.add("张掖");dic.add("白银");dic.add("武威");
          dic.add("拉萨");dic.add("乌鲁木齐");dic.add("石河子");dic.add("克拉玛依");dic.add("和田");dic.add("吐鲁番");dic.add("吉林");dic.add("长春");dic.add("白山");
          dic.add("白城");dic.add("松原");dic.add("银川");dic.add("吴忠");dic.add("中卫");dic.add("石嘴山");dic.add("香港");dic.add("澳门");
            //行业字典
          dic.add("金融");dic.add("互联网");dic.add("农业"); dic.add("林业");dic.add("娱乐业"); dic.add("采矿业");dic.add("制造业");dic.add("服装业");dic.add("食品制造业");
          dic.add("纺织业");dic.add("家具制造业");dic.add("医疗行业");dic.add("汽车行业");dic.add("铁路行业");dic.add("物流行业");dic.add("建筑业");dic.add("电力行业");
          dic.add("批发业");dic.add("零售业");dic.add("航空业");dic.add("餐饮业");
            //职位字典
          dic.add("程序员");dic.add("产品经理");dic.add("产品总监");dic.add("UI设计师");dic.add("交互设计师");dic.add("新媒体运营");dic.add("产品运营");dic.add("网络推广");
          dic.add("市场营销");dic.add("市场推广");dic.add("采购主管");dic.add("品牌推广");dic.add("策划经理");dic.add("行政主管");dic.add("CEO");dic.add("物业经理");
          dic.add("药剂师");dic.add("营养师");dic.add("物流专员");dic.add("服务员");dic.add("发型师");dic.add("生产总监");dic.add("生产员");
            //程序员字典
          dic.add("项目经理");dic.add("软件工程师");dic.add("嵌入式软件工程师");dic.add("java开发工程师");dic.add("软件开发工程师");dic.add("android");
          dic.add("嵌入式软件开发工程师");dic.add("软件测试工程师");dic.add("高级软件工程师");dic.add("测试工程师");dic.add("产品经理");
          dic.add(".net");dic.add("java");dic.add("高级项目经理");dic.add("技术支持工程师");
          dic.add("ios");dic.add("C++");dic.add("高级java开发工程师");dic.add("实施工程师");dic.add("软件实施工程师");dic.add("前端开发工程师");dic.add("项目助理");
          dic.add("web前端开发工程师");dic.add("java软件开发工程师");dic.add("软件项目经理");dic.add("施工员");dic.add("软件开发");dic.add("硬件工程师");dic.add("嵌入式开发工程师");
          dic.add("技术总监");dic.add("售前工程师");dic.add("嵌入式软件开发");
         dic.add("高级软件开发工程师");dic.add("c#");
          dic.add("前端");dic.add("后端");dic.add("数据库");dic.add("测试");dic.add("HR");dic.add("市场管理");dic.add("游戏开发");dic.add("H5");dic.add("CSS");dic.add("javaScript");dic.add("javascript");
    	}
        List<String> wordList = new ArrayList<>();
        for(int index = 0;index < sentence.length();index++){
            for(int offset = index+1; offset <= sentence.length();offset++){
                String sub = sentence.substring(index,offset);
                if(dic.contains(sub)){
                    wordList.add(sub);
                }
            }
        }
        return wordList;
    }
    /*
        获得String List中每个String出现的次数
     */
    public static Map<String,Integer> frequencyOfListElements( List<String> items ) {
        if (items == null || items.size() == 0) return null;
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (String temp : items) {
            Integer count = map.get(temp);
            map.put(temp, (count == null) ? 1 : count + 1);
        }
        return map;
    }
}

