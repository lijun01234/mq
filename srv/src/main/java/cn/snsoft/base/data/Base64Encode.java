
//Title:        南北软件
//Version:
//Copyright:    Copyright (c) 1999
//Author:       曹玉武
//Company:      南北公司
//Description:  南北软件,南北公司
package cn.snsoft.base.data;
import java.io.*;
/*
	public static final String base64Encode(InputStream is) throws IOException
	{
		StringBuffer  buffer = new StringBuffer();
		int remainder = 0;  int bitsRemainder = 0;
		int outCount = 0;
		for(;;)
		{
			if(bitsRemainder>=6)
			{
				//os.write(toBase64Code((remainder>>(bitsRemainder-6))&63));
				buffer.append((char)toBase64Code((remainder>>(bitsRemainder-6))&63));
				outCount++;
				bitsRemainder -= 6;
				continue;
			}
			int indata = is.read();
			if( indata<0 ) break;
			remainder = (remainder<<8)|(indata&0xff);  bitsRemainder+=8;
		}
		if( bitsRemainder>0 ) // bitsRemainder<6
		{
			//os.write(toBase64Code((remainder<<(6-bitsRemainder))&63));
			buffer.append((char)toBase64Code((remainder<<(6-bitsRemainder))&63));
			outCount++;
		}
		for(;(outCount&3)!=0;outCount++)
			buffer.append('=');
			//os.write('=');
		return buffer.toString();
		//return outCount;
	}
*/
//import java.io.*;
/*
  "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
*/
@Deprecated
public class Base64Encode  //extends snsoft.commons.util.Base64.Encoder
{
	/*
	public Base64Encode()
	{
           // os = new ByteArrayOutputStream() ;
	}
	public Base64Encode(java.io.OutputStream os)
	{
            super(os);
	} */
	
	final static public String encode(byte a[]) throws IOException
	{
		return Base64.encode(a);
	}
	
	final static public String encodeString(String text,String charset) throws IOException
    {
    	return encode(charset==null?text.getBytes():text.getBytes(charset));
    }
	
}
