package com.bandwidth.sdk.xml;

import com.bandwidth.sdk.exception.XMLMarshallingException;
import com.bandwidth.sdk.xml.elements.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "Response")
public class Response {

    private final List<Elements> verbList = new ArrayList<Elements>();
    
    private final JAXBContext jc;
    private final Marshaller marshaller;
    
    public Response() throws JAXBException {
        this.jc = JAXBContext.newInstance(Hangup.class, Transfer.class, 
                SpeakSentence.class, PlayAudio.class, Redirect.class);
        this.marshaller = jc.createMarshaller();
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        this.marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
    }

    public void add(final Elements verb) {
        verbList.add(verb);
    }

    public String toXml() throws XMLMarshallingException {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        stringBuilder.append("<Response>");

        if (verbList.isEmpty()) {
            throw new XMLMarshallingException("Error marshalling xml, at least one tag within <Response> is required");
        }

        Writer writer;
        for (final Elements verb : verbList) {
            try {
                writer = new StringWriter();
                this.marshaller.marshal(verb, writer);
                stringBuilder.append(writer.toString());
            } catch (final JAXBException ex) {
                throw new XMLMarshallingException("Error marshalling " + verb.toString(), ex);
            }
        }
        stringBuilder.append("</Response>");
        return stringBuilder.toString();
    }
}