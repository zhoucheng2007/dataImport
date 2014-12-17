package com.util;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.*;


import org.phprpc.util.Cast;
import org.phprpc.util.PHPSerializer;

public class SerializerUtil {

	private static PHPSerializer serializer = new PHPSerializer();

	public static String serialize(Object obj) {
		byte[] b;
		try {
			b = serializer.serialize(obj);
			return new String(b, "utf-8");
		} catch (Exception e) {
		}
		return "";
	}

	public static Object unserialize(String str) {
		try {
			return serializer.unserialize(str.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T unserialize(String str, Class<T> cls) {
		try {
			return (T) serializer.unserialize(str.getBytes("utf-8"), cls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T unserialize(String str, Class<T> cls, boolean tostring) {
		Object obj;

		try {
			obj = serializer.unserialize(str.getBytes("utf-8"), cls);

			if (tostring) {
				if (obj instanceof Map) {
					Map m = (Map) obj;
					Iterator it = m.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry entry = (Map.Entry) it.next();
						m.put(entry.getKey(),
								cast(entry.getValue(), String.class));
					}
				} else if (obj instanceof List) {
					List list = (List) obj;
					for (int i = 0; i < list.size(); ++i) {
						list.set(i, cast(list.get(i), String.class));
					}
				}
			}
			return (T) obj;
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T cast(Object obj, Class<T> cls) {
		return (T) Cast.cast(obj, cls);
	}

	public static Map<String, List> getSubBeKeyArray(List<Map> data, String key) {
		if ((data == null) || (key == null) || ("".equals(key)))
			return null;
		Map ret = new HashMap();
		String[] keys = key.split(",");
		for (int i = 0; i < data.size(); ++i)
			for (int j = 0; j < keys.length; ++j) {
				String tkey = keys[j].trim();
				List values = (List) ret.get(tkey);
				if (values == null)
					values = new ArrayList();
				values.add(((Map) data.get(i)).get(tkey));
				ret.put(tkey, values);
			}

		return ret;
	}

	public static List getSubByKey(List<Map> data, String key) {
		if ((data == null) || (key == null) || ("".equals(key)))
			return null;
		List ret = new ArrayList();
		key = key.trim();
		for (int i = 0; i < data.size(); ++i) {
			Map values = (Map) data.get(i);
			ret.add(values.get(key));
		}
		return ret;
	}

	public static String time() {
		long time = System.currentTimeMillis();
		String t = String.valueOf(time);
		return t.substring(0, 10);
	}

	public static List removeSame(List data) {
		Set set = new HashSet();
		set.addAll(data);
		List res = new ArrayList();
		res.addAll(set);
		return res;
	}

	public static String md5(String plainText) {
		if (plainText == null)
			plainText = "";
		byte[] temp = plainText.getBytes();

		StringBuffer buffer = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("md5");
			md.update(temp);
			temp = md.digest();

			int i = 0;
			for (int offset = 0; offset < temp.length; ++offset) {
				i = temp[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buffer.append("0");
				buffer.append(Integer.toHexString(i));
			}
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

		return buffer.toString();
	}

	public static Map CastByteToString(Map m) {
		Iterator it = m.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			if (entry.getValue() instanceof byte[])
				m.put(entry.getKey(), cast(entry.getValue(), String.class));
		}

		return m;
	}

	public static List CastByteToString(List ll) {
		List c = new ArrayList();
		for (Iterator localIterator = ll.iterator(); localIterator.hasNext();) {
			Object l = localIterator.next();
			if (l instanceof byte[]) {
				c.add(cast(l, String.class));
				break;
			}
			label56: c.add(l);
		}

		return c;
	}

}
