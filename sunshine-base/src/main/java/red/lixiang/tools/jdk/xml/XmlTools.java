package red.lixiang.tools.jdk.xml;

import com.alibaba.fastjson.JSONObject;

import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.StringTools;
import red.lixiang.tools.jdk.reflect.ReflectTools;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class XmlTools {

    /**
     * 每解析到一个注解,就行成一个配置类和其对应
     */
    static class XmlFieldConf {
        public String xmlTag;
        public String javaField;
        public String rowWrapper;

        public XmlFieldConf(String xmlTag, String javaField, String rowWrapper) {
            this.xmlTag = xmlTag;
            this.javaField = javaField;
            this.rowWrapper = rowWrapper;
        }
    }


    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) {
        try {
            Map<String, String> data = new HashMap<String, String>();
            DocumentBuilder documentBuilder = XmlBase.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;

                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static JSONObject xmlToJSONObject(String strXML) {
        try {
            JSONObject data = new JSONObject();
            DocumentBuilder documentBuilder = XmlBase.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getChildNodes();
            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
                Node node = nodeList.item(idx);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                    data.put(element.getNodeName(), element.getTextContent());
                }
            }
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return data;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static Map<String, XmlFieldConf> dealWithXmlClass(Class<?> clazz) {
        Field[] fields = ReflectTools.getAllFields(clazz);
        Map<String, XmlFieldConf> confMap = new HashMap<>(fields.length);
        //先通过反射,获取xml标签和属性名的对应关系
        for (Field field : fields) {
            XmlField xmlField = field.getAnnotation(XmlField.class);
            if (xmlField == null) {
                continue;
            }
            String name = field.getName();
            XmlFieldConf conf = new XmlFieldConf(xmlField.xmlEle(), name, xmlField.row());
            confMap.put(xmlField.xmlEle(), conf);
        }
        return confMap;
    }

    public static <T> T xmlToBean(String strXML, Class<T> clazz) {
        try {
            // 先读取xml
            DocumentBuilder documentBuilder = XmlBase.newDocumentBuilder();
            InputStream stream = new ByteArrayInputStream(strXML.getBytes(StandardCharsets.UTF_8));
            org.w3c.dom.Document doc = documentBuilder.parse(stream);
            doc.getDocumentElement().normalize();
            // 开始处理document
            T instance = clazz.getDeclaredConstructor().newInstance();
            //所有的node,
            Element rootNode = doc.getDocumentElement();
            nodeToBean(rootNode, instance, clazz);
            try {
                stream.close();
            } catch (Exception ex) {
                // do nothing
            }
            return instance;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static <T> void nodeToBean(Node rootNode, T t, Class<?> clazz) {
        try {
            Map<String, XmlFieldConf> confMap = dealWithXmlClass(clazz);
            NodeList nodeList = rootNode.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                String xmlTagName = node.getNodeName();
                if(xmlTagName.equals("#text")){
                    continue;
                }
                XmlFieldConf conf = confMap.get(xmlTagName);
                if (conf == null) {
                    //对应着xml里有这个字段,但是实体类里面没有配置
                    System.out.println("检测到xml有,实体类没有的字段:" + xmlTagName);
                    continue;
                }
                Field field = clazz.getDeclaredField(conf.javaField);

                field.setAccessible(true);
                Type fieldType = field.getType();
                if (isTextNode(node)) {
                    String textContent = node.getTextContent();
                    field.set(t, textContent);
                } else {
                    // 是嵌套类型的node , 需要进一步的解析
                    // 先看有没有row
                    String rowWrapper = conf.rowWrapper;
                    if (StringTools.isBlank(rowWrapper)) {
                        //没有row,就是单独的,可以直接递归解析
                        nodeToBean(node, t, (Class<?>) fieldType);
                    }
                    //可能是list,或者是单个的
                    List<Node> nodesByTagName = getNodesByTagName(node, rowWrapper);
                    if (nodesByTagName.size() == 1 && fieldType!=List.class) {
                        //是单个的
                        Class<?> fieldClass = (Class<?>) fieldType;
                        Object o = fieldClass.getDeclaredConstructor().newInstance();
                        field.set(t, o);
                        nodeToBean(nodesByTagName.get(0), o, fieldClass);
                    } else if (fieldType == List.class) {
                        //是个list
                        List list = new ArrayList();
                        ParameterizedType parameterizedType = (ParameterizedType)field.getGenericType();
                        Class<?> rawClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                        for (Node listNode : nodesByTagName) {
                            Object listObj = rawClass.getDeclaredConstructor().newInstance();
                            list.add(listObj);
                            nodeToBean(listNode, listObj, rawClass);
                        }
                        field.set(t, list);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 得到节点下Tag为name的节点集合
     *
     * @param node
     * @param name
     * @return 节点集合
     */
    public static List<Node> getNodesByTagName(Node node, String name) {
        Element elem = (Element) node;
        NodeList nodelist = elem.getElementsByTagName(name);
        List<Node> result = new ArrayList<Node>();
        for (int i = 0; i < nodelist.getLength(); i++) {
            result.add(nodelist.item(i));
        }
        return result;
    }

    /**
     * 判断节点是否为文本节点 <a>string</a> 就是文本节点
     *
     * @param node
     * @return
     */
    public static Boolean isTextNode(Node node) {
        NodeList children = node.getChildNodes();
        if (children.getLength() == 1) {
            Node child = children.item(0);
            return child.getNodeType() == Node.TEXT_NODE;
        }
        return false;
    }


    /**
     * 节点非文本节点的集合
     *
     * @return
     */
    public static List<Node> getChildNodes(Node node) {
        NodeList nodelist = node.getChildNodes();
        List<Node> result = new ArrayList<Node>();
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node child = nodelist.item(i);
            if (child instanceof Text) {
                continue;
            }
            result.add(child);
        }
        return result;
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) {
        try {
            Document document = XmlBase.newDocument();
            Element root = document.createElement("xml");
            document.appendChild(root);
            for (String key : data.keySet()) {
                String value = data.get(key);
                if (value == null) {
                    value = "";
                }
                value = value.trim();
                Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String output = writer.getBuffer().toString(); // .replaceAll("\n|\r", "");

            writer.close();
            return output;
        } catch (TransformerException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 实体类中值转成xml的格式, 如果值为空的话,则生成空的xml节点 先不考虑复杂节点,不考虑list节点
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> String beanToXml(T t) {
        try {
            Document document = XmlBase.newDocument();
            document.setXmlStandalone(true);

            // 先处理根节点
            // 对这个类创建一个节点
            Class<?> clazz = t.getClass();
            String rootXmlName = clazz.getSimpleName();
            XmlField rootXmlField = clazz.getAnnotation(XmlField.class);
            if (rootXmlField != null) {
                rootXmlName = rootXmlField.xmlRoot();
            }
            Element root = document.createElement(rootXmlName);
            document.appendChild(doBeanToElement(root, t, document));
            // 生成字符串
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document.getFirstChild());
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            String output = writer.getBuffer().toString(); // .replaceAll("\n|\r", "");
            output = output.substring(38);
            writer.close();
            return output;
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 取值,生成xml
        return null;
    }

    private static <T> Node doBeanToElement(Node root, T t, Document document) {

        try {

            // 先获取实体类里面的字段
            Field[] allFields = ReflectTools.getAllFields(t.getClass());
            for (Field field : allFields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (fieldName.contains("this") || Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                Class<?> fieldType = field.getType();
                XmlField xmlField = field.getAnnotation(XmlField.class);
                //获取下面新的节点名称
                String childName = fieldName;
                String row = null;
                if (xmlField != null) {
                    childName = xmlField.xmlEle();
                     row= xmlField.row();
                }


                if (ReflectTools.simpleClass(fieldType)) {
                    // 这里要考虑到list的情况
                    if (fieldType == List.class) {
                        Element child = document.createElement(childName);
                        root.appendChild(child);
                        List objList = (List) field.get(t);
                        if(objList == null){
                            continue;
                        }
                        for (Object o : objList) {
                            Element rowEle = document.createElement(row);
                            child.appendChild(doBeanToElement(rowEle,o,document));
                        }
                        continue;
                    }
                    Object obj = field.get(t);
                    String value = "";
                    if (obj != null) {
                        value = obj.toString();
                    }
                    value = value.trim();
                    Element child = document.createElement(childName);
                    child.appendChild(document.createTextNode(value));
                    root.appendChild(child);
                } else {
                    // 如果是复杂类型
                    Element child = document.createElement(childName);

                    if (StringTools.isNotBlank(row)) {
                        Element rowEle = document.createElement(row);
                        root.appendChild(child);
                        child.appendChild(doBeanToElement(rowEle, field.get(t), document));
                    } else {
                        root.appendChild(doBeanToElement(child, field.get(t), document));
                    }
                }
            }
            return root;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        Model model = new Model("李翔", 26);
        Fee fee = new Fee();
        fee.setFeeStr("test");
        model.setFeeList(fee);
        String s  = beanToXml(model);
        System.out.println(s);
    }

    @XmlField(xmlRoot = "TTTT")
    static class Model {
        @XmlField(xmlEle = "NAME")
        private String name;
        private Integer age;
        private String depart;
        @XmlField(xmlEle = "lists")
        private Fee feeList ;

        public Fee getFeeList() {
            return feeList;
        }

        public Model setFeeList(Fee feeList) {
            this.feeList = feeList;
            return this;
        }

        public String getDepart() {
            return depart;
        }

        public Model setDepart(String depart) {
            this.depart = depart;
            return this;
        }

        public Model(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

    }
    static  class Fee{
        private String feeStr;

        public String getFeeStr() {
            return feeStr;
        }

        public Fee setFeeStr(String feeStr) {
            this.feeStr = feeStr;
            return this;
        }
    }
}
