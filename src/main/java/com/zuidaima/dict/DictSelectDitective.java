package com.zuidaima.dict;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class DictSelectDitective implements TemplateDirectiveModel {
	
	

	@Override
	public void execute(Environment env, Map params, TemplateModel[] arg2,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
	SimpleScalar id = (SimpleScalar) params.get("id");
	SimpleScalar name = (SimpleScalar) params.get("name");
	SimpleScalar type = (SimpleScalar) params.get("type");
	TemplateModel value = (SimpleScalar) params.get("value");
	SimpleScalar style = (SimpleScalar) params.get("class");
	SimpleScalar object = (SimpleScalar) params.get("id");
	SimpleScalar option = (SimpleScalar) params.get("option");
	
	
	StringBuffer buffer = new StringBuffer();
	
	List<String[]> list = DictContent.getInstance().map.get(type.getAsString());
	
	if(list!=null)
	{
		
		int values=0;
		
		if(value instanceof SimpleNumber)
		{
			values=((SimpleNumber)value).getAsNumber().intValue();
		}
		else if(value instanceof SimpleScalar)
		{
			values=Integer.valueOf(((SimpleScalar)value).getAsString());
		}
		
		
		buffer.append("<select>");
		for (String[] s : list)
		{
			
			
			
			buffer.append("<option value='"+s[0]+"'");
			
			if(values==Integer.parseInt(s[0]))
			{
				buffer.append(" selected ");
			}
			
			buffer.append(">").append(s[1]).append("</option>");
		}
		
		buffer.append("</select>");

		
	}
	
	Writer writer=env.getOut();
	
	writer.write(buffer.toString());
	
	
	
	body.render(writer);

	
	
	
	
	
		
	}

}
