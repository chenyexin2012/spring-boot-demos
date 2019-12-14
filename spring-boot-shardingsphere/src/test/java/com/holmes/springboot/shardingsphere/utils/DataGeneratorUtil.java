package com.holmes.springboot.shardingsphere.utils;

import java.util.Random;

/**
 *
 */
public class DataGeneratorUtil {

    /**
     * 随机生成姓名
     *
     * @return
     */
    public static String generateName() {

        Random random = new Random();
        // 随机生成姓氏
        StringBuilder name = new StringBuilder();
        name.append(SURNAME[random.nextInt(SURNAME.length)]);
        int sex = random.nextInt(2);
        if (sex == 0) {
            int index = random.nextInt(BOY_NAME.length() - 1);
            name.append(BOY_NAME.substring(index, index + 1));
            if (random.nextInt(10) < 8) {
                index = random.nextInt(BOY_NAME.length() - 1);
                name.append(BOY_NAME.substring(index, index + 1));
            }
        } else {
            int index = random.nextInt(GIRL_NAME.length() - 1);
            name.append(GIRL_NAME.substring(index, index + 1));
            if (random.nextInt(10) < 8) {
                index = random.nextInt(GIRL_NAME.length() - 1);
                name.append(GIRL_NAME.substring(index, index + 1));
            }
        }
        return name.toString();
    }

    /**
     * 随机生成地址 省市
     *
     * @return
     */
    public static final String generateAddress() {
        Random random = new Random();
        StringBuilder address = new StringBuilder();
        String[] cities = PLACE[random.nextInt(PLACE.length)];
        address.append(cities[0]).append(cities[1 + random.nextInt(cities.length - 1)]);
        return address.toString();
    }

    /**
     * 随机生成手机号码
     *
     * @return
     */
    public static final String generatePhoneNumber() {

        Random random = new Random();
        StringBuilder phone = new StringBuilder();
        phone.append(String.valueOf(PHONE_PREFIX[random.nextInt(PHONE_PREFIX.length)]));
        phone.append(String.valueOf(1000 + random.nextInt(9000)));
        phone.append(String.valueOf(1000 + random.nextInt(9000)));
        return phone.toString();
    }

    /**
     * 随机生成email地址
     *
     * @return
     */
    public static final String generateEmailAddress() {

        Random random = new Random();
        StringBuilder email = new StringBuilder();
        int length = random.nextInt(8) + 8;
        for(int i = 0; i < length; i++) {
            email.append((char)(random.nextInt(26) + 'a'));
        }
        return email.append(EMAIL_SUFFIX[random.nextInt(EMAIL_SUFFIX.length)]).toString();
    }

