package org.sharkness.artifacts.jsf;

public class ViewValue {

	private String name;
	
	private Boolean dataTable;
	
	private TagValues tag;

	public ViewValue(String name, Boolean dataTable, TagValues tag) {
		setName(name);
		setDataTable(dataTable);
		setTag(tag);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getDataTable() {
		return dataTable;
	}

	public void setDataTable(Boolean dataTable) {
		this.dataTable = dataTable;
	}

	public TagValues getTag() {
		return tag;
	}

	public void setTag(TagValues tag) {
		this.tag = tag;
	}

}