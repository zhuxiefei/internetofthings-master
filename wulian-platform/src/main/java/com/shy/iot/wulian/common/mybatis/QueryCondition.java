package com.shy.iot.wulian.common.mybatis;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class QueryCondition {
	/** =查询 */
	public final static int TYPE_EQUAL = 1;

	/** 模糊查询 */
	public final static int TYPE_LIKE = 2;

	/** IN查询 */
	public final static int TYPE_IN = 3;

	/** 不等于查询 */
	public final static int TYPE_NOT_EQUAL = 4;

	/** 不匹配查询 */
	public final static int TYPE_NOT_LIKE = 5;

	/** 不在查询 */
	public final static int TYPE_NOT_IN = 6;

	/** 之间查询 */
	public final static int TYPE_BETWEEN = 7;

	/** 大于查询 */
	public final static int TYPE_MORE_THEN = 8;

	/** 小与查询 */
	public final static int TYPE_LES_THEN = 9;

	/** 为空 */
	public final static int TYPE_IS_NULL = 10;

	/** 不为空查询 */
	public final static int TYPE_IS_NOT_NUL = 11;
	/**
	 * 查询子组织机构 in
	 */
	public final static int TYPE_ORG_IN = 12;
	/**
	 * 查询子组织机构 in 不带字段名
	 */
	public final static int TYPE_ORG_IN_NO_NAME = 13;

	/**
	 * 查询子组织机构 not in
	 */
	public final static int TYPE_ORG_NOT_IN = 14;

	/** 模糊查询 只在右边加 % */
	public final static int TYPE_LIKE_RIGHT = 15;

	/** 或查询 */
	public final static int TYPE_OR = 16;

	/** 多个字段或查询 */
	public final static int TYPE_MORE_OR = 17;

	/** 多个字段或查询 */
	public final static int TYPE_THREE_OR = 18;

	private String column;

	private String column2;
	
	private String column3;

	private Object value;

	private int type;

	private String exp;

	public QueryCondition() {
	}

	public QueryCondition(String column, Object value) {
		this.setColumn(column);
		this.setValue(value);
		this.setType(TYPE_EQUAL);
	}

	public QueryCondition(String column, Object value, int type) {
		this.setColumn(column);
		this.setValue(value);
		this.setType(type);
	}

	public QueryCondition(String column, String column2, Object value, int type) {
		this.setColumn(column);
		this.setColumn2(column2);
		this.setValue(value);
		this.setType(type);
	}

	public QueryCondition(String column, String column2, String column3,
			Object value, int type) {
		this.setColumn(column);
		this.setColumn2(column2);
		this.setColumn3(column3);
		this.setValue(value);
		this.setType(type);
	}

	/**
	 * @return 返回 column
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * @param 对column进行赋值
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	public String getColumn2() {
		return column2;
	}

	public void setColumn2(String column2) {
		this.column2 = column2;
	}

	
	public String getColumn3() {
		return column3;
	}

	public void setColumn3(String column3) {
		this.column3 = column3;
	}

	/**
	 * @return 返回 value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param 对value进行赋值
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	@SuppressWarnings("rawtypes")
	public String getExp() {
		if (StringUtils.isNotBlank(exp)) {
			return exp;
		}
		if (StringUtils.isBlank(column)) {
			return null;
		} else {
			// 防止注入
			if (value instanceof String) {
				String tempValue = (String) value;
				tempValue = StringEscapeUtils.escapeSql(tempValue);
				setValue(tempValue);
			}
			switch (getType()) {
			case 1:
				if (value instanceof String) {
					return " and " + column + "='" + value + "'";
				} else {
					return " and " + column + "=" + value;
				}

			case 2:
				return " and " + column + " like '%" + value + "%'";

			case 3:
				if (value instanceof List) {
					return " and " + column + "  in ("
							+ listToSqlInString((List) value) + ")";
				} else {
					return " and " + column + "  in (" + value + ")";
				}

			case 4:
				if (value instanceof String) {
					return " and " + column + " !='" + value + "'";
				} else {
					return " and " + column + " !=" + value;
				}
			case 5:
				return " and " + column + " not like '%" + value + "%'";
			case 6:
				if (value instanceof List) {
					return " and " + column + " not in ("
							+ listToSqlInString((List) value) + ")";
				} else {
					return " and " + column + " not in (" + value + ")";
				}
			case 7:
				if (value instanceof String[]) {
					if (((String[]) value)[0].indexOf("'") < 0) {
						return " and " + column + " between '"
								+ ((String[]) value)[0] + "' and '"
								+ ((String[]) value)[1] + "'";
					} else {
						return " and " + column + " between "
								+ ((String[]) value)[0] + " and "
								+ ((String[]) value)[1];
					}
				} else if (value instanceof String
						&& ((String) value).indexOf(",") > 0) {
					return " and " + column + " between "
							+ ((String) value).split(",")[0] + " and "
							+ ((String) value).split(",")[1];
				} else {

					@SuppressWarnings("unchecked")
					List<String> list = (List<String>) value;
					return " and " + column + " between " + list.get(0)
							+ " and " + list.get(1);
				}
			case 8:
				if (value instanceof String) {
					if (((String) value).indexOf("(") < 0) {
						return " and " + column + " >= '" + value + "'";
					} else {
						return " and " + column + " >= " + value;
					}
				} else {
					return " and " + column + " >= " + value;
				}
			case 9:
				if (value instanceof String) {
					if (((String) value).indexOf("(") < 0) {
						return " and " + column + " <= '" + value + "'";
					} else {
						return " and " + column + " <= " + value + "";
					}
				} else {
					return " and " + column + " <= " + value + "";
				}
			case TYPE_IS_NULL:
				return " and " + column + " is  null";

			case TYPE_IS_NOT_NUL:
				return " and " + column + " is not null";
			case TYPE_ORG_IN:
				return " and "
						+ column
						+ "  in (select o.org_code from tp_organization o start with o.org_code ='"
						+ value + "' connect by prior o.org_code = o.org_sup)";
			case TYPE_ORG_IN_NO_NAME:
				return "    (select o.org_code from tp_organization o start with o.org_code ='"
						+ value + "' connect by prior o.org_code = o.org_sup)";
			case TYPE_ORG_NOT_IN:
				return " and "
						+ column
						+ " not in (select o.org_code from tp_organization o start with o.org_code ='"
						+ value + "' connect by prior o.org_code = o.org_sup)";
			case 15:
				return " and " + column + " like '" + value + "%'";
			case 16:
				String text = "and ( ";
				if (value instanceof List) {
					List<Object> list = (List) value;
					for (int i = 0; i < list.size(); i++) {
						String v = list.get(i).toString();
						if (i == 0) {
							text += column + " = '" + v + "'";
						} else {
							text += " or " + column + " = '" + v + "'";
						}
					}
				}
				text += " )";
				return text;
			case 17:
				String text2 = "and ( ";
				text2 += column + " like '%" + value + "%'";
				text2 += " or " + column2 + " like '%" + value + "%'";
				text2 += " )";
				return text2;
			case 18:
				String text3 = "and ( ";
				text3 += column + " like '%" + value + "%'";
				text3 += " or " + column2 + " like '%" + value + "%'";
				text3 += " or " + column3 + " like '%" + value + "%'";
				text3 += " )";
				return text3;
			default:
				return null;
			}
		}
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	/**
	 * @return 返回 type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param 对type进行赋值
	 */
	public void setType(int type) {

		this.type = type;
	}

	/**
	 * 将List转换为Sql的in 类型的SQL字符串 <一句话功能简述> <功能详细描述>
	 * 
	 * @param list
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String listToSqlInString(List<?> list) {
		String inVal = "";
		for (Object val : list) {
			if (val instanceof String) {
				inVal += ("'" + val + "'");
			} else {
				inVal += val;
			}
			if (val != list.get(list.size() - 1)) {
				inVal += ", ";
			}
		}
		return inVal;
	}

}