    //百家姓
    private static final String[] SURNAME = {"赵", "钱", "孙", "李", "周", "吴", "郑", "王", "冯", "陈", "褚", "卫", "蒋",
            "沈", "韩", "杨", "朱", "秦", "尤", "许", "何", "吕", "施", "张", "孔", "曹", "严", "华", "金", "魏", "陶", "姜",
            "戚", "谢", "邹", "喻", "柏", "水", "窦", "章", "云", "苏", "潘", "葛", "奚", "范", "彭", "郎", "鲁", "韦", "昌",
            "马", "苗", "凤", "花", "方", "俞", "任", "袁", "柳", "酆", "鲍", "史", "唐", "费", "廉", "岑", "薛", "雷", "贺",
            "倪", "汤", "滕", "殷", "罗", "毕", "郝", "邬", "安", "常", "乐", "于", "时", "傅", "皮", "卞", "齐", "康", "伍",
            "余", "元", "卜", "顾", "孟", "平", "黄", "和", "穆", "萧", "尹", "姚", "邵", "湛", "汪", "祁", "毛", "禹", "狄",
            "米", "贝", "明", "臧", "计", "伏", "成", "戴", "谈", "宋", "茅", "庞", "熊", "纪", "舒", "屈", "项", "祝", "董",
            "梁", "杜", "阮", "蓝", "闵", "席", "季", "麻", "强", "贾", "路", "娄", "危", "江", "童", "颜", "郭", "梅", "盛",
            "林", "刁", "钟", "徐", "邱", "骆", "高", "夏", "蔡", "田", "樊", "胡", "凌", "霍", "虞", "万", "支", "柯", "昝",
            "管", "卢", "莫", "经", "房", "裘", "缪", "干", "解", "应", "宗", "丁", "宣", "贲", "邓", "郁", "单", "杭", "洪",
            "包", "诸", "左", "石", "崔", "吉", "钮", "龚", "程", "嵇", "邢", "滑", "裴", "陆", "荣", "翁", "荀", "羊", "于",
            "惠", "甄", "曲", "家", "封", "芮", "羿", "储", "靳", "汲", "邴", "糜", "松", "井", "段", "富", "巫", "乌", "焦",
            "巴", "弓", "牧", "隗", "山", "谷", "车", "侯", "宓", "蓬", "全", "郗", "班", "仰", "秋", "仲", "伊", "宫", "宁",
            "仇", "栾", "暴", "甘", "钭", "厉", "戎", "祖", "武", "符", "刘", "景", "詹", "束", "龙", "叶", "幸", "司", "韶",
            "郜", "黎", "蓟", "溥", "印", "宿", "白", "怀", "蒲", "邰", "从", "鄂", "索", "咸", "籍", "赖", "卓", "蔺", "屠",
            "蒙", "池", "乔", "阴", "郁", "胥", "能", "苍", "双", "闻", "莘", "党", "翟", "谭", "贡", "劳", "逄", "姬", "申",
            "扶", "堵", "冉", "宰", "郦", "雍", "却", "璩", "桑", "桂", "濮", "牛", "寿", "通", "边", "扈", "燕", "冀", "浦",
            "尚", "农", "温", "别", "庄", "晏", "柴", "瞿", "阎", "充", "慕", "连", "茹", "习", "宦", "艾", "鱼", "容", "向",
            "古", "易", "慎", "戈", "廖", "庾", "终", "暨", "居", "衡", "步", "都", "耿", "满", "弘", "匡", "国", "文", "寇",
            "广", "禄", "阙", "东", "欧", "殳", "沃", "利", "蔚", "越", "夔", "隆", "师", "巩", "厍", "聂", "晁", "勾", "敖",
            "融", "冷", "訾", "辛", "阚", "那", "简", "饶", "空", "曾", "毋", "沙", "乜", "养", "鞠", "须", "丰", "巢", "关",
            "蒯", "相", "查", "后", "荆", "红", "游", "郏", "竺", "权", "逯", "盖", "益", "桓", "公", "仉", "督", "岳", "帅",
            "缑", "亢", "况", "郈", "有", "琴", "归", "海", "晋", "楚", "闫", "法", "汝", "鄢", "涂", "钦", "商", "牟", "佘",
            "佴", "伯", "赏", "墨", "哈", "谯", "篁", "年", "爱", "阳", "佟", "言", "福", "南", "火", "铁", "迟", "漆", "官",
            "冼", "真", "展", "繁", "檀", "祭", "密", "敬", "揭", "舜", "楼", "疏", "冒", "浑", "挚", "胶", "随", "高", "皋",
            "原", "种", "练", "弥", "仓", "眭", "蹇", "覃", "阿", "门", "恽", "来", "綦", "召", "仪", "风", "介", "巨", "木",
            "京", "狐", "郇", "虎", "枚", "抗", "达", "杞", "苌", "折", "麦", "庆", "过", "竹", "端", "鲜", "皇", "亓", "老",
            "是", "秘", "畅", "邝", "还", "宾", "闾", "辜", "纵", "侴", "万俟", "司马", "上官", "欧阳", "夏侯", "诸葛", "闻人",
            "东方", "赫连", "皇甫", "羊舌", "尉迟", "公羊", "澹台", "公冶", "宗正", "濮阳", "淳于", "单于", "太叔", "申屠",
            "公孙", "仲孙", "轩辕", "令狐", "钟离", "宇文", "长孙", "慕容", "鲜于", "闾丘", "司徒", "司空", "兀官", "司寇",
            "南门", "呼延", "子车", "颛孙", "端木", "巫马", "公西", "漆雕", "车正", "壤驷", "公良", "拓跋", "夹谷", "宰父",
            "谷梁", "段干", "百里", "东郭", "微生", "梁丘", "左丘", "东门", "西门", "南宫", "第五", "公仪", "公乘", "太史",
            "仲长", "叔孙", "屈突", "尔朱", "东乡", "相里", "胡母", "司城", "张廖", "雍门", "毋丘", "贺兰", "綦毋", "屋庐",
            "独孤", "南郭", "北宫", "王孙"};

