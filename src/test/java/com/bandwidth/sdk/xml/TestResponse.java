package com.bandwidth.sdk.xml;

import com.bandwidth.sdk.exception.XMLInvalidAttributeException;
import com.bandwidth.sdk.exception.XMLInvalidTagContentException;
import com.bandwidth.sdk.exception.XMLMarshallingException;
import com.bandwidth.sdk.xml.elements.*;
import junit.framework.Assert;
import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class TestResponse {

    private static final String DUMMY_AUDIO_URL = "http://dummy.audio.url";

    private static final String DUMMY_REQUEST_URL = "http://dummy.request.url";

    @Test
    public void testHangup() throws IOException, XMLMarshallingException, ParserConfigurationException, SAXException {
        Response response = new Response();
        Hangup hangup = new Hangup();

        response.add(hangup);

        String output = response.toXml();
        String xmlReference = IOUtils.toString(getClass().getResourceAsStream("/hangup.xml"), "UTF-8");
        compareXML(xmlReference, output);
    }

    @Test
    public void testPlayAudio() throws IOException, XMLMarshallingException, XMLInvalidTagContentException, ParserConfigurationException, SAXException {
        Response response = new Response();
        PlayAudio playAudio = new PlayAudio(DUMMY_AUDIO_URL);

        response.add(playAudio);

        String output = response.toXml();
        String xmlReference = IOUtils.toString(getClass().getResourceAsStream("/playaudio.xml"), "UTF-8");
        compareXML(xmlReference, output);
    }

    @Test
    public void testPlayAudioWithDigits() throws IOException, XMLMarshallingException, XMLInvalidTagContentException, ParserConfigurationException, SAXException {
        Response response = new Response();
        PlayAudio playAudio = new PlayAudio(DUMMY_AUDIO_URL, "12345");

        response.add(playAudio);

        String output = response.toXml();
        String xmlReference = IOUtils.toString(getClass().getResourceAsStream("/playaudiowithdigits.xml"), "UTF-8");
        compareXML(xmlReference, output);
    }

    @Test
    public void testRedirect() throws IOException, XMLMarshallingException, XMLInvalidAttributeException, ParserConfigurationException, SAXException {
        Response response = new Response();
        // timeout expressed in milliseconds
        Redirect redirect = new Redirect(DUMMY_REQUEST_URL, 6000);

        response.add(redirect);

        String output = response.toXml();
        String xmlReference = IOUtils.toString(getClass().getResourceAsStream("/redirect.xml"), "UTF-8");
        compareXML(xmlReference, output);
    }

    @Test
    public void testSpeakSentence() throws IOException, XMLMarshallingException, XMLInvalidAttributeException, ParserConfigurationException, SAXException {
        Response response = new Response();
        SpeakSentence speakSentence = new SpeakSentence("This is a test of spoken sentence",
                                                        "paul",
                                                        "male",
                                                        "en_US");

        response.add(speakSentence);

        String output = response.toXml();
        String xmlReference = IOUtils.toString(getClass().getResourceAsStream("/speaksentence.xml"), "UTF-8");
        compareXML(xmlReference, output);
    }

    @Test
    public void testSendMessage() throws IOException, XMLMarshallingException, XMLInvalidAttributeException, ParserConfigurationException, SAXException {
        Response response = new Response();
        SendMessage sendMessage = new SendMessage("+1234567890", "+1987654320", "This is the message text");
        sendMessage.setRequestUrl("http://localhost:8082/dummy", 10);

        response.add(sendMessage);

        String output = response.toXml();
        String xmlReference = IOUtils.toString(getClass().getResourceAsStream("/sendmessage.xml"), "UTF-8");
        compareXML(xmlReference, output);
    }

    @Test
    public void testTransfer() throws IOException, XMLMarshallingException, XMLInvalidAttributeException, ParserConfigurationException, SAXException {
        Response response = new Response();
        // timeout expressed in milliseconds
        Transfer transfer = new Transfer("+1234567890", "+0123456789");

        response.add(transfer);

        String output = response.toXml();
        String xmlReference = IOUtils.toString(getClass().getResourceAsStream("/transfer.xml"), "UTF-8");
        compareXML(xmlReference, output);
    }

    @Test
    public void testSpeakSentenceWithinTransfer() throws IOException, XMLMarshallingException, XMLInvalidAttributeException, ParserConfigurationException, SAXException {
        Response response = new Response();
        SpeakSentence speakSentence = new SpeakSentence("This is a test of spoken sentence",
                "paul",
                "male",
                "en_US");
        Transfer transfer = new Transfer("+1234567890", "+0123456789", speakSentence);
        response.add(transfer);

        String output = response.toXml();
        String xmlReference = IOUtils.toString(getClass().getResourceAsStream("/speaksentencewithintransfer.xml"), "UTF-8");
        compareXML(xmlReference, output);
    }

    @Test
    public void testPlayAudio_Hangup() throws IOException, XMLMarshallingException, XMLInvalidTagContentException, ParserConfigurationException, SAXException {
        Response response = new Response();
        PlayAudio playAudio = new PlayAudio(DUMMY_AUDIO_URL);
        Hangup hangup = new Hangup();

        response.add(playAudio);
        response.add(hangup);

        String output = response.toXml();
        String xmlReference = IOUtils.toString(getClass().getResourceAsStream("/playaudio_hangup.xml"), "UTF-8");
        compareXML(xmlReference, output);
    }

    @Test
    public void testTransfer_SpeakSentence_Redirect() throws IOException, XMLMarshallingException, XMLInvalidAttributeException, ParserConfigurationException, SAXException {
        Response response = new Response();
        Transfer transfer = new Transfer("+1234567890", "+0123456789");
        SpeakSentence speakSentence = new SpeakSentence("Next command will get and run another xml from requestUrl",
                "paul",
                "male",
                "en_US");
        Redirect redirect = new Redirect(DUMMY_REQUEST_URL, 6000);

        response.add(transfer);
        response.add(speakSentence);
        response.add(redirect);

        String output = response.toXml();
        String xmlReference = IOUtils.toString(getClass().getResourceAsStream("/transfer_speaksentence_redirect.xml"), "UTF-8");
        compareXML(xmlReference, output);
    }

    @Test(expected = XMLInvalidTagContentException.class)
    public void testPlayAudio_InvalidCase_NullAudioUrl() throws XMLMarshallingException, XMLInvalidTagContentException {
        new PlayAudio(null);
    }

    @Test(expected = XMLInvalidTagContentException.class)
    public void testPlayAudio_InvalidCase_EmptyAudioUrl() throws XMLMarshallingException, XMLInvalidTagContentException {
        new PlayAudio("");
    }

    @Test(expected = XMLInvalidTagContentException.class)
    public void testPlayAudio_InvalidCase_NullAudioUrlWithDigits() throws XMLMarshallingException, XMLInvalidTagContentException {
        new PlayAudio(null, "12345");
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testRedirect_InvalidCase_NullRequesturl() throws XMLMarshallingException, XMLInvalidAttributeException {
        new Redirect(null, 6000);
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testRedirect_InvalidCase_EmptyRequesturl() throws XMLMarshallingException, XMLInvalidAttributeException {
        new Redirect("", 6000);
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testRedirect_InvalidCase_TimeoutLessThanZero() throws XMLMarshallingException, XMLInvalidAttributeException {
        new Redirect(DUMMY_REQUEST_URL, 0);
    }

    @Test(expected = XMLMarshallingException.class)
    public void test_InvalidCase_NoTags() throws XMLMarshallingException {
        Response response = new Response();
        response.toXml();
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testSpeakSentence_InvalidCase_NullSentence() throws XMLInvalidAttributeException {
        new SpeakSentence(null,
                "paul",
                "male",
                "en_US");
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testSpeakSentence_InvalidCase_EmptySentence() throws XMLInvalidAttributeException {
        new SpeakSentence("",
                "paul",
                "male",
                "en_US");
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testSpeakSentence_InvalidCase_NullVoice() throws XMLInvalidAttributeException {
        new SpeakSentence("anysentence",
                null,
                "male",
                "en_US");
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testSpeakSentence_InvalidCase_EmptyVoice() throws XMLInvalidAttributeException {
        new SpeakSentence("anysentence",
                "",
                "male",
                "en_US");
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testSpeakSentence_InvalidCase_NullGender() throws XMLInvalidAttributeException {
        new SpeakSentence("anysentence",
                "paul",
                null,
                "en_US");
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testSpeakSentence_InvalidCase_EmptyGender() throws XMLInvalidAttributeException {
        new SpeakSentence("anysentence",
                "paul",
                "",
                "en_US");
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testSpeakSentence_InvalidCase_NullLocale() throws XMLInvalidAttributeException {
        new SpeakSentence("anysentence",
                "paul",
                "male",
                null);
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testSpeakSentence_InvalidCase_EmptyLocale() throws XMLInvalidAttributeException {
        new SpeakSentence("anysentence",
                "paul",
                "male",
                "");
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testTransfer_InvalidCase_NullTransferTo() throws XMLInvalidAttributeException {
        new Transfer(null, "+0123456789");
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testTransfer_InvalidCase_EmptyTransferTo() throws XMLInvalidAttributeException {
        new Transfer("", "+0123456789");
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testTransfer_InvalidCase_NullTransferCallerId() throws XMLInvalidAttributeException {
        new Transfer("+0123456789", null);
    }

    @Test(expected = XMLInvalidAttributeException.class)
    public void testTransfer_InvalidCase_EmptyTransferCallerId() throws XMLInvalidAttributeException {
        new Transfer("+0123456789", "");
    }

    private void compareXML (String expected, String current) throws ParserConfigurationException, IOException, SAXException {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);

        DetailedDiff diff = new DetailedDiff(XMLUnit.compareXML(expected, current));

        List<?> allDifferences = diff.getAllDifferences();
        Assert.assertEquals("Differences found: "+ diff.toString(), 0, allDifferences.size());
    }

}
