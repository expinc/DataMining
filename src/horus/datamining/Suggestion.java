package horus.datamining;

import java.util.Map;


public interface Suggestion
{
	public Object getFieldValue(String name);


	public Map<String, Object> getAllFields(); // name to value
	// TODO: implement
}
