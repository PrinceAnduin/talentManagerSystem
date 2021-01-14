package com.tema.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class getKeyWords {//加载字典中的所有词汇
    @SuppressWarnings("unchecked")
	static Set<String> dic  = new HashSet(){{
        //省级字典
        add("北京");add("广东");add("山东");add("江苏");add("河南");add("上海");add("河北");add("浙江");add("香港");add("陕西");
        add("湖南");add("重庆");add("福建");add("天津");add("云南");add("四川"); add("广西");add("安徽");add("海南");add("江西");
        add("湖北");add("山西");add("辽宁");add("台湾");add("黑龙江");add("内蒙古");add("澳门");add("贵州");add("甘肃");add("青海");
        add("新疆");add("西藏");add("吉林");add("宁夏");
        //市级字典
        add("北京");add("东莞");add("广州");add("中山");add("深圳");add("惠州");add("江门");add("珠海");add("汕头");add("梅州");
        add("佛山");add("湛江");add("河源");add("肇庆");add("潮州");add("清远");add("韶关");add("揭阳");add("阳江");add("云浮");
        add("茂名");add("济南");add("青岛");add("临沂");add("济宁");add("菏泽");add("烟台");add("泰安");add("淄博");add("潍坊");
        add("日照");add("威海");add("滨州");add("东营");add("聊城");add("德州");add("莱芜");add("枣庄");add("苏州");add("徐州");
        add("盐城");add("无锡");add("南京");add("南通");add("连云港");add("常州");add("扬州");add("镇江");add("淮安");add("泰州");
        add("宿迁");add("上海");add("郑州");add("南阳");add("新乡");add("安阳");add("洛阳");add("信阳");add("平顶山");add("周口");
        add("商丘");add("开封");add("焦作");add("驻马店");add("濮阳");add("三门峡");add("漯河");add("许昌");add("鹤壁");add("济源");
        add("石家庄");add("唐山");add("承德");add("邯郸");add("邢台");add("沧州");add("秦皇岛");add("张家口");add("衡水");add("廊坊");
        add("保定");add("温州");add("宁波");add("杭州");add("台州");add("嘉兴");add("金华");add("湖州");add("绍兴");add("舟山");
        add("丽水");add("衢州");add("西安");add("咸阳");add("宝鸡");add("汉中");add("渭南");add("安康");add("榆林");add("商洛");
        add("延安");add("铜川");add("长沙");add("邵阳");add("常德");add("衡阳");add("株洲");add("湘潭");add("永州");add("岳阳");
        add("怀化");add("郴州");add("娄底");add("益阳");add("张家界");add("湘西");add("重庆");add("漳州");add("泉州");add("厦门");
        add("福州");add("莆田");add("宁德");add("三明");add("南平");add("龙岩");add("天津");add("昆明");add("曲靖");add("昭通");
        add("保山");add("玉溪");add("丽江");add("西双版纳");add("成都");add("绵阳");add("广元");add("达州");add("南充");add("德阳");
        add("广安");;add("巴中");add("遂宁");add("内江");add("凉山州");add("攀枝花");add("乐山");add("自贡");add("泸州");add("贵港");
        add("玉林");add("北海");add("南宁");add("眉山");add("资阳");add("宜宾");add("雅安");add("柳州");add("桂林");add("梧州");
        add("钦州");add("来宾");add("河池");add("百色");add("贺州");add("崇左");add("防城港");add("芜湖");add("合肥");add("六安");
        add("宿州");add("阜阳");add("安庆");add("马鞍山");add("蚌埠");add("宣城");add("黄山");add("铜陵");add("亳州");add("池州");
        add("三亚");add("海口");add("琼海");add("文昌");add("东方");add("昌江");add("陵水");add("南昌");add("赣州");add("上饶");
        add("吉安");add("九江");add("新余");add("抚州");add("武汉");add("宜昌");add("荆州");add("孝感");add("黄冈");add("十堰");
        add("黄石");add("荆门");add("天门");add("太原");add("大同");add("焦作");add("运城");add("长治");add("晋城");add("大连");
        add("沈阳");add("丹东");add("辽阳");add("葫芦岛");add("鞍山");add("锦州");add("抚顺");add("台北");add("高雄");add("台中");
        add("新竹");add("齐齐哈尔");add("哈尔滨");add("大庆");  add("双鸭山");add("牡丹江");add("黑河");add("鸡西");add("佳木斯");
        add("赤峰");add("包头");add("通辽");add("呼和浩特");add("梅州");add("鄂尔多斯");add("呼伦贝尔");add("贵阳");add("六盘水");
        add("安顺");add("清远");add("遵义");add("兰州");add("天水");add("庆阳");add("酒泉");add("张掖");add("白银");add("武威");
        add("拉萨");add("乌鲁木齐");add("石河子");add("克拉玛依");add("和田");add("吐鲁番");add("吉林");add("长春");add("白山");
        add("白城");add("松原");add("银川");add("吴忠");add("中卫");add("石嘴山");add("香港");add("澳门");
        //行业字典
        add("金融");  add("互联网");add("农业"); add("林业");add("娱乐业"); add("采矿业");add("制造业");  add("服装业");add("食品制造业");
        add("纺织业");add("家具制造业");  add("医疗行业");add("汽车行业");  add("铁路行业");add("物流行业");  add("建筑业");add("电力行业");
        add("批发业");add("零售业");  add("航空业");add("餐饮业");
        //职位字典
        add("程序员");add("产品经理");add("产品总监");add("UI设计师");add("交互设计师");add("新媒体运营");add("产品运营");add("网络推广");
        add("市场营销");add("市场推广");add("采购主管");add("品牌推广");add("策划经理");add("行政主管");add("CEO");add("物业经理");
        add("药剂师");add("营养师");add("物流专员");add("服务员");add("发型师");add("生产总监");add("生产员");
        //程序员字典
        add("项目经理");add("软件工程师");add("嵌入式软件工程师");add("java开发工程师");add("软件开发工程师");add("android");
        add("嵌入式软件开发工程师");add("软件测试工程师");add("高级软件工程师");add("测试工程师");add("产品经理");
        add(".net");add("java");add("高级项目经理");add("技术支持工程师");
        add("ios");add("C++");add("高级java开发工程师");add("实施工程师");add("软件实施工程师");add("前端开发工程师");add("项目助理");
        add("web前端开发工程师");add("java软件开发工程师");add("软件项目经理");add("施工员");add("软件开发");add("硬件工程师");add("嵌入式开发工程师");
        add("技术总监");add("售前工程师");add("嵌入式软件开发");
       add("高级软件开发工程师");add("c#");
        add("前端");add("后端");add("数据库");add("测试");
    }};
    /*
        匹配句子中词典中存在的所有词汇
     */
    public static List<String> getAllWordsMatched(String sentence){
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

