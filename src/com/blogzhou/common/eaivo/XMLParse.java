package com.blogzhou.common.eaivo;

import java.io.IOException;
import java.io.StringReader;

import org.dom4j.io.SAXReader;

class XMLParse {

	   /**
     * XML数组
     */
    private char[] stream = null;

    /**
     * 位置
     */
    private int pos = 0;

    /**
     * 是否在元素标签的括号里面
     */
    private boolean isInner = false;

    /**
     * 是否为属性
     */
    private boolean isAttribute = false;

    /**
     * 是否为结束标签
     */
    private boolean isEndTag = false;

    /**
     * 是否为文本节点
     */
    private boolean isTextNode = false;

    /**
     * 当前名称
     */
    private String currentName = null;

    /**
     * 当前值
     */
    private String currentValue = null;

    /**
     * 临时变量
     */
    private char tc = 0;

    /**
     * 构造函数
     * @param xml XMLString
     */
    public XMLParse(String xml) {
        stream = new char[xml.length() + 1]; // 最后一个字节是0
        xml.getChars(0, xml.length(), stream, 0);
        pos = xml.indexOf("<rootVo");
    }

    /**
     * 解析下一个节点
     * @return 是否存在下一个节点，false表示已经到了未端，没有数据可以解析了
     * @throws IOException
     */
    public boolean next() {
        isAttribute = false;
        isEndTag = false;
        isTextNode = false;
        if (pos >= stream.length) {
            return false;
        }
        char c = stream[pos++];
        for (; c != 0; c = stream[pos++]) {
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r') { // 空格、tab、回车、换行                
            } else if (c == '<') {
                if (stream[pos] == 0) {
                    c = 0;
                    break;
                } else if (stream[pos] == '!') { // 注释段
                    skipNotation();
                } else { // Tag起始标记
                    currentValue = null;
                    if (stream[pos++] == '/') { // 结束Tag
                        isEndTag = true;
                        currentName = readEndNodeName();
                        break;
                    } else { // 起始Tag
                        currentName = readStartNodeName();
                        break;
                    }
                }
            } else if (c == '/') { // 无内容Tag
            } else if (c == '>') {
                isInner = false;
            } else if (isInner) {// 属性
                isAttribute = true;
                currentName = readAttrName();
                if (pos + 2 < stream.length) {
                    currentValue = readAttribute();
                } else {
                    c = 0;
                }
                break;
            } else { // 文本节点
                isTextNode = true;
                currentValue = readTextNode();
                break;

            }
        }

