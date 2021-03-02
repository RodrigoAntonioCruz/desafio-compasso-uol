package com.compasso.uol.repositories.specs;

public class SearchCriteria {
	private String k;
	private String key;
	private Object value;
	private SearchOperation operation;

	public SearchCriteria() {
	}

	public SearchCriteria(String key, Object value, SearchOperation operation) {
		this.key = key;
		this.value = value;
		this.operation = operation;
	}

	public SearchCriteria(String key, String k, Object value, SearchOperation operation) {
		this.key = key;
		this.k = k;
		this.value = value;
		this.operation = operation;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public SearchOperation getOperation() {
		return operation;
	}

	public void setOperation(SearchOperation operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		return "SearchCriteria{" + "k='" + key + '\'' + "key='" + key + '\'' + ", value=" + value + ", operation=" + operation + '}';
	}
}
