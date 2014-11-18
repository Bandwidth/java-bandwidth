package com.bandwidth.sdk.examples;

import com.bandwidth.sdk.model.Media;
import com.bandwidth.sdk.model.MediaFile;
import com.bandwidth.sdk.model.MediaMimeType;
import com.bandwidth.sdk.model.Message;
import java.io.File;

/**
 * This example shows how to send an MMS message using the Media, MediaFile and Message classes. You start by uploading
 * a media file to the App Platform server using the Media.upload() method. Then simple create a new message with the
 * returns MediaFile object.
 *
 * To run this program you want to pass in the path to an image file. If you run this is an IDE, you can set the
 * argument to one of the image files in src/main/resources, e.g. "catapult1.jpg". Note that you must provide the
 * fully qualified path to the file.
 *
 * And don't forget to set your credentials. See the CredentialsExample for an explanation of how to do this.
 *
 * Created by smitchell on 11/17/14.
 */
public class MMSExample {
    public static void main(String []args){
        for (String arg : args) {
            System.out.println("arg:" + arg);
        }

        if (args.length < 1) {
            System.out.println("Please provide a path to a media file.");
            System.out.println("Usage: com.bandwidth.sdk.examples.MMSExample <path to resource file>");
            System.exit(0);
        }

        String fileName = args[0];
        System.out.println("fileName:" + fileName);

        File file = new File(fileName);

        String toNumber = "+1"; // populate this with the number you want to send the mms text to
        String fromNumber = "+1"; // populate this with your Application Platform number
        String text = "test, test, check out this great catapult!";


        try {
            Media media = Media.create();

            MediaFile mediaFile = media.upload(file.getName(), file, MediaMimeType.IMAGE_JPG);

            System.out.println("mediaFile:" + mediaFile);

            Message message = Message.create(toNumber, fromNumber, text, mediaFile);

            System.out.println(message);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
