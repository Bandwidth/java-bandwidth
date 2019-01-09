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

    private List<Elements> verbList = new ArrayList<Elements>();

    public void add(Elements verb) {
        verbList.add(verb);
    }

    public String toXml() throws XMLMarshallingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        stringBuilder.append("<Response>");

        if (verbList.isEmpty()) {
            throw new XMLMarshallingException("Error marshalling xml, at least one tag within <Response> is required");
        }

        for (Elements verb : verbList) {
            try {
                JAXBContext jc = JAXBContext.newInstance(Hangup.class, Transfer.class, SpeakSentence.class,
                        PlayAudio.class, Redirect.class, SendMessage.class, Gather.class, Record.class,
                        SendDtmf.class, Pause.class);
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

                Writer writer = new StringWriter();
                marshaller.marshal(verb, writer);
                writer.toString();
                stringBuilder.append(writer.toString());
            } catch (JAXBException ex) {
                throw new XMLMarshallingException("Error marshalling " + verb.toString(), ex);
            }
        }
        stringBuilder.append("</Response>");
        return stringBuilder.toString();
    }
}