        if (c == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 跳过一段注释
     */
    private void skipNotation() {
        for (tc = stream[pos++]; tc != 0; tc = stream[pos++]) {
            if (tc == '>' && stream[pos - 2] == '-') {
                break;
            }
        }
    }

    /**
     * 读取文本节点
     * @return 文本节点内容
     */
    private String readTextNode() {
        int start = pos - 1;
        while (pos < stream.length && stream[pos++] != '<');
        pos--;
        return new String(stream, start, pos - start);
    }

    /**
     * 读取1个属性名
     * @return 属性名
     */
    private String readAttrName() {
        int start = pos - 1;
        for (tc = stream[pos++]; tc != 0; tc = stream[pos++]) {
            if (tc == ' ' || tc == '=') {
                break;
            }
        }
        return new String(stream, start, pos - start - 1);
    }

    /**
     * 读取1个起始节点名
     * @return 节点名
     */
    private String readStartNodeName() {
        int start = pos - 1;
        for (tc = stream[pos++]; tc != 0; tc = stream[pos++]) {
            if (tc == ' ') {
                isInner = true;
                break;
            } else if (tc == '>' || tc == '/') {
                break;
            }
        }
        return new String(stream, start, pos - start - 1);
    }

    /**
     * 读取1个终止节点名
     * @return 节点名
     */
    private String readEndNodeName() {
        int start = pos;
        for (tc = stream[pos++]; tc != 0; tc = stream[pos++]) {
            if (tc == '>') {
                isInner = false;
                break;
            }
        }
        return new String(stream, start, pos - start - 1);
    }

    /**
     * 读取属性值
     * @return 属性值
     */
    private String readAttribute() {
        for (tc = stream[pos++]; tc != 0 && tc != '"'; tc = stream[pos++])
            ;
        int start = pos;
        for (tc = stream[pos++]; tc != 0 && tc != '"'; tc = stream[pos++])
            ;
        return new String(stream, start, pos - start - 1);
    }

    /**
     * @return Returns the currentValue.
     */
    String getCurrentValue() {
        return currentValue;
    }

    /**
     * @return Returns the currentWord.
     */
    String getCurrentNode() {
        return currentName;
    }

    /**
     * @return Returns the isAttribute.
     */
    boolean isAttribute() {
        return isAttribute;
    }

    /**
     * @return Returns the isEndTag.
     */
    boolean isEndTag() {
        return isEndTag;
    }

    /**
     * @return Returns the isTextNode.
     */
    boolean isTextNode() {
        return isTextNode;
    }

    /**
     * 获取XMLString
     * @return XML
     */
    String getXML() {
        return new String(stream);
    }

    /**
     * 测试启动函数
     * @param args 参数
     * @throws Exception 测试失败
     */
    public static void main(String[] args) throws Exception {
        String xmlTest = "<?xml version=\"1.0\" encoding=\"GBK\"?>\n\n<!-- 注释 --><rootVo blhName=\"GSndhsqjBLH\" class=\"gov.gdlt.taxcore.taxevent.zshs.sb.gs54.GSscjygssubmitReqEvent\" sid=\"TestSID\" action=\"TestAction\"><head><!--交易序号--><jyxh>0011000011001010</jyxh><!--发起方标识--><fqf>fqf-Name</fqf><!--交易名称--><jymc>sbHandler</jymc><!--交易名称：申报时为:sbHandler 查询为：queryHandler 作废为：zfHandler--></head><bizInfo><jhbs>sbHandle</jhbs></bizInfo><!--企业申报明细--><vo name=\"gov.gdlt.taxcore.taxevent.zshs.sb.gs54.GSscjyqygssbVO\" setMethod=\"setDzhhqygssbVO\"><!--纳税人内部码--><cell name=\"nsrnbm\" value=\"2632630\"/><!--税费所属期起--><cell name=\"ssqq\" value=\"2005-01-01\"/><!--税费所属期止--><cell name=\"ssqz\" value=\"2005-12-31\"/><!--生产经营所得额--><cell name=\"scjysdje\" value=\"8300\"/><!--不允许扣除的项目--><cell name=\"byxkcdxmje\" value=\"0\"/><!--成本--><cell name=\"cbje\" value=\"500\"/><!--超过规定标准扣除的项目--><cell name=\"cggdbzkcdxmje\" value=\"0\"/><!--从业人员工资支出--><cell name=\"cyrygzzcje\" value=\"0\"/><!--非教育和公益事业捐赠--><cell name=\"fjyhgysyjzje\" value=\"0\"/><!--费用及税金--><cell name=\"fyjsjje\" value=\"1000\"/><!--投资者标准费用扣除额--><cell name=\"fykceje\" value=\"0\"/><!--广告费--><cell name=\"ggfje\" value=\"0\"/><!--工会经费--><cell name=\"ghjfje\" value=\"0\"/><!--国库卷利息收入--><cell name=\"gkjlxsrje\" value=\"0\"/><!--工资总额--><cell name=\"gzzeje\" value=\"0\"/><!--各种赞助支出--><cell name=\"gzzzzcje\" value=\"0\"/><!--计提的各种准备金--><cell name=\"jtdgzzbjje\" value=\"0\"/><!--教育和公益事业损赠--><cell name=\"jyhgysyjzje\" value=\"0\"/><!--利息支出--><cell name=\"lxzcje\" value=\"0\"/><!--弥补亏损--><cell name=\"mbksje\" value=\"0\"/><!--年平均职工人数--><cell name=\"npjzgrssl\" value=\"0\"/><!--纳税调整减少额--><cell name=\"nstzjseje\" value=\"0\"/><!--纳税调整增加额--><cell name=\"nstzzjeje\" value=\"0\"/><!--其它超规定标准扣除项目--><cell name=\"qtaje\" value=\"0\"/><!--其他--><cell name=\"qtje\" value=\"0\"/><!--企业利润总额--><cell name=\"qylrzeje\" value=\"7800\"/><!--少计应税收益--><cell name=\"sjyssyje\" value=\"0\"/><!--收入总额--><cell name=\"srzeje\" value=\"10000\"/><!--税收滞纳金、罚金、罚款--><cell name=\"ssznjfjfkje\" value=\"0\"/><!--提取折旧费--><cell name=\"tqzjfje\" value=\"0\"/><!--投资者的工资--><cell name=\"tzzgzje\" value=\"0\"/><!--违法经营罚款和被没收财物损失--><cell name=\"wfjyfkbmscwssje\" value=\"0\"/><!--未计应税收益--><cell name=\"wjyssyje\" value=\"0\"/><!--无形资产受让、开发支出--><cell name=\"wxzcsrkfzcje\" value=\"0\"/><!--无形资产摊销--><cell name=\"wxzctxje\" value=\"0\"/><!--与收入无关支出--><cell name=\"ysrwgzcje\" value=\"0\"/><!--应税收益项目--><cell name=\"yssyxmje\" value=\"0\"/><!--业务招待费--><cell name=\"ywzdfje\" value=\"0\"/><!--营业外支出--><cell name=\"yywzcje\" value=\"200\"/><!--资本性支出--><cell name=\"zbxzcje\" value=\"0\"/><!--职工福利费--><cell name=\"zgflfje\" value=\"0\"/><!--职工教育经费--><cell name=\"zgjyjfje\" value=\"0\"/><!--灾情事故损失赔偿--><cell name=\"zqsgsspcje\" value=\"0\"/></vo><linkedList name=\"gov.gdlt.taxcore.taxevent.zshs.sb.gs54.GSscjygrgssbVO\"><vo><!--纳税人内部码--><cell name=\"nsrnbm\" value=\"2632630\"/><!--凭证序号--><cell name=\"pzxh\" value=\"0\"/><!--生产经营所得额--><cell name=\"scjysdje\" value=\"8300\"/><!--分配比例--><cell name=\"tzbl\" value=\"0\"/><!--分配所占比例--><cell name=\"fpszbl\" value=\"100\"/><!--期初未缴所得税额--><cell name=\"qcwjsdseje\" value=\"0\"/><!--应缴入库所得税额--><cell name=\"yjrksdseje\" value=\"1093.06\"/><!--境外所得应补缴税额--><cell name=\"jwsdybjseje\" value=\"0\"/><!--申报明细序号--><cell name=\"sbmxxh\" value=\"0\"/><!--企业纳税人内码--><cell name=\"nsgrnbm\" value=\"1000811543\"/><!--其他生产经营所得--><cell name=\"qtscjysdseje\" value=\"7000\"/><!--其他企业已扣除的投资者标准费用扣除额合计数--><cell name=\"qtqykcfyhjsje\" value=\"3000\"/><!--应征明细序号--><cell name=\"yzmxxh\" value=\"0\"/><!--征收项目代码--><cell name=\"zspmdm\" value=\"0200\"/><!--申报期限--><cell name=\"sbqx\" value=\"2006-03-31\"/><!--税费所属期起--><cell name=\"ssqq\" value=\"2005-01-01\"/><!--税费所属期止--><cell name=\"ssqz\" value=\"2005-12-31\"/><!--征收项目代码--><cell name=\"zsxmdm\" value=\"06\"/><!--重复申报标志--><cell name=\"cfsbbz\" value=\"false\"/><!--计税金额--><cell name=\"jsje\" value=\"8300\"/><!--税率--><cell name=\"sl\" value=\"0.2\"/><!--应纳税额--><cell name=\"yingnseje\" value=\"1093.06\"/><!--减免金额--><cell name=\"jmje\" value=\"0\"/><!--已纳税额--><cell name=\"yinseje\" value=\"1012.25\"/><!--本期应补退税额--><cell name=\"bqybtseje\" value=\"80.81\"/><!--罚款金额--><cell name=\"fkje\" value=\"0\"/><!--罚款文书号--><cell name=\"fkwsh\" value=\"0\"/><!--抵缴金额对应的本金余额--><cell name=\"bjye\" value=\"0\"/><!--抵缴金额对应的利息余额--><cell name=\"lxye\" value=\"0\"/><!--减免文书号--><cell name=\"jmwsh\" value=\"0\"/><!--减免明细号--><cell name=\"jmmxh\" value=\"0\"/><!--抵缴文书号--><cell name=\"djwsh\" value=\"0\"/><!--抵缴明细号--><cell name=\"djmxh\" value=\"0\"/><!--抵缴金额--><cell name=\"djje\" value=\"0\"/><!--速算扣除数--><cell name=\"sskcs\" value=\"1250\"/><!--主附关联号--><cell name=\"zfglh\" value=\"0\"/><!--缴款期限--><cell name=\"jkqx\" value=\"2006-03-31\"/><!--滞纳金额--><cell name=\"znje\" value=\"37.71\"/><!--滞纳天数--><cell name=\"znts\" value=\"69\"/><!--处理状态描述--><cell name=\"clztms\" value=\"计税成功\"/><!--处理状态--><cell name=\"clzt\" value=\"a2\"/><!--起征金额数量--><cell name=\"qzjesl\" value=\"0\"/><!--流水号(稽查接口)--><cell name=\"lsh\" value=\"0\"/><!--逾期天数--><cell name=\"yqts\" value=\"0\"/><!--关联文书明细号--><cell name=\"glwsmxh\" value=\"0\"/><cell name=\"wxzbz\" value=\"false\"/><!--已开票标志--><cell name=\"ykp\" value=\"false\"/><!--征收标志,国税:0,null;地税:1--><cell name=\"zsbz\" value=\"1\"/><!--批准减免上限--><cell name=\"pzjmsx\" value=\"0\"/><cell name=\"xh\" value=\"0\"/><cell name=\"useJM\" value=\"0\"/><cell name=\"useDJ\" value=\"0\"/><cell name=\"hh\" value=\"0\"/><cell name=\"hdzsje\" value=\"0\"/><cell name=\"yyze\" value=\"0\"/><cell name=\"kcje\" value=\"0\"/><cell name=\"tkfpje\" value=\"0\"/><cell name=\"gslr\" value=\"false\"/><!--个人对其它企业的投资明细--><arrayList name=\"gov.gdlt.taxcore.taxevent.zshs.sb.gs54.GSscjygrqtztxxVO\"><vo><!--凭证序号--><cell name=\"pzxh\" value=\"0\"/><!--申报明细序号--><cell name=\"sbmxxh\" value=\"0\"/><!--企业纳税人内码--><cell name=\"nsgrnbm\" value=\"0\"/><!--序号--><cell name=\"xh\" value=\"0\"/><!--投资企业名称--><cell name=\"tzqymc\" value=\"白沙集团\"/><!--投资企业纳税编码--><cell name=\"tzqynsrbm\" value=\"123456\"/><!--从企业取得的生产经营所得--><cell name=\"scjysdeje\" value=\"2000\"/></vo><vo><cell name=\"pzxh\" value=\"0\"/><cell name=\"sbmxxh\" value=\"0\"/><cell name=\"nsgrnbm\" value=\"0\"/><cell name=\"xh\" value=\"0\"/><cell name=\"tzqymc\" value=\"红塔山集团\"/><cell name=\"tzqynsrbm\" value=\"11111\"/><cell name=\"scjysdeje\" value=\"5000\"/></vo></arrayList></vo></linkedList><!--投资者明细--><arrayList name=\"gov.gdlt.taxcore.taxevent.zshs.sb.gs54.GSscjygrgssbVO\" setMethod=\"setSbmxList\"><vo>    <!--企业纳税人内码--><cell name=\"nsgrnbm\" value=\"1000811543\"/><!--征收项目代码--><cell name=\"zsxmdm\" value=\"06\"/>    <!--征收品目代码--><cell name=\"zspmdm\" value=\"0200\"/><!--期初未缴所得税额--><cell name=\"qcwjsdseje\" value=\"0\"/><!--生产经营所得额--><cell name=\"scjysdje\" value=\"8300\"/><!--分配所占比例--><cell name=\"fpszbl\" value=\"100\"/><!--境外所得应补缴税额--><cell name=\"jwsdybjseje\" value=\"0\"/><!--其他生产经营所得--><cell name=\"qtscjysdseje\" value=\"7000\"/><!--其他企业已扣除的投资者标准费用扣除额合计数--><cell name=\"qtqykcfyhjsje\" value=\"3000\"/><!--税费所属期起--><cell name=\"ssqq\" value=\"2005-01-01\"/><!--税费所属期止--><cell name=\"ssqz\" value=\"2005-12-31\"/><!--个人对其它企业的投资明细--><arrayList name=\"gov.gdlt.taxcore.taxevent.zshs.sb.gs54.GSscjygrqtztxxVO\" setMethod=\"setGrtzxxList\"><vo>    <!--投资企业名称--><cell name=\"tzqymc\" value=\"白沙集团\"/><!--投资企业纳税编码--><cell name=\"tzqynsrbm\" value=\"123456\"/><!--从企业取得的生产经营所得--><cell name=\"scjysdeje\" value=\"2000\"/></vo><vo>    <!--投资企业名称--><cell name=\"tzqymc\" value=\"红塔山集团\"/><!--投资企业纳税编码--><cell name=\"tzqynsrbm\" value=\"11111\"/><!--从企业取得的生产经营所得--><cell name=\"scjysdeje\" value=\"5000\"/></vo></arrayList></vo></arrayList><properties>    <!--纳税人内部码--><cell name=\"nsrnbm\" value=\"2632630\"/><!--注册类型代码--><cell name=\"zclxdm\" value=\"175\"/><!--行业代码--><cell name=\"hydm\" value=\"395\"/><!--隶属关系代码--><cell name=\"lsgxdm\" value=\"90\"/><!--管理机关代码--><cell name=\"gljgdm\" value=\"244060601\"/><!--征收机关代码--><cell name=\"zsjgdm\" value=\"244060601\"/><!--纳税人编码--><cell name=\"nsrbm\" value=\"06811254240\"/><!--申报日期--><cell name=\"sbrq\" value=\"2006-06-08\"/><!--申报方式代码--><cell name=\"sbfsdm\" value=\"32\"/><!--申报属性代码--><cell name=\"sbsxdm\" value=\"0\"/></properties><vo name=\"gov.gdlt.taxcore.taxevent.zshs.sb.comm.SBnsrxxVO\" setMethod=\"setNsrxxVO\"><!--纳税人编码--><cell name=\"nsrbm\" value=\"06811254240\"/><!--纳税人内部码--><cell name=\"nsrnbm\" value=\"2632630\"/><!--纳税人名称--><cell name=\"nsrmc\" value=\"共和国纳税人\"/><!--所属期起--><cell name=\"ssqq\" value=\"2005-01-01\"/><!--缴款方式代码--><cell name=\"jkfsdm\" value=\"14\"/><!--所属期止--><cell name=\"ssqz\" value=\"2005-12-31\"/><!--注册地址--><cell name=\"zcdz\" value=\"顺德区容桂街道容桂大道南23号\"/><!--征收机关代码--><cell name=\"zsjgdm\" value=\"244060601\"/><!--申报方式代码--><cell name=\"sbfsdm\" value=\"32\"/><!--注册类型代码--><cell name=\"zclxdm\" value=\"175\"/>\n<!--管理机关代码-->\n<cell name=\"gljgdm\" value=\"244060601\"/><!--隶属关系代码--><cell name=\"lsgxdm\" value=\"90\"/><!--行业代码--><cell name=\"hydm\" value=\"395\"/>\n<cell name=\"empty\" value=\"\"/></vo>\n</rootVo>\n";
        int testRound = 1000;
        long timer = 0;
        XMLParse parse = new XMLParse(xmlTest);
        //System.out.println((int) parse.stream[parse.stream.length-1]);
        while (parse.next()) {
           // System.out.println(parse.currentName + ": " + parse.currentValue);
        }
        parse = new XMLParse(xmlTest);
        //Root root = Root.parse(parse);
        //System.out.println(root);   

        /* 本方法解析 */
        timer = System.currentTimeMillis();
        for (int i = 0; i < testRound; i++) {
            parse = new XMLParse(xmlTest);
            while (parse.next())
                ;
        }
        timer = System.currentTimeMillis() - timer;
        System.out.println("\nThis total: " + timer + "\t\t\tavg: " + ((timer + 0.0) / testRound));

        //SAXBuilder builder = new SAXBuilder();
        new SAXReader().read(new StringReader(xmlTest));
        /* SAX解析 */
        timer = System.currentTimeMillis();
        for (int i = 0; i < testRound; i++) {
            new SAXReader().read(new StringReader(xmlTest));
        }
        timer = System.currentTimeMillis() - timer;
        System.out.println("\nSAXB total: " + timer + "\t\t\tavg: " + ((timer + 0.0) / testRound));

    }
}