    private static final String GIRL_NAME = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香"
            + "桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭"
            + "冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽嘉琼勤珍贞莉月莺媛艳瑞凡佳";

    private static final String BOY_NAME = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新"
            + "光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣"
            + "朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘杰涛昌成康星利清飞彬富顺信子";

    private static final String PLACE[][] = {
            {"北京", "市辖区", "市辖县"},
            {"天津", "市辖区", "市辖县"},
            {"安徽", "安庆市", "蚌埠市", "亳州市", "巢湖市", "池州市", "滁州市", "阜阳市", "合肥市", "淮北市", "淮南市",
                    "黄山市", "六安市", "马鞍山市", "宿州市", "铜陵市", "芜湖市", "宣城市"},
            {"澳门", "澳门"},
            {"香港", "香港"},
            {"福建", "福州市", "龙岩市", "南平市", "宁德市", "莆田市", "泉州市", "厦门市", "漳州市"},
            {"甘肃", "白银市", "定西市", "甘南藏族自治州", "嘉峪关市", "金昌市", "酒泉市", "兰州市", "临夏回族自治州", "陇南市",
                    "平凉市", "庆阳市", "天水市", "武威市", "张掖市"},
            {"广东", "潮州市", "东莞市", "佛山市", "广州市", "河源市", "惠州市", "江门市", "揭阳市", "茂名市", "梅州市",
                    "清远市", "汕头市", "汕尾市", "韶关市", "深圳市", "阳江市", "云浮市", "湛江市", "肇庆市", "中山市", "珠海市"},
            {"广西", "百色市", "北海市", "崇左市", "防城港市", "贵港市", "桂林市", "河池市", "贺州市", "来宾市", "柳州市",
                    "南宁市", "钦州市", "梧州市", "玉林市"},
            {"贵州", "安顺市", "毕节地区", "贵阳市", "六盘水市", "黔东南苗族侗族自治州", "黔南布依族苗族自治州",
                    "黔西南布依族苗族自治州", "铜仁地区", "遵义市"},
            {"海南", "海口市", "三亚市", "省直辖县级行政区划"},
            {"河北", "保定市", "沧州市", "承德市", "邯郸市", "衡水市", "廊坊市", "秦皇岛市", "石家庄市", "唐山市", "邢台市",
                    "张家口市"},
            {"河南", "安阳市", "鹤壁市", "焦作市", "开封市", "洛阳市", "漯河市", "南阳市", "平顶山市", "濮阳市", "三门峡市",
                    "商丘市", "新乡市", "信阳市", "许昌市", "郑州市", "周口市", "驻马店市"},
            {"黑龙江", "大庆市", "大兴安岭地区", "哈尔滨市", "鹤岗市", "黑河市", "鸡西市", "佳木斯市", "牡丹江市", "七台河市",
                    "齐齐哈尔市", "双鸭山市", "绥化市", "伊春市"},
            {"湖北", "鄂州市", "恩施土家族苗族自治州", "黄冈市", "黄石市", "荆门市", "荆州市", "十堰市", "随州市", "武汉市",
                    "咸宁市", "襄樊市", "孝感市", "宜昌市"},
            {"湖南", "长沙市", "常德市", "郴州市", "衡阳市", "怀化市", "娄底市", "邵阳市", "湘潭市", "湘西土家族苗族自治州",
                    "益阳市", "永州市", "岳阳市", "张家界市", "株洲市"},
            {"吉林", "白城市", "白山市", "长春市", "吉林市", "辽源市", "四平市", "松原市", "通化市", "延边朝鲜族自治州"},
            {"江苏", "常州市", "淮安市", "连云港市", "南京市", "南通市", "苏州市", "宿迁市", "泰州市", "无锡市", "徐州市",
                    "盐城市", "扬州市", "镇江市"},
            {"江西", "抚州市", "赣州市", "吉安市", "景德镇市", "九江市", "南昌市", "萍乡市", "上饶市", "新余市", "宜春市",
                    "鹰潭市"},
            {"辽宁", "鞍山市", "本溪市", "朝阳市", "大连市", "丹东市", "抚顺市", "阜新市", "葫芦岛市", "锦州市", "辽阳市",
                    "盘锦市", "沈阳市", "铁岭市", "营口市"},
            {"内蒙古", "阿拉善盟", "巴彦淖尔市", "包头市", "赤峰市", "鄂尔多斯市", "呼和浩特市", "呼伦贝尔市", "通辽市", "乌海市",
                    "乌兰察布市", "锡林郭勒盟", "兴安盟"},
            {"宁夏", "固原市", "石嘴山市", "吴忠市", "银川市", "中卫市"},
            {"青海", "果洛藏族自治州", "海北藏族自治州", "海东地区", "海南藏族自治州", "海西蒙古族藏族自治州", "黄南藏族自治州",
                    "西宁市", "玉树藏族自治州"},
            {"山东", "滨州市", "德州市", "东营市", "菏泽市", "济南市", "济宁市", "莱芜市", "聊城市", "临沂市", "青岛市",
                    "日照市", "泰安市", "威海市", "潍坊市", "烟台市", "枣庄市", "淄博市"},
            {"山西", "长治市", "大同市", "晋城市", "晋中市", "临汾市", "吕梁市", "朔州市", "太原市", "忻州市", "阳泉市",
                    "运城市"},
            {"陕西", "安康市", "宝鸡市", "汉中市", "商洛市", "铜川市", "渭南市", "西安市", "咸阳市", "延安市", "榆林市"},
            {"四川", "阿坝藏族羌族自治州", "巴中市", "成都市", "达州市", "德阳市", "甘孜藏族自治州", "广安市", "广元市",
                    "乐山市", "凉山彝族自治州", "泸州市", "眉山市", "绵阳市", "内江市", "南充市", "攀枝花市", "遂宁市",
                    "雅安市", "宜宾市", "资阳市", "自贡市"},
            {"西藏", "阿里地区", "昌都地区", "拉萨市", "林芝地区", "那曲地区", "日喀则地区", "山南地区"},
            {"新疆", "阿克苏地区", "阿勒泰地区", "巴音郭楞蒙古自治州", "博尔塔拉蒙古自治州", "昌吉回族自治州", "哈密地区",
                    "和田地区", "喀什地区", "克拉玛依市", "克孜勒苏柯尔克孜自治州", "塔城地区", "吐鲁番地区", "乌鲁木齐市",
                    "伊犁哈萨克自治州", "自治区直辖县级行政区划"},
            {"云南", "保山市", "楚雄彝族自治州", "大理白族自治州", "德宏傣族景颇族自治州", "迪庆藏族自治州", "红河哈尼族彝族自治州",
                    "昆明市", "丽江市", "临沧市", "怒江僳僳族自治州", "普洱市", "曲靖市", "文山壮族苗族自治州",
                    "西双版纳傣族自治州", "玉溪市", "昭通市"},
            {"浙江", "杭州市", "湖州市", "嘉兴市", "金华市", "丽水市", "宁波市", "衢州市", "绍兴市", "台州市", "温州市",
                    "舟山市"},
            {"重庆", "市辖区", "市辖县", "县级市"},
            {"台湾", "台北市", "高雄市", "基隆市", "台中市", "台南市", "新竹市", "嘉义市"}
    };

    private static final int[] PHONE_PREFIX = new int[]{134, 135, 136, 137, 138, 139, 150, 151, 152, 157, 158, 159, 130,
            131, 132, 155, 156, 133, 153, 182};

    private static final String[] EMAIL_SUFFIX = ("@gmail.com,@yahoo.com,@msn.com,@hotmail.com,@aol.com,@ask.com," +
            "@live.com,@qq.com,@0355.net,@123.com,@123.net,@163.com,@163.net,@263.net,@3721.net,@yeah.net," +
            "@googlemail.com,@126.com,@sina.com,@sohu.com,@yahoo.com.cn").split(",");
}