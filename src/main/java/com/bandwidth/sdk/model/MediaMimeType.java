package com.bandwidth.sdk.model;

/**
 * Created by smitchell on 11/18/14.
 */
public enum MediaMimeType {

    IMAGE_JPG("image/jpg"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_XMSBMP("image/x-ms-bmp"),
    IMAGE_BMP("image/bmp"),
    IMAGE_GIF("image/gif"),
    AUDIO_MPEG("audio/mpeg"),
    AUDIO_MP3("audio/mp3"),
    AUDIO_WAV("audio/wav"),
    AUDIO_XWAV("audio/x-wav"),
    AUDIO_3GPP("audio/3gpp"),
    VIDEO_3GPP("video/3gpp"),
    VIDEO_3GPP2("video/3gpp2"),
    VIDEO_AVI("video/avi"),
    VIDEO_XMSVIDEO("video/x-msvideo"),
    VIDEO_MP4("video/mp4"),
    VIDEO_MPEG("video/mpeg"),
    UNKOWN("unknown");

    private final String val;

    private MediaMimeType(final String val) {
        this.val = val;
    }

    public String toString() {
        return val;
    }

    public static MediaMimeType getEnum(final String type) {
        if (IMAGE_JPG.toString().equalsIgnoreCase(type))
            return IMAGE_JPG;
        else if (IMAGE_JPEG.toString().equalsIgnoreCase(type))
            return IMAGE_JPEG;
        else if (IMAGE_PNG.toString().equals(type))
            return IMAGE_PNG;
        else if (IMAGE_XMSBMP.toString().equals(type))
            return IMAGE_XMSBMP;
        else if (IMAGE_BMP.toString().equalsIgnoreCase(type))
            return IMAGE_BMP;
        else if (IMAGE_GIF.toString().equals(type))
            return IMAGE_GIF;
        else if (AUDIO_MPEG.toString().equals(type))
            return AUDIO_MPEG;
        else if (AUDIO_MP3.toString().equals(type))
            return AUDIO_MP3;
        else if (AUDIO_WAV.toString().equals(type))
            return AUDIO_WAV;
        else if (AUDIO_XWAV.toString().equals(type))
            return AUDIO_XWAV;
        else if (AUDIO_3GPP.toString().equals(type))
            return AUDIO_3GPP;
        else if (VIDEO_3GPP.toString().equals(type))
            return VIDEO_3GPP;
        else if (VIDEO_3GPP2.toString().equals(type))
            return VIDEO_3GPP2;
        else if (VIDEO_AVI.toString().equals(type))
            return VIDEO_AVI;
        else if (VIDEO_XMSVIDEO.toString().equals(type))
            return VIDEO_XMSVIDEO;
        else if (VIDEO_MP4.toString().equals(type))
            return VIDEO_MP4;
        else if (VIDEO_MPEG.toString().equals(type))
            return VIDEO_MPEG;
        else
            return UNKOWN;
    }
}
