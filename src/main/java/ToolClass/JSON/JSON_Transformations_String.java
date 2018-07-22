package ToolClass.JSON;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 解析JSON格式的String字符串
 * 工具类
 * @author Administrator
 *
 */
public class JSON_Transformations_String {

	/**
	 * 传入JSON格式的str字符串   key  返回JSON的值
	 * @param str
	 * @param KeyWord
	 * @return
	 */
	public String Transformations_String (String str,String KeyWord){
		//将json字符串转化为JSONObject
		JSONObject jsonObject = JSONObject.fromObject(str);
		// 判断关键字是否存在
		try {
			//通过getString("")分别取出里面的信息
			return jsonObject.getString(KeyWord);
		} catch (Exception e) {
			return "{\"errMsg\":\"Key words do not exist\"}";
		}
	}

	/**
	 * 传入JSON数组格式的字符串,返回JSON的值
	 * @param json     JSON 字符串
	 * @param element  数组 元素
	 * @param position 元素所在的位置
	 * @param KeyWord  关键字
	 * @return
	 */
	public String JSONArray_Transformations_String (String json,String element,int position,String KeyWord){
		//将jsonArray字符串转化为JSONArray
		JSONArray jsonArray = JSONArray.fromObject(json);
		//取出数组第一个元素
		JSONObject jUser = jsonArray.getJSONObject(position).getJSONObject(element);
		// 判断关键字是否存在
		try {
			//通过getString("")分别取出里面的信息
			return jUser.getString(KeyWord);
		} catch (Exception e) {
			return "{\"errMsg\":\"Key words do not exist\"}";
		}
	}
}
