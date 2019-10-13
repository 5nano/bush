package com.nano.Bush.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EncryptUtils {

  public static final String DEFAULT_ENCODING = "UTF-8";
  static final BASE64Encoder base64Encoder = new BASE64Encoder();
  static final BASE64Decoder base64Decoder = new BASE64Decoder();

  public static String encode(String text) {
    try {
      return base64Encoder.encode(text.getBytes(DEFAULT_ENCODING));
    } catch (UnsupportedEncodingException e) {
      return null;
    }
  }//base64encode

  public static String decode(String text) {
    try {
      return new String(base64Decoder.decodeBuffer(text), DEFAULT_ENCODING);
    } catch (IOException e) {
      return null;
    }
  }//base64decode

  public static void main(String[] args) {
    String txt = "some text to be encrypted";
    String key = "key phrase used for XOR-ing";
    System.out.println(txt + " XOR-ed to: " + (txt = xorMessage(txt, key)));

    String encoded = encode(txt);
    System.out.println(" is encoded to: " + encoded + " and that is decoding to: " + (txt = decode(encoded)));
    System.out.print("XOR-ing back to original: " + xorMessage(txt, key));
  }

  public static String xorMessage(String message, String key) {
    try {
      if (message == null || key == null) return null;

      char[] keys = key.toCharArray();
      char[] mesg = message.toCharArray();

      int ml = mesg.length;
      int kl = keys.length;
      char[] newmsg = new char[ml];

      for (int i = 0; i < ml; i++) {
        newmsg[i] = (char)(mesg[i] ^ keys[i % kl]);
      }//for i

      return new String(newmsg);
    } catch (Exception e) {
      return null;
    }
  }//xorMessage
}//c

