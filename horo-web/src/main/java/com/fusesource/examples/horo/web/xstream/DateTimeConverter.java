package com.fusesource.examples.horo.web.xstream;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class DateTimeConverter implements Converter {

    @Override
    public boolean canConvert(Class clazz) {
        return clazz.isAssignableFrom(DateTime.class);
    }

    @Override
    public void marshal(Object value, HierarchicalStreamWriter writer,
                        MarshallingContext context) {
        DateTime dateTime = (DateTime) value;
        DateTimeFormatter formatter = ISODateTimeFormat.basicDateTime().withZoneUTC();
        writer.startNode("dateTime");
        writer.setValue(formatter.print(dateTime));
        writer.endNode();
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        reader.moveDown();
        DateTimeFormatter formatter = ISODateTimeFormat.basicDateTime().withZoneUTC();
        DateTime dateTime = formatter.parseDateTime(reader.getValue());
        reader.moveUp();
        return dateTime;
    }

}
